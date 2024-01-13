package edu.jnu.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月13日 10:01
 */
public class NotContainSpaceValidator implements ConstraintValidator<NotContainSpace, String> {
    @Override
    public void initialize(NotContainSpace notContainSpace) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if (s != null && s.trim().contains(" ")) {
            // 获取默认提示信息
            String message = context.getDefaultConstraintMessageTemplate();

            // 禁用默认提示信息
            context.disableDefaultConstraintViolation();

            // 设置提示语
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}