package com.onlineedu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onlineedu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlineedu.eduservice.vo.TeacherQueryVo;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Hzhero
 * @since 2019-09-09
 */
public interface EduTeacherService extends IService<EduTeacher> {
    public void pageQueryCondition(Page<EduTeacher> objectPage, TeacherQueryVo teacherQueryVo);
}
