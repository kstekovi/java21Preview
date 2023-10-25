package org.kstekovi.java.preview.multihreading;

import java.time.Instant;

public class SleepingThread extends Thread{

    private final long sleepMillisecond;

    private final String message;

    public SleepingThread(long time, String message){
        sleepMillisecond = time;
        this.message = message;
    }

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(sleepMillisecond);
            System.out.println(STR. "\{ Instant.now() }:\{currentThread(). getName()}:\{ message }" );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
