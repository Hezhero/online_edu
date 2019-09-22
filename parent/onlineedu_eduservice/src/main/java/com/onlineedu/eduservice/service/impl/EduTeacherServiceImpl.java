package com.onlineedu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onlineedu.eduservice.entity.EduTeacher;
import com.onlineedu.eduservice.mapper.EduTeacherMapper;
import com.onlineedu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlineedu.eduservice.vo.TeacherQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Hzhero
 * @since 2019-09-09
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQueryCondition(Page<EduTeacher> objectPage, TeacherQueryVo teacherQueryVo) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();


        if (teacherQueryVo == null) {
            baseMapper.selectPage(objectPage, null);
            return;
        }

        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String begin = teacherQueryVo.getBegin();
        String end = teacherQueryVo.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }

        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }

        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }

        baseMapper.selectPage(objectPage,queryWrapper);

    }
}
