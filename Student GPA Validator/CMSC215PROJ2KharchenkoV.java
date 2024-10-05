/* 

Name: Kharchenko, Vadym

Project Name: Programming Project 2 - Student GPA Validator

Date: 06 July, 2024

Description: The goal of this program is to determine if 
a student is eligible for membership in an honor society.

There are two main classes:
Student class 
    contains name, credit hours, quality points
    gpa - returns grade point average.
    eligibleForHonorSociety - returns whether gpa exceeds the threshold
    toString - prints student name and grade point average
    setGpaThreshold - sets minimum gpa for honor society.

    Student class has two subclasses:
    Undergraduate
        (year) Specifies if a student is freshman, sophmore, junior, or sinior
        constractor that allows name, cred hour, qual points, and year to be initilized
        eligibleForHonorSociety - must be junior or sinior
        toString returns name, gpa, year

    Graduate
        Sets if the sudent is going after Master or Doctorate.
        Constractor for: name, cred hours, qual points, degree sought.
        eligibleForHonorSociety - overridden method - bypasses year requirment
        toString - overridden method - returns name, gpa, and degree sought

    DataControl class
        Reads students information from a text file named student.txt.
        After everything is read, it calculates GPA threshold based on the average gpa
        of all students.
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Student {
    protected String name;
    protected int creditHours;
    protected double qualityPoints;
    private static double gpaThreshold;

    public Student(String name, int creditHours, double qualityPoints) {
        this.name = name;
        this.creditHours = creditHours;
        this.qualityPoints = qualityPoints;
    }

    public double gpa() {
        return qualityPoints / creditHours;
    }

    public boolean eligibleForHonorSociety() {
        return gpa() >= gpaThreshold;
    }

    public static void setGpaThreshold(double threshold) {
        gpaThreshold = threshold;
    }

    @Override
    public String toString() {
        return name + " with GPA: " + String.format("%.2f", gpa());
    }
}

class Undergraduate extends Student {
    private String year;

    public Undergraduate(String name, int creditHours, double qualityPoints, String year) {
        super(name, creditHours, qualityPoints);
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    @Override
    public boolean eligibleForHonorSociety() {
        return (year.equalsIgnoreCase("junior") || year.equalsIgnoreCase("senior")) && super.eligibleForHonorSociety();
    }

    @Override
    public String toString() {
        return super.toString() + ", Year: " + year;
    }
}

class Graduate extends Student {
    private String degreeSought;

    public Graduate(String name, int creditHours, double qualityPoints, String degreeSought) {
        super(name, creditHours, qualityPoints);
        this.degreeSought = degreeSought;
    }

    public String getDegreeSought() {
        return degreeSought;
    }

    @Override
    public boolean eligibleForHonorSociety() {
        return super.eligibleForHonorSociety();
    }

    @Override
    public String toString() {
        return super.toString() + ", Degree Sought: " + degreeSought;
    }
}

class DataControl {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                String name = data[0] + " " + data[1];
                int creditHours = Integer.parseInt(data[2]);
                double qualityPoints = Double.parseDouble(data[3]);
                String lastField = data[4];

                if (lastField.equalsIgnoreCase("junior") || lastField.equalsIgnoreCase("senior") || 
                    lastField.equalsIgnoreCase("freshman") || lastField.equalsIgnoreCase("sophomore")) {
                    students.add(new Undergraduate(name, creditHours, qualityPoints, lastField));
                } else {
                    students.add(new Graduate(name, creditHours, qualityPoints, lastField));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calculate the average GPA
        double totalGpa = 0.0;
        for (Student student : students) {
            totalGpa += student.gpa();
        }
        double averageGpa = totalGpa / students.size();
        Student.setGpaThreshold(averageGpa);

        // Output the purpose of the program
        System.out.println("**********************");
        System.out.println();
        System.out.println("This program determines which students are eligible for membership in the Honor Society.");
        System.out.println();
        System.out.println("**********************");
        System.out.println();

        // Output the threshold GPA
        System.out.println("The threshold GPA for membership in the Honor Society is: " + String.format("%.2f", averageGpa));
        System.out.println();

        // Explanation of the following entries
        System.out.println("The following students meet the GPA threshold for Honor Society membership:");
        System.out.println();

        // Display only eligible students
        for (Student student : students) {
            if (student.eligibleForHonorSociety()) {
                System.out.println("    " + student);
            }
        }
    }
}
