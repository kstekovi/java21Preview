package org.kstekovi.java.preview.multihreading;

import java.time.Instant;

public class WorkingThread extends Thread {

    private final long sleepMillisecond;


    private long result = 0;

    public WorkingThread(long time) {
        super();
        sleepMillisecond = time;
    }

    @Override
    public void run() {
        super.run();
        Instant until = Instant.now().plusMillis(sleepMillisecond);

        while (Instant.now().isBefore(until)) {
            result += 1;
        }
//        long usedMemery = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//        System.out.println(FMT. "\{ Instant.now() }:%-16s\{ currentThread().getName() }: Current memory usage: \{ usedMemery }" );
    }
}
