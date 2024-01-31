package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Tests FacultyRecordIO.
 */
public class FacultyRecordIOTest {

	/**
	 * VTF
	 */
    private final String validTestFile = "test-files/faculty_records.txt";
    
    /**
     * ITF
     */
    private final String invalidTestFile = "test-files/invalid_faculty_records.txt";
    
    /**
     * VTOF
     */
    private final String validTestOutputFile = "test-files/output_faculty_records.txt";
    
    /**
     * HASHPW YES
     */
    private String hashPW;
    
    /**
     * HAS ALGO NO
     */
    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Got this from StudentRecordIOTest
     * @throws Exception e
     */
    @BeforeEach
    public void setUp() throws Exception {
        try {
            String password = "pw";
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(password.getBytes());
            hashPW = Base64.getEncoder().encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            fail("An unexpected NoSuchAlgorithmException was thrown.");
        }
    }

    /**
     * Test read faculty records
     */
    @Test
    public void testReadFacultyRecords() {
        try {
            LinkedList<Faculty> facultyList = FacultyRecordIO.readFacultyRecords(validTestFile);
            assertEquals(8, facultyList.size());
            // Additional assertions as needed for other records
        } catch (IOException e) {
            fail("Unexpected IOException was thrown: " + e.getMessage());
        }
    }

    /**
     * Test read invalid faculty records
     */
    @Test
    public void testReadInvalidFacultyRecords() {
        try {
            LinkedList<Faculty> facultyList = FacultyRecordIO.readFacultyRecords(invalidTestFile);
            assertEquals(0, facultyList.size());
        } catch (IOException e) {
            fail("Unexpected IOException was thrown: " + e.getMessage());
        }
    }

    /**
     * Test write faculty records
     * @throws IOException e
     */
    @Test
    public void testWriteFacultyRecords() throws IOException {
        LinkedList<Faculty> facultyList = new LinkedList<>();
        facultyList.add(new Faculty("Ashely", "Witt", "awitt", "email1@ncsu.edu", hashPW, 2));
        FacultyRecordIO.writeFacultyRecords(validTestOutputFile, facultyList);
        List<String> fileContents = Files.readAllLines(Paths.get(validTestOutputFile));
        assertEquals(facultyList.size(), fileContents.size());
        for (int i = 0; i < facultyList.size(); i++) {
            assertEquals(facultyList.get(i).toString(), fileContents.get(i));
        }
    }

}
