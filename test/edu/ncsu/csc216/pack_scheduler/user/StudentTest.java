package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Test for student object
 * @author Peyton Herring
 * @author Aaron Green
 * @author Arnav Ganguly
 */
class StudentTest {
	
	/** First name */
	private static final String FIRST = "John";
	/** Last name */
	private static final String LAST = "Doe";
	/** ID */
	private static final String ID = "Jdoe7";
	/** Email */
	private static final String EMAIL = "Jdoe7@ncsu.edu";
	/** Hash password */
	private static final String HASHPW = "W0lfP4ck";
	/** Credits */
	private static final int CREDITS = 15;
	/** Max Credits */
	private static final int MAX = 18;

	/**
	 * Tests hashed code
	 */
	@Test
	void testHashCode() {
		Student s1 = new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS);
		Student s2 = new Student(FIRST, LAST, ID, EMAIL, HASHPW, MAX);
		Student s3 = new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS);
		assertEquals(s1.hashCode(), s3.hashCode()); //Object hash equals identical object
		assertNotEquals(s1.hashCode(), s2.hashCode()); //Object hash does not equal different object
		assertEquals(s1.hashCode(), s1.hashCode()); //Object hash equals itself
	}

	/**
	 * Tests student constructor with credits provided
	 */
	@Test
	void testStudentWithCredits() {
		Student s = assertDoesNotThrow(
				() -> new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS), "Should not throw exception");
		
		assertAll("Student", () -> assertEquals(FIRST, s.getFirstName(), "Incorrect first name"),
				() -> assertEquals(LAST, s.getLastName(), "Incorrect last name"),
				() -> assertEquals(ID, s.getId(), "Incorrecr ID"),
				() -> assertEquals(EMAIL, s.getEmail(), "Incorrect email"),
				() -> assertEquals(HASHPW, s.getPassword(), "Incorrect HashPW"),
				() -> assertEquals(CREDITS, s.getMaxCredits(), "Incorrect credits"));
	}

	/**
	 * Tests student constructor without credits provided
	 */
	@Test
	void testStudentWithNoCredits() {
		Student s = assertDoesNotThrow(
				() -> new Student(FIRST, LAST, ID, EMAIL, HASHPW), "Should not throw exception");
		
		assertAll("Student", () -> assertEquals(FIRST, s.getFirstName(), "Incorrect first name"),
				() -> assertEquals(LAST, s.getLastName(), "Incorrect last name"),
				() -> assertEquals(ID, s.getId(), "Incorrecr ID"),
				() -> assertEquals(EMAIL, s.getEmail(), "Incorrect email"),
				() -> assertEquals(HASHPW, s.getPassword(), "Incorrect HashPW"),
				() -> assertEquals(MAX, s.getMaxCredits(), "Incorrect credits"));
	}

	/**
	 * Tests set Email
	 */
	@Test
	void testSetEmail() {
		Student s = new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setEmail("j.doe@ncsuedu"));
				assertEquals("Invalid email", e1.getMessage()); //Check correct exception message
				assertEquals(EMAIL, s.getEmail()); //Check email doesn't change
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setEmail("jdoe@ncsuedu"));
				assertEquals("Invalid email", e2.getMessage()); //Check correct exception message
				assertEquals(EMAIL, s.getEmail()); //Check email doesn't change
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> s.setEmail("jdoencsu.edu"));
				assertEquals("Invalid email", e3.getMessage()); //Check correct exception message
				assertEquals(EMAIL, s.getEmail()); //Check email doesn't change
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> s.setEmail(null));
				assertEquals("Invalid email", e4.getMessage()); //Check correct exception message
				assertEquals(EMAIL, s.getEmail()); //Check email doesn't change
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> s.setEmail(""));
				assertEquals("Invalid email", e5.getMessage()); //Check correct exception message
				assertEquals(EMAIL, s.getEmail()); //Check email doesn't change
	}

	@Test
	void testSetHashPW() {
		Student s = new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setPassword(null));
				assertEquals("Invalid password", e1.getMessage()); //Check correct exception message
				assertEquals(HASHPW, s.getPassword()); //Check PW doesn't change
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setPassword(""));
				assertEquals("Invalid password", e2.getMessage()); //Check correct exception message
				assertEquals(HASHPW, s.getPassword()); //Check PW doesn't change
	}

	/**
	 * Tests set credits
	 */
	@Test
	void testSetMaxCredits() {
		Student s = new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setMaxCredits(1));
				assertEquals("Invalid max credits", e1.getMessage()); //Check correct exception message
				assertEquals(CREDITS, s.getMaxCredits()); //Check credits doesn't change
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setMaxCredits(19));
				assertEquals("Invalid max credits", e2.getMessage()); //Check correct exception message
				assertEquals(CREDITS, s.getMaxCredits()); //Check credits doesn't change
	}

	/**
	 * Tests set first name
	 */
	@Test
	void testSetFirstName() {
		Student s = new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setFirstName(null));
				assertEquals("Invalid first name", e1.getMessage()); //Check correct exception message
				assertEquals(FIRST, s.getFirstName()); //Check name doesn't change
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setFirstName(""));
				assertEquals("Invalid first name", e2.getMessage()); //Check correct exception message
				assertEquals(FIRST, s.getFirstName()); //Check name doesn't change
	}

	/**
	 * Tests set last name
	 */
	@Test
	void testSetLastName() {
		Student s = new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setLastName(null));
				assertEquals("Invalid last name", e1.getMessage()); //Check correct exception message
				assertEquals(LAST, s.getLastName()); //Check name doesn't change
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setLastName(""));
				assertEquals("Invalid last name", e2.getMessage()); //Check correct exception message
				assertEquals(LAST, s.getLastName()); //Check name doesn't change
	}

	/**
	 * Test set ID
	 */
	@Test
	void testSetId() {
		Student s = new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setId(null));
				assertEquals("Invalid id", e1.getMessage()); //Check correct exception message
				assertEquals(ID, s.getId()); //Check ID doesn't change
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setId(""));
				assertEquals("Invalid id", e2.getMessage()); //Check correct exception message
				assertEquals(ID, s.getId()); //Check ID doesn't change
	}
	
	/**
	 * Tests if 2 students are equal
	 */
	@Test
	void testEquals() {
		Student s1 = new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS);
		Student s2 = new Student(FIRST, LAST, ID, EMAIL, HASHPW, MAX);
		Student s3 = new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS);
		Student s4 = new Student("Jane", LAST, ID, EMAIL, HASHPW, MAX);
		Student s5 = new Student(FIRST, "Boe", ID, EMAIL, HASHPW, CREDITS);
		Student s6 = new Student(FIRST, LAST, "Jdoe5", EMAIL, HASHPW, CREDITS);
		Student s7 = new Student(FIRST, LAST, ID, "Jdoe5@ncsu.edu", HASHPW, CREDITS);
		Student s8 = new Student(FIRST, LAST, ID, EMAIL, "PackW0lf", CREDITS);
		s1.equals(s2);
		assertEquals(s1, s3); //Object equals identical object
		assertNotEquals(s1, s2); //Object does not equal different object (credits)
		assertEquals(s1, s1); //Object equals itself
		assertNotEquals(s1, s4); //Object does not equal different object (first)
		assertNotEquals(s1, s5); //Object does not equal different object (last)
		assertNotEquals(s1, s6); //Object does not equal different object (id)
		assertNotEquals(s1, s7); //Object does not equal different object (email)
		assertNotEquals(s1, s8); //Object does not equal different object (PW)
	}

	/**
	 * Tests toString method
	 */
	@Test
	void testToString() {
		Student s1 = new Student(FIRST, LAST, ID, EMAIL, HASHPW, CREDITS);
		assertEquals("John,Doe,Jdoe7,Jdoe7@ncsu.edu,W0lfP4ck,15", s1.toString()); //Strings should match
	}
	
	/**
	 * Tests compare to method
	 */
	@Test
	void testCompareTo() {
		Student s1 = new Student("John", "Doe", "Jdoe1", EMAIL, HASHPW, CREDITS); //3
		Student s2 = new Student("Jane", "Dale", "Jdale1", EMAIL, HASHPW, CREDITS); //1
		Student s3 = new Student("Jonathan", "Doe", "Jdoyle1", EMAIL, HASHPW, CREDITS); //4
		Student s4 = new Student("Jake", "Dickson", "Jdickson1", EMAIL, HASHPW, CREDITS); //2
		
		// x.compareTo(y)) == -sgn(y.compareTo(x)
		assertEquals(s1.compareTo(s2), -s2.compareTo(s1));
		assertEquals(s1.compareTo(s3), -s3.compareTo(s1));
		assertEquals(s1.compareTo(s4), -s4.compareTo(s1));
		assertEquals(s2.compareTo(s3), -s3.compareTo(s2));
		assertEquals(s2.compareTo(s4), -s4.compareTo(s2));
		assertEquals(s3.compareTo(s4), -s4.compareTo(s3));
		
		
		
		
	}
	
	/**
	 * Tests the get schedule method
	 */
	@Test
	void testSchedule() {
		Student s1 = new Student("John", "Doe", "Jdoe1", EMAIL, HASHPW, CREDITS);
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100);
		assertTrue(s1.getSchedule().addCourseToSchedule(c1));
	}

}
