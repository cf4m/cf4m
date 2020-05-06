package cn.enaium.cf4m.event;

public enum EventPriority {

    LOW(0), MEDIUM(1), HIGH(2);

    private int value;

    EventPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
