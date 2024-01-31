package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Represents a linked abstract list which holds list nodes to serve the course roll function
 * Can add, remove, set, and get data types
 * Keeps track of the list capacity
 * @param <E> The data type used in the list
 * @author Diego Sarria
 */
public class LinkedAbstractList<E> extends java.util.AbstractList<E> {
	
	/**
	 * Private subclass for list nodes to be used within linked lists
	 * @author Diego Sarria
	 */
	private class ListNode {
		/** Data for a list node */
		private E data;
		/** Next node in the list */
		private ListNode next;
		
		
		
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
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
	/** First node in the list */
	private ListNode front;
	/** Last node in the list */
	private ListNode back;
	/** Number of nodes in the list */
	private int size;
	/** Maximum nodes possible for a list */
	private int capacity;
	
	/**
	 * Constructor for linked abstract lists
	 * @param capacity the max number of nodes for the list
	 * @throws IllegalArgumentException if capacity is less than 0
	 */
	public LinkedAbstractList(int capacity) {
		 if (capacity < 0) {
	            throw new IllegalArgumentException();
	        }
	        this.front = new ListNode(null);
	        this.back = new ListNode(null); // Initialize back as null
	        this.size = 0;
	        this.capacity = capacity;
	}
	
	/**
	 * Gets the size of the linked list
	 * @return size of the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Adds an element to the list
	 * @param idx The index to add at
	 * @param element The element to insert into the list
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element is a duplicate, or capacity is reached
	 * @throws IndexOutOfBoundsException if idx is not within the list bounds
	 */
	@Override
	public void add(int idx, E element) {
	   
	    if (this.size >= this.capacity) {
	        throw new IllegalArgumentException("Capacity exceeded");
	    }
	    
	    if (element == null) {
	        throw new NullPointerException();
	    }
	    
	    if (idx < 0 || idx > size) {
	        throw new IndexOutOfBoundsException();
	    }

	    // Check for duplicates across the entire list
	    ListNode current = front;
	    while (current != null) {
	        if (element.equals(current.data)) {
	            throw new IllegalArgumentException("Duplicate element");
	        }
	        current = current.next;
	    }

	    // Add element
	    if (idx == 0) {
	        front = new ListNode(element, front);
	        if (size == 0) {
	            back = front;
	        }
	    } else {
	        current = front;
	        
	        for (int i = 0; i < idx - 1; i++) {
	            current = current.next;
	        }
	        
	        current.next = new ListNode(element, current.next);
	        
	        if (idx == size) {
	            back = new ListNode(current.next.data);
	        }
	    }
	    
	    size++;
	}

	
	/**
	 * Removes a node from the linked list
	 * @param idx the index of the node to remove
	 * @return the removed nodes data
	 * @throws IndexOutOfBoundsException if idx is outside the size range
	 */
	@Override
    public E remove(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException();
        }

        E data;
        if (idx == 0) {
            data = front.data;
            front = front.next;
            if (size == 1) {
                back = null;
            }
        } else {
            ListNode current = front;
            for (int i = 0; i < idx - 1; i++) {
                current = current.next;
            }
            data = current.next.data;
            current.next = current.next.next;
            if (idx == size - 1) {
                back = current;
            }
        }
        size--;
        return data;
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
	@Override
	public E set(int idx, E element) {
		ListNode current = front;
		if (this.size >= this.capacity) {
			throw new IllegalArgumentException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		while (current != null) {
			if (current.data == element) {
				throw new IllegalArgumentException();
			}
			current = current.next;
		}
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		current = front;
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		E remove = current.data;
		current.data = element;
		
		if (idx == size - 1) {
			back = new ListNode(element);
		}
		
		return remove;
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
		
		if (idx == size - 1) {
			return back.data;
		}
		
		ListNode current = front;
		int count = 0;
		while (current != null) {
			if (count == idx) {
				return current.data;
			}
			count++;
			current = current.next;
		}
		
		return null;
	}
	
	/**
	 * Sets capacity
	 * @param capacity capacity
	 */
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < this.size()) {
			throw new IllegalArgumentException();
		}
		
		this.capacity = capacity;
	}
}
