package edu.neu.csye7374;

public class Cell {
    private final String value;

    public Cell(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void display() {
        System.out.print(value);
    }
}
