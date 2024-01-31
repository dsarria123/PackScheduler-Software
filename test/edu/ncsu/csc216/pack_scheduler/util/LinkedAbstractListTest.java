package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * Testing class for linked abstract lists. This class tests all the functionality of linked abstract lists.
 * @author Diego Sarria
 */
public class LinkedAbstractListTest {

    /**
     * Tests the list function of adding elements.
     */
    @Test
    public void addTest() {

        LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);

        assertEquals(0, list.size());

        // Adding at index 0
        list.add(0, "1");
        assertEquals(1, list.size());
        assertEquals("1", list.get(0));
        
        // Shift
        list.add(0, "4");
        assertEquals(2, list.size());
        assertEquals("4", list.get(0));
        assertEquals("1", list.get(1));
        
        // Adding at index 1
        list.add(1, "2");
        assertEquals(3, list.size());
        assertEquals("4", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals("1", list.get(2));

        // Shift
        list.add(1, "3");
        assertEquals(4, list.size());
        assertEquals("4", list.get(0));
        assertEquals("3", list.get(1));
        assertEquals("2", list.get(2));
        assertEquals("1", list.get(3));

        // Null value test
        try {
            list.add(2, null);
            fail();
        } catch (NullPointerException e) {
            assertEquals(4, list.size());
        }

        // Testing duplicate
        try {
            list.add(2, "3");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(4, list.size());
        }

        // Index out of bounds test (negative index)
        try {
            list.add(-1, "5");
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(4, list.size());
        }

        // Index out of bounds test (index > size)
        try {
            list.add(11, "5");
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(4, list.size());
        }

        // Test for capacity
        for (int i = 5; i <= 10; i++) {
            list.add(list.size(), Integer.toString(i));
        }

        // Adding beyond capacity
        try {
            list.add(11, "11");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(10, list.size());
        }

    }

    /**
     * Tests the list function of removing elements.
     */
    @Test
    public void removeTest() {

        LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);

        // Add the elements
        list.add(0, "1");
        list.add(1, "2");
        list.add(2, "3");

        // Remove and make sure it shifts
        String removed = list.remove(1);
        assertEquals("2", removed);
        assertEquals(2, list.size());
        assertEquals("3", list.get(1));

        // Index test (negative index)
        try {
            list.remove(-1);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(2, list.size());
        }

        // Index test (index > size)
        try {
            list.remove(3);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(2, list.size());
        }

    }

    /**
     * Tests the list function of setting elements.
     */
    @Test
    public void setTest() {
        LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
        list.add(0, "1");
        list.add(1, "2");

        // Set at index 1
        String original = list.set(1, "3");
        assertEquals("2", original);
        assertEquals("3", list.get(1));

        // Test NullPointerException
        try {
            list.set(1, null);
            fail();
        } catch (NullPointerException e) {
            assertEquals("3", list.get(1));
        }

        // Test IllegalArgumentException
        try {
            list.set(1, "1");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("3", list.get(1));
        }

        // Test Index (negative index)
        try {
            list.set(-1, "4");
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("3", list.get(1));
        }

        // Test IndexOut (index >= size)
        try {
            list.set(3, "4");
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("3", list.get(1));
        }
    }

    /**
     * Test get method
     */
    @Test
    public void getTest() {

        LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
        list.add(0, "1");
        assertEquals("1", list.get(0));
        list.add(1, "2");
        assertEquals("2", list.get(1));

        // Test IndexOut (negative index)
        try {
            list.get(-1);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("1", list.get(0));
        }

        // Test IndexOut (index > size)
        try {
            list.get(7);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("1", list.get(0));
        }
    }
    
    /**
     * Set capacity Test yes
     */
    @Test
    public void setCapacityTest() {
    	LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
        assertThrows(IllegalArgumentException.class, () -> list.setCapacity(-1));
        
    	list.setCapacity(3);
    	list.add(0, "1");
        list.add(1, "2");
        list.add(2, "3");

        assertThrows(IllegalArgumentException.class, () -> list.setCapacity(list.size() - 1));
        assertThrows(IllegalArgumentException.class, () -> list.add(3, "4"));
        

       
}
    
}
