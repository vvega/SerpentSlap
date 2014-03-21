package com.designethereal.dragon;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.designethereal.resources.ResourceManager;

public class Dragon {

	Head head;
	ArrayList<BodyPart> body;
	World world;
	int length;
	Vector2 pos;
	private RopeJointDef jointDef;
	
	public Dragon(World world, Vector2 pos) {
		this.world = world;
		this.pos = pos;
		this.length = 5;
		this.body = new ArrayList<BodyPart>();
		
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
		jointDef = new RopeJointDef();
		
		for(BodyPart p : body) {
			if(p.hasNext()) {
				jointDef.bodyA = p.getBody();
				jointDef.bodyB = p.getNext().getBody();
				jointDef.maxLength = 
						(p.getNext().width > p.width) ? p.width : p.getNext().width;
				jointDef.collideConnected = false;
				jointDef.localAnchorA.set(p.width/2,p.height/2);
				jointDef.localAnchorB.set(p.getNext().width/2,p.getNext().height/2);
				
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
	
	public ArrayList<BodyPart> getBodies() {
		return this.body;
	}
	
	public float calculateAngleToHead(float fromX, float fromY) {
		return fromY;
		
	}
	
	public void move(Vector3 toPos, Body ground) {
		
	}

}
