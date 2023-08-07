package org.kstekovi.java.preview.collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class StringTemplatesTest {

    private static StringTemplates templates;

    @BeforeAll
    static void creteInstance(){
        templates = new StringTemplates();
    }

    @Test
    public void testTemplate(){
        String actualValue = templates.newTemplateGreating("John");
        String expected = "Hello John";
        assertEquals(expected, actualValue);
    }

    @Test
    public void mathOperation(){
        String actualValue = templates.mathOperation(15, 8);
        String expectedValue = "15 + 8 = 23";
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void multiLines(){
        String actualValue = templates.multiLines("Multi Lines", "Awesome");
        String expectedValue = "<html>\n" +
                "  <head>\n" +
                "    <title>Multi Lines</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <p>Awesome</p>\n" +
                "  </body>\n" +
                "</html>\n";
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void dateFormatting() {
        LocalTime now = LocalTime.now();
        String actualValue = templates.dateTimeFormat(now);
        String expectedValue = String.format("The time is %s right now", DateTimeFormatter
                .ofPattern("HH:mm:ss")
                .format(now));
        assertEquals(expectedValue, actualValue);
    }

}