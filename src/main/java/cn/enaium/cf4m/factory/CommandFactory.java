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

package cn.enaium.cf4m.factory;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.annotation.command.Param;
import cn.enaium.cf4m.configuration.CommandConfiguration;
import cn.enaium.cf4m.container.CommandContainer;
import cn.enaium.cf4m.service.CommandService;
import cn.enaium.cf4m.provider.CommandProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;


/**
 * @author Enaium
 */
@SuppressWarnings("unchecked")
public final class CommandFactory {

    private final HashMap<Object, CommandProvider> commands;

    public final CommandContainer commandContainer;


    public CommandFactory() {
        commands = new HashMap<>();

        commandContainer = new CommandContainer() {
            @Override
            public ArrayList<CommandProvider> getAll() {
                return new ArrayList<>(commands.values());
            }

            @Override
            public CommandProvider getByInstance(Object instance) {
                return commands.get(instance);
            }

            @Override
            public CommandProvider get(Object instance) {
                return commands.get(instance);
            }

            @Override
            public <T> CommandProvider getByClass(Class<T> klass) {
                return getByInstance(CF4M.CLASS.create(klass));
            }

            @Override
            public <T> CommandProvider get(Class<T> klass) {
                return getByInstance(CF4M.CLASS.create(klass));
            }

            @Override
            public CommandProvider getByKey(String key) {
                for (CommandProvider commandProvider : getAll()) {
                    for (String s : commandProvider.getKey()) {
                        if (s.equalsIgnoreCase(key)) {
                            return commandProvider;
                        }
                    }
                }
                return null;
            }

            @Override
            public CommandProvider get(String key) {
                for (CommandProvider commandProvider : getAll()) {
                    for (String s : commandProvider.getKey()) {
                        if (s.equalsIgnoreCase(key)) {
                            return commandProvider;
                        }
                    }
                }
                return null;
            }

            @Override
            public boolean execCommand(String rawMessage) {
                if (!rawMessage.startsWith(CF4M.CONFIGURATION.get(CommandConfiguration.class).getPrefix())) {
                    return false;
                }

                boolean safe = rawMessage.split(CF4M.CONFIGURATION.get(CommandConfiguration.class).getPrefix()).length > 1;

                if (safe) {
                    String beheaded = rawMessage.split(CF4M.CONFIGURATION.get(CommandConfiguration.class).getPrefix())[1];
                    List<String> args = new ArrayList<>();
                    Collections.addAll(args, beheaded.split(" "));
                    String key = args.get(0);
                    args.remove(key);

                    Object command = getCommand(key);

                    if (command != null) {
                        if (!CommandFactory.this.execCommand(command, args)) {
                            for (List<String> parameters : this.getByInstance(command).getParam()) {
                                CF4M.CONFIGURATION.get(CommandConfiguration.class).message(key + " " + parameters);
                            }
                        }
                    } else {
                        help();
                    }
                } else {
                    help();
                }
                return true;
            }
        };

        for (Class<?> klass : CF4M.CLASS.getAll()) {
            if (klass.isAnnotationPresent(Command.class)) {
                final Object commandInstance = CF4M.CLASS.create(klass);
                commands.put(commandInstance, new CommandProvider() {
                    @Override
                    public String getName() {
                        return "";
                    }

                    @Override
                    public String getDescription() {
                        return klass.getAnnotation(Command.class).description();
                    }

                    @Override
                    public Object getInstance() {
                        return commandInstance;
                    }

                    @Override
                    public <T> T as() {
                        return (T) commandInstance;
                    }

                    @Override
                    public String[] getKey() {
                        return klass.getAnnotation(Command.class).value();
                    }

                    @Override
                    public List<List<String>> getParam() {
                        List<List<String>> param = new ArrayList<>();
                        for (Method method : getInstance().getClass().getDeclaredMethods()) {
                            if (method.isAnnotationPresent(Exec.class)) {
                                List<String> parameters = new ArrayList<>();
                                for (Parameter parameter : method.getParameters()) {
                                    parameters.add("<" + (parameter.isAnnotationPresent(Param.class) ? parameter.getAnnotation(Param.class).value() : "NULL") + "|" + parameter.getType().getSimpleName() + ">");
                                }
                                param.add(parameters);
                            }
                        }
                        return param;
                    }
                });
            }
        }
    }

    private boolean execCommand(Object command, List<String> args) {
        for (Method method : command.getClass().getDeclaredMethods()) {
            method.setAccessible(true);

            if (method.getParameterTypes().length == args.size() && method.isAnnotationPresent(Exec.class)) {
                Exec annotation = method.getAnnotation(Exec.class);
                List<Object> params = new ArrayList<>();
                for (int i = 0; i < method.getParameterTypes().length; i++) {
                    String arg = args.get(i);
                    Class<?> paramType = method.getParameterTypes()[i];
                    try {
                        if (paramType.equals(String.class)) {
                            params.add(arg);
                        } else {
                            params.add(paramType.getMethod("valueOf", String.class).invoke(null, arg));
                        }
                    } catch (Exception e) {
                        CF4M.CONFIGURATION.get(CommandConfiguration.class).message(e.getMessage());
                        e.printStackTrace();
                    }
                }

                List<CommandService> processors = CF4M.CLASS.getService(CommandService.class);
                processors.forEach(commandService -> commandService.beforeExec(commands.get(command)));
                Runnable runnable = () -> {
                    try {
                        if (params.isEmpty()) {
                            method.invoke(command);
                        } else {
                            method.invoke(command, params.toArray());
                        }

                    } catch (IllegalAccessException | InvocationTargetException e) {
                        CF4M.CONFIGURATION.get(CommandConfiguration.class).message(e.getMessage());
                        e.printStackTrace();
                    }
                };

                if (annotation.await()) {
                    runnable.run();
                } else {
                    new Thread(runnable).start();
                }
                processors.forEach(commandService -> commandService.afterExec(commands.get(command)));
                return true;
            }
        }
        return false;
    }

    private void help() {
        for (CommandProvider commandProvider : commandContainer.getAll()) {
            CF4M.CONFIGURATION.get(CommandConfiguration.class).message(CF4M.CONFIGURATION.get(CommandConfiguration.class).getPrefix() + Arrays.toString(commandProvider.getKey()) + commandProvider.getDescription());
        }
    }

    private Object getCommand(String key) {
        for (Map.Entry<Object, CommandProvider> entry : commands.entrySet()) {
            for (String s : entry.getValue().getKey()) {
                if (s.equalsIgnoreCase(key)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }
}
