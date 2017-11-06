package com.ckcc.disruptor.common;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

public class QueueEventProducer<T> {

    private final RingBuffer<QueueEvent<T>> ringBuffer;

    public QueueEventProducer(RingBuffer<QueueEvent<T>> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private final EventTranslatorOneArg<QueueEvent<T>, T> EVENT_TRANSLATOR_ONE_ARG = new EventTranslatorOneArg<QueueEvent<T>, T>() {
        public void translateTo(QueueEvent<T> event, long sequence, T data) {
            event.setValue(data);
        }
    };

    public void onData(T data) {
        long sequence = ringBuffer.next();
        try {
            QueueEvent<T> event = ringBuffer.get(sequence);
            event.setValue(data);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    public void onDataWithTranslator(T data) {
        ringBuffer.publishEvent(EVENT_TRANSLATOR_ONE_ARG,data);
    }

}
