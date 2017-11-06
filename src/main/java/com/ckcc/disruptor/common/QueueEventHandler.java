package com.ckcc.disruptor.common;

import com.lmax.disruptor.EventHandler;

public class QueueEventHandler<T> implements EventHandler<QueueEvent<T>> {

    protected String name;

    public QueueEventHandler(String name) {
        super();
        this.name = name;
    }

    public void onEvent(QueueEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        StringBuffer buf = new StringBuffer();
        buf.append("EventHandler : ").append(name).append(" > event: ").append(event.getValue())
                .append(" | sequence: ").append(sequence).append(" | ").append(Thread.currentThread().getName())
                .append(" | ").append(this.hashCode());
        System.out.println(buf.toString());
    }

}
