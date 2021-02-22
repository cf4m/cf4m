package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Command;
import cn.enaium.cf4m.command.ICommand;
import com.google.common.collect.Maps;

import java.util.Arrays;
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
    private final HashMap<String[], ICommand> commands = Maps.newHashMap();

    /**
     * Prefix.
     */
    private final String prefix = CF4M.INSTANCE.configuration.prefix();

    public CommandManager() {
        try {
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Command.class)) {
                    commands.put(type.getAnnotation(Command.class).value(), (ICommand) type.newInstance());
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

            for (Map.Entry<String[], ICommand> entry : commands.entrySet()) {
                String[] key = entry.getKey();

                for (String s : key) {
                    if (s.equalsIgnoreCase(args[0])) {
                        ICommand command = entry.getValue();
                        if (!command.run(args)) {
                            String[] usages = command.usage().split("\n");
                            for (String usage : usages) {
                                CF4M.INSTANCE.configuration.message(Arrays.toString(entry.getKey()) + " " + usage);
                            }
                        }
                    }
                }
            }
        } else {
            CF4M.INSTANCE.configuration.message("Try " + prefix + "help");
        }


        return true;
    }

    public HashMap<String[], ICommand> getCommands() {
        return commands;
    }
}
