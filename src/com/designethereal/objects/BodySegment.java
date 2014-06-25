package com.designethereal.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.designethereal.sprites.BodySprite;

public class BodySegment extends GameObject{
	
	private Limb limb = null;
	private BodySegment previous = null;
	private BodySegment next = null;

	public BodySegment(World world, Vector2 position) {
		super(world, position);
		Setup();
		super.init();
	}
	
	protected void Setup() {
		this.sprite = new BodySprite();
		this.fixtureShape = new CircleShape();
		this.fixtureShape.setRadius(this.sprite.getWidthInMeters()/3);
	}
	
	protected void setPrevious(BodySegment b) {
		this.previous = b;
	}
	
	protected void setNext(BodySegment b) {
		this.next = b;
	}
	
	public BodySegment getPrevious() {
		return previous;
	}
	
	public BodySegment getNext() {
		return next;
	}
	
	public boolean hasNext() {
		return (next != null) ? true : false;
	}
	
	public boolean hasPrevious() {
		return (previous != null) ? true : false;
	}
}
