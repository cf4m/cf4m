package cn.enaium.cf4m.event;

import cn.enaium.cf4m.event.Listener;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class KeyboardEvent extends Listener {

    private final int key;

    public KeyboardEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
