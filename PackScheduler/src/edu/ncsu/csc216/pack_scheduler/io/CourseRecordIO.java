package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files. Writes a set of CourseRecords to a
 * file.
 * 
 * @author Diego Sarria
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file
		SortedList<Course> courses = new SortedList<Course>(); // Create an empty array of Course objects
		while (fileReader.hasNextLine()) { // While we have more lines in the file
			try { // Attempt to do the following
					// Read the line, process it in readCourse, and get the object
					// If trying to construct a Course in readCourse() results in an exception, flow
					// of control will transfer to the catch block, below
				Course course = readCourse(fileReader.nextLine());

				// Create a flag to see if the newly created Course is a duplicate of something
				// already in the list
				boolean duplicate = false;
				// Look at all the courses in our list
				for (int i = 0; i < courses.size(); i++) {
					// Get the course at index i
					Course current = courses.get(i);
					// Check if the name and section are the same
					if (course.getName().equals(current.getName())
							&& course.getSection().equals(current.getSection())) {
						// It's a duplicate!
						duplicate = true;
						break; // We can break out of the loop, no need to continue searching
					}
				}
				// If the course is NOT a duplicate
				if (!duplicate) {
					courses.add(course); // Add to the ArrayList!
				} // Otherwise ignore
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a course, skip it!
			}
		}
		// Close the Scanner b/c we're responsible with our file handles
		fileReader.close();
		// Return the ArrayList with all the courses we read!
		return courses;
	}

	/**
	 * This is the helper method for readCourseRecords that helps read the file.
	 * 
	 * @param nextLine is the line from the file
	 * @return Course object if all of the fields are correctly present in the file
	 * @throws IllegalArgumentException if the line has extra fields
	 */
	private static Course readCourse(String nextLine) {
		Scanner scanner = new Scanner(nextLine);
		scanner.useDelimiter(",");

		try {
			String name = scanner.next();
			String title = scanner.next();
			String section = scanner.next();
			int creditHours = scanner.nextInt();
			String instructor = scanner.next();
			int enrollmentCap = scanner.nextInt();
			String meetingDay = scanner.next();

			if ("A".equals(meetingDay)) {
				if (scanner.hasNext()) {
					scanner.close();
					throw new IllegalArgumentException("Invalid line");
				} else {
					scanner.close();
					Course course = new Course(name, title, section, creditHours, null, enrollmentCap, meetingDay, 0, 0);
					
					FacultyDirectory facultyDirectory = RegistrationManager.getInstance().getFacultyDirectory();
					FacultyDirectory facultyDirectory2 = new FacultyDirectory();
					if (facultyDirectory == facultyDirectory2) {
						Faculty faculty = facultyDirectory.getFacultyById(instructor);
						if (faculty != null) {
							faculty.schedule.addCourseToSchedule(course);
						}
					}
					return course;
				}
			} else {

				int startTime = scanner.nextInt();
				int endTime = scanner.nextInt();

				if (scanner.hasNext()) {
					scanner.close();
					throw new IllegalArgumentException("Invalid line");
				}
				scanner.close();
				Course course = new Course(name, title, section, creditHours, null, enrollmentCap, meetingDay, startTime, endTime);
				FacultyDirectory facultyDirectory = RegistrationManager.getInstance().getFacultyDirectory();
				FacultyDirectory facultyDirectory2 = new FacultyDirectory();
				if (facultyDirectory == facultyDirectory2) {
					Faculty faculty = facultyDirectory.getFacultyById(instructor);
					if (faculty != null) {
						faculty.schedule.addCourseToSchedule(course);
					}
				}
				return course;
			}

		} catch (Exception e) {
			scanner.close();
			throw new IllegalArgumentException("Invalid line");

		}

	}
	
	/**
	 * Writes the Course records into a external file
	 * @param fileName name of the file that the records will be exported as
	 * @param courses the array list for all the courses
	 * @throws IOException when the file path cannot be found
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();

	}

}