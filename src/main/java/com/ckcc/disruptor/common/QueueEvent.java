package com.ckcc.disruptor.common;

public class QueueEvent<T> {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
