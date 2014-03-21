package com.designethereal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
import com.designethereal.dragon.BodyPart;
import com.designethereal.dragon.Dragon;
import com.designethereal.dragon.Head;
import com.designethereal.dragonslap.Dragonslap;
import com.designethereal.sprites.BaseSprite;
import com.designethereal.sprites.TailSprite;

public class GameScreen extends AbstractScreen implements InputProcessor {

	World world;
	Head head;
	Body ground, ball;
	Dragon dragon;
	MouseJointDef jointDef;
	MouseJoint joint;
	Box2DDebugRenderer debugRenderer;
	Array<Body> tmpBodies = new Array<Body>();
	OrthographicCamera camera;

	public GameScreen(Dragonslap game) {
		super(game);
		
	    camera = new OrthographicCamera(this.stage.getWidth(), this.stage.getHeight());                
       // camera.position.set(0, 15, 0);

	    debugRenderer = new Box2DDebugRenderer();
	   
		world = new World(new Vector2(0, -4), false);
		dragon = new Dragon(world, new Vector2(2,2));
	
		//head.getSprite().setSize(100, 100);

		//ground
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;

		//ball
	/*	CircleShape ballShape = new CircleShape();
		ballShape.setRadius(1);

		ball = world.createBody(bodyDef);
		ball.createFixture(ballShape, 1);
		Sprite headSprite = new HeadSprite();
		headSprite.setPosition(0, 0);
		headSprite.setSize(100, 100);
		ball.setUserData(headSprite);*/
		
		
		EdgeShape groundShape = new EdgeShape();
		groundShape.set(new Vector2(-25,-5), new Vector2(15,-5));

		bodyDef.type = BodyType.StaticBody;
		ground = world.createBody(bodyDef);
		//ground.setUserData(new TailSprite());
		ground.createFixture(groundShape, 0);
		groundShape.dispose();

		// mouse joint
		jointDef = new MouseJointDef();
		jointDef.bodyA = ground;
		jointDef.collideConnected = true;
		jointDef.maxForce = 100;
		jointDef.dampingRatio = 4;
		jointDef.frequencyHz = 5;
		
	}

	@Override
	public void show() {
		super.show();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		//multiplexer.addProcessor(dragon.getHead());
		Gdx.input.setInputProcessor(multiplexer);	
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resize(int width, int height) {
		//this.stage.addActor(dragon);
		//super.resize(width, height);
		camera.viewportWidth = width/25;
		camera.viewportHeight = height/25;
		camera.update();
	}
	
	Vector3 tempVect = new Vector3();
	@Override
	public void render(float delta) {
		
		super.render(delta);
		
		//camera.position.set(head.getBody().getPosition().x, head.getBody().getPosition().y, 0);
		//camera.update();
		this.getBatch().setProjectionMatrix(camera.combined);

		
		world.getBodies(tmpBodies);
       
		this.getBatch().begin();
	    for(Body bod : tmpBodies) {

		    if (bod.getUserData() != null && bod.getUserData() instanceof Sprite) {
		    	BaseSprite dragonBody = (BaseSprite) bod.getUserData();

		        // Update the entities/sprites position and angle
		    	dragonBody.setPosition(bod.getPosition().x, bod.getPosition().y);
		    	
		        dragonBody.draw(this.getBatch());

		    }
	    }
		    
		
		//dragon.act(delta);
		this.getBatch().end();
		
		world.step(1/30f, 8, 3);
		debugRenderer.render(world, camera.combined);

	}
	
	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		 for(Body bod : tmpBodies) { 
	    	if (bod.getUserData() != null && bod.getUserData() instanceof Sprite) {
			    ((Sprite) bod.getUserData()).getTexture().dispose();
	    	}	    	
		 }
		 
         world = null;
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
	
    /** we instantiate this vector and the callback here so we don't irritate the GC **/
    Vector3 testPoint = new Vector3();
    Vector2 testPoint2 = new Vector2();
    
    QueryCallback callback = new QueryCallback() {
            @Override 
            public boolean reportFixture (Fixture fixture) {
                    // if the hit point is inside the fixture of the body
                    // we report it
            	
                    if (!fixture.testPoint(testPoint.x, testPoint.y)) {
                    	return true;
                    } 

                    if(fixture == dragon.getHead().getFixture()) {
	            		camera.unproject(testPoint.set(testPoint.x,testPoint.y,0));
	            		System.out.println("clicka!");
	            		jointDef.bodyB = dragon.getHead().getBody();
	            		//jointDef.target.set(1,1);
	            		jointDef.target.set(testPoint.x/32, testPoint.y/32);
	            		//jointDef.target.set(fixture.getBody().getPosition().x, fixture.getBody().getPosition().y +1);
	    				joint = (MouseJoint) world.createJoint(jointDef);
	    				//dragon.getBodies().get(1).setPosition(new Vector2(testPoint.x, testPoint.y));
	    				//dragon.move(testPoint, ground);
                    }
    				//System.out.println("joint created.");
    				return false;
            }
    };
    
    @Override 
    public boolean touchDown (int x, int y, int pointer, int button) {
            // translate the mouse coordinates to world coordinates
    		this.camera.unproject(testPoint.set(x,y,0));
            world.QueryAABB(callback, testPoint.x, testPoint.y, testPoint.x, testPoint.y);
            
            System.out.println("x:" + testPoint.x +" y:" + testPoint.y);
    		//world.QueryAABB(callback, x, y, x, y);
            return false;
    }
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(joint == null)
			return false;
		
		//System.out.println("joint destroyed.");
		world.destroyJoint(joint);
		joint = null;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(joint == null)
			return false;
		
		camera.unproject(testPoint.set(screenX, screenY, 0));
		joint.setTarget(testPoint2.set(testPoint.x, testPoint.y));
	//	joint.setTarget(new Vector2(50, 50));
		return true;
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
