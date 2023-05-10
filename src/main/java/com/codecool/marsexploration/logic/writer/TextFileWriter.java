package com.codecool.marsexploration.logic.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileWriter {
    public void write(String text, String destination) {
        try {
            doWrite(text, destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void doWrite(String text, String destination) throws IOException {
        File file = new File(destination);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        }
    }
}

