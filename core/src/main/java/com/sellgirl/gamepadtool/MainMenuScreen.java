package com.sellgirl.gamepadtool;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.touchable;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.sellgirl.gamepadtool.language.CN;
import com.sellgirl.gamepadtool.language.TXT;
import com.sellgirl.gamepadtool.phone.ControllerMappingScreen;
import com.sellgirl.gamepadtool.screen.GamepadSettingScreen;
import com.sellgirl.gamepadtool.screen.KeySettingScreen;
import com.sellgirl.gamepadtool.screen.SimulateScreen;
import com.sellgirl.gamepadtool.util.Constants;
import com.sellgirl.sgGameHelper.SGConfirmPopups;
import com.sellgirl.sgGameHelper.SGFileDownloader;
import com.sellgirl.sgGameHelper.SGGameHelper;
import com.sellgirl.sgGameHelper.SGLibGdxHelper;
import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
import com.sellgirl.sgGameHelper.tabUi.TabUi;
import com.sellgirl.sgJavaHelper.SGAction1;
import com.sellgirl.sgJavaHelper.SGDate;
import com.sellgirl.sgJavaHelper.antivirus.SGProcess;
import com.sellgirl.sgJavaHelper.antivirus.SGProcessHelper;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class MainMenuScreen implements Screen {
//	private static final String tag="MainMenuScreen";
	private static  final String tag=MainMenuScreen.class.getName();
	private final Stage stage;
	private final Table table;
	final GamepadTool game;

//	// OrthographicCamera camera;
//	@Deprecated
//	Controller controller;
//	SGPS5Gamepad sgcontroller;
//	private static IApiUrl url = null;
//	SashaData sasha = null;
	private final Skin skin;
	TextButton enterGameBtn;

	private void testReadFile(String path){
		testReadResourceAsStream(path);
		testReadByLibGdx(path);
		testReadResourceBySellgirl(path);
	}
	private void testReadResourceBySellgirl(String path){
		try {
//			idea:ok; java -jar:ok;  exe混淆:ok; exe不混淆:??
			String s=SGDataHelper.readAnylResource(path,10);
			SGDataHelper.getLog().print("testReadResourceBySellgirl :\r\n "+(null!=s?s:"null"));

		}catch (Throwable e){}
	}
	private void testReadResourceAsStream(String path){
		try {
//			idea:no; java -jar:ok;  exe混淆:ok; exe不混淆:no
			SGDataHelper.getLog().print("MainMenuScreen.class.getResourceAsStream ok:"+(null!=MainMenuScreen.class.getResourceAsStream(path)));
//			idea:no; java -jar:ok;  exe混淆:ok; exe不混淆:no
			SGDataHelper.getLog().print("SGDataHelper.class.getResourceAsStream ok:"+(null!=SGDataHelper.class.getResourceAsStream(path)));
//			idea:ok; java -jar:ok;  exe混淆:no; exe不混淆:no
			SGDataHelper.getLog().print("ClassLoader.getSystemResourceAsStream ok:"+(null!=ClassLoader.getSystemResourceAsStream(path)));
//		if(source==null) throw new IOException("Cannot open resource from classpath "+path);
		}catch (Throwable e){}
	}
	private void testReadByLibGdx(String path){
		try {
//			idea:ok; java -jar:ok; exe混淆:ok; exe不混淆:ok
			SGDataHelper.getLog().print("Gdx.files.internal ok:"+(null!=Gdx.files.internal(path).read()));
//			idea:ok; exe混淆:ok; exe不混淆:ok
			SGDataHelper.getLog().print("FileHandle.class.getResourceAsStream('/xx') ok:"+(null!=FileHandle.class.getResourceAsStream("/" + path.replace('\\', '/'))));
//			idea:no; exe混淆:no; exe不混淆:no
			SGDataHelper.getLog().print("new FileInputStream(new File(Gdx.files.getExternalStoragePath(), path)) ok:"+(new File(Gdx.files.getExternalStoragePath(), path)).exists());
//			idea:no; exe混淆:no; exe不混淆:no
			SGDataHelper.getLog().print("new FileInputStream(new File(path)) ok:"+(new File(path)).exists());
//		if(source==null) throw new IOException("Cannot open resource from classpath "+path);
		}catch (Throwable e){
			SGDataHelper.getLog().printException(e,tag+"testReadByLibGdx");
		}
	}
ISGPS5Gamepad sgcontroller;
	TabUi tabUi;
	public MainMenuScreen(final GamepadTool game) {
//		public MainMenuScreen( SashaGame game) {
		this.game = game;

//		initLibGdx();
//		initSG();

//
//		sasha = readSashaData();
//		// sasha.setCountry(Country.CN.toString());
////		sasha.setCountry(Country.CN);
////		saveSashaData(sasha);
//
//		if (Country.CN == sasha.getCountry()) {
//			url = new CnUrl();
//
//			if(GameSetting.isGameX()) {
//				Map<String,String> m=CN.get();
//				m.putAll(GameSetting.getTXTX().getCN());
//				TXT.init(m);
//			}else {
				TXT.init(CN.get());
//			}
//		}
//		getSashaVip();
//
//		double now=SashaData.getDaysBetween(SGDate.Now());
//
////		Gdx.app.setLogLevel(Application.LOG_DEBUG);
////		Gdx.app.log("SGLibGdxLog", "my informative message");
////		Gdx.app.error("SGLibGdxLog", "my error message", new Exception("aaa"));
////		Gdx.app.debug("SGLibGdxLog", "my debug message");
////		SGDataHelper.getLog().print("bbbb");
////		try {
//////			System.out.println("bbbb");
//////			SGDataHelper.getLog().print("bbbbb");
////			Gdx.app.error("aaaa","aaaa");
////			this.testEmailSend();
////		} catch (Exception e) {
//////			//System.out.println("aaaaa");
//////			SGDataHelper.getLog().print("aaaaa");
//////			SGDataHelper.getLog().print(e);
////			Gdx.app.error("bbbb","bbbb");
////			//System.out.println(e);
////		}
//		if(now>sasha.getLastLogin()) {
//			sasha.setLastLogin(now);
//			saveSashaData(sasha);
//			String deviceName=sasha.getUserId();
//			if("0".equals(sasha.getUserId())){
//				try {
//					deviceName=InetAddress.getLocalHost().getHostName();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			SGEmailSend.SendMail(new String[] { SGEmailSend.EMAIL_OWNER_ADDR },
//					"knightSasha_" + deviceName + "_登陆通知", deviceName + "登陆通知,isGameX "+GameSetting.isGameX());
//
//
//		}
		this.sgcontroller= SGLibGdxHelper.getSGGamepad();
		// 使用伸展视口（StretchViewport）创建舞台
		stage = new Stage(new StretchViewport(ScreenSetting.WORLD_WIDTH, ScreenSetting.WORLD_HEIGHT));

//      /* 事件初始化 */
//
		// 首先必须注册输入处理器（stage）, 将输入的处理设置给 舞台（Stage 实现了 InputProcessor 接口）
		// 这样舞台才能接收到输入事件, 分发给相应的演员 或 自己处理。
		Gdx.input.setInputProcessor(stage);


//		skin = MainMenuScreen.getSkin();
//		skin.add("default", MainMenuScreen.getButtonStyle(skin));
//		skin.add("default", MainMenuScreen.getLabelStyle(skin));
//		skin.add("default", MainMenuScreen.getTextFieldStyle(skin));
//		skin.add("default", MainMenuScreen.getWindowStyle(skin));
//		skin.add("default", MainMenuScreen.getCheckBoxStyle(skin));
//		skin.add("default-horizontal", MainMenuScreen.getSliderStyle(skin));

		skin=MainMenuScreen.getSkin2(game.font);

		skinLibgdx=skin;
		//skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
		//skinLibgdx = new Skin(Gdx.files.internal("skin/uiskin.json"), new TextureAtlas("skin/uiskin.atlas"));


		table = new Table();
		table.setFillParent(true);


		TextButton gamePadToKeyboardBtn = null;
		TextButton gamePadToKeyboardSettingBtn=null;
		TextButton gamePadDeadZoneSettingBtn=null;
		//if(game.hasKeyboard) {

			gamePadToKeyboardBtn=new TextButton(TXT.g("gamepad to keyboard input"), skin);
			gamePadToKeyboardSettingBtn = new TextButton(TXT.g("setting"), skin);
			gamePadDeadZoneSettingBtn = new TextButton(TXT.g("dead zone of joystick"), skin);
		TextButton exitGameBtn = new TextButton(TXT.g("exit app"), skin);

			gamePadToKeyboardBtn.addListener(new ClickListener() {

				@Override
				public void clicked(InputEvent event, float x, float y) {
					goToGamepadToKeyboardPage();
				}
			});
			gamePadToKeyboardSettingBtn.addListener(new ClickListener() {

				@Override
				public void clicked(InputEvent event, float x, float y) {
					goToGamepadToKeyboardSettingPage();
				}
			});
		gamePadDeadZoneSettingBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				goToGamepadDeadZoneSettingPage();
			}
		});
		//}
