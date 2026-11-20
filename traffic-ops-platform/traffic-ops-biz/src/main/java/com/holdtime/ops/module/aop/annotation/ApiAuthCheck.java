/**
 * Copyright @ 2016 HoldTime Inner Mongolia Co. Ltd.
 */
package com.holdtime.ops.module.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * 项目名称：dmv-biz<br/>
 * 描　　述：ApiAuthCheck描述 api接口权限校验<br/>
 * 创  建 人：yanxl<br/>
 * 创建日期：2024年04月22日<br/>
 * 修  改 人： <br/>
 * 修改时间：<br/>
 * 修改内容：<br/>
 * 版　　本：1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD}) //指定该注解使用的用处：用在class上和用在方法体上。
public @interface ApiAuthCheck {
    String value() default "";
    
    String businessName() default "";
}
