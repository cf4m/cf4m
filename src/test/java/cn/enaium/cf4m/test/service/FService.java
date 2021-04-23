package cn.enaium.cf4m.test.service;

import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.service.AutowiredService;

import java.lang.reflect.Field;

/**
 * @author Enaium
 */
@Service
public class FService implements AutowiredService {
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
