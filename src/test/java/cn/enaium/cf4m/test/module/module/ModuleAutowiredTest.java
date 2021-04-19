package cn.enaium.cf4m.test.module.module;

import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.*;
import org.junit.jupiter.api.Assertions;

/**
 * @author Enaium
 */
@Autowired
@Module(value = "ModuleAutowiredTest", key = 6)
public class ModuleAutowiredTest {
    private ClassContainer classContainer;
    private IConfiguration configuration;
    private EventContainer eventContainer;
    private ModuleContainer moduleContainer;
    private CommandContainer commandContainer;
    private ConfigContainer configContainer;
    private String auto;


    @Enable
    public void enable() {
        Assertions.assertNotNull(classContainer);
        Assertions.assertNotNull(configuration);
        Assertions.assertNotNull(eventContainer);
        Assertions.assertNotNull(moduleContainer);
        Assertions.assertNotNull(commandContainer);
        Assertions.assertNotNull(configContainer);
        Assertions.assertNotNull(auto);
    }
}
