package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;

import java.util.*;
import java.util.stream.Collectors;

public class ShapeGenerator {
    private final Random random;

    public ShapeGenerator(Random random) {
        this.random = random;
    }


    public HashMap<Coordinate, String> createShape(int area, String symbol) {
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
        takenCoord.add(firstCoords);
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
    public HashMap<Coordinate, String> createShape2(int mapSideLength, int areaSize, String symbol) {
        HashMap<Coordinate, String> shape = createEmptyShape(mapSideLength, mapSideLength);

        Set<Coordinate> possibleCoord = new HashSet<>();
        Set<Coordinate> takenCoord = new HashSet<>();

        int firstCoordX = random.nextInt(mapSideLength);
        int firstCoordY = random.nextInt(mapSideLength);
        Coordinate firstCoords = new Coordinate(firstCoordX, firstCoordY);
        shape.put(firstCoords, symbol);
        takenCoord.add(firstCoords);
        addPossibleCoordinatesToList(mapSideLength, mapSideLength, firstCoords, possibleCoord, takenCoord);

        int lowestX = firstCoordX;
        int highestX = firstCoordX;
        int lowestY = firstCoordY;
        int highestY = firstCoordY;
        int counter = 1;
        while (counter < areaSize) {
            Coordinate[] possibleCoordArray = possibleCoord.toArray(new Coordinate[possibleCoord.size()]);
            Coordinate nextCoords = possibleCoordArray[random.nextInt(possibleCoordArray.length)];
            lowestX = nextCoords.x() < lowestX ? nextCoords.x() : lowestX;
            highestX = nextCoords.x() > highestX ? nextCoords.x() : highestX;
            lowestY = nextCoords.y() < lowestY ? nextCoords.y() : lowestY;
            highestY = nextCoords.y() > highestY ? nextCoords.y() : highestY;

            shape.put(nextCoords, symbol);
            possibleCoord.remove(nextCoords);
            takenCoord.add(nextCoords);
            addPossibleCoordinatesToList(mapSideLength, mapSideLength, nextCoords, possibleCoord, takenCoord);
            counter++;
            }
        Coordinate shapeFrame = new Coordinate((highestX - lowestX +1), (highestY - lowestY + 1));
        System.out.println(shapeFrame);

        HashMap<Coordinate, String> shapeResetToZeroCoords = new HashMap<>();
        for (Map.Entry<Coordinate, String> entry : shape.entrySet()) {
            shapeResetToZeroCoords.put(new Coordinate(
                    entry.getKey().x() - lowestX, entry.getKey().y() - lowestY),
                    entry.getValue());
        }
        Set<Coordinate> possibleRessourceCoords = new HashSet<>();
        for (Coordinate coordinate : possibleCoord) {
            possibleRessourceCoords.add(new Coordinate(coordinate.x() - lowestX, coordinate.y() - lowestY));
        }
        System.out.println(possibleRessourceCoords);
        return shapeResetToZeroCoords;
    }

    private void addPossibleCoordinatesToList(int numberOfColumns, int numberOfRows, Coordinate coordToCheck, Set<Coordinate> possibleCoord, Set<Coordinate> takenCoord) {
        for (int x = coordToCheck.x() - 1; x <= coordToCheck.x() + 1; x++) {
            if (x >= 0 && x <= numberOfColumns - 1) {
                for (int y = coordToCheck.y() - 1; y <= coordToCheck.y() + 1; y++) {
                    if (y >= 0 && y <= numberOfRows - 1) {
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
