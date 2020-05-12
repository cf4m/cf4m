---
sidebar: auto
---

# Guide

## Install

### Maven

```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
<dependency>
	<groupId>com.github.Enaium</groupId>
	<artifactId>cf4m</artifactId>
	<version>1.2.0</version>
</dependency>
```

### Gradle

```groovy
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
dependencies {
	implementation 'com.github.Enaium:cf4m:1.2.0'
}    
```

### Libraries

[releases](https://github.com/Enaium/cf4m/releases)

## Usage

### Instance

Create the main class.

```java
public enum Example {

    instance;

    public CF4M cf4M = new CF4M(this);

}
```

### Start and Stop

Use `Start` and `Stop` in game Start and Stop.

`Example.instance.cf4M.start();` `Example.instance.cf4M.start();`


### Event

::: tip
2 events are built into CF4M (KeyboardEvent,UpdateEvent)
:::

::: warning 
You must `new KeyboardEvent(keyCode).call();` `new UpdateEvent().call();` can be used
:::

### Module

::: warning
Must be under the sibling package as the main class
:::

Create the `Sprint` class.

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
Plus `@ModuleAT` annotation CF4M will automatically add it to ModuleManager for you
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
Plus `@SettingAT` annotation CF4M will automatically add it to SettingManager for you
:::

### Command

::: warning
You need to use `Example.instance.cf4M.commandManager.isCommand(message)` in the `sendChatMessage` method of the game
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
Plus `@Command({"index"})` annotation CF4M will automatically add it to CommandManager for you
:::

### Config

```java
@ConfigAT
public class ModuleConfig extends Config {
    public ModuleConfig() {
        super("Modules");
    }

    @Override
    public void load() {
        for (Module module : CF4M.getInstance().moduleManager.modules) {
            JsonArray jsonArray = new JsonArray();
            try {
                jsonArray = new Gson().fromJson(read(getPath()), JsonArray.class);
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (module.getName().equals(new Gson().fromJson(jsonObject, JsonObject.class).get("name").getAsString())) {
                    if (jsonObject.get("enable").getAsBoolean())
                        module.enable();
                    module.setKeyCode(jsonObject.get("keyCode").getAsInt());
                }
            }
        }
        super.load();
    }

    @Override
    public void save() {
        JsonArray jsonArray = new JsonArray();
        for (Module module : CF4M.getInstance().moduleManager.modules) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", module.getName());
            jsonObject.addProperty("enable", module.isEnable());
            jsonObject.addProperty("keyCode", module.getKeyCode());
            jsonArray.add(jsonObject);
        }
        try {
            write(getPath(), new Gson().toJson(jsonArray));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        super.save();
    }

    private String read(String path) throws IOException {
        return FileUtils.readFileToString(new File(path));
    }

    private void write(String path, String string) throws IOException {
        FileUtils.writeStringToFile(new File(path), string, "UTF-8");
    }
}
```

::: tip
Plus `@ConfigAT` annotation CF4M will automatically add it to ConfigManager for you
:::