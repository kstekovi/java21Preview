package org.kstekovi.java.preview.multithreading;

import org.junit.jupiter.api.Test;
import org.kstekovi.java.preview.multihreading.MyScope;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.StructuredTaskScope;

import static java.lang.Thread.currentThread;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScopeValesTest {

    public final static ScopedValue<String> MESSAGE = ScopedValue.newInstance();

    private static void printRequestMessage(){
        System.out.println(STR. "\{ Instant.now() }:\{currentThread(). getName()} \{ MESSAGE.get() }");
    }

    @Test
    public void noScopeTest(){
        assertThrows(NoSuchElementException.class, ScopeValesTest::printRequestMessage);
    }

    @Test
    public void copeTest(){
        ScopedValue.where(MESSAGE, "Message A").run(ScopeValesTest::printRequestMessage);
        ScopedValue.where(MESSAGE, "Message B").run(ScopeValesTest::printRequestMessage);
    }

    @Test
    public void inheritingScopeValues() {
        ScopedValue.where(MESSAGE, "Message A").run(() -> {
            printRequestMessage();
            ScopedValue.where(MESSAGE, "Message B").run(() -> {
                printRequestMessage();
                try (StructuredTaskScope<String> scope = new StructuredTaskScope<>()) {
                    StructuredTaskScope.Subtask<String> fork = scope.fork(futureTask(100));
                    scope.join();  // Wait for forks
                    System.out.println(fork.get());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            printRequestMessage();
        });
    }

    private Callable<String> futureTask(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return () -> STR. "\{ Instant.now() }:\{ currentThread().getName() } \{ MESSAGE.get() }" ;
    }

    @Test
    public void shutdownOnSuccessTest(){
        ScopedValue.where(MESSAGE, "Message A").run(() -> {
            try (var scope = new StructuredTaskScope.ShutdownOnSuccess<>()){
                StructuredTaskScope.Subtask<String> fork1 = scope.fork(futureTask(100));
                StructuredTaskScope.Subtask<String> fork2 = scope.fork(futureTask(200));
                scope.join(); // Wait for forks
                assertTrue(scope.isShutdown());// true - the scope was shot down
                System.out.println(fork1.get());
                assertThrows(IllegalStateException.class, fork2::get);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void shutdownOnFailureTest(){
        ScopedValue.where(MESSAGE, "Message A").run(() -> {
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()){
                StructuredTaskScope.Subtask<String> fork1 = scope.fork(futureTask(100));
                StructuredTaskScope.Subtask<String> fork2 = scope.fork(futureTask(200));
                scope.join();  // Wait for forks
                assertFalse(scope.isShutdown()); // false - the scope was not shot down
                System.out.println(fork1.get());
                System.out.println(fork2.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void customTaskScopeTest(){
        ScopedValue.where(MESSAGE, "error").run(() -> {
            try (var scope = new MyScope()){
                StructuredTaskScope.Subtask<String> fork1 = scope.fork(futureTask(100));
                StructuredTaskScope.Subtask<String> fork2 = scope.fork(futureTask(200));
                scope.join();  // Wait for forks
                assertTrue(scope.isShutdown()); // true - the scope was shot down
                System.out.println(fork1.get());
                assertThrows(IllegalStateException.class, fork2::get);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
