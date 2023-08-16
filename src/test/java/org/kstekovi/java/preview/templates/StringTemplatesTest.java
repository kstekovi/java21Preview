package org.kstekovi.java.preview.templates;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.util.FormatProcessor.FMT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringTemplatesTest {

    private static StringTemplates templates;


    @BeforeAll
    static void creteInstance(){
        templates = new StringTemplates();
    }

    @Test
    public void testTemplate(){
        String name = "John";
        // Prior to Java 21
        String stringFormat = String.format("Hello %s", name);
        // As of Java 21
        String stringTemplate = STR."Hello \{name}";
        assertEquals(stringFormat, stringTemplate);
    }

    @Test
    public void multiLines(){
        String multilineTitle = "Multi Lines";
        String body = "Awesome page";
        // Prior to Java 21 - look at the method
        String multilineFormat = templates.multiLineFormat(multilineTitle, body);
        // As of Java 21 - look at the method
        String multilineTemplate = templates.multiLineTemplate(multilineTitle, body);
        assertEquals(multilineFormat, multilineTemplate);
    }

    @Test
    public void mathOperation(){
        int x = 15;
        int y = 8;
        // Prior to Java 21
        String mathFormat = String.format("%d + %d = %d", x, y, x+y);
        // As of Java 21
        String mathTemplate = STR."\{x} + \{y} = \{x + y}";
        assertEquals(mathFormat, mathTemplate);
    }

    @Test
    public void dateFormatting() {
        LocalTime now = LocalTime.now();
        // Prior to Java 21
        String time = DateTimeFormatter.ofPattern("HH:mm:ss").format(now);
        String formattedMessage = String.format("The time is %s right now", time);
        // As of Java 21
        String templateMessage = STR. "The time is \{
                // The java.time.format package is very useful
                DateTimeFormatter
                        .ofPattern("HH:mm:ss")
                        .format(now)
                } right now" ;


        assertEquals(formattedMessage, templateMessage);
    }

    @Test
    public void textFormatting() {
        Rectangle[] zone = new Rectangle[]{
                new Rectangle("Alfa", 17.8, 31.4),
                new Rectangle("Bravo", 9.6, 12.4),
                new Rectangle("Charlie", 7.1, 11.23),
        };
        String table = FMT. """
    Description     Width    Height     Area
    %-12s\{ zone[0].name }  %7.2f\{ zone[0].width }  %7.2f\{ zone[0].height }     %7.2f\{ zone[0].area() }
    %-12s\{ zone[1].name }  %7.2f\{ zone[1].width }  %7.2f\{ zone[1].height }     %7.2f\{ zone[1].area() }
    %-12s\{ zone[2].name }  %7.2f\{ zone[2].width }  %7.2f\{ zone[2].height }     %7.2f\{ zone[2].area() }
    \{ " ".repeat(28) } Total %7.2f\{ zone[0].area() + zone[1].area() + zone[2].area() }
    """ ;
        System.out.println(table);
    }

    record Rectangle(String name, double width, double height) {
        double area() {
            return width * height;
        }
    }

}