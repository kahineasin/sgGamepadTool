package com.sellgirl.gamepadtool;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sellgirl.sgGameHelper.SGFileDownloader;
import com.sellgirl.sgGameHelper.SGGameHelper;
import com.sellgirl.sgJavaHelper.SGDate;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

import java.util.HashMap;
import java.util.Map;

public class GamepadTool extends Game //extends ApplicationAdapter
{
	SpriteBatch batch;
	public BitmapFont font;
	//Texture img;
	
	@Override
	public void create () {
//		initLibGdx();
//		initSG();
//		SGDataHelper.getLog().print("开始运行: "+ SGDate.Now());
//
//		batch = new SpriteBatch();
////		font = MainMenuScreen.getFont2();
//		font = getFont2();
//		//img = new Texture("badlogic.jpg");

		doCreate();

		this.setScreen(new MainMenuScreen(this));
	}
	protected void doCreate(){
		initLibGdx();
		initSG();
		SGDataHelper.getLog().print("开始运行: "+ SGDate.Now());

		batch = new SpriteBatch();
//		font = MainMenuScreen.getFont2();
		font = getFont2();
		fontDatas=new HashMap<>();
		//img = new Texture("badlogic.jpg");

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
//		//img.dispose();
//		font.dispose();
		if(null!=font){
			font.dispose();
			font=null;
		}
		disposeFont();
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

	/*------------------FONT------------------*/
	private  FreeTypeFontGenerator generator;// TTF字体发生器
	private FreeTypeFontGenerator.FreeTypeBitmapFontData fontData;// 负责处理FreeTypeFontGenerator的数据.可以简单地理解成为一个加工好的字符库
	//不同size的font
	private HashMap<Integer,FreeTypeFontGenerator.FreeTypeBitmapFontData>  fontDatas;
	//     @Deprecated
//     private FreeTypeFontGenerator.FreeTypeBitmapFontData fontData2;
	public boolean hasNewCn=false;
	/**
	 * 常用，省内存，但不会刷新本次新增的中文字
	 * @return
	 */
	public BitmapFont getFont2() {
//		if(null!=font) {return font;}
		if(null==generator) {

			/**
			 * 以下是进行初始化
			 */
			generator = new FreeTypeFontGenerator(Gdx.files.internal("simhei.ttf"));//generator这个东西就算dispose也没用的,内存增加9MB,没办法了
		}

		if(hasNewCn&&null!=fontData){
			fontData.dispose();
			fontData=null;
		}
		if(null==fontData) {//测试发现fontData就算dispose了,还是消耗内存,原因不明
			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			FileHandle file = Gdx.files.internal("font_cn.txt");
//			String text = file.readString(SGDataHelper.encoding);
			String text = file.readString("utf8");

			parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS
					+"△○□↑→↓←↖↗↘↙∞"
					+ text//+MainMenuScreen.readExCn()
			;

			parameter.size=ScreenSetting.FONT_SIZE;//24
			fontData = generator.generateData(parameter);

			if(hasNewCn){hasNewCn=false;}
		}

		BitmapFont font = new BitmapFont(fontData, fontData.regions, false);
		font.setColor(Color.WHITE);

		return font;
	}

	public BitmapFont getFont3(int size) {
//		if(null!=font) {return font;}
		if(null==generator) {

			/**
			 * 以下是进行初始化
			 */
			generator = new FreeTypeFontGenerator(Gdx.files.internal("simhei.ttf"));//generator这个东西就算dispose也没用的,内存增加9MB,没办法了
		}

		if(hasNewCn&&null!=fontData){
			fontData.dispose();
			fontData=null;
		}
		if(!fontDatas.containsKey(size)){//null==fontData) {//测试发现fontData就算dispose了,还是消耗内存,原因不明
			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			FileHandle file = Gdx.files.internal("font_cn.txt");
//			String text = file.readString(SGDataHelper.encoding);
			String text = file.readString("utf8");

			parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS
					+"△○□↑→↓←↖↗↘↙∞"
					+ text//+MainMenuScreen.readExCn()
			;

			parameter.size=size;//ScreenSetting.FONT_SIZE;//24
			FreeTypeFontGenerator.FreeTypeBitmapFontData fontData2 = generator.generateData(parameter);

			fontDatas.put(size,fontData2);
//			if(hasNewCn){hasNewCn=false;}
		}

		BitmapFont font = new BitmapFont(fontDatas.get(size), fontDatas.get(size).regions, false);
		font.setColor(Color.WHITE);

		return font;
	}
	/**
	 * harmony有些情况下（比如屏幕右侧向内推时）,会进入Game.dispose方法，
	 * 之后旧的generator会导致文字变黑块（可能被系统自动释放了）。
	 * 所以game.create时要重新new fontData
	 */
	public void disposeFont(){
		if(null!=fontData){
			fontData.dispose();
			fontData=null;
		}
		if(null!=fontDatas&&!fontDatas.isEmpty()){
			for (FreeTypeFontGenerator.FreeTypeBitmapFontData m1 : fontDatas.values()) {
				m1.dispose();
			}
			fontDatas.clear();
			fontDatas=null;
		}
	}
	/*------------------FONT end------------------*/
}
