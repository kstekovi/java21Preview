package org.kstekovi.java.preview.multihreading;

import java.util.concurrent.StructuredTaskScope;

public class MyScope extends StructuredTaskScope<String> {

    @Override
    protected void handleComplete(Subtask<? extends String> subtask) {
        super.handleComplete(subtask);
        String taskResult = subtask.get();
        if(taskResult.contains("error")){
            super.shutdown();
        }
    }
}
