package com.designethereal.objects;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.designethereal.resources.ResourceManager;

public class Head extends Actor implements InputProcessor {

	  private TextureRegion region;
	  private Dragon body;

      public Head (float x, float y, Dragon dragon) {
  		
    	this.region = ResourceManager.headTexture;
    	this.body = dragon;

		this.setWidth(region.getRegionWidth());
		this.setHeight(region.getRegionHeight());
	 	this.setOrigin(this.getWidth()/2, this.getHeight()/2);
		this.setScale(1);
		this.setRotation(0);
  		this.setX(x);
		this.setY(y);
      }

      @Override
      public void draw (SpriteBatch batch, float parentAlpha) {
    	  batch.draw(region, this.getX(), this.getY() - ResourceManager.headYOffset, this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
      }
      
     /* @Override
      public void setRotation(float degrees){
    	  this.getStage().getSpriteBatch().draw(region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), 1, 1, degrees);
    	  super.setRotation(degrees);
      }*/
      
      public TextureRegion getTexture() {
    	  return region;
      }
      
      public void setTexture(TextureRegion t) {
    	  this.region = t;
      }
      
	  @Override
	  public boolean touchDragged(int screenX, int screenY, int pointer) {
	  		// TODO Auto-generated method stub
	  	body.move(screenX, screenY);
		//  System.out.println("moving!");
	  		return false;
	  }
	  
	  public void move(float x, float y, float m_speed, float r_speed) {
		  this.addAction(Actions.parallel(Actions.moveTo(x, y, m_speed), Actions.rotateTo(Dragon.calculateRotationAngle(this, x, y), r_speed)));
	  }

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
