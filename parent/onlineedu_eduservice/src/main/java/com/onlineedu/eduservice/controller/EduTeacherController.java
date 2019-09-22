package com.onlineedu.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onlineedu.common.R;
import com.onlineedu.eduservice.entity.EduTeacher;
import com.onlineedu.eduservice.exceptions.MyException;
import com.onlineedu.eduservice.service.EduTeacherService;
import com.onlineedu.eduservice.vo.TeacherQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Hzhero
 * @since 2019-09-09
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    //讲师列表查询
    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("login")
    public R login(){
        return  R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @ApiOperation("获取所有讲师信息")
    @GetMapping("getAll")
    public R getAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        list.forEach(System.out::println);
        return R.ok().data("list", list);
    }


    @ApiOperation("根据id删除讲师")
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id) {
        boolean b = eduTeacherService.removeById(id);
        System.out.println(b);
        return b ? R.ok() : R.error();
    }

    @ApiOperation("分页讲师列表")
    @GetMapping("PageList/{page}/{limit}")
    public R PageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<EduTeacher> objectPage = new Page<>(page, limit);

        eduTeacherService.page(objectPage, null);
        List<EduTeacher> records = objectPage.getRecords();
        long total = objectPage.getTotal();

        return R.ok().data("total", total).data("records", records);
    }

    @ApiOperation("带条件分页查询讲师")
    @PostMapping("PageListCondition/{page}/{limit}")
    public R PageListCondition(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {

        Page<EduTeacher> objectPage = new Page<>(page, limit);
        eduTeacherService.pageQueryCondition(objectPage, teacherQueryVo);
        long total = objectPage.getTotal();
        List<EduTeacher> records = objectPage.getRecords();


        return R.ok().data("total", total).data("records", records);


    }
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public R  addTeacher(@RequestBody(required = false) EduTeacher teacher){
        boolean save = eduTeacherService.save(teacher);
        return save?R.ok():R.error();
    }

    @ApiOperation("根据id获取讲师信息")
    @GetMapping("getTeacherById/{id}")
    public R  getTeacherById(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    @ApiOperation("修改讲师信息")
    @PostMapping("editTeacher")
    public R  editTeacher(@RequestBody(required = false) EduTeacher teacher){
        boolean result = eduTeacherService.updateById(teacher);
        return result?R.ok():R.error();
    }



}

