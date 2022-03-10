package cn.enaium.cf4m.factory;

import cn.enaium.cf4m.provider.Provider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Enaium
 */
public class ProviderFactory<T extends Provider> {
    private final Map<Object, T> providers = new HashMap<>();

    public void addProvider(Object instance, T provider) {
        getProviders().put(instance, provider);
    }

    public Map<Object, T> getProviders() {
        return providers;
    }
}
