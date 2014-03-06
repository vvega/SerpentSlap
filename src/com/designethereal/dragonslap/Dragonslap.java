package com.designethereal.dragonslap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.designethereal.resources.ResourceManager;
import com.designethereal.screens.GameScreen;
import com.designethereal.screens.MenuScreen;
import com.designethereal.screens.SplashScreen;

public class Dragonslap extends com.badlogic.gdx.Game {
	
	 // constant useful for logging
    public static final String LOG = Dragonslap.class.getSimpleName();

    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;
	
	public SplashScreen getSplashScreen() {
		return new SplashScreen(this);
	}

	public GameScreen getGameScreen() {	
		return new GameScreen(this);
	}
	
	public MenuScreen getMenuScreen() {
		return new MenuScreen(this);
	}

	@Override
	public void create() {		
		Gdx.app.log( Dragonslap.LOG, "Creating game on " + Gdx.app.getType() );
        fpsLogger = new FPSLogger();
        ResourceManager.init();
	}

	@Override
	public void dispose() {
		 super.dispose();
	     Gdx.app.log( Dragonslap.LOG, "Disposing game" );
	}

	@Override
	public void render() {		
		super.render();

        // output the current FPS
       // fpsLogger.log();
	}

	@Override
	public void resize(int width, int height) {
		super.resize( width, height );
        Gdx.app.log( Dragonslap.LOG, "Resizing game to: " + width + " x " + height );

        // show the splash screen when the game is resized for the first time;
        // this approach avoids calling the screen's resize method repeatedly
        if( getScreen() == null ) {
            setScreen( getGameScreen() );
        }
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
		 Gdx.app.log( Dragonslap.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
	}
}
