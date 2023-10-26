package org.kstekovi.java.preview.multihreading;

import java.time.Instant;

public class WorkingThread extends Thread {

    private final long calculationTime;


    private long result = 0;

    public WorkingThread(long time) {
        super();
        calculationTime = time;
    }

    @Override
    public void run() {
        super.run();
        Instant until = Instant.now().plusMillis(calculationTime);

        while (Instant.now().isBefore(until)) {
            result += 1;
        }
    }
}
