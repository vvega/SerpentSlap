package com.designethereal.objects;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.designethereal.resources.ResourceManager;

public class Dragon {

	Head head;
	ArrayList<BodySegment> body;
	World world;
	int length;
	Vector2 pos;
	private RevoluteJointDef jointDef;
	private boolean initialized = false;
	
	public Dragon(World world, Vector2 pos) {
		this.world = world;
		this.pos = pos;
		this.length = 10;
		this.body = new ArrayList<BodySegment>();
		
		constructBody();
	}
	
	private void constructBody() {
		
		//init head and body
		head = new Head(world, pos);
		float curX = head.getBody().getPosition().x;
		float curY = head.getBody().getPosition().y;
		
		//build body
		body.add(head);
		curX -= head.width/1.7;
		for(int i = 1; i <= length; i++) {
			body.add(new BodySegment(world, new Vector2(curX, curY)));
			body.get(i).setPrevious(body.get(i-1));
			body.get(i-1).setNext(body.get(i));
			curX -= body.get(i).width;
		}
		curX -= ResourceManager.tailXOffset/32;
		body.add(new Tail(world, new Vector2(curX, curY)));
		body.get(body.size() - 2).setNext(body.get(body.size() - 1));
		
		//initialize joints
		initJointDef();
	}
	
	private void initJointDef() {
		jointDef = new RevoluteJointDef();
		
		jointDef.enableMotor = false;
		jointDef.enableLimit = false;
		jointDef.maxMotorTorque = 0;
		jointDef.referenceAngle = MathUtils.degreesToRadians*0;
		jointDef.lowerAngle = MathUtils.degreesToRadians*0;
		jointDef.upperAngle = MathUtils.degreesToRadians*360;
		jointDef.motorSpeed = 0;
		jointDef.collideConnected = false;
		
		for(BodySegment p : body) {
			if(p.hasNext()) {
				
				jointDef.bodyA = p.getBody();
				jointDef.bodyB = p.getNext().getBody();
				
				if(p instanceof Head) {
					jointDef.localAnchorA.set(p.width - p.width/1.5f,p.height/2);
				} else {
					jointDef.localAnchorA.set(p.width - p.width/3,p.height/2);
				}

				jointDef.localAnchorB.set(0,p.getNext().height/2);				
				world.createJoint(jointDef);
			}
		}
	}
	
	public void setPosition(Vector2 pos) {
		head.setPosition(pos);
	}
	
	public Head getHead() {
		return head;
	}
	
	public ArrayList<BodySegment> getBodies() {
		return this.body;
	}
	
	public float calculateAngleToHead(float fromX, float fromY) {
		return fromY;
		
	}
	
	public void move(Vector3 toPos, Body ground) {
		//need to apply some type of angle altering velocity
		
	}
	
	public void setInitialized(boolean init) {
		this.initialized = init;
	}
	
	public boolean isInitialized() {
		return this.initialized;
	}

}
