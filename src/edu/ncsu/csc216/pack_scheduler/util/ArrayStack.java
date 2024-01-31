package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Creates a Stack of elements utilizing an ArrayList
 * Works with ArrayList for adding, removing, and getting the size functionality
 * Keeps track of the Stack's capacity
 * @param <E> the element being implemented
 * 
 * @author Connor
 */
public class ArrayStack<E> implements Stack<E> {
	
	/** The list of elements */
	private ArrayList<E> list;
	/** Limit for list if size is increased */
	private int capacity;
	
	/**
	 * The Array Stack constructor
	 * @param capacity the capacity to be set
	 */
	public ArrayStack(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Adds elements to the top of the list
	 * @param element the element being put into the stack
	 * @throws IllegalArgumentException if there is no room in the stack (capacity has been reached)
	 */
	@Override
	public void push(E element) {
		if (list.size() == capacity) {
			throw new IllegalArgumentException();
		}
		list.add(list.size(), element);
	}

	/**
	 * Removes and returns the element at the top of the stack
	 * @return the element being popped
	 * @throws EmptyStackException if the stack is empty
	 */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list.remove(list.size() - 1);
	}

	/**
	 * Returns true if the stack is empty
	 * @return true if the stack is empty
	 */
	@Override
	public boolean isEmpty() {
		
		boolean isEmpty = false;
		
		if (list.size() == 0) {
			isEmpty = true;
		}
		return isEmpty;
		
	}

	/**
	 * Returns the number of elements in the stack
	 * @return the number of elements in the stack
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the stack’s capacity
	 * @param capacity the capacity of the stack
	 * @throws IllegalArgumentException if the capacity is less than the number of elements in the stack or negative
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

}
