package com.app.kokonut.woody.common.security.bean;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@AuthenticationPrincipal -> 'org.springframework.security.web.bind.annotation.AuthenticationPrincipal' is deprecated
public @interface AuthorizedUser {}