package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Interface that represents a Queue of objects with a certain capacity.
 * Elements can only be added to the back of the Queue and removed from the front
 * 
 * @author Chloe Israel
 * @param <E> a generic Object
 */
public interface Queue<E> {
	/**
	 * Adds an element to the back of the Queue
	 * @param element the element to add
	 * @throws IllegalArgumentException if the Queue capacity has been reached
	 */
	void enqueue(E element);
	
	/**
	 * Removes an element from the front of the Queue
	 * @return the removed element
	 * @throws NoSuchElementException if the Queue is empty
	 */
	E dequeue();
	
	/**
	 * Checks if the Queue is empty
	 * @return true if it is empty, false if not
	 */
	boolean isEmpty();
	
	/**
	 * Gets the number of elements in the Queue
	 * @return the number of elements in the Queue
	 */
	int size();
	
	/**
	 * Sets the capacity of the Queue
	 * @param capacity the Queue's capacity to set
	 * @throws IllegalArgumentException if capacity is negative or less than the Queue size
	 */
	void setCapacity(int capacity);
		
	
}
