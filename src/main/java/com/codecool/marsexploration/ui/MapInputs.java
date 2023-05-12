package com.codecool.marsexploration.ui;

import com.codecool.marsexploration.helper.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapInputs {
    private final Random random = new Random(); //TODO: rethink random instances - refactoring

    Input input;
    public MapInputs(Input input) {
        this.input = input;
    }

    public boolean askUserForData() {
        int decision = 0;
        while (decision != 1 && decision != 2 ) {
            System.out.println("Choose either '1' to enter your own or '2' for random values.");
            try {
                //decision = new Scanner(System.in).nextInt();
                decision = input.getUserInputInt();
            } catch(NumberFormatException e) {

            }

        }
        return decision == 1;
    }

    public String chooseMapName() {
        String question = "Enter a name for a new map: ";
        System.out.print(question);
        while (true) {
            String newMapName = input.getUserInput();
            if (newMapName.matches("[a-zA-Z ]+")) {
                return newMapName;
            } else {
                System.out.println("Invalid input. Please enter a valid name (letters are a must, numbers can be used.");
                System.out.print(question);
            }
        }
    }

    public List<Integer> chooseTerrainAmountAndSizes() {
        List<Integer> terrainsSizes = new ArrayList<>();
        System.out.println("Enter one integer for one terrain-entity. (Type '0' to finish):");
        while (true) {
            int number = input.getUserInputInt();
            if (number == 0) {
                break;
            }
            try {
                terrainsSizes.add(number);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter an integer or 'q' to quit.");
            }
        }
        System.out.println("ArrayList with amount & sizes: " + terrainsSizes);
        return terrainsSizes;
    }
    public int getUserInputNumber(String question) {
        System.out.print(question);
        while (true) {
            try {
                return input.getUserInputInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print(question);
            }
        }
    }

    //TODO: put this function into the Randomizer class
    public int generateRandomInteger() {
        return random.nextInt(50) + 1;
    }

    //TODO: put this function into the Randomizer class
    public List<Integer> generateRandomList() {
        List<Integer> randomList = new ArrayList<>();
        int listSize = random.nextInt(5) + 1;

        for (int i = 0; i < listSize; i++) {
            randomList.add(random.nextInt(20) + 1);
        }

        return randomList;
    }
}
