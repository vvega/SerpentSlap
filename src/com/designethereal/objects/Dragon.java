package com.designethereal.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.designethereal.resources.ResourceManager;

public class Dragon extends Actor {

	private Head head;
	private ArrayList<BodySection> body;
	private int length;
	private int sectionWidth;
	private int coordX;
	private final float moveSpeed = .5f;
	private final float rotateSpeed = .01f;
	
	public Dragon(int length) {
		
		this.head = new Head(380, 200, this);
		this.body = new ArrayList<BodySection>();
		this.length = length;
		init();
	}

	private void init() {
		sectionWidth = ResourceManager.bodyTexture.getRegionWidth();
		coordX = (int) (head.getX() - (head.getTexture().getRegionWidth()/2)); 

		for(int i = 0; i < length; i++) {
			body.add(new BodySection(coordX, head.getY(), false));
			coordX -= sectionWidth;
		}
		
		//tack on tail
		body.add(new BodySection(coordX, head.getY(), true));
		
		stringTogether();
	}
	
	public void move(int x, int y) {
		y = (int) (this.getStage().getHeight() - y);
		int i = 1;
		//head.addAction(Actions.parallel(Actions.moveTo(x, y, moveSpeed), Actions.rotateTo(calculateRotationAngle(head, x, y), rotateSpeed)));
		head.move(x, y, moveSpeed, rotateSpeed);
	//	body.get(0).move(x - head.getOriginX(), y - head.getOriginY(), speed);
		for(BodySection part : this.body) {
			if(part.isTail()) {
				part.move(part.getPrevious().getX(), part.getPrevious().getY(), moveSpeed, rotateSpeed);
			} else if(part.getPrevious() == null) {
				part.move(head.getX() - head.getWidth(), head.getY(), moveSpeed, rotateSpeed);
			} else {
				part.move(part.getPrevious().getX(), part.getPrevious().getY(), moveSpeed, rotateSpeed);
			}
			i++;
		}
	}
	
	public void stringTogether() {

		for(int i = 0; i < body.size(); i++) {
			System.out.println(i);
			//first body section will be guided by the head
			if(i == 0) {
				body.get(i).setPrevious(this.head);
			} else {
				body.get(i).setPrevious(body.get(i-1));
			}
		}
	}
	
	public Head getHead(){
		return this.head;
	}
	
	/*public void grow() {
		int insertPos = (int) (body.size()/2);
		float insertXPos = body.get(insertPos).getX() + body.get(insertPos).getWidth();
		float insertYPos = (body.get(insertPos).getY() + body.get(insertPos + 1).getY())/2;
		body.add(insertPos, new BodySection(insertXPos, insertYPos, false));
	}*/
	
	protected static float calculateRotationAngle(Actor actor, float toX, float toY) {
			//return angle calculation plus angle adjustment to align with tip of the nose
			//note that deltaY is reversed to properly calculate Y coordinates on the stage
			return (float) (Math.atan2(toX - actor.getX(), actor.getY() - toY) * 180 / Math.PI) - 90;
	
	}
	
	@Override
	public void draw(SpriteBatch batch, float delta) {
		head.draw(batch, delta);
		for(BodySection part : body) {
			part.draw(batch, delta);
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		this.head.act(delta);
		for(BodySection part : this.body) {
			part.act(delta);
		}
	}
	
}

