package cz.meduna;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortedLinkedListTest {

    @Test
    public void constructorTest() {
        List<String> strings = new SortedLinkedList<>(List.of("HH", "652", "0", "a", "Foo"));

        assertThat(strings, hasSize(5));
        assertThat(strings, contains("0", "652", "Foo", "HH", "a"));

        List<Integer> ints = new SortedLinkedList<>(List.of(15, 30, 0, -20, -20, 233));

        assertThat(ints, hasSize(6));
        assertThat(ints, contains(-20, -20, 0, 15, 30, 233));
    }

    @Test
    public void addTest() {
        List<String> strings = new SortedLinkedList<>();

        assertThat(strings, empty());

        strings.add("C");
        strings.add("v");
        strings.add("0");

        assertThat(strings, hasSize(3));
        assertThat(strings, contains("0", "C", "v"));

        List<Integer> ints = new SortedLinkedList<>();

        assertThat(ints, empty());

        ints.add(56);
        ints.add(41);
        ints.add(0);
        ints.add(-98);
        ints.add(0);

        assertThat(ints, hasSize(5));
        assertThat(ints, contains(-98, 0, 0, 41, 56));

    }

    @Test
    public void removeTest() {
        List<String> strings = new SortedLinkedList<>(List.of("HH", "652", "0", "a", "Foo"));
        strings.remove("0");

        assertThat(strings, hasSize(4));
        assertThat(strings, contains("652", "Foo", "HH", "a"));

        List<Integer> ints = new SortedLinkedList<>(List.of(15, 30, 0, -20, -20, 233));
        ints.remove(Integer.valueOf(-20));
        assertThat(ints, hasSize(5));
        assertThat(ints, contains(-20, 0, 15, 30, 233));

        ints.remove(0);
        assertThat(ints, hasSize(4));
        assertThat(ints, contains(0, 15, 30, 233));

        assertThrows(IndexOutOfBoundsException.class, () -> ints.remove(50));
    }

    @Test
    public void removeFirstTest() {
        SortedLinkedList<String> strings = new SortedLinkedList<>(List.of("HH", "652", "0", "a", "Foo"));
        assertThat(strings, hasSize(5));

        strings.removeFirst();

        assertThat(strings, hasSize(4));
        assertThat(strings, contains("652", "Foo", "HH", "a"));

        SortedLinkedList<Integer> ints = new SortedLinkedList<>(List.of(15, 30, 0, -20, -20, 233));
        assertThat(ints, hasSize(6));

        ints.removeFirst();
        assertThat(ints, hasSize(5));
        assertThat(ints, contains(-20, 0, 15, 30, 233));
    }

    @Test
    public void removeLastTest() {
        SortedLinkedList<String> strings = new SortedLinkedList<>(List.of("HH", "652", "0", "a", "Foo"));
        assertThat(strings, hasSize(5));

        strings.removeLast();

        assertThat(strings, hasSize(4));
        assertThat(strings, contains("0", "652", "Foo", "HH"));

        SortedLinkedList<Integer> ints = new SortedLinkedList<>(List.of(15, 30, 0, -20, -20, 233));
        assertThat(ints, hasSize(6));

        ints.removeLast();
        assertThat(ints, hasSize(5));
        assertThat(ints, contains(-20, -20, 0, 15, 30));
    }

    @Test
    public void containsTest() {
        SortedLinkedList<String> strings = new SortedLinkedList<>(List.of("HH", "652", "0", "a", "Foo"));

        assertThat(strings, hasSize(5));
        assertTrue(strings.contains("0"));
        assertTrue(strings.contains("Foo"));


        SortedLinkedList<Integer> ints = new SortedLinkedList<>(List.of(15, 30, 0, -20, -20, 233));

        assertThat(ints, hasSize(6));
        assertTrue(ints.contains(0));
        assertTrue(ints.contains(-20));
    }

}
