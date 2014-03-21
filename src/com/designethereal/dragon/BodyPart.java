package com.designethereal.dragon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.designethereal.sprites.BaseSprite;

public abstract class BodyPart {
	
	protected BaseSprite sprite;
	protected Fixture fixture;
	protected Shape fixtureShape;
	protected float width;
	protected float height;
	
	private Body body;
	private FixtureDef fDef;
	private BodyDef bDef;
	private Vector2 position;
	private World world;
	private BodyPart previous = null;
	private BodyPart next = null;
	private final short GROUP_DRAGON = 0;
	private final short GROUP_BOMBS = 1;
	private final short GROUP_SCENERY = 2;

	
	public BodyPart(World world, Vector2 position) {
		this.world = world;
		this.position = position;
	}
	
	protected void init() {
		initSprite();
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		
		initBDef();
		this.body = world.createBody(this.bDef);
		
		initFDef();
		initFixtureShape();
		this.fixture = this.body.createFixture(this.fDef);

		body.setUserData(sprite);
		fixtureShape.dispose();
	}
	
	public Vector2 centerPosition() {
		return new Vector2(body.getLocalCenter().x + width/2, 
				body.getLocalCenter().y + height/2);
		
	}
	
	public FixtureDef getFixtureDef() {
		return this.fDef;
	}
	
	public BodyDef getBodyDef() {
		return this.bDef;
	}
	
	private void initBDef() {
		bDef = new BodyDef();
		bDef.active = true;
		bDef.fixedRotation = true;
		//bDef.angularDamping
		bDef.type = BodyType.DynamicBody;
		bDef.position.set(this.position);
	}
	
	private void initFDef() {
		fDef = new FixtureDef();
		fDef.shape = this.fixtureShape;
		fDef.filter.groupIndex = GROUP_DRAGON;
		fDef.friction = .75f;
		fDef.restitution = .1f;
		fDef.density = 1;
	}
	
	private void initFixtureShape() {
		if(fixtureShape instanceof CircleShape) {
			((CircleShape) fixtureShape).setPosition(centerPosition());
		} else if(fixtureShape instanceof PolygonShape) {
			((PolygonShape) fixtureShape).setAsBox(width/2, height/2, centerPosition(), body.getAngle());
		} 
	}
	
	protected void initSprite() {
		this.sprite.setSize(this.sprite.getWidthInMeters(), this.sprite.getHeightInMeters());
	}
	
	public void setPosition(Vector2 pos) {
		this.position = pos;
		//this.bDef.position.set(pos);
	}
	
	public Body getBody() {
		return this.body;
	}
	
	public BaseSprite getSprite() {
		return this.sprite;
	}
	
	public Fixture getFixture() {
		return this.fixture;
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
	
	protected void setPrevious(BodyPart b) {
		this.previous = b;
	}
	
	protected void setNext(BodyPart b) {
		this.next = b;
	}
	
	protected BodyPart getPrevious() {
		return previous;
	}
	
	protected BodyPart getNext() {
		return next;
	}
	
	protected boolean hasNext() {
		return (next != null) ? true : false;
	}
	
	protected boolean hasPrevious() {
		return (previous != null) ? true : false;
	}

	
}
