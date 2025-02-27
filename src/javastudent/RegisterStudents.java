/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javastudent;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author henar
 */
public class RegisterStudents {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here 
        System.out.println("-------------------------");
        System.out.println("Registry Students program");
        System.out.println("-------------------------");
        
        BufferedReader brConsole = StudentRegistry.getBufferedReaderConsole();
        int option;
        while (true) {
            System.out.println("\n----------------------");
            System.out.println("Registry Students menu");
            System.out.println("----------------------");
            System.out.println("1) Add a new student to the registry");
            System.out.println("2) Show the list of students ");
            System.out.println("3) Remove a student from the registry");
            System.out.println("4) Search for a student by their NID");
            System.out.println("5) Exit program");
            System.out.print("Choose an option: ");
            try {
                option = Integer.parseInt(brConsole.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Option must be a integer");
                continue;
            } catch (IOException e) {
                System.out.println("Error reading input.");
                continue;
            }
            
            if (option < 1 || option > 5) {
                System.out.println("Option is not valid");
                continue;
            }
            
            if (option == 5) {
                break;
            }
            
            switch (option) {
                case 1 -> StudentRegistry.addStudent();
                case 2 -> StudentRegistry.showListStudents();
                case 3 -> StudentRegistry.removeStudent();
                case 4 -> StudentRegistry.showStudentByNid();
            }
        }
        try {
            brConsole.close();
        } catch (IOException e) {
            System.out.println("Error closing console buffered reader.");
        }
    }
}
