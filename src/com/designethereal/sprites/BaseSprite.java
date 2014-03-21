package com.designethereal.sprites;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class BaseSprite extends Sprite {

	public BaseSprite() {
		super();
	}
	
	public BaseSprite(TextureRegion t) {
		super(t);
	}
	
	public float getHeightInMeters() {
		return this.getHeight()/32;
	}
	
	public float getWidthInMeters() {
		return this.getWidth()/32;
	}

}
