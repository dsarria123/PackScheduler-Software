package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * This java test file tests the course roll class and all of its methods
 * @author Diego Sarria
 */
public class CourseRollTest {

	/**
	 * Tests the functions regarding creating course rolls and enrollment capacities (and setting them)
	 */
	@Test
	public void testSetEnrollmentCap() {
		Course course1 = new Course("CSC216", "Programming Concepts", "001", 4, "sesmith5", 10, "A");
		CourseRoll c1 = course1.getCourseRoll(); //low bound
		
		Course course2 = new Course("CSC216", "Programming Concepts", "001", 4, "sesmith5", 250, "A");
		CourseRoll c2 = course2.getCourseRoll(); //high bound
		
		Course course3 = new Course("CSC216", "Programming Concepts", "001", 4, "sesmith5", 100, "A");
		CourseRoll c3 = course3.getCourseRoll(); //middle value
		assertEquals(10, c1.getEnrollmentCap());
		assertEquals(250, c2.getEnrollmentCap());
		assertEquals(100, c3.getEnrollmentCap());
		c1.setEnrollmentCap(20); //Set new cap
		assertEquals(20, c1.getEnrollmentCap());
		assertThrows(IllegalArgumentException.class, () -> c2.setEnrollmentCap(251)); //Out of bounds
		assertThrows(IllegalArgumentException.class, () -> c2.setEnrollmentCap(9));
		c3.setEnrollmentCap(100); //Same cap should not throw
		assertEquals(100, c3.getOpenSeats()); //No seats taken, same as size
		
	}
	
	/**
	 * Tests the ability to actually enroll and add students
	 * And drop them from enrollment
	 */
	@Test
	public void testEnrollmentAndDrop() {
		Course course1 = new Course("CSC216", "Programming Concepts", "001", 4, "sesmith5", 10, "A");
		CourseRoll c1 = course1.getCourseRoll();
		Student s1 = new Student("Peyton", "Herring", "phherrin", "phherrin@ncsu.edu", "st4te", 16);
		assertTrue(c1.canEnroll(s1));
		c1.enroll(s1);
		assertFalse(c1.canEnroll(s1)); // duplicate
		
		
		Student duplicate = new Student("Peyton", "Herring", "phherrin", "phherrin@ncsu.edu", "st4te", 16);
		assertFalse(c1.canEnroll(duplicate)); //Duplicate student cannot be added
		assertEquals(9, c1.getOpenSeats());
		assertEquals(10, c1.getEnrollmentCap());
		assertThrows(IllegalArgumentException.class, () -> c1.enroll(null));
		assertThrows(IllegalArgumentException.class, () -> c1.drop(null));
		
		c1.drop(s1);
		assertTrue(c1.canEnroll(s1));
		Student s2 = new Student("John", "Herring", "phherrin1", "phherrin@ncsu.edu", "st4te", 16);
		Student s3 = new Student("Joe", "Herring", "phherrin2", "phherrin@ncsu.edu", "st4te", 16);
		Student s4 = new Student("Greg", "Herring", "phherrin3", "phherrin@ncsu.edu", "st4te", 16);
		Student s5 = new Student("Bob", "Herring", "phherrin4", "phherrin@ncsu.edu", "st4te", 16);
		Student s6 = new Student("Phil", "Herring", "phherrin5", "phherrin@ncsu.edu", "st4te", 16);
		Student s7 = new Student("Jane", "Herring", "phherrin6", "phherrin@ncsu.edu", "st4te", 16);
		Student s8 = new Student("Mary", "Herring", "phherrin7", "phherrin@ncsu.edu", "st4te", 16);
		Student s9 = new Student("Guy", "Herring", "phherrin8", "phherrin@ncsu.edu", "st4te", 16);
		Student s0 = new Student("Name", "Herring", "phherrin9", "phherrin@ncsu.edu", "st4te", 16);
		Student s = new Student("Full", "Herring", "phherrin0", "phherrin@ncsu.edu", "st4te", 16);
		c1.enroll(s1);
		c1.enroll(s2);
		c1.enroll(s3);
		c1.enroll(s4);
		c1.enroll(s5);
		c1.enroll(s6);
		c1.enroll(s7);
		c1.enroll(s8);
		c1.enroll(s9);
		c1.enroll(s0);
		assertEquals(0, c1.getOpenSeats());
		
		assertTrue(c1.canEnroll(s)); //List is full, waitlist is open
		assertEquals(0, c1.getNumberOnWaitlist());
		
		// Add to waitlist
		c1.enroll(s);
		assertEquals(1, c1.getNumberOnWaitlist());
		
	}
	
