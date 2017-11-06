package com.ckcc.disruptor.common;

import com.lmax.disruptor.EventFactory;

public class QueueEventFactory<T> implements EventFactory<QueueEvent<T>> {

    public QueueEvent<T> newInstance() {
        return new QueueEvent<T>();
    }
}
