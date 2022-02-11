package cn.enaium.cf4m.test.configuration;

import cn.enaium.cf4m.annotation.configuration.Key;
import cn.enaium.cf4m.annotation.configuration.Properties;

@Properties("test.properties")
public class PropertiesTest {
    @Key("test.name")
    public String name;
}
