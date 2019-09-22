package com.onlineedu.eduservice.service;

import com.onlineedu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlineedu.eduservice.vo.CourseInfoFormDTO;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Hzhero
 * @since 2019-09-19
 */
public interface EduCourseService extends IService<EduCourse> {
    //1、添加课程基本信息
    String addBaseCourseInfo(CourseInfoFormDTO courseInfoFormDTO);
    //根据id获取课程信息
    CourseInfoFormDTO getCourseInfoById(String id);

    void updateCourseInfoById(CourseInfoFormDTO courseInfoFormDTO);
}
