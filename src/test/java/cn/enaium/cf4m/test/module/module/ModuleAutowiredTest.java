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
@Module(value = "ModuleAutowiredTest", key = 6)
public class ModuleAutowiredTest {

    private ClassContainer classContainer;
    @Autowired
    private IConfiguration configuration;
    @Autowired
    private EventContainer eventContainer;
    @Autowired
    private ModuleContainer moduleContainer;
    @Autowired
    private CommandContainer commandContainer;
    @Autowired
    private ConfigContainer configContainer;
    @Autowired
    private String auto;

    @Autowired
    public void setClassContainer(ClassContainer classContainer) {
        this.classContainer = classContainer;
    }

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
