package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Represents a Faculty member, including their course load capabilities.
 * Extends the User super class and implements the Comparable interface to compare two Faculty members.
 * The structure is similar to the Student class, but with maxCourses instead of maxCredits.
 * @author Diego Sarria
 */
public class Faculty extends User implements Comparable<Faculty> {

    /** Faculty's maximum number of courses they can teach */
    private int maxCourses;
    
    /** Constant for the default maximum number of courses a faculty can teach */
    public static final int MAX_COURSES = 3;
    
    /** Constant for the default minimum number of Course a Faculty can teach */
    public static final int MIN_COURSES = 1;
    
    /** The schedule of Courses for the Faculty to teach */
    public FacultySchedule schedule;

    /**
     * Constructor for Faculty with all fields.
     * Creates the Faculty's schedule of Courses
     * @param firstName  the first name of the faculty
     * @param lastName   the last name of the faculty
     * @param id         the faculty ID
     * @param email      the faculty email
     * @param hashPW     the hashed password
     * @param maxCourses the maximum number of courses the faculty can teach
     */
    public Faculty(String firstName, String lastName, String id, String email, String hashPW, int maxCourses) {
        super(firstName, lastName, id, email, hashPW);
        setMaxCourses(maxCourses);
        this.schedule = new FacultySchedule(id);

    }

    /**
     * Gets the FacultySchedule
     * @return FacultySchedule schedule
     */
    public FacultySchedule getSchedule() {
        return schedule;
        
    }

    /**
     * Gets whether the scheduled Courses are greater than the maximum Courses
     * @return boolean true if the number of scheduled Courses are greater than the maximum Course
     * 		   or false if not
     */

    public boolean isOverloaded() {
    	return schedule.getNumScheduledCourses() > getMaxCourses();
    	
    }


    /**
     * Gets the faculty's maximum number of Courses it can teach
     * @return int maxCourses
     */
    public int getMaxCourses() {
        return maxCourses;
    }

    /**
     * Sets the faculty's maximum courses.
     * @param maxCourses the maxCourses to set
     * @throws IllegalArgumentException if maxCourses is less than 1 or greater than 3
     */
    public void setMaxCourses(int maxCourses) {
        if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
            throw new IllegalArgumentException("Invalid max courses");
        }
        this.maxCourses = maxCourses;
    }

    /**
     * Generates a hashCode for the Faculty's maximum Courses
     * @return the hash code for maxCourses as int
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + maxCourses;
        return result;
    }

    /**
     * Tests if two Faculty members are equal based on their maximum Courses
     * 
     * @param obj the object to compare
     * @return true if they are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Faculty))
            return false;
        Faculty other = (Faculty) obj;
        return maxCourses == other.maxCourses;
    }

    /**
     * Compares two Faculty members for ordering.
     * 
     * @param other the Faculty to be compared
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the other Faculty object is null
     */
    @Override
    public int compareTo(Faculty other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare to null.");
        }

        // Compare last names
        int lastNameComparison = this.getLastName().compareTo(other.getLastName());
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }

        // If last names are the same, compare first names
        int firstNameComparison = this.getFirstName().compareTo(other.getFirstName());
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }

        // If first names are also the same, compare IDs
        return this.getId().compareTo(other.getId());
    }


    /**
     * Converts the Faculty to a String format containing all fields for display purposes
     * 
     * @return the string representation of the Faculty
     */
    @Override
    public String toString() {
        return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + maxCourses;
    }
}

