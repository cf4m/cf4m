package cn.enaium.cf4m.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this annotation to the class/field to the field of this class will be auto put
 * <p>
 * Support all classes in the container
 *
 * @author Enaium
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
}
