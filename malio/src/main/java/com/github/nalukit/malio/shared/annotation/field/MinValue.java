package com.github.nalukit.malio.shared.annotation.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>field needs to be not null</p>
 *
 * @author Frank Hossfeld, Philipp Kohl
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinValue {

  int value();
}