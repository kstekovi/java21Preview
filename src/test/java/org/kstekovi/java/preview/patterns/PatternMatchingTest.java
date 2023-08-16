package org.kstekovi.java.preview.patterns;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class PatternMatchingTest {

    @Test
    public void recordTest(){
        MyPatternsObject object1 = new MyPatternsObject("some text", 1);
        MyPatternsObject object2 = new MyPatternsObject("some text", 1);

        MyPatternsRecord record1 = new MyPatternsRecord("some text", 1);
        MyPatternsRecord record2 = new MyPatternsRecord("some text", 1);

        assertNotEquals(object1, object2);
        assertEquals(record1, record2);

        assertEquals(object1.getText(), record1.text());
    }

    @Test
    public void java16JEP394Test(){
        Object object = "some text";
        // Prior to Java 16
        if (object instanceof String){
            String text = (String) object;
            assertThat(text, instanceOf(String.class));
        }
        // As of Java 16
        if (object instanceof String text){
            assertThat(text, instanceOf(String.class));
        }
    }

    @Test
    public void recordMatchingTest(){
        Object object = new MyPatternsRecord("some text", 1);
        // Prior to Java 21
        if (object instanceof MyPatternsRecord record){
            assertEquals("some text", record.text());
            assertEquals(1 , record.id());
        }
        // As of Java 21
        if (object instanceof MyPatternsRecord(String text, Integer id)){
            assertEquals("some text", text);
            assertEquals(1, id);
        }
    }

    @Test
    public void switchMatching() {
        Object obj = "some text";
        // Prior to Java 21 (from 16)
        if (obj instanceof Integer i) {
            assertThat(i, instanceOf(Integer.class));
        } else if (obj instanceof Long l) {
            assertThat(l, instanceOf(Long.class));
        } else if (obj instanceof Double d) {
            assertThat(d, instanceOf(Double.class));
        } else if (obj instanceof String s) {
            assertThat(s, instanceOf(String.class));
        }
        // As of Java 21 (17 preview)
        switch (obj) {
            case Integer i -> assertThat(i, instanceOf(Integer.class));
            case Long l -> assertThat(l, instanceOf(Long.class));
            case Double d -> assertThat(d, instanceOf(Double.class));
            case String s -> assertThat(s, instanceOf(String.class));
            default -> fail();
        }
    }

    @Test
    public void nullTest() {
        String nullObject = null;
        // Prior to Java 21
        if (nullObject == null) {
            assertNull(nullObject);
        } else {
            switch (nullObject) {
                case "Foo" -> assertEquals("Foo", nullObject);
                default -> fail();
            }
        }
        // As of Java 21
        switch (nullObject) {
            case "Foo" -> assertEquals("Foo", nullObject);
            case null -> assertNull(nullObject);
            default -> fail();
        }
    }

    @Test
    public void caseRefinement() {
        // prepsat na text A a text B
        Object text = "text A";
        switch (text) {
            case Integer _ -> fail();
            case String s -> {
                if (s.equals("text B")){
                    assertEquals("text B", s);
                } else if (s.equals("text A")){
                    assertEquals("text A", s);
                } else {
                    fail();
                }
            }
            default -> fail();
        }

        // As of Java 21
        switch (text) {
            case String s when s.equals("text B") -> assertEquals("text B", text);
            case String s when s.equals("text A") -> assertEquals("text A", text);
            case null -> assertNull(text);
            default -> fail("No case match");
        }
    }
}
