package com.codecool.marsexploration;

import com.codecool.marsexploration.helper.Input;
import com.codecool.marsexploration.logic.MapConfigFactory;
import com.codecool.marsexploration.logic.MapManager;
import com.codecool.marsexploration.ui.MapInputs;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Input input = new Input(new Scanner(System.in));
        MapInputs mapInputs = new MapInputs(input);

        AppManager appManager = new AppManager(mapInputs, new MapConfigFactory(mapInputs, new MapManager()));
        appManager.run();
    }
}
