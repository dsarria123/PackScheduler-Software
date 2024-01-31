package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;
/**
 * Interface representing a Stack of Objects
 * Elements can only be added and removed to the "top" of the Stack
 * 
 * @author Chloe Israel
 * @param <E> the element being evaluated for
 * 
 */
public interface Stack<E> {

	/**
	 * Adds elements to the top of the list
	 * @param element the element being put into the stack
	 * @throws IllegalArgumentException if there is no room in the stack (capacity has been reached)
	 */
	void push(E element);
	
	/**
	 * Removes and returns the element at the top of the stack
	 * @return the element being popped
	 * @throws EmptyStackException if the stack is empty
	 */
	E pop();
	
	/**
	 * Returns true if the stack is empty
	 * @return true if the stack is empty
	 */
	boolean isEmpty();
	
	/**
	 * Returns the number of elements in the stack
	 * @return the number of elements in the stack
	 */
	int size();
	
	/**
	 * Sets the stack’s capacity
	 * @param capacity the capacity of the stack
	 * @throws IllegalArgumentException if the capacity is less than the number of elements in the stack or negative
	 */
	void setCapacity(int capacity);
}
