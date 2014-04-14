package com.designethereal.dragon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.designethereal.sprites.BodySprite;

public class BodySegment extends GameObject{
	
	private Limb limb = null;
	private BodySegment previous = null;
	private BodySegment next = null;
	private RopeJoint connectingJoint = null;


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
	
	protected void setJoint(RopeJoint joint) {
		this.connectingJoint = joint;
	}
	
	public RopeJoint getJoint() {
		return this.connectingJoint;
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
	
	public float getRotationAngle() {
		if(this.previous != null) {
			return 0;
		}
		//return angle calculation plus angle adjustment to align with tip of the nose
		//note that deltaY is reversed to properly calculate Y coordinates on the stage
		return (float) (Math.atan2(previous.getBody().getPosition().x - this.getBody().getPosition().x, 
				previous.getBody().getPosition().y - this.getBody().getPosition().y) * 180 / Math.PI) - 90;
	}
}
