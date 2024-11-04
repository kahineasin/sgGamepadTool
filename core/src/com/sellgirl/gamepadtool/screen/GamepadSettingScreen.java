package com.sellgirl.gamepadtool.screen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.touchable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.sellgirl.gamepadtool.GamepadTool;
import com.sellgirl.gamepadtool.MainMenuScreen;
import com.sellgirl.gamepadtool.ScreenSetting;
import com.sellgirl.gamepadtool.language.TXT;
import com.sellgirl.gamepadtool.util.LocalSaveSettingHelper3;
import com.sellgirl.sgGameHelper.SGLibGdxHelper;
import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
import com.sellgirl.sgGameHelper.gamepad.SGKeyboardGamepad;
import com.sellgirl.sgGameHelper.gamepad.SGPS5Gamepad;
import com.sellgirl.sgGameHelper.gamepad.SGPS5GamepadSetting;
import com.sellgirl.sgGameHelper.tabUi.TabUi;
import com.sellgirl.sgJavaHelper.SGAction;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//import com.sellgirl.sgJavaHelper.config.SGDataHelper;

public class GamepadSettingScreen implements Screen {

	private Stage stage;
	private Table table;
	final GamepadTool game;
	SpriteBatch batch;

//	// OrthographicCamera camera;
//	@Deprecated
//	Controller controller;
	ISGPS5Gamepad sgcontroller;
//	IApiUrl url = null;
	//SashaData sasha;
//	SashaData previewSasha;
	// Label dataLbl;
	public Skin skin;
//	String code = "";
//
//	TextField text1;

	private Label axisLeftLbl;
	TextField axisLeftX1TF;
	TextField axisLeftX2TF;
	TextField axisLeftY1TF;
	TextField axisLeftY2TF;
	TextButton axisLeftAutoBtn;
	boolean axisLeftCalculating=false;
	float axisLeftCalculatingTime=0;
	private SGPS5Gamepad.AxisSpace axisLeftTmp=new SGPS5Gamepad.AxisSpace();

	private Label axisRightLbl;
	TextField axisRightX1TF;
	TextField axisRightX2TF;
	TextField axisRightY1TF;
	TextField axisRightY2TF;
	TextButton axisRightAutoBtn;
	boolean axisRightCalculating=false;
	float axisRightCalculatingTime=0;
	private SGPS5Gamepad.AxisSpace axisRightTmp=new SGPS5Gamepad.AxisSpace();

	private Label axisL2Lbl;
	private Label axisR2Lbl;
	TextField axisL2TF;
	TextField axisR2TF;
	TextButton axisL2AutoBtn;
	TextButton axisR2AutoBtn;
	boolean axisL2Calculating=false;
	boolean axisR2Calculating=false;
	float axisL2CalculatingTime=0;
	float axisR2CalculatingTime=0;
	private float axisL2Tmp=0.1f;
	private float axisR2Tmp=0.1f;

	Label saveResultLbl;
	String tmpEmail;
	// TextureRegion paycode;

	// public PurchaseManager wechatPurchaseManager;
	float xWait = 4;
	float oWait = 1;
	float squartWait = 1;

	private float buttonWait=0.4f;
	private float buttonWaitCount=0.4f;
//	private KofGameScreen screen = null;
//	private SGCharacter currentCharacter = SGCharacter.GODDESSPRINCESSSASHA;

//	 TextButton sashaBtn ;
//	 TextButton aliceBtn ;

//	private float nextWait = 0;
//	private int step = 1;
//
//	private float actorBeginX;
//	TextButton goToGameBtn;
//	TextButton goToGameD3Btn;
	// AssetManager manager;
	// private Screen lastScreen = null;
	ArrayList<ISGPS5Gamepad> pads = new ArrayList<ISGPS5Gamepad>();
//	ArrayList<KofPlayer> players = new ArrayList<KofPlayer>();
//	ArrayList<KofPlayer> pcplayers = new ArrayList<KofPlayer>();
	//ArrayList<Label> playerlbls = new ArrayList<Label>();
//	ArrayList<TextButton> keySettingLbls = null;
	HashMap<String,Label> keySettingLbls = null;
	Label settingTypeLbl=null;
	IKnightSashaGameKey gameKey=null;
	HashMap<String,Integer> gameKeyMap=null;
	/**
	 * 当前正在设定的手柄
	 */
	ISGPS5Gamepad gamepad;
	boolean settingDefault=true;
//	KeySettingDialog dialog;
	TabUi tabUi;
//
//	public SelectCharacterScreen(final SashaGame game, final SashaData sasha
//			) {

