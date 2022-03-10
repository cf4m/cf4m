package cn.enaium.cf4m.test.command;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.configuration.CommandConfiguration;
import cn.enaium.cf4m.container.ClassContainer;
import org.junit.jupiter.api.Assertions;

/**
 * @author Enaium
 */
@Command({"t", "test"})
public class CommandTest {

    @Autowired
    private ClassContainer classContainer;

    @Exec
    public void exec() {
        Assertions.assertNotNull(classContainer);
        CF4M.CONFIGURATION.get(CommandConfiguration.class).message("No var");
    }

    @Exec
    public void exec(String var1) {
        CF4M.CONFIGURATION.get(CommandConfiguration.class).message("var1 | " + var1);
    }

    @Exec
    public void exec(String var1, Integer var2) {
        CF4M.CONFIGURATION.get(CommandConfiguration.class).message(var1 + "|" + var2);
    }
}
