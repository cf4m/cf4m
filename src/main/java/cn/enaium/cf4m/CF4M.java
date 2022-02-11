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
            final ClassContainer classContainer = classFactory.classContainer;
            CLASS = classContainer;
            classContainer.create(ClassContainer.class, classContainer);

            final ConfigurationContainer configuration = classFactory.configuration;
            CONFIGURATION = configuration;
            classContainer.create(ConfigurationContainer.class, configuration);

            final EventContainer eventContainer = new EventFactory().eventContainer;
            EVENT = eventContainer;
            classContainer.create(EventContainer.class, eventContainer);

            final ModuleContainer moduleContainer = new ModuleFactory().moduleContainer;
            MODULE = moduleContainer;
            classContainer.create(ModuleContainer.class, moduleContainer);

            final CommandContainer commandContainer = new CommandFactory().commandContainer;
            COMMAND = commandContainer;
            classContainer.create(CommandContainer.class, commandContainer);

            final ConfigContainer configContainer = new ConfigFactory(path).configContainer;
            CONFIG = configContainer;
            classContainer.create(ConfigContainer.class, configContainer);

            run = true;
            classFactory.after();
            configContainer.load();
            Runtime.getRuntime().addShutdownHook(new Thread("CF4M Shutdown Thread") {
                @Override
                public void run() {
                    configContainer.save();
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