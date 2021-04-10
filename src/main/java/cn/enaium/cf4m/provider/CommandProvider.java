package cn.enaium.cf4m.provider;

/**
 * @author Enaium
 */
public interface CommandProvider extends Provider {
    /**
     * NotNull
     *
     * @return command key
     */
    String[] getKey();
}
