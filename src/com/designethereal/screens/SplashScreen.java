package com.designethereal.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.designethereal.dragonslap.Dragonslap;
import com.designethereal.resources.ResourceManager;

public class SplashScreen extends AbstractScreen {
	
	public SplashScreen(Dragonslap game) {
		super(game);
	}

	 @Override
	    public void show()
	    {
	        super.show();
	    }

	    @Override
	    public void resize(int width, int height)
	    {
	        super.resize( width, height );

	        Action completeAction = new Action(){
	        	public boolean act(float delta) {
	        		 // when the image is faded out, move on to the next screen
	                game.setScreen(game.getMenuScreen());
	        		return true;
	        	}
	        };
	        // in the image atlas, our splash image begins at (0,0) of the
	        // upper-left corner and has a dimension of 512x301
	        TextureRegion splashRegion = new TextureRegion(ResourceManager.splashTexture, 0, 0, 1024, 512);

	        // here we create the splash image actor and set its size
	        Image splashImage = new Image(splashRegion);
	        splashImage.setWidth(width);
	        splashImage.setHeight(height);

	        // this is needed for the fade-in effect to work correctly; we're just
	        // making the image completely transparent
	        splashImage.getColor().a = 0f;
	        
	        // configure the fade-in/out effect on the splash image
	        SequenceAction actions = new SequenceAction(Actions.fadeIn(0.75f), Actions.fadeOut(.75f), completeAction);	
	        
	        splashImage.addAction(actions);

	        // and finally we add the actor to the stage
	        stage.addActor( splashImage );
	    }

	    @Override
	    public void dispose()
	    {
	        super.dispose();
	        ResourceManager.splashTexture.dispose();
	    }

		@Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}

}
