package cn.enaium.cf4m.command;

import java.util.List;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public interface ICommand {
    /**
     * @param args input.
     */
    boolean run(String[] args);

    /**
     * @return usage.
     */
    String usage();
}