//		enterD3GameBtn.addListener(new ClickListener() {
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				goToGameD3Page();
//			}
//		});
//
//		enterKofGameBtn.addListener(new ClickListener() {
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				goToKofCharacterPage();
//			}
//		});
//
//		enterRhythmGameBtn.addListener(new ClickListener() {
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				goToRhythmPage();
//			}
//		});
//		optionBtn.addListener(new ClickListener() {
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				onOptionsClicked();
//			}
//		});
//		keySettingBtn.addListener(new ClickListener() {
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				goToKeySettingPage();
//			}
//		});
		exitGameBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				exitGame();
			}
		});

		int buttonSpace=20;
//		table.add(button1).spaceBottom(buttonSpace);
//		table.row();
//		table.add(enterGameBtn).spaceBottom(buttonSpace);
//		table.row();
//		table.add(mergeSettingBtn).spaceBottom(buttonSpace);
//		table.row();
		table.add(gamePadToKeyboardBtn).spaceBottom(buttonSpace);
		table.row();
		table.add(gamePadToKeyboardSettingBtn).spaceBottom(buttonSpace);
		table.row();
		table.add(gamePadDeadZoneSettingBtn).spaceBottom(buttonSpace);
		table.row();
//		table.add(enterKofGameBtn).spaceBottom(buttonSpace);
//		table.row();
//		table.add(enterD3GameBtn).spaceBottom(buttonSpace);
//		table.row();
//		table.add(enterRhythmGameBtn).spaceBottom(buttonSpace);
//		table.row();
//		table.add(optionBtn).spaceBottom(buttonSpace);
//		table.row();
//		table.add(keySettingBtn).spaceBottom(buttonSpace);
//		table.row();
		table.add(exitGameBtn);

		tabUi=new TabUi();
		tabUi.setItem(table.getChildren());

		stage.addActor(table);

		if(Application.ApplicationType.Android==Gdx.app.getType()||Application.ApplicationType.Desktop==Gdx.app.getType()) {
			getLastVersion(new SGAction1<String>() {
				@Override
				public void go(String s) {
					String version = s;
					if (null != version && 0 < SGDataHelper.compareVersion(version, gameVersion)) {
						SGConfirmPopups confirmPopups= new SGConfirmPopups(SGDataHelper.FormatString(TXT.g("found new version {0}, update now?"), version),
								new Consumer<Object>() {
									@Override
									public void accept(Object o) {
										downloadJar(true);
									}
								},
								skin
						);
						confirmPopups.show(stage);
//                // 将对话框右下
						confirmPopups.setPosition(
								(stage.getWidth() - confirmPopups.getWidth()) *7f/ 8f,
								(stage.getHeight() - confirmPopups.getHeight()) / 8f);
					}
				}
			});
		}
