package cn.enaium.cf4m.configuration;

import cn.enaium.cf4m.annotation.configuration.Configuration;

import java.lang.reflect.Field;

/**
 * @author Enaium
 */
@Configuration("cf4m.name-generator")
public class NameGeneratorConfiguration {
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

    public static void generate(Field field) {
        String name = field.getName();
        /// TODO: 2022/3/6 generate

    }
}