/* 

Name: Kharchenko, Vadym

Project Name: Programming Project 2 - Student GPA Validator

Date: 05 July, 2024

Description: The goal of this progrqm is to determine if 
a student is eligible for membership in an honor society.

There are four classes:
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
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import hava.util.Scanner;

public class CMSC215PROJ2KharchenkoV {
    public static void main(String[] args){
        ArrayList<Student>
    }
}