//		if(Application.ApplicationType.Android==Gdx.app.getType()){
//			((AndroidGamepadTool)game).checkSimulateService();
//		}
	}
	// private int cnt=0;


//	@SuppressWarnings("unused")
//	private void initTestSashaData() {
//		// readUserData();
//		sasha = SashaData.init();
//		// sasha = readSashaData();
//		sasha.setCountry(Country.CN);
//		sasha.setEmail("li@sellgirl.com");
//		sasha.setUserId("li@sellgirl.com");
//		saveSashaData(sasha);
//	}

	@Override
	public void show() {
		// System.out.println("listeners count "+enterGameBtn.getListeners().size);

	}

	private final float  buttonWait=0.4f;
	private float buttonWaitCount=1f;
	@Override
	public void render(float delta) {

//		if (null != sgcontroller && sgcontroller.isCROSS()) {
//			// gotoGamePage();
//			goToGuidePage();
//		}
//		else if (null != sgcontroller && sgcontroller.isSQUARE()) {
////			goToKofGamePage();
//			goToKofCharacterPage();
//		}
//		else if (null != sgcontroller && sgcontroller.isTRIANGLE()) {
//			goToUserInfoPage();
//		}
//		else if (null != sgcontroller && sgcontroller.isL1()) {
//			goToGameD3Page();
//		}else if (null != sgcontroller && sgcontroller.isR1()) {
//			goToRhythmPage();
//		}

		if(0>=buttonWaitCount) {
			if (null != sgcontroller && sgcontroller.isUP()) {
				tabUi.up();
				buttonWaitCount = buttonWait;
			} else if (null != sgcontroller && sgcontroller.isDOWN()) {
				tabUi.down();
				buttonWaitCount = buttonWait;
			}
			if (null != sgcontroller && sgcontroller.isLEFT()) {
				tabUi.left();
				buttonWaitCount = buttonWait;
			} else if (null != sgcontroller && sgcontroller.isRIGHT()) {
				tabUi.right();
				buttonWaitCount = buttonWait;
			} else if (null != sgcontroller && sgcontroller.isCROSS()) {
				tabUi.select();
				buttonWaitCount = buttonWait;
			}
//			else if (null != sgcontroller && sgcontroller.isROUND()) {
//				//popups.hide();
//				showOptionsWindow(false, true);
//			}
		}
		if (buttonWaitCount > 0) {

			buttonWaitCount -= delta;
		} else if (buttonWaitCount < 0) {
			buttonWaitCount = 0;
		}

//		final SGFileDownloader jarDownloader=game.getJarDownloader();
		if(null!=game.getJarDownloader()&& game.getJarDownloader().downloading){
			if(null==jarPB){
				jarPB=new ProgressBar(0,100,1,false,skin);
				jarPB.setX(ScreenSetting.WORLD_WIDTH*3f/4f);
				jarPB.setY(ScreenSetting.WORLD_HEIGHT/4f);
				stage.addActor(jarPB);
			}
			if(100<=game.getJarDownloader().progress){
				if(game instanceof AndroidGamepadTool){
					((AndroidGamepadTool)game).updateApk(Constants.EXTERNAL_APK_FILE);
					//在某些系统上，可能有漏掉的权限导致updateApk没反应，下面这行是为了防止死循环
					game.getJarDownloader().downloading=false;
				}else
				if(Application.ApplicationType.Desktop== Gdx.app.getType()){// 启动更新脚本并退出当前应用
					try {
						new ProcessBuilder("cmd", "/c", "start", "updater.bat").start();
						Gdx.app.exit();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}else{
				jarPB.setValue(game.getJarDownloader().progress);
			}
		}

		if(null==stage){return;}    //这句也一定不能少

//		ScreenUtils.clear(0, 0, 0.2f, 1);
		ScreenUtils.clear(Color.PINK);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		stage.act(Gdx.graphics.getDeltaTime());
		stage.act(delta);
		stage.draw();

		Batch batch=stage.getBatch();
		batch.begin();
		game.font.draw(batch,"version:"+gameVersion,ScreenSetting.WORLD_WIDTH-300,40);
		batch.end();
	}


	private  void goToGamepadToKeyboardPage() {

//		game.setScreen(new com.mygdx.game.sasha.screen.KeySettingScreen(game, //manager,
//				sasha));

		if(game instanceof AndroidGamepadTool){
			game.setScreen(new ControllerMappingScreen((AndroidGamepadTool) game));
		}else{
			game.setScreen(new SimulateScreen(game));
		}

		dispose();

	}
	private  void goToGamepadToKeyboardSettingPage() {

		game.setScreen(new KeySettingScreen(game));

		dispose();

	}

	private  void goToGamepadDeadZoneSettingPage() {

		game.setScreen(new GamepadSettingScreen(game));

		dispose();

	}

//	private void goToKillBadPage() {
//		//game.setScreen(new KillBadScreen(game));
//		dispose();
//
//	}
//
//	private void goToGuidePage() {
//		Guide1Screen g1 = new Guide1Screen(game, sasha);
//		// g1.setLastScreen(this);
//		game.setScreen(g1);
//
//		dispose();
//
//	}
//
//	private void goToGameD3Page() {
//
////		AssetManager manager = com.mygdx.game.sasha.screen.GameScreen.getAssetsManager();
////		GameScreenD3.loadAssets(manager);
////		SashaActor.loadAssets(manager);
////		SashaActorD3.loadAssets(manager);
////		AliceActor.loadAssets(manager);
////		manager.load("cardGame/carddeck.atlas", TextureAtlas.class);
////		manager.update();
////		manager.finishLoading();
////
////		Screen g1 = new GameScreenD3(game, sasha.getCharacter(),manager);
////		// g1.setLastScreen(this);
////		game.setScreen(g1);
////
////		dispose();
//
//		game.setScreen(new com.mygdx.game.sasha.bulletD3.SelectCharacterScreenD3(game, //manager,
//				sasha));
//		dispose();
//
//	}
//
//
//	private  void goToKofCharacterPage() {
//
//		game.setScreen(new com.mygdx.game.sasha.screen.kof.SelectCharacterScreen(game, //manager,
//				sasha));
//		dispose();
//
//	}
//
//	private  void goToRhythmPage() {
//
//		AssetManager manager = new EncryptAssetManager();
//		RhythmScreen.loadAssets(manager);
//		manager.update();
//		manager.finishLoading();
//
//		ISGPS5Gamepad pad=null;
//		for (Controller controller : Controllers.getControllers()) {
//			pad=new SGPS5Gamepad(controller);
//			break;
//		}
//
//		if(null==pad&&game.hasKeyboard){
//			pad=new SGKeyboardGamepad();
//		}
//		if(null==pad&&game.showTouchpad){
//			pad=new SGTouchGamepad();
//		}
//		SGLibGdxHelper.getGamepad();
//		game.setScreen(new RhythmScreen(game,manager,RhythmScreen.getGameKeyByGamepad(pad)));
//		dispose();
//
//	}
//	private  void goToKeySettingPage() {
//
//		game.setScreen(new com.mygdx.game.sasha.screen.KeySettingScreen(game, //manager,
//				sasha));
//		dispose();
//
//	}
	private void exitGame() {

//		screen.dispose();
////		PauseScreen.this.dispose();
		dispose();
		//game.dispose();
		Gdx.app.exit();
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

		stage.dispose();
		skin.dispose();

		if(null!=skinLibgdx){ skinLibgdx.dispose();}
		//System.out.println(this.getClass().getSimpleName()+" dispose");
	}

	// ...Rest of class omitted for succinctness.


//	private  void initLibGdx(){
//		Gdx.app.setLogLevel(Application.LOG_DEBUG);
////		SGDataHelper.getLog().print("开始运行: "+SGDate.Now());
//	}
//
//	public void testEmailSend() throws InterruptedException {
//
//		String title = "测试发邮件20230902_1_" + SGDate.Now();
//
//		String[] emails = new String[] { "li@sellgirl.com" };
//		SGEmailSend.SendMail(emails, title, title);
//		Thread.sleep(2000);
//		// System.out.println("测试通过");
//	}
//
//
//
//
//	public class MyTextInputListener implements TextInputListener {
//		@Override
//		public void input(String text) {
//			System.out.println(text);
//		}
//
//		@Override
//		public void canceled() {
//		}
//	}


	public static TextButtonStyle getButtonStyle(Skin skin) {

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);//DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		return textButtonStyle;
	}

	public static LabelStyle getLabelStyle(Skin skin) {

		LabelStyle textButtonStyle = new LabelStyle();
		textButtonStyle.font = skin.getFont("default");
		// textButtonStyle.fontColor = Color.BLACK;
		return textButtonStyle;
	}

	public static TextFieldStyle getTextFieldStyle(Skin skin) {

		TextFieldStyle textButtonStyle = new TextFieldStyle();
		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.fontColor = Color.BLACK;
		textButtonStyle.background = skin.newDrawable("white", Color.YELLOW);
		return textButtonStyle;
	}

	public static WindowStyle getWindowStyle(Skin skin) {

		WindowStyle textButtonStyle = new WindowStyle();
//		textButtonStyle.font = skin.getFont("default");
//		textButtonStyle.fontColor = Color.BLACK;
		textButtonStyle.titleFont = skin.getFont("default");
		// textButtonStyle.titleFontColor = Color.BLACK;
		Drawable tmp= skin.newDrawable("white", Color.GRAY);
//		tmp.setMinWidth(500);
//		tmp.setMinHeight(500);
//		textButtonStyle.background = skin.newDrawable("white", Color.GRAY);
		textButtonStyle.background = tmp;
		//textButtonStyle.titleFontColor=Color.WHITE;
		return textButtonStyle;
	}

	public static TextButtonStyle getTextButtonStyle(Skin skin) {

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.fontColor = Color.BLACK;
		// textButtonStyle.background = skin.newDrawable("white", Color.YELLOW);
		return textButtonStyle;
	}
	public static CheckBox.CheckBoxStyle getCheckBoxStyle(Skin skin) {

		CheckBox.CheckBoxStyle textButtonStyle = new CheckBox.CheckBoxStyle();
		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.fontColor = Color.WHITE;
		int w=10;
		int w2=10;
		Drawable tmp=skin.newDrawable("white", Color.LIGHT_GRAY);
		tmp.setMinWidth(w);
		tmp.setMinHeight(w);
//		textButtonStyle.checkboxOn = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checkboxOn = tmp;
		Drawable tmp2=skin.newDrawable("white", Color.DARK_GRAY);
		tmp2.setMinWidth(w2);
		tmp2.setMinHeight(w2);
//		textButtonStyle.checkboxOff = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.checkboxOff = tmp2;
		// textButtonStyle.background = skin.newDrawable("white", Color.YELLOW);
		return textButtonStyle;
	}
	public static Slider.SliderStyle getSliderStyle(Skin skin) {

		Slider.SliderStyle textButtonStyle = new Slider.SliderStyle();
		Drawable tmp2=skin.newDrawable("white", Color.YELLOW);
		tmp2.setMinHeight(3);
		 textButtonStyle.background = tmp2;
		 Drawable tmp=skin.newDrawable("white", Color.RED);
//		 int border=4;
//		 tmp.setTopHeight(border);
//		 tmp.setBottomHeight(border);
//		tmp.setLeftWidth(border);
//		tmp.setRightWidth(border);
		 tmp.setMinWidth(6);
		 tmp.setMinHeight(14);
		textButtonStyle.knob=tmp;
		 return textButtonStyle;
	}

	public static List.ListStyle getListStyle(Skin skin) {

		List.ListStyle textButtonStyle = new List.ListStyle();
//		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);//DARK_GRAY);
//		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
//		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
//		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		Drawable tmp2=skin.newDrawable("white", Color.PURPLE);
		tmp2.setMinHeight(3);
		textButtonStyle.selection = tmp2;
		Drawable tmp1=skin.newDrawable("white", Color.BLUE);
		tmp1.setMinHeight(3);
		textButtonStyle.background = tmp1;//非必需,但设置了可以挡住后面
		return textButtonStyle;
	}
	public static ScrollPane.ScrollPaneStyle getScrollStyle(Skin skin) {

		ScrollPane.ScrollPaneStyle textButtonStyle = new ScrollPane.ScrollPaneStyle();
//		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);//DARK_GRAY);
//		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
//		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
//		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
//		textButtonStyle.font = skin.getFont("default");
//		Drawable tmp2=skin.newDrawable("white", Color.YELLOW);
//		tmp2.setMinHeight(3);
//		textButtonStyle.selection = tmp2;
		return textButtonStyle;
	}
	public static SelectBox.SelectBoxStyle getSelectBoxStyle(Skin skin) {

		SelectBox.SelectBoxStyle textButtonStyle = new SelectBox.SelectBoxStyle();
//		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);//DARK_GRAY);
//		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
//		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
//		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.listStyle=getListStyle(skin);
		textButtonStyle.scrollStyle=getScrollStyle(skin);
		return textButtonStyle;
	}

	public static ProgressBar.ProgressBarStyle getProgressBarStyle(Skin skin) {

		ProgressBar.ProgressBarStyle textButtonStyle = new ProgressBar.ProgressBarStyle();
		Drawable tmp2=skin.newDrawable("white", Color.YELLOW);
		tmp2.setMinHeight(3);
//		textButtonStyle.background = tmp2;
		Drawable tmp=skin.newDrawable("white", Color.RED);
//		 int border=4;
//		 tmp.setTopHeight(border);
//		 tmp.setBottomHeight(border);
//		tmp.setLeftWidth(border);
//		tmp.setRightWidth(border);
		tmp.setMinWidth(6);
		tmp.setMinHeight(14);
		textButtonStyle.knob=tmp;
		Drawable tmp1=skin.newDrawable("white", Color.BLUE);
		tmp1.setMinHeight(3);
		textButtonStyle.background = tmp1;//非必需,但设置了可以挡住后面
		return textButtonStyle;
	}
	public static ImageButton.ImageButtonStyle getImageButtonStyle(Skin skin) {

		ImageButton.ImageButtonStyle textButtonStyle = new ImageButton.ImageButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);//DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		// textButtonStyle.background = skin.newDrawable("white", Color.YELLOW);
		return textButtonStyle;
	}
