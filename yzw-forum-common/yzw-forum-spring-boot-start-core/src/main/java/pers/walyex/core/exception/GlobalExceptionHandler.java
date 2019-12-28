package pers.walyex.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pers.walyex.common.core.enums.ResultEnum;
import pers.walyex.common.core.exception.BusinessException;
import pers.walyex.common.core.util.ResultUtil;

import java.util.List;

/**
 * 全局异常处理
 *
 * @author Waldron Ye
 * @date 2019/6/2 13:31
 */
@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 系统繁忙异常
     *
     * @param e 异常对象
     * @return object
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Exception异常内容:", e);
        return ResultUtil.getResult(ResultEnum.ERROR_SYS);
    }

    /**
     * 系统繁忙异常
     *
     * @param e 异常对象
     * @return object
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Object handleBusinessException(BusinessException e) {
        log.error("BusinessException异常内容:", e);
        return ResultUtil.getResult(e.getCode(), e.getMessage());
    }


    /**
     * IllegalArgumentException异常处理返回json
     * 返回状态码:400
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public Object badRequestException(IllegalArgumentException e) {
        return ResultUtil.getResult(ResultEnum.PARAMETER_RESOLVE_FAIL);
    }

    /**
     * 返回状态码:405
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Object handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResultUtil.getResult(ResultEnum.NO_SUPPORT_REQUEST_METHOD);
    }

    /**
     * 返回状态码:415
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Object handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return ResultUtil.getResult(ResultEnum.NO_SUPPORT_REQUEST_MEDIA);
    }

    /**
     * 参数错误异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> objectErrorList = bindingResult.getAllErrors();
        return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), objectErrorList.get(0).getDefaultMessage());
    }

}
