package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Creates a Stack of elements utilizing a LinkedAbstractList
 * Works with LinkedAbstractList for adding, removing, getting the size, and setting the capacity functionality
 * @param <E> A generic type Object
 * 
 * @author Connor
 */
public class LinkedStack<E> implements Stack<E> {

	/** The list of elements */
	private LinkedAbstractList<E> list;
	
	/** The stacks capacity */
	private int capacity;

	/**
	 * Constructs the LinkedStack
	 * Creates the LinkedAbstractList of elements and sets the capacity of the Stack
	 * @param capacity the stacks capacity
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
		
	}
	
	/**
	 * Adds an element to the front of the Stack
	 * @param element the element to add
	 * @throws IllegalArgumentException if the Stack capacity has been reached
	 */
	@Override
	public void push (E element) {
		if (this.size() == capacity) {
			throw new IllegalArgumentException();
		}
		list.add(this.size(), element);
	}
	
	/**
	 * Removes an element from the front of the stack
	 * @return the removed element
	 * @throws EmptyStackException if the Queue is empty
	 */
	@Override
	public E pop() {
		if (this.isEmpty()) {
			throw new EmptyStackException();
		}
		
		return list.remove(list.size() - 1);
	}

	/**
	 * Checks if the Queue is empty
	 * @return true if it is empty, false if not
	 */
	@Override
	public boolean isEmpty() {
		boolean isEmpty = false;
		
		if (this.size() == 0) {
			isEmpty = true;
		}
		return isEmpty;
	}
	
	/**
	 * Gets the number of elements in the Queue
	 * @return the number of elements in the Queue
	 */
	@Override
	public int size() {
		return list.size();
	}
	
	/**
	 * Sets the capacity of the Queue
	 * @param capacity the Queue's capacity to set
	 * @throws IllegalArgumentException if capacity is negative or less than the Queue size
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < this.size()) {
			throw new IllegalArgumentException();
		}
		
		this.capacity = capacity;
		list.setCapacity(capacity);
		
	}

}
