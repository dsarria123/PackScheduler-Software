package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;


/**
 * Tests StudentDirectory.
 * 
 * @author Sarah Heckman
 * @author Arnav Ganguly
 */
public class StudentDirectoryTest {

	/** Valid course records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "home/invalid_student_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 15;

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests StudentDirectory().
	 */
	@Test
	public void testStudentDirectory() {
		// Test that the StudentDirectory is initialized to an empty list
		StudentDirectory sd = new StudentDirectory();
		assertFalse(sd.removeStudent("sesmith5"));
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.testNewStudentDirectory().
	 */
	@Test
	public void testNewStudentDirectory() {
		// Test that if there are students in the directory, they
		// are removed after calling newStudentDirectory().
		StudentDirectory sd = new StudentDirectory();

		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);

		sd.newStudentDirectory();
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile().
	 */
	@Test
	public void testLoadStudentsFromFile() {
		StudentDirectory sd = new StudentDirectory();
		StudentDirectory sd2 = new StudentDirectory();

		// Test valid file
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);

		// Test invalid file
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> sd2.loadStudentsFromFile(invalidTestFile));
		assertEquals("Unable to read file " + invalidTestFile, exception.getMessage());
	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String[][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);

		// Test invalid passwords
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_CREDITS));
		assertEquals("Invalid password", exception.getMessage());

		Exception exception2 = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_CREDITS));
		assertEquals("Invalid password", exception2.getMessage());

		Exception exception3 = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, "", PASSWORD, MAX_CREDITS));
		assertEquals("Invalid password", exception3.getMessage());

		Exception exception4 = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "", MAX_CREDITS));
		assertEquals("Invalid password", exception4.getMessage());

		Exception exception5 = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "123456789", MAX_CREDITS));
		assertEquals("Passwords do not match", exception5.getMessage());

		// Test invalid credits
		StudentDirectory sd2 = new StudentDirectory();
		sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, 20);
		sd2.addStudent(FIRST_NAME, LAST_NAME, "sdenttwo", EMAIL, PASSWORD, PASSWORD, 2);
		String[][] studentDirectory2 = sd2.getStudentDirectory();

		// extra credits
		assertEquals(2, studentDirectory2.length);
		assertEquals(FIRST_NAME, studentDirectory2[0][0]);
		assertEquals(LAST_NAME, studentDirectory2[0][1]);
		assertEquals(ID, studentDirectory2[0][2]);

		// insufficient credits
		assertEquals(2, studentDirectory2.length);
		assertEquals(FIRST_NAME, studentDirectory2[1][0]);
		assertEquals(LAST_NAME, studentDirectory2[1][1]);
		assertEquals("sdenttwo", studentDirectory2[1][2]);

		// Test duplicate student record
		assertFalse(sd2.addStudent("John", "Doe", ID, "johndoe@ncsu.edu", PASSWORD, PASSWORD, MAX_CREDITS));
		assertFalse(sd2.addStudent("John", "Doe", "sdenttwo", "johndoe@ncsu.edu", PASSWORD, PASSWORD, MAX_CREDITS));

	}

	/**
	 * Tests StudentDirectory.removeStudent().
	 */
	@Test
	public void testRemoveStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Add students and remove
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		assertTrue(sd.removeStudent("efrost"));
		String[][] studentDirectory = sd.getStudentDirectory();
		assertEquals(9, studentDirectory.length);
		assertEquals("Zahir", studentDirectory[5][0]);
		assertEquals("King", studentDirectory[5][1]);
		assertEquals("zking", studentDirectory[5][2]);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();

		// Add a student
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		assertEquals(1, sd.getStudentDirectory().length);
		sd.saveStudentDirectory("test-files/actual_student_records.txt");
		checkFiles("test-files/expected_student_records_2.txt", "test-files/actual_student_records.txt");

		// Test invalid file name
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> sd.saveStudentDirectory("home/index.txt"));
		assertEquals("Unable to write to file home/index.txt", exception.getMessage());
	}
	
	/**
	 * Tests the directory's ability to find a student based on their ID
	 */
	@Test
	public void testGetStudentById() {
		StudentDirectory sd = new StudentDirectory();
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		Student s = new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", 15);
		Student test = sd.getStudentById("zking");
		assertEquals(s.getFirstName(), test.getFirstName());
		assertEquals(s.getLastName(), test.getLastName());
		assertEquals(s.getId(), test.getId());
		assertEquals(s.getEmail(), test.getEmail());
		assertEquals(s.getMaxCredits(), test.getMaxCredits());
		assertEquals(null, sd.getStudentById(ID));
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