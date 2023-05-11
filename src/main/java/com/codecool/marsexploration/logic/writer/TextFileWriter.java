package com.codecool.marsexploration.logic.writer;

import com.codecool.marsexploration.data.Coordinate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TextFileWriter {
    public void writeMap(String destination, HashMap<Coordinate, String> map) {
        try {
            doWrite(destination, map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void doWrite(String destination, HashMap<Coordinate, String> map) throws IOException {
        String path = "src/main/resources/" + destination + ".txt";
        File file = new File(path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            boolean isChecking = true;
            int row = 0;
            while (isChecking) {
                Coordinate firstTileOfRow = new Coordinate(0, row);
                if(map.get(firstTileOfRow) == null) {
                    isChecking = false;
                }
                else {
                    writeRow(map, row, writer);
                    row++;
                }
            }
        }
    }
    public void writeRow(HashMap<Coordinate, String> map, int row, BufferedWriter writer) {
        boolean isChecking = true;
        int column = 0;
        ArrayList<String> rowSymbols = new ArrayList<>();
        while (isChecking) {
            Coordinate nextCoords = new Coordinate(column, row);
            String nextTile = map.get(nextCoords);
            if(nextTile == null) {
                isChecking = false;
                try {
                    writer.write(String.join("", rowSymbols));
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                rowSymbols.add(nextTile);
                column++;
            }
        }
    }
}

