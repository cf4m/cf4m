package cn.enaium.cf4m.container;

import java.util.ArrayList;

/**
 * @author Enaium
 */
public interface ClassContainer {
    /**
     * get all the classes in the container
     *
     * @return class list
     */
    ArrayList<Class<?>> getAll();

    <T> T recreate(Class<T> klass, Object instance);

    /**
     * put the classes in the container
     *
     * @param klass    klass
     * @param instance object
     * @param <T>      class
     * @return class instance
     */
    <T> T create(Class<T> klass, Object instance);

    /**
     * put the classes in the container
     *
     * @param klass class
     * @param <T>   class
     * @return class instance
     */
    <T> T create(Class<T> klass);

    /**
     * get service
     *
     * @param type Service
     * @param <T>  Service
     * @return Service list
     */
    <T> ArrayList<T> getService(Class<T> type);
}
