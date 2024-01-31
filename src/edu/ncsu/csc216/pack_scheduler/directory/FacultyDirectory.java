package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Maintains a directory of all faculty at the university.
 * All faculty have a unique id.
 * @author Diego Sarria
 */
public class FacultyDirectory {
    
    /** List of faculty in the directory */
    private LinkedList<Faculty> facultyDirectory;
    /** Hashing algorithm */
    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Creates an empty faculty directory.
     */
    public FacultyDirectory() {
        newFacultyDirectory();
    }

    /**
     * Creates an empty faculty directory. All faculty in the previous list are lost unless saved by the user.
     */
    public void newFacultyDirectory() {
        facultyDirectory = new LinkedList<>();
    }

    /**
     * Constructs the faculty directory by reading in faculty information from the given file.
     * Throws an IllegalArgumentException if the file cannot be found.
     * @param fileName file containing list of faculty
     * @throws IllegalArgumentException if file cannot be read properly
     */
    public void loadFacultyFromFile(String fileName) {
        try {
            facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to read file " + fileName);
        }
    }

    /**
     * Adds a Faculty to the directory. Returns true if the faculty is added and false if
     * the faculty is unable to be added because their id matches another faculty's id.
     * This method also hashes the faculty's password for internal storage.
     * @param firstName faculty's first name
     * @param lastName faculty's last name
     * @param id faculty's id
     * @param email faculty's email
     * @param password faculty's password
     * @param repeatPassword faculty's repeated password
     * @param maxCourses faculty's max courses.
     * @return true if added
     * @throws IllegalArgumentException if password is null, empty, or does not match the repeat
     */
    public boolean addFaculty(String firstName, String lastName, String id, String email, String password, String repeatPassword, int maxCourses) {
        String hashPW = "";
        String repeatHashPW = "";
        if (password == null || repeatPassword == null || "".equals(password) || "".equals(repeatPassword)) {
            throw new IllegalArgumentException("Invalid password");
        }
        
        hashPW = hashString(password);
        repeatHashPW = hashString(repeatPassword);
        
        if (!hashPW.equals(repeatHashPW)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
        
        for (int i = 0; i  < facultyDirectory.size(); i++) {
            if (facultyDirectory.get(i).getId().equals(id)) {
            	return false;
            }
        }
        return facultyDirectory.add(faculty);
    }

    /**
     * Removes the faculty with the given id from the list of faculty.
     * Returns true if the faculty is removed and false if the faculty is not in the list.
     * @param facultyId faculty's id
     * @return true if removed
     */
    public boolean removeFaculty(String facultyId) {
        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty f = facultyDirectory.get(i);
            if (f.getId().equals(facultyId)) {
                facultyDirectory.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all faculty in the directory with a column for first name, last name, and id.
     * @return String array containing faculty first name, last name, and id.
     */
    public String[][] getFacultyDirectory() {
        String[][] directory = new String[facultyDirectory.size()][3];
        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty f = facultyDirectory.get(i);
            directory[i][0] = f.getFirstName();
            directory[i][1] = f.getLastName();
            directory[i][2] = f.getId();
        }
        return directory;
    }

    /**
     * Saves all faculty in the directory to a file.
     * @param fileName name of file to save faculty to.
     * @throws IllegalArgumentException if file cannot be written
     */
    public void saveFacultyDirectory(String fileName) {
        try {
            FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
       
          } catch (IOException e) {
            throw new IllegalArgumentException("Unable to write to file " + fileName);
        }
    }

    /**
     * Hashes a String according to the SHA-256 algorithm, and outputs the digest in base64 encoding.
     * This allows the encoded digest to be safely copied, as it only uses [a-zA-Z0-9+/=].
     * 
     * @param toHash the String to hash 
     * @return the encoded digest of the hash algorithm in base64
     * @throws IllegalArgumentException if password cannot be hashed
     */
    private static String hashString(String toHash) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(toHash.getBytes());
            return Base64.getEncoder().encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Cannot hash password");
        }
    }

    /**
     * Returns a Faculty object from the faculty directory if the id matches.
     * @param id the id of the faculty
     * @return the Faculty object with the matching id, or null if no match is found
     */
    public Faculty getFacultyById(String id) {
    	for (int i = 0; i  < facultyDirectory.size(); i++) {
            if (facultyDirectory.get(i).getId().equals(id)) {
            	facultyDirectory.get(i);
            }
        }
        return null;
    }

}