	/**
	 * Tests getNumberOnWaitlist
	 */
	@Test
	public void testEnrollDropWaitlist() {
		Course course1 = new Course("CSC216", "Programming Concepts", "001", 4, "sesmith5", 10, "A");
		CourseRoll c1 = course1.getCourseRoll();
		assertEquals(0, c1.getNumberOnWaitlist());
		
		// Enroll to the CourseRoll limit
		Student s1 = new Student("Peyton", "Herring", "phherrin", "phherrin@ncsu.edu", "st4te", 16);
		Student s2 = new Student("John", "Herring", "phherrin1", "phherrin@ncsu.edu", "st4te", 16);
		Student s3 = new Student("Joe", "Herring", "phherrin2", "phherrin@ncsu.edu", "st4te", 16);
		Student s4 = new Student("Greg", "Herring", "phherrin3", "phherrin@ncsu.edu", "st4te", 16);
		Student s5 = new Student("Bob", "Herring", "phherrin4", "phherrin@ncsu.edu", "st4te", 16);
		Student s6 = new Student("Phil", "Herring", "phherrin5", "phherrin@ncsu.edu", "st4te", 16);
		Student s7 = new Student("Jane", "Herring", "phherrin6", "phherrin@ncsu.edu", "st4te", 16);
		Student s8 = new Student("Mary", "Herring", "phherrin7", "phherrin@ncsu.edu", "st4te", 16);
		Student s9 = new Student("Guy", "Herring", "phherrin8", "phherrin@ncsu.edu", "st4te", 16);
		Student s0 = new Student("Name", "Herring", "phherrin9", "phherrin@ncsu.edu", "st4te", 16);
		c1.enroll(s1);
		c1.enroll(s2);
		c1.enroll(s3);
		c1.enroll(s4);
		c1.enroll(s5);
		c1.enroll(s6);
		c1.enroll(s7);
		c1.enroll(s8);
		c1.enroll(s9);
		c1.enroll(s0);
		assertEquals(0, c1.getOpenSeats());
		
		// Add to waitlist
		Student s = new Student("Full", "Herring", "phherrin0", "phherrin@ncsu.edu", "st4te", 16);
		c1.enroll(s);
		assertEquals(1, c1.getNumberOnWaitlist());
		assertFalse(c1.canEnroll(s)); // On waitlist already
		
		// Drop Student from main
		c1.drop(s1);
		assertEquals(0, c1.getOpenSeats());
		assertEquals(0, c1.getNumberOnWaitlist());
		
		// Add to waitlist
		c1.enroll(s1);
		assertEquals(1, c1.getNumberOnWaitlist());
		
		// Drop from waitlist
		c1.drop(s1);
		assertEquals(0, c1.getNumberOnWaitlist());
		
	}
	
	/**
	 * Tests enrolling to a full waitlist
	 */
	@Test
	public void testFullWaitlist() {
		Course course1 = new Course("CSC216", "Programming Concepts", "001", 4, "sesmith5", 10, "A");
		CourseRoll c1 = course1.getCourseRoll();
		assertEquals(0, c1.getNumberOnWaitlist());
		
		// Enroll to the CourseRoll limit
		Student s1 = new Student("Peyton", "Herring", "phherrin", "phherrin@ncsu.edu", "st4te", 16);
		Student s2 = new Student("John", "Herring", "phherrin1", "phherrin@ncsu.edu", "st4te", 16);
		Student s3 = new Student("Joe", "Herring", "phherrin2", "phherrin@ncsu.edu", "st4te", 16);
		Student s4 = new Student("Greg", "Herring", "phherrin3", "phherrin@ncsu.edu", "st4te", 16);
		Student s5 = new Student("Bob", "Herring", "phherrin4", "phherrin@ncsu.edu", "st4te", 16);
		Student s6 = new Student("Phil", "Herring", "phherrin5", "phherrin@ncsu.edu", "st4te", 16);
		Student s7 = new Student("Jane", "Herring", "phherrin6", "phherrin@ncsu.edu", "st4te", 16);
		Student s8 = new Student("Mary", "Herring", "phherrin7", "phherrin@ncsu.edu", "st4te", 16);
		Student s9 = new Student("Guy", "Herring", "phherrin8", "phherrin@ncsu.edu", "st4te", 16);
		Student s0 = new Student("Name", "Herring", "phherrin9", "phherrin@ncsu.edu", "st4te", 16);
		c1.enroll(s1);
		c1.enroll(s2);
		c1.enroll(s3);
		c1.enroll(s4);
		c1.enroll(s5);
		c1.enroll(s6);
		c1.enroll(s7);
		c1.enroll(s8);
		c1.enroll(s9);
		c1.enroll(s0);
		assertEquals(0, c1.getOpenSeats());
		
		// Enroll to waitlist limit
		Student s11 = new Student("Name1", "Herring", "phherrin11", "phherrin@ncsu.edu", "st4te", 16);
		Student s12 = new Student("Name2", "Herring", "phherrin12", "phherrin@ncsu.edu", "st4te", 16);
		Student s13 = new Student("Name3", "Herring", "phherrin13", "phherrin@ncsu.edu", "st4te", 16);
		Student s14 = new Student("Name4", "Herring", "phherrin31", "phherrin@ncsu.edu", "st4te", 16);
		Student s15 = new Student("Name5", "Herring", "phherrin14", "phherrin@ncsu.edu", "st4te", 16);
		Student s16 = new Student("Name6", "Herring", "phherrin15", "phherrin@ncsu.edu", "st4te", 16);
		Student s17 = new Student("Name7", "Herring", "phherrin16", "phherrin@ncsu.edu", "st4te", 16);
		Student s18 = new Student("Name8", "Herring", "phherrin17", "phherrin@ncsu.edu", "st4te", 16);
		Student s19 = new Student("Name9", "Herring", "phherrin18", "phherrin@ncsu.edu", "st4te", 16);
		Student s20 = new Student("Name10", "Herring", "phherrin19", "phherrin@ncsu.edu", "st4te", 16);
		c1.enroll(s11);
		c1.enroll(s12);
		c1.enroll(s13);
		c1.enroll(s14);
		c1.enroll(s15);
		c1.enroll(s16);
		c1.enroll(s17);
		c1.enroll(s18);
		c1.enroll(s19);
		c1.enroll(s20);
		assertEquals(10, c1.getNumberOnWaitlist());
		
		// Cannot add to full waitlist
		Student unlucky = new Student("Willy", "Patten", "wpatten22", "wpatten@ncsu.edu", "st4te", 16);
		assertThrows(IllegalArgumentException.class, () -> c1.enroll(unlucky));
		
		
	}

}
