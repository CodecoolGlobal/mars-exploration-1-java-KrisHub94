package com.codecool.marsexploration.ui;

import com.codecool.marsexploration.data.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;

public class Printer {
    public void displayMap(HashMap<Coordinate, String> map) {
        boolean isChecking = true;
        int row = 0;
        while (isChecking) {
            Coordinate firstTileOfRow = new Coordinate(0, row);
            if(map.get(firstTileOfRow) == null) {
                isChecking = false;
            }
            else {
                displayRow(map, row);
                row++;
            }
        }
    }
    public void displayRow(HashMap<Coordinate, String> map, int row) {
        boolean isChecking = true;
        int column = 0;
        ArrayList<String> rowSymbols = new ArrayList<>();
        while (isChecking) {
            Coordinate nextCoords = new Coordinate(column, row);
            String nextTile = map.get(nextCoords);
            if(nextTile == null) {
                isChecking = false;
                System.out.println(String.join("", rowSymbols));
            }
            else {
                rowSymbols.add(nextTile);
                column++;
            }
        }
    }
}
