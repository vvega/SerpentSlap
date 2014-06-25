package com.designethereal.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.designethereal.sprites.TailSprite;

public class Tail extends BodySegment {

	public Tail(World world, Vector2 position) {
		super(world, position);	
	}
	
	@Override
	protected void Setup() {
		this.sprite = new TailSprite();
		this.fixtureShape = new PolygonShape();
	}


}
