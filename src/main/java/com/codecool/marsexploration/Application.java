package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfig;
import com.codecool.marsexploration.logic.MapManager;
import com.codecool.marsexploration.logic.PlacementManager;
import com.codecool.marsexploration.logic.ShapeGenerator;
import com.codecool.marsexploration.ui.Printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(random.nextInt(42));
        MapConfig mapConfig = new MapConfig("test", 30, new ArrayList<>(), new ArrayList<>());
        MapManager mapManager = new MapManager();
        Printer printer = new Printer();
        PlacementManager placementManager = new PlacementManager();
        HashMap<Coordinate, String> map = mapManager.createMap(mapConfig);
        System.out.println("empty map:");
        printer.displayMap(map);
        ShapeGenerator shapeGenerator = new ShapeGenerator(random);
        HashMap<Coordinate, String> shape1 = shapeGenerator.createShape(10, "Y");
        HashMap<Coordinate, String> shape2 = shapeGenerator.createShape(15, "Y");
        placementManager.handleShape(map, shape1, mapConfig);
        System.out.println("shape1:");
        printer.displayMap(shape1);
        System.out.println("map with shape1:");
        printer.displayMap(map);
        placementManager.handleShape(map, shape2, mapConfig);
        System.out.println("shape2:");
        printer.displayMap(shape2);
        System.out.println("map with shape2:");
        printer.displayMap(map);
    }
}
