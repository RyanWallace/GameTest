package com.sim.test.game.Helpers;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.joda.time.DateTime;


/**
 * Fix to blend between rgba colours per certain number of minutes for more smoother light cycle
 */

public class DayCycle {

    private DateTime time = new DateTime();
    private int  minute, hour;
    private Sprite sprite;
    public static boolean isPaused = false;


    public DayCycle() {
        minute = 0;
        hour = 0;
    }

    public OrthogonalTiledMapRenderer run(OrthogonalTiledMapRenderer renderer, Sprite player) {

        sprite = player;

        if((new DateTime().getMillis() - time.getMillis()) >= 100) {
            minute++;
            if(minute==60) {
                minute =0;
                hour++;
            }
            if(hour==24) {
                hour =0;
            }
            time = new DateTime();
        }

        //DUSK 9PM | 9AM
        if(hour == 9 || hour == 21) {
            renderer.getBatch().setColor(Color.CYAN);
            player.setColor(Color.CYAN);
        }
        //SUNSET 6PM | 6AM
        if(hour == 6 || hour == 18 ){
            renderer.getBatch().setColor(Color.ORANGE);
            player.setColor(Color.ORANGE);
        }
        //12 PM
        if(hour == 12){
            renderer.getBatch().setColor(Color.WHITE);
            player.setColor(Color.WHITE);
        }

        //12 AM
        if(hour == 0){
            renderer.getBatch().setColor(Color.GREEN);
            player.setColor(Color.GREEN);
        }
        //3AM
        if(hour == 3) {
            renderer.getBatch().setColor(Color.PURPLE);
            player.setColor(Color.PURPLE);
        }
        //3 PM
        if(hour == 15){
            renderer.getBatch().setColor(Color.PINK);
            player.setColor(Color.PINK);
        }
        //TODO - remove this
        renderer.getBatch().setColor(Color.WHITE);
        player.setColor(Color.WHITE);
        return renderer;
    }
    public Sprite getTintedSprite() {
        return sprite;
    }

    public java.lang.String getMinute() {
        if(minute < 10) {
            return "0" + minute;
        }else {
            return "" + minute;
        }
    }

    public java.lang.String getHour() {
        if(hour < 10) {
            return "0" + hour;
        }else {
            return "" + hour;
        }
    }

    /**
     *
     * Leave out until Saving/loading from txt file getting location, time, inventory
     ***/
//    public void setMinute(int minute) {
//        this.minute = minute;
//    }
//
//    public void setHour(int hour) {
//        this.hour = hour;
//    }
}
