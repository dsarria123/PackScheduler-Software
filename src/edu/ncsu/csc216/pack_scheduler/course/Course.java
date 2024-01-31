package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * This is a class that handles the course objects. It contains the setters,
 * getters, equals and hash code methods for the Course objects. The equals and
 * hashCode methods have been overridden to not compare start and end time of
 * the courses. The constructors have been updated to reduce redundant code .
 * This class extends to its super class that is Activity
 * 
 * @author Arnav Ganguly
 * @author Diego SArria
 */
public class Course extends Activity implements Comparable<Course> {

	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** EnrollmentCap for variable for course roll */
	private int enrollmentCap;
	/** Variable for the course roll */
	private CourseRoll roll;
	/** Minimum length for the course name */
	private int nameMin = 5;

	/** Maximum length for the course name */
	private int nameMax = 8;

	/** Minimum number of characters required in the name */
	private int nameMinLetters = 1;

	/** Maximum number of characters required in the name */
	private int nameMaxLetters = 4;

	/** Number of digits in the course name */
	private int nameDigits = 3;

	/** Length of the section */
	private int secLength = 3;

	/** Minimum credits */
	private int creditMin = 1;

	/** MAXIMUM CREDITS */
	private int creditMax = 5;

	/**
	 * Returns the name of the course
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the course
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if the parameter entered is invalid or doesn't meet requirements
	 */
	private void setName(String name) {

		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("Invalid course name.");

		} else
			try {
				CourseNameValidator c = new CourseNameValidator();
				if (!c.isValid(name)) {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} catch (InvalidTransitionException e) {
				throw new IllegalArgumentException("Invalid course name.");
			}

		this.name = name;

	}

	/**
	 * Returns the section of the course
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the section of the course
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if the parameter entered is invalid or doesn't meet requirements
	 */
	public void setSection(String section) {

		if (section == null || section.length() != secLength) {
			throw new IllegalArgumentException("Invalid section.");
		} else {

			for (int i = 0; i < section.length(); i++) {
				if (!Character.isDigit(section.charAt(i))) {
					throw new IllegalArgumentException("Invalid section.");
				}

			}
			this.section = section;
		}

	}

	/**
	 * Returns the credits of the course
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the credits of the course
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if the parameter entered is invalid or doesn't meet requirements
	 */
	public void setCredits(int credits) {
		if (credits < creditMin || credits > creditMax) {
			throw new IllegalArgumentException("Invalid credits.");
		} else {
			this.credits = credits;
		}

	}

	/**
	 * Returns the instructorID of the course
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the instructor id of the course
	 * 
	 * @param instructorId the instructorId to set
	 */
	public void setInstructorId(String instructorId) {
			this.instructorId = instructorId;
	}

	/**
	 * Constructs a Course object with values for all fields.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap the cap for enrollment
	 * @param meetingDays  meeting days for Course as series of chars
	 * @param startTime    start time for Course
	 * @param endTime      end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setTitle(title);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		setCourseRoll(enrollmentCap);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap the cap for enrollment
	 * @param meetingDays  meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}
	
	/**
	 * Gets the course roll of current course
	 * @return the course roll
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}
	
	/**
	 * Sets the course roll variable
	 * @param enrollmentCap the cap to set for the course roll
	 * @throws IllegalArgumentException if course roll can not be made due to out of bounds
	 */
	private void setCourseRoll(int enrollmentCap) {
		try {
			roll = new CourseRoll(this, enrollmentCap);
			this.enrollmentCap = enrollmentCap;
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid course roll.");
		}
	}
	