	//LocalSaveSettingHelper2 localSaveSettingHelper2=null;
	LocalSaveSettingHelper3 localSaveSettingHelper3=null;
	public void loadGamepadSetting(ISGPS5Gamepad gamepad){

//		gameKey=localSaveSettingHelper2.readGameKey(gamepad.getPadUniqueName(),SGKeyboardGamepad.class==gamepad.getClass()?new GameKeyKeyboard():new GameKey());
//		if(null==gameKey){
//			gameKey= SGKeyboardGamepad.class==gamepad.getClass()?new GameKeyKeyboard(): new GameKey();
//			settingDefault=true;
//		}else{
//			if(gameKey.getClass()!=(SGKeyboardGamepad.class==gamepad.getClass()?GameKeyKeyboard.class: GameKey.class)){
//				gameKey=SGKeyboardGamepad.class==gamepad.getClass()?new GameKeyKeyboard(): new GameKey();
//			}
//			settingDefault=false;
//		}
//		if(null==gameKeyMap){
////			gameKeyMap=new HashMap<>();
//			gameKeyMap=new LinkedHashMap<>();
//		}
//		gameKeyMap=gameKey.toMap(gameKeyMap);
		this.gamepad=gamepad;


		if(SGPS5Gamepad.class==gamepad.getClass()){
			SGPS5Gamepad tmp=(SGPS5Gamepad) gamepad;
			tmp.loadSetting();

			settingDefault=tmp.isDefaultSetting();

//			SGPS5GamepadSetting setting= localSaveSettingHelper3.readGameKey(gamepad.getPadUniqueName(),new SGPS5GamepadSetting());
//			if(null!=setting){
//				tmp.setAxisLeftSpace(setting.getAxisLeftSpace().x1,setting.getAxisLeftSpace().x2,setting.getAxisLeftSpace().y1,setting.getAxisLeftSpace().y2);
//				tmp.setAxisRightSpace(setting.getAxisRightSpace().x1,setting.getAxisRightSpace().x2,setting.getAxisRightSpace().y1,setting.getAxisRightSpace().y2);
//			}else{
//				tmp.setAxisLeftSpace=new AxisSpace();
//				axisRightSpace=new AxisSpace();
//			}

			axisLeftTmp.x1=tmp.getAxisLeftSpace().x1;
			axisLeftTmp.x2=tmp.getAxisLeftSpace().x2;
			axisLeftTmp.y1=tmp.getAxisLeftSpace().y1;
			axisLeftTmp.y2=tmp.getAxisLeftSpace().y2;
			axisRightTmp.x1=tmp.getAxisRightSpace().x1;
			axisRightTmp.x2=tmp.getAxisRightSpace().x2;
			axisRightTmp.y1=tmp.getAxisRightSpace().y1;
			axisRightTmp.y2=tmp.getAxisRightSpace().y2;
			axisL2Tmp=tmp.getL2Space();
			axisR2Tmp=tmp.getR2Space();
		}else{

			axisLeftTmp.x1=0;
			axisLeftTmp.x2=0;
			axisLeftTmp.y1=0;
			axisLeftTmp.y2=0;
			axisRightTmp.x1=0;
			axisRightTmp.x2=0;
			axisRightTmp.y1=0;
			axisRightTmp.y2=0;
			axisL2Tmp=0f;
			axisR2Tmp=0f;
		}

//		if(SGPS5Gamepad.class==gamepad.getClass()){
//			SGPS5Gamepad tmp=(SGPS5Gamepad) gamepad;
//		}else{
//
//		}
//		settingDefault=-0.1f==axisLeftTmp.x1&&0.1f==axisLeftTmp.x2&&-0.1f==axisLeftTmp.y1&&0.1f==axisLeftTmp.y2
//		&&-0.1f==axisRightTmp.x1&&0.1f==axisRightTmp.x2&&-0.1f==axisRightTmp.y1&&0.1f==axisRightTmp.y2
//		;
	}
	public void updateUIAfterChangeGamepadSetting(){

		if(null!=saveResultLbl){
			saveResultLbl.setText("");}
//		if(null!=keySettingLbls){
//
//			for (Map.Entry<String, Integer> m1 : gameKeyMap.entrySet()) {
////				keySettingLbls.get(m1.getKey()).setText(XBoxKey.getTexts(m1.getValue()));
//				keySettingLbls.get(m1.getKey()).setText(gameKey.getKeyNamesByMask(m1.getValue()) );
//			}
//		}

		int pow=3;
		axisLeftX1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.x1,3)));
		axisLeftX2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.x2,3)));
		axisLeftY1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.y1,3)));
		axisLeftY2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.y2,3)));
		axisRightX1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.x1,3)));
		axisRightX2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.x2,3)));
		axisRightY1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.y1,3)));
		axisRightY2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.y2,3)));

		axisL2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisL2Tmp,pow)));
		axisR2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisR2Tmp,pow)));
		if(null!=settingTypeLbl){
			if(settingDefault){
				settingTypeLbl.setText("当前为默认配置");
			}else{
				settingTypeLbl.setText("当前为自定义配置");
			}
		}
	}
	public GamepadSettingScreen(final GamepadTool game//, SashaData sasha
			) {
//		localSaveSettingHelper2=new LocalSaveSettingHelper2();
		localSaveSettingHelper3=new LocalSaveSettingHelper3();
		this.game = game;

//		if (sasha.getKill() > 0) {
//			xWait = 1;
//		}

		batch = new SpriteBatch();

//		skin = MainMenuScreen.getSkin();
//		skin.add("default", MainMenuScreen.getButtonStyle(skin));
//		skin.add("default", MainMenuScreen.getLabelStyle(skin));
//		skin.add("default", MainMenuScreen.getTextFieldStyle(skin));
//		skin.add("default", MainMenuScreen.getWindowStyle(skin));
////		skin.add("default", MainMenuScreen.getListStyle(skin));
//		skin.add("default", MainMenuScreen.getSelectBoxStyle(skin));

		skin=MainMenuScreen.getSkin2(game.font);

//		for (Controller controller : Controllers.getControllers()) {
//			if (SGControllerName.XInputController.equals(controller.getName())) {
//				this.controller = controller;
//				this.controller.addListener(new SGXInputControllerListener());
//			}
//
//		}

//		this.controller = SGLibGdxHelper.getGamepad();
//		if (null != controller) {
//			sgcontroller=new SGPS5Gamepad(controller);
//			this.controller.addListener(new SGXInputControllerListener());
//		}
//
//		players = new ArrayList<KofPlayer>(pads.size() + 1);
		int i = 0;
		for (Controller controller : Controllers.getControllers()) {
			pads.add(new SGPS5Gamepad(controller));
//			KofPlayer player = new KofPlayer();
//			player.setPlayName("p" + (1 + i));
//			players.add(player);
			i++;
		}
//		if(game.hasKeyboard){
//			pads.add(new SGKeyboardGamepad());
//			i++;
//		}
//		if(game.showTouchpad){
//			pads.add(new SGTouchGamepad());
//			i++;
//		}
		if(0<i){
			ok=true;
		}else{
			return;
		}
		gamepad=pads.get(0);
//		this.sgcontroller = pads.get(0);//主控
		this.sgcontroller = SGLibGdxHelper.getSGGamepad();

//		players.get(0).setTeam(1);
//		players.get(0).setCharacter(SGCharacter.GODDESSPRINCESSSASHA);
//
//		//默认p2为敌人
//		if (players.size() > 1) {
//			players.get(1).setTeam(2);
//			//players.get(1).setCharacter(SGCharacter.ALICE);
//			players.get(1).setCharacter(SGCharacter.GODDESSPRINCESSSASHA);
//		}

//		//默认pc为敌人
//		KofPlayer tmpPcPlayer = new KofPlayer();
//		tmpPcPlayer.setPlayName("pc" + (1 + pcCount));
//		tmpPcPlayer.setPC(true);
//		tmpPcPlayer.setTeam(2);
////		tmpPcPlayer.setCharacter(SGCharacter.GODDESSPRINCESSSASHA);
//		tmpPcPlayer.setCharacter(SGCharacter.ALICE);
//		this.pcCount++;
//		pcplayers.add(tmpPcPlayer);


//		if (players.size()+pcplayers.size() < 2) {
//			KofPlayer tmpPcPlayer = new KofPlayer();
//			tmpPcPlayer.setPlayName("pc" + (1 + pcCount));
//			tmpPcPlayer.setPC(true);
//			tmpPcPlayer.setTeam(2);
//			tmpPcPlayer.setCharacter(SGCharacter.GODDESSPRINCESSSASHA);
//			//tmpPcPlayer.setCharacter(SGCharacter.ALICE);
//			this.pcCount++;
//			pcplayers.add(tmpPcPlayer);
//		}
//		gameKey=MainMenuScreen.readGameKey(pads.get(0).getPadUniqueName());
//		if(null==gameKey){
//			gameKey=new GameKey();
//		}
//		gameKeyMap=gameKey.toMap();
		loadGamepadSetting(pads.get(0));
		// initSG();

		//this.sasha = sasha;



		// stage = new Stage(new ExtendViewport(800, 450));//如果二维码变型不能用时,就改为这句的方式
		stage = new Stage(new StretchViewport(ScreenSetting.WORLD_WIDTH, ScreenSetting.WORLD_HEIGHT));
		Gdx.input.setInputProcessor(stage);


		prepareUI();
	}

	private final int pcCount = 0;
	//private ArrayList<Actor> pcList = new ArrayList<Actor>();

	private static class AxisGroup{

	}
	// private Table table=null;
	private void prepareUI() {


		// Create a table that fills the screen. Everything else will go inside this
		// table.
		table = new Table();
		tabUi=new TabUi();
		// table.setFillParent(true);
		// table.defaults().pad(5);
		int valueCol=5;
		int totalCol=6;
		table.setX(ScreenSetting.WORLD_WIDTH*0.5f );//300);
//		table.setY(ScreenSetting.WORLD_HEIGHT - 200);
		table.setY(ScreenSetting.WORLD_HEIGHT*0.5f);

		stage.addActor(table);



//		final Table playerRow = new Table();

		SelectBox<String> gamepadCombo=new SelectBox(skin);

		Array<String> gamepadComboItem=new Array<>();
		for ( ISGPS5Gamepad pad : pads) {
			gamepadComboItem.add(pad.getPadUniqueName());
		}
		gamepadCombo.setItems(gamepadComboItem);

		gamepadCombo.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//onSaveClicked();
				//gameKey=new GameKey();gameKeyMap=gameKey.toMap();

				for(ISGPS5Gamepad i:pads){
					if(((SelectBox<String>)actor).getSelected().equals(i.getPadUniqueName())){
						loadGamepadSetting(i);
						updateUIAfterChangeGamepadSetting();
						break;
					}
				}
			}
		});

		settingTypeLbl=new Label( settingDefault?"当前为默认配置":"当前为自定义配置",skin);
		table.add(gamepadCombo).colspan(totalCol).spaceBottom(20);
		table.row();
		table.add(settingTypeLbl).colspan(totalCol).spaceBottom(20);
		table.row();

		tabUi.addItem(gamepadCombo);


