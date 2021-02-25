package cn.enaium.cf4m.event.events;

import cn.enaium.cf4m.event.Listener;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class KeyboardEvent extends Listener {

    private final int key;

    public KeyboardEvent(int key) {
        super(At.NONE);
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
