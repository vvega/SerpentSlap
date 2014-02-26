package com.designethereal.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Dragon extends Actor {

	private Head head;
	private ArrayList<BodySection> body;
	private final int BODY_SPACING = 5;
	
	public Dragon(int length) {
		
		this.head = new Head(200, 200);
		this.body = new ArrayList<BodySection>();
		
		for(int i = 0; i < length; i++) {
			body.add(new BodySection(i*BODY_SPACING, 100, false));
			if(i == length - 1) {
				body.add(new BodySection(i*BODY_SPACING, 100, true));
			}
		}
	}
	
	public void grow() {
		
		int insertPos = (int) (body.size()/2);
		float insertXPos = body.get(insertPos).getX() + body.get(insertPos).getWidth();
		float insertYPos = (body.get(insertPos).getY() + body.get(insertPos + 1).getY())/2;
		body.add(insertPos, new BodySection(insertXPos, insertYPos, false));
	}
	
	@Override
	public void draw(SpriteBatch batch, float delta) {
		head.draw(batch, delta);
		for(int i = 0; i < body.size(); i++) {
			body.get(i).draw(batch, delta);
		}
	}
}
