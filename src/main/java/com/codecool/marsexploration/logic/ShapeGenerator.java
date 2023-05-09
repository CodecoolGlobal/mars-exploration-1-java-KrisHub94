package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;

import java.util.HashMap;
import java.util.Random;

public class ShapeGenerator {
    public HashMap<Coordinate, String> createShape(int area, String symbol) {
        Random random = new Random();
        int numberOfRows = random.nextInt(area) + 1;
        int leastNumberOfColumns = area - (numberOfRows - 1);
        int numberOfColumns = random.nextInt(area/2) + leastNumberOfColumns;
        HashMap<Coordinate, String> shape = createEmptyShape(numberOfColumns, numberOfRows);
        int counter = 0;
        while (counter < area) {
            int randomRow = random.nextInt(numberOfRows);
            int randomColumn = random.nextInt(numberOfColumns);
            Coordinate nextCoords = new Coordinate(randomColumn, randomRow);
            if(shape.get(nextCoords).equals(" ")) {
                shape.put(nextCoords, symbol);
                counter++;
            }
        }
        return shape;
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
