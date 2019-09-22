package com.onlineedu.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义返回结果类
 */
@Data
public class R {

    private boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<String, Object>();
//私有化构造器
    private R() {}


    //提供获取方法
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.OK);
        r.setMessage("成功");
        return r;
    }

    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
