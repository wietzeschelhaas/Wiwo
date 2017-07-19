package com.wgames.wiwo;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Wiwo extends Game {


	public static final int screenWidth = 828;
	public static final int screenHeight = 1472;

	// these values are used as additional size
	public static final float brickSize = 0.4f;
	public static final float letterSize = 0.16f;
	public static final float brickSizeHUD = 0.08f;


	FPSLogger fpsLogger;
	OrthographicCamera camera;
	Viewport viewport;

	TextureAtlas menuItems;
	TextureAtlas letters;
	public AssetManager manager;

	static DataBase dataBase;
	static Preferences preferences;

	public Wiwo(DataBase db, Preferences p){
		fpsLogger = new FPSLogger();
		camera = new OrthographicCamera();
		camera.position.set(screenWidth/2,screenHeight/2,0);
		viewport = new FillViewport(screenWidth,screenHeight,camera);
		manager = new AssetManager();

		dataBase = db;
		preferences = p;
	}
	@Override
	public void create () {

		manager.load("UI.pack", TextureAtlas.class);
		manager.load("menuBrick.png", Texture.class);
		manager.load("wiwo.png", Texture.class);
		manager.load("letters.pack", TextureAtlas.class);
		manager.finishLoading();

		menuItems = manager.get("UI.pack",TextureAtlas.class);
		letters = manager.get("letters.pack",TextureAtlas.class);

		dataBase.createDataBase();
		dataBase.openDatabase();


		setScreen(new MenuScene(this));

	}

	@Override
	public void render() {
		//fpsLogger.log();
		super.render();
	}

	public void resize(int width, int height) {
		viewport.update(width, height);
	}
}
