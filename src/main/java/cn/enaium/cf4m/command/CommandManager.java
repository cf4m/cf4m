package cn.enaium.cf4m.command;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.module.ModuleAT;
import com.google.common.reflect.ClassPath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class CommandManager {
    /**
     * Command list.
     */
    public HashMap<String[], Command> commands;

    /**
     * Prefix.
     */
    public String prefix = "`";

    public CommandManager() {
        commands = new HashMap<>();
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClasses()) {
                if (info.getName().startsWith(CF4M.getInstance().packName)) {
                    Class<?> clazz = info.load();
                    if (clazz.isAnnotationPresent(CommandAT.class)) {
                        commands.put(clazz.getAnnotation(CommandAT.class).value(), (Command) clazz.newInstance());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
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
            Command command = getCommand(args[0]);
            if (command != null) {
                command.run(args);
            }
        }
        return true;
    }

    /**
     * @param name Index.
     * @return Whether match index.
     */
    private Command getCommand(String name) {
        for (Map.Entry entry : commands.entrySet()) {
            String[] key = (String[]) entry.getKey();
            for (String s : key) {
                if (s.equalsIgnoreCase(name)) {
                    return (Command) entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * @return Get Command list
     */
    public HashMap<String[], Command> getCommands() {
        return commands;
    }
}
