package com.designethereal.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.designethereal.dragonslap.Dragonslap;

public class MenuScreen extends AbstractScreen {
		
	private static final float BUTTON_WIDTH = 800f;
	private static final float BUTTON_HEIGHT = 100f;
	//private static final float BUTTON_SPACING = 10f;
	
	public MenuScreen(Dragonslap game) {
		super(game);
		// TODO Auto-generated constructor stub
	}
	
	 @Override
	 public void resize(int width, int height)
	 {
		 	super.resize(width, height);
	        final float buttonX = ( width - BUTTON_WIDTH ) / 2;
	        float currentY = 280f;
	 
	        // label "welcome"
	        Label welcomeLabel = new Label("Welcome to notgonnatellyouFGT for Android!", getSkin());
	        welcomeLabel.setX((width - welcomeLabel.getWidth()) / 2 );
	        welcomeLabel.setY((width - welcomeLabel.getWidth()) / 2 );
	        stage.addActor(welcomeLabel);
	 
	        // button "start game"
	        TextButton startGameButton = new TextButton("Start game", getSkin());
	        startGameButton.setX(buttonX);
	        startGameButton.setY(currentY);
	        startGameButton.setWidth(BUTTON_WIDTH);
	        startGameButton.setHeight(BUTTON_HEIGHT);
	        stage.addActor(startGameButton);
	        startGameButton.addListener(new ClickListener() {
	            public void clicked(InputEvent event, float x, float y) {
	                game.setScreen(game.getGameScreen());
	            }
	        });
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
