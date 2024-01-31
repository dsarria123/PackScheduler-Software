package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.ArrayQueue;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * Represents a Courses CourseRoll, which keeps track of the number of Students that can enroll in a Course,
 * the seats left in a Course, the seats left on a Course's wait list, and whether a Student can enroll or not
 * Adds or drops a Student from the Course and/or Course waitlist
 * @author Diego Sarria
 * @author Chloe Israel
 */
public class CourseRoll {

	/**Custom LinkedAbstractList of Students*/
	private LinkedAbstractList<Student> roll;
	
	/**The roll's enrollment capacity*/
	private int enrollmentCap;
	
	/**The minimum class size*/
	private static final int MIN_ENROLLMENT = 10;
	
	/**The maximum class size*/
	private static final int MAX_ENROLLMENT = 250;
	
	/** The CourseRoll's list of people to enroll */
	private ArrayQueue<Student> waitlist;
	
	/**
	 * Constructs CourseRoll object
	 * @param enrollmentCap the capacity of enrollments
	 * @param c the Course associated with the CourseRoll
	 * @throws IllegalArgumentException if the passed in Course c is null
	 */
	public CourseRoll(Course c, int enrollmentCap) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		
		roll = new LinkedAbstractList<Student>(enrollmentCap);
		
		setEnrollmentCap(enrollmentCap);
		
		waitlist = new ArrayQueue<Student>(MIN_ENROLLMENT);
		
	}
	
	/**
	 * Gets the enrollment cap
	 * @return the capacity of enrollment
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}
	
	/**
	 * Returns difference between enrollment cap and size of roll
	 * @return open or available seats 
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}
	
	
	/**
	 * Sets the enrollment capacity
	 * @param enrollmentCap the enrollment capacity
	 * @throws IllegalArgumentException if cap is below the minimum or above the maximum
	 */
    public void setEnrollmentCap(int enrollmentCap) {
        if(enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
            throw new IllegalArgumentException("Enrollment capacity must be within the allowed range.");
        }
        
        if(enrollmentCap < roll.size()) {
            throw new IllegalArgumentException("Enrollment capacity cannot be less than the current number of enrollments.");
        }
       
        roll.setCapacity(enrollmentCap);

        this.enrollmentCap = enrollmentCap;
    }

	
	
	/**
	 * Adds the student to the roll or waitlist if roll is full
	 * @param s the student being added
	 * @throws IllegalArgumentException if s is null or adding the student produces an exception
	 */
	public void enroll(Student s) {
		
		if (s == null) {
			throw new IllegalArgumentException();
		}
		
		try {
			if (roll.size() == enrollmentCap) {
				waitlist.enqueue(s);
			} else if (canEnroll(s)) {
				roll.add(roll.size(), s);
			}
			
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Drops the student from the roll or waitlist
	 * @param s the student being dropped
	 * @throws IllegalArgumentException if s is null or removing causes an exception
	 */
	public void drop(Student s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		
		boolean inRoll = false;
		try {
			for (int i = 0; i < roll.size(); i++) {
				if (s == roll.get(i)) {
					inRoll = true;
					roll.remove(i);
					
					if (!waitlist.isEmpty()) {
						roll.add(waitlist.dequeue());
					}
					break;
				}
			}
			
			if (!inRoll) {
				ArrayQueue<Student> temp = new ArrayQueue<Student>(waitlist.size());
				for (int i = 0; i <  waitlist.size(); i++) {
					Student sTemp = waitlist.dequeue();
					
					if (sTemp == s) {
						break;
					} else if (sTemp != s) {
						temp.enqueue(sTemp);
					}
				}
				
				if (!temp.isEmpty()) {
					for (int i = 0; i < temp.size(); i++) {
						waitlist.enqueue(temp.dequeue());
					}
				}
				
			}

		} catch (Exception e) {
			throw new IllegalArgumentException();
		
		}
	}
	
	/**
	 * Returns true if Student can be added to roll or waitlist, false if there is no room or they're already enrolled
	 * @param s the student
	 * @return true if student can enroll, false if student cannot enroll
	 */
	public boolean canEnroll(Student s) {
		boolean canEnroll = true;
		
		if (roll.size() == this.enrollmentCap && waitlist.size() > 9) {
			canEnroll = false;
		} else if (roll.contains(s)) {
			canEnroll = false;
		}
		
		// Check if in waitlist
		ArrayQueue<Student> temp = new ArrayQueue<Student>(waitlist.size());
		for (int i = 0; i <  waitlist.size(); i++) {
			Student sTemp = waitlist.dequeue();
			
			if (sTemp == s) {
				canEnroll = false;
				temp.enqueue(sTemp);
				break;
			} else {
				temp.enqueue(sTemp);
			}
		}
		
		for (int i = 0; i < temp.size(); i++) {
			waitlist.enqueue(temp.dequeue());
		}
		
		return canEnroll;
	}
	
	/**
	 * Gets the number of students currently on the waitlist
	 * @return the size of the waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
}