//		table.add(playerRow).colspan(valueCol+1).spaceBottom(40);
//		table.row();

		int i = 0;


//		dialog=new KeySettingDialog(KeySettingScreen.this//, //sasha,
//
//				);

//		keySettingLbls=new HashMap<>();
//		for (final Map.Entry<String, Integer> player : gameKeyMap.entrySet()) {
//			final Label playerlbl = new Label(gameKey.getKeyNamesByMask(player.getValue()), skin);
//			//playerlbls.add(playerlbl);
//			keySettingLbls.put(player.getKey(),playerlbl);
//
//			TextButton padNameBtn = new TextButton(player.getKey() + ":", skin);
//			//// final SGRef<Integer> iRef=new SGRef<Integer>(i);
//			//final int i2 = i;
//			ClickListener listener = new ClickListener() {
//
//				@Override
//				public void clicked(InputEvent event, float x, float y) {
////					new KeySettingDialog(KeySettingScreen.this, //sasha,
////							//player, pcCount,
////							gamepad,player.getKey(),
////							new SGAction1<Object>() {
////
////								@Override
////								public void go(Object t) {
//////									playerlbl.setText("team" + player.getTeam() + ", " + player.getCharacter());
////									player.setValue((int)t);
////									playerlbl.setText(XBoxKey.getTexts(player.getValue()));
////								}
////							}).show(stage);
//
//					dialog.init(//KeySettingScreen.this, //sasha,
//							//player, pcCount,
//							gamepad,player.getKey(),
//							new SGAction<String,Integer,Object>() {
//
//								@Override
//								public void go(String s, Integer integer, Object o) {
//
//////									playerlbl.setText("team" + player.getTeam() + ", " + player.getCharacter());
////									player.setValue((int)t);//这里用iter操作已经很有风险了
////									playerlbl.setText(XBoxKey.getTexts(player.getValue()));
//									onKeyChange(s,integer);
//									if (SGTouchGamepad.class ==gamepad.getClass()) {
//										((SGTouchGamepad) gamepad).remove();
//										//((SGTouchGamepad) gamepad).setVisible(false);
//										//if(!needAccessStageInput){needAccessStageInput=true;}
//									}
//								}
//
////								@Override
////								public void go(Object t) {
////////									playerlbl.setText("team" + player.getTeam() + ", " + player.getCharacter());
//////									player.setValue((int)t);//这里用iter操作已经很有风险了
//////									playerlbl.setText(XBoxKey.getTexts(player.getValue()));
////									onKeyChange(player.getKey(),(int)t);
////								}
//							},
//							SGTouchGamepad.class ==gamepad.getClass()?new SGAction<String,Integer,Object>() {
//
//								@Override
//								public void go(String s, Integer integer, Object o) {
//									if (SGTouchGamepad.class ==gamepad.getClass()) {
//										((SGTouchGamepad) gamepad).remove();
////										((SGTouchGamepad) gamepad).setVisible(false);
////										//if(!needAccessStageInput){needAccessStageInput=true;}
//									}
//								}
//
////								@Override
////								public void go(Object t) {
////////									playerlbl.setText("team" + player.getTeam() + ", " + player.getCharacter());
//////									player.setValue((int)t);//这里用iter操作已经很有风险了
//////									playerlbl.setText(XBoxKey.getTexts(player.getValue()));
////									onKeyChange(player.getKey(),(int)t);
////								}
//							}:null
//							);
//					dialog.show(stage);
//
//					if (SGTouchGamepad.class ==gamepad.getClass()) {
//						if(null==((SGTouchGamepad) gamepad).getStage()){
//							((SGTouchGamepad) gamepad).init();
//							stage.addActor((SGTouchGamepad) gamepad);
//						}else{
////							((SGTouchGamepad) gamepad).init();
////							stage.addActor((SGTouchGamepad) gamepad);
//////							((SGTouchGamepad) gamepad).setVisible(true);
//////							((SGTouchGamepad) gamepad).setTouchable(Touchable.enabled);
////////							Gdx.input.i;
////							Gdx.input.setInputProcessor(null);
////							Gdx.input.setInputProcessor(stage);
//						}
//						//if(!needAccessStageInput){needAccessStageInput=true;}
//					}
//				}
//			};
//			playerlbl.addListener(listener);
//			padNameBtn.addListener(listener);
//			playerRow.add(padNameBtn).spaceBottom(20).spaceRight(10);
//			playerRow.add(playerlbl).spaceBottom(20);
//			playerRow.row();
//
//			tabUi.addItem(padNameBtn);
//			i++;
//		}

		axisLeftLbl=new Label("左摇杆偏移(左右上下):",skin);
