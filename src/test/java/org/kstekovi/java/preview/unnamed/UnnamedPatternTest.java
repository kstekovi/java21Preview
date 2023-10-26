package org.kstekovi.java.preview.unnamed;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnnamedPatternTest {


    private final TestRecord record = new TestRecord(1, "some text");
    private final CompositedRecord compositedRecord = new CompositedRecord(record, MyEnum.FIRST);

    @Test
    public void test(){
        // Prior to Java 21 (from 16)
        if (compositedRecord instanceof CompositedRecord compositedRecord){
            assertEquals(1, compositedRecord.record().id());
            assertEquals("some text", compositedRecord.record().text());
            assertEquals(MyEnum.FIRST, compositedRecord.enumValue());
        }
        // As of Java 21
        // if (object instanceof MyCompositedRecord(
        //      MyUnnamedRecord(Integer id, String text), MyEnum enumValue)){
        if (compositedRecord instanceof CompositedRecord(TestRecord(Integer id, _), _)){
            assertEquals(1 , id);
        }
        if (compositedRecord instanceof CompositedRecord(_, MyEnum enumValue)){
            assertEquals(MyEnum.FIRST, enumValue);
        }
        // it doesn't work with object. It works only with records
    }
}
