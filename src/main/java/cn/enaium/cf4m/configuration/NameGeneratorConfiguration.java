package cn.enaium.cf4m.configuration;

import cn.enaium.cf4m.annotation.configuration.Configuration;

import java.lang.reflect.Field;

/**
 * @author Enaium
 * @since 1.11.0
 */
@Configuration("cf4m.name-generator")
public class NameGeneratorConfiguration {

    /**
     * Example:
     * <p>
     * ExampleModule -> Example
     * <p>
     * exampleModule -> example
     * <p>
     * example-> example
     * <p>
     *
     * @param klass class
     * @return name
     * @since 1.11.0
     */
    public String generate(Class<?> klass) {
        String simpleName = klass.getSimpleName();
        int l = -1, r = -1;
        char[] chars = simpleName.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                if (l == -1) {
                    l = i;
                } else {
                    if (r == -1) {
                        r = i;
                    }
                }
            }
        }

        if (l == -1) {
            l = 0;
        }

        if (r == -1) {
            r = simpleName.length();
        }

        return simpleName.substring(l, r);
    }

    /**
     * <p>
     * Example:
     * <p>
     * ExampleSetting -> Example
     * <p>
     * exampleSetting -> example
     * <p>
     * example-> Example
     * <p>
     *
     * @param field field
     * @return name
     * @since 1.11.0
     */
    public String generate(Field field) {
        String name = field.getName();

        String initials = String.valueOf(name.charAt(0));

        char[] chars = name.toCharArray();
        int end = -1;
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                if (end == -1) {
                    end = i;
                }
            }
        }

        if (end == -1) {
            end = name.length();
        }

        if (!Character.isUpperCase(name.charAt(0))) {
            return initials.toUpperCase() + name.substring(1, end);
        } else {
            return name.substring(0, end);
        }
    }
}