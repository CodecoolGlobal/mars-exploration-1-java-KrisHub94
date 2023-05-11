package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;

import java.util.*;

public class ShapeGenerator {
    private final Random random;

    public ShapeGenerator(Random random) {
        this.random = random;
    }

    public HashMap<Coordinate, String> createShape(int mapSideLength, int areaSize, String symbol) {
        HashMap<Coordinate, String> shape = createEmptyShape(mapSideLength, mapSideLength);

        Set<Coordinate> possibleCoord = new HashSet<>();
        Set<Coordinate> takenCoord = new HashSet<>();

        Coordinate firstCoords = getFirstCoordinates(mapSideLength);
        handleNewCoordinatesForShape(mapSideLength, symbol, shape, possibleCoord, takenCoord, firstCoords);

        int lowestX = firstCoords.x();
        int lowestY = firstCoords.y();
        int counter = 1;
        while (counter < areaSize) {
            Coordinate nextCoords = getNextCoords(possibleCoord);

            lowestX = nextCoords.x() < lowestX ? nextCoords.x() : lowestX;
            lowestY = nextCoords.y() < lowestY ? nextCoords.y() : lowestY;

            handleNewCoordinatesForShape(mapSideLength, symbol, shape, possibleCoord, takenCoord, nextCoords);
            counter++;
            }
        HashMap<Coordinate, String> shapeResetToZeroCoords = setShapeCoordinatesToZero(shape, lowestX, lowestY);
        return shapeResetToZeroCoords;
    }

    private Coordinate getNextCoords(Set<Coordinate> possibleCoord) {
        Coordinate[] possibleCoordArray = possibleCoord.toArray(new Coordinate[possibleCoord.size()]);
        Coordinate nextCoords = possibleCoordArray[random.nextInt(possibleCoordArray.length)];
        return nextCoords;
    }

    private void handleNewCoordinatesForShape(int mapSideLength, String symbol, HashMap<Coordinate, String> shape, Set<Coordinate> possibleCoord, Set<Coordinate> takenCoord, Coordinate newCoordinates) {
        if (!possibleCoord.isEmpty()) {
            possibleCoord.remove(newCoordinates);
        }
        shape.put(newCoordinates, symbol);
        takenCoord.add(newCoordinates);
        addPossibleCoordinatesToList(mapSideLength, mapSideLength, newCoordinates, possibleCoord, takenCoord);
    }

    private Coordinate getFirstCoordinates(int mapSideLength) {
        return new Coordinate(random.nextInt(mapSideLength), random.nextInt(mapSideLength));
    }

    private static HashMap<Coordinate, String> setShapeCoordinatesToZero(HashMap<Coordinate, String> shape, int lowestX, int lowestY) {
        HashMap<Coordinate, String> shapeResetToZeroCoords = new HashMap<>();
        for (Map.Entry<Coordinate, String> entry : shape.entrySet()) {
            shapeResetToZeroCoords.put(new Coordinate(
                    entry.getKey().x() - lowestX, entry.getKey().y() - lowestY),
                    entry.getValue());
        }
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
