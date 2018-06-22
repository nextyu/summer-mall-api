package com.nextyu.mall.web.handler;

import com.nextyu.mall.common.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created on 2017-05-31 17:25
 *
 * @author nextyu
 */
@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {


    @ExceptionHandler(value = Exception.class)
    public Object errorHandlerOverJson(HttpServletRequest request, HttpServletResponse response,
                                       Exception e) {
        log.error(request.getRequestURL().toString(), e);
        return ServiceResponse.buildError(e.getMessage());
    }
}
