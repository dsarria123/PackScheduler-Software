package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Exception class for invalid transitions
 * Throws this exception when a course is created with an invalid name.
 * A course is valid if it begins with 1-4 letters and followed by 3 digits, with an optional 1 letter suffix to conclude it.
 * All other inputs that do not meet that criteria are invalid and this exception is thrown.
 * @author Peyton Herring
 * @author Dee Kandel
 */
public class InvalidTransitionException extends Exception {

	/**
	 * Default serial for exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Exception constructor with a specific message
	 * @param message the message to throw with the exception
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	
	/**
	 * Constructs the exception with a default message
	 * 'Invalid FSM Transition.'
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}
}
