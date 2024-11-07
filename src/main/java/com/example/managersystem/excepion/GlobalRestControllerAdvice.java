/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2019
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.example.managersystem.excepion;

import com.example.managersystem.hanbler.ReturnMessage;
import com.example.managersystem.hanbler.ReturnState;
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
