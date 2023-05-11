package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfig;
import com.codecool.marsexploration.data.ResourceConfig;
import com.codecool.marsexploration.data.TerrainConfig;
import com.codecool.marsexploration.logic.ConfigValidator;
import com.codecool.marsexploration.logic.MapManager;
import com.codecool.marsexploration.logic.PlacementManager;
import com.codecool.marsexploration.logic.ShapeGenerator;
import com.codecool.marsexploration.logic.writer.TextFileWriter;
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
    private List<Integer> mountainsSizes = new ArrayList<>(), pitsSizes = new ArrayList<>();
    private int mapWidth, mineralsAmount, waterAmount;
    private MapInputs mapInputs = new MapInputs();
    TextFileWriter writer = new TextFileWriter();


    public void run() {
        mapName = mapInputs.chooseMapName();

        boolean isUserCreated = mapInputs.askUserForData();
        if(isUserCreated) {
            boolean isConfigValid = false;
            while (!isConfigValid) {
                MapConfig mapConfig = createUserConfig();
                boolean validatorResult = configValidator.validateConfig(mapConfig);
                if(validatorResult) {
                    isConfigValid = true;
                    createNewMap(mapConfig);
                }
            }
        }
        else {
            boolean isConfigValid = false;
            while (!isConfigValid) {
                MapConfig mapConfig = createRandomConfig();
                boolean validatorResult = configValidator.validateConfig(mapConfig);
                if(validatorResult) {
                    isConfigValid = true;
                    createNewMap(mapConfig);
                }
            }

        }
        ;

    }

    private MapConfig createUserConfig() {
        mapWidth = mapInputs.chooseMapWidth();
        mountainsSizes = mapInputs.chooseTerrainAmountAndSizes(mountainsSizes);
        pitsSizes = mapInputs.chooseTerrainAmountAndSizes(pitsSizes);
        mineralsAmount = mapInputs.chooseResourceAmount();
        waterAmount = mapInputs.chooseResourceAmount();
        MapConfig mapConfig = mapManager.createMapConfig(mountainsSizes, pitsSizes, mineralsAmount, waterAmount, mapName, mapWidth);
        return mapConfig;
    }
    private MapConfig createRandomConfig() {
        mapWidth = mapInputs.generateRandomInteger();
        mountainsSizes = mapInputs.generateRandomList();
        pitsSizes = mapInputs.generateRandomList();
        mineralsAmount = mapInputs.generateRandomInteger();
        waterAmount = mapInputs.generateRandomInteger();
        MapConfig mapConfig = mapManager.createMapConfig(mountainsSizes, pitsSizes, mineralsAmount, waterAmount, mapName, mapWidth);
        return mapConfig;
    }

    private void createNewMap(MapConfig mapConfig) {


        createdMap = mapManager.createMap(mapConfig);

        List <HashMap> createdShapes = createShapes(mapConfig);

        placeShapes(createdShapes, mapConfig);
        placeResources(mapConfig, createdMap);
        printer.displayMap(createdMap);
        writer.writeMap(mapName, createdMap);
    }

    public List<HashMap> createShapes(MapConfig mapConfig) {
        List <HashMap> allShapes = new ArrayList<>();
        for(TerrainConfig terrainConfig: mapConfig.terrainConfigs())  {
            for (Integer area : terrainConfig.areas()) {
                HashMap<Coordinate, String> newShape = shapeGenerator.createShape2(mapWidth, area, terrainConfig.symbol());
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
