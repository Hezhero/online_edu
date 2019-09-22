package com.onlineedu.eduservice.controller;


import com.onlineedu.common.R;
import com.onlineedu.eduservice.entity.EduSubject;
import com.onlineedu.eduservice.service.EduSubjectService;
import com.onlineedu.eduservice.vo.OneLevelSubjectDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Hzhero
 * @since 2019-09-17
 */
@Api(description = "课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/edusubject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("addSubject")
    public R addSubject(@RequestBody  MultipartFile file){
        List<String> msg = eduSubjectService.importData(file);
        return msg.size() == 0?R.ok().message("导入成功"):R.error().message("导入失败").data("messageList",msg);
    }


    //查询所有分类
    @GetMapping("getAllSubject")
    public R getAllSubject(){
       List<OneLevelSubjectDTO> list = eduSubjectService.getAllSubject();

        return R.ok().data("oneLevelSubjectList",list);
    }


    //删除分类
    @DeleteMapping("deleteSubjectById/{id}")
    public R deleteSubjectById(@PathVariable String id){
        boolean b = eduSubjectService.deleteSubjectById(id);
            return b?R.ok().message("删除分类成功~"):R.error().message("删除分类失败~");
    }

    //添加一级分类
    @PostMapping("addOneLevelSubject")
    public R addOneLevelSubject(@RequestBody EduSubject eduSubject){
        eduSubject.setParentId("0");
       boolean result = eduSubjectService.addOneLevelSubject(eduSubject);

        return result?R.ok().message("添加成功"):R.error().message("添加失败");
    }

    //添加二级分类
    @PostMapping("addTwoLevelSubject")
    public R addTwoLevelSubject(@RequestBody EduSubject eduSubject){

        boolean result = eduSubjectService.addTwoLevelSubject(eduSubject);

        return result?R.ok().message("添加成功"):R.error().message("添加失败");
    }

}

