package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests LinkedQueue
 * 
 * @author Chloe Israel
 */
public class LinkedQueueTest {
	
	/**
	 * Tests the constructor
	 */
	@Test
	public void testLinkedQueue() {
		LinkedQueue<String> q = new LinkedQueue<String>(3);
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
	}

	/**
	 * Tests enqueue
	 */
	@Test
	public void testEnqueue() {
		LinkedQueue<String> q = new LinkedQueue<String>(3);
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
		
		q.enqueue("A");
		assertEquals(1, q.size());
		assertFalse(q.isEmpty());
		
		q.enqueue("B");
		q.enqueue("C");
		assertEquals(3, q.size());
		assertFalse(q.isEmpty());
		
		// Add to full queue
        assertThrows(IllegalArgumentException.class, () -> q.enqueue("D"));
	}

	/**
	 *  Tests dequeue
	 */
	@Test
	public void testDequeue() {
		LinkedQueue<String> q = new LinkedQueue<String>(3);
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
		
		// Remove from empty queue
        assertThrows(NoSuchElementException.class, () -> q.dequeue());
        
		// Add
		q.enqueue("B");
		q.enqueue("A");
		q.enqueue("C");
		assertEquals(3, q.size());
		assertFalse(q.isEmpty());
		
		// Remove
		assertEquals("B", q.dequeue());
		assertEquals("A", q.dequeue());
		assertEquals("C", q.dequeue());
		
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
	}

	/**
	 * Tests setCapacity
	 */
	@Test
	public void testSetCapacity() {
		LinkedQueue<String> q = new LinkedQueue<String>(3);
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
		
		q.setCapacity(0);
		
		// Cannot add
        assertThrows(IllegalArgumentException.class, () -> q.enqueue("D"));
        
        q.setCapacity(1);
        
        // Negative capacity
        assertThrows(IllegalArgumentException.class, () -> q.setCapacity(-1));
        
        q.enqueue("D");
		assertEquals(1, q.size());
		assertFalse(q.isEmpty());
		
		// Set to less than size
        assertThrows(IllegalArgumentException.class, () -> q.setCapacity(0));
	}

}
