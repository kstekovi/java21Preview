package org.kstekovi.java.preview.templates;


public class StringTemplates {

    public String multiLineFormat(String title, String text) {
        return String.format("""
                <html>
                  <head>
                    <title>%s</title>
                  </head>
                  <body>
                    <p>%s</p>
                  </body>
                </html>
                """, title, text);
    }

    public String multiLineTemplate(String title, String text) {
        return STR. """
        <html>
          <head>
            <title>\{ title }</title>
          </head>
          <body>
            <p>\{ text }</p>
          </body>
        </html>
        """ ;
    }
}
