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
        AppManager appManager = new AppManager();
        appManager.run();
    }
}
