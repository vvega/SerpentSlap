package com.designethereal.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.designethereal.resources.ResourceManager;

public class BodySection extends Actor {
	
	private TextureRegion region;
	private boolean isTail;
	
	public BodySection(float f, float g, boolean tail){
		
		this.isTail = tail;

		//determing whether to render a standard body texture or tail texture
		if(this.isTail) {
			this.region = ResourceManager.tailTexture;
		} else {
			this.region = ResourceManager.bodyTexture;
		}
		
		setX(f);
		setY(g);

	}

	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		batch.draw(region, getX(), getY());
    }

}
