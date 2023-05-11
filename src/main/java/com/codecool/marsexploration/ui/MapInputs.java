package com.codecool.marsexploration.ui;

import com.codecool.marsexploration.data.MapConfig;
import com.codecool.marsexploration.data.ResourceConfig;
import com.codecool.marsexploration.data.TerrainConfig;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class MapInputs {
    private final Scanner scanner = new Scanner(System.in);
    private String mapName;
    private List<Integer> mountainsSizes = new ArrayList<>();
    private List<Integer> pitsSizes = new ArrayList<>();
    private int mapWidth = 0, mineralsAmount = 0, waterAmount = 0;
    private final Random random = new Random(); //TODO: rethink random instances - refactoring

    public boolean askUserForData() {
        mapName = chooseMapName();
        int decision = 0;
        while (decision != 1 && decision != 2 ) {
            System.out.println("Choose either '1' to enter your own or '2' for random values.");
            decision = scanner.nextInt();
        }
        if (decision == 1) {
               return true;
        } else if (decision == 2) {
            return false;
        }
        return false;
    }

    public String chooseMapName() {
        System.out.print("Enter a name for a new map: ");
        while (true) {
            String newMapName = scanner.nextLine();
            if (newMapName.matches("[a-zA-Z ]+")) {
                return newMapName;
            } else {
                System.out.println("Invalid input. Please enter a valid name (letters are a must, numbers can be used.");
                System.out.print("Enter a name for a new map: ");
            }
        }
    }

    public int chooseMapWidth() {
        System.out.print("Enter map-width: ");
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                System.out.print("Enter map-width: ");
            }
        }
    }

    public List<Integer> chooseTerrainAmountAndSizes(List<Integer> terrainsSizes) {
        System.out.println("Enter one integer for one terrain-entity. (Type '0' to finish):");
        while (true) {
            int number = scanner.nextInt();
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

    public int chooseResourceAmount() {
        System.out.print("Enter amount of resource: ");
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                System.out.print("Enter amount of resource: ");
            }
        }
    }

    public int generateRandomInteger() {
        return random.nextInt(50) + 1;
    }

    public List<Integer> generateRandomList() {
        List<Integer> randomList = new ArrayList<>();
        int listSize = random.nextInt(5) + 1;

        for (int i = 0; i < listSize; i++) {
            randomList.add(random.nextInt(20) + 1);
        }

        return randomList;
    }

    public String getMapName() {
        return mapName;
    }
    public int getMapWidth() {
        return mapWidth;
    }
    public List<Integer> getMountainsSizes() {
        return mountainsSizes;
    }

    public List<Integer> getPitsSizes() {
        return pitsSizes;
    }

    public int getMineralsAmount() {
        return mineralsAmount;
    }

    public int getWaterAmount() {
        return waterAmount;
    }
}
