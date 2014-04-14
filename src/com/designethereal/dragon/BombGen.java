package com.designethereal.dragon;

import com.badlogic.gdx.physics.box2d.World;

public class BombGen {

	public static BombGen instance = null;
	private World world;
	
	public BombGen(World world) {
		instance = new BombGen(world);
	}
	
	public static BombGen getInstance() {
		if(instance != null) {
			return instance;
		}
		return null;
	}
	
	public static void generateBombs() {
		
	}
}
