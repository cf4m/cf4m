package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.annotation.command.Param;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class CommandManager {
    /**
     * Command list.
     */
    private final HashMap<String[], Object> combs;

    /**
     * Prefix.
     */
    private final String prefix;

    public CommandManager() {
        prefix = CF4M.INSTANCE.configuration.prefix();
        combs = Maps.newHashMap();

        try {
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Command.class)) {
                    combs.put(type.getAnnotation(Command.class).value(), type.newInstance());
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
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
            String[] args = beheaded.split(" ");
            Object command = getCommand(args[0]);

            if (command != null) {
                if (!execCommand(command, args)) {
                    for (Method method : command.getClass().getDeclaredMethods()) {
                        Parameter[] parameters = method.getParameters();
                        String[] parameter = new String[parameters.length];
                        for (int i = 0; i < method.getParameters().length; i++) {
                            parameter[i] = "<" + (parameters[i].isAnnotationPresent(Param.class) ? parameters[i].getAnnotation(Param.class).value() : "NULL") + "|" + parameters[i].getType().getSimpleName() + ">";
                        }
                        CF4M.INSTANCE.configuration.message(args[0] + " " + Arrays.toString(parameter));
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

    private boolean execCommand(Object command, String[] args) {
        for (Method method : command.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getParameterTypes().length == args.length - 1 && method.isAnnotationPresent(Exec.class)) {
                Object[] params = new Object[args.length - 1];
                for (int i = 0; i < method.getParameterTypes().length; i++) {
                    Class<?> paramType = method.getParameterTypes()[i];
                    if (paramType.equals(Boolean.class)) {
                        params[i] = (Boolean.parseBoolean(args[i + 1]));
                    } else if (paramType.equals(Integer.class)) {
                        params[i] = (Integer.parseInt(args[i + 1]));
                    } else if (paramType.equals(Float.class)) {
                        params[i] = (Float.parseFloat(args[i + 1]));
                    } else if (paramType.equals(Double.class)) {
                        params[i] = (Double.parseDouble(args[i + 1]));
                    } else if (paramType.equals(Long.class)) {
                        params[i] = (Long.parseLong(args[i + 1]));
                    } else if (paramType.equals(Short.class)) {
                        params[i] = (Short.parseShort(args[i + 1]));
                    } else if (paramType.equals(String.class)) {
                        params[i] = (String.valueOf(args[i + 1]));
                    }
                }

                try {
                    if (params.length == 0) {
                        method.invoke(command);
                    } else {
                        method.invoke(command, params);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    private void help() {
        for (Map.Entry<String[], Object> entry : combs.entrySet()) {
            CF4M.INSTANCE.configuration.message(prefix + Arrays.toString(entry.getKey()));
        }
    }

    private Object getCommand(String key) {
        for (Map.Entry<String[], Object> entry : combs.entrySet()) {
            for (String s : entry.getKey()) {
                if (s.equalsIgnoreCase(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}