//	/**
//	 * 风格为:
//	 * 白色字
//	 * 已启用: 白色
//	 * 未启用: 深色
//	 * @return
//	 */
//	public static Skin getSkin() {
//		// A skin can be loaded via JSON or defined programmatically, either is fine.
//		// Using a skin is optional but strongly
//		// recommended solely for the convenience of getting a texture, region, etc as a
//		// drawable, tinted drawable, etc.
//		Skin skin = new Skin();
//
//		// Generate a 1x1 white texture and store it in the skin named "white".
//		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
//		pixmap.setColor(Color.WHITE);
//		pixmap.fill();
//		skin.add("white", new Texture(pixmap));
//
////		Pixmap pixmap2 = new Pixmap(5, 5, Format.RGBA8888);
////		pixmap2.setColor(Color.WHITE);
////		pixmap2.fill();
////		skin.add("white5", new Texture(pixmap2));
//
//		// Store the default libGDX font under the name "default".
//		// skin.add("default", new BitmapFont());
////		/**
////		 * BitmapFont的初始化。
////		 * 3个参数分别为:fontFile(字体文件)、imageFile(所对应的png文件)、是否翻转
////		 */
//		// skin.add("default", new BitmapFont(Gdx.files.internal("sasha_font.fnt"),
//		// Gdx.files.internal("sasha_font.png"), false));
//
//		skin.add("default", getFont2());
//		return skin;
//	}

	/**
	 * 一套的样式，原本没有中文字
	 * @param font
	 * @return
	 */
	public static Skin getSkin2(BitmapFont font) {
		Skin skin = new Skin(Gdx.files.internal(com.sellgirl.gamepadtool.util.Constants.SKIN_LIBGDX_UI), new com.badlogic.gdx.graphics.g2d.TextureAtlas(com.sellgirl.gamepadtool.util.Constants.TEXTURE_ATLAS_LIBGDX_UI));
		//为了中文字体
		font.setColor(Color.BLACK);
		//font.setColor(0,0,0,1);
		skin.get(TextButton.TextButtonStyle.class).font=font;
		skin.get(Label.LabelStyle.class).font=font;
		skin.get(SelectBox.SelectBoxStyle.class).font=font;
		skin.add("default", font);
////		skin.get(SelectBox.SelectBoxStyle.class).font=font;//
////		skin.get(List.ListStyle.class).font=font;//
//		skin.get(SelectBox.SelectBoxStyle.class).listStyle.font=font;
////		skin.add("default", MainMenuScreen.getSelectBoxStyle(skin));//没有这句的话，下拉菜单的字显示不出来--benjamin20250911
//		skin.get(CheckBox.CheckBoxStyle.class).font=font;//没这句显示不了中文字
////		skin.add("default", MainMenuScreen.getCheckBoxStyle(skin));//没有这句的话，checkbox的文字显示不出来
////		skin.get(ScrollPane.ScrollPaneStyle.class).font=font;
////		skin.add("default", MainMenuScreen.getScrollStyle(skin));
//		skin.add("default", MainMenuScreen.getImageButtonStyle(skin));
		return skin;
	}

