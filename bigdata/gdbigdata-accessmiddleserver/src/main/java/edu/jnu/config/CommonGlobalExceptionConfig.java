package edu.jnu.config;

import edu.jnu.response.controller.CtrlResEnum;
import edu.jnu.exception.ConditionException;
import edu.jnu.utils.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Guo zifan
 * @date 2022年01月24日 19:55
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionConfig {

    public static Logger LOGGER = LoggerFactory.getLogger(CommonGlobalExceptionConfig.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<?> commonExceptionHandler(HttpServletRequest ignoredRequest, Exception e) {
        if (e instanceof ConditionException) {
            LOGGER.info("发生已考虑的错误:"+ CtrlResEnum.getErrMsg(((ConditionException) e).getCode()));
            int code = ((ConditionException)e).getCode();
            return new JsonResponse<String>(code, CtrlResEnum.getErrMsg(code));
        } else if(e instanceof MethodArgumentNotValidException){
            Map<String, String> errors = new HashMap<>();
            for (ObjectError error : ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors()) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
                LOGGER.info("输入值未通过校验:" + fieldName + "---" + errorMessage);
            }
            return new JsonResponse<>(CtrlResEnum.VALID_FAIL);
        } else {
            LOGGER.error("发生未考虑的错误:", e);
            return new JsonResponse<>(CtrlResEnum.EXCEPTION);
        }
    }
}