//		axisLeftX1TF=new TextField("-0.1",skin);
//		axisLeftX2TF=new TextField("0.1",skin);
//		axisLeftY1TF=new TextField("-0.1",skin);
//		axisLeftY2TF=new TextField("0.1",skin);

		if(SGPS5Gamepad.class==gamepad.getClass()){
			SGPS5Gamepad tmp=(SGPS5Gamepad) gamepad;
//			axisLeftTmp.x1=tmp.getAxisLeftSpace().x1;
//			axisLeftTmp.x2=tmp.getAxisLeftSpace().x2;
//			axisLeftTmp.y1=tmp.getAxisLeftSpace().y1;
//			axisLeftTmp.y2=tmp.getAxisLeftSpace().y2;
			axisLeftX1TF=new TextField(String.valueOf(SGDataHelper.getFloatPrecision(tmp.getAxisLeftSpace().x1,3)),skin);
			axisLeftX2TF=new TextField(String.valueOf(SGDataHelper.getFloatPrecision(tmp.getAxisLeftSpace().x2,3)),skin);
			axisLeftY1TF=new TextField(String.valueOf(SGDataHelper.getFloatPrecision(tmp.getAxisLeftSpace().y1,3)),skin);
			axisLeftY2TF=new TextField(String.valueOf(SGDataHelper.getFloatPrecision(tmp.getAxisLeftSpace().y2,3)),skin);
		}else{
			axisLeftX1TF=new TextField("-0.1",skin);
			axisLeftX2TF=new TextField("0.1",skin);
			axisLeftY1TF=new TextField("-0.1",skin);
			axisLeftY2TF=new TextField("0.1",skin);
		}
		HorizontalGroup axisLeftGroup = new HorizontalGroup();
		axisLeftGroup.space(20);
		axisLeftGroup.addActor(axisLeftX1TF);
		axisLeftGroup.addActor(axisLeftX2TF);
		axisLeftGroup.addActor(axisLeftY1TF);
		axisLeftGroup.addActor(axisLeftY2TF);

//		tabUi.addItem(axisLeftX1TF);
//		tabUi.addItem(axisLeftX2TF);
//		tabUi.addItem(axisLeftY1TF);
//		tabUi.addItem(axisLeftY2TF);
		axisLeftX1TF.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Float tmp=SGDataHelper.stringToFloat(((TextField)actor).getText());
				if(null!=tmp){
					axisLeftTmp.x1=tmp;
				}
//				if(0.1<tmp){
//					axisLeftTmp.x1=tmp;
//				}
			}
		});
		axisLeftX2TF.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Float tmp=SGDataHelper.stringToFloat(((TextField)actor).getText());
				if(null!=tmp){
					axisLeftTmp.x2=tmp;
				}
			}
		});
		axisLeftY1TF.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Float tmp=SGDataHelper.stringToFloat(((TextField)actor).getText());
				if(null!=tmp){
					axisLeftTmp.y1=tmp;
				}
			}
		});
		axisLeftY2TF.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Float tmp=SGDataHelper.stringToFloat(((TextField)actor).getText());
				if(null!=tmp){
					axisLeftTmp.y2=tmp;
				}
			}
		});
		axisLeftAutoBtn=new TextButton(TXT.g("auto setting"),skin);
		axisLeftAutoBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!axisLeftCalculating) {
					axisLeftCalculating = true;
					axisLeftCalculatingTime=0;
					//axisLeftTmp.init();
					axisLeftAutoBtn.setText(TXT.g("caculating")+"...");
					axisLeftAutoBtn.setDisabled(true);
				}
			}
		});
		tabUi.addItem(axisLeftAutoBtn);

		axisRightLbl=new Label("右摇杆偏移(左右上下):",skin);
		if(SGPS5Gamepad.class==gamepad.getClass()){
			SGPS5Gamepad tmp=(SGPS5Gamepad) gamepad;
//			axisRightTmp.x1=tmp.getAxisRightSpace().x1;
//			axisRightTmp.x2=tmp.getAxisRightSpace().x2;
//			axisRightTmp.y1=tmp.getAxisRightSpace().y1;
//			axisRightTmp.y2=tmp.getAxisRightSpace().y2;
			axisRightX1TF=new TextField(String.valueOf(SGDataHelper.getFloatPrecision(tmp.getAxisRightSpace().x1,3)),skin);
			axisRightX2TF=new TextField(String.valueOf(SGDataHelper.getFloatPrecision(tmp.getAxisRightSpace().x2,3)),skin);
			axisRightY1TF=new TextField(String.valueOf(SGDataHelper.getFloatPrecision(tmp.getAxisRightSpace().y1,3)),skin);
			axisRightY2TF=new TextField(String.valueOf(SGDataHelper.getFloatPrecision(tmp.getAxisRightSpace().y2,3)),skin);
		}else{
			axisRightX1TF=new TextField("-0.1",skin);
			axisRightX2TF=new TextField("0.1",skin);
			axisRightY1TF=new TextField("-0.1",skin);
			axisRightY2TF=new TextField("0.1",skin);
		}
		HorizontalGroup axisRightGroup = new HorizontalGroup();
		axisRightGroup.space(20);
		axisRightGroup.addActor(axisRightX1TF);
		axisRightGroup.addActor(axisRightX2TF);
		axisRightGroup.addActor(axisRightY1TF);
		axisRightGroup.addActor(axisRightY2TF);

		axisRightX1TF.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Float tmp=SGDataHelper.stringToFloat(((TextField)actor).getText());
				if(null!=tmp){
					axisRightTmp.x1=tmp;
				}
//				if(0.1<tmp){
//					axisRightTmp.x1=tmp;
//				}
			}
		});
		axisRightX2TF.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
//				axisRightTmp.x2=Float.valueOf(((TextField)actor).getText());
				Float tmp=SGDataHelper.stringToFloat(((TextField)actor).getText());
				if(null!=tmp){
					axisRightTmp.x2=tmp;
				}
//				if(0.1<tmp){
//					axisRightTmp.x2=tmp;
//				}
			}
		});
		axisRightY1TF.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
//				axisRightTmp.y1=Float.valueOf(((TextField)actor).getText());
				Float tmp=SGDataHelper.stringToFloat(((TextField)actor).getText());
				if(null!=tmp){
					axisRightTmp.y1=tmp;
				}
//				if(0.1<tmp){
//					axisRightTmp.y1=tmp;
//				}
			}
		});
		axisRightY2TF.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
