package cn.enaium.cf4m.test.component;

import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.Component;
import cn.enaium.cf4m.annotation.Value;
import cn.enaium.cf4m.container.ClassContainer;
import org.junit.jupiter.api.Assertions;

/**
 * @author Enaium
 */
@Component
public class ValueTest {
    @Value("custom")
    private String c;

    @Autowired
    private ClassContainer classContainer;

    public void custom() {
        Assertions.assertNotNull(c);
        Assertions.assertNotNull(classContainer);
        System.out.println(c);
    }
}
