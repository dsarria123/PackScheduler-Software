package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * Tests FacultyDirectory.
 */
public class FacultyDirectoryTest {

    /** Valid faculty records */
    private final String validTestFile = "test-files/faculty_records.txt";
    /** Test first name */
    private static final String FIRST_NAME = "Jane";
    /** Test last name */
    private static final String LAST_NAME = "Doe";
    /** Test id */
    private static final String ID = "jdoe";
    /** Test email */
    private static final String EMAIL = "jdoe@email.com";
    /** Test password */
    private static final String PASSWORD = "password";
    /** Test max courses */
    private static final int MAX_COURSES = 3;

    /**
     * Resets faculty_records.txt for use in other tests.
     */
    @Before
    public void setUp() throws Exception {
        // Reset faculty_records.txt to its original state
        Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_faculty_records.txt");
        Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
        try {
            Files.deleteIfExists(destinationPath);
            Files.copy(sourcePath, destinationPath);
        } catch (IOException e) {
            fail("Unable to reset files");
        }
    }

    /**
     * Tests FacultyDirectory().
     */
    @Test
    public void testFacultyDirectory() {
        FacultyDirectory fd = new FacultyDirectory();
        assertFalse(fd.removeFaculty(ID));
        assertEquals(0, fd.getFacultyDirectory().length);
    }

    /**
     * Tests FacultyDirectory.newFacultyDirectory().
     */
    @Test
    public void testNewFacultyDirectory() {
        FacultyDirectory fd = new FacultyDirectory();

        fd.loadFacultyFromFile(validTestFile);
        assertEquals(8, fd.getFacultyDirectory().length);

        fd.newFacultyDirectory();
        assertEquals(0, fd.getFacultyDirectory().length);
    }

    /**
     * Tests FacultyDirectory.loadFacultyFromFile().
     */
    @Test
    public void testLoadFacultyFromFile() {
        FacultyDirectory fd = new FacultyDirectory();

        // Test valid file
        fd.loadFacultyFromFile(validTestFile);
        assertEquals(8, fd.getFacultyDirectory().length);

        // Test invalid file
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> fd.loadFacultyFromFile("invalid_file.txt"));
        assertEquals("Unable to read file invalid_file.txt", exception.getMessage());
    }

    /**
     * Tests FacultyDirectory.addFaculty().
     */
    @Test
    public void testAddFaculty() {
        FacultyDirectory fd = new FacultyDirectory();

        // Test valid Faculty
        fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
        String[][] facultyDirectory = fd.getFacultyDirectory();
        assertEquals(1, facultyDirectory.length);
        assertEquals(FIRST_NAME, facultyDirectory[0][0]);
        assertEquals(LAST_NAME, facultyDirectory[0][1]);
        assertEquals(ID, facultyDirectory[0][2]);

        // Test adding a duplicate faculty
        assertFalse(fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));

        // Test invalid passwords
        assertThrows(IllegalArgumentException.class,
                () -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", "", MAX_COURSES));
    }

    /**
     * Tests FacultyDirectory.removeFaculty().
     */
    @Test
    public void testRemoveFaculty() {
        FacultyDirectory fd = new FacultyDirectory();

        // Add a faculty and remove
        fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
        assertEquals(1, fd.getFacultyDirectory().length);
        assertTrue(fd.removeFaculty(ID));
        assertEquals(0, fd.getFacultyDirectory().length);

        // Try to remove a non-existent faculty
        assertFalse(fd.removeFaculty("nonexistent"));
    }

    /**
     * Tests FacultyDirectory.saveFacultyDirectory().
     */
    @Test
    public void testSaveFacultyDirectory() {
        FacultyDirectory fd = new FacultyDirectory();

        // Add a faculty and save directory to a file
        fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
        assertEquals(1, fd.getFacultyDirectory().length);
        fd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
        checkFiles("test-files/Untitled 1", "test-files/actual_faculty_records.txt");

        // Test saving to an invalid file
        assertThrows(IllegalArgumentException.class,
                () -> fd.saveFacultyDirectory("/home/invalid_file.txt"));
    }

    /**
     * Helper method to compare two files for the same contents
     * 
     * @param expFile expected output
     * @param actFile actual output
     */
    private void checkFiles(String expFile, String actFile) {
        try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
             Scanner actScanner = new Scanner(new FileInputStream(actFile))) {

            while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
                assertEquals(expScanner.nextLine(), actScanner.nextLine());
            }
            assertFalse(expScanner.hasNext());
            assertFalse(actScanner.hasNext());
        } catch (IOException e) {
            fail("Error reading files: " + e.getMessage());
        }
    }


}
