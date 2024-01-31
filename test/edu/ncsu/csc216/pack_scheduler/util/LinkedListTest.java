package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ListIterator;

import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedList Class
 * 
 * @author Chloe Israel
 */
public class LinkedListTest {

	/**
	 * Tests the Constructor
	 */
	@Test
	public void testLinkedList() {
		LinkedList<String> list = new LinkedList<String>();
		assertEquals(0, list.size());
	}
	
	/**
	 * Tests add
	 */
	@Test
	public void testAdd() {
		LinkedList<String> list = new LinkedList<String>();
		assertEquals(0, list.size());
		
		// Add to front of empty list
		list.add(0, "B");
		assertEquals(1, list.size());
		assertEquals("B", list.get(0));
		
		// Add to front
		list.add(0, "A");
		assertEquals(2, list.size());
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		
		// Add to middle
		list.add(1, "C");
		assertEquals(3, list.size());
		assertEquals("A", list.get(0));
		assertEquals("C", list.get(1));
		assertEquals("B", list.get(2));
		
		// Add to end
		list.add(3, "D");
		assertEquals(4, list.size());
		assertEquals("A", list.get(0));
		assertEquals("C", list.get(1));
		assertEquals("B", list.get(2));
		assertEquals("D", list.get(3));
		
		// Add duplicate
		assertThrows(IllegalArgumentException.class, () -> list.add(4, "D"));
		
		LinkedList<String> list2 = new LinkedList<String>();
		assertEquals(0, list2.size());
		
		list2.add("A");
		assertEquals(1, list2.size());
		assertEquals("A", list2.get(0));
				
	}
	
	/**
	 * Tests set
	 */
	@Test
	public void testSet() {
		LinkedList<String> list = new LinkedList<String>();
		assertEquals(0, list.size());
		
		// Add
		list.add(0, "Connor");
		assertEquals(1, list.size());
		assertEquals("Connor", list.get(0));
		
		// Set values
		list.set(0, "Ferlito");
		assertEquals(1, list.size());
		assertEquals("Ferlito", list.get(0));
		
		list.set(0, "Martin");
		assertEquals(1, list.size());
		assertEquals("Martin", list.get(0));
		
		list.set(0, "Nicole");
		assertEquals(1, list.size());
		assertEquals("Nicole", list.get(0));
		
		list.set(0, "Berkin");
		assertEquals(1, list.size());
		assertEquals("Berkin", list.get(0));
		
	}
	
	/**
	 * Tests remove
	 */
	@Test
	public void testRemove() {
		LinkedList<String> list = new LinkedList<String>();
		assertEquals(0, list.size());
		
		list.add(0, "A");
		list.add(1, "B");
		list.add(2, "C");
		list.add(3, "D");
		
		ListIterator<String> listI = list.listIterator();
		listI.next();
		listI.remove();
		assertEquals(3, list.size());
	
	}
}
