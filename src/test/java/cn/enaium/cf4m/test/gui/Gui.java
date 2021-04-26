package cn.enaium.cf4m.test.gui;

import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.container.ModuleContainer;
import org.junit.jupiter.api.Assertions;

/**
 * @author Enaium
 */
public class Gui {

    @Autowired
    private ModuleContainer moduleContainer;

    public void render() {
        Assertions.assertNotNull(moduleContainer);
    }
}