//				axisRightTmp.y2=Float.valueOf(((TextField)actor).getText());
				Float tmp=SGDataHelper.stringToFloat(((TextField)actor).getText());
				if(null!=tmp){
					axisRightTmp.y2=tmp;
				}
//				if(0.1<tmp){
//					axisRightTmp.x1=tmp;
//				}
			}
		});
		axisRightAutoBtn=new TextButton(TXT.g("auto setting"),skin);
		axisRightAutoBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!axisRightCalculating) {
					axisRightCalculating = true;
					axisRightCalculatingTime=0;
					//axisRightTmp.init();
					axisRightAutoBtn.setText(TXT.g("caculating")+"...");
					axisRightAutoBtn.setDisabled(true);
				}
			}
		});
		tabUi.addItem(axisRightAutoBtn);

		//-------------------------
		int pow=3;
		axisL2Lbl=new Label("L2偏移:",skin);

		if(SGPS5Gamepad.class==gamepad.getClass()){
			SGPS5Gamepad tmp=(SGPS5Gamepad) gamepad;
			axisL2TF=new TextField(String.valueOf(SGDataHelper.getFloatPrecision(tmp.getL2Space(),pow)),skin);
		}else{
			axisL2TF=new TextField("0.1",skin);
		}
		HorizontalGroup axisL2Group = new HorizontalGroup();
		axisL2Group.space(20);
		axisL2Group.addActor(axisL2TF);

		axisL2TF.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Float tmp=SGDataHelper.stringToFloat(((TextField)actor).getText());
				if(null!=tmp){
					axisL2Tmp=tmp;
				}
			}
		});
		axisL2AutoBtn=new TextButton(TXT.g("auto setting"),skin);
		axisL2AutoBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!axisL2Calculating) {
					axisL2Calculating = true;
					axisL2CalculatingTime=0;
					axisL2AutoBtn.setText(TXT.g("calculating")+"...");
					axisL2AutoBtn.setDisabled(true);
				}
			}
		});
		tabUi.addItem(axisL2AutoBtn);
		//R2

		axisR2Lbl=new Label("R2偏移:",skin);

		if(SGPS5Gamepad.class==gamepad.getClass()){
			SGPS5Gamepad tmp=(SGPS5Gamepad) gamepad;
			axisR2TF=new TextField(String.valueOf(SGDataHelper.getFloatPrecision(tmp.getR2Space(),pow)),skin);
		}else{
			axisR2TF=new TextField("0.1",skin);
		}
		HorizontalGroup axisR2Group = new HorizontalGroup();
		axisR2Group.space(20);
		axisR2Group.addActor(axisR2TF);

		axisR2TF.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Float tmp=SGDataHelper.stringToFloat(((TextField)actor).getText());
				if(null!=tmp){
					axisR2Tmp=tmp;
				}
			}
		});
		axisR2AutoBtn=new TextButton(TXT.g("auto setting"),skin);
		axisR2AutoBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!axisR2Calculating) {
					axisR2Calculating = true;
					axisR2CalculatingTime=0;
					axisR2AutoBtn.setText(TXT.g("calculating")+"...");
					axisR2AutoBtn.setDisabled(true);
				}
			}
		});
		tabUi.addItem(axisR2AutoBtn);
		
		//-------------------------

		saveResultLbl=new Label("",skin);
		TextButton saveBtn = new TextButton(TXT.g("save setting, press □"), skin);
		saveBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				saveKey();
			}
		});

		final TextButton restoreBtn = new TextButton(TXT.g("restore default settings")+" △", skin);
		restoreBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				restoreKey();
			}
		});

		TextButton backBtn = new TextButton(TXT.g("return, press ○"), skin);
		backBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				goToMainPage();
			}
		});

		table.add(axisLeftLbl).spaceRight(20).spaceBottom(20);
		table.add(axisLeftGroup).colspan(4).spaceRight(20).spaceBottom(20);
		table.add(axisLeftAutoBtn).spaceBottom(20);
		table.row();

		table.add(axisRightLbl).spaceRight(20).spaceBottom(20);
		table.add(axisRightGroup).colspan(4).spaceRight(20).spaceBottom(20);
		table.add(axisRightAutoBtn).spaceBottom(20);
		table.row();

		int spaceRight=20;
		int spaceBottom=20;
		table.add(axisL2Lbl).spaceRight(spaceRight).spaceBottom(spaceBottom);
		table.add(axisL2Group).spaceRight(spaceRight).spaceBottom(spaceBottom);
		table.add(axisL2AutoBtn)//.colspan(4)
				.spaceRight(spaceRight)
				.spaceBottom(spaceBottom);
//		table.row();
		table.add(axisR2Lbl).spaceRight(spaceRight).spaceBottom(spaceBottom);
		table.add(axisR2Group).spaceRight(spaceRight).spaceBottom(spaceBottom);
		table.add(axisR2AutoBtn)//.colspan(4)
				.spaceBottom(spaceBottom);
		table.row();
		
		table.add(saveResultLbl).colspan(totalCol).spaceBottom(20);
		table.row();
		table.add(saveBtn).colspan(totalCol).spaceBottom(20);
		table.row();
		table.add(restoreBtn).colspan(totalCol).spaceBottom(20);
		table.row();
		table.add(backBtn).colspan(totalCol);

	}
	private boolean ok=false;
	private void saveKey(){
//		gameKey.applyMap(gameKeyMap);
////				LocalSaveSettingHelper.saveKnightGameKeyData(gamepad.getPadUniqueName(),gameKey);
//		localSaveSettingHelper2.saveGameKey(gamepad.getPadUniqueName(),gameKey);
		if(SGPS5Gamepad.class==gamepad.getClass()){
			SGPS5GamepadSetting tmp=new SGPS5GamepadSetting();
			tmp.setAxisLeftSpace(axisLeftTmp.x1,axisLeftTmp.x2,axisLeftTmp.y1,axisLeftTmp.y2);
			tmp.setAxisRightSpace(axisRightTmp.x1,axisRightTmp.x2,axisRightTmp.y1,axisRightTmp.y2);
			tmp.setL2Space(axisL2Tmp);
			tmp.setR2Space(axisR2Tmp);
			localSaveSettingHelper3.saveGameKey(gamepad.getPadUniqueName(),tmp);
		}

		saveResultLbl.setText(TXT.g("save success"));
		settingDefault=false;
		settingTypeLbl.setText("当前为自定义配置");
		this.msgWaitCount=this.msgWait;
		showSaveResultLbl(true,false);
	}
	private void restoreKey(){

		if(SGPS5Gamepad.class==gamepad.getClass()){
////					LocalSaveSettingHelper.saveGamepadSettingData(gamepad.getPadUniqueName(),tmp);
			localSaveSettingHelper3.saveGameKey(gamepad.getPadUniqueName(),null);

//			//现在没有办法reload gamepad的setting, 暂时就这样设置回去吧
//			SGPS5Gamepad tmp=(SGPS5Gamepad) gamepad;
//			tmp.setAxisLeftSpace(-0.1f,0.1f,-0.1f,0.1f);
//			tmp.setAxisRightSpace(-0.1f,0.1f,-0.1f,0.1f);

//			axisLeftTmp.x1=-0.1f;
//			axisLeftTmp.x2=0.1f;
//			axisLeftTmp.y1=-0.1f;
//			axisLeftTmp.y2=0.1f;
//			axisRightTmp.x1=-0.1f;
//			axisRightTmp.x2=0.1f;
//			axisRightTmp.y1=-0.1f;
//			axisRightTmp.y2=0.1f;
		}
		settingDefault=true;
		loadGamepadSetting(gamepad);
		updateUIAfterChangeGamepadSetting();
	}
