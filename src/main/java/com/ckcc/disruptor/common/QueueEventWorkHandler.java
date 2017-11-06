package com.ckcc.disruptor.common;

import com.lmax.disruptor.WorkHandler;

public class QueueEventWorkHandler<T> implements WorkHandler<QueueEvent<T>> {

    protected String name;

    public QueueEventWorkHandler(String name) {
        this.name = name;
    }

    public void onEvent(QueueEvent<T> event) throws Exception {
        StringBuffer buf = new StringBuffer();
        buf.append("EventHandler : ").append(name).append(" > event: ").append(event.getValue())
                .append(" | sequence: ").append("NULL").append(" | ").append(Thread.currentThread().getName())
                .append(" | ").append(this.hashCode());
        System.out.println(buf.toString());
    }
}
