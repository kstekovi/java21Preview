package org.kstekovi.java.preview.multihreading;

public class SleepingThread extends Thread{

    private final long sleepMillisecond;

    public SleepingThread(long time){
        super();
        sleepMillisecond = time;
    }

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(sleepMillisecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
