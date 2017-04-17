package com.sim.test.game.Sprites;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;

import java.util.ArrayList;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.sim.test.game.Helpers.CollisionDetector;
import com.sim.test.game.Maps.Redtown;

public class newPlayer extends Sprite implements InputProcessor{

	private static final char KEY_UP_BREAK_CHAR = ' ';
	private static final float ANIMATION_DELTA = 0.005f;
	private static final float PLAYER_POSITION_DELTA = 0.01f;
	private static final float PLAYER_SPEED = 100f;

	private static Vector2 velocity = new Vector2();
    private static TextureAtlas playerAtlas;
    private float animationTime = 0;
    private static Animation still, up, down, left ,right;
    private CollisionDetector collisionDetector;
    private char keyPressed;
    public static ArrayList<Character> keyPresses = new ArrayList<>();


    public newPlayer(Animation initialStanceAnimation, TiledMap m) {
        super(initialStanceAnimation.getKeyFrame(0));
        still = initialStanceAnimation;
        renderAnimation();
        collisionDetector = new CollisionDetector(m, this);

    }

    public static TextureAtlas setPlayerAtlas(String textureAtlas) {
        return playerAtlas = new TextureAtlas(textureAtlas);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("Key Held");
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyPresses.add(KEY_UP_BREAK_CHAR);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {

        boolean keyTyped = false;
        System.out.println("Key Typed: " + character);

       if (KEY_UP_BREAK_CHAR != character) {
    	   keyPressed = character;
       } else {
    	   return false;
       }
        
        switch (character) {

            case 'w' :

                if(collisionDetector.detectCollision(character, this)) {
                    return true;
                }

                setPlayerVelocity(0,PLAYER_SPEED);

                System.out.println("W Typed");
                keyTyped = true;
                break;

            case 'a' :

                if(collisionDetector.detectCollision(character, this)) {
                    return true;
                }

                setPlayerVelocity(-PLAYER_SPEED,0);

                System.out.println("A Typed");
                keyTyped = true;
                break;

            case 's' :

                if(collisionDetector.detectCollision(character, this)) {
                    return true;
                }
                setPlayerVelocity(0,-PLAYER_SPEED);

                System.out.println("S Typed");
                keyTyped = true;
                break;

            case 'd' :

                if(collisionDetector.detectCollision(character, this)) {
                    return true;
                }
                setPlayerVelocity(PLAYER_SPEED,0);

                System.out.println("D Typed");
                keyTyped = true;
                break;
        }
        String keyPressesToString ="";
        String keyPressesToString2 ="";
        for (int i = 0; i < keyPresses.size(); i++) {

        	keyPressesToString2+="| "+ i + " ";			        		
        	if (i>=10) {
        		keyPressesToString+="| "+ keyPresses.get(i) + "  ";			
        	} else {
        		keyPressesToString+="| "+ keyPresses.get(i) + " ";			
        	}
		}
	        
        keyPressesToString+="|";
        keyPressesToString2+="|";
        System.out.println(keyPressesToString2);
        System.out.println(keyPressesToString);
        return keyTyped;
    }


    @Override
    public void draw(Batch batch) {

    	updateAnimation();
    	updatePlayerPosition();
        super.draw(batch);

    }

	private void updateAnimation() {
		animationTime += ANIMATION_DELTA;
        
        setRegion(velocity.x < 0 ? left.getKeyFrame(animationTime) : velocity.x > 0 ? right.getKeyFrame(animationTime)
                : velocity.y<0 ? down.getKeyFrame(animationTime) : velocity.y > 0 ? up.getKeyFrame(animationTime)
                : still.getKeyFrame(animationTime));
	}

    public void resetVelocity() {
        velocity.x = 0;
        velocity.y = 0;
        updateAnimation();
    }

    private void updatePlayerPosition() {
    	if (!keyPresses.isEmpty()) {
    		if(KEY_UP_BREAK_CHAR != keyPresses.get(0)) {
    			
    			char currentKeyPress = keyPresses.get(0);
    			
    			switch (currentKeyPress) {
    			case 'w' :

                    float incrementYUp = PLAYER_SPEED * PLAYER_POSITION_DELTA;
                    if (incrementYUp != 0.0f) {
                    	System.err.println("step incrementY: " + incrementYUp );        	
                    }
                    
                    float newYPositionUp = getY() + incrementYUp;

                    if (newYPositionUp > 14 && newYPositionUp < Redtown.getMapBounds().y) {
                        setY(newYPositionUp);
                    }
    				
                    break;

                case 'a' :
                	
            		float incrementXLeft = -PLAYER_SPEED * PLAYER_POSITION_DELTA;
                    if (incrementXLeft != 0.0f) {
                    	System.err.println("step incrementX: " + incrementXLeft );        	
                    }

                    float newXPositionLeft = getX() + incrementXLeft;
                    if (newXPositionLeft > 14 && newXPositionLeft < Redtown.getMapBounds().x) {
                        setX(newXPositionLeft);
                    }
                    break;

                case 's' :
                    float incrementYDown = -PLAYER_SPEED * PLAYER_POSITION_DELTA;
                    if (incrementYDown != 0.0f) {
                    	System.err.println("step incrementY: " + incrementYDown );        	
                    }
                    
                    float newYDownPosition = getY() + incrementYDown;

                    if (newYDownPosition > 14 && newYDownPosition < Redtown.getMapBounds().y) {
                        setY(newYDownPosition);
                    }
                	
                    break;

                case 'd' :
                	
            		float incrementXRight = PLAYER_SPEED * PLAYER_POSITION_DELTA;
                    if (incrementXRight != 0.0f) {
                    	System.err.println("step incrementX: " + incrementXRight );        	
                    }

                    float newXPositionRight = getX() + incrementXRight;
                    if (newXPositionRight > 14 && newXPositionRight < Redtown.getMapBounds().x) {
                        setX(newXPositionRight);
                    }
                	
                    break;
            }

    		} else {
    			resetVelocity();
    		}

    		keyPresses.remove(keyPresses.get(0));
    	}
    }

    private void setPlayerVelocity(float x, float y) {
    	if(keyPresses.size() < 16) {
    		for(int i=0; i<16; i++) {
    			keyPresses.add(keyPressed);
    		}    		
    	}
    	
    	System.out.println("Velocity is set");
    	
        velocity.x = x;
        velocity.y = y;
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
}
