package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfig;
import com.codecool.marsexploration.logic.MapManager;
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
        HashMap<Coordinate, String> map = mapManager.createMap(mapConfig);
        printer.displayMap(map);
        ShapeGenerator shapeGenerator = new ShapeGenerator(random);
        //HashMap<Coordinate, String> shape = shapeGenerator.createShape(10, "Y");
        HashMap<Coordinate, String> shape = shapeGenerator.createShape2(30, 10, "^");
        printer.displayMap(shape);
    }
}
