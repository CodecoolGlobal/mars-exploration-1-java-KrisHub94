package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ShapeGenerator {
    public HashMap<Coordinate, String> createShape(int area, String symbol) {
        Random random = new Random();
        int numberOfRows = random.nextInt(area) + 1;
        int leastNumberOfColumns = area - (numberOfRows - 1);
        int numberOfColumns = random.nextInt(area/2) + leastNumberOfColumns;
        HashMap<Coordinate, String> shape = createEmptyShape(numberOfColumns, numberOfRows);
        /*int counter = 0;
        while (counter < area) {
            int randomRow = random.nextInt(numberOfRows);
            int randomColumn = random.nextInt(numberOfColumns);
            Coordinate nextCoords = new Coordinate(randomColumn, randomRow);
            if(shape.get(nextCoords).equals(" ")) {
                shape.put(nextCoords, symbol);
                counter++;
            }
        }*/
        Set<Coordinate> possibleCoord = new HashSet<>();
        Set<Coordinate> takenCoord = new HashSet<>();

        int firstCoordX = random.nextInt(numberOfColumns);
        int firstCoordY = random.nextInt(numberOfRows);
        Coordinate firstCoords = new Coordinate(firstCoordX, firstCoordY);
        shape.put(firstCoords, symbol);
        addPossibleCoordinatesToList(numberOfColumns, numberOfRows, firstCoords, possibleCoord, takenCoord);
        int counter = 1;
        while (counter < area) {
            int randomRow = random.nextInt(numberOfRows);
            int randomColumn = random.nextInt(numberOfColumns);
            Coordinate nextCoords = new Coordinate(randomColumn, randomRow);
            if (possibleCoord.contains(nextCoords)) {
                shape.put(nextCoords, symbol);
                possibleCoord.remove(nextCoords);
                takenCoord.add(nextCoords);
                addPossibleCoordinatesToList(numberOfColumns, numberOfRows, nextCoords, possibleCoord, takenCoord);
                counter++;
            }
        }
        return shape;
    }

    private void addPossibleCoordinatesToList(int numberOfColumns, int numberOfRows, Coordinate coordToCheck, Set<Coordinate> possibleCoord, Set<Coordinate> takenCoord) {
        for (int x = coordToCheck.x() - 1; x <= coordToCheck.x() + 1; x++) {
            if (x >= 0 && x <= numberOfColumns) {
                for (int y = coordToCheck.y() - 1; y <= coordToCheck.y() + 1; y++) {
                    if (y >= 0 && y <= numberOfRows) {
                        Coordinate coordinate = new Coordinate(x, y);
                        if (coordinate != coordToCheck && !takenCoord.contains(coordinate)) {
                            possibleCoord.add(coordinate);
                        }
                    }
                }
            }
        }
    }

    public HashMap<Coordinate, String> createEmptyShape(int columns, int rows) {
        HashMap<Coordinate, String> shape = new HashMap<>();
        for(int x = 0; x < columns; x++) {
            for(int y = 0; y < rows; y++) {
               Coordinate coordinate = new Coordinate(x, y);
               shape.put(coordinate, " ");
            }
        }
        return shape;
    }
}
