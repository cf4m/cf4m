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
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

### Gradle

```groovy
dependencies {
    compile group: 'cn.enaium', name: 'cf4m', version: '1.0.0'
}

repositories {
	maven { url 'https://enaium.gitee.io/maven/' }
}
```

### Libraries

[releases](https://github.com/Enaium/cf4m/releases)

## 使用

::: warning
当前仅有Event、Module和Setting
:::

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


