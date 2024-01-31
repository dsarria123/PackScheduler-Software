package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Main class for the schedule object which utilizes custom array lists to help
 * students create and visualize their schedules
 * @author Peyton Herring
 * @author Dee Kandel
 * @author Diego Sarria
 */
public class Schedule {
	
	/** Title of a schedule */
	private String title;
	/** Custom array list of courses */
	private ArrayList<Course> schedule;
	
	/**
	 * Default constructor for schedules
	 */
	public Schedule() {
		schedule = new ArrayList<Course>();
		title = "My Schedule";
	}
	
	/**
	 * Gets the current schedule of courses
	 * @return the scheduled courses
	 */
	public ArrayList<Course> getSchedule() {
		return schedule;
	}
	
	/**
	 * Gets the title of the schedule
	 * @return the schedule title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Adds a course to the schedule
	 * @param c the course to be added
	 * @return true if the course was added
	 * @throws IllegalArgumentException if the course is a duplicate or has a conflict with another course
	 * The array list will throw a null pointer exception if c is null
	 */
	public boolean addCourseToSchedule(Course c) {

		for (int i = 0; i < schedule.size(); i++) {
			Course test = schedule.get(i);
			if (c.isDuplicate(test)) {
				throw new IllegalArgumentException("You are already enrolled in " + c.getName());
			}
			try {
				c.checkConflict(test);
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}
		schedule.add(schedule.size(), c); //Adds c to the end of the list
		return true;
	}
	
	/**
	 * Removes a course from the schedule
	 * @param c the course to remove
	 * @return true if the course was removed, false if nothing was removed
	 */
	public boolean removeCourseFromSchedule(Course c) {
		for (int i = 0; i < schedule.size(); i++) {
			Course test = schedule.get(i);
			if (c != null && c.compareTo(test) == 0) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Resets the current schedule to be a blank array list and the title resets too
	 */
	public void resetSchedule() {
		this.schedule = new ArrayList<Course>();
		this.title = "My Schedule";
	}
	
	/**
	 * Gets the scheduled courses into a 2D array
	 * @return the scheduled courses name, section, title, and meeting string
	 */
	public String[][] getScheduledCourses() {
		String[][] courses = new String[schedule.size()][5];
		for (int i = 0; i < schedule.size(); i++) {
			Course temp = schedule.get(i);
			String[] courseArray = temp.getShortDisplayArray();
			courses[i][0] = courseArray[0];
			courses[i][1] = courseArray[1];
			courses[i][2] = courseArray[2];
			courses[i][3] = courseArray[3];
			courses[i][4] = courseArray[4];
		}
		return courses;
	}
	
	/**
	 * Sets the title of the schedule
	 * @param title the title to set
	 * @throws IllegalArgumentException if title is null
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}
	
	/**
	 * Cumulative sum that returns the total credits in the Schedule
	 * @return total credits in the Schedule
	 */
	public int getScheduleCredits() {
		int totalCredits = 0;
		
		for(int i = 0; i < schedule.size(); i++) {
			Course c = schedule.get(i);
			totalCredits += c.getCredits();
		}
		return totalCredits;
	}
	
	/**
	 * Returns true if the Course can be added to the schedule
	 * @param c check if Course c can be added to schedule
	 * @return true if Course can be added to schedule, 
	 * false if Course is null, duplicate, or conflict exists
	 */
	public boolean canAdd(Course c) {
		//check if null
		if(c == null) {
			return false;
		}
		
		//check duplicate
		for (int i = 0; i < schedule.size(); i++) {
			Course test = schedule.get(i);
			if (c.isDuplicate(test)) {
				return false;
			}
			
			//check conflict
			try {
				c.checkConflict(test);
			} catch (ConflictException e) {
				return false;
			}
		}
		
		return true;

	}
}
