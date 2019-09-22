package com.onlineedu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlineedu.eduservice.entity.EduSubject;
import com.onlineedu.eduservice.exceptions.MyException;
import com.onlineedu.eduservice.mapper.EduSubjectMapper;
import com.onlineedu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlineedu.eduservice.vo.OneLevelSubjectDTO;
import com.onlineedu.eduservice.vo.TwoLevelSubjectDTO;
import io.swagger.models.auth.In;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Hzhero
 * @since 2019-09-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {






    //添加分类信息  导入数据
    @Override
    public List<String> importData(MultipartFile file) {


            //创建集合装数据,存储错误信息
            List<String> msg = new ArrayList<>();
        try {
            //1、创建workbook，在workbook传递文件输入流
            InputStream in = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(in);
            //2、读取sheet
            Sheet sheet = workbook.getSheetAt(0);
            //3、读取row
            //首先要确定行数
            //第一行是表头无需读取，从第二行开始读取

            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum <= 1){
                msg.add("导入数据为空");
                return msg;
            }

            String oneLevelSubjectValue="";
            String twoLevelSubjectValue="";
            String parentId = null;
            //获取一级分类
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                //判断行不为空
                if(row == null){
                    msg.add("第"+(i+1)+"为空");
                    continue;
                }
                    //4、读取第一列cell
                    Cell cellOne = row.getCell(0);
                    //5、读取cell中的值
                        if(cellOne != null){
                            oneLevelSubjectValue  = cellOne.getStringCellValue();
                            if(StringUtils.isEmpty(oneLevelSubjectValue)){
                                msg.add("第"+(i+1)+"行一级分类为空");
                                //跳出当前循环
                                continue;
                            }
                        }

                //添加一级分类之前先判断数据库中是否存在相同名称的一级分类

                EduSubject existEduSubjectOne = this.existOneSubject(oneLevelSubjectValue);
                //如果不存在相同名称的一级分类，则添加到数据库中
                    if(existEduSubjectOne == null){
                        EduSubject subjectOne = new EduSubject();
                        subjectOne.setTitle(oneLevelSubjectValue);
                        //parent_id是0 表示一级分类
                        subjectOne.setParentId("0");
                        //6、把读取的内容添加到数据库中
                        baseMapper.insert(subjectOne);
                        //添加完成一级分类之后，获取添加之后的一级分类id值
                        parentId = subjectOne.getId();
                    }else{
                        //如果存在一级分类，则不添加直接获得parentId
                        parentId = existEduSubjectOne.getId();
                    }

                //获取二级分类,添加之前需要一级分类的id作为parent_id
                Cell cellTwo = row.getCell(1);
                if(cellTwo != null){
                    twoLevelSubjectValue = cellTwo.getStringCellValue();
                    if(StringUtils.isEmpty(twoLevelSubjectValue)){
                        msg.add("第"+(i+1)+"行二级分类为空");
                        continue;
                    }
                    //添加二级分类之前，判断表中是否有相同名称的二级分类
                    EduSubject existEduSubjectTwo = this.existTwoSubject(twoLevelSubjectValue,parentId);

                    if(existEduSubjectTwo == null){//不存在
                        EduSubject twoSubject = new EduSubject();
                        twoSubject.setTitle(twoLevelSubjectValue);
                        twoSubject.setParentId(parentId);

                        //6、把读取的内容添加到数据库中
                        baseMapper.insert(twoSubject);
                    }
                }
            }
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }


    //判断一级分类是否存在相同名称
    public EduSubject existOneSubject(String title){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",title);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return  eduSubject;
    }


    //判断二级分类是否存在相同名称
    public EduSubject existTwoSubject(String title,String parentId){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        wrapper.eq("parent_id",parentId);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;


    }

    //查询数据  获取分类列表
    //按照要求 返回所有一级分类
    // [
    //    {id: 1,label: '一级分类',
    //     children: [{id: 4,label: '二级分类'}]
    //    }
    // ]
    @Override
    public List<OneLevelSubjectDTO> getAllSubject() {
        //创建最终集合 封装数据
        List<OneLevelSubjectDTO> finalList = new ArrayList<>();
        
        //1、查出所有一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);



        //2、查询所有二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);



        //3、封装一级分类
        //遍历查询出来的 一级分类，进行封装
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //得到每个一级分类
            EduSubject oneSubject = oneSubjectList.get(i);
            //创建 oneSubjectDTO对象
            OneLevelSubjectDTO oneLevelSubjectDTO = new OneLevelSubjectDTO();
            //数据对拷
            BeanUtils.copyProperties(oneSubject,oneLevelSubjectDTO);
            //把赋值后的DTO对象放在finalList中
            finalList.add(oneLevelSubjectDTO);


            //因为二级分类属于积极分类，所以嵌套在for里
            //创建list集合，用于封装一级里边的所有二级分类
            List<TwoLevelSubjectDTO> twoDTOList = new ArrayList<>();

            for (int j = 0; j < twoSubjectList.size(); j++) {
                //获取每一个二级分类
                EduSubject twoSubject = twoSubjectList.get(j);
                //4、遍历封装二级分类   判断一级分类跟二级分类的关系
                if(twoSubject.getParentId().equals(oneSubject.getId())){
                    //创建dto对象
                    TwoLevelSubjectDTO twoLevelSubjectDTO = new TwoLevelSubjectDTO();
                    //数据对拷
                    BeanUtils.copyProperties(twoSubject,twoLevelSubjectDTO);
                    //添加到二级分类集合中
                    twoDTOList.add(twoLevelSubjectDTO);

                }

            }
            //上边循环操作之后，twoLevelSubjectList里边就包含里所有二级分类
            //那么该二级分类就属于此一级分类
            oneLevelSubjectDTO.setChildren(twoDTOList);


        }

        return finalList;
    }

    @Override
    public boolean deleteSubjectById(String id) {
        //查询数据库判断，当前删除的分类下面是否有子分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        //判断该分类下面有没有子分类，不需要获取子分类
        Integer count = baseMapper.selectCount(wrapper);
        System.out.println(count+"```````````````````````");
        if(count == 0){
            //证明没有子分类  可以删除
            int i = baseMapper.deleteById(id);
            return i>0;
        }else{
            //证明有子分类
            throw new MyException(20001,"不能删除");
        }


    }
    //添加一级分类
    @Override
    public boolean addOneLevelSubject(EduSubject eduSubject) {
        //判断表中是否存在一级分类
        EduSubject eduSubject1 = this.existOneSubject(eduSubject.getTitle());
        if(eduSubject1 == null){
            //不存在  可以添加
            int i = baseMapper.insert(eduSubject);
            return i>0;
        }else{
            return  false;
        }

    }

    @Override
    public boolean addTwoLevelSubject(EduSubject eduSubject) {
        //判断表中是否存在二级分类
        EduSubject eduSubject1 = this.existTwoSubject(eduSubject.getTitle(),eduSubject.getParentId());
        if(eduSubject1 == null){
            //不存在  可以添加
            int i = baseMapper.insert(eduSubject);
            return i>0;
        }else{
            throw new MyException(20001,"有重复分类，添加失败！");
        }
    }


}

