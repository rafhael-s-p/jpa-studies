package com.studies.ecommerce.concurrency;

import org.junit.Test;

public class ThreadTest {

    private static void log(Object obj, Object... args) {
        System.out.println(
                String.format("[LOG " + System.currentTimeMillis() + "] " + obj, args)
        );
    }

    private static void myWaiting(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {}
    }

    @Test
    public void runningThreads() {
        Runnable runnable1 = () -> {
            log("Runnable 01 will wait 3 seconds.");
            myWaiting(3);
            log("Runnable 01 finished.");
        };

        Runnable runnable2 = () -> {
            log("Runnable 02 will wait 1 seconds.");
            myWaiting(1);
            log("Runnable 02 finished.");
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log("Terminating test method.");
    }

}