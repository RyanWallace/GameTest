package com.sim.test.game.Maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sim.test.game.Constants.MapMetrics;

import static com.sim.test.game.Constants.Assets.MAP;


public class Redtown {

    private TiledMap map;
    private int[]  grass = new int[] {0}, paths = new int[] {1},
            trees = new int[] {2}, buildings = new int[] {3},
            confectionery = new int[] {4}, water = new int[] {5}, boundary = new int[] {6};

    public Redtown() {
        setMap();
    }

    public void render(OrthogonalTiledMapRenderer renderer) {
        renderer.render(grass);
        renderer.render(paths);
        renderer.render(trees);
        renderer.render(buildings);
        renderer.render(confectionery);
        renderer.render(water);
        renderer.render(boundary);
    }

    private void setMap() {
        map = new TmxMapLoader().load(MAP.localName());
    }

    public TiledMap getMap() {
        return map;
    }

    public static Vector2 getMapBounds() {
        return new Vector2(1516, 746);
    }
}
