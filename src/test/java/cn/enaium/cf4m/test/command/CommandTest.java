package cn.enaium.cf4m.test.command;

import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;

/**
 * Project: cf4m
 * Author: Enaium
 */
@Command({"t", "test"})
public class CommandTest {
    @Exec
    public void exec(String var1) {
        System.out.println(var1);
    }
}
