package com.sellgirl.gamepadtool;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sellgirl.sgGameHelper.SGFileDownloader;
import com.sellgirl.sgGameHelper.SGGameHelper;
import com.sellgirl.sgJavaHelper.SGDate;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

public class GamepadTool extends Game //extends ApplicationAdapter
{
	SpriteBatch batch;
	public BitmapFont font;
	//Texture img;
	
	@Override
	public void create () {
		initLibGdx();
		initSG();
		SGDataHelper.getLog().print("开始运行: "+ SGDate.Now());

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
	private  void initLibGdx(){
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
//		SGDataHelper.getLog().print("开始运行: "+ SGDate.Now());
	}
	private void initSG() {
		SGDataHelper.SetConfigMapper(new SGConfigMapper());
//		SGEmailSend.EMAIL_OWNER_ADDR_HOST = "";
		SGDataHelper.sgLog=new SGLibGdxLog();
		SGGameHelper.setGameConfig(new SGGameConfig());
//		try {
//			SGEmailSend.EMAIL_OWNER_ADDR = AES.AESDecryptDemo("u8k/Cz5Z9ddjUvNuTeXVVA==",
//					SGDataHelper.decodeBase64(key));
//			SGEmailSend.EMAIL_OWNER_ADDR_PASS = AES.AESDecryptDemo("9Y4YkBmOw1mlrmmx4QHz8wK5E7/ZhZxuvoll2MmCvVc=",
//					SGDataHelper.decodeBase64(key));
//			SGEmailSend.EMAIL_OWNER_ADDR_HOST_PROPERTY = HostType.SELLGIRL.getProperties();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/*------------------apk自动更新------------------*/
//     @Override
	public SGFileDownloader getJarDownloader() {
		return jarDownloader;
	}

	//     @Override
	public void setJarDownloader(SGFileDownloader d) {
		jarDownloader=d;
	}
	public SGFileDownloader jarDownloader=null;
	/*------------------apk自动更新 end------------------*/
}
