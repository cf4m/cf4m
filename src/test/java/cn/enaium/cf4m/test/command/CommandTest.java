package cn.enaium.cf4m.test.command;

import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.provider.CommandProvider;

/**
 * @author Enaium
 */
@Command({"t", "test"})
public class CommandTest {
    @Exec
    public void exec() {
        System.out.println("No var");
    }

    @Exec
    public void exec(String var1) {
        System.out.println("No var");
    }

    @Exec
    public void exec(String var1, int var2) {
        System.out.println(var1 + "|" + var2);
    }
}
