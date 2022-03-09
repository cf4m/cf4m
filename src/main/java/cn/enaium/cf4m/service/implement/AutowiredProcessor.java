package cn.enaium.cf4m.service.implement;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.service.AutowiredService;
import cn.enaium.cf4m.service.ClassService;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Enaium
 * @since 1.11.0
 */
@Service
public class AutowiredProcessor implements ClassService {
    @Override
    public void afterCreate(Class<?> klass, Object instance) {
        autowired(klass, instance);
    }

    @Override
    public void afterProcessor() {
        CF4M.CLASS.getBeans().forEach(this::autowired);
    }

    private void autowired(Class<?> klass, Object instance) {
        if (instance == null) {
            return;
        }

        for (Field field : klass.getDeclaredFields()) {
            field.setAccessible(true);
            if (!klass.isAnnotationPresent(Autowired.class) && !field.isAnnotationPresent(Autowired.class)) {
                continue;
            }

            try {
                CF4M.CLASS.getService(AutowiredService.class).forEach(postProcessor -> postProcessor.beforePut(field, instance));
                if (field.get(instance) == null) {
                    Object o = CF4M.CLASS.getBeans().get(field.getType());
                    if (o == null && !CF4M.CLASS.getService(field.getType()).isEmpty()) {
                        o = CF4M.CLASS.getService(field.getType()).get(0);
                    }

                    field.set(instance, o);
                }
                CF4M.CLASS.getService(AutowiredService.class).forEach(autowiredService -> autowiredService.afterPut(field, instance));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
