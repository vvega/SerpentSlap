package com.designethereal.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ResourceManager {
	
	//Splash textures
	public static Texture splashTexture;
	//UI
	public static Skin uiSkin;
	//Game Textures
	public static TextureAtlas atlas;
	public static TextureRegion headTexture;
	public static TextureRegion bodyTexture;
	public static TextureRegion tailTexture;
	public static int headYOffset;
	public static int tailYOffset;
	public static int tailXOffset;
	
	public static void init() {

		//Splash
        splashTexture = new Texture("data/ui/splash.png");
        splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        //UI
        uiSkin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
        
        //Game
		atlas = new TextureAtlas(Gdx.files.internal("data/textures/lo/dragon.atlas"));
		headTexture = atlas.findRegion("head");
		//headTexture.flip(true, true);
		bodyTexture = atlas.findRegion("body");
		bodyTexture.flip(true, true);
		tailTexture = atlas.findRegion("tail");
		tailTexture.flip(true, true);
		headYOffset = headTexture.getRegionHeight() - bodyTexture.getRegionHeight();
		tailXOffset = tailTexture.getRegionWidth() - bodyTexture.getRegionWidth();
		tailYOffset = tailTexture.getRegionHeight() - bodyTexture.getRegionHeight();
	}
	
	
}
