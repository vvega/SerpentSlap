package com.designethereal.dragon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Bomb extends GameObject {
	
	float time;
	
	public Bomb(float time, Vector2 start, World world) {
		super(world, start);
		this.time = time;
	}
}
