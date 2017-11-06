package com.ckcc.disruptor.demo;

import com.ckcc.disruptor.common.*;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataProcessorByChain {

    public static void main(String[] args) throws DisruptorCheckedException {
        ExecutorService executor = Executors.newFixedThreadPool(6);

        QueueEventFactory<String> eventFactory = new QueueEventFactory<String>();
        final DisruptorTemplate<String> disruptorTemplate = new DisruptorTemplate<String>(eventFactory, 1024);
        try {
            EventHandler<QueueEvent<String>> eventHandler_1 = new QueueEventHandler<String>("event-handler-1");
            EventHandler<QueueEvent<String>> eventHandler_2 = new QueueEventHandler<String>("event-handler-2");
            EventHandler<QueueEvent<String>> eventHandler_3 = new QueueEventHandler<String>("event-handler-3");
            WorkHandler<QueueEvent<String>> workHandler_1 = new QueueEventWorkHandler<String>("work-handler-1");
            WorkHandler<QueueEvent<String>> workHandler_2 = new QueueEventWorkHandler<String>("work-handler-2");
            WorkHandler<QueueEvent<String>> workHandler_3 = new QueueEventWorkHandler<String>("work-handler-3");
            disruptorTemplate.registerHandler(workHandler_1, workHandler_2, workHandler_3);
//            disruptorTemplate.registerHandler(eventHandler_1, eventHandler_2, eventHandler_3);
//            disruptorTemplate.registerHandler(workHandler_1);
//            disruptorTemplate.registerHandler(eventHandler_1);
            //            disruptorTemplate.registerHandler(eventHandler_1, eventHandler_2).then(eventHandler_3);
            disruptorTemplate.start();

            for (int i = 0; i < 12; i++) {
                final int j = i;
                executor.submit(new Runnable() {
                    public void run() {
                        try {
//                            System.out.println(">>> gsdg　" + j);
                            disruptorTemplate.processDataWithTranslator("hello disruptor! >>> " + j);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
//            disruptorTemplate.processDataWithTranslator("hello disruptor! >>> ");
        } finally {
//            disruptorTemplate.shutdown();
        }
    }

}
