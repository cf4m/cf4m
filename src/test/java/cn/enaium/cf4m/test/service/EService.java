package cn.enaium.cf4m.test.service;

import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.service.EventService;

/**
 * @author Enaium
 */
@Service
public class EService implements EventService {
    @Override
    public void beforeRegister(Object instance) {
        System.out.println(instance.getClass() + " beforeRegister");
    }
}
