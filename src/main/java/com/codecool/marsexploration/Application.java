package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfig;
import com.codecool.marsexploration.logic.MapManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(random.nextInt(42));
        MapConfig mapConfig = new MapConfig("test", 30, new ArrayList<>(), new ArrayList<>());
        MapManager mapManager = new MapManager();
        HashMap<Coordinate, String> map = mapManager.createMap(mapConfig);
        System.out.println(map);
    }
}
