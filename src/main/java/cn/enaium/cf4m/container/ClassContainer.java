package cn.enaium.cf4m.container;

import java.util.ArrayList;

/**
 * @author Enaium
 */
public interface ClassContainer {
    ArrayList<Class<?>> getClasses();

    <T> T create(Class<?> klass);

    <T> T get(Class<?> klass);

    void accept();
}
