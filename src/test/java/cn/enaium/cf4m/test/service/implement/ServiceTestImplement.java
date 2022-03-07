package cn.enaium.cf4m.test.service.implement;

import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.test.service.ServiceTest;

/**
 * @author Enaium
 */
@Service
public class ServiceTestImplement implements ServiceTest {
    @Override
    public void accept() {
        System.out.println("accept ServiceTestImplement");
    }
}
