package com.designethereal.objects;

import java.util.ArrayList;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BombGen extends Thread {
	
	public ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	public boolean isRunning = false;
	public static TweenManager manager = new TweenManager();
	
	private World world = null;
	
	public BombGen(World w) {
		this.world = w;
	}
	
	public static void generateBombs() {
		
	}
	
	public ArrayList<Bomb> getBombs() {
		return bombs;
	}
	@Override
	public void start() {
		super.start();
		isRunning = true;
	}
	
	@Override 
	public void run() {
		while(isRunning) {
			try {
				if(world != null) {
					bombs.add(new Bomb(3f, new Vector2(randomFloat(40),randomFloat(40)), world));
					bombs.add(new Bomb(5f, new Vector2(randomFloat(40),randomFloat(40)), world));
					bombs.add(new Bomb(3f, new Vector2(randomFloat(-40),randomFloat(-40)), world));
					bombs.add(new Bomb(5f, new Vector2(randomFloat(-40),randomFloat(-40)), world));
				}
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	private float randomFloat(int seed){
		return (float) Math.random()*seed;
	}
}
