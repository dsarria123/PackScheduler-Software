package edu.ncsu.csc216.pack_scheduler.course.validator;
/**
 * Validates a given course name, a course name must start with at least 1 letter and cannot have more than 4.
 * Courses must have 3 digits and may end after that or contain a single letter suffix, any other input is invalid.
 * This follows the given FSM flowchart and may throw exceptions when an invalid input is given.
 * @author Dee Kandel
 * @author Peyton Herring
 */
public class CourseNameValidator {
	
	/** Final instance of the LetterState class */
	private final State letterState = new LetterState();
	
	/** Final instance of the SuffixState class */
	private final State suffixState = new SuffixState();
	
	/** Final instance of the LetterState class */
	private final State initialState = new InitialState();
	
	/** Final instance of the LetterState class */
	private final State numberState = new NumberState();
	
	/** Current state of course name */
	private State currentState;
	
	/** Counts number of letters in course name */
	private int letterCount;
	
	/** Counts number of digits in course name */
	private int digitCount;
	
	/**
	 * Constructs CourseNameValidator
	 */
	public CourseNameValidator() {
		currentState = initialState;
	}
	
	/**
	 * Returns true if string is correct based on FSM 
	 * @param courseName name of course
	 * @return true if string is correct
	 * @throws InvalidTransitionException if the transition is not valid
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
		for (int i = 0; i < courseName.length(); i++) {
			char c = courseName.charAt(i);
			if (Character.isLetter(c)) {
				getCurrentState().onLetter();
			} else if (Character.isDigit(c)) {
				getCurrentState().onDigit();
			} else {
				reset();
				State.onOther();
			}
		}
		if (letterCount <= 4 && digitCount == 3) {
			reset();
			return true;
		} 
		reset();
		return false;
	}
	
	/**
	 * Resets all variables
	 */
	private void reset() {
		letterCount = 0;
		digitCount = 0;
		currentState = initialState;
	}
	
	/**
	 * Gets the current state
	 * @return the current state
	 */
	public State getCurrentState() {
		return currentState;
	}
	
	/**
	 * Class for the different states on a course
	 * Calls the methods depending on if a given char is a letter, digit, or other (throws exception)
	 */
	private abstract class State {
		
		/** Method to call when the given char is a letter */
		public abstract void onLetter() throws InvalidTransitionException;
		
		/** Method to call when the given char is a digit */
		public abstract void onDigit() throws InvalidTransitionException;
		
		/**
		 * Calls to throw an exception
		 * @throws InvalidTransitionException when the given char is not a digit nor a letter
		 */
		public static void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
		
	}
	
	/**
	 * Concrete class used to define state of Letter, LetterState
	 */
	private class LetterState extends State {
		/**
		 * Constructor for Letter State 
		 */
		private LetterState() {
			
		}
		
		/**
		 * When the state encounters letter
		 * @throws InvalidTransitionException if there is an invalid transition
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if(letterCount >= 4 ) {
				reset();
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
			else {
				letterCount++;
			}
			currentState = letterState;
		}

		/**
		 * When the state encounters digit
		 * @throws InvalidTransitionException if there is an invalid transition
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			digitCount++;
			currentState = numberState;
		}
		
	}
	
	/**
	 * Concrete class used to define state of Suffix, SuffixState
	 */
	private class SuffixState extends State {
		/**
		 * Constructor for Suffix State 
		 */
		private SuffixState() {
			
		}
		
		/**
		 * When the state encounters letter
		 * @throws InvalidTransitionException if there is an invalid transition
		 */
		@Override
		public void onLetter() throws InvalidTransitionException{
			reset();
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}

		/**
		 * When the state encounters digit
		 * @throws InvalidTransitionException if there is an invalid transition
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			reset();
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
		
	}
	
	/**
	 * Concrete class used to define Initial state, InitialState
	 */
	private class InitialState extends State {
		/**
		 * Constructor for Initial State 
		 */
		private InitialState() {
			
		}
		
		/**
		 * When the state encounters letter
		 * @throws InvalidTransitionException if there is an invalid transition
		 */
		@Override
		public void onLetter () throws InvalidTransitionException{
			letterCount++;
			currentState = letterState;
			
		}

		/**
		 * When the state encounters digit
		 * @throws InvalidTransitionException if there is an invalid transition
		 */
		@Override
		public void onDigit() throws InvalidTransitionException{
			//letter has to come first, digit cannot 
			reset();
			throw new InvalidTransitionException("Course name must start with a letter.");
			
		}
		
	}
	
	/**
	 * Concrete class used to define state of Number, NumberState
	 */
	private class NumberState extends State {
		/**
		 * Constructor for Number State 
		 */
		private NumberState() {
			
		}
		
		/**
		 * When the state encounters letter
		 * @throws InvalidTransitionException if there is an invalid transition
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if(digitCount < 3) {
				reset();
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
			currentState = suffixState;
		}
		
		/**
		 * When the state encounters digit
		 * @throws InvalidTransitionException if there is an invalid transition
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			if (digitCount >= 3) {
				reset();
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
			digitCount++;
		}
		
	}
	
	
}
