package org.kstekovi.java.preview.collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayTest {
    private  List<String> list = new ArrayList<>();

    private static Array array = new Array();

    @BeforeEach
    void creteInstance(){
        list = new ArrayList<>();
        list.add("First");
        list.add("Second");
        list.add("Third");
    }


    @Test
    public void addAtStart(){
        array.addAtStart(list);
        List<String> expectedValue = List.of("At start", "First", "Second", "Third");
        assertEquals(expectedValue, list);
    }
}