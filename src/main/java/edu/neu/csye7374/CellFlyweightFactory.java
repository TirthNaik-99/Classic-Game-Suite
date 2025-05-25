package edu.neu.csye7374;

import java.util.HashMap;
import java.util.Map;

public class CellFlyweightFactory {
    private final Map<String, Cell> cells = new HashMap<>();

    public Cell getCell(String key) {
        return cells.computeIfAbsent(key, Cell::new);
    }
}
