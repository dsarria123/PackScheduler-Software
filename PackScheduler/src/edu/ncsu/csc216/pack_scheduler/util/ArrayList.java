package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * This class constructs custom array lists and has functions
 * to order elements around, add, remove, and edit them where ever.
 * @author Peyton Herring
 * @author Diego Sarria
 * @author Dee Kandel
 * @param <E> The element contained within the array list
 */
public class ArrayList<E> extends AbstractList<E> {

	/** Constant for max array size */
	private static final int INIT_SIZE = 10;
	/** The array of items */
	private E[] list;
	/** The current size the array */
	private int size;
	/** Limit for list if size is increased */
	private int limit = 10;
	
	/**
	 * Creates the array list and casts it to type E
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[INIT_SIZE];
	}
	
	/**
	 * Returns the current size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Adds an element to the list
	 * @param idx The index to add at
	 * @param element The element to insert into the list
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element is a duplicate
	 * @throws IndexOutOfBoundsException if idx is not within the list bounds
	 */
	public void add(int idx, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < size(); i++) {
			if (list[i] != null && list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (size() == limit) {
			growArray();
		}
		
		for (int i = size(); i > idx; i--) {
			list[i + 1] = list[i];
			list[i] = list[i - 1];
		}
		size++;
		list[idx] = element;
		
	}
	
	/**
	 * Removes an element from the list by index
	 * @param idx The index of the element to remove
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if idx is not within the list bounds
	 */
	public E remove(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E remove = list[idx];
		for (int i = idx; i < size(); i++) {
			list[i] = list[i + 1];
		}
		list[size()] = null;
		size--;
		return remove;
	}
	
	/**
	 * Sets an element to the list
	 * @param idx The index to set
	 * @param element The element to insert into the list
	 * @return the old element before it was changed
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element is a duplicate
	 * @throws IndexOutOfBoundsException if idx is not within the list bounds
	 */
	public E set(int idx, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < size(); i++) {
			if (list[i] != null && list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E old = list[idx];
		list[idx] = element;
		return old;
	}
	
	/**
	 * Gets the element at a specific index
	 * @param idx The index to retrieve the element from
	 * @throws IndexOutOfBoundsException if idx is not within the list bounds
	 */
	@Override
	public E get(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return list[idx];
	}
	
	/**
	 * Grows the array when size limit is reached by a factor of 2
	 */
	private void growArray() {
		@SuppressWarnings("unchecked")
		E[] newList = (E[]) new Object[size() * 2];
		for (int i = 0; i < size(); i++) {
			newList[i] = list[i];
		}
		limit = limit * 2;
		list = newList;
	}

}
