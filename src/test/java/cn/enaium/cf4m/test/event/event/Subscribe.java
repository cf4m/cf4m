package cn.enaium.cf4m.test.event.event;

import cn.enaium.cf4m.event.Listener;

/**
 * Project: cf4m
 * Author: Enaium
 */
public class Subscribe extends Listener {
    private boolean cancel = false;

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean getCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
}
