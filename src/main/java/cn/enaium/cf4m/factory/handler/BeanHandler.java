package cn.enaium.cf4m.factory.handler;

/**
 * @author Enaium
 */
public abstract class BeanHandler {

    private final ClassLoader classLoader;

    protected BeanHandler(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    public abstract void handler(Class<?> klass, Object instance);
}
