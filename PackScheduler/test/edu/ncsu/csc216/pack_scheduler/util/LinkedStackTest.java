package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

/**
 * Tests LinkedStack
 *
 */
public class LinkedStackTest {
	
	/**
	 * Tests the constructor
	 */
	@Test
	public void testLinkedStack() {
		LinkedStack<String> q = new LinkedStack<String>(3);
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
	}

	/**
	 * Tests push
	 */
	@Test
	public void testPush() {
		LinkedStack<String> q = new LinkedStack<String>(3);
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
		
		q.push("A");
		assertEquals(1, q.size());
		assertFalse(q.isEmpty());
		
		q.push("B");
		q.push("C");
		assertEquals(3, q.size());
		assertFalse(q.isEmpty());
		
		// Add to full queue
        assertThrows(IllegalArgumentException.class, () -> q.push("D"));
	}

	/**
	 *  Tests pop
	 */
	@Test
	public void testPop() {
		LinkedStack<String> q = new LinkedStack<String>(3);
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
		
		// Remove from empty queue
        assertThrows(EmptyStackException.class, () -> q.pop());
        
		// Add
		q.push("B");
		q.push("A");
		q.push("C");
		assertEquals(3, q.size());
		assertFalse(q.isEmpty());
		
		// Remove
		assertEquals("C", q.pop());
		assertEquals("A", q.pop());
		assertEquals("B", q.pop());
		
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
	}

	/**
	 * Tests setCapacity
	 */
	@Test
	public void testSetCapacity() {
		LinkedStack<String> q = new LinkedStack<String>(3);
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
		
		q.setCapacity(0);
		
		// Cannot add
        assertThrows(IllegalArgumentException.class, () -> q.push("D"));
        
        q.setCapacity(1);
        
        // Negative capacity
        assertThrows(IllegalArgumentException.class, () -> q.setCapacity(-1));
        
        q.push("D");
		assertEquals(1, q.size());
		assertFalse(q.isEmpty());
		
		// Set to less than size
        assertThrows(IllegalArgumentException.class, () -> q.setCapacity(0));
	}

}
