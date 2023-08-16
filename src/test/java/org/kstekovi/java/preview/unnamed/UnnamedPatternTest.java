package org.kstekovi.java.preview.unnamed;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnnamedPatternTest {


    private final MyRecord record = new MyRecord(1, "some text");
    private final MyCompositedRecord object = new MyCompositedRecord(record, MyEnum.FIRST);

    @Test
    public void test(){
        // Prior to Java 21 (from 16)
        if (object instanceof MyCompositedRecord compositedRecord){
            assertEquals(1, compositedRecord.record().id());
            assertEquals("some text", compositedRecord.record().text());
            assertEquals(MyEnum.FIRST, compositedRecord.enumValue());
        }
        // As of Java 21
        // if (object instanceof MyCompositedRecord(
        //      MyUnnamedRecord(Integer id, String text), MyEnum enumValue)){
        if (object instanceof MyCompositedRecord(MyRecord(Integer id, _), _)){
            assertEquals(1 , id);
        }
        if (object instanceof MyCompositedRecord(_, MyEnum enumValue)){
            assertEquals(MyEnum.FIRST, enumValue);
        }
        // it doesn't work with object. It works only with records
    }
}
