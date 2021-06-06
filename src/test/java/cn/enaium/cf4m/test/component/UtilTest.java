package cn.enaium.cf4m.test.component;

import cn.enaium.cf4m.annotation.Bean;
import cn.enaium.cf4m.annotation.Component;

/**
 * @author Enaium
 */
@Component
public class UtilTest {
    public void util() {
        System.out.println("Util test");
    }

    @Bean
    public UtilBean utilBean() {
        return new UtilBean();
    }
}
