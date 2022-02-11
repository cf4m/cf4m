package cn.enaium.cf4m.test.module.module;

import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.test.component.UtilBean;
import cn.enaium.cf4m.test.component.UtilTest;
import cn.enaium.cf4m.test.configuration.ConfigurationTest;
import cn.enaium.cf4m.test.configuration.PropertiesTest;
import cn.enaium.cf4m.test.gui.Gui;
import org.junit.jupiter.api.Assertions;

/**
 * @author Enaium
 */
@Module(value = "ModuleAutowiredTest", key = 6)
public class ModuleAutowiredTest {

    @Autowired
    private ClassContainer classContainer;
    @Autowired
    private ConfigurationContainer configuration;
    @Autowired
    private EventContainer eventContainer;
    @Autowired
    private ModuleContainer moduleContainer;
    @Autowired
    private CommandContainer commandContainer;
    @Autowired
    private ConfigContainer configContainer;
    @Autowired
    private UtilTest utilTest;
    @Autowired
    private UtilBean utilBean;
    @Autowired
    private ConfigurationTest configurationTest;
    @Autowired
    private PropertiesTest propertiesTest;

    @Enable
    public void enable() {
        Assertions.assertNotNull(classContainer);
        Assertions.assertNotNull(configuration);
        Assertions.assertNotNull(eventContainer);
        Assertions.assertNotNull(moduleContainer);
        Assertions.assertNotNull(commandContainer);
        Assertions.assertNotNull(configContainer);
        Assertions.assertNotNull(utilTest);
        Assertions.assertNotNull(utilBean);
        Assertions.assertNotNull(configurationTest);
        System.out.println(configurationTest.getValue());
        utilTest.util();
        utilBean.utilBean();
        classContainer.create(Gui.class).render();
        System.out.println(propertiesTest.name);
    }
}
