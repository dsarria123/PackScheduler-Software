/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests Activity class but checks only the conflict library
 */
class ActivityTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.wolf_scheduler.course.Activity#checkConflict(edu.ncsu.csc216.wolf_scheduler.course.Activity)}.
	 */
	@Test
	void testCheckConflict() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		
		assertDoesNotThrow(() -> a1.checkConflict(a2));
	    assertDoesNotThrow(() -> a2.checkConflict(a1));
	}
	
	@Test
	public void testCheckConflictWithConflict() {
		
		// same start and end times
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "M", 1330, 1445);
		
	    Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
	    assertEquals("Schedule conflict.", e1.getMessage());
		
	    Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	    assertEquals("Schedule conflict.", e2.getMessage());
	    
	    // different end and start times 
	    Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
	    Activity a4 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWF", 1230, 1745);
		
	    Exception e3 = assertThrows(ConflictException.class, () -> a3.checkConflict(a4));
	    assertEquals("Schedule conflict.", e3.getMessage());
		
	    Exception e4 = assertThrows(ConflictException.class, () -> a4.checkConflict(a3));
	    assertEquals("Schedule conflict.", e4.getMessage());
	    
	    //endtime for one event == start time of another (same day)
	    
	    Activity a5 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
	    Activity a6 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWF", 1445, 1745);
		
	    Exception e5 = assertThrows(ConflictException.class, () -> a5.checkConflict(a6));
	    assertEquals("Schedule conflict.", e5.getMessage());
		
	    Exception e6 = assertThrows(ConflictException.class, () -> a6.checkConflict(a5));
	    assertEquals("Schedule conflict.", e6.getMessage());
	    
	    //endtime for one event == start time of another (different day)
	    
	    Activity a7 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "HF", 1330, 1445);
	    Activity a8 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTW", 1445, 1745);
		
	    assertDoesNotThrow(() -> a7.checkConflict(a8));
	    assertDoesNotThrow(() -> a8.checkConflict(a7));
	   
	}

}
