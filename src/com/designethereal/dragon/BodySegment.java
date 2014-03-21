package com.designethereal.dragon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.designethereal.sprites.BodySprite;

public class BodySegment extends BodyPart{
	
	private Limb limb = null;

	public BodySegment(World world, Vector2 position) {
		super(world, position);	
		this.sprite = new BodySprite();
		this.fixtureShape = new PolygonShape();
		super.init();
	}
}
