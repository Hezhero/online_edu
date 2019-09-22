package com.onlineedu.eduservice.service.impl;

import com.onlineedu.eduservice.entity.EduCourse;
import com.onlineedu.eduservice.entity.EduCourseDescription;
import com.onlineedu.eduservice.entity.EduSubject;
import com.onlineedu.eduservice.exceptions.MyException;
import com.onlineedu.eduservice.mapper.EduCourseMapper;
import com.onlineedu.eduservice.service.EduCourseDescriptionService;
import com.onlineedu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlineedu.eduservice.vo.CourseInfoFormDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Hzhero
 * @since 2019-09-19
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;



    //1、添加课程基本信息
    @Override
    public String addBaseCourseInfo(CourseInfoFormDTO courseInfoFormDTO) {
        //1、把课程基本信息添加到课程表中
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoFormDTO,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        //判断是否添加成功
            if(insert == 0){
                //添加失败直接抛异常 以下不执行
                throw new MyException(20001,"添加失败");
            }


        //获取课程id
        String courseId = eduCourse.getId();

        //2、把课程简介添加到课程描述表中

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoFormDTO.getDescription());
        //表示一对一关系  课程和课程描述
        eduCourseDescription.setId(courseId);
        eduCourseDescriptionService.save(eduCourseDescription);

        return courseId;
    }


    //根据id获取课程信息
    @Override
    public CourseInfoFormDTO getCourseInfoById(String id) {
        //创建一个DTO对象封装所有数据
        CourseInfoFormDTO courseInfoFormDTO = new CourseInfoFormDTO();

        // 获取课程信息
        EduCourse eduCourse = baseMapper.selectById(id);
        BeanUtils.copyProperties(eduCourse,courseInfoFormDTO);

        //获取描述信息
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        courseInfoFormDTO.setDescription(eduCourseDescription.getDescription());

        return courseInfoFormDTO;
    }

    @Override
    public void updateCourseInfoById(CourseInfoFormDTO courseInfoFormDTO) {
            //1、修改课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoFormDTO,eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if(result == 0){
            throw new MyException(20001,"修改课程失败");
        }

        //2、修改描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoFormDTO.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);

    }
}
