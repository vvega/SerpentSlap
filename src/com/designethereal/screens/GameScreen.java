package com.designethereal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.designethereal.dragonslap.Dragonslap;
import com.designethereal.objects.Dragon;

public class GameScreen extends AbstractScreen {
	Dragon dragon;

	public GameScreen(Dragonslap game) {
		super(game);
		dragon = new Dragon(3);	
	}

	@Override
	public void show() {
		super.show();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(dragon.getHead());
		Gdx.input.setInputProcessor(multiplexer);	
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resize(int width, int height) {
		this.stage.addActor(dragon);
	}
	
	@Override
	public void render(float delta) {
		
		dragon.act(delta);
		super.render(delta);
	}

}
