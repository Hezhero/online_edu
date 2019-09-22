package com.onlineedu.eduservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
@ApiModel(value ="Teacher查询条件对象",description="讲师查询对象封装")
@Data
public class TeacherQueryVo {
    @ApiModelProperty("教师名称，模糊查询")
    private String name;
    @ApiModelProperty("头衔 1高级讲师 2首席讲师")
    private Integer level;
    @ApiModelProperty("查询开始时间")
    private String begin;
    @ApiModelProperty("查询结束时间")
    private String end;
}