	/**
	 * Gets the enrollment cap of a course
	 * @return the enrollment capacity of a course
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
					+ roll.getEnrollmentCap() + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays()
				+ "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * Converts the fields to hashCode
	 * 
	 * @return result which are the fields in hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + creditMax;
		result = prime * result + creditMin;
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nameDigits;
		result = prime * result + nameMax;
		result = prime * result + nameMaxLetters;
		result = prime * result + nameMin;
		result = prime * result + nameMinLetters;
		result = prime * result + secLength;
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares two objects to check if theyre equal
	 * 
	 * @return true if they are and false if they arent
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (creditMax != other.creditMax)
			return false;
		if (creditMin != other.creditMin)
			return false;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nameDigits != other.nameDigits)
			return false;
		if (nameMax != other.nameMax)
			return false;
		if (nameMaxLetters != other.nameMaxLetters)
			return false;
		if (nameMin != other.nameMin)
			return false;
		if (nameMinLetters != other.nameMinLetters)
			return false;
		if (secLength != other.secLength)
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Generates the short version of the array to display in the GUI
	 * 
	 * @return shortArray which consists of the name, section, title, meeting
	 *         details, and open seats
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortArray = new String[5];
		shortArray[0] = getName();
		shortArray[1] = getSection();
		shortArray[2] = getTitle();
		shortArray[3] = getMeetingString();
		shortArray[4] = "" + roll.getOpenSeats();

		return shortArray;
	}

	/**
	 * Generates the long version of the array to display in the GUI
	 * 
	 * @return longArray which consists of the name, section, title, credits,
	 *         instructorID and the meeting details It also contains an empty field
	 *         for a field that will be filled by Event
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longArray = new String[7];
		longArray[0] = getName();
		longArray[1] = getSection();
		longArray[2] = getTitle();
		longArray[3] = "" + getCredits();
		longArray[4] = getInstructorId();
		longArray[5] = getMeetingString();
		longArray[6] = "";

		return longArray;
	}

	/**
	 * This is the method that overrides the setMeetingDaysAndTime in Activity to
	 * suit the requirements for the Course object, as separate checks are required
	 * by Event and Course objects.
	 * 
	 * @param meetingDays is the details for the meeting days
	 * @param startTime   is the time the class starts
	 * @param endTime     is the time the class ends
	 * @throws IllegalArgumentException if the parameter entered is invalid or doesn't meet requirements
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {

		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		else if ("A".equals(meetingDays)) {
			if (startTime != 0 || endTime != 0 || meetingDays.length() != 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} else {

				super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
			}
		}

		else {
			int countM = 0;
			int countT = 0;
			int countW = 0;
			int countH = 0;
			int countF = 0;

			for (int i = 0; i < meetingDays.length(); i++) {
				if (meetingDays.charAt(i) == 'M') {
					countM++;
				} else if (meetingDays.charAt(i) == 'T') {
					countT++;
				} else if (meetingDays.charAt(i) == 'W') {
					countW++;
				} else if (meetingDays.charAt(i) == 'H') {
					countH++;
				} else if (meetingDays.charAt(i) == 'F') {
					countF++;
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}

			if (countM > 1 || countT > 1 || countW > 1 || countH > 1 || countF > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} else {
				super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
			}

		}

	}

	/**
	 * Checks if an Activity is a duplicate of this Course, determined by if the two names are equals
	 * @return true if the given activity is a duplicate of the Course,
	 *         or false if not
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course) {
			Course course = (Course) activity;
			return this.getName().equals(course.getName());
		} else {
			return false;
		}
	}

	/**
	 * Compares an Object to this Course to check if equal, greater than or less than
	 * @return 0 if the two objects are equal, -1 if the given object is less than this Course,
	 *         or 1 if the given object is greater than this Course 
	 * @param o the object to compare 'this' to
	 * @throws NullPointerException if the object o is null
	 * @throws ClassCastException if the given object is not a course
	 */
	@Override
	public int compareTo(Course o) {
		if (o == null) {
			throw new NullPointerException("The object entered is null");
		}
		
		else if (!(o instanceof Course)) {
			throw new ClassCastException("The object entered is not a Course");
		}
		else {
			
			if (!this.getName().equals(o.getName())) {
				SortedList<String> nameList = new SortedList<String>();
				nameList.add(this.getName());
				nameList.add(o.getName());
				if (this.getName().equals(nameList.get(0))) {
					nameList.clear();
					return -1; //this course is before Course s 
				}
				else {
					nameList.clear();
					return 1; //this course is after Course 
				}
			}
			else if (!this.getSection().equals(o.getSection())){
				SortedList<String> sectionList = new SortedList<String>();
				sectionList.add(this.getSection());
				sectionList.add(o.getSection());
				if (this.getSection().equals(sectionList.get(0))) {
					sectionList.clear();
					return -1; //this course is before Course  
				}
				else {
					sectionList.clear();
					return 1; //this course is after Course 
				}
			}
			else {
				return 0; //Courses are equal
			}
		
		}
	}

}
