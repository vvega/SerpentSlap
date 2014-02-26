package com.designethereal.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ResourceManager {
	
	public static TextureAtlas atlas;
	public static TextureRegion headTexture;
	public static TextureRegion bodyTexture;
	public static TextureRegion tailTexture;
	
	public static void init() {
		//gather textures
		atlas = new TextureAtlas(Gdx.files.internal("data/textures/dragon.atlas"));
		headTexture = atlas.findRegion("head");
		bodyTexture = atlas.findRegion("body");
		tailTexture = atlas.findRegion("tail");
	}
}
