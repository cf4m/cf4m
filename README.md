# Client Framework for Minecraft (CF4M)

[![](https://cf4m.github.io/hero.png)]("https://cf4m.github.io")

## Install

### [CF4M Maven](https://cf4m.github.io/maven)

[![Maven URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.enaium.cn%2Fcn%2Fenaium%2Fcf4m%2Fcf4m%2Fmaven-metadata.xml&style=flat-square)](https://maven.enaium.cn)

### [or Releases](https://github.com/cf4m/cf4m/releases)

[![Releases](https://img.shields.io/github/v/release/cf4m/cf4m?style=flat-square)](https://github.com/cf4m/cf4m/releases)

## Demo

A sample module

```java

@Module("Sprint")
public class Sprint {
    @Event
    private void onUpdate(UpdateEvent updateEvent) {
        System.out.println("a sample");
    }
}
```

A sample command

```java

@Command({"e", "enable"})
public class EnableCommand {
    @Exec
    private void exec() {
        System.out.println("a sample");
    }
}
```

A sample config

```java

@Config("Module")
public class ModuleConfig {
    @Load
    public void load() {
        System.out.println("Load file");
    }

    @Save
    public void save() {
        System.out.println("Save file");
    }
}
```

[More](https://cf4m.enaium.cn)