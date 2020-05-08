---
sidebar: auto
---

# 指南

## 安装

### Maven

```xml
<repositories>
    <repository>
        <name>Enaium</name>
        <id>Enaium</id>
        <url>https://enaium.gitee.io/maven/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>cn.enaium</groupId>
        <artifactId>cf4m</artifactId>
        <version>1.1.0</version>
    </dependency>
</dependencies>
```

### Gradle

```groovy
dependencies {
    compile group: 'cn.enaium', name: 'cf4m', version: '1.1.0'
}

repositories {
	maven { url 'https://enaium.gitee.io/maven/' }
}
```

### Libraries

[releases](https://github.com/Enaium/cf4m/releases)

## 使用

### Instance

创建主类.

```java
public enum Example {

    instance;

    public CF4M cf4M = new CF4M(this);

}
```

### Start and Stop

在游戏启动和停止时使用`Start`和`Stop`.

`Example.instance.cf4M.start();` `Example.instance.cf4M.start();`


### Event

::: tip
CF4M内置了2个Event(KeyboardEvent,UpdateEvent)
:::

::: warning 
你必须`new KeyboardEvent(keyCode).call();` `new UpdateEvent().call();` 才能使用
:::

### Module

::: warning
必须和主类在同级包下
:::

创建`Sprint`类.
```java
@ModuleAT
public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Sprint", Keyboard.KEY_V, Category.MOVEMENT);
    }

    @EventTarget
    private void onUpdate(UpdateEvent updateEvent) {
        Minecraft.getMinecraft().thePlayer.setSprinting(true);
    }
}
```

::: tip
加`@ModuleAT`注解CF4M会自动为您添加到ModuleManager
:::

### Setting

```java
    @SettingAT
    private EnableSetting test1 = new EnableSetting(this, "test1", "test1", false);

    @SettingAT
    private IntegerSetting test2 = new IntegerSetting(this, "test1", "test1", 1, 1, 1);

    @SettingAT
    private FloatSetting test3 = new FloatSetting(this, "test1", "test1", 1.0F, 1.0F, 1.0F);

    @SettingAT
    private DoubleSetting test4 = new DoubleSetting(this, "test1", "test1", 1.0D, 1.0D, 1.0D);

    @SettingAT
    private LongSetting test5 = new LongSetting(this, "test1", "test1", 1L, 1L, 1L);

    @SettingAT
    private ModeSetting test6 = new ModeSetting(this, "test1", "test1", "Mode1", Arrays.asList("Mode1", "Mode2"));

    public Sprint() {
        super("Sprint", "Sprint", Keyboard.KEY_V, Category.MOVEMENT);
    }

    @EventTarget
    private void onUpdate(UpdateEvent updateEvent) {
        Minecraft.getMinecraft().thePlayer.setSprinting(true);
    }
```

::: tip
加`@SettingAT`注解CF4M会自动为您添加到SettingManager
:::

### Command

::: warning
你需要在游戏的`sendChatMessage`方法下使用`Example.instance.cf4M.commandManager.isCommand(message)`
:::

prefix: `

```java
@CommandAT({"h", "help"})
public class HelpCommand implements Command {

    @Override
    public void run(String[] args) {
        ChatUtils.message("Here are the list of commands:");
        for (Command c : CF4M.getInstance().commandManager.commands.values()) {
            for (String s : c.usage()) {
                ChatUtils.message("USAGE:" + CF4M.getInstance().commandManager.prefix + s);
            }
        }
    }

    @Override
    public List<String> usage() {
        return Arrays.asList("help");
    }
}
```

```java
@CommandAT({"e", "enable"})
public class EnableCommand implements Command {
    @Override
    public void run(String[] args) {
        if (args.length == 2) {

            Module module = getModule(args[1]);

            if (module == null) {
                ChatUtils.message("The module with the name " + args[1] + " does not exist.");
                return;
            }

            module.enable();

        }
    }

    public Module getModule(String name) {
        for (Module m : CF4M.getInstance().moduleManager.modules) {
            if (m.getName().equalsIgnoreCase(name))
                return m;
        }
        return null;
    }

    @Override
    public List<String> usage() {
        return Arrays.asList("enable [module]");
    }
}
```

::: tip
加`@Command({"index"})`注解CF4M会自动为您添加到CommandManager
:::
