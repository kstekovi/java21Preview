package org.kstekovi.java.preview.concurrency;

import java.text.MessageFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Concurrency {

    public static void concurrency(){
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Supplier<String> method1  = scope.fork(() -> someLongRunningMethod(3));
            Supplier<String> method2 = scope.fork(() -> someLongRunningMethod(2));

            scope.join()            // Join both subtasks
                    .throwIfFailed();  // ... and propagate errors

            System.out.printf("%s - %s", new Date(), method1.get());
            System.out.printf("%s - %s", new Date(), method2.get());

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String someLongRunningMethod(int sleepTime) throws InterruptedException {
        System.out.printf("%s - before sleep", new Date());
        TimeUnit.SECONDS.sleep(sleepTime);
        System.out.printf("%s - after sleep%n", new Date());
        return String.format("sleep time was: %s", sleepTime);
    }
}
