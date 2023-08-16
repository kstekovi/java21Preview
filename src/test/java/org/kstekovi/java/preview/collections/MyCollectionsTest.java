package org.kstekovi.java.preview.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.SequencedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MyCollectionsTest {

    // SequencedCollection, SequencedSet, SequencedMap - new interfaces with new methods
    // | -> ArrayList - existing class updated
    // | -> LinkedHashMap - new class implementation
    // https://openjdk.org/jeps/431

    private List<String> actualList;
    private List<String> expectedList;
    private SequencedMap<Integer, String> actualMap;
    private Map<Integer, String> expectedMap;

    @BeforeEach
    void setUp() {
        actualList = new ArrayList<>(List.of("First", "Second", "Third"));
        expectedList = new ArrayList<>(List.of("First", "Second", "Third"));

        actualMap = new LinkedHashMap<>();
        actualMap.put(1, "First");
        actualMap.put(2, "Second");
        actualMap.put(3, "Third");

        expectedMap = new LinkedHashMap<>();
        expectedMap.put(1, "First");
        expectedMap.put(2, "Second");
        expectedMap.put(3, "Third");
    }

    @Test
    public void addFirstTest(){
        // Prior to Java 21
        expectedList.add(0, "At start");
        // As of Java 21
        actualList.addFirst("At start");

        assertEquals(expectedList, actualList);
    }

    @Test
    void addLastTest() {
        // Prior to Java 21
        expectedList.add( "At end");
        // As of Java 21
        actualList.addLast("At end");

        assertEquals(expectedList, actualList);
    }

    @Test
    void getFirstTest() {
        // Prior to Java 21
        String expectedValue = actualList.get(0);
        // As of Java 21
        String actualValue = actualList.getFirst();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getLastTest() {
        // Prior to Java 21
        String expectedValue = actualList.get(actualList.size()-1);
        // As of Java 21
        String actualValue = actualList.getLast();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getReversedIteratorTest() {
        // Prior to Java 21
        List<String> expectedList = new ArrayList<>();
        ListIterator<String> listIterator = actualList.listIterator(actualList.size());
        while(listIterator.hasPrevious()){
            String string = listIterator.previous();
            expectedList.add(string);
        }
        // As of Java 21
        List<String> actualList = this.actualList.reversed();

        assertEquals(expectedList, actualList);
        assertNotEquals(this.actualList, actualList);
    }

    @Test
    void getReversedCollectionsTest(){
        // Prior to Java 21
        // modify the list - previous state is lost
        java.util.Collections.reverse(expectedList);
        // As of Java 21
        List<String> actualValue = actualList.reversed();

        assertEquals(expectedList, actualValue);
    }

    @Test
    void removeFirst() {
        // Prior to Java 21
        String expected = expectedList.remove(0);
        // As of Java 21
        String actual = actualList.removeFirst();

        assertEquals(actualList, expectedList);
        assertEquals(actual, expected);
    }

    @Test
    void removeLast() {
        // Prior to Java 21
        String expected = expectedList.remove(expectedList.size()-1);
        // As of Java 21
        String actual = actualList.removeLast();

        assertEquals(actualList, expectedList);
        assertEquals(actual, expected);
    }

    @Test
    void pollFirstTest(){
        // Prior to Java 21
        Map.Entry<Integer, String> expectedEntry = expectedMap.entrySet().stream().findFirst().orElseThrow();
        expectedMap.remove(expectedEntry.getKey());
        // As of Java 21
        Map.Entry<Integer, String> actualEntry = actualMap.pollFirstEntry();

        assertEquals(actualEntry, expectedEntry);
        assertEquals(actualMap, expectedMap);
    }

    @Test
    void pollLastTest(){
        // Prior to Java 21
        // reduce values to last
        Map.Entry<Integer, String> expectedEntryV1 = expectedMap.entrySet()
                .stream()
                .reduce((first, second) -> second)
                .orElseThrow();
        // Prior to Java 21
        // skip x values
        Map.Entry<Integer, String> expectedEntryV2 = expectedMap.entrySet()
                .stream()
                .skip(expectedMap.size() - 1)
                .findFirst()
                .orElseThrow();
        // remove from the map
        expectedMap.remove(expectedEntryV1.getKey());

        // As of Java 21
        Map.Entry<Integer, String> actualEntry = actualMap.pollLastEntry();

        assertEquals(actualEntry, expectedEntryV1);
        assertEquals(actualEntry, expectedEntryV2);
        assertEquals(actualMap, expectedMap);
    }
}