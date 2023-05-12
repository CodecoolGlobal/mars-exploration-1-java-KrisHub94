package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfig;
import com.codecool.marsexploration.data.ResourceConfig;
import com.codecool.marsexploration.data.TerrainConfig;
import com.codecool.marsexploration.logic.*;
import com.codecool.marsexploration.logic.writer.TextFileWriter;
import com.codecool.marsexploration.ui.MapInputs;
import com.codecool.marsexploration.ui.Printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AppManager {
    private Random random = new Random();//TODO: refactor too many randoms
    Printer printer = new Printer();
    PlacementManager placementManager = new PlacementManager(random);
    ShapeGenerator shapeGenerator = new ShapeGenerator(random);
    ConfigValidator configValidator = new ConfigValidator();
    HashMap<Coordinate, String> createdMap;

    private MapInputs mapInputs;
    private MapConfigFactory mapConfigFactory;
    TextFileWriter writer = new TextFileWriter();


    public AppManager(MapInputs mapInputs, MapConfigFactory mapConfigFactory) {
        this.mapInputs = mapInputs;
        this.mapConfigFactory = mapConfigFactory;
    }



    public void run() {
        boolean isUserCreated = mapInputs.askUserForData();
        while (true) {
            MapConfig mapConfig = isUserCreated ?
                    mapConfigFactory.createUserConfig() :
                    mapConfigFactory.createRandomConfig();
            boolean validatorResult = configValidator.validateConfig(mapConfig);
            if(validatorResult) {
                createNewMap(mapConfig);
                break;
            }
        }

//        if(isUserCreated) {
//            while (true) {
//                MapConfig mapConfig = mapConfigFactory.createUserConfig();
//                boolean validatorResult = configValidator.validateConfig(mapConfig);
//                if(validatorResult) {
//                    createNewMap(mapConfig);
//                    break;
//                }
//            }
//        }
//        else {
//            while (true) {
//                MapConfig mapConfig = mapConfigFactory.createRandomConfig();
//                boolean validatorResult = configValidator.validateConfig(mapConfig);
//                if(validatorResult) {
//                    createNewMap(mapConfig);
//                    break;
//                }
//            }
//
//        }

    }

    //TODO: extract to own class and following functions
    private void createNewMap(MapConfig mapConfig) {
        createdMap = new MapManager().createMap(mapConfig);

        List <HashMap> createdShapes = createShapes(mapConfig);

        placeShapes(createdShapes, mapConfig);
        placeResources(mapConfig, createdMap);
        printer.displayMap(createdMap);
        writer.writeMap(mapConfig.mapName(), createdMap);
    }

    public List<HashMap> createShapes(MapConfig mapConfig) {
        List <HashMap> allShapes = new ArrayList<>();
        for(TerrainConfig terrainConfig: mapConfig.terrainConfigs())  {
            for (Integer area : terrainConfig.areas()) {
                HashMap<Coordinate, String> newShape = shapeGenerator.createShape(mapConfig.mapWidth(), area, terrainConfig.symbol());
                allShapes.add(newShape);
            }
        };
        return allShapes;
    }

    public void placeShapes(List <HashMap> createdShapes, MapConfig mapConfig) {
        for(HashMap createdShape : createdShapes) {
            placementManager.handleShape(
                    createdMap,
                    createdShape,
                    mapConfig);
        }
    }

    public void placeResources(MapConfig mapConfig, HashMap map) {
        for(ResourceConfig resourceConfig: mapConfig.resourceConfigs()) {
            placementManager.handleResource(
                    resourceConfig.symbol(),
                    resourceConfig.amount(),
                    resourceConfig.preference(),
                    map,
                    mapConfig.mapWidth());
        };
    }
}
