package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * This class handles reading and writing faculty records to and from files.
 * @author Diego Sarria
 */
public class FacultyRecordIO {

    /**
     * Reads faculty records from a file and returns them as a LinkedList.
     * 
     * @param fileName the file to read faculty records from
     * @return a LinkedList of faculty
     * @throws FileNotFoundException if the file cannot be found
     */
    public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new FileInputStream(fileName));
        LinkedList<Faculty> faculty = new LinkedList<>();
        
        while (fileReader.hasNextLine()) {
            try {
                Faculty facultyMember = readFaculty(fileReader.nextLine());
                faculty.add(facultyMember);
            } catch (IllegalArgumentException e) {
                // Skip the line if it's improperly formatted.
            }
        }
        fileReader.close();
        return faculty;
    }

    /**
     * Helper method to parse a line into a Faculty object.
     * 
     * @param line the line to parse
     * @return the Faculty object
     * @throws IllegalArgumentException if the line cannot be parsed into a Faculty object
     */
    private static Faculty readFaculty(String line) {
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(",");
        try {
            String firstName = scanner.next();
            String lastName = scanner.next();
            String id = scanner.next();
            String email = scanner.next();
            String hashPW = scanner.next();
            int maxCourses = scanner.nextInt();
            
            Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
            scanner.close();
            return faculty;
        } catch (Exception e) {
            scanner.close();
            throw new IllegalArgumentException("Invalid line");
        }
    }

    /**
     * Writes the LinkedList of Faculty to the specified file.
     * 
     * @param fileName the file to write the faculty records to
     * @param facultyDirectory the LinkedList of Faculty
     * @throws IOException if cannot write to the file
     */
    public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
        PrintStream fileWriter = new PrintStream(new File(fileName));
        
        for (int i = 0; i < facultyDirectory.size(); i++) {
            fileWriter.println(facultyDirectory.get(i).toString());
        }

        fileWriter.close();
    }
}
