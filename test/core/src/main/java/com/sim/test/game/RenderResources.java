package com.sim.test.game;


import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;
import static com.sim.test.game.Constants.TileLayers.BUILDINGS;
import static com.sim.test.game.Constants.TileLayers.CONFECTIONERY;
import static com.sim.test.game.Constants.TileLayers.TREES;
import static com.sim.test.game.Constants.TileLayers.WATER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.sim.test.game.Constants.Assets;
import com.sim.test.game.Helpers.DayCycle;
import com.sim.test.game.Helpers.UI;
import com.sim.test.game.Maps.Redtown;
import com.sim.test.game.Sprites.newPlayer;

class RenderResources implements Screen{


    private OrthogonalTiledMapRenderer renderer;
    private static OrthographicCamera camera;
    private Redtown redtown;
    private UI ui;
    private static Vector3 touchPoint = new Vector3();
    TiledMap  map;
    int i = 3;
    TiledMapTileLayer tmp;


    private newPlayer player;
    private DayCycle dayCycle;

    @Override
    public void show() {
        TextureAtlas playerAtlas = newPlayer.setPlayerAtlas(Assets.PLAYER.localName());

        redtown = new Redtown();
        camera = new OrthographicCamera();
        /*TiledMap*/  map = redtown.getMap();

        dayCycle = new DayCycle();

        Animation still = new Animation(1 / 9f, playerAtlas.findRegions("still"));
        still.setPlayMode(LOOP);

        player = new newPlayer(still,  map);
//        player.setPosition(245, 500);
//        player.setPosition(175, 140);
        player.setPosition(895, 345);

        renderer = new OrthogonalTiledMapRenderer(map);
        
        ui = new UI(renderer.getBatch());
        ui.setColor(RED);
        ui.getData().setScale(0.6f, 0.6f);

        Gdx.input.setInputProcessor(player);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        camera.update();

        renderer.setView(camera);

        //check isAndroid to check for touch points
        if(Main.isAndroidApp) {
            getTouchMove();
        }

        if(!DayCycle.isPaused) {
            renderer = dayCycle.run(renderer, player);
        }

        player = (newPlayer) dayCycle.getTintedSprite();

        //render the map layers
        redtown.render(renderer);
        switch (i) {
            case 0 :
                tmp = (TiledMapTileLayer)map.getLayers().get(BUILDINGS.localName());
            case 1 :
                tmp = (TiledMapTileLayer)map.getLayers().get(WATER.localName());
            case 2 :
                tmp = (TiledMapTileLayer)map.getLayers().get(CONFECTIONERY.localName());
            case 3 :
                tmp = (TiledMapTileLayer)map.getLayers().get(TREES.localName());
        }
        
        renderer.getBatch().begin();
        player.draw(renderer.getBatch());

        /**
         * Needed for when resize screen ui is still stuck to camera position
         */
//        ui.uiAdd("Time", dayCycle.getMinute(), camera.position.x - 270, camera.position.y + 140);
        ui.uiAddText("Time",dayCycle.getHour() + " : " + dayCycle.getMinute(), camera.position.x + 75, camera.position.y + 95);
        ui.uiAddText("Position", player.getX() + ", " + player.getY(), camera.position.x -125, camera.position.y + 95);
        ui.uiAddText("KeyPresses", player.keyPresses.size(), camera.position.x , camera.position.y + 95);
        
        
        renderer.getBatch().end();

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 2.5f;
        camera.viewportHeight = height / 2.5f;
    }

    /**
     *
     * get the touchpoint for the camera not screen as it is the whole loaded screen which is big,
     * unless set a viewport for the constant width of the screen depending on device used
     *
     * or fix screen sizes and views for desktop and samsung, also allow android portrait and landscape
     *
     */
    private Vector3 getTouchMove() {
        if(Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        }

        if(touchPoint.x > 500) {
            player.keyDown(Input.Keys.D);
         //   player.keyUp(Input.Keys.D);
        } else if(touchPoint.x < 500) {
            player.keyDown(Input.Keys.A);
            //player.keyUp(Input.Keys.A);
        } else if(touchPoint.y > 300) {
            player.keyDown(Input.Keys.W);
          //  player.keyUp(Input.Keys.W);
        } else if(touchPoint.y < 300) {
            player.keyDown(Input.Keys.S);
           // player.keyUp(Input.Keys.S);
        }

            return touchPoint;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}