//	private void onKeyChange(String key,int keyMask){
////		player.setValue((int)t);//这里用iter操作已经很有风险了
////		playerlbl.setText(XBoxKey.getTexts(player.getValue()));
//		gameKeyMap.put(key,keyMask);//这里用iter操作已经很有风险了
////		keySettingLbls.get(key).setText(XBoxKey.getTexts(keyMask));
//		keySettingLbls.get(key).setText(gameKey.getKeyNamesByMask(keyMask));
//	}
//	private void goToGamePage() {
//////		if (null == screen) {
//////
//////			AssetManager manager = GameScreen.getAssetsManager();
//////			boolean b = manager.update();
//////			manager.finishLoading();
//////			game.setScreen(new GameScreen(game, manager, sasha));
//////			dispose();
//////		} else {
//////			game.setScreen(screen);
//////			screen.resume();
//////			dispose();
//////		}
////
////		if(null!=screen) {//暂停页是不应该进入本页的,所以如果进这判断,就是有问题
////			Guide2Screen g1=new Guide2Screen(game,screen.getSashaData());
////			g1.setGameScreen(screen);
////			game.setScreen(g1);
////			//screen.resume();
////			dispose();
////		}else {
////			sasha=MainMenuScreen.changeCharacterData(currentCharacter);
////			Guide2Screen g1=new Guide2Screen(game,sasha);
////			game.setScreen(g1);
////			dispose();
////		}
//
//		sasha=MainMenuScreen.changeCharacterData(currentCharacter);
//		Guide2Screen g1=new Guide2Screen(game,sasha);
//		game.setScreen(g1);
//		dispose();
//	}

	private void goToMainPage() {
		game.setScreen(new MainMenuScreen(game));
		dispose();
	}
