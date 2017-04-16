package com.sim.test.game.Sprites;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.W;
import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;

import org.joda.time.LocalTime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.sim.test.game.Helpers.CollisionDetector;


public class Player extends Sprite implements InputProcessor {

    private static Vector2 velocity = new Vector2();
    private static TextureAtlas playerAtlas;
    private float animationTime = 0;
    private static Animation still, up, down, left ,right;
    private CollisionDetector collisionDetector;
    private float oldPostionionX = 0, oldPostionionY = 0;
    private boolean keyDown = false, blockedW = false, blockedS = false, blockedA = false, blockedD = false;
    public static int keyUpTimer =0, keyDownTimer =0;
    public static String posn ="INITIAL STATE";
    public static String startTime = "", endTime = "";
    private static LocalTime localTime;
    private static float positionX = 0, positionY = 0;

 

    public Player(Animation initialStanceAnimation, TiledMap m) {
//        super(initialStanceAnimation.getKeyFrame(0));
        still = initialStanceAnimation;
        renderAnimation();
        collisionDetector = new CollisionDetector(m, this);

    }

    private void renderAnimation() {
            up = new Animation(1 / 15f, playerAtlas.findRegions("up"));
            down = new Animation(1 / 15f, playerAtlas.findRegions("down"));
            left = new Animation(1 / 15f, playerAtlas.findRegions("left"));
            right = new Animation(1 / 15f, playerAtlas.findRegions("right"));

            up.setPlayMode(LOOP);
            down.setPlayMode(LOOP);
            left.setPlayMode(LOOP);
            right.setPlayMode(LOOP);
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    private void update(float delta) {

        if(collisionDetector.isCellBlocked(getX() + 1f , getY())) {
            posn = "BLOCKED RIGHT";
            blockedD = true;
            resetVelocity();
        }

        if(collisionDetector.isCellBlocked(getX(), getY() + 1f)) {
            posn = "BLOCKED UP";
            blockedW = true;
            resetVelocity();
        }

        if(collisionDetector.isCellBlocked(getX() -1f , getY())) {
            posn = "Blocked LEFT";
            blockedA = true;
            resetVelocity();
        }

        if(collisionDetector.isCellBlocked(getX(), getY() -1f )) {
            posn = "BLOCKED DOWN";
            blockedS = true;
            resetVelocity();
        }

        if (getY() - positionY == 15f || positionY - getY() == 15f) {
            resetVelocity();
        } else if (getX() - positionX == 15f || positionX - getX() == 15f) {
            resetVelocity();
        }

//        if(Gdx.input.isKeyPressed(Input.Keys.W)){
//            keyDown(Input.Keys.W);
//            velocity.x = 0;
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.S)){
//            keyDown(Input.Keys.S);
//            velocity.x = 0;
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.A)){
//            keyDown(Input.Keys.A);
//            velocity.y = 0;
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.D)){
//            keyDown(Input.Keys.D);
//            velocity.y = 0;
//        }

//        resetVelocity();



        setPlayerPosition(delta);


//        animationTime += delta;
//        setRegion(velocity.x < 0 ? left.getKeyFrame(animationTime) : velocity.x > 0 ? right.getKeyFrame(animationTime)
//                : velocity.y<0 ? down.getKeyFrame(animationTime) : velocity.y > 0 ? up.getKeyFrame(animationTime)
//                : still.getKeyFrame(animationTime));

    }

    private void setPlayerPosition(float delta) {
        // move on x
//        System.out.println("Velocity x: " + velocity.x);
        setX(getX() + velocity.x * delta);


        // move on y
        setY(getY() + velocity.y * delta);
//        System.out.println("Velocity y: " + velocity.y);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }



    @Override
    public boolean keyDown(int keycode) {
        float speed = 60 * 2;

        if(keyDown) {
            resetVelocity();
            return false;
        }

        System.out.println("Key Down");


        switch (keycode) {

            case W:
                System.out.println("W Pressed");

                if (blockedW && hasMoved()) {
                    blockedW = false;
                }

//                if (getY() - positionY == 15) {
//                    keyUp(keycode);
//                    break;
//                }

                positionX = getX();
                positionY = getY();
                localTime = new LocalTime();
                posn = "W Pressed";
                keyDown = true;
                velocity.x = 0;
                setOldPositions();
                velocity.y = speed;
                animationTime = 0;

                break;

            case A:
                System.out.println("A Pressed");


//                if (positionX - getX() == 15) {
//                    keyUp(keycode);
//                    break;
//                }
                if (blockedA && hasMoved()) {
                    blockedA = false;
                }

                positionX = getX();
                positionY = getY();
                posn = "A Pressed";
                keyDown = true;
                velocity.y = 0;
                setOldPositions();
                velocity.x = -speed;
                animationTime = 0;
                break;

            case S:

                System.out.println("S Pressed");

//                if (positionY - getY() == 15) {
//                    keyUp(keycode);
//                    break;
//                }
                if (blockedS && hasMoved()) {
                    blockedS = false;
                }


                positionX = getX();
                positionY = getY();
                posn = "S Pressed";
                keyDown = true;

                velocity.x = 0;
                setOldPositions();
                velocity.y = -speed;
                animationTime = 0;
                break;

            case D:


                System.out.println("D Pressed");
//                if (getX() - positionX == 15) {
//                    keyUp(keycode);
//                    break;
//                }

                if (blockedD && hasMoved()) {
                    blockedD = false;
                }


                positionX = getX();
                positionY = getY();
                posn = "D Pressed";
                keyDown = true;
                velocity.y = 0;
                setOldPositions();
                velocity.x = speed;
                animationTime = 0;
                break;
        }

        System.out.println("Return KeyDown");
        keyDown = true;


        return false;
    }

    public boolean keyDownn(int keycode) {
        startTime = "" + Gdx.graphics.getDeltaTime();
        float speed = 60 * 2;
        keyDownTimer++;
        posn = "Start";


        switch(keycode) {

            /**
             * we have check methods for each direction, need the increment,
             * tho which is the tilewidth, which should be the movement distance also
             *
             * for each direction first check if the cell is blocked for that direction (pos plus increment which is step)
             * then if true for blocked then just break from switch case
             * else move the direction by the increment and animated
             *
             */
            case W:

                if(blockedW && hasMoved()) {
                    blockedW = false;
                    break;
                }

                if(blockedW || !hasMoved()) {
                    break;
                }

                if(collisionDetector.isCellBlocked(getX(), getY() + 4)) {
                    keyDown = false;
                    //blockedW = true;
                    break;
                } else {
                    velocity.x = 0;
                    setOldPositions();
                    velocity.y = speed;
                    animationTime = 0;
                    keyDown = true;
                    //blockedW = false;
                }

                posn = "W Pressed";

                break;

            case S:

                if(blockedS && hasMoved()) {
                    blockedS = false;
                    break;
                }

                if(blockedS|| !hasMoved()) {
                    break;
                }

                if(collisionDetector.isCellBlocked(getX(), getY() - 4)) {
                    keyDown = false;
                    //blockedS = true;
                    break;
                } else {
                    velocity.x = 0;
                    setOldPositions();
                    velocity.y = -speed;
                    animationTime = 0;
                    keyDown = true;
                    //blockedS = false;
                }

                posn = "S Pressed";

                break;
            case A:

                if(blockedA && hasMoved()) {
                    blockedA = false;
                    break;
                }

                if(blockedA || !hasMoved()) {
                    break;
                }

                if(collisionDetector.isCellBlocked(getX() - 4, getY())) {
                    keyDown = false;
                    //blockedA = true;
                    break;
                } else {
                    velocity.y = 0;
                    setOldPositions();
                    velocity.x = -speed;
                    animationTime = 0;
                    keyDown = true;
                    //blockedA = false;
                }


                posn = "A Pressed";

                break;


            case D:

                if(blockedD && hasMoved()) {
                    blockedD = false;
                    break;
                }

                if(blockedD || !hasMoved()) {
                    break;
                }

                if(collisionDetector.isCellBlocked(getX() + 4, getY())) {
                    keyDown = false;
                    //blockedD = true;
                    break;
                } else {
                    velocity.y = 0;
                    setOldPositions();
                    velocity.x = speed;
                    animationTime = 0;
                    keyDown = true;
                    //blockedD= false;

                }


                posn = "D Pressed";
                break;
        }


        posn = "END";


        return keyDown;
    }



    private void setOldPositions() {
        oldPostionionX = getX();
        oldPostionionY = getY();
    }

    private boolean hasMoved() {
        return getX() != oldPostionionX || getY()!= oldPostionionY;
    }



    @Override
    public boolean keyUp(int keycode) {



        System.out.println("Key is up");
//        switch(keycode) {
//
//            case Input.Keys.W:
//                break;
//
//            case Input.Keys.A:
//                break;
//
//            case Input.Keys.S:
//                break;
//
//            case Input.Keys.D:
//                break;
//
//        }
//
//        endTime = "" + Gdx.graphics.getDeltaTime();
//        keyUpTimer++;
        keyDown = false;


        resetVelocity();
//        animationTime = 0;

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
//        float speed = 15;//60 * 2;
//
//        if(keyDown) {
//            resetVelocity();
//            return false;
//        }
//
//        System.out.println("Key Down");
//
//
//        switch (character) {
//
//            case 'w':
//                System.out.println("W Pressed");
//
//                if (blockedW && hasMoved()) {
//                    blockedW = false;
//                }
//
////                if (getY() - positionY == 15) {
////                    keyUp(keycode);
////                    break;
////                }
//
//                positionX = getX();
//                positionY = getY();
//                localTime = new LocalTime();
//                posn = "W Pressed";
//                keyDown = true;
//                velocity.x = 0;
//                setOldPositions();
//                velocity.y = speed;
//                animationTime = 0;
//
//                break;
//
//            case 'a':
//                System.out.println("A Pressed");
//
//
////                if (positionX - getX() == 15) {
////                    keyUp(keycode);
////                    break;
////                }
//                if (blockedA && hasMoved()) {
//                    blockedA = false;
//                }
//
//                positionX = getX();
//                positionY = getY();
//                posn = "A Pressed";
//                keyDown = true;
//                velocity.y = 0;
//                setOldPositions();
//                velocity.x = -speed;
//                animationTime = 0;
//                break;
//
//            case 's':
//
//                System.out.println("S Pressed");
//
////                if (positionY - getY() == 15) {
////                    keyUp(keycode);
////                    break;
////                }
//                if (blockedS && hasMoved()) {
//                    blockedS = false;
//                }
//
//
//                positionX = getX();
//                positionY = getY();
//                posn = "S Pressed";
//                keyDown = true;
//
//                velocity.x = 0;
//                setOldPositions();
//                velocity.y = -speed;
//                animationTime = 0;
//                break;
//
//            case 'd':
//
//
//                System.out.println("D Pressed");
////                if (getX() - positionX == 15) {
////                    keyUp(keycode);
////                    break;
////                }
//
//                if (blockedD && hasMoved()) {
//                    blockedD = false;
//                }
//
//
//                positionX = getX();
//                positionY = getY();
//                posn = "D Pressed";
//                keyDown = true;
//                velocity.y = 0;
//                setOldPositions();
//                velocity.x = speed;
//                animationTime = 0;
//                break;
//        }
//
//        System.out.println("Return KeyDown");
//        keyDown = true;
        return false;
    }

    public static void resetVelocity() {
                velocity.x = 0;
                velocity.y = 0;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public static TextureAtlas setPlayerAtlas(String textureAtlas) {
        return playerAtlas = new TextureAtlas(textureAtlas);
    }





























    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
