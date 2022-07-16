package com.topstack.nav_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author Administrator
 */
@Target(ElementType.TYPE)
public @interface Destination {
    /**
     * 页面在路由中的名称
     *
     * @return
     */
    String pageUrl();

    /**
     * 是否作为路由中的第一个启动项
     *
     * @return
     */
    boolean asStarter() default false;
}
