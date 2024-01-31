package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Tests StudentRecordIO
 * 
 * Test for student IO java
 * @author Peyton Herring
 * @author Aaron Green
 * @author Arnav Ganguly
 */
class StudentRecordIOTest {

	/**
	 * Path for valid test-file
	 */
	private String validFile = "test-files/student_records.txt";
	/**
	 * Path for invalid test-file
	 */
	private String invalidFile = "test-files/invalid_student_records.txt";
	/**
	 * Path for test file with duplicate records
	 */
	private String duplicateFile = "test-files/duplicate_records.txt";

	/**
	 * Valid Record 1
	 */
	private String validStudent6 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/**
	 * Valid Record 2
	 */
	private String validStudent8 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/**
	 * Valid Record 3
	 */
	private String validStudent4 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/**
	 * Valid Record 4
	 */
	private String validStudent0 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/**
	 * Valid Record 5
	 */
	private String validStudent2 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/**
	 * Valid Record 6
	 */
	private String validStudent3 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/**
	 * Valid Record 7
	 */
	private String validStudent1 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/**
	 * Valid Record 8
	 */
	private String validStudent9 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	/**
	 * Valid Record 9
	 */
	private String validStudent5 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/**
	 * Valid Record 10
	 */
	private String validStudent7 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";

	/**
	 * Valid String array of all the records in student_records.txt
	 */
	private String[] validStudents = { validStudent0, validStudent1, validStudent2, validStudent3, validStudent4,
			validStudent5, validStudent6, validStudent7, validStudent8, validStudent9 };

	/**
	 * This is the variable that stores the hashCode for the password "pw"
	 */
	private String hashPW;
	/**
	 * This is algorithm used to hash the password
	 */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Sets up password before each test
	 * Fails test if exception is thrown
	 */
	@BeforeEach
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = Base64.getEncoder().encodeToString(digest.digest());

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}

	/**
	 * Tests StudentRecordIO.readStudentRecords
	 */
	@Test
	void testReadStudentRecords() {
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords("test-files/student_records.txt");
			// testing size
			//assertEquals(10, students.size());
			// testing order and state
			for (int i = 0; i < students.size(); i++) {
				assertEquals(validStudents[i], students.get(i).toString());
			}
			SortedList<Student> studentsEmpty = StudentRecordIO.readStudentRecords(invalidFile);
			// testing to check the array list is empty
			assertEquals(0, studentsEmpty.size());

			// testing with files that has duplicate records
			SortedList<Student> studentsDuplicate = StudentRecordIO.readStudentRecords(duplicateFile);
			assertEquals(2, studentsDuplicate.size());

			// checking if an invalid path leads to the method throwing a
			// FileNotFoundException
			Exception exception = assertThrows(FileNotFoundException.class,
					() -> StudentRecordIO.readStudentRecords("/home/sesmith5/invalid_file_name.txt"));
			assertEquals("File does not exist", exception.getMessage());

		} catch (FileNotFoundException e) {
			fail("Project Structure is invalid");
		}
	}

	
	/**
	 * Tests StudentRecordIO.writeStudentRecords
	 */
	@Test
	void testWriteStudentRecords() {
		try {
			// testing to see if the result of the method is an expected output
			SortedList<Student> studentTest = new SortedList<Student>();
			// adding all studentrecords.txt records to a studentTest
			for (int i = 0; i < validStudents.length; i++) {
				String[] studentRecord = validStudents[i].split(",");
				studentTest.add(new Student(studentRecord[0], studentRecord[1], studentRecord[2], studentRecord[3],
						hashPW, Integer.parseInt(studentRecord[5])));
			}

			StudentRecordIO.writeStudentRecords(validFile, studentTest);

			checkFiles("test-files/expected_student_records.txt", validFile);

			// testing checkFiles
			// checkFiles("test-files/extra_student_records.txt", validFile); //extra
			// records
			// checkFiles("test-files/insufficient_student_records.txt", validFile); //not
			// enough records

			// testing to check if the method throw a IOException when invalid file path is
			// given
			SortedList<Student> students = new SortedList<Student>();
			students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));

			Exception exception = assertThrows(IOException.class,
					() -> StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students));
			assertEquals("/home/sesmith5/actual_student_records.txt (No such file or directory)",
					exception.getMessage());
		} catch (IOException e) {
			fail("Project Structure is invalid");
		}
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));) {

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act);
				// The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
