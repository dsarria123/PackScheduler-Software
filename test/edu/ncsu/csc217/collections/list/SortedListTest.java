package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for sortedList class
 * @author Peyton Herring
 * @author Arnva Ganguly
 * @author Aaron Green
 */
public class SortedListTest {

	/**
	 * Tests the list size can grow larger than original size
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		
		list.add("apple");
		list.add("banana");
		list.add("cat");
		list.add("dog");
		list.add("elephant");
		list.add("fish");
		list.add("whale");
		list.add("monkey");
		list.add("ox");
		list.add("zebra");
		list.add("shark");
		//Remember the list's initial capacity is 10
		assertEquals(11, list.size());
		
	}

	/**
	 * Tests the list can add elements and they are sorted
	 * Can not add null/duplicate elements
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		
		//front of the list
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		
		//end of the list
		list.add("zebra");
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("zebra", list.get(2));
		
		//middle of the list
		list.add("dog");
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("dog", list.get(2));
		assertEquals("zebra", list.get(3));
		
		
		Exception e = assertThrows(NullPointerException.class, () -> list.add(null));
		assertEquals(NullPointerException.class, e.getClass());
		
		
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> list.add("dog"));
		assertEquals("Element already in list.", e2.getMessage());
		
	}
	
	/**
	 * Tests method to get elements from list
	 * Can not get an element from an index out of bounds
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		//Since get() is used throughout the tests to check the
		//contents of the list, we don't need to test main flow functionality
		//here.  Instead this test method should focus on the error 
		//and boundary cases.
		
		
		Exception e  = assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
		assertEquals(IndexOutOfBoundsException.class, e.getClass());
		
		
		list.add("apple");
		list.add("banana");
		list.add("cat");
		list.add("dog");
		
		
		Exception e2  = assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		assertEquals(IndexOutOfBoundsException.class, e2.getClass());
		
		
		Exception e3  = assertThrows(IndexOutOfBoundsException.class, () -> list.get(list.size()));
		assertEquals(IndexOutOfBoundsException.class, e3.getClass());
		
		
	}
	
	/**
	 * Tests remove function of a list
	 * Can not remove an element from an out of bounds index
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		
		Exception e  = assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
		assertEquals(IndexOutOfBoundsException.class, e.getClass());
		
		
		list.add("apple");
		list.add("banana");
		list.add("cat");
		list.add("dog");
		list.add("elephant");
		assertEquals(5, list.size());
		
		
		Exception e2  = assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertEquals(IndexOutOfBoundsException.class, e2.getClass());
		
		
		Exception e3  = assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size()));
		assertEquals(IndexOutOfBoundsException.class, e3.getClass());
		
		
		list.remove(list.size() / 2);
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("dog", list.get(2));
		assertEquals("elephant", list.get(3));
		
		
		list.remove(list.size() - 1);
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("dog", list.get(2));
		
		
		
		list.remove(0);
		assertEquals(2, list.size());
		assertEquals("banana", list.get(0));
		assertEquals("dog", list.get(1));
		
		list.remove(list.size() - 1);
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
	}
	
	/**
	 * Tests indexOf function for a list
	 * Can not find the index of a null element
	 * Elements not found return -1
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		
		assertEquals(-1, list.indexOf("cat"));
		
		list.add("apple");
		list.add("banana");
		list.add("cat");
		list.add("dog");
		list.add("elephant");
		
		assertEquals(0, list.indexOf("apple"));
		assertEquals(1, list.indexOf("banana"));
		assertEquals(2, list.indexOf("cat"));
		assertEquals(3, list.indexOf("dog"));
		assertEquals(4, list.indexOf("elephant"));
		assertEquals(-1, list.indexOf("whale"));
		
		Exception e = assertThrows(NullPointerException.class, () -> list.indexOf(null));
		assertEquals(NullPointerException.class, e.getClass());
		
	}
	
	/**
	 * Tests clear function of a list
	 * Causes list to remove all elements
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		list.add("apple");
		list.add("banana");
		list.add("cat");
		list.add("dog");
		list.add("elephant");
		list.clear();
		
		assertEquals(0, list.size());
		
	}

	/**
	 * Tests if a list is empty
	 * returns true if empty, false if not
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		assertTrue(list.isEmpty());
		
		list.add("apple");
		
		assertFalse(list.isEmpty());
	}

	/**
	 * Tests if a list contains a certain element
	 * Returns true if found, false if not
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		assertFalse(list.contains("apple"));
		
		list.add("apple");
		list.add("banana");
		list.add("cat");
		list.add("dog");
		list.add("elephant");
		
		assertTrue(list.contains("apple"));
		assertTrue(list.contains("banana"));
		assertTrue(list.contains("cat"));
		assertTrue(list.contains("dog"));
		assertTrue(list.contains("elephant"));
		assertFalse(list.contains("whale"));
		assertFalse(list.contains("zebra"));
		assertFalse(list.contains("ox"));
		assertFalse(list.contains("monkey"));
		
	}
	
	/**
	 * Tests if 2 lists are equal to each other
	 * Return true if equal, false if not
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("apple");
		list1.add("dog");
		list2.add("apple");
		list2.add("cat");
		list3.add("apple");
		list3.add("dog");
		
		assertEquals(list1, list3);
		assertNotEquals(list1, list2);
	}
	
	/**
	 * Tests if 2 lists share the same hash code
	 * Returns true if the same, false if not
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("apple");
		list1.add("dog");
		list2.add("apple");
		list2.add("cat");
		list3.add("apple");
		list3.add("dog");
		
		assertEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list1.hashCode(), list2.hashCode());
	}

}
 