//	private void goToKofGamePage() {
//
//		AssetManager manager = GameScreen.getAssetsManager();
//
//		HashMap<SGCharacter, Boolean> map = new HashMap<SGCharacter, Boolean>();
//
//		ArrayList<KofPlayer> mergeList = new ArrayList<KofPlayer>(players);
//		mergeList.addAll(pcplayers);
//		for (KofPlayer player : mergeList) {
//			map.put(player.getCharacter(), true);
//		}
//		for (SGCharacter i : map.keySet()) {
//			if (SGCharacter.GODDESSPRINCESSSASHA == i) {
//				SashaActor.loadAssets(manager);
//			}
//			else if (SGCharacter.ALICE == i) {
//				AliceActor.loadAssets(manager);
//			}
//			else if (SGCharacter.CHOMPER == i) {
//				ChomperActor.loadAssets(manager);
//			}
//			else if (SGCharacter.GIANT == i) {
//				GiantActor.loadAssets(manager);
//			}
//		}
//		// boolean b =
//		manager.update();
//		manager.finishLoading();
////		ArrayList<KofPlayer> tmp=new ArrayList<KofPlayer>(players);
////		tmp.addAll(pcplayers);
////		game.setScreen(new KofGameScreen(game, manager, sasha, mergeList, pads));
//		game.setScreen(new KofGameScreen(game, manager, mergeList, pads));
//		dispose();
//
//	}
//
//	private void goToKofGameD3Page() {
//
//		AssetManager manager = GameScreen.getAssetsManager();
////		GameScreenD3.loadAssets(manager);
//		KofGameScreenD3.loadAssets(manager);
////		SashaActor.loadAssets(manager);
////		SashaActorD3.loadAssets(manager);
////		AliceActor.loadAssets(manager);
//
//		HashMap<SGCharacter, Boolean> map = new HashMap<SGCharacter, Boolean>();
//
//		ArrayList<KofPlayer> mergeList = new ArrayList<KofPlayer>(players);
//		mergeList.addAll(pcplayers);
//		for (KofPlayer player : mergeList) {
//			map.put(player.getCharacter(), true);
//		}
//		PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
//		Array<ParticleBatch<?>> list=new Array<>();
//		list.add(pointSpriteBatch);
//		ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(list);
//		for (SGCharacter i : map.keySet()) {
//			if (SGCharacter.GODDESSPRINCESSSASHA == i) {
//				SashaActor.loadAssets(manager);
//				SashaActorD3.loadAssets(manager);
//				SashaActorD3.loadParticleAssets(manager,loadParam);
//			}
//			else if (SGCharacter.ALICE == i) {
//				AliceActor.loadAssets(manager);
//			}
//			else if (SGCharacter.CHOMPER == i) {
//				ChomperActor.loadAssets(manager);
//			}
//			else if (SGCharacter.GIANT == i) {
//				GiantActor.loadAssets(manager);
//			}
//		}
//		// boolean b =
//		manager.update();
//		manager.finishLoading();
//
//		//game.setScreen(new KofGameScreenD3(game, manager, mergeList, pads));
//
//		KofGameScreenD3 tmp=new KofGameScreenD3();
//		tmp.setPointSpriteBatch(pointSpriteBatch);
//		tmp.init(game, manager, mergeList, pads);
//		game.setScreen(tmp);
//		dispose();
//
//	}
//	private void goToGuide() {
//		Guide1Screen g1=new Guide1Screen(game,screen.getSashaData());
//		g1.setGameScreen(screen);
//		game.setScreen(g1);
//		//screen.resume();
//		dispose();
//	}
//	/**
//	 * 发布前要初始化数据
//	 */
//	private void initSysDataBeforeRelease() {
//		sasha = SashaData.init();
//		saveSashaData(sasha);
//	}
//
//	private void initTestSashaData() {
//		// readUserData();
//		sasha = SashaData.init();
//		sasha = readSashaData();
//		sasha.setCountry("cn");
//		sasha.setEmail(key);
//		;
//		saveSashaData(sasha);
//	}

	@Override
	public void show() {

	}

	//private int jumpCount = 0;
	private float backToMainTime=3;
	private final float  msgWait=2f;
	private float msgWaitCount=2f; //这个要设置为所有wait的max值
	private void showSaveResultLbl(boolean visible, boolean animated) {
		float alphaTo = visible ? 0.8f : 0.0f;
		float duration = animated ? 1.0f : 0.0f;
		Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;
		saveResultLbl.addAction(sequence(
				touchable(touchEnabled),
				alpha(alphaTo, duration)));
	}
	@Override
	public void render(float delta) {

		if(!ok){

			if(0>=backToMainTime){
				this.goToMainPage();
				return;
			}else{
				ScreenUtils.clear(0, 0, 0.2f, 1);
				Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

				batch.begin();
				game.font.draw(batch,String.format(TXT.g("no gamepad. back to main screen(%.1f seconds)"),backToMainTime),100,ScreenSetting.WORLD_HEIGHT-100);
				batch.end();
				backToMainTime-=delta;
				return;
			}
		}
		if(0>=buttonWaitCount) {
//			if (
//				true//	null == dialog || (!dialog.isVisible()) || null == dialog.getStage()
//			) {
				if (tabUi.isEditing()) {

					if (0 >= buttonWaitCount) {
						if (sgcontroller.isROUND()) {
							tabUi.select();
							buttonWaitCount = buttonWait;
						} else {
							if (tabUi.edit(sgcontroller)) {

								buttonWaitCount = buttonWait;
							}
						}
					}
				} else {
					//无编辑时的主要操作
					if (oWait <= 0) {
						if (null != sgcontroller && sgcontroller.isROUND()) {
							//
							//				game.setScreen(new MainMenuScreen(game));
							//				// game.setScreen(new UserInfoScreen(game,sasha));
							//				dispose();

							goToMainPage();
							return;
						}
					}

					if (0 >= buttonWaitCount) {
						if (null != sgcontroller && sgcontroller.isUP()) {
							tabUi.up();
							buttonWaitCount = buttonWait;
						} else if (null != sgcontroller && sgcontroller.isDOWN()) {
							tabUi.down();
							buttonWaitCount = buttonWait;
						} else if (null != sgcontroller && sgcontroller.isCROSS()) {
							//				((ClickListener)((TextButton)tabUi.getCurrentActor()).getListeners().get(((TextButton)tabUi.getCurrentActor()).getListeners().size-1)).clicked(null,0,0);
							//				((TextButton)tabUi.getCurrentActor()).getClickListener().clicked(new InputEvent(),0,0);
							tabUi.select();
							buttonWaitCount = buttonWait;
						}
						else if(null != sgcontroller && sgcontroller.isSQUARE()){
							saveKey();
						}else if(null != sgcontroller && sgcontroller.isTRIANGLE()){
							restoreKey();
						}
					}
				}
//			} else {
//				//有弹窗时
//				if (0 >= buttonWaitCount) {
//
//					if (null != sgcontroller && sgcontroller.isCROSS() && dialog.isDone()) {
//						dialog.getRestoreButton().toggle();
//						buttonWaitCount = buttonWait;
//					} else if (null != sgcontroller && sgcontroller.isROUND() && dialog.isDone()) {
//						dialog.getCloseButton().toggle();
//						buttonWaitCount = buttonWait;
//					}
//				}
//			}
		}



		if(axisRightCalculating){

			if(2>axisRightCalculatingTime){
				axisRightTmp.x1=Math.min(axisRightTmp.x1,gamepad.axisRightX());
				axisRightTmp.x2=Math.max(axisRightTmp.x2,gamepad.axisRightX());
				axisRightTmp.y1=Math.min(axisRightTmp.y1,gamepad.axisRightY());
				axisRightTmp.y2=Math.max(axisRightTmp.y2,gamepad.axisRightY());
				axisRightCalculatingTime+=delta;
			}else{
				updateUIAfterChangeGamepadSetting();
				axisRightCalculating=false;
				axisRightAutoBtn.setText(TXT.g("auto setting"));
				axisRightAutoBtn.setDisabled(false);
			}
		}

		if(axisLeftCalculating){

			if(2>axisLeftCalculatingTime){
				axisLeftTmp.x1=Math.min(axisLeftTmp.x1,gamepad.axisLeftX());
				axisLeftTmp.x2=Math.max(axisLeftTmp.x2,gamepad.axisLeftX());
				axisLeftTmp.y1=Math.min(axisLeftTmp.y1,gamepad.axisLeftY());
				axisLeftTmp.y2=Math.max(axisLeftTmp.y2,gamepad.axisLeftY());
				axisLeftCalculatingTime+=delta;
			}else{
				updateUIAfterChangeGamepadSetting();
				axisLeftCalculating=false;
				axisLeftAutoBtn.setText(TXT.g("auto setting"));
				axisLeftAutoBtn.setDisabled(false);
			}
		}

		if(axisL2Calculating){
			if(2>axisL2CalculatingTime){
				axisL2Tmp=Math.max(axisL2Tmp,gamepad.axisL2());
				axisL2CalculatingTime+=delta;
			}else{
				updateUIAfterChangeGamepadSetting();
				axisL2Calculating=false;
				axisL2AutoBtn.setText(TXT.g("auto setting"));
				axisL2AutoBtn.setDisabled(false);
			}
		}

		if(axisR2Calculating){
			if(2>axisR2CalculatingTime){
				axisR2Tmp=Math.max(axisR2Tmp,gamepad.axisR2());
				axisR2CalculatingTime+=delta;
			}else{
				updateUIAfterChangeGamepadSetting();
				axisR2Calculating=false;
				axisR2AutoBtn.setText(TXT.g("auto setting"));
				axisR2AutoBtn.setDisabled(false);
			}
		}

		if(0>=msgWaitCount){
			if(Touchable.disabled!=saveResultLbl.getTouchable()) {
				showSaveResultLbl(false, true);
			}
		}

//		ScreenUtils.clear(0, 0, 0.2f, 1);
		ScreenUtils.clear(Color.PINK);
		if (buttonWaitCount > 0) {

			buttonWaitCount -= delta;
		} else if (buttonWaitCount < 0) {
			buttonWaitCount = 0;
		}

		if (msgWaitCount > 0) {
			msgWaitCount -= delta;
		} else if (msgWaitCount < 0) {
			msgWaitCount = 0;
		}

		if (xWait > 0) {

			xWait -= delta;
		} else if (xWait < 0) {
			xWait = 0;
		}

		if (oWait > 0) {

			oWait -= delta;
		} else if (oWait < 0) {
			oWait = 0;
		}

		if (squartWait > 0) {

			squartWait -= delta;
		} else if (squartWait < 0) {
			squartWait = 0;
		}




//		// if(nextWait<=0) {
//		if (SGCharacter.SASHA == sasha.getCharacter()) {
//			if (1 == step) {
//				if (nextWait <= 0) {
//					actor.dodge();
//					skillLbl.setText("○ 躲闪");
//				}
//				nextWait += delta;
//				if (nextWait > 2) {
//
//					nextWait = 0;
//					step++;
//					actor.setX(actorBeginX);
//				}
//			} else if (2 == step) {
//				actor.defence();
//				;
//				skillLbl.setText("L1 防御");
//				nextWait += delta;
//				if (nextWait > 2) {
//
//					nextWait = 0;
//					step++;
//				}
//			} else if (3 == step) {
//				if (nextWait <= 0) {
//					actor.jump();
//					skillLbl.setText("X 跳");
//				}else if(nextWait>0.5&&jumpCount==0) {
//					actor.jump();
//					jumpCount++;
//				}
//				nextWait += delta;
//				if (nextWait > 2) {
//
//					nextWait = 0;
//					step++;
//					jumpCount=0;
//				}
//			}  else if (4== step) {
//				if (nextWait <= 0) {
//					actor.doSkill1(stage);
//					skillLbl.setText("↓↘→□ 气功");
//				}
//				nextWait += delta;
//				if (nextWait > 2) {
//
//					nextWait = 0;
//					step++;
//				}
//			} else if (5 == step) {
//				if (nextWait <= 0) {
//					actor.doSkill2(stage);
//					skillLbl.setText("→↘↓↘→□ 天翔龙闪");
//				}
//				nextWait += delta;
//				if (nextWait > 2) {
//
//					nextWait = 0;
//					step = 1;
//					actor.setX(actorBeginX);
//				}
//			}
////				step++;
////				if(step>4) {step=1;}
//		}

		// }
//		nextWait-=delta;
//		if(nextWait<0) {nextWait=0;}

//		int paycodeW = 220;
//		int paycodeH = 300;
//		batch.begin();
//		batch.draw(paycode, Gdx.graphics.getWidth() / 2 - (paycodeW / 2), Gdx.graphics.getHeight() - paycodeH - 20,
//				paycodeW, paycodeH);
//
//		game.font.draw(batch,
//				TXT.g("This is a free game developed by BENJAMIN, and VIP will have a better experience in the game. You may pay 1 yuan or more to permanently activate VIP. The developer promises to permanently update and maintain this game. You can scan the QR code above to make payment, I will active your VIP in one day, thanks."),
//				10, Gdx.graphics.getHeight() - paycodeH - 40, Gdx.graphics.getWidth() - 20,
//				Gdx.graphics.getHeight() - paycodeH - 20, true);
//		
//
////		game.font.draw(batch,
////				TXT.g("pay description"),
////				10, Gdx.graphics.getHeight() - paycodeH - 40, Gdx.graphics.getWidth() - 20,
////				Gdx.graphics.getHeight() - paycodeH - 20, true);
//		batch.end();

//		// 更新舞台逻辑
		stage.act();
		// 绘制舞台
		stage.draw();

//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		stage.act(Gdx.graphics.getDeltaTime());
//		stage.draw();
	}

