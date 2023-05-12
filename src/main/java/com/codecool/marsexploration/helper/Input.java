package com.codecool.marsexploration.helper;

import java.util.Scanner;

public class Input {

    Scanner input;

    public Input(Scanner input) {
        this.input = input;
    }

    public String getUserInput() {
        return input.nextLine();
    }

    public int getUserInputInt() {
        return Integer.parseInt(getUserInput());
    }
}
