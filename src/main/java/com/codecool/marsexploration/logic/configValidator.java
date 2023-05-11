package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.MapConfig;
import com.codecool.marsexploration.data.ResourceConfig;
import com.codecool.marsexploration.data.TerrainConfig;

import java.util.ArrayList;
import java.util.List;

public class configValidator {
    private final int MAX_COVERAGE_PERCENT = 90;

    public boolean validateConfig(MapConfig configs) {
        double mapSize = Math.pow(configs.mapWidth(), 2);
        double sumOfTerrainOccupation = getSumOfTerrainOccupation(configs.terrainConfigs());
        double sumOfRessourceOccupation = getSumOfResourceOccupation(configs.resourceConfigs());
        return (mapSize/100)*MAX_COVERAGE_PERCENT >= (sumOfTerrainOccupation + sumOfRessourceOccupation) ? true : false;
    }

    private static double getSumOfResourceOccupation(List<ResourceConfig> ressources) {
        if (ressources.isEmpty()) return 0;
        double sumOfRessources = ressources.stream()
                .reduce(0, (acc, curr) -> acc + curr.amount(), Integer::sum);
        return 0;
    }

    private static double getSumOfTerrainOccupation(List<TerrainConfig> terrains) {
        double sumOfTerrains = terrains.stream()
                .mapToDouble(t -> getSumUpInstancesOfTerrain(t))
                .sum();
        return 0;
    }

    private static double getSumUpInstancesOfTerrain(TerrainConfig t) {
        if (t.areas().isEmpty()) return 0;
        double sumOfTerrain = t.areas().stream()
                .reduce(0, Integer::sum);
        return sumOfTerrain;
    }
}
