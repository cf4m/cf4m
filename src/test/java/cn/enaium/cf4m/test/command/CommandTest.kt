package cn.enaium.cf4m.test.command

import cn.enaium.cf4m.annotation.command.Command
import cn.enaium.cf4m.annotation.command.Exec

/**
 * @author Enaium
 */
@Command("t", "test")
class CommandTest {
    @Exec
    fun exec() {
        println("No var")
    }

    @Exec
    fun exec(var1: String?) {
        println(var1)
    }

    @Exec
    fun exec(var1: String, var2: Int) {
        println("$var1|$var2")
    }
}