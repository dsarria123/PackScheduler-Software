/**
 * Test class for testing transition out from each state
 * @author Dee Kandel
 * @author Peyton Herring
 */package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * commit?
 * 
 */

/**
 * Test class for course name validator
 * Conducts same tests and expects same results as FSM test
 * @author Peyton Herring
 * @author Dee Kandel
 */
public class CourseNameValidatorTest {
	
	/** Course name validator */
	private CourseNameValidator c;
	
	/**
	 * Creates the validator
	 */
	@BeforeEach
	public void setUp() {
		c = new CourseNameValidator();
	}
	
	/**
	 * Tests multiple valid course creations
	 */
	@Test
	public void testValidCourses() {
		try {
			assertTrue(c.isValid("CSC216"));
			assertTrue(c.isValid("E115"));
			assertTrue(c.isValid("MA242"));
			assertTrue(c.isValid("CH101A"));
			assertTrue(c.isValid("CSCS217"));
		} catch (InvalidTransitionException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Tests invalid course creations
	 */
	@Test
	public void testInvalidCourses() {
		try {
			//All return false, should not throw
			assertFalse(c.isValid("E10"));
			assertFalse(c.isValid("E1"));
			assertFalse(c.isValid("E"));
			assertFalse(c.isValid("EOE"));
		} catch (InvalidTransitionException e) {
			fail(e.getMessage());
		}
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("E1E"));
		assertEquals("Course name must have 3 digits.", e1.getMessage());
		Exception e2 = assertThrows(InvalidTransitionException.class, () -> c.isValid("E10E"));
		assertEquals("Course name must have 3 digits.", e2.getMessage());
		Exception e3 = assertThrows(InvalidTransitionException.class, () -> c.isValid("E$"));
		assertEquals("Course name can only contain letters and digits.", e3.getMessage());
		Exception e4 = assertThrows(InvalidTransitionException.class, () -> c.isValid("1M"));
		assertEquals("Course name must start with a letter.", e4.getMessage());
		Exception e5 = assertThrows(InvalidTransitionException.class, () -> c.isValid("LETTER"));
		assertEquals("Course name cannot start with more than 4 letters.", e5.getMessage());
		Exception e6 = assertThrows(InvalidTransitionException.class, () -> c.isValid("E1010"));
		assertEquals("Course name can only have 3 digits.", e6.getMessage());
		Exception e7 = assertThrows(InvalidTransitionException.class, () -> c.isValid("E115AA"));
		assertEquals("Course name can only have a 1 letter suffix.", e7.getMessage());
		Exception e8 = assertThrows(InvalidTransitionException.class, () -> c.isValid("E115A5"));
		assertEquals("Course name cannot contain digits after the suffix.", e8.getMessage());
		Exception e9 = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", e9.getMessage()); //Test default message
	}
}
