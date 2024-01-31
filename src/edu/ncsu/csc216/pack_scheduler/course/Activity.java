package edu.ncsu.csc216.pack_scheduler.course;

/**
 * This is the abstract class which is the super class for Course and Event. It
 * has the getter and setter methods and handles the common checks between Event
 * and Course for setMeetingDays. The isDuplicate method is overridden for Event
 * and Course which is why it is blank. The checkConflict method is also overridden 
 * to properly implement its functionality for event and course classes.
 * 
 * @author Arnav Ganguly
 * @author Diego Sarria
 */
public abstract class Activity implements Conflict {

	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	/** MAXIMUM HOURS */
	private int maxHours = 23;
	/** MAXIMUM MINUTES */
	private int maxMinutes = 59;

	/**
	 * provide a short version of the array to display in the GUI
	 * 
	 * @return array that needs to be displayed
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * provide a long version of the array to display in the GUI
	 * 
	 * @return array that needs to be displayed
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * This is the constructor for Activity objects that only sets the title and
	 * sets the meeting days and time.
	 * 
	 * @param title       of the activity
	 * @param meetingDays of the activity
	 * @param startTime   of the activity
	 * @param endTime     of the activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Returns the title of the course
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the course
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if title is null or empty
	 */
	public void setTitle(String title) {

		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid title.");
		} else {
			this.title = title;
		}

	}

	/**
	 * Returns the meeting days of the course
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the start time of the course
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the end time of the course
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Combined set meetingDays, startTime and endTime into one method to simplify
	 * code. Makes sure that the mentioned meetingDays, startTime and endTime for
	 * the course are valid and follow the requirements
	 * 
	 * @param meetingDays is the details for the meeting days
	 * @param startTime   is the time the class starts
	 * @param endTime     is the time the class ends
	 * @throws IllegalArgumentException if meetinddays is null/empty
	 * Or if the start and end times are invalid (start time after end time, or less than 0)
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {

		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		else if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		else {

			int startHours = startTime / 100;
			int startMinutes = startTime % 100;
			int endHours = endTime / 100;
			int endMinutes = endTime % 100;

			if (startHours < 0 || startHours > maxHours) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} else if (startMinutes < 0 || startMinutes > maxMinutes) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} else if (endHours < 0 || endHours > maxHours) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} else if (endMinutes < 0 || endMinutes > maxMinutes) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} else {
				this.meetingDays = meetingDays;
				this.startTime = startTime;
				this.endTime = endTime;
			}

		}

	}

	/**
	 * This is a getter method for the details of the Course classes in a readable
	 * format
	 * 
	 * @return a String with meetingDays, start and end time given.
	 */
	public String getMeetingString() {
		if ("A".equals(meetingDays)) {
			return "Arranged";
		} else {
			return meetingDays + " " + convertTime(startTime) + "-" + convertTime(endTime);
		}
	}

	/**
	 * This is a helper method for getMeetingString that converts military time to
	 * standard format
	 * 
	 * @param time is the time in military format
	 * @return finalString that has the time in standard format
	 */
	private String convertTime(int time) {

		int milHours = time / 100;
		int milMinutes = time % 100;

		String stdMinutes = "";
		String stdHours = "";

		boolean timePM = false;

		if (milMinutes < 10) {
			stdMinutes = "0" + milMinutes;
		} else {
			stdMinutes = "" + milMinutes;
		}

		if (milHours == 12 && milMinutes >= 0) {
			stdHours = "" + milHours;
			timePM = true;

		} else if (milHours > 12) {
			stdHours = "" + (milHours - 12);
			timePM = true;
		}

		else {
			stdHours = "" + milHours;
		}

		if (timePM) {
			String finalTime = stdHours + ":" + stdMinutes + "PM";
			return finalTime;
		} else {
			String finalTime = stdHours + ":" + stdMinutes + "AM";
			return finalTime;
		}

	}

	/**
	 * Converts the fields to hashCode
	 * 
	 * @return result which are the fields in hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + maxHours;
		result = prime * result + maxMinutes;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares two objects to check if they're equal
	 * 
	 * @return true if they are and false if they aren't
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (maxHours != other.maxHours)
			return false;
		if (maxMinutes != other.maxMinutes)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * This method is overridden for Event and Course which is why its blank.
	 * 
	 * @param activity is the activity object
	 * @return true or false based of what kind of object is entered as a parameter
	 *         and this method is written differently in Event and Course
	 */
	public abstract boolean isDuplicate(Activity activity);
	
	
	/**
	 * This is the method that checks conflict between two activities. The checks are the same for 
	 * events and courses so its implemented in activity
	 * @param possibleConflictingActivity is the activity we use to compare and check for a conflict
	 * @throws ConflictException if the activities are conflicting
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		
		// counting what days the activities are happening on
		String activityOneDays = this.getMeetingDays();
		
		int countM = 0;
		int countT = 0;
		int countW = 0;
		int countH = 0;
		int countF = 0;
		int countS = 0;
		int countU = 0;

		for (int i = 0; i < activityOneDays.length(); i++) {
			if (activityOneDays.charAt(i) == 'M') {
				countM++;
			} else if (activityOneDays.charAt(i) == 'T') {
				countT++;
			} else if (activityOneDays.charAt(i) == 'W') {
				countW++;
			} else if (activityOneDays.charAt(i) == 'H') {
				countH++;
			} else if (activityOneDays.charAt(i) == 'F') {
				countF++;
			} else if (activityOneDays.charAt(i) == 'S') {
				countS++;
			} else if (activityOneDays.charAt(i) == 'U') {
				countU++;
			}
		}
		
		//Counting what days the activities are happening on 
		String activityTwoDays = possibleConflictingActivity.getMeetingDays();
		
		int countM2 = 0;
		int countT2 = 0;
		int countW2 = 0;
		int countH2 = 0;
		int countF2 = 0;
		int countS2 = 0;
		int countU2 = 0;

		for (int i = 0; i < activityTwoDays.length(); i++) {
			if (activityTwoDays.charAt(i) == 'M') {
				countM2++;
			} else if (activityTwoDays.charAt(i) == 'T') {
				countT2++;
			} else if (activityTwoDays.charAt(i) == 'W') {
				countW2++;
			} else if (activityTwoDays.charAt(i) == 'H') {
				countH2++;
			} else if (activityTwoDays.charAt(i) == 'F') {
				countF2++;
			} else if (activityTwoDays.charAt(i) == 'S') {
				countS2++;
			} else if (activityTwoDays.charAt(i) == 'U') {
				countU2++;
			}
		}
		
		//checking if the events are happening on the same day
		if (countM == 1 && countM2 == 1 || countT == 1 && countT2 == 1 || countW == 1 && countW2 == 1 || countH == 1 && countH2 == 1 || countF == 1 && countF2 == 1 || countS == 1 && countS2 == 1 || countU == 1 && countU2 == 1) {
			
			// when the end time of one event is the same as start time of the other one
			if (this.getEndTime() == possibleConflictingActivity.getStartTime() || possibleConflictingActivity.getEndTime() == this.getStartTime()) {
				throw new ConflictException();
			}
			
			else if(this.getStartTime() == possibleConflictingActivity.getStartTime() || this.getEndTime() == possibleConflictingActivity.getEndTime()) {
				throw new ConflictException();
			}
			
			// check what event comes first
			else if(this.getEndTime() > possibleConflictingActivity.getStartTime() && this.getEndTime() < possibleConflictingActivity.getEndTime()) {
				throw new ConflictException();
			}
			
			else if(possibleConflictingActivity.getEndTime() > this.getStartTime() && possibleConflictingActivity.getEndTime() < this.getEndTime()) {
				throw new ConflictException();
			}
			
		}
		
	}
	
	

}