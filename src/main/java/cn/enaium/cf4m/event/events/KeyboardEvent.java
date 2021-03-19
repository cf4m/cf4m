package cn.enaium.cf4m.event.events;

import cn.enaium.cf4m.event.Listener;

/**
 * Project: cf4m
 * Author: Enaium
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
