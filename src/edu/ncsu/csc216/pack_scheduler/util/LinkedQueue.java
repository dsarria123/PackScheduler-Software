package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Creates a Queue of elements utilizing a LinkedAbstractList
 * Works with LinkedAbstractList for adding, removing, getting the size, and setting the capacity functionality
 * @param <E> A generic type Object
 * 
 * @author Chloe Israel
 */
public class LinkedQueue<E> implements Queue<E> {

	/** The list of elements */
	private LinkedAbstractList<E> list;
	
	/** The Queue's capacity */
	private int capacity;

	/**
	 * Constructs the LinkedQueue
	 * Creates the LinkedAbstractList of elements and sets the capacity of the Queue
	 * @param capacity the Queue's capacity
	 */
	public LinkedQueue(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
		
	}
	
	/**
	 * Adds an element to the back of the Queue
	 * @param element the element to add
	 * @throws IllegalArgumentException if the Queue capacity has been reached
	 */
	@Override
	public void enqueue(E element) {
		if (this.size() == capacity) {
			throw new IllegalArgumentException();
		}
		
		if (this.size() == 0) {
			list.add(0, element);
		} else {
			list.add(this.size(), element);
		}
		
	}
	
	/**
	 * Removes an element from the front of the Queue
	 * @return the removed element
	 * @throws NoSuchElementException if the Queue is empty
	 */
	@Override
	public E dequeue() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return list.remove(0);
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
