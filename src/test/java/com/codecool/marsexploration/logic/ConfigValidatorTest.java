package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.MapConfig;
import com.codecool.marsexploration.data.ResourceConfig;
import com.codecool.marsexploration.data.TerrainConfig;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigValidatorTest {

    ConfigValidator validator = new ConfigValidator();
    static Random random = new Random();

    @ParameterizedTest
    @MethodSource("provideArgumentsForValidateConfigTest")
    void validateConfig(ValidatorTestParams params) {
        int mapWidth = params.configs().mapWidth();
        double mapSize = mapWidth * mapWidth;
        boolean result = validator.validateConfig(params.configs());
        boolean expected = Math.round((mapSize/100)*90) >= params.sum() ? true : false;
        assertEquals(expected, result);
    }
    private static Stream<Arguments> provideArgumentsForValidateConfigTest() {
        return Stream.of(
                Arguments.of(getTestParams(random.nextInt(60)+1, random.nextInt(6)+1, random.nextInt(10)+1, random.nextInt(6)+1, random.nextInt(10)+1, random.nextInt(50)+1, random.nextInt(50)+1)),
                Arguments.of(getTestParams(random.nextInt(60)+1, random.nextInt(3)+1, random.nextInt(10)+1, random.nextInt(3)+1, random.nextInt(10)+1, random.nextInt(20)+1, random.nextInt(20)+1)),
                Arguments.of(getTestParams(0, 0, 0, 0, 0, 0, 0)),
                Arguments.of(getTestParams(0, 0, 0, 0, 0, 1, 1))
        );
    }

    private static ValidatorTestParams getTestParams(int mapSize, int amountMountains, int maxMountainSize, int amountPits, int maxPitSize, int amountWater, int amountMinerals) {
        double sum = amountWater + amountMinerals;

        List<Integer> mountainAreas = new ArrayList<>();
        sum += getTerrainListAndReturnAreaSum(amountMountains, maxMountainSize, mountainAreas);
        TerrainConfig mountainConfig = new TerrainConfig("M", (ArrayList<Integer>) mountainAreas);
        List<Integer> pitAreas = new ArrayList<>();
        sum += getTerrainListAndReturnAreaSum(amountPits, maxPitSize, pitAreas);
        TerrainConfig pitConfig = new TerrainConfig("P", (ArrayList<Integer>) pitAreas);
        List<TerrainConfig> terrainConfigs = new ArrayList<>();
        terrainConfigs.add(mountainConfig);
        terrainConfigs.add(pitConfig);
        List<ResourceConfig> resourceConfigs = new ArrayList<>();
        resourceConfigs.add(new ResourceConfig("W", amountWater, "P"));
        resourceConfigs.add(new ResourceConfig("MI", amountMinerals, "M"));
        System.out.println(mapSize);
        System.out.println(sum);
        return new ValidatorTestParams(new MapConfig("Test", mapSize, (ArrayList<TerrainConfig>) terrainConfigs, (ArrayList<ResourceConfig>) resourceConfigs), sum);

    }

    private static double getTerrainListAndReturnAreaSum(int amountTerrain, int maxTerrainSize, List<Integer> terrainArea) {
        double sum = 0;
        for (int i = 0; i < amountTerrain; i++) {
            Integer areaToAdd = random.nextInt(maxTerrainSize);
            terrainArea.add(areaToAdd);
            sum += areaToAdd;
        }
        return sum;
    }
}