/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * The Conflict Interface is where we will make sure that there is no conflict between any courses or 
 * events. It is an interface because when it is implemented to a class, the class will have to define 
 * the checkConflict method to check for conflicts.
 * 
 */
public interface Conflict {
	
	/**
	 * This is the method that checks for a conflicting time overlap between events and courses
	 * @param possibleConflictingActivity is the activity that maybe conflicting with our current activity
	 * @throws ConflictException is the exception thrown that cannot be ignored
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

	
}
