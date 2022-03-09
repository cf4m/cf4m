package cn.enaium.cf4m.annotation;

import cn.enaium.cf4m.service.implement.ValueReadProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * read value from cf4m.configuration.properties file
 * <p>
 * Processor:{@link ValueReadProcessor}
 *
 * @author Enaium
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    String value();
}
