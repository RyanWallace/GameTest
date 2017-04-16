package com.sim.test.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.sim.test.game.Helpers.DayCycle;

public class Main extends Game{

    public static boolean isAndroidApp = false;
    
    @Override
    public void create () {
       setScreen(new RenderResources());
    }

    @Override
    public void render () {
        super.render();

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            try {
                this.setScreen(getScreen().getClass().newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(DayCycle.isPaused) {
                DayCycle.isPaused = false;
            }else {
                DayCycle.isPaused = true;
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    /** TODO
     *
     *
     * phone controls
     *
     * screens share resources(map, renderer, playerAtlas Camera); [[implement singleton interface for all to use,
     * maybe have array to keep record of screens to load]]
     *
     * fix player location
     * player rails to walk certain cells, or place in specific pixels then only move so far with
     * one button press which is ultimately moved the length of the player {20 Pixels}
     *
     *
     * dynamic light from street lights
     *
     * night/day cycle and timer
     *
     * 1.05f, 100.05f, 60, 90 grey rain / storm
     * 100.05f, 150.05f, 60, 90 dusk
     * 1.05f, 150.05f, 60, 90 night
     *
     *
     *
     * OPTIONAL
     * implement some patterns
     */



    /** DONE SO FAR
     *
     *
     *  Got player and assets loaded
     *  got classes segregated
     *
     *   finish method to initialise shared resources between screens
     *
     *  instantiate all camera an renderer resources in renderResources class and pass into each screen,
     *  allow them to edit and add to these resources
     *  then finally 'Start()' them after both screens have been loaded
     *
     */
}
