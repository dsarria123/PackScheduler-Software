package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Represents a LinkedList of elements utilizing recursive methods
 * Can add, get, remove, and set elements and keeps track of the list size
 * @param <E> the generic object type
 * 
 * @author Chloe Israel
 * @author Connor Ferlito
 */
public class LinkedListRecursive<E> {
	
	/** Reference to the first node in the list */
	private ListNode front;
	
	/** Gives the number of nodes in the list */
	private int size;
	
	/**
	 * Constructs the LinkedListRecursive
	 * Sets front to null and size to zero
	 */
	public LinkedListRecursive() {
		front = null;
		size = 0;
	}
	
	/**
	 * Gets the size of the list
	 * @return integer, the size of the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Gets whether the list is empty or not
	 * @return boolean, true if the list is empty or false if not
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Gets whether the list contains a given element
	 * @param element the element to compare
	 * @return boolean, true if it does contain the given element or false if not
	 */
	public boolean contains(E element) {
		boolean hasEle = true; 
		
		if (size == 0) {
			hasEle = false;
			return hasEle;
		}
		
		return front.contains(element);
	}

	
	/**
	 * Adds the given element to the end of the list
	 * @param element the element to add
	 * @return boolean, true if the given element is added, false if not
	 * @throws IllegalArgumentException if the given element is already in the list
	 */
	public boolean add(E element) {
		
		
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		
		if (size == 0) {
			front = new ListNode(element, front);
			size++;
			return true;
		} else {
			return front.add(element);
		}
	}
	
	/**
	 * Adds the given element to the given index
	 * @param index the index to insert the element
	 * @param element the element to add
	 * @throws IllegalArgumentException if the given element is already in the list
	 * @throws IndexOutOfBoundsException if the given index is out of bounds for the list
	 * @throws NullPointerException if the given element is null
	 */
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		} 
		
		// Add to the front of the list
		if (index == 0) {
			front = new ListNode(element, front);
			size++;
		} else {
			front.add(index, element); // All other cases
		}
	}
	
	/**
	 * Gets the element from the given index
	 * @param index the index of the element to get
	 * @return E, the element at the given index
	 * @throws IndexOutOfBoundsException if the given index is out of bounds for the list
	 */
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		return front.get(index);
	}
	
	/**
	 * Removes an element from the given index
	 * @param index the index of the element to remove
	 * @return E, the removed element
	 * @throws IndexOutOfBoundsException if the given index is out of bounds for the list 
	 */
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (index == 0) {
			E removedEl = front.data;
			
			front = front.next;
			size--;
			return removedEl;
		} else { 
			return front.remove(index); 
		}
	}
	
	/**
	 * Removes the given element from the list
	 * @param element the element to remove
	 * @return boolean, true if the given element is removed or false if not
	 * @throws NullPointerException if the given element is null
	 */
	public boolean remove(E element) {
		if (element == null) {
			return false;
		}
				
		if (!this.contains(element)) {
			return false;
		}
		
		if (size != 0) {
			if (element == front.data) {
				front = front.next;
				size--;
				return true;
			} else {
				return front.remove(element);
			}
		} else {
			return false;
		}
		

	}
	
	/**
	 * Sets the given element to the given index
	 * @param index the index to set the element to
	 * @param element the element to set
	 * @return E, the replaced element
	 * @throws IndexOutOfBoundsException if the given index is out of bounds for the list
	 */
	public E set(int index, E element) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		return front.set(index, element);
	}
	
	/**
	 * Private subclass representing the list nodes used within the linked lists
	 * @author Chloe Israel
	 */
	private class ListNode {
		/** Data for a list node */
		private E data;
		/** Reference to the next node in the list */
		private ListNode next;
		
		/**
		 * Constructor for the list node 
		 * Takes in data and a reference to the next list node
		 * @param data the data for the node
		 * @param next the next node for it to be connected to
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
		
		/**
		 * Gets whether to list contains a given element
		 * Private counterpart of LinkedListRecursive.contains(E)
		 * @param element the element to compare
		 * @return boolean, true if it does contain the given element or false if not
		 */
		private boolean contains(E element) {
			boolean doesContain = true;
			
			if (this.data != element && this.next == null) {
				doesContain = false;
				return doesContain;
			}
			
			if (this.data == element) {
				doesContain = true;
				return doesContain;
			}
						
			return next.contains(element);
		}
		
		/**
		 * Adds the given element to the end of the list
		 * Private counterpart of LinkedListRecursive.add(E)
		 * @param element the element to add
		 * @return boolean, true if the given element is added, false if not
		 */
		private boolean add(E element) {
			if (next == null) {
				next = new ListNode(element, next);
				size++;
				return true;
			}
			
			return next.add(element);
		}
		
		/**
		 * Adds the given element to the given index
		 * Private counterpart of LinkedListRecursive.add(int, E)
		 * @param index the index to insert the element
		 * @param element the element to add
		 */
		private void add(int index, E element) {
			if (index == 1) { // Stop before the given index
				next = new ListNode(element, next); // Add to next, which should be at the given index
				size++;
			} else {
				int newIndex = index - 1; // Decrement index and try again
				next.add(newIndex, element);
			}
			
		}
		
		/**
		 * Gets the element from the given index
		 * Private counterpart of LinkedListRecursive.get(int)
		 * @param index the index of the element to get
		 * @return E, the element at the given index
		 */
		private E get(int index) {
			if (index == 0) { // Stop at given index and return the data held there
				return this.data; 
			}
			
			int newIndex = index - 1; // Decrement index and try again
			return next.get(newIndex);
		}
		
		/**
		 * Removes an element from the given index
		 * Private counterpart of LinkedListRecursive.remove(int)
		 * @param index the index of the element to remove
		 * @return E, the removed element
		 */
		private E remove(int index) {
			 if (index == 1) { // Stop before the given index
				 E removedEl = next.data; // Remove from next, which should be at the given index
				 next = next.next; // Set next to the node after it
				 size--;
				 return removedEl;
			 }
			 
			 int newIndex = index - 1; // Decrement index and try again
			 return next.remove(newIndex);
		}
		
		/**
		 * Removes the given element from the list
		 * Private counterpart of LinkedListRecursive.remove(E)
		 * @param element the element to remove
		 * @return boolean, true if the given element is removed or false if not
		 */
		private boolean remove(E element) {
			if (element == next.data) {
				next = next.next; // Set next to the node after it
				size--;
				return true;
			}
			
			return next.remove(element);
		}
		
		/**
		 * Sets the given element to the given index
		 * Private counterpart of LinkedListRecursive.set(int, E)
		 * @param index the index to set the element to
		 * @param element the element to set
		 * @return E, the replaced element
		 */
		private E set(int index, E element) {
			if (index == 0) { // Stop at given index
				E replacedEl = this.data;
				this.data = element;
				return replacedEl;
			}
			
			int newIndex = index - 1;
			return next.set(newIndex, element);
		}
		

	}
}