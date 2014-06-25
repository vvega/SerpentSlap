package com.designethereal.resources;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.designethereal.sprites.BombSprite;

public class BombContactListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {
		 Fixture fixtureA = contact.getFixtureA();
         Fixture fixtureB = contact.getFixtureB();
         Fixture otherFixture = null;
         
         //if either one of the fixtures is owned by this bomb, capture the opposite fixture
   /*      if(fixtureA == this.getFixture()) {
        	 otherFixture = fixtureB;
         } else if(fixtureB == this.getFixture()) {
        	 otherFixture = fixtureA;
         } else {
        	 return;
         }
         
         System.out.println("CONTACT!");
         //if the other fixture is a bomb
         if(otherFixture.getUserData() instanceof BombSprite) {
        	 System.out.println("BOOM");
        	 this.explode();
         }*/
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
