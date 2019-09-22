package com.onlineedu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onlineedu.eduservice.entity.EduChapter;
import com.onlineedu.eduservice.entity.EduVideo;
import com.onlineedu.eduservice.mapper.EduChapterMapper;
import com.onlineedu.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlineedu.eduservice.service.EduVideoService;
import com.onlineedu.eduservice.vo.ChapterVo;
import com.onlineedu.eduservice.vo.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Hzhero
 * @since 2019-09-19
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    //根据courseId查询该课程下所有的章节
    @Override
    public List<ChapterVo> getAllChapterByCourseId(String courseId) {
        //1、获取所有章节信息

        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(queryWrapper);

        //2、根据课程id查询课程里面所有小节
        QueryWrapper<EduVideo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id",courseId);

        List<EduVideo> eduVideoList = eduVideoService.list(queryWrapper1);

        //5、创建最终集合封装最终数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3、遍历查询出来所有章节，进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);

            //创建videolist存放1对多关系的video
            List<VideoVo> list = new ArrayList<>();

            //4、遍历查询出来的所有小节，进行封装
            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo eduVideo = eduVideoList.get(j);
                //判断 如果小节的章节id跟该章节id相同  证明是1对多关系
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                   BeanUtils.copyProperties(eduVideo,videoVo);
                    list.add(videoVo);
                }
            }
            chapterVo.setChildren(list);
            finalList.add(chapterVo);
        }


        return finalList;
    }
}
