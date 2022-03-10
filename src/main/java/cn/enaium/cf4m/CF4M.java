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

package cn.enaium.cf4m;

import cn.enaium.cf4m.container.*;
import cn.enaium.cf4m.factory.*;

import java.io.File;

/**
 * @author Enaium
 */
public final class CF4M {

    private CF4M() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Only read
     */
    public static ClassContainer CLASS;

    /**
     * Only read
     */
    public static ConfigurationContainer CONFIGURATION;

    /**
     * Only read
     */
    public static EventContainer EVENT;

    /**
     * Only read
     */
    public static ModuleContainer MODULE;

    /**
     * Only read
     */
    public static CommandContainer COMMAND;

    /**
     * Only read
     */
    public static ConfigContainer CONFIG;

    private static boolean run = false;

    /**
     * @param mainClass MainClass.
     * @param path      .minecraft/{clientName} path.
     */
    public static void run(Class<?> mainClass, String path) {
        if (run) {
            new Exception("CF4M already run").printStackTrace();
        } else {
            ClassFactory classFactory = new ClassFactory(mainClass);
            CLASS.create(ClassContainer.class, CLASS);
            new ConfigurationFactory(CF4M.CLASS, mainClass.getClassLoader());
            CLASS.create(ConfigurationContainer.class, CONFIGURATION);
            new EventFactory();
            CLASS.create(EventContainer.class, EVENT);
            new ModuleFactory();
            CLASS.create(ModuleContainer.class, MODULE);
            new CommandFactory();
            CLASS.create(CommandContainer.class, COMMAND);
            new ConfigFactory(path);
            CLASS.create(ConfigContainer.class, CONFIG);

            run = true;
            classFactory.after();
            CONFIG.load();
            Runtime.getRuntime().addShutdownHook(new Thread("CF4M Shutdown Thread") {
                @Override
                public void run() {
                    CONFIG.save();
                }
            });
        }
    }

    /**
     * @param mainClass MainClass.
     */
    public static void run(Class<?> mainClass) {
        run(mainClass, new File(mainClass.getSimpleName()).toString());
    }

    /**
     * @param instance MainClass instance.
     */
    public static void run(Object instance) {
        run(instance.getClass());
    }

    /**
     * @param instance MainClass instance.
     * @param path     .minecraft/{clientName} path.
     */
    public static void run(Object instance, String path) {
        run(instance.getClass(), path);
    }
}