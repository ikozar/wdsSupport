package ru.ki.service.model;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author ikozar
 * date    27.06.13
 */
@Target({FIELD, METHOD, TYPE})
@Retention(RUNTIME)
public @interface Comment {
    String value();
}
