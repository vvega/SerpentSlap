package com.designethereal.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.designethereal.resources.ResourceManager;

public class BodySection extends Actor {
	
	private TextureRegion region;
	private boolean tail;
	private BodySection next;
	private Actor previous;
	private Action moveNext;
	
	public BodySection(float x, float y, boolean tail){
		
		this.tail = tail;

		//determing whether to render a standard body texture or tail texture
		if(this.tail) {
			this.region = ResourceManager.tailTexture;
		} else {
			this.region = ResourceManager.bodyTexture;
		}

	   	this.setOrigin(this.region.getRegionWidth()/2, this.region.getRegionHeight()/2);
		this.setWidth(region.getRegionWidth());
		this.setHeight(region.getRegionHeight());
	 	this.setOrigin(this.getWidth()/2, this.getHeight()/2);
		this.setScale(1);
		this.setRotation(0);
  		this.setX(x);
		this.setY(y);
		
/*		moveNext = new Action() {
			@Override
			public boolean act(float delta) {
				if(next != null) {
					next.move(this.getActor().getX(), this.getActor().getY(), .1f);
				}
				return false;
			}			
		};*/

	}

	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		if(this.tail) {
		  batch.draw(region, this.getX() - ResourceManager.tailXOffset, this.getY() - ResourceManager.tailYOffset, this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
		} else {
		  batch.draw(region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
		}
		
    }
	
	public void move(float x, float y, float m_speed, float r_speed) {
		this.addAction(Actions.parallel(Actions.moveTo(x, y, m_speed), Actions.rotateTo(Dragon.calculateRotationAngle(this, x, y), r_speed)));
	}
	
	protected void setNext(BodySection next) {
		this.next = next;
	}
	
	protected void setPrevious(Actor previous) {
		this.previous = previous;
	}
	
	protected Actor getPrevious() {
		return this.previous;
	}
	
	protected BodySection getNext() {
		return this.next;
	}
	
	protected boolean isTail() {
		return this.tail;
	}
}
