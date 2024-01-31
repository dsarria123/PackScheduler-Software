package edu.ncsu.csc216.pack_scheduler.user;

/**
 * Super class for users, inherited by Student and Registrar
 * Handles the users first name, last name, id, email and password
 * @author Peyton Herring
 * @author Dee Kandel
 * @author Diego Sarria
 */
public abstract class User {
	
	/** First name variable */
	private String firstName;
	/** Last name variable */
	private String lastName;
	/** ID variable */
	private String id;
	/** Email variable */
	private String email;
	/** HashPW variable */
	private String hashPW;
	
	/**
	 * Constructs the User 
	 * @param firstName first name of User
	 * @param lastName last name of User
	 * @param id student id of User
	 * @param email email of User
	 * @param hashPW of User
	 */
	public User(String firstName, String lastName, String id, String email, String hashPW) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(hashPW);
	}
	
	/**
	 * Gets users first name
	 * 
	 * @return first name of user
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets users last name
	 * 
	 * @return last name of user
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets User ID
	 * 
	 * @return User ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets user email
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of current user
	 * 
	 * @param email the email to set
	 * @throws IllegalArgumentException if email is null or length 0, or if @ and
	 *                                  characters are in improper spots/absent
	 */
	public void setEmail(String email) {

		if (email == null || email.length() == 0) {
			throw new IllegalArgumentException("Invalid email");
		}
		int atIndex = email.indexOf("@");
		int dotIndex = email.lastIndexOf('.');

		if (atIndex == -1 || dotIndex == -1) {
			throw new IllegalArgumentException("Invalid email");
		} else if (dotIndex <= atIndex) {
			throw new IllegalArgumentException("Invalid email");
		} else {
			this.email = email;
		}

	}

	/**
	 * Gets the hash PW
	 * 
	 * @return the hashPW
	 */
	public String getPassword() {
		return hashPW;
	}

	/**
	 * Sets the hashPW
	 * 
	 * @param hashPW the hashPW to set
	 * @throws IllegalArgumentException if password is null or length 0
	 */
	public void setPassword(String hashPW) {
		if (hashPW == null || hashPW.length() == 0) {
			throw new IllegalArgumentException("Invalid password");
		} else {
			this.hashPW = hashPW;
		}

	}
	
	/**
	 * Sets first name of current user
	 * 
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException if name is null/empty
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.length() == 0) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Sets last name of current user
	 * 
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException if name is null/empty
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.length() == 0) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Sets the ID of current student
	 * 
	 * @param id the id to set
	 * @throws IllegalArgumentException if ID is null/empty
	 */
	public void setId(String id) {
		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Generates hashcode of user
	 * @return the hashcode as int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((hashPW == null) ? 0 : hashPW.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	/**
	 * Tests if 2 users are equal
	 * @return true if they are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (hashPW == null) {
			if (other.hashPW != null)
				return false;
		} else if (!hashPW.equals(other.hashPW))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

}
