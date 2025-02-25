/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javastudent;

/**
 *
 * @author henar
 */
public class Student {
    private String firstName;
    private String lastName;
    private int age;
    private String course;
    private String nid;

    public Student(String firstName, String lastName, int age, String course, String nid) {
        this.firstName = capitalizeString(firstName).trim();
        this.lastName = capitalizeString(lastName).trim();
        this.age = age;
        this.course = capitalizeString(course).trim();
        this.nid = nid.toUpperCase().trim();
    }
    
    private String capitalizeString(String input) {
        String [] words =  input.split(" ");
        String output = "";
        for (String word : words) {
            output += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ";
        }
        
        return output;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getCourse() {
        return course;
    }

    public String getNid() {
        return nid;
    }  
    
    @Override
    public String toString() {
        return "Name: " + this.firstName + "\nSurname: " + this.lastName + "\nAge: " + this.age + "\nCourse: " + this.course + "\nNID: " + this.nid;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        
        Student student = (Student) obj;
        
        return student.getNid().equals(this.getNid());
    }
}
