package cz.meduna;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("START");

        System.out.println("Creating new...");
        SortedLinkedList<String> strings = new SortedLinkedList<>(List.of("1", "C", "A", "0"));
        System.out.println(strings);
        System.out.println("Contains 'A'?: " + strings.contains("A"));

        System.out.println("Removing first...");
        strings.removeFirst();
        System.out.println(strings);

        System.out.println("Clearing list...");
        strings.clear();
        System.out.println(strings);
        System.out.println("Contains 'A'?: " + strings.contains("A"));

        System.out.println("Adding all 4 item...");
        strings.addAll(List.of("Z", "B", "00", "1Y"));
        System.out.println(strings);

        System.out.println("Removing last...");
        strings.removeLast();
        System.out.println(strings);

        System.out.println("DONE");
    }

}