package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the schedule object and its functionality
 * @author Peyton Herring
 * @author Dee Kandel
 */
public class ScheduleTest {

	/**
	 * Tests the Schedule constructor
	 */
	@Test
	void testSchedule() {
		//new Schedule object is created
		Schedule s = new Schedule();
		
		//test that title is set to "My Schedule"
		assertEquals("My Schedule", s.getTitle());
		
		//check that schedule is empty upon being constructed
		assertEquals(0, s.getScheduledCourses().length);
	}
	
	/**
	 * Tests addCourseToSchedule() method
	 */
	@Test
	void testAddCourseToSchedule() {
		Schedule s = new Schedule();
		
		//tests adding null course
		assertThrows(NullPointerException.class, () -> s.addCourseToSchedule(null));
		
		//test adding a course to schedule
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100);
		//add to schedule
		assertTrue(s.addCourseToSchedule(c1));
		//create course with conflicting time
		Course c2 = new Course("CSC217", "Software Development Fundamentals Lab", "202", 1, "sesmith5", 10, "M", 1040, 1230);
		//create non-conflicting course
		Course c3 = new Course("CSC217", "Software Development Fundamentals Lab", "202", 1, "sesmith5", 10, "M", 1330, 1510);
		
		
		//test adding duplicate course
		try {
			s.addCourseToSchedule(c1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("You are already enrolled in " + c1.getName(), e.getMessage());
		}
		
		//tests adding a course that conflicts with schedule
		try {
			s.addCourseToSchedule(c2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("The course cannot be added due to a conflict.", e.getMessage());
		}
		
		//tests adding non-conflicting course 
		assertTrue(s.addCourseToSchedule(c3));
		assertEquals(2, s.getScheduledCourses().length);
		
		
		
		
	}
	
	
	/**
	 * Tests removeCourseFromSchedule() method
	 */
	@Test
	void testRemoveCourseFromSchedule() {
		Schedule s = new Schedule();
		
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100);
		Course c2 = new Course("CSC217", "Software Development Fundamentals Lab", "202", 1, "sesmith5", 10, "M", 1330, 1510);
		Course c3 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "002", 3, "tmbarnes", 10, "TF", 1200, 1315);
		Course c4 = new Course("CSC316", "Data Structures and Algorithms", "001", 3, "jtking", 10, "MW", 800, 900);
		
		// remove from empty schedule
		assertFalse(s.removeCourseFromSchedule(c1));
		
		//add some courses 
		assertTrue(s.addCourseToSchedule(c1));
		assertTrue(s.addCourseToSchedule(c2));
		assertTrue(s.addCourseToSchedule(c3));
		assertEquals(3, s.getScheduledCourses().length);
		
		
		//test removing courses that don't exist in schedule
		assertFalse(s.removeCourseFromSchedule(c4));
		assertEquals(3, s.getScheduledCourses().length);
		
		
		//remove a course from schedule
		assertTrue(s.removeCourseFromSchedule(c1));
		assertEquals(2, s.getScheduledCourses().length);
		
		
		//remove rest of courses until empty
		assertTrue(s.removeCourseFromSchedule(c3));
		assertEquals(1, s.getScheduledCourses().length);
		
