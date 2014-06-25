package com.designethereal.objects;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.designethereal.resources.GameObjectAccessor;
import com.designethereal.sprites.BombSprite;

public class Bomb extends GameObject implements ContactListener {
	
	float time;
	Timer timer = null;
	
	private float EXPLOSION_DURATION = 10f;
	private float EXPLOSION_SIZE = 5;
	private boolean isExploding;
	
	public Bomb(float time, Vector2 start, World world) {
		super(world, start);
		this.time = time;
		Setup();
		super.init();
		this.body.applyForceToCenter(new Vector2(randomFloat(50),randomFloat(50)), true);	
	}
	
	protected void setupListener() {
		world.setContactListener(this);
	}
	
	protected void Setup() {
		this.sprite = new BombSprite();
		this.fixtureShape = new CircleShape();
		this.fixtureShape.setRadius(this.sprite.getWidthInMeters()/3);
		Tween.registerAccessor(GameObject.class, GameObjectAccessor.getInstance());
		this.timer = new Timer();
		this.timer.scheduleTask(new Explosion(this), time);
	}
	
	@Override
	protected void initFDef() {
		fDef = new FixtureDef();
		fDef.shape = this.fixtureShape;
		//fDef.filter.groupIndex = GROUP_DRAGON;
		fDef.friction = 2f;
		fDef.restitution = 5;
		fDef.density = 5f;
	}
	
	private float randomFloat(int seed){
		return (float) Math.random()*seed;
	}

	public void explode() {
		
		if(timer != null){
			timer.clear();
		}
		//tween animation for explosion
		Timeline.createSequence()
			//expansion
			.push(Tween.to(this, GameObjectAccessor.RESIZE, EXPLOSION_DURATION)
					.target(getFixture().getShape().getRadius()*EXPLOSION_SIZE))
			//shrink
			.push(Tween.to(this, GameObjectAccessor.RESIZE, EXPLOSION_DURATION/2)
					.target(0)).setCallback(new TweenCallback() {

				@Override
				public void onEvent(int type, BaseTween<?> source) {
					
					//destroy the bomb after explosion
					if(type == TweenCallback.COMPLETE) {
						getBody().destroyFixture(getFixture());
						isExploding = false;
						//((Bomb)source.getUserData()).setFlaggedForDeletion(true);
					}
					
				}
				
			}).start(BombGen.manager);
		
		this.isExploding = true;				
	}
	
	public boolean isExploding() {
		return this.isExploding;
	}
	
	private class Explosion extends Task {
		
		protected Bomb bomb;
		
		protected Explosion(Bomb b) {
			this.bomb = b;
		}
		
		@Override
		public void run() {
			bomb.explode();
		}
	}

	@Override
	public void beginContact(Contact contact) {
		 Fixture fixtureA = contact.getFixtureA();
         Fixture fixtureB = contact.getFixtureB();
         Fixture otherFixture = null;
         
         //if either one of the fixtures is owned by this bomb, capture the opposite fixture
         if(fixtureA == this.getFixture()) {
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
         }
		
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
