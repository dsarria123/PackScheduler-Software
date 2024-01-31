package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Class for registration managers which includes users and registrars to set up schedules/catalogs.
 * This class also allows users to 'login' and 'logout' to view their respective abilities/schedules.
 * Only one registration manager may be open at a single time, that manager may clear all of it's data.
 * @author Peyton Herring
 * @author Dee Kandel
 * @author Diego Sarria
 */
public class RegistrationManager {
	
	/** A singular registration manager may be created */
	private static RegistrationManager instance;
	
	/** The catalog to be used by the manager */
	  private CourseCatalog courseCatalog;
	  
	/** The directory to be used by the manager */
	private StudentDirectory studentDirectory;
	
	/** The directory to be used by the faculty */
	private FacultyDirectory facultyDirectory;

	  /** The variable for a registrar user */
	  private User registrar;
	  
	  /** The variable for the current active user */
	   private User currentUser;
	   
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** Prop file constant */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Constructor for registration manager, only one manager may be created at a time
	 */
	private RegistrationManager() {
		createRegistrar();
		this.courseCatalog = new CourseCatalog();
		this.studentDirectory = new StudentDirectory();
		this.facultyDirectory = new FacultyDirectory();
	}

	/**
	 * Creates the registrar
	 * @throws IllegalArgumentException if the registrar cannot be created
	 */
	private void createRegistrar() {
	    Properties prop = new Properties();
	    
	    try (InputStream input = new FileInputStream(PROP_FILE)) {
	        prop.load(input);
	        
	        String hashPW = hashPW(prop.getProperty("pw"));
	        
	        registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"), prop.getProperty("email"), hashPW);
	    } catch (IOException e) {
	        throw new IllegalArgumentException("Cannot create registrar.");
	    }
	}

	/**
	 * Hashes a password
	 * @param pw the pw to be hashed
	 * @return pw in hashed form
	 * @throws IllegalArgumentException if the password cannot be hashed
	 */
	private String hashPW(String pw) {
	    try {
	        MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
	        digest1.update(pw.getBytes());
	        return Base64.getEncoder().encodeToString(digest1.digest());
	    } catch (NoSuchAlgorithmException e) {
	        throw new IllegalArgumentException("Cannot hash password");
	    }
	}

	
	/**
	 * Creates a registration manager if one has not been made
	 * Otherwise return the current manager
	 * @return the registration manager
	 */
	public static RegistrationManager getInstance() {
		  if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}
	
	/**
	 * Gets the course catalog
	 * @return the course catalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}
	
	/**
	 * Gets the student directory
	 * @return the student director
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}
	
	/**
	 * Gets the faculty directory
	 * @return the faculty director
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Attempts to login as a user
	 * @param id the id of the user
	 * @param password the password of the user
	 * @return true if able to login, false otherwise
	 * @throws IllegalArgumentException if no user has the id
	 */
	public boolean login(String id, String password) {
		if (studentDirectory.getStudentById(id) != null) {
			Student s = studentDirectory.getStudentById(id);
			if (currentUser != null) {
				return false;
			}
			
			String localHashPW = hashPW(password);
			if (s != null && s.getPassword().equals(localHashPW)) {
				
				currentUser = s;
				return true;
			}
			
			if (registrar.getId().equals(id) && registrar.getPassword().equals(localHashPW)) {
				currentUser = registrar;
				return true;
			}
			
			if (s == null && !registrar.getId().equals(id)) {
				throw new IllegalArgumentException("User doesn't exist.");
			}
			return false;
		} else {
			Faculty s = facultyDirectory.getFacultyById(id);
			if (currentUser != null) {
				return false;
			}
			
			String localHashPW = hashPW(password);
			if (s != null && s.getPassword().equals(localHashPW)) {
				
				currentUser = s;
				return true;
			}
			
			if (registrar.getId().equals(id) && registrar.getPassword().equals(localHashPW)) {
				currentUser = registrar;
				return true;
			}
			
			if (s == null && !registrar.getId().equals(id)) {
				throw new IllegalArgumentException("User doesn't exist.");
			}
			return false;
		}
	}

	/**
	 * Resets the current user to 'log out' from another user
	 */
	public void logout() {
		currentUser = null; 
	}
	
	/**
	 * Gets the current user
	 * @return the current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Clears the directory and catalog of all contents
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
		currentUser = null;
	}
	
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 * @throws IllegalArgumentException if the currentUser isn't an instance of Student
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 * @throws IllegalArgumentException if the currentUser isn't an instance of Student
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 * @throws IllegalArgumentException if the currentUser isn't an instance of Student
	 * 
	 */
	public void resetSchedule() {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
	
	/**
	 * Method that adds a faculty to the course if the current user is the Registrar
	 * @param course the course being added 
	 * @param faculty the faculty accepting the course
	 * @return boolean, true if added, false if not
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
		if (currentUser != null && registrar.getId().equals(currentUser.getId())) {
			faculty.getSchedule().addCourseToSchedule(course);
			return true;
		}
		return false;
	}
	
	/**
	 * Method that removes a faculty from the course if the current user is the Registrar
	 * @param course the course being added 
	 * @param faculty the faculty accepting the course
	 * @return boolean, true if added, false if not
	 */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
		if (currentUser != null && registrar.getId().equals(currentUser.getId())) {
			faculty.getSchedule().removeCourseFromSchedule(course);
			return true;
		}
		return false;
	}
	
	/**
	 * Method that resets the faculties schedule if the current user is the Registrar
	 * @param faculty the faculty having their courses being cleared
	 */
	public void resetFacultySchedule(Faculty faculty) {
		if (currentUser != null && registrar.getId().equals(currentUser.getId())) {
			faculty.getSchedule().resetSchedule();
		}
	}
	
	
	/**
	 * Private user variable registrar
	 */
	private static class Registrar extends User {
		
		/**
		 * Constructor for a registrar
		 * @param firstName of registrar
		 * @param lastName of registrar
		 * @param id of registrar
		 * @param email of registrar
		 * @param hashPW password of registrar
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
		
		
	}
}
