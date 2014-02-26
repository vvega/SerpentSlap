package com.designethereal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.designethereal.dragonslap.Dragonslap;
import com.designethereal.objects.BodySection;
import com.designethereal.objects.Dragon;
import com.designethereal.resources.ResourceManager;

public class GameScreen extends AbstractScreen {
	Dragon dragon;

	public GameScreen(Dragonslap game) {
		super(game);
		dragon = new Dragon(5);
		//this.stage.addActor(dragon);

	}

	@Override
	public void show() {
		
		super.show();
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(float delta) {
       
		super.render(delta);
		this.stage.addActor(dragon);
		this.getBatch().begin();
		stage.draw();
		this.getBatch().end();
		
	}

}
