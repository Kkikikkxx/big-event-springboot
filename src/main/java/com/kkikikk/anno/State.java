package com.kkikikk.anno;

import com.kkikikk.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented// 指明本注解将被包含在 javadoc 中
@Target({ElementType.FIELD})// 指定本注解只能作用于字段上
@Retention(RetentionPolicy.RUNTIME)// 指定本注解在运行时可见，可用于反射
@Constraint(validatedBy = {StateValidation.class})// 指定提供校验规则的类

/*
  State 注解用于标记状态字段，确保其值仅限于指定的状态集合。
  该注解提供了一种方式来对状态字段进行有效性验证，以确保数据的正确性和一致性。
 */
public @interface State {

    /**
     * 提供校验失败时的默认提示信息。
     * @return 默认错误消息
     */
    String message() default "state参数的值只能是->已发布|草稿";

    /**
     * 允许指定校验的分组，用于更细致的校验场景。
     * @return 分组数组
     */
    Class<?>[] groups() default {};

    /**
     * 指定负载，即获取到 State 注解的附加信息的类。
     * @return 负载数组
     */
    Class<? extends Payload>[] payload() default {};
}

