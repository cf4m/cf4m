package cn.enaium.cf4m.plugin;

import cn.enaium.cf4m.ICF4M;
import cn.enaium.cf4m.container.ClassContainer;

import java.util.Properties;

/**
 * @author Enaium
 */
public interface Plugin {
    /**
     * configuration
     *
     * @return configuration
     */
    Properties getConfiguration();
}
