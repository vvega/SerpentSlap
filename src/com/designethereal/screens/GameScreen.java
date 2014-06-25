package com.designethereal.screens;

import java.util.Iterator;

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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
import com.designethereal.dragonslap.Dragonslap;
import com.designethereal.objects.BodySegment;
import com.designethereal.objects.Bomb;
import com.designethereal.objects.BombGen;
import com.designethereal.objects.Dragon;
import com.designethereal.objects.GameObject;
import com.designethereal.objects.Head;
import com.designethereal.sprites.BaseSprite;

public class GameScreen extends AbstractScreen implements InputProcessor {

	World world;
	Head head;
	Body ground, ball;
	Dragon dragon;
	BombGen bGen;
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
	   
		world = new World(new Vector2(0, 0), false);
		dragon = new Dragon(world, new Vector2(camera.viewportWidth/40, camera.viewportHeight/40));
		bGen = new BombGen(world);
		bGen.start();

		//ground
		EdgeShape groundShape = new EdgeShape();
		groundShape.set(new Vector2(-camera.viewportWidth/35, -camera.viewportHeight/35), new Vector2(camera.viewportWidth/35, -camera.viewportHeight/35));
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		ground = world.createBody(bodyDef);
		//ground.setUserData(new TailSprite());
		ground.createFixture(groundShape, 0);

		Body wall_left = world.createBody(bodyDef);
		Body wall_right = world.createBody(bodyDef);
		Body ceiling = world.createBody(bodyDef);
		
		groundShape.set(new Vector2(-camera.viewportWidth/35, -camera.viewportHeight/35), new Vector2(-camera.viewportWidth/35, camera.viewportHeight/35));
		wall_left.createFixture(groundShape, 0);
		groundShape.set(new Vector2(camera.viewportWidth/35, -camera.viewportHeight/35), new Vector2(camera.viewportWidth/35, camera.viewportHeight/35));
		wall_right.createFixture(groundShape, 0);
		groundShape.set(new Vector2(-camera.viewportWidth/35, camera.viewportHeight/35), new Vector2(camera.viewportWidth/35, camera.viewportHeight/35));
		ceiling.createFixture(groundShape, 0);
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
		camera.viewportWidth = width/17;
		camera.viewportHeight = height/17;
		camera.update();
	}
	
	public float getRotationAngle(Vector2 to, Vector2 from) {

		//return angle calculation plus angle adjustment to align with tip of the nose
		//note that deltaY is reversed to properly calculate Y coordinates on the stage
		return (float) (Math.atan2(to.x - from.x, to.y - from.y) * 180 / Math.PI) + 90;
	}
	
	Vector3 tempVect = new Vector3();
	@Override
	public void render(float delta) {
		
		super.render(delta);
		world.getBodies(tmpBodies);
		
		BombGen.manager.update(1);
		this.getBatch().setProjectionMatrix(camera.combined);
		this.getBatch().begin();
		
		//handle dragon drawing
		for(BodySegment part : dragon.getBodies()) {
	    	drawBody(part.getBody());
		}
		
		//handle bomb drawing
		/*for(Bomb bomb : BombGen.getBombs()) {
			drawBody(bomb.getBody());
		}*/

		this.getBatch().end();
		
		world.step(1/5f, 8, 3);
		//sweepDeadBodies(tmpBodies);
		debugRenderer.render(world, camera.combined);

	}
	
/*	public void sweepDeadBodies(Array<Body> tempBodies) {
		   for (Iterator<Body> iter = tempBodies.iterator(); iter.hasNext();) {
		     Body body = iter.next();
		     if(body!=null) {
		          GameObject data = (GameObject) body.getUserData();
		          if(data.isFlaggedForDeletion()) {
		        	  world.destroyBody(body);
		        	  body.setUserData(null);
		        	  body = null;
		          }
		     }
		   }
	}*/
	
	private void drawBody(Body b) {
		BaseSprite sprite = (BaseSprite) b.getUserData(); 
    	sprite.setOrigin(0,0);
    	sprite.setPosition(b.getPosition().x, b.getPosition().y);
		sprite.setOrigin(sprite.getWidthInMeters()/2, sprite.getHeightInMeters()/2);
		sprite.setRotation(MathUtils.radiansToDegrees*b.getAngle());
		
		sprite.draw(this.getBatch());
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
 
                    if (!fixture.testPoint(testPoint.x, testPoint.y)) {
                    	return true;
                    } 

                    if(fixture == dragon.getHead().getFixture()) {
                  
	            		camera.unproject(testPoint.set(testPoint.x,testPoint.y,0));
	            		jointDef.bodyB = dragon.getHead().getBody();
	            		jointDef.target.set(jointDef.bodyB.getWorldCenter().x, jointDef.bodyB.getWorldCenter().y);	  
	    				joint = (MouseJoint) world.createJoint(jointDef);
                    }
    				return false;
            }
    };
    
    @Override 
    public boolean touchDown (int x, int y, int pointer, int button) {
            // translate the mouse coordinates to world coordinates
    		this.camera.unproject(testPoint.set(x,y,0));
            world.QueryAABB(callback, testPoint.x, testPoint.y, testPoint.x, testPoint.y);
            return false;
    }
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(joint == null)
			return false;
		
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
