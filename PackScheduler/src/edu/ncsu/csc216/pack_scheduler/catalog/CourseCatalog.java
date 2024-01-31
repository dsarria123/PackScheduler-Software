package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;
import edu.ncsu.csc217.collections.list.SortedList;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * Class for the course catalog Stores all classes/courses in a catalog and
 * sorts them using a sorted list
 * 
 * @author Peyton Herring
 * @author Diego Sarria
 */
public class CourseCatalog {

	// Creating fields
	/**
	 * The array list for catalog of all courses
	 */
	private SortedList<Course> catalog;

	/**
	 * This is method that helps us make a 2D String array using the catalog that
	 * makes it easier for the GUI to display the catalog It returns an empty array
	 * if catalog is null
	 * 
	 * @return courseCatalog thats a 2D string array of the name, section and title
	 *         of the courses in the catalog
	 */
	public String[][] getCourseCatalog() {
		if (catalog == null) {
			return new String[0][0];
		} else {
			String[][] courseCatalog = new String[catalog.size()][4];

			for (int i = 0; i < catalog.size(); i++) {
				Course c = catalog.get(i);
				courseCatalog[i] = c.getShortDisplayArray();
			}

			return courseCatalog;
		}
	}

	/**
	 * Creates a new and blank course catalog
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}

	/**
	 * Constructs a new blank course catalog
	 */
	public CourseCatalog() {
		catalog = new SortedList<Course>();
	}

	/**
	 * This method gets the course from the catalog using the parameters
	 * 
	 * @param name    of the course
	 * @param section of the course
	 * @return the course as a Course object or null if the course can't be found
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (course.getName().equals(name) && course.getSection().equals(section)) {
				return course;
			}
		}
		return null;
	}

	/**
	 * This method helps add a course to schedule
	 * 
	 * @param name         of the course to be added
	 * @param section      of the course to be added
	 * @param title        of the course to be added
	 * @param credits      number of credits for the course
	 * @param instructorId instructor of the course
	 * @param enrollmentCap the enrollment capacity of the course
	 * @param meetingDays  days the course meets
	 * @param startTime    time the course starts
	 * @param endTime      time the course ends
	 * @return true if the addition was successful, or false if it wasn't
	 * @throws IllegalArgumentException if the course is already added or there's a
	 *                                  conflict
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			int enrollmentCap, String meetingDays, int startTime, int endTime) {
		
	
		
		
		Course selectedCourse;
		try {
			selectedCourse = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Course could not be constructed.");
		}

		for (int i = 0; i < catalog.size(); i++) {
			Course catalogCourses = catalog.get(i);
			if (catalogCourses.getName().equals(name) && catalogCourses.getSection().equals(section)) {
				return false;
			}
		}

		catalog.add(selectedCourse);
		return true;

	}

	/**
	 * removes a course from schedule if name and section are found
	 * 
	 * @param name    of the course to be removed
	 * @param section of the course to be removed
	 * @return true if the course was removed false otherwise
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		try {
			for (int i = 0; i < catalog.size(); i++) {
				if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
					catalog.remove(i);
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Reads a file and applies it to catalog
	 * 
	 * @param fileName name of file to use
	 * @throws IllegalArgumentException if FileNotFound exception is caught meaning
	 *                                  the file path is invalid
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File could not be read.");
		}

	}

	/**
	 * Saves the course catalog to a given file
	 * 
	 * @param fileName file to be saved to
	 * @throws IllegalArgumentException when the file cannot be saved meaning the
	 *                                  file path cannot be found
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("File could not be saved.");
		}
	}
}