//	private void gotoGamePage() {
//		AssetManager manager = new AssetManager();
//		// manager.load("data/mytexture.png", Texture.class);
////		manager.load("sasha/man1.png", Texture.class);
////		manager.load("sasha/man2.png", Texture.class);
////		manager.load("sasha/man3.png", Texture.class);
//		manager.load("man1.png", Texture.class);
//		manager.load("man2.png", Texture.class);
//		manager.load("man3.png", Texture.class);
//		manager.load("drop.wav", Sound.class);
//		manager.load("rain.mp3", Music.class);
//
//		boolean b = manager.update();
//		manager.finishLoading();
//		game.setScreen(new GameScreen(game, manager));
//		dispose();
//
//	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
//		if(null!=lastScreen) {
//			lastScreen.dispose();
//			lastScreen=null;
//		}
		if(null!=stage) {
			stage.dispose();
			stage=null;
		}
		if(null!=skin) {
			skin.dispose();
			skin=null;
		}
		if(null!=batch) {
			batch.dispose();
			batch=null;
		}
		//System.out.println(this.getClass().getSimpleName()+" dispose");
	}

//	public void onSelectCharacterDialogConfirm(int playerId) {
//		this.playerlbls.get(playerId)
//				.setText("team" + players.get(playerId).getTeam() + ", " + players.get(playerId).getCharacter());
//	}

	// ...Rest of class omitted for succinctness.

//	private class SGXInputControllerListener extends ControllerAdapter {
//
//		@Override
//		public boolean buttonDown(Controller controller, int buttonIndex) {
////		         if(XBoxKey.CROSS.ordinal()==buttonIndex) {
////			         Gdx.app.log(TAG, "jump: " +buttonIndex);
////		         }
//
//			if (buttonIndex == controller.getMapping().buttonA) {
////			         Gdx.app.log(TAG, "jump: " +buttonIndex);
//				// gotoGamePage();
//			}
//
//			return false;
//		}
//
//		@Override
//		public boolean buttonUp(Controller controller, int buttonIndex) {
//			return false;
//		}
//
//		@Override
//		public void connected(Controller controller) {
//		}
//
//		@Override
//		public void disconnected(Controller controller) {
//		}
//	}

//	public void setGameScreen(GameScreen screen) {
//		this.screen = screen;
//	}

//	private void readUserData() {
//		Preferences preferences = Gdx.app.getPreferences("pre1.test");
//		preferences.putString("sasha", "Kitty");
//		preferences.putBoolean("visible", true);
//		preferences.putInteger("age", 25);
//		preferences.flush();
//
//		String strName1 = preferences.getString("name");
//		boolean isVisible = preferences.getBoolean("visible");
//		int age1 = preferences.getInteger("age");
//	}
//
//	private String key = "a2FoaW5lYXNpbjEyMzQ1Ng==";
//
//	private void saveSashaData(SashaData sasha) {
//		Preferences preferences = Gdx.app.getPreferences("pre1.test");
//		String str = sasha.getAgi() + "|" + sasha.getEmail() + "|" + sasha.getKill() + "|" + sasha.getBirth() + "|"
//				+ sasha.getStr() + "|" + sasha.getUserId() + "|" + sasha.getObedient() + "|" + sasha.getLastLogin()
//				+ "|" + sasha.getVip() + "|" + sasha.getVer() + "|" + sasha.getCountry();
//		try {
//			preferences.putString("sasha", AES.AESEncryptDemo(str, SGDataHelper.decodeBase64(key)));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		preferences.flush();
//	}


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


}
