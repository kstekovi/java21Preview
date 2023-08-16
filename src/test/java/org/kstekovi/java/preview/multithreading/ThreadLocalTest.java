package org.kstekovi.java.preview.multithreading;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.currentThread;

public class ThreadLocalTest {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    @Test
    public void threadLocalTest() throws ExecutionException, InterruptedException {
        // As of Java 21
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

        Future<String> futureA = executorService.submit(futureTask("Message A", 200));
        Future<String> futureB = executorService.submit(futureTask("Message B", 100));
        Future<String> futureC = executorService.submit(futureTask("Message C", 100));
        System.out.println(futureA.get());
        System.out.println(futureB.get());
        System.out.println(futureC.get());
    }

    @Test
    public void threadPoolTest() throws ExecutionException, InterruptedException {
        // Prior to Java 21 - newFixedThreadPool
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<String> futureA = executorService.submit(futureTask("Message A", 200));
        Future<String> futureB = executorService.submit(futureTask("Message B", 100));
        Future<String> futureC = executorService.submit(futureTask("Message C", 100));
        System.out.println(futureA.get());
        System.out.println(futureB.get());
        System.out.println(futureC.get());
    }



    private Callable<String> futureTask(String message, int sleepMilliseconds){
        return () -> {
            if(THREAD_LOCAL.get() == null) {
                THREAD_LOCAL.set(message);
            }
            Thread.sleep(sleepMilliseconds);
            return STR. "\{ Instant.now() }:\{currentThread(). getName()} \{ THREAD_LOCAL.get() }";
        };
    }
}
