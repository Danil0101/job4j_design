package ru.job4j.map;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleMapTest {
    private Map<String, Integer> map;

    @Before
    public void initObjects() {
        map = new SimpleMap<>();
    }

    @Test
    public void whenTwoObjectsAddedThenGetTwoObjects() {
        map.put("one", 1);
        map.put("two", 2);
        assertThat(map.get("one"), is(1));
        assertThat(map.get("two"), is(2));
    }

    @Test
    public void whenCellIsBusyThenFalse() {
        map.put("one", 1);
        assertFalse(map.put("one", 1));
    }

    @Test
    public void whenItemIsNotFoundThenNull() {
        assertNull(map.get("one"));
    }

    @Test
    public void whenObjectSuccessfullyDeletedThenTrue() {
        map.put("one", 1);
        assertTrue(map.remove("one"));
    }

    @Test
    public void whenObjectIsNotDeletedThenFalse() {
        map.put("one", 1);
        assertFalse(map.remove("two"));
    }

    @Test
    public void whenGettingTwoObjectsFromIterator() {
        map.put("one", 1);
        map.put("two", 2);
        Iterator<String> it = map.iterator();
        assertThat(it.next(), is("two"));
        assertThat(it.next(), is("one"));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenWasChangeDuringIteration() {
        map.put("one", 1);
        map.put("two", 2);
        Iterator<String> it = map.iterator();
        map.remove("two");
        it.hasNext();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNoMoreObjects() {
        map.put("one", 1);
        Iterator<String> it = map.iterator();
        it.next();
        it.next();
    }
}