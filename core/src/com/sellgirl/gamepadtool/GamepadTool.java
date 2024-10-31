package com.sellgirl.gamepadtool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GamepadTool extends Game //extends ApplicationAdapter
{
	SpriteBatch batch;
	public BitmapFont font;
	//Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = MainMenuScreen.getFont2();
		//img = new Texture("badlogic.jpg");


		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
//		ScreenUtils.clear(1, 0, 0, 1);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
		font.dispose();
	}
}
