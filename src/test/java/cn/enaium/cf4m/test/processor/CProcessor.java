package cn.enaium.cf4m.test.processor;

import cn.enaium.cf4m.annotation.Processor;
import cn.enaium.cf4m.processor.ClassProcessor;

/**
 * @author Enaium
 */
@Processor
public class CProcessor implements ClassProcessor {
    @Override
    public void afterCreate(Class<?> klass, Object instance) {
        System.out.println(klass.getName());
    }
}
