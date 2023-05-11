package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ShapeGeneratorTest {

    ShapeGenerator generator = new ShapeGenerator(new Random());

    @ParameterizedTest
    @CsvSource({
            "20, 6, +, 6",
            "30, 10, X, 10"
            //"1, 0, Y, 0"
    })
    void createShape2amountOfShapeFieldsTest(int mapWidth, int shapeSize, String symbol, int expected) {
        Map<Coordinate, String> shape = generator.createShape2(mapWidth, shapeSize, symbol);
        Map<Coordinate, String> shapeFields = shape.entrySet().stream()
                .filter(entry -> entry.getValue().equals(symbol))
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        assertEquals(expected, shapeFields.size());
    }

    @ParameterizedTest
    @CsvSource({
            "20, 6, +, 6",
            "30, 10, X, 10"
            //"1, 0, Y, 0"
    })
    void createShape2ConnectionOfFieldsTest(int mapWidth, int shapeSize, String symbol, int expected) {
        Map<Coordinate, String> shape = generator.createShape2(mapWidth, shapeSize, symbol);
        Map<Coordinate, String> shapeFields = shape.entrySet().stream()
                .filter(entry -> entry.getValue().equals(symbol))
                .filter(entry -> hasNeighbor(entry.getKey(), shape))
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        assertEquals(expected, shapeFields.size());
    }

    private boolean hasNeighbor(Coordinate coordToCheck, Map<Coordinate, String> shape) {
        for (int x = coordToCheck.x() - 1; x <= coordToCheck.x() + 1; x++) {
                for (int y = coordToCheck.y() - 1; y <= coordToCheck.y() + 1; y++) {
                        Coordinate coordinate = new Coordinate(x, y);
                        if (coordinate != coordToCheck && shape.containsKey(coordinate)) {
                            return true;
                        }
                }

        }
       return false;
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 10, 100})
    void createEmptyShapeTest(int input) {
        Map<Coordinate, String> map = generator.createEmptyShape(input, input);
        assertEquals(input * input, map.size());
    }
}