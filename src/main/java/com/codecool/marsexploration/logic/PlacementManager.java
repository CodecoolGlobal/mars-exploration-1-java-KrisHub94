package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class PlacementManager {
    public void handleShape(HashMap<Coordinate, String> map, HashMap<Coordinate, String> shape, MapConfig mapConfig) {
        Set<Coordinate> possibleCoordinates = map.keySet().stream().filter(coordinate -> checkForShape(map, shape, coordinate, mapConfig)).collect(Collectors.toSet());
        if(possibleCoordinates.isEmpty()) {
            return;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(possibleCoordinates.size());
        Coordinate targetCoordinate = possibleCoordinates.stream().toList().get(randomIndex);
        placeShape(map, shape, targetCoordinate);
    }
    public boolean checkForShape(HashMap<Coordinate, String> map, HashMap<Coordinate, String> shape, Coordinate targetCoordinate, MapConfig mapConfig) {
        int xOffset = targetCoordinate.x();
        int yOffset = targetCoordinate.y();
        Set<Coordinate> nonEmptyTiles = shape.keySet().stream().filter(coordinate -> !shape.get(coordinate).equals(" ")).collect(Collectors.toSet());
        int mapWidth = mapConfig.mapWidth();
        int shapeSize = nonEmptyTiles.size();
        if(((shapeSize + xOffset) >= mapWidth) || ((shapeSize + yOffset) >= mapWidth)) {
            return false;
        }
        Set<Coordinate> coordinatesToCheck = nonEmptyTiles.stream()
                .map(coordinate -> {
            int targetX = coordinate.x() + xOffset;
            int targetY = coordinate.y() + yOffset;
            return new Coordinate(targetX, targetY);
        }).collect(Collectors.toSet());
        Set<Coordinate> takenTiles = coordinatesToCheck.stream().filter(coordinate -> !map.get(coordinate).equals(" ")).collect(Collectors.toSet());
        if(takenTiles.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
    public void placeShape(HashMap<Coordinate, String> map, HashMap<Coordinate, String> shape, Coordinate targetCoordinate) {
        int xOffset = targetCoordinate.x();
        int yOffset = targetCoordinate.y();
        for(Coordinate coordinate: shape.keySet()) {
            String symbol = shape.get(coordinate);
            if(!symbol.equals(" ")) {
                Coordinate mapCoordinate = new Coordinate(coordinate.x() + xOffset, coordinate.y() + yOffset);
                map.put(mapCoordinate, symbol);
            }
        };
    }
    public void handleResource(String symbol, int amount, String preference, HashMap<Coordinate, String> map, int mapWidth) {
        Set<Coordinate> possibleCoordinates = map.keySet().stream().filter(coordinate -> checkForResource(coordinate, map, mapWidth, preference)).collect(Collectors.toSet());
        if(possibleCoordinates.size() < amount) {
            return;
        }
        int counter = 0;
        while (counter < amount) {
            Random random = new Random();
            int randomIndex = random.nextInt(possibleCoordinates.size());
            Coordinate targetCoordinate = possibleCoordinates.stream().toList().get(randomIndex);
            map.put(targetCoordinate, symbol);
            counter++;
        }

    }
    public boolean checkForResource(Coordinate coordinate, HashMap<Coordinate, String> map, int mapWidth, String symbol) {
        String tileSymbol = map.get(coordinate);
        if(!tileSymbol.equals(" ")) {
            return false;
        }
        Set<Coordinate> surroundingCoordinates = new HashSet<>();
        for(int x = coordinate.x() - 1; x <= coordinate.x() + 1; x++) {
            if(x < 0 || x >= mapWidth) {
                continue;
            }
            for (int y = coordinate.y() - 1; y <= coordinate.y() + 1; y++) {
                if(y < 0 || y >= mapWidth) {
                    continue;
                }
                Coordinate surroundingCoordinate = new Coordinate(x, y);
                surroundingCoordinates.add(surroundingCoordinate);
            }
        }
        for(Coordinate checkedCoordinate: surroundingCoordinates) {
            if(map.get(checkedCoordinate).equals(symbol)) {
                return true;
            }
        }
        return false;
    }
}
