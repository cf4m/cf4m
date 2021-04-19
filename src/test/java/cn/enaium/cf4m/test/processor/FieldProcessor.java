package cn.enaium.cf4m.test.processor;

import cn.enaium.cf4m.annotation.Processor;
import cn.enaium.cf4m.processor.AutowiredProcessor;

import java.lang.reflect.Field;

/**
 * @author Enaium
 */
@Processor
public class FieldProcessor implements AutowiredProcessor {
    @Override
    public void beforeAutowired(Field field, Object instance) throws IllegalAccessException {
        if (field.getName().equals("auto")) {
            field.set(instance, "AUTO");
        }
    }
}
