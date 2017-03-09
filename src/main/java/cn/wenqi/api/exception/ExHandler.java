package cn.wenqi.api.exception;

import cn.wenqi.api.authentication.AuthenticationException;
import cn.wenqi.api.model.ApiResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wenqi
 */
@ControllerAdvice
@Controller
public class ExHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ApiResult authFail(AuthenticationException ex){
        return new ApiResult(502,ex.getMessage());
    }
}
