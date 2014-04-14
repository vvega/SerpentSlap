package com.designethereal.dragon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.designethereal.sprites.BaseSprite;

public abstract class GameObject {
	
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
	private final short GROUP_DRAGON = 0;
	private final short GROUP_BOMBS = 1;
	private final short GROUP_SCENERY = 2;

	
	public GameObject(World world, Vector2 position) {
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
		bDef.fixedRotation = false;
		//bDef.angularDamping
		bDef.type = BodyType.DynamicBody;
		bDef.position.set(this.position);
	}
	
	private void initFDef() {
		fDef = new FixtureDef();
		fDef.friction = 1;
		fDef.shape = this.fixtureShape;
		fDef.filter.groupIndex = GROUP_DRAGON;
		fDef.friction = .75f;
		fDef.restitution = .1f;
		fDef.density = .5f;
	}
	
	private void initFixtureShape() {
		if(fixtureShape instanceof CircleShape) {
			((CircleShape) fixtureShape).setPosition(centerPosition());
		} else if(fixtureShape instanceof PolygonShape && this instanceof BodySegment) {
			((PolygonShape) fixtureShape).setAsBox(width/2 - .2f, height/2, centerPosition(), body.getAngle());
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
	
}
