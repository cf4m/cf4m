package cn.enaium.cf4m.event.events;

import cn.enaium.cf4m.event.Event;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class KeyboardEvent extends Event {

    private int key;

    public KeyboardEvent(int keyCode) {
        super(Type.PRE);
        this.key = keyCode;
    }

    public int getKey() {
        return key;
    }
}
