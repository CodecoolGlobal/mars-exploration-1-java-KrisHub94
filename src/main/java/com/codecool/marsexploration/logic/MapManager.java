package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfig;

import java.util.HashMap;

public class MapManager {
    private final String EMPTY_TILE = " ";
    public HashMap<Coordinate, String> createMap(MapConfig mapConfig) {
        int width = mapConfig.mapWidth();
        HashMap<Coordinate, String> map = new HashMap<>();
        for(int x = 1; x <= width; x++) {
            for(int y = 1; y <= width; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                map.put(coordinate, EMPTY_TILE);
            }
        }
        return map;
    }
}
