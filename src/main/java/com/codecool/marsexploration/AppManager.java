package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfig;
import com.codecool.marsexploration.data.ResourceConfig;
import com.codecool.marsexploration.data.TerrainConfig;
import com.codecool.marsexploration.logic.ConfigValidator;
import com.codecool.marsexploration.logic.MapManager;
import com.codecool.marsexploration.logic.PlacementManager;
import com.codecool.marsexploration.logic.ShapeGenerator;
import com.codecool.marsexploration.ui.MapInputs;
import com.codecool.marsexploration.ui.Printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AppManager {
    private Random random = new Random();//TODO: refactor too many randoms
    MapManager mapManager = new MapManager();
    Printer printer = new Printer();
    PlacementManager placementManager = new PlacementManager(random);
    ShapeGenerator shapeGenerator = new ShapeGenerator(random);
    ConfigValidator configValidator = new ConfigValidator();
    HashMap<Coordinate, String> createdMap;

    private String mapName;
    private List<Integer> mountainsSizes, pitsSizes;
    private int mapWidth, mineralsAmount, waterAmount;

    public void run() {
        getAllData();
        createNewMap();

    }

    private void getAllData() {
        MapInputs mapInputs = new MapInputs();
        mapInputs.askUserForData();

        mapName = mapInputs.getMapName();
        mapWidth = mapInputs.getMapWidth();
        mountainsSizes = mapInputs.getMountainsSizes();
        pitsSizes = mapInputs.getPitsSizes();
        mineralsAmount = mapInputs.getMineralsAmount();
        waterAmount = mapInputs.getWaterAmount();
    }

    private void createNewMap() {
        List terrainConfig = new ArrayList<>();
        terrainConfig.add(new TerrainConfig("^", mountainsSizes));
        terrainConfig.add(new TerrainConfig("#", pitsSizes));
        List resourceConfig = new ArrayList<>();
        resourceConfig.add(new ResourceConfig("*", mineralsAmount, "^"));
        resourceConfig.add(new ResourceConfig("~", waterAmount, "#"));
        MapConfig mapConfig = new MapConfig(mapName, mapWidth, terrainConfig, resourceConfig);
        if(!configValidator.validateConfig(mapConfig)) {
            System.out.println("not valid");
            return;
        }
        createdMap = mapManager.createMap(mapConfig);

        List <HashMap> createdShapes = createShapes(mapConfig);

        placeShapes(createdShapes, mapConfig);
        placeResources(mapConfig, createdMap);
        printer.displayMap(createdMap);
    }

    public List<HashMap> createShapes(MapConfig mapConfig) {
        List <HashMap> allShapes = new ArrayList<>();
        for(TerrainConfig terrainConfig: mapConfig.terrainConfigs())  {
            for (Integer area : terrainConfig.areas()) {
                HashMap<Coordinate, String> newShape = shapeGenerator.createShape2(mapWidth, area, terrainConfig.symbol());
                System.out.println("shape:");
                printer.displayMap(newShape);
                allShapes.add(newShape);
            }
        };
        return allShapes;
    }

    public void placeShapes(List <HashMap> createdShapes, MapConfig mapConfig) {
        for(HashMap createdShape : createdShapes) {
            placementManager.handleShape(createdMap, createdShape, mapConfig);
        }
    }

    public void placeResources(MapConfig mapConfig, HashMap map) {
        for(ResourceConfig resourceConfig: mapConfig.resourceConfigs()) {
            placementManager.handleResource(resourceConfig.symbol(), resourceConfig.amount(), resourceConfig.preference(), map, mapWidth);
        };
    }
}
