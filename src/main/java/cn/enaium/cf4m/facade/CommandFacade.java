package cn.enaium.cf4m.facade;

import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.annotation.command.Param;
import cn.enaium.cf4m.configuration.CommandConfiguration;
import cn.enaium.cf4m.container.ClassContainer;
import cn.enaium.cf4m.container.CommandContainer;
import cn.enaium.cf4m.container.ConfigurationContainer;
import cn.enaium.cf4m.service.CommandService;
import cn.enaium.cf4m.provider.CommandProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;


/**
 * @author Enaium
 */
public final class CommandFacade {

    private final HashMap<Object, CommandProvider> commands;

    public final CommandContainer commandContainer;

    private final ConfigurationContainer configuration;

    private final ClassContainer classContainer;

    public CommandFacade(ClassContainer classContainer, ConfigurationContainer configuration) {
        this.classContainer = classContainer;
        this.configuration = configuration;
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
            public <T> CommandProvider getByClass(Class<T> klass) {
                return getByInstance(classContainer.create(klass));
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
            public boolean execCommand(String rawMessage) {
                if (!rawMessage.startsWith(configuration.getByClass(CommandConfiguration.class).getPrefix())) {
                    return false;
                }

                boolean safe = rawMessage.split(configuration.getByClass(CommandConfiguration.class).getPrefix()).length > 1;

                if (safe) {
                    String beheaded = rawMessage.split(configuration.getByClass(CommandConfiguration.class).getPrefix())[1];
                    List<String> args = new ArrayList<>();
                    Collections.addAll(args, beheaded.split(" "));
                    String key = args.get(0);
                    args.remove(key);

                    Object command = getCommand(key);

                    if (command != null) {
                        if (!CommandFacade.this.execCommand(command, args)) {
                            for (Method method : command.getClass().getDeclaredMethods()) {
                                if (method.isAnnotationPresent(Exec.class)) {
                                    Parameter[] parameters = method.getParameters();
                                    List<String> params = new ArrayList<>();
                                    for (Parameter parameter : parameters) {
                                        params.add("<" + (parameter.isAnnotationPresent(Param.class) ? parameter.getAnnotation(Param.class).value() : "NULL") + "|" + parameter.getType().getSimpleName() + ">");
                                    }
                                    configuration.getByClass(CommandConfiguration.class).message(key + " " + params);
                                }
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

        for (Class<?> klass : classContainer.getAll()) {
            if (klass.isAnnotationPresent(Command.class)) {
                final Object commandInstance = classContainer.create(klass);
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
                    public String[] getKey() {
                        return klass.getAnnotation(Command.class).value();
                    }
                });
            }
        }
    }

    private boolean execCommand(Object command, List<String> args) {
        for (Method method : command.getClass().getDeclaredMethods()) {
            method.setAccessible(true);

            if (method.getParameterTypes().length == args.size() && method.isAnnotationPresent(Exec.class)) {
                List<Object> params = new ArrayList<>();
                for (int i = 0; i < method.getParameterTypes().length; i++) {
                    String arg = args.get(i);
                    Class<?> paramType = method.getParameterTypes()[i];
                    try {
                        if (paramType.equals(Boolean.class) || paramType.equals(boolean.class)) {
                            params.add(Boolean.parseBoolean(arg));
                        } else if (paramType.equals(Integer.class) || paramType.equals(int.class)) {
                            params.add(Integer.parseInt(arg));
                        } else if (paramType.equals(Float.class) || paramType.equals(float.class)) {
                            params.add(Float.parseFloat(arg));
                        } else if (paramType.equals(Double.class) || paramType.equals(double.class)) {
                            params.add(Double.parseDouble(arg));
                        } else if (paramType.equals(Long.class) || paramType.equals(long.class)) {
                            params.add(Long.parseLong(arg));
                        } else if (paramType.equals(Short.class) || paramType.equals(short.class)) {
                            params.add(Short.parseShort(arg));
                        } else if (paramType.equals(Byte.class) || paramType.equals(byte.class)) {
                            params.add(Byte.parseByte(arg));
                        } else if (paramType.equals(String.class)) {
                            params.add(String.valueOf(arg));
                        }
                    } catch (Exception e) {
                        configuration.getByClass(CommandConfiguration.class).message(e.getMessage());
                        e.printStackTrace();
                    }
                }

                List<CommandService> processors = classContainer.getService(CommandService.class);

                try {
                    processors.forEach(commandService -> commandService.beforeExec(commands.get(command)));
                    if (params.isEmpty()) {
                        method.invoke(command);
                    } else {
                        method.invoke(command, params.toArray());
                    }
                    processors.forEach(commandService -> commandService.afterExec(commands.get(command)));
                    return true;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    configuration.getByClass(CommandConfiguration.class).message(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private void help() {
        for (CommandProvider commandProvider : commandContainer.getAll()) {
            configuration.getByClass(CommandConfiguration.class).message(configuration.getByClass(CommandConfiguration.class).getPrefix() + Arrays.toString(commandProvider.getKey()) + commandProvider.getDescription());
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
