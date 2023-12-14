package edu.jnu.valid;

import edu.jnu.utils.Tools;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月13日 10:51
 */
public class IsANumValidator implements ConstraintValidator<IsANum, String> {
    @Override
    public void initialize(IsANum isANum) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {

        if (!Tools.isNumeric(s)) {
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
