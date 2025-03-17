/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javastudent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author henar
 */
public class StudentRegistry {
    // Get the file of the students data
    private static File getFile() {
        String fileRoute = System.getProperty("user.dir");
        String separator = File.separator;
        
        // Create directory for files in project route if not existed
        File directory = new File(fileRoute + separator + "files");
        if (!directory.exists()) {
            directory.mkdir();
        }
        
        // Create file to collect students in files directory if not existed
        File record = new File(directory + separator + "record.txt");
        if (!record.exists()) {
            try {
                record.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file");
            }
        }
        return record;
    }
    
    // Create a bufferedReader to read the console inputs from the user
    public static BufferedReader getBufferedReaderConsole() {
        InputStream in = System.in;
        InputStreamReader is = new InputStreamReader(in);
        BufferedReader brConsole = new BufferedReader(is);
        return brConsole;
    }
    
    // Create a bufferedReader to read the file that contains students info 
    private static BufferedReader getBufferedReaderFile() {
        File record = getFile();
        BufferedReader brFile = null;
        try {
            FileReader fr = new FileReader(record);
            brFile = new BufferedReader(fr); 
        } catch (IOException e) {
            System.out.println("Error reading to file " + record.getName());
        }
        return brFile;
    }
    
    // Create a bufferedWriter to write the file that contains students info 
    private static BufferedWriter getBufferedWriter(Boolean overwrite) {
        File record = getFile();
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(record, overwrite);
            bw = new BufferedWriter(fw);
            return bw;
        } catch (IOException e) {
            System.out.println("Error writing to file " + record.getName());
        }
        return bw;
    }
       
    // Get an ArrayList of students to edit the file
    public static ArrayList<Student> getStudentsFromFile() {
        ArrayList<Student> students = new ArrayList<>();
        BufferedReader brFile = getBufferedReaderFile();
        try {
            String line;
            String [] lineValues;
            // For each line of the file if there's info collect it to the ArrayList.
            while (true) {
                line = brFile.readLine();
                
                if (line == null) {
                    break;
                }
                
                if (line.isEmpty()) {
                    continue;
                }
                
                lineValues = line.split(";");
                students.add(new Student(lineValues[0], lineValues[1], Integer.valueOf(lineValues[2]), lineValues[3], lineValues[4]));
            }
            brFile.close(); 
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        return students;
    }
    
    // Add the student info to the end of the file
    public static void addStudent() {
        Student student = createStudent();
        if (student == null) {
            return;
        }
        
        BufferedWriter bw = getBufferedWriter(true);
        try {
            // Add to the last line the info of a student in a formmated way
            bw.write(student.getFirstName() + ";" + student.getLastName() + ";" + student.getAge() + ";" + student.getCourse() + ";" + student.getNid());
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
    
    // User interface to collect and validate the info of a student that will be added to the file
    public static Student createStudent() {
        BufferedReader brConsole = getBufferedReaderConsole();
        System.out.println("------------------------------");
        System.out.println("Creating a new student profile");
        System.out.println("------------------------------");
        while (true) {
            try {
                System.out.print("Enter first name: ");
                String firstName = brConsole.readLine();
                if (firstName.isEmpty()) {
                    System.out.println("First Name is empty");
                    continue;
                }
                
                System.out.print("Enter last name: ");
                String lastName = brConsole.readLine();
                if (lastName.isEmpty()) {
                    System.out.println("Last Name is empty");
                    continue;
                }
                
                int age;
                try {
                    System.out.print("Enter age: ");
                    age = Integer.valueOf(brConsole.readLine());
                    if (age < 0) {
                        System.out.println("Age must be a positive integer");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Age must be an integer");
                    continue;
                }
                
                System.out.print("Enter course: ");
                String course = brConsole.readLine();
                if (course.isEmpty()) {
                    System.out.println("Course is empty");
                    continue;
                }
                
                System.out.print("Enter NID: ");
                String nid = brConsole.readLine();
                if (nid.isEmpty()) {
                    System.out.println("NID is empty");
                    continue;
                }
                // NID cannot be repeated
                if (searchStudentByNid(nid) != null) {
                    System.out.println("A student with this NID already exists");
                    return null;
                }
                
                return new Student(firstName, lastName, age, course, nid);
            } catch (IOException e) {
                System.out.println("Error reading console");
            }
        }
    }
    
    public static void showListStudents() {
        ArrayList<Student> students = getStudentsFromFile();
        
        System.out.println("");
        for (Student student : students) {
            System.out.println(student + "\n");
        }
    }
    
    public static void removeStudent() {
        // Collect all the students from the file to an ArrayList
        ArrayList<Student> students = getStudentsFromFile();
        
        System.out.println("--------------------------");
        System.out.println("Removing a student profile");
        System.out.println("--------------------------");
        
        // Remove the student if found from the ArrayList
        Student studentToRemove = searchStudentByNid();
        if (studentToRemove == null) {
            System.out.println("Error removing a student.");
            return;
        }
        students.remove(studentToRemove);
        
        // Write again all the data from the ArrayList to the file but without the student that was removed 
        String content = "";
        for (Student student : students) {
            content += student.getFirstName() + ";" + student.getLastName() + ";" + student.getAge() + ";" + student.getCourse() + ";" + student.getNid() + "\n";
        }
        
        BufferedWriter bwFile = getBufferedWriter(false);
        try {
            bwFile.write(content);
            bwFile.flush();
            bwFile.close(); 
        } catch (IOException e) {
            System.out.println("Error writing file.");
        }   
    }
    
    // User interface to enter an NID and return a student if found 
    public static Student searchStudentByNid() {
        // Show to user all students of the file
        ArrayList<Student> students = getStudentsFromFile();
        showListStudents();
        BufferedReader brConsole = getBufferedReaderConsole();
        String nid = "";
        try {
            System.out.print("Enter NID: ");
            nid = brConsole.readLine().toUpperCase();
        } catch (IOException e) {
            System.out.println("Error reading console");
        }
        
        // Return a student if there's a student with the NID introduced
        for (Student student : students) {
            if (student.getNid().equals(nid)) {    
                return student;
            }
        }
        
        System.out.println("Student not found with an NID " + nid);
        return null;
    }
    
    public static Student searchStudentByNid(String nid) {
        ArrayList<Student> students = getStudentsFromFile();
        
        for (Student student : students) {
            if (student.getNid().equals(nid)) {    
                return student;
            }
        }
        
        return null;
    }
    
    public static void showStudentByNid() {
        System.out.println("-------------------------------");
        System.out.println("Search a student profile by NID");
        System.out.println("-------------------------------");
        
        Student student = searchStudentByNid();
        if (student != null) {
            System.out.println(student);
        }
    }
}