//	private static FreeTypeFontGenerator generator;// TTF字体发生器
//	private static FreeTypeBitmapFontData fontData;// 负责处理FreeTypeFontGenerator的数据.可以简单地理解成为一个加工好的字符库
	//private static BitmapFont font;// 要现实的内容
//	//private static SpriteBatch batch;
//
//	public static BitmapFont getFont2() {
//		/**
//		 * 以下是进行初始化
//		 */
//		generator = new FreeTypeFontGenerator(Gdx.files.internal("simhei.ttf"));
//
//		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//		FileHandle file = Gdx.files.internal("font_cn.txt");
//		String text = file.readString();
//
//		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS
//				+"△○□↑→↓←↖↗↘↙∞"
//				+ text;
//
//		fontData = generator.generateData(parameter);
//
//		font = new BitmapFont(fontData, fontData.regions, false);
//		font.setColor(Color.WHITE);
//		generator.dispose();
//		return font;
//	}
//	/**
//	 * @return
//	 */
//	public static BitmapFont getFont2() {
////		if(null!=font) {return font;}
//		if(null==generator) {
//
//			/**
//			 * 以下是进行初始化
//			 */
//			generator = new FreeTypeFontGenerator(Gdx.files.internal("simhei.ttf"));//generator这个东西就算dispose也没用的,内存增加9MB,没办法了
//		}
//
//		if(null==fontData) {//测试发现fontData就算dispose了,还是消耗内存,原因不明
//			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//			FileHandle file = Gdx.files.internal("font_cn.txt");
//			String text = file.readString(SGDataHelper.encoding);
////			if(GameSetting.isGameX()) {
////				FileHandle filex = Gdx.files.internal("txtx/font_cn.txt");
////				try {
////					//text += AES.AESDecryptDemo(filex.readString(), SGDataHelper.decodeBase64(key)) ;
////					File filexF=filex.file();
////
////					try(FileInputStream fs=new FileInputStream(filexF);) {
////						String text2 = SGEncryptByte.DecryptByteToString(fs,SGEncryptByte.DEFAULT_BUFFER_SIZE, Integer.decode(SGDataHelper.decodeBase64(key2)));
////						text=text+text2;
////					}catch(Exception e) {}
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
////			}
//
//			parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS
//					+"△○□↑→↓←↖↗↘↙∞"
//					+ text;
//
//			parameter.size=24;
//			fontData = generator.generateData(parameter);
//		}
//
//		BitmapFont font = new BitmapFont(fontData, fontData.regions, false);
//		font.setColor(Color.WHITE);
////		generator.dispose();
////		fontData.dispose();
//		return font;
//	}
//	public static BitmapFont getFont3(AssetManager manager) {
//		/**
//		 * 以下是进行初始化
//		 */
//		//FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("simhei.ttf"));
//		FreeTypeFontGenerator generator=manager.get("simhei.ttf",FreeTypeFontGenerator.class);//AssetManager这样加载可以,但dispose时会报fatal错误
//
//		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//		FileHandle file = Gdx.files.internal("font_cn.txt");
//		String text = file.readString();
//
//		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS
//				+"△○□↑→↓←↖↗↘↙∞"
//				+ text;
//
//		FreeTypeBitmapFontData fontData = generator.generateData(parameter);
//
//		BitmapFont font = new BitmapFont(fontData, fontData.regions, false);
//		font.setColor(Color.WHITE);
//		generator.dispose();
//		return font;
//	}
//
//	public static BitmapFont getFont4(AssetManager manager) {
////		/**
////		 * 以下是进行初始化
////		 */
////		//FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("simhei.ttf"));
////		//FreeTypeFontGenerator generator=manager.get("simhei.ttf",FreeTypeFontGenerator.class);
////
////		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
////		FileHandle file = Gdx.files.internal("font_cn.txt");
////		String text = file.readString();
////
////		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS
////				+"△○□↑→↓←↖↗↘↙∞"
////				+ text;
////
////		FreeTypeBitmapFontData fontData = generator.generateData(parameter);
////
////		BitmapFont font = new BitmapFont(fontData, fontData.regions, false);
////		font.setColor(Color.WHITE);
////		generator.dispose();
//		BitmapFont font=manager.get("simhei.ttf",BitmapFont.class);
//		return font;
//	}

	/*------------------游戏设置相关------------------*/
	private final Skin skinLibgdx;
	private Window winOptions;
	private TextButton btnWinOptSave;
	private TextButton btnWinOptCancel;
	private CheckBox chkSound;
	private Slider sldSound;
	private CheckBox chkMusic;
	private CheckBox chkOrthographic;
	private CheckBox chkKofOrthographic;

	private Slider sldMusic;
