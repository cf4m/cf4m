package cn.enaium.cf4m.test.service;

import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.service.ClassService;

import java.util.List;

/**
 * @author Enaium
 */
@Service
public class CService implements ClassService {
    @Override
    public void afterCreate(Class<?> klass, Object instance) {
        System.out.println(klass.getName());
    }
}
