package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests Faculty class.
 */
public class FacultyTest {

    /**
     * First name
     */
    private static final String FIRST = "Jane";
    
    /**
     * Last name
     */
    private static final String LAST = "Doe";
    
    /**
     * ID
     */
    private static final String ID = "jdoe";
    
    /**
     * Email
     */
    private static final String EMAIL = "jdoe@ncsu.edu";
    
    /**
     * HasedPW
     */
    private static final String HASHPW = "hashedpassword";
    
    /**
     * Max courses
     */
    private static final int MAX_COURSES = 3;

  
    /**
     * Tests the Faculty constructor with all field parameters.
     */
    @Test
    public void testFacultyStringStringStringStringStringInt() {
        Faculty faculty = assertDoesNotThrow(() -> new Faculty(FIRST, LAST, ID, EMAIL, HASHPW, MAX_COURSES),
            "Creating a faculty with valid parameters should not throw an exception.");
        
        assertAll("Valid Faculty Construction",
            () -> assertEquals(FIRST, faculty.getFirstName()),
            () -> assertEquals(LAST, faculty.getLastName()),
            () -> assertEquals(ID, faculty.getId()),
            () -> assertEquals(EMAIL, faculty.getEmail()),
            () -> assertEquals(HASHPW, faculty.getPassword()),
            () -> assertEquals(MAX_COURSES, faculty.getMaxCourses())
        );
    }


    /**
     * Tests setting the faculty's first name.
     */
    @Test
    public void testSetFirstName() {
        Faculty faculty = new Faculty(FIRST, LAST, ID, EMAIL, HASHPW, MAX_COURSES);
        
        // Test with a valid first name
        faculty.setFirstName("Alice");
        assertEquals("Alice", faculty.getFirstName(), "Faculty's first name should be updated.");
        
        // Test with null first name
        Exception e1 = assertThrows(IllegalArgumentException.class,
            () -> faculty.setFirstName(null));
        assertEquals("Invalid first name", e1.getMessage(), "Exception message should match expected for null first name.");
        
        // Test with empty first name
        Exception e2 = assertThrows(IllegalArgumentException.class,
            () -> faculty.setFirstName(""));
        assertEquals("Invalid first name", e2.getMessage(), "Exception message should match expected for empty first name.");
    }

    /**
     * Tests setting the faculty's last name.
     */
    @Test
    public void testSetLastName() {
        Faculty faculty = new Faculty(FIRST, LAST, ID, EMAIL, HASHPW, MAX_COURSES);
        
        // Test with a valid last name
        faculty.setLastName("Brown");
        assertEquals("Brown", faculty.getLastName(), "Faculty's last name should be updated.");
        
        // Test with null last name
        Exception e1 = assertThrows(IllegalArgumentException.class,
            () -> faculty.setLastName(null));
        assertEquals("Invalid last name", e1.getMessage(), "Exception message should match expected for null last name.");
        
        // Test with empty last name
        Exception e2 = assertThrows(IllegalArgumentException.class,
            () -> faculty.setLastName(""));
        assertEquals("Invalid last name", e2.getMessage(), "Exception message should match expected for empty last name.");
    }

    /**
     * Tests setting the faculty's email.
     */
    @Test
    public void testSetEmail() {
        Faculty faculty = new Faculty(FIRST, LAST, ID, EMAIL, HASHPW, MAX_COURSES);
        
        // Test with a valid email
        faculty.setEmail("alice@ncsu.edu");
        assertEquals("alice@ncsu.edu", faculty.getEmail(), "Faculty's email should be updated.");
        
        // Test with an invalid email
        Exception e = assertThrows(IllegalArgumentException.class,
            () -> faculty.setEmail("alicensu.edu"));
        assertEquals("Invalid email", e.getMessage(), "Exception message should match expected for invalid email.");
    }

    /**
     * Tests setting the faculty's password.
     */
    @Test
    public void testSetPassword() {
        Faculty faculty = new Faculty(FIRST, LAST, ID, EMAIL, HASHPW, MAX_COURSES);
        
        // Test with a valid password
        faculty.setPassword("newHashedPassword");
        assertEquals("newHashedPassword", faculty.getPassword(), "Faculty's password should be updated.");
        
        // Test with null password
        Exception e1 = assertThrows(IllegalArgumentException.class,
            () -> faculty.setPassword(null));
        assertEquals("Invalid password", e1.getMessage(), "Exception message should match expected for null password.");
        
        // Test with empty password
        Exception e2 = assertThrows(IllegalArgumentException.class,
            () -> faculty.setPassword(""));
        assertEquals("Invalid password", e2.getMessage(), "Exception message should match expected for empty password.");
    }

