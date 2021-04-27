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

    @Autowired
    private IConfiguration configuration;

    @Exec
    public void exec() {
        configuration.getCommand().message("No var");
    }

    @Exec
    public void exec(String var1) {
        configuration.getCommand().message(var1);
    }

    @Exec
    public void exec(String var1, int var2) {
        configuration.getCommand().message(var1 + "|" + var2);
    }
}