//	private SelectBox<CharacterSkin> selCharSkin;
//	private Image imgCharSkin;
	private CheckBox chkShowFpsCounter;
	private CheckBox chkShowFollowPath;
	private final boolean debugEnabled = false;
	private Skin getSkinLibgdx(){
		return null==skinLibgdx?skin:skinLibgdx;
	}
	private String getFontName(){
		return null==skinLibgdx?"default":"default-font";
	}
	private Table buildOptWinAudioSettings() {
		Table tbl = new Table();
		// + Title: "Audio"
		tbl.pad(10, 10, 0, 10);
		//tbl.add(new Label("Audio", skinLibgdx, "default", Color.ORANGE)).colspan(3);
		tbl.add(new Label("Audio", getSkinLibgdx(), getFontName(), Color.ORANGE)).colspan(3);
		tbl.row();
		tbl.columnDefaults(0).padRight(10);
		tbl.columnDefaults(1).padRight(10);
		// + Checkbox, "Sound" label, sound volume slider
		chkSound = new CheckBox("", getSkinLibgdx());
		tbl.add(chkSound);
		tbl.add(new Label("Sound", getSkinLibgdx()));
		sldSound = new Slider(0.0f, 1.0f, 0.1f, false, getSkinLibgdx());
		tbl.add(sldSound);
		tbl.row();
		// + Checkbox, "Music" label, music volume slider
		chkMusic = new CheckBox("", getSkinLibgdx());
		tbl.add(chkMusic);
		tbl.add(new Label("Music", getSkinLibgdx()));
		sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, getSkinLibgdx());
		tbl.add(sldMusic);
		tbl.row();
		return tbl;
	}
	//我加的 --benjamin
	private Table buildOptWinVideoSettings() {
		Table tbl = new Table();
		// + Title: "Audio"
		tbl.pad(10, 10, 0, 10);
		//tbl.add(new Label("Audio", skinLibgdx, "default", Color.ORANGE)).colspan(3);
		tbl.add(new Label("Video", getSkinLibgdx(), getFontName(), Color.ORANGE)).colspan(3);
		tbl.row();
		tbl.columnDefaults(0).padRight(10);
		tbl.columnDefaults(1).padRight(10);
		// + Checkbox, "Sound" label, sound volume slider
//		chkSound = new CheckBox("", getSkinLibgdx());
		chkOrthographic = new CheckBox("", getSkinLibgdx());
		tbl.add(new Label("Orthographic", getSkinLibgdx()));
		tbl.add(chkOrthographic);
//		sldSound = new Slider(0.0f, 1.0f, 0.1f, false, getSkinLibgdx());
//		tbl.add(sldSound);
		tbl.row();
		chkKofOrthographic = new CheckBox("", getSkinLibgdx());
		tbl.add(new Label("Kof Orthographic", getSkinLibgdx()));
		tbl.add(chkKofOrthographic);
//		sldSound = new Slider(0.0f, 1.0f, 0.1f, false, getSkinLibgdx());
//		tbl.add(sldSound);
		tbl.row();
		return tbl;
	}



	private Table buildOptWinDebug() {
		Table tbl = new Table();
		// + Title: "Debug"
		tbl.pad(10, 10, 0, 10);
//		tbl.add(new Label("Debug", skinLibgdx, "default-font", Color.RED)).colspan(3);
		tbl.add(new Label("Debug", getSkinLibgdx(),  getFontName(), Color.RED)).colspan(3);
		tbl.row();
		tbl.columnDefaults(0).padRight(10);
		tbl.columnDefaults(1).padRight(10);
		// + Checkbox, "Show FPS Counter" label
		chkShowFpsCounter = new CheckBox("", getSkinLibgdx());
		tbl.add(new Label("Show FPS Counter", getSkinLibgdx()));
		tbl.add(chkShowFpsCounter);
		tbl.row();

		chkShowFollowPath = new CheckBox("", getSkinLibgdx());
		tbl.add(new Label("Show Follow Path", getSkinLibgdx()));
		tbl.add(chkShowFollowPath);
		tbl.row();
		return tbl;
	}

	private Table buildOptWinButtons() {
		Table tbl = new Table();
		// + Separator
		Label lbl;
		lbl = new Label("", getSkinLibgdx());
		lbl.setColor(0.75f, 0.75f, 0.75f, 1);
		lbl.setStyle(new LabelStyle(lbl.getStyle()));
		lbl.getStyle().background = getSkinLibgdx().newDrawable("white");
		tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 0, 0, 1);
		tbl.row();
		lbl = new Label("", getSkinLibgdx());
		lbl.setColor(0.5f, 0.5f, 0.5f, 1);
		lbl.setStyle(new LabelStyle(lbl.getStyle()));
		lbl.getStyle().background = getSkinLibgdx().newDrawable("white");
		tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 1, 5, 0);
		tbl.row();
		// + Save Button with event handler
		btnWinOptSave = new TextButton("Save", getSkinLibgdx());
		tbl.add(btnWinOptSave).padRight(30);
		btnWinOptSave.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onSaveClicked();
			}
		});
		// + Cancel Button with event handler
		btnWinOptCancel = new TextButton("Cancel", getSkinLibgdx());
		tbl.add(btnWinOptCancel);
		btnWinOptCancel.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onCancelClicked();
			}
		});
		return tbl;
	}
	private Table buildOptionsWindowLayer() {
		winOptions = new Window("Options",getSkinLibgdx());
		// + Audio Settings: Sound/Music CheckBox and Volume Slider
		winOptions.add(buildOptWinAudioSettings()).row();
		winOptions.add(buildOptWinVideoSettings()).row();
//		// + Character Skin: Selection Box (White, Gray, Brown)
//		winOptions.add(buildOptWinSkinSelection()).row();
		// + Debug: Show FPS Counter
		winOptions.add(buildOptWinDebug()).row();
		// + Separator and Buttons (Save, Cancel)
		winOptions.add(buildOptWinButtons()).pad(10, 0, 10, 0);

		// Make options window slightly transparent
		winOptions.setColor(1, 1, 1, 0.8f);
		// Hide options window by default
		showOptionsWindow(false, false);
		if (debugEnabled)
			winOptions.debug();
		// Let TableLayout recalculate widget sizes and positions
		winOptions.pack();
		// Move options window to bottom right corner
//		winOptions.setPosition(Constants.VIEWPORT_GUI_WIDTH - winOptions.getWidth() - 50, 50);
		winOptions.setPosition(ScreenSetting.WORLD_WIDTH - winOptions.getWidth() - 50, 50);
		return winOptions;
	}
	private void onOptionsClicked() {
		loadSettings();
		//showMenuButtons(false);
		showOptionsWindow(true, true);
	}
	private void onSaveClicked() {
//		saveSettings();
//		onCancelClicked();
//		AudioManager.instance.onSettingsUpdated();
	}

	private void onCancelClicked() {
//		//showMenuButtons(true);
//		showOptionsWindow(false, true);
//		AudioManager.instance.onSettingsUpdated();
	}
	private void loadSettings() {
//		GamePreferences prefs = GamePreferences.instance;
//		prefs.load();
//		chkSound.setChecked(prefs.sound);
//		sldSound.setValue(prefs.volSound);
//		chkMusic.setChecked(prefs.music);
//		sldMusic.setValue(prefs.volMusic);
//		chkOrthographic.setChecked(prefs.orthographic);
//		chkKofOrthographic.setChecked(prefs.kofOrthographic);
////		selCharSkin.setSelectedIndex(prefs.charSkin);
////		onCharSkinSelected(prefs.charSkin);
//		chkShowFpsCounter.setChecked(prefs.showFpsCounter);
//		chkShowFollowPath.setChecked(prefs.showFollowPath);
	}
