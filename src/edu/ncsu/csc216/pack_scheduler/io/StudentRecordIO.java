package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * This is StudentRecordIO class that helps read the input file, convert it into an sorted list for us 
 * to use in our code and the GUI as well as handles writing the output file
 * @author Diego SArria
 */
public class StudentRecordIO {
	
	/**
	 * THis is the method that reads the data from the file and converts it to an sorted list
	 * @param fileName is the name of the input file
	 * @return students which is an sorted list of all the data in the input file
	 * @throws FileNotFoundException if there is a problem reading the file
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		try { 
			Scanner fileReader = new Scanner(new FileInputStream(fileName));
			
	        SortedList<Student> students = new SortedList<Student>(); 
	        while (fileReader.hasNextLine()) { 
	        	try {
	            		Student student = readStudent(fileReader.nextLine()); 
	            		
		                boolean duplicate = false;
		                
		                for (int i = 0; i < students.size(); i++) {
		                    
		                    Student current = students.get(i);
		                    
		                    if (student.getId().equals(current.getId())) {
		                        
		                        duplicate = true;
		                        break;
		                    } 
		                }
		                
		                if (!duplicate) {
		                    students.add(student);
		                }
	            	}
	        	catch (IllegalArgumentException e) {
	        		//skipping the line that throws an IllegalArgumentException
	        	}
	        }
	        fileReader.close();
	        return students;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File does not exist");
	    
	    }
	}
	
	/**
	 * This is a helper method for readStudentRecords that converts the file line into a student object
	 * @param nextLine is the next line from the file
	 * @return student which is a Student object with the data from the file
	 * @throws IllegalArgumentException if line has too many inputs or input is invalid
	 */
	private static Student readStudent(String nextLine) {
		Scanner scanner = new Scanner(nextLine);
		scanner.useDelimiter(",");
		
		try {
			
			String firstName = scanner.next();
			String lastName = scanner.next();
			String id = scanner.next();
			String email = scanner.next();
			String hashPW = scanner.next();
			int maxCredits = scanner.nextInt();
			if (!scanner.hasNext()) {
				Student student = new Student(firstName, lastName, id, email, hashPW, maxCredits);
				scanner.close();
				return student;
			}
			else {
				scanner.close();
				throw new IllegalArgumentException();
			}
			
			
			
		}
		catch (Exception e) {
			scanner.close();
			throw new IllegalArgumentException("Invalid line");
		}
	}
	
	/**
	 * This method writes the output file filled with student records
	 * @param fileName is the name of the output file
	 * @param studentDirectory is the sorted list consisting of all the student records
	 * @throws IOException if the method is unable to write the file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		try {
			
			PrintStream fileWriter = new PrintStream(new File(fileName));
	
	    	for (int i = 0; i < studentDirectory.size(); i++) {
	    	    fileWriter.println(studentDirectory.get(i).toString());
	    	}
	
	    	fileWriter.close();
		}
		catch (Exception e) {
			throw new IOException(fileName + " (No such file or directory)");
		}
		
	}

}
