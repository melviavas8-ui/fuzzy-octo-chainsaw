package com.darkmagician6.eventapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Marks an event handler method. Lower priority values run first (0 = highest, 4 = lowest, default 2). */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventTarget {
   byte value() default 2;
}
