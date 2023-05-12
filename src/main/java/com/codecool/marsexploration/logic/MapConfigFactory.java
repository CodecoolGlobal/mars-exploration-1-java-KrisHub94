package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.MapConfig;
import com.codecool.marsexploration.ui.MapInputs;

public class MapConfigFactory {
    MapInputs mapInputs;
    MapManager mapManager;

    public MapConfigFactory(MapInputs mapInputs, MapManager mapManager) {
        this.mapInputs = mapInputs;
        this.mapManager = mapManager;
    }

    public MapConfig createUserConfig() {
        return mapManager.createMapConfig(
                mapInputs.chooseTerrainAmountAndSizes(),
                mapInputs.chooseTerrainAmountAndSizes(),
                mapInputs.getUserInputNumber("Enter amount of minerals: "),
                mapInputs.getUserInputNumber("Enter amount of water: "),
                mapInputs.chooseMapName(),
                mapInputs.getUserInputNumber("Enter map-width: "));
    }
    public MapConfig createRandomConfig() {
        return mapManager.createMapConfig(
                mapInputs.generateRandomList(),
                mapInputs.generateRandomList(),
                mapInputs.generateRandomInteger(),
                mapInputs.generateRandomInteger(),
                mapInputs.chooseMapName(),
                mapInputs.generateRandomInteger());
    }
}
