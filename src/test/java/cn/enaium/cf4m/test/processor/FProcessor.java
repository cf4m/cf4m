package cn.enaium.cf4m.test.processor;

import cn.enaium.cf4m.annotation.Processor;
import cn.enaium.cf4m.processor.AutowiredProcessor;

import java.lang.reflect.Field;

/**
 * @author Enaium
 */
@Processor
public class FProcessor implements AutowiredProcessor {
    @Override
    public void beforePut(Field field, Object instance) {
        if (field.getName().equals("auto")) {
            try {
                field.set(instance, "AUTO");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
