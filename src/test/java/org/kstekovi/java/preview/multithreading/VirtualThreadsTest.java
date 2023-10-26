package org.kstekovi.java.preview.multithreading;

import org.junit.jupiter.api.Test;
import org.kstekovi.java.preview.multihreading.LockingThread;
import org.kstekovi.java.preview.multihreading.SleepingThread;
import org.kstekovi.java.preview.multihreading.WorkingThread;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.currentThread;
import static java.util.FormatProcessor.FMT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VirtualThreadsTest {

    @Test
    public void platformThreadTest() throws InterruptedException {
        // prepare thread
        Thread thread = new SleepingThread(300, "Hello message");
        assertEquals(Thread.State.NEW, thread.getState());
        // start thread
        thread.start();
//        Thread.startVirtualThread(thread);
        assertEquals(Thread.State.RUNNABLE, thread.getState());
        // wait it end
        thread.join();
        // Since java 19
        // thread.join(2000);
        assertEquals(Thread.State.TERMINATED, thread.getState());
    }

    @Test
    public void threadExecutorTest() throws InterruptedException {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(STR."Thread name: \{currentThread().getName()}");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Test
    public void threadsPoolTest() {
        // limit number of CPU cores or your own limitation as in example=
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            Future<String> futureA = executorService.submit(futureTask("Message A"));
            Future<String> futureB = executorService.submit(futureTask("Message B"));
            Future<String> futureC = executorService.submit(futureTask("Message C"));
            System.out.println(STR."\{Instant.now()}:\{currentThread(). getName()} Start");
            // method get will wait for result
            System.out.println(futureA.get());
            System.out.println(futureB.get());
            System.out.println(futureC.get());
        }catch (ExecutionException| InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private Callable<String> futureTask(String message){
        return () -> {
            Thread.sleep(100);
            return STR."\{Instant.now()}:\{currentThread().getName()} \{message}";
        };
    }

    @Test
    public void lambdaParallelTest() {
        List<String> strings = List.of("message A", "Message B", "Message C");
        System.out.println(STR."\{Instant.now()}:\{currentThread(). getName()} Start");
        strings.parallelStream().forEach(message -> {
            try {
                Thread.sleep(100);
                System.out.println(STR. "\{ Instant.now() }:\{currentThread(). getName()} \{ message }" );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void virtualThreadsTest() throws ExecutionException, InterruptedException {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> futureA = executorService.submit(futureTask("Message A"));
            Future<String> futureB = executorService.submit(futureTask("Message B"));
            Future<String> futureC = executorService.submit(futureTask("Message C"));
            System.out.println(STR."\{Instant.now()}:\{currentThread(). getName()} Start");
            System.out.println(futureA.get());
            System.out.println(futureB.get());
            System.out.println(futureC.get());
        }
    }

    @Test
    public void workingThreadsTest() {
        Instant start = Instant.now();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= 100; i ++){
                Thread thread = new WorkingThread(500);
                executorService.submit(thread);
            }
        }
        Instant afterVirtualThreads = Instant.now();
        try (ExecutorService executorService = Executors.newFixedThreadPool(50)) {
            for (int i = 1; i <= 100; i ++){
                Thread thread = new WorkingThread(500);
                executorService.submit(thread);
            }
        }
        Instant afterPlatformThreads = Instant.now();
        Duration virtualThreads = Duration.between(start, afterVirtualThreads);
        Duration platformThread = Duration.between(afterVirtualThreads, afterPlatformThreads);
        System.out.println(FMT."Virtual threads: \{virtualThreads}, Platform threads: \{platformThread}");
    }

    @Test
    public void LockingThreadTest() {
        Instant start = Instant.now();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= 500; i ++){
                Thread thread = new LockingThread();
                executorService.submit(thread);
            }
        }
        Instant afterVirtualThreads = Instant.now();
        try (ExecutorService executorService = Executors.newFixedThreadPool(50)) {
            for (int i = 1; i <= 500; i ++){
                Thread thread = new LockingThread();
                executorService.submit(thread);
            }
        }
        Instant afterPlatformThreads = Instant.now();
        Duration virtualThreads = Duration.between(start, afterVirtualThreads);
        Duration platformThread = Duration.between(afterVirtualThreads, afterPlatformThreads);
        System.out.println(FMT."Virtual threads: \{virtualThreads}, Platform threads: \{platformThread}");
    }
}
