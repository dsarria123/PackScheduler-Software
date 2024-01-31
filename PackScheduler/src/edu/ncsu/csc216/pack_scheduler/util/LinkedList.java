package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Represents a LinkedList, utilizing a LinkedListIterator to move through the list
 * Extends AbstractSequentialList
 * Can add, set, and remove from the list
 * @author Chloe Israel
 * @author Connor Ferlito
 * @param <E> a generic object type
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	
	/** First node in the list */
	private ListNode front;
	/** Last node in the list */
	private ListNode back;
	/** Number of nodes in the list */
	private int size;
	
	/**
	 * The Linked List constructor
	 */
	public LinkedList() {
		front = new ListNode(null);
        back = new ListNode(null);
        front.next = back;
        back.prev = front;
		size = 0;
	}
	
	/**
	 * The size method for the Linked List
	 * @return size , the size of the linked list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * The List Iterator method
	 * @param index the index that the list iterator is going to be inserted at
	 * @return ListIterator the list iterator to be constructed
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		LinkedListIterator list = new LinkedListIterator(index);
		return list;
	}
	
	
	/**
	 * Adds the given element to the given index
	 * Overrides add method from parent to check for a duplicate element
	 * @param index the index to add to
	 * @param element the element to add
	 * @throws IllegalArgumentException if the given element is duplicate of an existing one
	 */
	@Override
	public void add(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		super.add(index, element);
	}

	

	/**
	 * Sets the given element to the given index
	 * Overrides set method from parent to check for a duplicate element
	 * @param index the index to set to
	 * @param element the element to set
	 * @throws IllegalArgumentException if the given element is duplicate of an existing one
	 */
	@Override
	public E set(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		
		return super.set(index, element);
	}

	/**
	 * The private inner class List Node, holding the data in a single node
	 * Keeps track of the next and previous node
     * @author Connor Ferlito
	 */
	private class ListNode { 
		
		/** Data for a list node */
		private E data;
		/** Next node in the list */
		private ListNode next;
		/** Previous node in the list */
		private ListNode prev;
		
		/**
		 * Basic constructor for a list node without a next node
		 * @param data data for the node
		 */
		public ListNode(E data) {
			this.data = data;
		}
		
		/**
		 * Constructor for a list node connected to another node
		 * @param data the data for the node
		 * @param next the next node for it to be connected to
		 * @param prev the previous node
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	/**
	 * The private inner class Linked list iterator
	 * Implements ListIterator to move through a list
	 * Keeps track of the next and previous node, next and previous index, and last retrieved node
	 * @author Connor Ferlito
	 * @author Chloe Israel
	 */
	private class LinkedListIterator implements ListIterator<E> {
		
		/** Next node in the list */
		private ListNode next;
		/** Previous node in the list */
		private ListNode previous;
		/** Previous node index */
		private int previousIndex;
		/** next node index */
		private int nextIndex;
		/** the last retrieved node in the list */
		private ListNode lastRetrieved;
		
		/**
		 * The constructor for the Linked list iterator
		 * @param index the index to position the iterator
		 */
		public LinkedListIterator(int index) { 
			   if (index < 0 || index > size) {
				   throw new IndexOutOfBoundsException();
			   }  
			   
//			   for (int i = 0; i < index; i++) {
//				   previous = next;
//				   next = previous.next;
//			   }
//			   
//			   nextIndex = index;
//			   previousIndex = index - 1;
//			   lastRetrieved = null;
			   ListNode position = front.next;
			   for (int i = 0; i < index; i++) {
				   position = position.next;
			   }
			   
			   next = position;
			   previous = position.prev;
			   nextIndex = index;
			   previousIndex = index - 1;
			   lastRetrieved = null;
			   
			}

		/**
		 * Gets whether the List has a next data point
		 * @return true if there is a next, false if not
		 */
		@Override
		public boolean hasNext() {
			boolean hasNext = true;
			
			if (this.next == null) {
				hasNext = false;
			}
			return hasNext;
		}

		/**
		 * Gets the next element in the Linked List
		 * @return E  the next element
		 * @throws NoSuchElementException if there isn't a next
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			E nextE = next.data;
			
			lastRetrieved = next;
			previous = next;
			next = next.next;
			nextIndex++;
			previousIndex++;
			
			return nextE;
		}

		/**
		 * Checks whether the list has a previous list node
		 * @return true if it does have a previous, false if not
		 */
		@Override
		public boolean hasPrevious() {
			boolean hasPrev = true;
			
			if (this.previous == null) {
				hasPrev = false;
			}
			return hasPrev;
			
		}

		/**
		 * Gets the list's previous element
		 * @return E the previous element
		 * @throws NoSuchElementException if there isn't a previous
		 */
		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			
			E prevE = previous.data;
			
			lastRetrieved = previous;
			next = previous;
			previous = previous.prev;
			previousIndex--;
			nextIndex--;
			
			return prevE;
		}

		/**
		 * Gets the index of the next element
		 * @return the next index
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		/**
		 * Gets the index of the previous element
		 * @return the previous index 
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}

		/**
		 * Removes the lastRetrieved element from the iterator
		 * @throws IllegalStateException if lastRetrieved is null
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
		        throw new IllegalStateException();
		    }
		    
		    lastRetrieved.prev.next = lastRetrieved.next;
		    lastRetrieved.next.prev = lastRetrieved.prev;

		    size--;
		    previousIndex--;
		    nextIndex--;	
		}

		/**
		 * Sets the last retrieved element
		 * @param e the element to set
		 * @throws NullPointerException if the element to set is null
		 * @throws IllegalStateException if the lastRetrieved element is null
		 */
		@Override
		public void set(E e) {
			 	if (e == null) {
			        throw new NullPointerException();
			    }

			    if (lastRetrieved == null) {
			        throw new IllegalStateException();
			    }

			    ListNode replaceNode = new ListNode(e, lastRetrieved.prev, lastRetrieved.next);
			    lastRetrieved.next.prev = replaceNode;
			    lastRetrieved.prev.next = replaceNode;
		}

		/**
		 * Adds a new element to the iterator
		 * @param e the element to add added 
		 * @throws NullPointerException if the element to add is null
		 */
		@Override
		public void add(E e) {
			if (e == null) {
		        throw new NullPointerException();
		    }

			ListNode newNode = new ListNode(e, previous, next);
			
			if (size == 0) {
			    front.next = newNode;
	    		back.prev = newNode;
	    		
			} else {
				previous.next = newNode;
				next.prev = newNode;
			}
		    
		    lastRetrieved = null;
		    size++;
		    previousIndex++;
		    nextIndex++;
		}
		
	}
}
