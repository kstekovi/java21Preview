package org.kstekovi.java.preview.patterns;

public class MyPatternsObject {

    private String text;
    private Integer id;

    public MyPatternsObject(String text, Integer id){
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Integer getId() {
        return id;
    }
}
