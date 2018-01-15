package net.senmori.senlib.annotation;

import net.senmori.senlib.configuration.ConfigOption;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation determines whether or not a {@link ConfigOption} requires a complete restart of the<br>
 * Minecraft server in order for its' value to be changed without error.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Documented
public @interface RequiresRestart {

    /**
     * A short description of why this option requires a restart.
     */
    String description() default "";
}
