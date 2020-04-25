package pers.walyex.web.annotation;

import pers.walyex.web.enums.PayEnum;

import java.lang.annotation.*;

/**
 * 支付服务注解
 *
 * @author Waldron Ye
 * @date 2020/4/25 11:18
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PayTradeTypeAnnotation {

    PayEnum[] payEnums();
}