    /**
     * Tests setting the faculty's max courses.
     */
    @Test
    public void testSetMaxCourses() {
        Faculty faculty = new Faculty(FIRST, LAST, ID, EMAIL, HASHPW, MAX_COURSES);
        
        // Test with a valid max courses
        faculty.setMaxCourses(2);
        assertEquals(2, faculty.getMaxCourses(), "Faculty's max courses should be updated.");
        
        // Test with invalid max courses (too low)
        Exception e1 = assertThrows(IllegalArgumentException.class,
            () -> faculty.setMaxCourses(0));
        assertEquals("Invalid max courses", e1.getMessage(), "Exception message should match expected for too low max courses.");
        
        // Test with invalid max courses (too high)
        Exception e2 = assertThrows(IllegalArgumentException.class,
            () -> faculty.setMaxCourses(4));
        assertEquals("Invalid max courses", e2.getMessage(), "Exception message should match expected for too high max courses.");
    }

   

    /**
     * Tests Faculty.hashCode().
     */
    @Test
    public void testHashCode() {
        Faculty faculty1 = new Faculty(FIRST, LAST, ID, EMAIL, HASHPW, MAX_COURSES);
        Faculty faculty2 = new Faculty(FIRST, LAST, ID, EMAIL, HASHPW, MAX_COURSES);
        Faculty faculty3 = new Faculty("John", LAST, ID, EMAIL, HASHPW, MAX_COURSES);
        
        assertEquals(faculty1.hashCode(), faculty2.hashCode(), "Hash codes should match for equivalent objects.");
        assertNotEquals(faculty1.hashCode(), faculty3.hashCode(), "Hash codes should not match for different objects.");
    }

    /**
     * Tests Faculty.equals().
     */
    @Test
    public void testEqualsObject() {
        Faculty faculty1 = new Faculty(FIRST, LAST, ID, EMAIL, HASHPW, MAX_COURSES);
        Faculty faculty2 = new Faculty(FIRST, LAST, ID, EMAIL, HASHPW, MAX_COURSES);
        Faculty faculty3 = new Faculty("John", LAST, ID, EMAIL, HASHPW, MAX_COURSES);

        assertEquals(faculty1, faculty2, "Two faculties with the same state should be equal.");
        assertNotEquals(faculty1, faculty3, "Faculties with different names should not be equal.");
    }

    /**
     * Tests Faculty.toString().
     */
    @Test
    public void testToString() {
        Faculty faculty = new Faculty(FIRST, LAST, ID, EMAIL, HASHPW, MAX_COURSES);
        String expected = FIRST + "," + LAST + "," + ID + "," + EMAIL + "," + HASHPW + "," + MAX_COURSES;
        assertEquals(expected, faculty.toString(), "The toString method should return the object state as a comma-separated string.");
    }

    /**
     * Test method for compare to
     */
    @Test
    public void testCompareTo() {
        Faculty faculty1 = new Faculty("John", "Doe", "jdoe", "jdoe@email.com", "hashedpassword", 2);
        Faculty faculty3 = new Faculty("John", "Smith", "jsmith", "jsmith@email.com", "hashedpassword", 2);
        Faculty faculty4 = new Faculty("John", "Doe", "jdoe", "jdoe@email.com", "hashedpassword", 2);

        // Test for comparing different last names
        assertTrue(faculty1.compareTo(faculty3) < 0, "John Doe should come before John Smith");
        assertTrue(faculty3.compareTo(faculty1) > 0, "John Smith should come after John Doe");

        // Test for comparing identical objects
        assertEquals(0, faculty1.compareTo(faculty4), "Comparing the same object should return 0");

        // Test for comparing to null
        Exception exception = assertThrows(NullPointerException.class, () -> faculty1.compareTo(null));
        assertEquals("Cannot compare to null.", exception.getMessage());
    }

}