//	private void saveSettings() {
//		GamePreferences prefs = GamePreferences.instance;
//		prefs.sound = chkSound.isChecked();
//		prefs.volSound = sldSound.getValue();
//		prefs.music = chkMusic.isChecked();
//		prefs.volMusic = sldMusic.getValue();
//		prefs.orthographic = chkOrthographic.isChecked();
//		prefs.kofOrthographic = chkKofOrthographic.isChecked();
////		prefs.charSkin = selCharSkin.getSelectedIndex();
//		prefs.showFpsCounter = chkShowFpsCounter.isChecked();
//		prefs.showFollowPath = chkShowFollowPath.isChecked();
//		prefs.save();
//	}
	private void showOptionsWindow(boolean visible, boolean animated) {
		float alphaTo = visible ? 0.8f : 0.0f;
		float duration = animated ? 1.0f : 0.0f;
		Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;
		winOptions.addAction(sequence(
				touchable(touchEnabled),
				alpha(alphaTo, duration)));
	}
	/*------------------游戏设置相关 end------------------*/

	/*------------------apk自动更新------------------*/
//	private SGFileDownloader jarDownloader=null;
	private ProgressBar jarPB=null;
	private String gameVersion="1.0.0";
	private void downloadJar(
			boolean forceUpdate
	) {
		if(null!=game.getJarDownloader()){return;}
		game.setJarDownloader(new SGFileDownloader());
		switch (Gdx.app.getType()){
			case Android:
				game.getJarDownloader().download(Constants.APK_URL,Gdx.files.external( Constants.EXTERNAL_APK_FILE),forceUpdate);
				break;
			case Desktop:
				game.getJarDownloader().download(Constants.JAR_URL,Gdx.files.local( Constants.LOCAL_JAR_FILE),forceUpdate);
				break;
			default:
				break;
		}
	}
	public static void getLastVersion(SGAction1<String> action){
		switch (Gdx.app.getType()){
			case Android:
				SGLibGdxHelper.getHttpStringAsync(Constants.LAST_APK_VERSION_URL,action);
				break;
			case Desktop:
				SGLibGdxHelper.getHttpStringAsync(Constants.LAST_JAR_VERSION_URL,action);
				break;
			default:
				break;
		}
	}
	/*------------------apk自动更新 end------------------*/
}
