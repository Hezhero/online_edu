package com.onlineedu.eduservice.service;

import com.onlineedu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlineedu.eduservice.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Hzhero
 * @since 2019-09-19
 */
public interface EduChapterService extends IService<EduChapter> {
    //根据courseId查询该课程下所有的章节
    List<ChapterVo> getAllChapterByCourseId(String courseId);
}
