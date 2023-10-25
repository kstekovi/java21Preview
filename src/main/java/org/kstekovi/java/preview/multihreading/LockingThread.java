package org.kstekovi.java.preview.multihreading;

public class LockingThread extends Thread {
    private static Long number = 0L;

    synchronized private static void plusOne() {
        number += 1;
    }

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        plusOne();
    }
}
