package edu.ncsu.csc216.pack_scheduler.catalog;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;


/**
 * Tests the WolfScheduler class.
 * 
 * @author Sarah Heckman
 * @author Arnav Ganguly
 */
public class CourseCatalogTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";
	/** Invalid file path */
	private final String invalidFilePath = "home/course-records.txt";
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";

	
	/**
	 * Testing the CourseCatalog constructor by trying to remove a course from an empty 
	 * catalog and then checking to see if the length of the catalog is zero.
	 */
	@Test
	public void testCourseCatalog() {
		// Test that the StudentDirectory is initialized to an empty list
		CourseCatalog cc = new CourseCatalog();
		assertFalse(cc.removeCourseFromCatalog("CSC116", "001"));
		assertEquals(0, cc.getCourseCatalog().length);
	}
	
	/**
	 * Testing to see if when a new Course Catalog is constructed, the previous one is 
	 * completely cleared out and brought back to length zero
	 */
	@Test
	public void testNewCourseCatalog() {
		// Test that if there are students in the directory, they
		// are removed after calling newStudentDirectory().
		CourseCatalog cc = new CourseCatalog();

		cc.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc.getCourseCatalog().length);

		cc.newCourseCatalog();
		assertEquals(0, cc.getCourseCatalog().length);
	}
	
	/**
	 * Testing loadCoursesFromFile by importing one of the valid files and then checking
	 * the length of the new catalog. We also ensure that the data is intact by checking to see
	 * if the instructorId and credits of one of the courses is correct. We also try and import
	 * a invalid file and check if the length is zero. Lastly, we enter an invalid filePath into
	 * the method and make sure an exception is thrown.
	 */
	@Test
	public void testLoadCoursesFromFile() {
		CourseCatalog cc = new CourseCatalog();
		CourseCatalog cc2 = new CourseCatalog();

		// Test valid file
		cc.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc.getCourseCatalog().length);
		Course fCourse = cc.getCourseFromCatalog("CSC116", "001");
		assertNull(fCourse.getInstructorId());
		assertEquals(3, fCourse.getCredits());
		
		// Test invalid file path
		cc.loadCoursesFromFile(invalidTestFile);
		assertEquals(0, cc.getCourseCatalog().length);
		
		// Test invalid file path
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> cc2.loadCoursesFromFile(invalidFilePath));
		assertEquals("File could not be read.", exception.getMessage());
	}
	
	/**
	 * Testing addCourseToCatalog by adding a valid course and checking the details, then
	 * adding an invalid Course and checking if an exception is thrown and then adding a duplicate course
	 * to check if the method returns false. 
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog cc = new CourseCatalog();
		
		// Testing valid course
		cc.addCourseToCatalog(NAME, TITLE, SECTION, 3, "asgangul", 10, "MW", 1300, 1500);
		String [][] courseCatalog = cc.getCourseCatalog();
		
		assertEquals(1, courseCatalog.length);
		assertEquals(NAME, courseCatalog[0][0]);
		assertEquals(SECTION, courseCatalog[0][1]);
		assertEquals(TITLE, courseCatalog[0][2]);
		
		// Testing invalid course
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> cc.addCourseToCatalog(NAME, TITLE, SECTION, -2, "asgangul", 10, "MW", 1300, 1500));
		assertEquals("Course could not be constructed.", exception.getMessage());
		
		// Testing duplicate course
		
		assertFalse(cc.addCourseToCatalog(NAME, TITLE, SECTION, 3, "asgangul", 10, "MW", 1300, 1500));
		
	}
	
	/**
	 * Testing RemoveCourseFromCatalog by first adding courses, then removing one and checking the 
	 * details. Next we try to remove a course thats not in the catalog to make sure that the method
	 * returns false. 
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		
		//removing valid course
		CourseCatalog cc = new CourseCatalog();
		
		cc.addCourseToCatalog(NAME, TITLE, SECTION, 3, "asgangul", 10, "MW", 1300, 1500);
		cc.addCourseToCatalog(NAME, TITLE, "002", 2, "asgangul", 10, "TF", 1300, 1500);
		
		assertEquals(2, cc.getCourseCatalog().length);
		cc.removeCourseFromCatalog(NAME, SECTION);
		
		String [][] courseCatalog = cc.getCourseCatalog();
		
		assertEquals(1, courseCatalog.length);
		assertEquals(NAME, courseCatalog[0][0]);
		assertEquals("002", courseCatalog[0][1]);
		assertEquals(TITLE, courseCatalog[0][2]);
		
		//removing invalid course
		assertFalse(cc.removeCourseFromCatalog(NAME, "007"));
		assertTrue(cc.removeCourseFromCatalog(NAME, "002"));
	}
	
	/**
	 * Testing saveCourseCatalog by first importing a file, then checking if the file matches the existing
	 * file. We also test if the method throws an exception when an illegal file path is added
	 */
	@Test
	public void testsaveCourseCatalog() {
		
		// valid save
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile("test-files/actual_course_records.txt");
		cc.saveCourseCatalog("test-files/course_save_test.txt");
		checkFiles("test-files/expected_course_records2.txt", "test-files/course_save_test.txt");
		
		//invalid save
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> cc.saveCourseCatalog("home/course_save_test.txt"));
		assertEquals("File could not be saved.", exception.getMessage());
		
		
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}