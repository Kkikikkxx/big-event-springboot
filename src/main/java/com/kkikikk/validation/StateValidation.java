package com.kkikikk.validation;

import com.kkikikk.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    /**
     * 校验规则
     *
     * @param value   将来要校验的数据
     * @param context 校验上下文，用于提供校验所需的额外信息
     * @return 如果返回false则校验失败, 如果返回true则校验成功
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // 检查数据是否为null，null值不允许
        if (value == null) {
            return false;
        }

        // 判断数据是否为预定义的合法值
        if (value.equals("已发布") || value.equals("草稿")) {
            return true;
        }

        // 不符合上述条件的数据视为无效
        return false;
    }

}
