package com.sim.test.game.Constants;

public enum TileCells {

    BLOCKED("Blocked"),
    DOOR("Door"),
    LIGHT("Light"),
    BOUNDARY("Boundary");

    private String tileCell;

    TileCells(String cell) {
        this.tileCell = cell;
    }

    public String localName() {
        return tileCell;
    }
}