		assertTrue(s.removeCourseFromSchedule(c2));
		assertEquals(0, s.getScheduledCourses().length);
		
		
		//add a course once all courses are removed
		assertTrue(s.addCourseToSchedule(c2));
		assertEquals(1, s.getScheduledCourses().length);
		
		
	}
	
	
	/**
	 * Tests resetSchedule() method
	 */
	@Test
	void testResetSchedule() {
		Schedule s = new Schedule();
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100);
		s.addCourseToSchedule(c1);
		
		s.resetSchedule();
		
		assertEquals(0, s.getScheduledCourses().length);
		assertEquals("My Schedule", s.getTitle());
	}
	
	
	/**
	 * Tests getScheduledCourses() method
	 */
	@Test
	void testGetScheduledCourses() {
		Schedule s = new Schedule();
		
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 50, "MW", 910, 1100);
		Course c2 = new Course("CSC217", "Software Development Fundamentals Lab", "202", 1, "sesmith5", 15, "M", 1330, 1510);
		Course c3 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "002", 3, "tmbarnes", 100, "TF", 1200, 1315);
		Course c4 = new Course("CSC316", "Data Structures and Algorithms", "001", 3, "jtking", 75, "MW", 800, 900);
		assertTrue(s.addCourseToSchedule(c1));
		assertTrue(s.addCourseToSchedule(c2));
		assertTrue(s.addCourseToSchedule(c3));
		assertTrue(s.addCourseToSchedule(c4));
		
		String [][] schedule = s.getScheduledCourses();
	
		//Row 0
		assertEquals("CSC116", schedule[0][0]);
		assertEquals("001", schedule[0][1]);
		assertEquals("Intro to Programming - Java", schedule[0][2]);
		assertEquals("MW 9:10AM-11:00AM", schedule[0][3]);
		assertEquals("50", schedule[0][4]);
		
		//Row 1
		assertEquals("CSC217", schedule[1][0]);
		assertEquals("202", schedule[1][1]);
		assertEquals("Software Development Fundamentals Lab", schedule[1][2]);
		assertEquals("M 1:30PM-3:10PM", schedule[1][3]);
		assertEquals("15", schedule[1][4]);
		
		//Row 2
		assertEquals("CSC226", schedule[2][0]);
		assertEquals("002", schedule[2][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", schedule[2][2]);
		assertEquals("TF 12:00PM-1:15PM", schedule[2][3]);
		assertEquals("100", schedule[2][4]);
		
		//Row 3
		assertEquals("CSC316", schedule[3][0]);
		assertEquals("001", schedule[3][1]);
		assertEquals("Data Structures and Algorithms", schedule[3][2]);
		assertEquals("MW 8:00AM-9:00AM", schedule[3][3]);
		assertEquals("75", schedule[3][4]);
			
	}
	
	/**
	 * Tests setTitle() method
	 */
	@Test
	void testSetTitle() {
		Schedule s = new Schedule();
		
		//set the title to any title but null (valid title)
		s.setTitle("My Class Schedule");
		
		assertEquals("My Class Schedule", s.getTitle());
		
		//invalid title
		assertThrows(IllegalArgumentException.class, () -> s.setTitle(null));
	}
	
	
	/**
	 * Tests getScheduleCredits() method
	 */
	@Test
	void testGetScheduleCredits() {
		Schedule s = new Schedule();
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100);
		Course c2 = new Course("CSC217", "Software Development Fundamentals Lab", "202", 1, "sesmith5", 10, "M", 1330, 1510);
		Course c3 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "002", 3, "tmbarnes", 10, "TF", 1200, 1315);
		Course c4 = new Course("CSC316", "Data Structures and Algorithms", "001", 3, "jtking", 10, "MW", 800, 900);
		
		//add courses to schedule
		assertTrue(s.addCourseToSchedule(c1));
		assertTrue(s.addCourseToSchedule(c2));
		assertTrue(s.addCourseToSchedule(c3));
		assertTrue(s.addCourseToSchedule(c4));
		assertEquals(4, s.getScheduledCourses().length);
		
		//sum of credits of all added courses (c1, c2, c3, c4) should be 10
		assertEquals(10, s.getScheduleCredits());
		
		//remove course then get total credits
		assertTrue(s.removeCourseFromSchedule(c1));
		assertEquals(7, s.getScheduleCredits());
	}
	
	/**
	 * Tests the canAdd() method
	 */
	@Test
	void testCanAdd() {
		Schedule s = new Schedule();
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MF", 1040, 1230);
		Course c3 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "002", 3, "tmbarnes", 10, "TF", 1200, 1315);
		Course c4 = new Course("CSC316", "Data Structures and Algorithms", "001", 3, "jtking", 10, "MW", 800, 900);
		Course c5 = new Course("CSC217", "Software Development Fundamentals Lab", "202", 1, "sesmith5", 10, "M", 1330, 1510);
		
		//check if valid course can be added
		assertTrue(s.canAdd(c1));
		assertTrue(s.canAdd(c3));
		assertTrue(s.canAdd(c4));
		assertTrue(s.canAdd(c5));
		
		//add some courses to schedule
		assertTrue(s.addCourseToSchedule(c1));
		assertTrue(s.addCourseToSchedule(c3));
		assertTrue(s.addCourseToSchedule(c4));
		assertTrue(s.addCourseToSchedule(c5));
		
		
		//add null course
		assertFalse(s.canAdd(null));
		
		//add duplicate course
		assertFalse(s.canAdd(c1));
		assertFalse(s.canAdd(c4));
		
		//add course that conflicts with an already existent course in Schedule
		assertFalse(s.canAdd(c2));
	}
	

}
