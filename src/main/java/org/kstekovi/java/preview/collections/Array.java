package org.kstekovi.java.preview.collections;

import java.util.ArrayList;
import java.util.List;

public class Array {
    public static void newArrayList(){
        var list = new ArrayList<>();

        list.add("first");
        list.add("second");
        list.add("third");

        var first = list.getFirst();
        var last = list.getLast();

        list.addFirst("add at start");
        list.addLast("add at end");

        list.removeFirst();
        list.removeLast();

        var reversed = list.reversed();
    }

    public void addAtStart(List<String> list) {
        list.addFirst("At start");
    }
}
