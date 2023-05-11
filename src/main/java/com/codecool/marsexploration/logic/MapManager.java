package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfig;
import com.codecool.marsexploration.data.ResourceConfig;
import com.codecool.marsexploration.data.TerrainConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapManager {
    private final String EMPTY_TILE = " ";
    public HashMap<Coordinate, String> createMap(MapConfig mapConfig) {
        int width = mapConfig.mapWidth();
        HashMap<Coordinate, String> map = new HashMap<>();
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < width; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                map.put(coordinate, EMPTY_TILE);
            }
        }
        return map;
    }
    public MapConfig createMapConfig(List mountainsSizes, List pitsSizes, Integer mineralsAmount, Integer waterAmount, String mapName, Integer mapWidth) {
        List terrainConfig = new ArrayList<>();
        terrainConfig.add(new TerrainConfig("^", mountainsSizes));
        terrainConfig.add(new TerrainConfig("#", pitsSizes));
        List resourceConfig = new ArrayList<>();
        resourceConfig.add(new ResourceConfig("*", mineralsAmount, "^"));
        resourceConfig.add(new ResourceConfig("~", waterAmount, "#"));
        MapConfig mapConfig = new MapConfig(mapName, mapWidth, terrainConfig, resourceConfig);
        return mapConfig;
    }
}
