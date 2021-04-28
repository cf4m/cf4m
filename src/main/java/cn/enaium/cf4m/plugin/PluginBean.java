package cn.enaium.cf4m.plugin;

public final class PluginBean<T> {
    private final T instance;
    private final String name;
    private final String description;
    private final String version;
    private final String author;

    public PluginBean(T instance, String name, String description, String version, String author) {
        this.instance = instance;
        this.name = name;
        this.description = description;
        this.version = version;
        this.author = author;
    }

    public T getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }
}