package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.command.Command;
import cn.enaium.cf4m.annotation.CommandAT;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class CommandManager {
    /**
     * Command list.
     */
    public HashMap<String[], Command> commands = Maps.newHashMap();

    /**
     * Prefix.
     */
    public String prefix = "`";

    public CommandManager() {
        try {
            for (Class<?> clazz : CF4M.getInstance().classManager.getClasses()) {
                if (clazz.isAnnotationPresent(CommandAT.class)) {
                    commands.put(clazz.getAnnotation(CommandAT.class).value(), (Command) clazz.newInstance());
                }
            }
        } catch (Exception e) {
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
}
