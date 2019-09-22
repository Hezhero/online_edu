package com.onlineedu.eduservice.controller;


import com.onlineedu.common.R;
import com.onlineedu.eduservice.entity.EduCourse;
import com.onlineedu.eduservice.service.EduCourseService;
import com.onlineedu.eduservice.vo.CourseInfoFormDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Hzhero
 * @since 2019-09-19
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/educourse")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;


    @ApiOperation("添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoFormDTO courseInfoFormDTO){
       String courseId = eduCourseService.addBaseCourseInfo(courseInfoFormDTO);
        //因为前端需要课程id，所以返回值设为id
        return R.ok().data("courseId",courseId);
    }

    @ApiOperation("根据id获取课程信息")
    @GetMapping("getCourseInfoById/{id}")
    public R getCourseInfoById(@PathVariable String id){
        CourseInfoFormDTO courseInfoFormDTO = eduCourseService.getCourseInfoById(id);

        return  R.ok().data("courseInfoFormDTO",courseInfoFormDTO);
    }

    @ApiOperation("更新课程信息")
    @PostMapping("updateById")
    public R updateById(@RequestBody CourseInfoFormDTO courseInfoFormDTO){
        eduCourseService.updateCourseInfoById(courseInfoFormDTO);

        return  R.ok();
    }

}

