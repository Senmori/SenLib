package net.senmori.senlib.annotation;

import net.senmori.senlib.configuration.ConfigOption;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation determines whether or not a {@link ConfigOption} requires a complete reload of the<br>
 * owning plugin in order for it's value to be changed without errors.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.TYPE})
@Documented
public @interface RequiresReload {

    /**
     * This is a short description of why this option requires a reload.
     */
    String description() default "";
}
