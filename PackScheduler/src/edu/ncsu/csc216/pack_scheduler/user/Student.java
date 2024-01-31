package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Represents a Student, getting its max credits, schedule, and whether a Student can add a Course
 * Extends the User super class and implements the Comparable interface to compare two Students
 * 
 * @author Peyton Herring
 * @author Arnav Ganguly
 * @author Diego Sarria
 */
public class Student extends User implements Comparable<Student> {

	/** Credits variable */
	private int maxCredits;
	/** Constant for max credits */
	public static final int MAX_CREDITS = 18;
	/** Student schedule variable */
	private Schedule schedule = new Schedule();

	/**
	 * Constructor for Student
	 * 
	 * @param firstName  first name of student
	 * @param lastName   last name of student
	 * @param id         student ID
	 * @param email      student email
	 * @param hashPW     student hash pw
	 * @param maxCredits students max credits
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCredits(maxCredits);
	}

	/**
	 * Alternate constructor if no max credits given
	 * 
	 * @param firstName first name of student
	 * @param lastName  last name of student
	 * @param id        student ID
	 * @param email     student email
	 * @param hashPW    student hash pw
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);
	}

	/**
	 * Gets the students max credits
	 * 
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets students max credits
	 * 
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if credits is more than 18 or less than 3
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * This is the method that converts students to a String format for displaying
	 * in the GUI
	 */
	@Override
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," + this.getEmail() + "," + this.getPassword() + "," + maxCredits;
	}

	/**
	 * This is the method that helps us compare two students and check which student
	 * is higher alphabetically, using first name, last name and student id.
	 * 
	 * @throws NullPointerException if the object is null
	 * @throws ClassCastException   is the object is not a student
	 * @return 1 if this student is higher, -1 if the student that is compared to is
	 *         higher and 0 if both of the students are equal.
	 */
	@Override
	public int compareTo(Student s) {

		if (s == null) {
			throw new NullPointerException("The object entered is null");
		}

		else if (!(s instanceof Student)) {
			throw new ClassCastException("The object entered is not a Student");
		} else {

			if (!this.getLastName().equals(s.getLastName())) {
				SortedList<String> lastNameList = new SortedList<String>();
				lastNameList.add(this.getLastName());
				lastNameList.add(s.getLastName());
				if (this.getLastName().equals(lastNameList.get(0))) {
					lastNameList.clear();
					return -1; // this student is before Student s
				} else {
					lastNameList.clear();
					return 1; // this student is after Student s
				}
			} else if (!this.getFirstName().equals(s.getFirstName())) {
				SortedList<String> firstNameList = new SortedList<String>();
				firstNameList.add(this.getFirstName());
				firstNameList.add(s.getFirstName());
				if (this.getFirstName().equals(firstNameList.get(0))) {
					firstNameList.clear();
					return -1; // this student is before Student s
				} else {
					firstNameList.clear();
					return 1; // this student is after Student s
				}
			} else if (!this.getId().equals(s.getId())) {
				SortedList<String> unityID = new SortedList<String>();
				unityID.add(this.getId());
				unityID.add(s.getId());
				if (this.getId().equals(unityID.get(0))) {
					unityID.clear();
					return -1;
				} else {
					unityID.clear();
					return 1;
				}
			} else {
				return 0; // Students are identical
			}

		}
	}

	/**
	 * Generates hash code of students
	 * @return the hash code as int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Tests if 2 students are equal
	 * @return true if they are the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}
	
	/**
	 * Gets the current students schedule
	 * @return the current students schedule
	 */
	public Schedule getSchedule() {
		return this.schedule;
	}
	
	/**
	 * Returns true if the Course can be added to the Student's Schedule
	 * @param c Course to check
	 * @return true if Course can be added, false if it cannot be added
	 */
	public boolean canAdd(Course c) {
		boolean canAdd = true;
		
		//check if null, duplicate, conflict exists
		//if schedule cannot add the course in Schedule.canAdd(), return false
		if (!this.getSchedule().canAdd(c)) {
			canAdd = false;
			
		//if student has no more room in Schedule (exceeds max credits), return false			
		} else if (this.getSchedule().getScheduleCredits() + c.getCredits() > maxCredits) {
			canAdd = false;
		}
		


		
		return canAdd;
	}

}