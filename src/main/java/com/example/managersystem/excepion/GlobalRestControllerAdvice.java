package com.example.managersystem.excepion;

import com.example.managersystem.dto.ReturnMessage;
import com.example.managersystem.common.ReturnState;
import com.example.managersystem.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 */
@Slf4j
@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(GlobalException.class)
    public ReturnMessage<String> globalException(final GlobalException exception) {

        log.info("globalException", exception);
        final ReturnMessage<String> returnMessage = new ReturnMessage<>(ReturnState.ERROR);
        returnMessage.setErrorCode(exception.getCode());
        returnMessage.setErrorMessage(exception.getMessage());
        log.info("全局异常:{}", JsonUtil.toString(returnMessage));
        return returnMessage;
    }
}
