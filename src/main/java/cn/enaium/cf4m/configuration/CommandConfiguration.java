/**
 * Copyright (C) 2020 Enaium
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.cf4m.configuration;

import cn.enaium.cf4m.annotation.configuration.Configuration;
import cn.enaium.cf4m.annotation.configuration.Key;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Enaium
 */
@Configuration("cf4m.command")
public class CommandConfiguration {
    @Key
    private String prefix = "`";

    @Key
    private String message = "cn.enaium.cf4m.configuration.CommandConfiguration:send";

    public String getPrefix() {
        return prefix;
    }

    public String getMessage() {
        return message;
    }

    public void message(String message) {
        String klass = getMessage().split(":")[0];
        String method = getMessage().split(":")[1];
        try {
            Method send = this.getClass().getClassLoader().loadClass(klass).getDeclaredMethod(method, String.class);
            send.setAccessible(true);
            send.invoke(null, message);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void send(String message) {
        System.err.println(message);
    }
}
