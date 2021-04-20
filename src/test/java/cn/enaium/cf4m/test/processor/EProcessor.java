package cn.enaium.cf4m.test.processor;

import cn.enaium.cf4m.annotation.Processor;
import cn.enaium.cf4m.processor.EventProcessor;

/**
 * @author Enaium
 */
@Processor
public class EProcessor implements EventProcessor {
    @Override
    public void beforeRegister(Object instance) {
        System.out.println(instance.getClass() + " beforeRegister");
    }
}
