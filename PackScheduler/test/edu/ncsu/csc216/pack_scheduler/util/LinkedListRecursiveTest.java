package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedListRecursive class
 * 
 * @author Chloe Israel
 */
public class LinkedListRecursiveTest {
	
	/**
	 * Tests the constructor
	 */
	@Test
	public void testLinkedListRecursive() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
	}
	
	/**
	 * Tests contains
	 */
	@Test
	public void testContains() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("A"));
		
		// Add
		list.add("A");
		list.add("B");
		assertEquals(2, list.size());
		assertFalse(list.isEmpty());
		
		// Check
		assertTrue(list.contains("A"));
		assertTrue(list.contains("B"));
		assertFalse(list.contains("C"));
		
	}
	
	/**
	 * Tests adding to the end of the list
	 */
	@Test
	public void testAddToEnd() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertEquals(0, list.size());
		
		// Add to the end of an empty list
		assertTrue(list.add("A"));
		assertEquals(1, list.size());
		
		// Add to the end
		assertTrue(list.add("B"));
		assertEquals(2, list.size());
		
		assertTrue(list.contains("A"));
		assertTrue(list.contains("B"));
		
		// Check if correct order
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		
		// Add an existing element
		assertThrows(IllegalArgumentException.class, () -> list.add("B"));
	}
	
	/**
	 * Tests adding at an index
	 */
	@Test
	public void testAddAtIndex() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertEquals(0, list.size());
		
		// Add to the front of the list
		list.add(0, "A");
		assertEquals(1, list.size());
		assertTrue(list.contains("A"));
		
		// Add to the front again
		list.add(0, "B");
		assertEquals(2, list.size());
		assertTrue(list.contains("B"));
		
		// Add to the end
		list.add(2, "C");
		assertEquals(3, list.size());
		assertTrue(list.contains("C"));
		
		// Add to the middle
		list.add(1, "D");
		assertEquals(4, list.size());
		assertTrue(list.contains("D"));
		
		// Check if correct order
		assertEquals("B", list.get(0));
		assertEquals("D", list.get(1));
		assertEquals("A", list.get(2));
		assertEquals("C", list.get(3));
		
		
		// Add an existing element
		assertThrows(IllegalArgumentException.class, () -> list.add(1, "B"));
		
		// Add null element
		assertThrows(NullPointerException.class, () -> list.add(2, null));
		
		// Add outside of bounds
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "E"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(5, "E"));
	}
	
	/**
	 * Tests get (just for bounds checking)
	 */
	@Test
	public void testGet() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add("A");
		assertEquals(1, list.size());
		
		// Add outside of bounds
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
		
	}
	
	/**
	 * Tests removing an element at an index
	 */
	@Test
	public void testRemoveAtIndex() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertEquals(0, list.size());
		
		// Add elements
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		assertEquals(4, list.size());
		
		// Remove from front and check contents
		assertEquals("A", list.remove(0));
		assertEquals(3, list.size());
		
		assertEquals("B", list.get(0));
		assertEquals("C", list.get(1));
		assertEquals("D", list.get(2));
		
		// Remove from middle and check contents
		assertEquals("C", list.remove(1));
		assertEquals(2, list.size());
		
		assertEquals("B", list.get(0));
		assertEquals("D", list.get(1));
		
		// Remove from end and check contents
		assertEquals("D", list.remove(1));
		assertEquals(1, list.size());
		
		assertEquals("B", list.get(0));
		
		// Remove outside of bounds
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
		
	}
	
	/**
	 * Tests removing a given element
	 */
	@Test
	public void testRemoveElement() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertEquals(0, list.size());
		
		// Remove from empty list
		assertFalse(list.remove("A"));
		
		// Add elements
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		assertEquals(4, list.size());
		
		// Remove non-existent element
		assertFalse(list.remove("E"));
		
		// Remove from front and check contents
		assertTrue(list.remove("A"));
		assertEquals(3, list.size());
		
		assertEquals("B", list.get(0));
		assertEquals("C", list.get(1));
		assertEquals("D", list.get(2));
		
		// Remove from middle and check contents
		assertTrue(list.remove("C"));
		assertEquals(2, list.size());
		
		assertEquals("B", list.get(0));
		assertEquals("D", list.get(1));
		
		// Remove from end and check contents
		assertTrue(list.remove("D"));
		assertEquals(1, list.size());
		
		assertEquals("B", list.get(0));
		
		// Remove null element
		assertFalse(list.remove(null));
		
		
	}
	
	/**
	 * Test set
	 */
	@Test
	public void testSet() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		assertEquals(3, list.size());
		
		// Set front
		assertEquals("A", list.set(0, "a"));
		assertEquals("a", list.get(0));
		
		// Set middle
		assertEquals("B", list.set(1, "b"));
		assertEquals("b", list.get(1));
		
		// Set back
		assertEquals("C", list.set(2, "c"));
		assertEquals("c", list.get(2));
		
		// Check
		assertTrue(list.contains("a"));
		assertTrue(list.contains("b"));
		assertTrue(list.contains("c"));
		
		assertFalse(list.contains("A"));
		assertFalse(list.contains("B"));
		assertFalse(list.contains("C"));
	}
	
}
