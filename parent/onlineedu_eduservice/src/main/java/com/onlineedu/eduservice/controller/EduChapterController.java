package com.onlineedu.eduservice.controller;


import com.onlineedu.common.R;
import com.onlineedu.eduservice.entity.EduChapter;
import com.onlineedu.eduservice.service.EduChapterService;
import com.onlineedu.eduservice.vo.ChapterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/eduservice/educhapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public  R  addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
            return R.ok();
    }

    @ApiOperation("根据courseId查询该课程下所有的章节")
    @GetMapping("getAllChapterByCourseId/{courseId}")
    public  R  getAllChapterByCourseId(@PathVariable String courseId){
      List<ChapterVo> chapterVoList =  eduChapterService.getAllChapterByCourseId(courseId);
        return R.ok().data("chapterVoList",chapterVoList);
    }

    @ApiOperation("删除章节")
    @DeleteMapping("removeChapterById/{id}")
    public  R  removeChapterById(@PathVariable String id){
        eduChapterService.removeById(id);
        return R.ok();
    }

    @ApiOperation("修改章节")
    @PostMapping("updateChapterById/{id}")
    public  R  updateChapterById(@PathVariable String id,@RequestBody EduChapter eduChapter){
        eduChapter.setId(id);
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @ApiOperation("根据章节id查询章节信息")
    @GetMapping("getChapterById/{id}")
    public  R  getChapterById(@PathVariable String id){
        EduChapter eduChapter = eduChapterService.getById(id);
        return R.ok().data("eduChapter",eduChapter);
    }

}

