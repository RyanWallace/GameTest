package com.sim.test.game.Constants;

public enum TileLayers {

    WATER("Water"),
    CONFECTIONERY("Confectionery"),
    BUILDINGS("Buildings"),
    TREES("Trees"),
    PATHS("Paths"),
    GRASS("Grass"),
    BOUNDARY("Boundary");

    private String tileLayer;

    TileLayers(String cell) {
        this.tileLayer = cell;
    }

    public String localName() {
        return tileLayer;
    }
}
