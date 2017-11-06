package com.ckcc.disruptor.common;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class DisruptorTemplate<T> {

    private EventFactory<QueueEvent<T>> eventFactory;
    private int bufferSize;
    private ThreadFactory threadFactory;
    private Disruptor<QueueEvent<T>> disruptor;

    public DisruptorTemplate(EventFactory<QueueEvent<T>> eventFactory, int bufferSize) {
        this.eventFactory = eventFactory;
        this.bufferSize = bufferSize;
        this.threadFactory = Executors.defaultThreadFactory();
    }

    public DisruptorTemplate(EventFactory<QueueEvent<T>> eventFactory, int bufferSize, ThreadFactory threadFactory) {
        this.eventFactory = eventFactory;
        this.bufferSize = bufferSize;
        this.threadFactory = threadFactory;
    }

    public EventHandlerGroup<QueueEvent<T>> registerHandler(EventHandler<QueueEvent<T>>... handlers) {
        disruptor = new Disruptor<QueueEvent<T>>(eventFactory, bufferSize, threadFactory, ProducerType.MULTI, new BlockingWaitStrategy());
        return disruptor.handleEventsWith(handlers);
    }

    public EventHandlerGroup<QueueEvent<T>> registerHandler(WorkHandler<QueueEvent<T>>... handlers) {
        disruptor = new Disruptor<QueueEvent<T>>(eventFactory, bufferSize, threadFactory, ProducerType.MULTI, new BlockingWaitStrategy());
        return disruptor.handleEventsWithWorkerPool(handlers);
    }

    public RingBuffer<QueueEvent<T>> start() throws DisruptorCheckedException {
        if(null == disruptor) {
            throw new DisruptorCheckedException("disruptor is null");
        }
        return disruptor.start();
    }

    public void processData(T data) throws DisruptorCheckedException {
        if(null == disruptor) {
            throw new DisruptorCheckedException("disruptor is null");
        }
        RingBuffer<QueueEvent<T>> ringBuffer = disruptor.getRingBuffer();
        QueueEventProducer<T> eventProducer = new QueueEventProducer<T>(ringBuffer);
        eventProducer.onData(data);
    }

    public void processDataWithTranslator(T data) throws DisruptorCheckedException {
        if(null == disruptor) {
            throw new DisruptorCheckedException("disruptor is null");
        }
        RingBuffer<QueueEvent<T>> ringBuffer = disruptor.getRingBuffer();
        QueueEventProducer<T> eventProducer = new QueueEventProducer<T>(ringBuffer);
        eventProducer.onDataWithTranslator(data);
    }

    public void shutdown() throws DisruptorCheckedException {
        if(null == disruptor) {
            throw new DisruptorCheckedException("disruptor is null");
        }
        disruptor.shutdown();
    }

}
