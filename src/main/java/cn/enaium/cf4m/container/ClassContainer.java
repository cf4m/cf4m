package cn.enaium.cf4m.container;

import java.util.ArrayList;

/**
 * @author Enaium
 */
public interface ClassContainer {
    /**
     * @return class list
     */
    ArrayList<Class<?>> getAll();

    /**
     * @param klass class
     * @param <T>   class
     * @return class instance
     */
    <T> T create(Class<T> klass);

    <T> ArrayList<T> getService(Class<T> type);

    void after();
}
