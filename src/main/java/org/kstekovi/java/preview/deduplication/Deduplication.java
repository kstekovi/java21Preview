package org.kstekovi.java.preview.deduplication;

import java.util.ArrayList;
import java.util.List;

public class Deduplication {
    // java -XX:+UseG1GC -XX:+UseStringDeduplication -Xlog:gc*=info StringDeduplication
    // https://medium.com/codex/string-deduplication-in-java-2dc1e1893a74

    public static void deduplication() {
        System.out.printf("Running pid: %d %n", ProcessHandle.current().pid());

        List<String> strings = new ArrayList<>();
        strings.add(new String(new char[]{'w', 'o', 'r', 's', 't'}));
        strings.add(new String(new char[]{'w', 'o', 'r', 's', 't'}));
        strings.add(new String("bad"));
        strings.add(new String("bad"));
        strings.add("good");
        strings.add("good");
    }
}
