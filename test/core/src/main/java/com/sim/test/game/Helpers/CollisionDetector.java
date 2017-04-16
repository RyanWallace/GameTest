package com.sim.test.game.Helpers;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.sim.test.game.Sprites.Player;
import com.sim.test.game.Sprites.newPlayer;

import static com.badlogic.gdx.Input.Keys.*;
import static com.sim.test.game.Constants.TileCells.BLOCKED;
import static com.sim.test.game.Constants.TileCells.BOUNDARY;
import static com.sim.test.game.Constants.TileLayers.*;

public class CollisionDetector {


    private  float increment;
    private Sprite player;
    private  TiledMap map;
    private  Vector2 velocity = new Vector2();

    public CollisionDetector(TiledMap m, Player p) {

        map = m;
        player = p;
        velocity = p.getVelocity();
    }

    public CollisionDetector(TiledMap m, newPlayer p) {

        map = m;
        player = p;
        velocity = p.getVelocity();
    }

    public void renderCollision() {


        float oldX = player.getX(), oldY = player.getY();
        boolean collisionX = false, collisionY = false;

        // calculate the increment for step in #collidesLeft() and #collidesRight()
        increment = 20; //getCollisionLayer().getTileWidth();
       // increment = player.getWidth() < increment ? player.getWidth() / 2 : increment / 2;

        if(velocity.x < 0) // going left
            collisionX = collidesLeft();
        else if(velocity.x > 0) // going right
            collisionX = collidesRight();

        // react to x collision
        if(collisionX) {
            player.setX(oldX);
            velocity.x = 0;
        }


        // calculate the increment for step in #collidesBottom() and #collidesTop()
        increment = 5; //getCollisionLayer().getTileHeight();
        increment = player.getHeight() < increment ? player.getHeight() / 2 : increment / 2;

        if(velocity.y < 0) // going down
            collisionY = collidesBottom();
        else if(velocity.y > 0) // going up
            collisionY = collidesTop();

        // react to y collision
        if(collisionY) {
            player.setY(oldY);
            velocity.y = 0;
        }

    }


    public boolean isCellBlocked(float x, float y) {

        boolean isBlocked = false;

        if (!isBlocked && checkCollisionLayerForBlockedTile(x, y, (TiledMapTileLayer) map.getLayers().get(BUILDINGS.localName()))) {
            isBlocked = true;
        } else if (!isBlocked && checkCollisionLayerForBlockedTile(x, y, (TiledMapTileLayer) map.getLayers().get(TREES.localName()))) {
            isBlocked = true;
        }  else if (!isBlocked && checkCollisionLayerForBlockedTile(x, y, (TiledMapTileLayer) map.getLayers().get(CONFECTIONERY.localName()))) {
            isBlocked = true;
        }  else if (!isBlocked && checkCollisionLayerForBlockedTile(x, y, (TiledMapTileLayer) map.getLayers().get(WATER.localName()))) {
            isBlocked = true;
        } else if (!isBlocked && checkCollisionLayerForBoundaryTile(x, y, (TiledMapTileLayer) map.getLayers().get(BOUNDARY.localName()))) {
            isBlocked = true;
        }

        return isBlocked;
    }

    public boolean collidesRight() {
        for(float step = 0; step <= player.getHeight(); step += increment)
            if(isCellBlocked(player.getX() + player.getWidth(), player.getY() + step)) {
                return true;
            }
        return  false;
    }

    public boolean collidesLeft() {
        for(float step = 0; step <= player.getHeight(); step += increment)
            if(isCellBlocked(player.getX(), player.getY() + step)) {
                return true;
            }
        return false;
    }

    public boolean collidesTop() {
        for(float step = 0; step <= player.getWidth(); step += increment)
            if(isCellBlocked(player.getX() + step, player.getY() + player.getHeight())) {
                return true;
            }
        return false;

    }

    public boolean collidesBottom() {
        for(float step = 0; step <= player.getWidth(); step += increment)
            if(isCellBlocked(player.getX() + step, player.getY())) {
                return true;
            }
        return false;
    }

    private boolean checkCollisionLayerForBlockedTile(float x, float y, TiledMapTileLayer layer) {
        TiledMapTileLayer.Cell cell = layer.getCell((int) (x / layer.getTileWidth()), (int) (y / layer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(BLOCKED.localName());
    }

    private boolean checkCollisionLayerForBoundaryTile(float x, float y, TiledMapTileLayer layer) {
        TiledMapTileLayer.Cell cell = layer.getCell((int) (x / layer.getTileWidth()), (int) (y / layer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(BOUNDARY.localName());
    }

    public boolean detectCollision(char keyCode, newPlayer player ) {
        boolean collisionDetected = false;


        switch (keyCode) {
            case 'w' :

                if(isCellBlocked(player.getX()+5, player.getY() + 10f)) {
                    player.resetVelocity();
                    collisionDetected = true;
                    break;
                }
                break;

            case 'a' :

                if(isCellBlocked(player.getX() -1f , player.getY())) {
                    player.resetVelocity();
                    collisionDetected = true;
                    break;
                }

                break;

            case 's' :

                if(isCellBlocked(player.getX() +5f, player.getY() -1f)) {
                    player.resetVelocity();
                    collisionDetected = true;
                    break;
                }
                break;

            case 'd' :

                if(isCellBlocked(player.getX() + 10f , player.getY())) {
                    player.resetVelocity();
                    collisionDetected = true;
                    break;
                }
                break;
        }

        return collisionDetected;
    }

    public boolean detectCollision(int keyCode, newPlayer player ) {
        boolean collisionDetected = false;


        switch (keyCode) {
            case W :

                if(isCellBlocked(player.getX(), player.getY() + 25f)) {
                    player.resetVelocity();
                    collisionDetected = true;
                    break;
                }
                break;

            case A :

                if(isCellBlocked(player.getX() -1f , player.getY())) {
                    player.resetVelocity();
                    collisionDetected = true;
                    break;
                }

                break;

            case S :

                if(isCellBlocked(player.getX(), player.getY() -1f )) {
                    player.resetVelocity();
                    collisionDetected = true;
                    break;
                }
                break;

            case D :

                if(isCellBlocked(player.getX() + 17f , player.getY())) {
                    player.resetVelocity();
                    collisionDetected = true;
                    break;
                }
                break;
        }

        return collisionDetected;
    }

}
