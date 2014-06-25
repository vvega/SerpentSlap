package com.designethereal.resources;

import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenManager;

import com.designethereal.objects.GameObject;
import com.designethereal.sprites.BaseSprite;

public class GameObjectAccessor implements TweenAccessor<GameObject>{
	
	//Tween types
	public final static int ROTATION = 1;
	public final static int RESIZE = 2;
	public static GameObjectAccessor instance = null;

	public static GameObjectAccessor getInstance() {
		if(instance == null) {
			instance = new GameObjectAccessor();
		}
		return instance;
	}
	//Tweening registration
	public int getValues(GameObject obj, int tweenType, float[] returnValues) {
		 switch (tweenType) {
	        case ROTATION:
	            returnValues[0] = ((BaseSprite) obj.getBody().getUserData()).getRotation();
	            return 1;
	        case RESIZE:
	        	returnValues[0] = obj.getFixture().getShape().getRadius();
	        	return 1;
	        default:
	        	assert false; return -1;
	    }
	}
	
	public void setValues(GameObject obj, int tweenType, float[] newValues) {
		 switch (tweenType) {
	         case ROTATION: 
        	 	//TODO: implement if necessary
         		break;
	         case RESIZE: 
        	 	obj.getFixture().getShape().setRadius(newValues[0]);
        	 	break;
         default: assert false; break;
     }
	}
		

}
