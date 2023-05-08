package com.codecool.marsexploration.data;

import java.util.ArrayList;

public record MapConfig(String fileName, int mapWidth, ArrayList<TerrainConfig> terrainConfigs, ArrayList<ResourceConfig> resourceConfigs) {
}
