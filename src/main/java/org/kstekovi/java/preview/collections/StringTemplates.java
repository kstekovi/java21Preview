package org.kstekovi.java.preview.collections;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.lang.StringTemplate.STR;

public class StringTemplates {

    /**
     * A template processor (STR);
     * A dot character (U+002E), as seen in other kinds of expressions; and
     * A template ("Hello \{name}") which contains an embedded expression (\{name}).
     * @param name
     * @return "Hello \{name}"
     */

    public String newTemplateGreating(String name){
        return STR."Hello \{name}";
    }

    public String mathOperation(int x, int y){
        return STR."\{x} + \{y} = \{x + y}";
    }

    public String multiLines(String title, String text) {
        return STR."""
        <html>
          <head>
            <title>\{title}</title>
          </head>
          <body>
            <p>\{text}</p>
          </body>
        </html>
        """;
    }


    public String dateTimeFormat(LocalTime now){

        return STR."The time is \{
        // The java.time.format package is very useful
        DateTimeFormatter
                .ofPattern("HH:mm:ss")
                .format(now)
        } right now";
    }
}
