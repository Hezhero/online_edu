package com.onlineedu.eduservice.exceptions;


import com.onlineedu.common.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyException extends RuntimeException {
        private Integer code;
        private String msg;



}
