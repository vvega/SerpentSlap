package com.designethereal.dragon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.designethereal.sprites.HeadSprite;

public class Head extends BodySegment {

	public Head(World world, Vector2 position) {
		super(world, position);	
	}
	
	@Override
	protected void Setup() {
		this.sprite = new HeadSprite();
		this.fixtureShape = new CircleShape();
		this.fixtureShape.setRadius(this.sprite.getWidthInMeters()/3);
	}
}
