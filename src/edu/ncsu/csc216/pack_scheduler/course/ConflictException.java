/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * This is the conflict Exception class that handles the creation of a new exception. We had to create
 * a new exception for conflict errors so that they cannot be ignored by the user and need to be handled
 * by the developer. The class is a child class of Exception which means it inherits all its functionalities
 * @author Arnav Ganguly
 */
public class ConflictException extends Exception {
	/**
	 * ID used for serialization
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Parameterized constructor for passing on the message to the Exception class
	 * @param message the message we want to pass on received from the second constructor
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * Parameterless constructor for passing the custom message "Schedule conflict" to the first
	 * constructor
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}

}
