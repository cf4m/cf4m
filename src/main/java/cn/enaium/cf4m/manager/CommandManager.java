package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.annotation.command.Param;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class CommandManager {
    /**
     * <K> command
     * <V> keys
     */
    private final HashMap<Object, String[]> commands;

    /**
     * Prefix.
     */
    private final String prefix;

    public CommandManager() {
        prefix = CF4M.configuration.command().prefix();
        commands = Maps.newHashMap();

        try {
            for (Class<?> klass : CF4M.klass.getClasses()) {
                if (klass.isAnnotationPresent(Command.class)) {
                    commands.put(klass.newInstance(), klass.getAnnotation(Command.class).value());
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param rawMessage SendChatMessage
     * @return Is it a command
     */
    public boolean isCommand(String rawMessage) {
        if (!rawMessage.startsWith(prefix)) {
            return false;
        }

        boolean safe = rawMessage.split(prefix).length > 1;

        if (safe) {
            String beheaded = rawMessage.split(prefix)[1];
            List<String> args = Lists.newArrayList(beheaded.split(" "));
            String key = args.get(0);
            args.remove(key);

            Object command = getCommand(key);

            if (command != null) {
                if (!execCommand(command, args)) {
                    for (Method method : command.getClass().getDeclaredMethods()) {
                        if (method.isAnnotationPresent(Exec.class)) {
                            Parameter[] parameters = method.getParameters();
                            List<String> params = Lists.newArrayList();
                            for (Parameter parameter : parameters) {
                                params.add("<" + (parameter.isAnnotationPresent(Param.class) ? parameter.getAnnotation(Param.class).value() : "NULL") + "|" + parameter.getType().getSimpleName() + ">");
                            }
                            CF4M.configuration.command().message(key + " " + params);
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

    private boolean execCommand(Object command, List<String> args) {
        for (Method method : command.getClass().getDeclaredMethods()) {
            method.setAccessible(true);

            if (method.getParameterTypes().length == args.size() && method.isAnnotationPresent(Exec.class)) {
                List<Object> params = Lists.newArrayList();
                for (int i = 0; i < method.getParameterTypes().length; i++) {
                    String arg = args.get(i);
                    Class<?> paramType = method.getParameterTypes()[i];

                    try {
                        if (paramType.equals(Boolean.class)) {
                            params.add(Boolean.parseBoolean(arg));
                        } else if (paramType.equals(Integer.class)) {
                            params.add(Integer.parseInt(arg));
                        } else if (paramType.equals(Float.class)) {
                            params.add(Float.parseFloat(arg));
                        } else if (paramType.equals(Double.class)) {
                            params.add(Double.parseDouble(arg));
                        } else if (paramType.equals(Long.class)) {
                            params.add(Long.parseLong(arg));
                        } else if (paramType.equals(Short.class)) {
                            params.add(Short.parseShort(arg));
                        } else if (paramType.equals(Byte.class)) {
                            params.add(Byte.parseByte(arg));
                        } else if (paramType.equals(String.class)) {
                            params.add(String.valueOf(arg));
                        }
                    } catch (Exception e) {
                        CF4M.configuration.command().message(e.getMessage());
                        e.printStackTrace();
                    }
                }

                try {
                    if (params.size() == 0) {
                        method.invoke(command);
                    } else {
                        method.invoke(command, params.toArray());
                    }
                    return true;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    CF4M.configuration.command().message(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private void help() {
        for (Map.Entry<Object, String[]> entry : commands.entrySet()) {
            CF4M.configuration.command().message(prefix + Arrays.toString(entry.getValue()) + getDescription(entry.getKey()));
        }
    }

    /**
     * @param object command instance
     * @return description
     */
    public String getDescription(Object object) {
        if (commands.containsKey(object)) {
            return object.getClass().getAnnotation(Command.class).description();
        }
        return null;
    }

    /**
     * @param object instance
     * @return command keys
     */
    public String[] getKey(Object object) {
        return commands.get(object);
    }

    private Object getCommand(String key) {
        for (Map.Entry<Object, String[]> entry : commands.entrySet()) {
            for (String s : entry.getValue()) {
                if (s.equalsIgnoreCase(key)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }
}
