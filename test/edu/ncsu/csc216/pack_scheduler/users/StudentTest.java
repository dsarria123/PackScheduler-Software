package edu.ncsu.csc216.pack_scheduler.users;


import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the Student object.
 * @author SarahHeckman
 */
public class StudentTest {
	
	/** Test Student's first name. */
	private String firstName = "first";
	/** Test Student's last name */
	private String lastName = "last";
	/** Test Student's id */
	private String id = "flast";
	/** Test Student's email */
	private String email = "first_last@ncsu.edu";
	/** Test Student's hashed password */
	private String hashPW;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	//This is a block of code that is executed when the StudentTest object is
	//created by JUnit.  Since we only need to generate the hashed version
	//of the plaintext password once, we want to create it as the StudentTest object is
	//constructed.  By automating the hash of the plaintext password, we are
	//not tied to a specific hash implementation.  We can change the algorithm
	//easily.
	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			this.hashPW = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}
	
	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		Student s1 = new Student(firstName, lastName, id, email, hashPW);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",18", s1.toString());
	}
	
	/**
	 * Test canAdd() method
	 */
	@Test
	public void testCanAdd() {
		Student s = new Student("Zach", "King", "zking", "zking@ncsu.edu", hashPW, 9); //assume max credits is 9
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
		
		//adding null course
		assertFalse(s.canAdd(null));
		
		//add some courses to schedule
		assertTrue(s.getSchedule().addCourseToSchedule(c1));
		assertTrue(s.getSchedule().addCourseToSchedule(c3));
		assertTrue(s.getSchedule().addCourseToSchedule(c4));
		
		//check duplicate
		assertFalse(s.canAdd(c1));
		
		//check schedule conflict
		assertFalse(s.canAdd(c2));
		
		//adding a course exceeding max credits
		assertFalse(s.canAdd(c5));
				
				
	}

}
