package com.designethereal.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.designethereal.resources.ResourceManager;

public class Head extends Actor {

	  private TextureRegion region;

      public Head (float x, float y) {
  		
    	this.region = ResourceManager.headTexture;
  		setX(x);
		setY(y);
      }

      @Override
      public void draw (SpriteBatch batch, float parentAlpha) {
    	  batch.draw(region, getX(), getY());
   }
	
}
