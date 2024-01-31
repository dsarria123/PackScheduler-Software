package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * Testing class for array lists, will test all functionality of array lists
 * @author Peyton Herring
 * @author Diego Sarria
 */
public class ArrayListTest {

	/**
	 * Tests the list function of adding elements
	 */
	@Test
	public void addTest() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		assertEquals(0, list.size());
		
	   // Adding index 0
        list.add(0, "1");
	    assertEquals(1, list.size());
	    assertEquals("1", list.get(0));

	    // Adding index 1
	    list.add(1, "2");
	    assertEquals(2, list.size());
	    assertEquals("2", list.get(1));

	    // Shift
	    list.add(1, "3");
	    assertEquals(3, list.size());
	    assertEquals("3", list.get(1));
	    assertEquals("2", list.get(2));
	    
	    //Null thing test
	    try {
	        list.add(2, null);
	        fail();  
	    } catch (NullPointerException e) {
	        assertEquals(3, list.size());  
	    }
	   
	    //Testing duplicate 
	    try {
	        list.add(2, "3");
	        fail();  
	    } catch (IllegalArgumentException e) {
	        assertEquals(3, list.size()); 
	    }

	    // Index outta bound test (negative index)
	    try {
	        list.add(-1, "4");
	        fail(); 
	    } catch (IndexOutOfBoundsException e) {
	        assertEquals(3, list.size());  
	    }

	    // Index outta bound test (index > size)
	    try {
	        list.add(5, "4");
	        fail(); 
	    } catch (IndexOutOfBoundsException e) {
	        assertEquals(3, list.size()); 
	    }

	}
	
	/**
	 * Tests the lists function of removing elements
	 */
	@Test
	public void removeTest() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		//Add the elements
		list.add(0, "1");
	    list.add(1, "2");
	    list.add(2, "3");

	    // remove and make sure it moves
	    String removed = list.remove(1);
	    assertEquals("2", removed);
	    assertEquals(2, list.size());
	    assertEquals("3", list.get(1));
	    

	    // Index test (negative index)
	    try {
	        list.remove(-1);
	        fail(); 
	    } catch (IndexOutOfBoundsException e) {
	        assertEquals(2, list.size());  
	    }

	    // Index test (index > size)
	    try {
	        list.remove(3);
	        fail();  
	    } catch (IndexOutOfBoundsException e) {
	        assertEquals(2, list.size()); 
	}

	}
	
	/**
	 * Tests the lists function of setting elements
	 */
	@Test
	public void setTest() {
	    ArrayList<String> list = new ArrayList<String>();
	    list.add(0, "1");
	    list.add(1, "2");

	    // Set at index 1
	    String original = list.set(1, "3");
	    assertEquals("2", original);
	    assertEquals("3", list.get(1));

	    // Test NullPointerException
	    try {
	        list.set(1, null);
	        fail();  
	    } catch (NullPointerException e) {
	        assertEquals("3", list.get(1));  
	    }

	    // Test Illegal
	    try {
	        list.set(1, "1");
	        fail();  
	    } catch (IllegalArgumentException e) {
	        assertEquals("3", list.get(1)); 
	    }

	    // Test Index (negative index)
	    try {
	        list.set(-1, "4");
	        fail(); 
	    } catch (IndexOutOfBoundsException e) {
	        assertEquals("3", list.get(1)); 
	    }

	    // Test IndexOut (index >= size)
	    try {
	        list.set(3, "4");
	        fail(); 
	    } catch (IndexOutOfBoundsException e) {
	        assertEquals("3", list.get(1)); 
	    }
	}
	
	/**
	 * Tests the lists ability to retrieve elements
	 */
	@Test
	public void getTest() {
		
		    ArrayList<String> list = new ArrayList<String>();
		    list.add(0, "1");
		    assertEquals("1", list.get(0));

		    // Test IndexOut (negative index)
		    try {
		        list.get(-1);
		        fail();  
		    } catch (IndexOutOfBoundsException e) {
		    	assertEquals("1", list.get(0));
		    }

		    // Test IndexOut (index > size)
		    try {
		        list.get(7);
		    } catch (IndexOutOfBoundsException e) {
		    	assertEquals("1", list.get(0));
		    }

	
	
	
	}
	
}

