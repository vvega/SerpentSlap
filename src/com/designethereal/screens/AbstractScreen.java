package com.designethereal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.designethereal.dragonslap.Dragonslap;
import com.designethereal.resources.ResourceManager;

//Base class for all screens within the game

public abstract class AbstractScreen implements Screen {
	
	protected final Dragonslap game;
	protected final Stage stage;
	
	private BitmapFont font;
	private SpriteBatch batch;
	private Skin skin;
	
	public AbstractScreen (Dragonslap game) {
		this.game = game;
		this.stage = new Stage();
	}
	
	protected String getName() {
		return getClass().getSimpleName();
	}
	
	public BitmapFont getFont()
	{
		if(font == null) {
			font = new BitmapFont();
		}
		return font;
	}
	
	public SpriteBatch getBatch() {
		if(batch == null){
			batch = new SpriteBatch();
		}
		return batch;
	}
	

	protected Skin getSkin() {
		return ResourceManager.uiSkin;
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void resize(int width, int height) {
		// resize and clear the stage
        stage.setViewport( width, height, true );
        stage.clear();
	}
	
	@Override
	public void render(float delta) {
       
		//stage.act(delta);
		stage.act(Gdx.graphics.getDeltaTime());
		
        // clear the screen with the given RGB color (black)
        //Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        stage.draw();
	}
	
	@Override
	public void hide() {
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void dispose() {
		stage.dispose();
        if( font != null ) font.dispose();
        if( batch != null ) batch.dispose();
        if( skin != null ) skin.dispose();
	}
}
