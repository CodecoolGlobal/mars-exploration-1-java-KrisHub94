package com.codecool.marsexploration.data;

import java.util.List;

public record MapConfig(String fileName, int mapWidth, List<TerrainConfig> terrainConfigs, List<ResourceConfig> resourceConfigs) {
}
