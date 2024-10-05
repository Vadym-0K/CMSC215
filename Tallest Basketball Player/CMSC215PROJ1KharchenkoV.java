/* 

Name: Kharchenko, Vadym

Project Name: Programming Project 1 - Tallest Basketball Player

Date: 24 June, 2024

Description: The goal of this program is to find the tallest player with
an accaptable age.

There are three classes. 

    Height class contains two integer instance variables for 
    the feet and inches.

    Player class contains three instance variables that include the 
    player’s name,the player’s height, which is stored as the type 
    of Height and the player’s age.

    InputData class prompts user for the input, creates a player obj, 
    calculates the avarage age, and finds the tallest player.

*/

import java.util.ArrayList;
import java.util.Scanner;

public class CMSC215PROJ1KharchenkoV {
    public static void main(String[] args) {
        InputData inputData = new InputData();
        inputData.inputPlayerData();
        inputData.findTallestPlayer();
    }
}

class Height {
    int feet;
    int inches;

    public Height(int feet, int inches) {
        if (inches >= 12) {
            feet += inches / 12;
            inches %= 12;
        }
        this.feet = feet;
        this.inches = inches;
    }

    public int toInches() {
        return feet * 12 + inches;
    }

    @Override
    public String toString() {
        return feet + "'" + inches + "\"";
    }
}

class Player {
    String name;
    Height height;
    int age;

    public Player(String name, Height height, int age) {
        this.name = name;
        this.height = height;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Height getHeight() {
        return height;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Height: " + height.toString() + ", Age: " + age;
    }
}

class InputData {
    ArrayList<Player> players = new ArrayList<>();
    int totalAge = 0;

    public InputData() {
        players = new ArrayList<>();
        totalAge = 0;

    }

    public void inputPlayerData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter player data or type 'stop' to end:");
        while (true) {
            System.out.print("Enter player's name: ");

            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("stop"))
                break;

            int feet, inches, age;
            try {
                System.out.print("Enter player's height [format: feet inches]: ");
                feet = scanner.nextInt();
                inches = scanner.nextInt();
                System.out.print("Enter player's age: ");
                age = scanner.nextInt();
                scanner.nextLine();

                Height height = new Height(feet, inches);
                Player player = new Player(name, height, age);
                players.add(player);
                totalAge += age;
            } catch (Exception e) {
                System.out.println("Enter the correct data! [invalid input]");
                scanner.nextLine();
            }

        }
        scanner.close();
    }

    public void findTallestPlayer() {
        double averageAge = totalAge / (double) players.size();
        System.out.println("Average age of all players: " + averageAge);
        Player tallestPlayer = null;
        for (Player player : players) {
            if (player.getAge() <= averageAge) {
                if (tallestPlayer == null || player.getHeight().toInches() > tallestPlayer.getHeight().toInches()) {
                    tallestPlayer = player;
                }
            }
        }

        if (tallestPlayer != null) {
            System.out.println("The tallest player with less or equal average: ");
            System.out.println(tallestPlayer);
        } else {
            System.out.println("There is no player that has an age less than or equal to the average age.");
        }
    }
}