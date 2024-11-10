package com.sellgirl.gamepadtool.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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
//import com.mygdx.game.sasha.SashaGame;
//import com.mygdx.game.sasha.language.TXT;
//import com.mygdx.game.sasha.screen.MainMenuScreen;
//import com.mygdx.game.share.SGLibGdxHelper;
//import com.mygdx.game.share.ScreenSetting;
//import com.mygdx.game.share.gamepad.ISGPS5Gamepad;
//import com.mygdx.game.share.gamepad.SGPS5Gamepad;
//import com.mygdx.game.share.gamepad.SGTouchGamepad;
//import com.mygdx.game.share.tabUi.TabUi;
import com.sellgirl.gamepadtool.GamepadTool;
import com.sellgirl.gamepadtool.MainMenuScreen;
import com.sellgirl.gamepadtool.ScreenSetting;
import com.sellgirl.gamepadtool.language.TXT;
import com.sellgirl.gamepadtool.model.KeySimulateItem;
import com.sellgirl.gamepadtool.model.MouseKey;
import com.sellgirl.sgGameHelper.SGLibGdxHelper;
import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
import com.sellgirl.sgGameHelper.gamepad.SGPS5Gamepad;
import com.sellgirl.sgGameHelper.gamepad.SGTouchGamepad;
import com.sellgirl.sgGameHelper.gamepad.XBoxKey;
import com.sellgirl.sgGameHelper.list.Array2;
import com.sellgirl.sgGameHelper.list.ISGList;
import com.sellgirl.sgGameHelper.tabUi.TabUi;
import com.sellgirl.sgJavaHelper.SGAction;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import com.sellgirl.sgJavaHelper.config.SGDataHelper;

/**
 * 把手柄的按键映射到键盘键位的功能, 应该只对pc断有用
 */
public class SimulateScreen implements Screen {

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
	//HashMap<Integer,TextButton> keyBtns=null;
	HashMap<String,Label> combinKeyLbls = null;
	HashMap<Integer,Label> combinKeySettingLbls = null;
//	HashMap<Integer,TextButton> combinKeyBtns=null;
	HashMap<Integer,Label> combinKeyBtns=null;
//	Label settingTypeLbl=null;
	IKnightSashaGameKey gameKey=null;
	@Deprecated
	HashMap<String,Integer> gameKeyMap=null;
	Array2<KeySimulateItem> gameKeyMap2=null;
//	HashMap<Integer,Integer> gameKeyCombinMap=null;
	Array2<KeySimulateItem> gameKeyCombinMap2=null;
	/**
	 * 当前正在设定的手柄
	 */
	ISGPS5Gamepad gamepad;
	boolean settingDefault=true;
	KeySettingDialog dialog;
	TabUi tabUi;
//
//	public SelectCharacterScreen(final SashaGame game, final SashaData sasha
//			) {

	LocalSaveSettingHelper2 localSaveSettingHelper2=null;
	//LocalSaveSettingHelper3 localSaveSettingHelper3=null;
	public void loadGamepadSetting(ISGPS5Gamepad gamepad){

//		gameKey=localSaveSettingHelper2.readGameKey(gamepad.getPadUniqueName(),gamepad.getClass());
//		gameKey=localSaveSettingHelper2.readGameKey(gamepad.getPadUniqueName(),SGKeyboardGamepad.class==gamepad.getClass()?new GameKeyKeyboard():new GameKey());
//		gameKey=localSaveSettingHelper2.readGameKey(gamepad.getPadUniqueName(),SGKeyboardGamepad.class==gamepad.getClass()?new GameKeyKeyboard():new GameKey());
//		gameKey=localSaveSettingHelper2.readGameKey(gamepad.getPadUniqueName(),new GameKey());
		String setting=settingCombo.getSelected();
		gameKey=localSaveSettingHelper2.readGameKey(gamepad.getPadUniqueName()+setting,new GameKey());
		if(null==gameKey){
//			gameKey=SGKeyboardGamepad.class==gamepad.getClass()?new GameKeyKeyboard(): new GameKey();
			gameKey= new GameKey();
			settingDefault=true;
		}else{
//			if(gameKey.getClass()!=(SGKeyboardGamepad.class==gamepad.getClass()?GameKeyKeyboard.class: GameKey.class)){
//				gameKey=SGKeyboardGamepad.class==gamepad.getClass()?new GameKeyKeyboard(): new GameKey();
//			}
			settingDefault=false;
		}
		if(null==gameKeyMap){
//			gameKeyMap=new HashMap<>();
			gameKeyMap=new LinkedHashMap<>();
		}
		gameKeyMap=gameKey.toMap(gameKeyMap);


		if(null==gameKeyMap2){
			gameKeyMap2=new Array2<KeySimulateItem>();
		}else{
			gameKeyMap2.clear();
		}
		if(null==gameKey.getKeyMap()){
			//gameKeyMap2=new Array2<KeySimulateItem>();
		}else{
			for(KeySimulateItem i:gameKey.getKeyMap()){
				if(null!=i.getDstKeyType()&&KeySimulateItem.KeyType.NONE!=i.getDstKeyType()) {
					gameKeyMap2.add(i);
				}
			}
			//gameKeyMap2=gameKey.getKeyMap();
		}
//		if(gameKeyMap2.isEmpty()){
//			for(KeySimulateItem i:GameKey.intDefaultKeyMap(new ArrayList<KeySimulateItem>())){
//				gameKeyMap2.add(i);
//			}
//			//gameKeyMap2=GameKey.intDefaultKeyMap(gameKeyMap2);
//		}

//		if(null==gameKey.getCombinedMap()){
//			gameKeyCombinMap=new LinkedHashMap<>();
//		}else{
//			gameKeyCombinMap=new LinkedHashMap<>(gameKey.getCombinedMap());
//		}

		if(null==gameKeyCombinMap2){
			gameKeyCombinMap2=new Array2<KeySimulateItem>();
		}else{
			gameKeyCombinMap2.clear();
		}
		if(null==gameKey.getCombinedMap2()){
			//gameKeyMap2=new Array2<KeySimulateItem>();
		}else{
			for(KeySimulateItem i:gameKey.getCombinedMap2()){
				if(null!=i.getDstKeyType()&&KeySimulateItem.KeyType.NONE!=i.getDstKeyType()) {
					gameKeyCombinMap2.add(i);
				}
			}
			//gameKeyMap2=gameKey.getKeyMap();
		}

		this.gamepad=gamepad;
		gameKey.setGamepad(gamepad);

//		if(SGPS5Gamepad.class==gamepad.getClass()){
//			SGPS5Gamepad tmp=(SGPS5Gamepad) gamepad;
//			axisLeftTmp.x1=tmp.getAxisLeftSpace().x1;
//			axisLeftTmp.x2=tmp.getAxisLeftSpace().x2;
//			axisLeftTmp.y1=tmp.getAxisLeftSpace().y1;
//			axisLeftTmp.y2=tmp.getAxisLeftSpace().y2;
//		}else{
//
//			axisLeftTmp.x1=0;
//			axisLeftTmp.x2=0;
//			axisLeftTmp.y1=0;
//			axisLeftTmp.y2=0;
//		}
//
//		if(SGPS5Gamepad.class==gamepad.getClass()){
//			SGPS5Gamepad tmp=(SGPS5Gamepad) gamepad;
//			axisRightTmp.x1=tmp.getAxisRightSpace().x1;
//			axisRightTmp.x2=tmp.getAxisRightSpace().x2;
//			axisRightTmp.y1=tmp.getAxisRightSpace().y1;
//			axisRightTmp.y2=tmp.getAxisRightSpace().y2;
//		}else{
//
//			axisRightTmp.x1=0;
//			axisRightTmp.x2=0;
//			axisRightTmp.y1=0;
//			axisRightTmp.y2=0;
//		}
//		int currentSize=gameKeyMap2.size+gameKeyCombinMap.size();
		int currentSize=gameKeyMap2.size+gameKeyCombinMap2.size();
		keyDowns=new boolean[currentSize];
//		if(null==keyDowns|| keyDowns.length<currentSize){
//			keyDowns=new boolean[currentSize];
//		}
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
//
//		}

		keySettingLbls.clear();
		//keyBtns.clear();
		playerRow1.clear();
		generateKeyLbls();

//		for (KeySimulateItem m1 : gameKeyMap2) {
//			if(m1.getDstKeyType()== KeySimulateItem.KeyType.KEYBOARD){
//				keySettingLbls.get(XBoxKey.values()[ m1.getSrcKey()].toString())
//						.setText(gameKey.getKeyNamesByMask(m1.getDstKey()) );
//			}else{
//				keySettingLbls.get(XBoxKey.values()[ m1.getSrcKey()].toString())
//						.setText("映射键盘");
//			}
//			if(m1.getDstKeyType()== KeySimulateItem.KeyType.MOUSE){
//				mouseKeySettingLbls.get(XBoxKey.values()[ m1.getSrcKey()].toString())
//						.setText(MouseKey.values()[m1.getDstKey()].toString() );
//			}else{
//				mouseKeySettingLbls.get(XBoxKey.values()[ m1.getSrcKey()].toString())
//						.setText("映射鼠标");
//			}
//		}

		combinKeySettingLbls.clear();
		combinKeyBtns.clear();
		playerRow2.clear();

		generateCombineLbls();
//		axisLeftX1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.x1,3)));
//		axisLeftX2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.x2,3)));
//		axisLeftY1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.y1,3)));
//		axisLeftY2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.y2,3)));
//		axisRightX1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.x1,3)));
//		axisRightX2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.x2,3)));
//		axisRightY1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.y1,3)));
//		axisRightY2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.y2,3)));
//		if(null!=settingTypeLbl){
//			if(settingDefault){
//				settingTypeLbl.setText("当前为默认配置");
//			}else{
//				settingTypeLbl.setText("当前为自定义配置");
//			}
//		}
	}
	public SimulateScreen(final GamepadTool game
                          //, SashaData sasha
			) {
		localSaveSettingHelper2=new LocalSaveSettingHelper2();
		//localSaveSettingHelper3=new LocalSaveSettingHelper3();
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

//		//这个字体没有中文
//		skin = new Skin(Gdx.files.internal(com.sellgirl.gamepadtool.util.Constants.SKIN_LIBGDX_UI), new com.badlogic.gdx.graphics.g2d.TextureAtlas(com.sellgirl.gamepadtool.util.Constants.TEXTURE_ATLAS_LIBGDX_UI));
//////		skin.add("default-font", MainMenuScreen.getFont2());//default
////		TextButton.TextButtonStyle style=skin.get(TextButton.TextButtonStyle.class);
////		style.font=MainMenuScreen.getFont2();
//		skin.get(TextButton.TextButtonStyle.class).font=game.font;
//		skin.get(Label.LabelStyle.class).font=game.font;
//		skin.get(SelectBox.SelectBoxStyle.class).font=game.font;
		skin=MainMenuScreen.getSkin2(game.font);


		//skinLibgdx = new Skin(Gdx.files.internal("skin/uiskin.json"), new TextureAtlas("skin/uiskin.atlas"));

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
////			KofPlayer tmpplayer = new KofPlayer();
////			tmpplayer.setPlayName("p" + (1 + i));
//			//players.add(tmpplayer);
//			i++;
//		}
//		if(game.showTouchpad){
//			//SGTouchGamepad touchPad= new SGTouchGamepad();
//			pads.add(new SGTouchGamepad());
////			KofPlayer tmpplayer = new KofPlayer();
////			tmpplayer.setPlayName("p" + (1 + i));
//			//players.add(tmpplayer);
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
//		loadGamepadSetting(pads.get(0));
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
//	TextButton simulateBtn;
	boolean simulating=false;
	// private Table table=null;
	private SelectBox<String> settingCombo=null;
	private Array<String> settingComboItem=null;
	private Table playerRow;
	private Table playerRow1;
	private Table playerRow2;
	private void prepareUI() {


		// Create a table that fills the screen. Everything else will go inside this
		// table.
		table = new Table();
		tabUi=new TabUi();
		// table.setFillParent(true);
		// table.defaults().pad(5);
		int valueCol=5;
//		table.setX(ScreenSetting.WORLD_WIDTH*0.5f );//300);
////		table.setY(ScreenSetting.WORLD_HEIGHT - 200);
//		table.setY(ScreenSetting.WORLD_HEIGHT*0.5f);

		stage.addActor(table);



		 playerRow = new Table();
		playerRow1 = new Table();
		 playerRow2 = new Table();

		final SelectBox<String> gamepadCombo = getStringSelectBox();
		settingCombo = getSettingSelectBox();

//		settingTypeLbl=new Label( settingDefault?"当前为默认配置":"当前为自定义配置",skin);
		table.add(gamepadCombo).colspan(2).spaceBottom(20);
		table.row();
		table.add(settingCombo).colspan(2).spaceRight(20).spaceBottom(20);
//		table.add(settingTF).spaceRight(20).spaceBottom(20);
//		table.add(addSettingBtn).spaceRight(20).spaceBottom(20);

		table.row();
//		table.add(settingTypeLbl).colspan(valueCol+1).spaceBottom(20);
//		table.row();

		loadGamepadSetting(pads.get(0));

		tabUi.addItem(gamepadCombo);
		tabUi.addItem(settingCombo);


//		table.add(playerRow).colspan(valueCol+1).spaceBottom(40);
//		table.row();

		int i = 0;


//		dialog=new KeySettingDialog(SimulateScreen.this//, //sasha,
//				//player, pcCount,
////				gamepad,player.getKey(),
////				new SGAction1<Object>() {
////
////					@Override
////					public void go(Object t) {
//////									playerlbl.setText("team" + player.getTeam() + ", " + player.getCharacter());
////						player.setValue((int)t);
////						playerlbl.setText(XBoxKey.getTexts(player.getValue()));
////					}
////				}
//				);
//		keySettingLbls=new ArrayList<>();
		keySettingLbls=new HashMap<>();
		combinKeyBtns=new HashMap<>();
		generateKeyLbls();


		combinKeySettingLbls=new HashMap<>();
		combinKeyBtns=new HashMap<>();
		generateCombineLbls();
//		for (final Map.Entry<Integer, Integer> player : gameKeyCombinMap.entrySet()) {
//			final Label playerlbl = new Label(gameKey.getKeyNamesByMask(player.getValue()), skin);
//			//playerlbls.add(playerlbl);
//			this.combinKeySettingLbls.put(player.getKey(),playerlbl);
//
//			final String keyName= XBoxKey.getTexts( player.getKey());
//			TextButton padNameBtn = new TextButton( keyName+ ":", skin);
////			//// final SGRef<Integer> iRef=new SGRef<Integer>(i);
////			//final int i2 = i;
////			ClickListener listener = new ClickListener() {
////
////				@Override
////				public void clicked(InputEvent event, float x, float y) {
////					dialog.init(//KeySettingScreen.this, //sasha,
////							//player, pcCount,
////							gamepad,
////							keyName,//player.getKey(),
////							new SGAction<String,Integer,Object>() {
////
////								@Override
////								public void go(String s, Integer integer, Object o) {
////
////									onGamepadKeySettingChange(player.getKey(),integer);
////									if (SGTouchGamepad.class ==gamepad.getClass()) {
////										((SGTouchGamepad) gamepad).remove();
////										//((SGTouchGamepad) gamepad).setVisible(false);
////										//if(!needAccessStageInput){needAccessStageInput=true;}
////									}
////								}
////
//////								@Override
//////								public void go(Object t) {
//////////									playerlbl.setText("team" + player.getTeam() + ", " + player.getCharacter());
////////									player.setValue((int)t);//这里用iter操作已经很有风险了
////////									playerlbl.setText(XBoxKey.getTexts(player.getValue()));
//////									onKeyChange(player.getKey(),(int)t);
//////								}
////							},
////							SGTouchGamepad.class ==gamepad.getClass()?new SGAction<String,Integer,Object>() {
////
////								@Override
////								public void go(String s, Integer integer, Object o) {
////									if (SGTouchGamepad.class ==gamepad.getClass()) {
////										((SGTouchGamepad) gamepad).remove();
//////										((SGTouchGamepad) gamepad).setVisible(false);
//////										//if(!needAccessStageInput){needAccessStageInput=true;}
////									}
////								}
////
//////								@Override
//////								public void go(Object t) {
//////////									playerlbl.setText("team" + player.getTeam() + ", " + player.getCharacter());
////////									player.setValue((int)t);//这里用iter操作已经很有风险了
////////									playerlbl.setText(XBoxKey.getTexts(player.getValue()));
//////									onKeyChange(player.getKey(),(int)t);
//////								}
////							}:null
////					);
////					dialog.show(stage);
////
////				}
////			};
////			playerlbl.addListener(listener);
////			padNameBtn.addListener(listener);
//			playerRow.add(padNameBtn).spaceBottom(20).spaceRight(10);
//			playerRow.add(playerlbl).spaceBottom(20);
//			playerRow.row();
//
//			tabUi.addItem(padNameBtn);
//			//i++;
//		}
		int rowSpaceBottom=20;
		playerRow.add(playerRow1).colspan(2).spaceBottom(rowSpaceBottom);
		playerRow.row();
		playerRow.add(playerRow2).colspan(2);
		ScrollPane scrollPane=new ScrollPane(playerRow);
		scrollPane.setWidth(ScreenSetting.WORLD_WIDTH);

		table.setFillParent(true);
		table.add(scrollPane).colspan(2).spaceBottom(40);
		table.row();



//		saveResultLbl=new Label("",skin);
//		TextButton saveBtn = new TextButton(TXT.g("save setting, press □"), skin);
//		saveBtn.addListener(new ClickListener() {
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//
//				saveKey();
//			}
//		});
//
//		final TextButton restoreBtn = new TextButton(TXT.g("restore default settings")+" △", skin);
//		restoreBtn.addListener(new ClickListener() {
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
////				gameKey.init();
//////				LocalSaveSettingHelper.saveKnightGameKeyData(gamepad.getPadUniqueName(),null);
////				localSaveSettingHelper2.saveGameKey(gamepad.getPadUniqueName(),null);
//////				if(null!=game.knightGameKey){
//////					game.knightGameKey.remove(gamepad.getPadUniqueName());
//////				}
////				gameKeyMap=gameKey.toMap(gameKeyMap);
////				settingDefault=true;
////				updateUIAfterChangeGamepadSetting();
//				restoreKey();
//			}
//		});


		final TextButton simulateBtn = new TextButton(TXT.g("start simulate"), skin);
		simulateBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				simulating=!simulating;
				if(simulating){
					simulateBtn.setText(TXT.g("stop simulate (simulating...)"));
					gamepadCombo.setDisabled(true);
					settingCombo.setDisabled(true);
				}else{
					simulateBtn.setText(TXT.g("start simulate"));
					gamepadCombo.setDisabled(false);
					settingCombo.setDisabled(false);
				}
			}
		});

		TextButton backBtn = new TextButton(TXT.g("return, press ○"), skin);
		backBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				goToMainPage();
			}
		});

//		table.add(axisLeftLbl).spaceRight(20).spaceBottom(20);
//		table.add(axisLeftGroup).spaceRight(20).spaceBottom(20);
//		table.add(axisLeftAutoBtn).spaceBottom(20);
//		table.row();

//		table.add(axisRightLbl).spaceRight(20).spaceBottom(20);
//		table.add(axisRightGroup).spaceRight(20).spaceBottom(20);
//		table.add(axisRightAutoBtn).spaceBottom(20);
//		table.row();
//		table.add(saveResultLbl).colspan(2).spaceBottom(20);
//		table.row();
//		table.add(saveBtn).colspan(2).spaceBottom(20);
//		table.row();
//		table.add(restoreBtn).colspan(2).spaceBottom(20);
//		table.row();
		table.add(simulateBtn).colspan(2).spaceBottom(20);
		table.row();
		table.add(backBtn).colspan(2);
		tabUi.addItem(simulateBtn);
		tabUi.addItem(backBtn);
//		if(SGCharacter.GODDESSPRINCESSSASHA==sasha.getCharacter()) {
//			stage.addActor(sashaActor);// .colspan(2);
//		}else {
//			stage.addActor(aliceActor);// .colspan(2);
//		}


		try {
			robot = new Robot();

//		if(Input.Keys.Z ==key){
//			return KeyEvent.VK_Z;
			gdxToSysKeyMap=new HashMap<>();
			 SimulateScreen.initGdxToSysKeyMap(gdxToSysKeyMap);

//			keyDowns=new boolean[24];
//			keyDowns=new boolean[24+gameKey.getCombinedMap().size()];

			//鼠标速度大概是5秒移动一个屏幕高度
//			speed= (ScreenSetting.WORLD_HEIGHT/(5*ScreenSetting.FPS));
			speed= (ScreenSetting.WORLD_HEIGHT/(ScreenSetting.FPS));
			scrollSpeed=speed;

		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
	}
	private float speed;
	private float scrollSpeed;
	private void generateKeyLbls(){

		//for (final Map.Entry<String, Integer> player : gameKeyMap.entrySet()) {
		for (final KeySimulateItem player : gameKeyMap2) {
			if(KeySimulateItem.KeyType.NONE!=player.getDstKeyType()
			&&0<player.getDstKey()
			){

			}else{
				continue;
			}
			String lblPrev=KeySimulateItem.KeyType.KEYBOARD==player.getDstKeyType()?"键盘:":"鼠标:";
			String lblText=KeySimulateItem.KeyType.KEYBOARD==player.getDstKeyType()
					?gameKey.getKeyNamesByMask(player.getDstKey())
					:MouseKey.values()[player.getDstKey()].toString();
			final Label playerlbl = new Label(lblPrev+lblText, skin);
			final String keyName=XBoxKey.values()[ player.getSrcKey()].toString();
			keySettingLbls.put(keyName,playerlbl);

//			TextButton padNameBtn = new TextButton(keyName + "->", skin);
			Label padNameBtn = new Label(keyName + "->", skin);
			playerRow1.add(padNameBtn).spaceBottom(20).spaceRight(10);
			playerRow1.add(playerlbl).spaceBottom(20);
			playerRow1.row();

//			tabUi.addItem(padNameBtn);
//			i++;
		}
	}
	private void generateCombineLbls(){

		//for (final Map.Entry<Integer, Integer> player : gameKeyCombinMap.entrySet()) {
		for (final KeySimulateItem player : gameKeyCombinMap2) {
			if(KeySimulateItem.KeyType.NONE!=player.getDstKeyType()
					&&0<player.getDstKey()
			){

			}else{
				continue;
			}
			String lblPrev=KeySimulateItem.KeyType.KEYBOARD==player.getDstKeyType()?"键盘:":"鼠标:";
			String lblText=KeySimulateItem.KeyType.KEYBOARD==player.getDstKeyType()
					?gameKey.getKeyNamesByMask(player.getDstKey())
					:MouseKey.values()[player.getDstKey()].toString();
//			final Label playerlbl = new Label(gameKey.getKeyNamesByMask(player.getValue()), skin);
			final Label playerlbl = new Label(lblPrev+lblText, skin);
			//playerlbls.add(playerlbl);
			this.combinKeySettingLbls.put(player.getSrcKey(),playerlbl);
			final String srcKeyName=XBoxKey.getTexts( player.getSrcKey());
//			final String srcKeyName=XBoxKey.values()[ player.getSrcKey()].toString();
//			TextButton padNameBtn = new TextButton( srcKeyName+ "->", skin);
			Label padNameBtn = new Label( srcKeyName+ "->", skin);
			this.combinKeyBtns.put(player.getSrcKey(),padNameBtn);
			playerRow2.add(padNameBtn).spaceBottom(20).spaceRight(10);
			playerRow2.add(playerlbl).spaceBottom(20);
			playerRow2.row();

			//tabUi.addItem(padNameBtn);
			//i++;
		}
		//String aa="aa";
	}
	private SelectBox<String> getStringSelectBox() {
		SelectBox<String> gamepadCombo=new SelectBox<String>(skin);

		Array<String> gamepadComboItem=new Array<>();
		for ( ISGPS5Gamepad pad : pads) {
			gamepadComboItem.add(pad.getPadUniqueName());
		}
		//gamepadComboItem.add("setting1");
		gamepadCombo.setItems(gamepadComboItem);

		gamepadCombo.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//onSaveClicked();
				//gameKey=new GameKey();gameKeyMap=gameKey.toMap();

				for(ISGPS5Gamepad i:pads){
					if(((SelectBox<String>)actor).getSelected().equals(i.getPadUniqueName())){

						gamepad=i;
						List<String> settings=localSaveSettingHelper2.getSettings(gamepad.getPadUniqueName());
						settingComboItem.clear();
						for ( String pad : settings) {
							settingComboItem.add(pad);
						}
						settingCombo.setItems(settingComboItem);

//						loadGamepadSetting(i);
//						updateUIAfterChangeGamepadSetting();

						break;
					}
				}
			}
		});
		return gamepadCombo;
	}

	private SelectBox<String> getSettingSelectBox() {
		SelectBox<String> gamepadCombo=new SelectBox(skin);

		//Array<String> gamepadComboItem=new Array<>();
//		if(null==gamepadComboItem){
//			gamepadComboItem=new Array<>();
//		}
		settingComboItem=new Array<>();
		List<String> settings=localSaveSettingHelper2.getSettings(gamepad.getPadUniqueName());
		for ( String pad : settings) {
			settingComboItem.add(pad);
		}
		//gamepadComboItem.add("setting1");
		gamepadCombo.setItems(settingComboItem);

		gamepadCombo.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//onSaveClicked();
				//gameKey=new GameKey();gameKeyMap=gameKey.toMap();

//				for(ISGPS5Gamepad i:pads){
//					if(((SelectBox<String>)actor).getSelected().equals(i.getPadUniqueName())){
//						loadGamepadSetting(i);
//						updateUIAfterChangeGamepadSetting();
//						break;
//					}
//				}
				loadGamepadSetting(gamepad);
				updateUIAfterChangeGamepadSetting();
			}
		});
		return gamepadCombo;
	}
	private Robot robot;
	private boolean[] keyDowns;
	private boolean ok=false;
	private void saveKey(){
//				MainMenuScreen.saveKnightGameKeyData(game.knightGameKey);
		gameKey.applyMap(gameKeyMap);
//				LocalSaveSettingHelper.saveKnightGameKeyData(gamepad.getPadUniqueName(),gameKey);
		localSaveSettingHelper2.saveGameKey(gamepad.getPadUniqueName(),gameKey);
//		if(SGPS5Gamepad.class==gamepad.getClass()){
//			SGPS5GamepadSetting tmp=new SGPS5GamepadSetting();
//			tmp.setAxisLeftSpace(axisLeftTmp.x1,axisLeftTmp.x2,axisLeftTmp.y1,axisLeftTmp.y2);
//			tmp.setAxisRightSpace(axisRightTmp.x1,axisRightTmp.x2,axisRightTmp.y1,axisRightTmp.y2);
////					LocalSaveSettingHelper.saveGamepadSettingData(gamepad.getPadUniqueName(),tmp);
//			localSaveSettingHelper3.saveGameKey(gamepad.getPadUniqueName(),tmp);
//		}
		saveResultLbl.setText(TXT.g("save success"));
		settingDefault=false;
//		settingTypeLbl.setText("当前为自定义配置");
	}
	private void restoreKey(){
		gameKey.init();
//				LocalSaveSettingHelper.saveKnightGameKeyData(gamepad.getPadUniqueName(),null);
		localSaveSettingHelper2.saveGameKey(gamepad.getPadUniqueName(),null);
//				if(null!=game.knightGameKey){
//					game.knightGameKey.remove(gamepad.getPadUniqueName());
//				}
		gameKeyMap=gameKey.toMap(gameKeyMap);
		settingDefault=true;
		updateUIAfterChangeGamepadSetting();
	}
	private void onKeyChange(String key,int keyMask){
//		player.setValue((int)t);//这里用iter操作已经很有风险了
//		playerlbl.setText(XBoxKey.getTexts(player.getValue()));
		gameKeyMap.put(key,keyMask);//这里用iter操作已经很有风险了
//		keySettingLbls.get(key).setText(XBoxKey.getTexts(keyMask));
		keySettingLbls.get(key).setText(gameKey.getKeyNamesByMask(keyMask));
	}
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

	Array2<Integer> activeCombine=new Array2<>();
	private float backToMainTime=3;
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

			if(simulating){

//				if(sgcontroller.isCROSS()){
//				}
				gameKey.update();



				//activeCombine激活的组合包含的单键不触发
				activeCombine.clear();

				//不用清0，上一帧留到下一帧计算
//				 mouseX=0;
//				 mouseY=0;
//				//鼠标滚轮
//				 scrollY=0;

				int historyId=0;
//				boolean isPress=false;
//				int gdxKey=0;
				historyId =simulateByMap(true,gameKeyCombinMap2,historyId);


				historyId =simulateByMap(false,gameKeyMap2,historyId);
				if(0!=mouseX||0!=mouseY){
					Point mouseLocation = MouseInfo.getPointerInfo().getLocation();

					//此版本的舍一法有问题，设想 272-0.13=271.8的情况，对结果271.8舍一的话，
					// 变化值会从-0.13变成了-1，反而相差更多了
					//改进的办法是对变化值-0.13舍1法,把不足1的变动累加到下一帧处理
//					robot.mouseMove(((Float)(mouseX+mouseLocation.x)).intValue(),
//							((Float)(mouseY+ mouseLocation.y)).intValue());

					int doX=((Float)(mouseX)).intValue();
					int doY=((Float)(mouseY)).intValue();
					robot.mouseMove(doX+mouseLocation.x,
							doY+ mouseLocation.y);
					mouseX-=doX;
					mouseY-=doY;
				}
				if(0!=scrollY){
//					robot.mouseWheel(((Float)scrollY).intValue());

					int doY=((Float)(scrollY)).intValue();
					robot.mouseWheel(doY);
					scrollY-=doY;
				}


//				//这里未完善,SGPS5Gamepad.getQuickBtnKey里面的组合不完整，比如没有l2 r2 --benjamin todo
//				keyPress(gameKey.getAttack(),gameKey.getGamepad().isSQUARE(),0+historyId,XBoxKey.SQUARE);
//				keyPress(gameKey.getKick(),gameKey.getGamepad().isTRIANGLE(),1+historyId,XBoxKey.TRIANGLE);
//				keyPress(gameKey.getJump(),gameKey.getGamepad().isCROSS(),2+historyId,XBoxKey.CROSS);
//				keyPress(gameKey.getDodge(),gameKey.getGamepad().isROUND(),3+historyId,XBoxKey.ROUND);
//				keyPress(gameKey.getDefend(),gameKey.getGamepad().isL1(),4+historyId,XBoxKey.L1);
//				keyPress(gameKey.getSkill(),gameKey.getGamepad().isR1(),5+historyId,XBoxKey.R1);
//				keyPress(gameKey.getL2(),0.1<gameKey.getGamepad().axisL2(),6+historyId,XBoxKey.L2);
//				keyPress(gameKey.getR2(),0.1<gameKey.getGamepad().axisR2(),7+historyId,XBoxKey.R2);
//				keyPress(gameKey.getUp(),gameKey.getGamepad().isUP(),8+historyId,XBoxKey.UP);
//				keyPress(gameKey.getDown(),gameKey.getGamepad().isDOWN(),9+historyId,XBoxKey.DOWN);
//				keyPress(gameKey.getLeft(),gameKey.getGamepad().isLEFT(),10+historyId,XBoxKey.LEFT);
//				keyPress(gameKey.getRight(),gameKey.getGamepad().isRIGHT(),11+historyId,XBoxKey.RIGHT);
//				keyPress(gameKey.getL3(),gameKey.getGamepad().isL3(),12+historyId,XBoxKey.L3);
//				keyPress(gameKey.getR3(),gameKey.getGamepad().isR3(),13+historyId,XBoxKey.R3);
//				keyPress(gameKey.getBack(),gameKey.getGamepad().isBACK(),14+historyId,XBoxKey.MENU);
//				keyPress(gameKey.getStart(),gameKey.getGamepad().isSTART(),15+historyId,XBoxKey.START);
//				keyPress(gameKey.getStick1Up(),-0.1>gameKey.getGamepad().axisLeftY(),16+historyId,null);
//				keyPress(gameKey.getStick1Down(),0.1<gameKey.getGamepad().axisLeftY(),17+historyId,null);
//				keyPress(gameKey.getStick1Left(),-0.1>gameKey.getGamepad().axisLeftX(),18+historyId,null);
//				keyPress(gameKey.getStick1Right(),0.1<gameKey.getGamepad().axisLeftX(),19+historyId,null);
//				keyPress(gameKey.getStick2Up(),-0.1>gameKey.getGamepad().axisRightY(),20+historyId,null);
//				keyPress(gameKey.getStick2Down(),0.1<gameKey.getGamepad().axisRightY(),21+historyId,null);
//				keyPress(gameKey.getStick2Left(),-0.1>gameKey.getGamepad().axisRightX(),22+historyId,null);
//				keyPress(gameKey.getStick2Right(),0.1<gameKey.getGamepad().axisRightX(),23+historyId,null);
			}else{

				if (null == dialog || (!dialog.isVisible()) || null == dialog.getStage()) {
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
//							else if(null != sgcontroller && sgcontroller.isSQUARE()){
//								saveKey();
//							}else if(null != sgcontroller && sgcontroller.isTRIANGLE()){
//								restoreKey();
//							}
						}
					}
				} else {
					//有弹窗时
					if (0 >= buttonWaitCount) {
						if (null != sgcontroller && sgcontroller.isCROSS() && dialog.isDone()) {
							dialog.getRestoreButton().toggle();
							buttonWaitCount = buttonWait;
						} else if (null != sgcontroller && sgcontroller.isROUND() && dialog.isDone()) {
							dialog.getCloseButton().toggle();
							buttonWaitCount = buttonWait;
						}
					}
				}
			}
		}

		if(null==stage){//tabUi触发页面跳转
			return;
		}

//		ScreenUtils.clear(0, 0, 0.2f, 1);
		ScreenUtils.clear(Color.PINK);
		if (buttonWaitCount > 0) {

			buttonWaitCount -= delta;
		} else if (buttonWaitCount < 0) {
			buttonWaitCount = 0;
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

//		if (xWait <= 0) {
//
//			goToGameBtn.setText(TXT.g("enter the 2d game, or press □"));
//			goToGameD3Btn.setText(TXT.g("enter the 3d game, or press X"));
//		} else {
//			goToGameBtn.setText(TXT.g("enter the 2d game, or press □") + "(等" + ((Float) xWait).intValue() + "秒)");
//			goToGameD3Btn.setText(TXT.g("enter the 3d game, or press X") + "(等" + ((Float) xWait).intValue() + "秒)");
//		}

//		dataLbl.setText("当前选择 "+currentCharacter+": agi("+com.sellgirl.sgJavaHelper.config.SGDataHelper.ScientificNotation(previewSasha.getAgi())
//		+") str("+com.sellgirl.sgJavaHelper.config.SGDataHelper.ScientificNotation(previewSasha.getStr())+")");



//		// 更新舞台逻辑
		stage.act();
		// 绘制舞台
		stage.draw();

//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		stage.act(Gdx.graphics.getDeltaTime());
//		stage.draw();
	}
	HashMap<Integer,Integer> gdxToSysKeyMap=null;
	private int gdxKeyToSysKey(int key){
		return gdxToSysKeyMap.get(key);
//		if(Input.Keys.Z ==key){
//			return KeyEvent.VK_Z;
//		}else
	}

	private float mouseX=0;
	private float mouseY=0;
	//鼠标滚轮
	private float scrollY=0;
//	private boolean test=true;
	private int simulateByMap(boolean isCombine,
							   ISGList<KeySimulateItem> gameKeyMap2,
							   int historyId){

//		//鼠标参数
//		float mouseX=0;
//		float mouseY=0;
//		//鼠标滚轮
//		float scrollY=0;
		////速度大概是5秒移动一个屏幕高度
		//int speed= (int) (ScreenSetting.WORLD_HEIGHT/(5*ScreenSetting.FPS));
		boolean isPress;
		int gdxKey=0;
//		float testNum=0f;
		for(KeySimulateItem j:gameKeyMap2){
			if(isCombine){
				isPress = gameKey.isBtn(j.getSrcKey());
			}else {
				isPress = gameKey.isBtn(1 << j.getSrcKey());
//				testNum=((SGPS5Gamepad) gamepad).getController().getAxis(2);
			}


			//mouse移动不跳过
//					if(isPress==keyDowns[historyId]){
////						return;
//						historyId++;
//						continue;
//					}
			XBoxKey padKey=null;
			if(!isCombine){
				padKey=XBoxKey.values()[j.getSrcKey()];
			}
			int keyMask=isCombine?j.getSrcKey():XBoxKey.values()[j.getSrcKey()].getBinary();
			//如果组合键中包含，就跳过
			if(//null!=padKey
					0!=keyMask
			) {
				boolean ok=false;
				for (Integer i : activeCombine) {
					//String aa="aa";
					if (//SGDataHelper.EnumHasFlag(i, padKey.ordinal())
//							SGDataHelper.EnumHasFlag(i, padKey.getBinary())
					SGDataHelper.EnumHasFlag(i, keyMask)
					) {
//									return;
						ok=true;
						break;
					}
				}
				if(ok){
					historyId++;
					continue;
				}
				if(isPress&&isCombine){
					activeCombine.add(j.getSrcKey());
				}
			}
			if(
					KeySimulateItem.KeyType.KEYBOARD==j.getDstKeyType()
			){
				if(isPress==keyDowns[historyId]){
//						return;
					historyId++;
					continue;
				}
				gdxKey=j.getDstKey();
				if(Input.Keys.UNKNOWN!=gdxKey
				&&gdxToSysKeyMap.containsKey(gdxKey)
				){
					int sysKey=gdxKeyToSysKey( gdxKey);
					if(isPress) {
						robot.keyPress(sysKey);
//						if(test){
//							System.out.println("press"+sysKey+" axis:"+testNum);
//						}
					}else {
						robot.keyRelease(sysKey);
//						if(test){
//							System.out.println("release"+sysKey+" axis:"+testNum);
//						}
					}
					keyDowns[historyId]=isPress;
				}
			}else if(KeySimulateItem.KeyType.MOUSE==j.getDstKeyType()){
				MouseKey dstKeyEnum=MouseKey.values()[j.getDstKey()];
				boolean isAxis=MouseKey.UP==dstKeyEnum||MouseKey.DOWN==dstKeyEnum
						||MouseKey.LEFT==dstKeyEnum||MouseKey.RIGHT==dstKeyEnum
						||MouseKey.scrollUp==dstKeyEnum||MouseKey.scrollDown==dstKeyEnum;
				if(isPress==keyDowns[historyId]
						&&!isAxis
				){
//						return;
					historyId++;
					continue;
				}
				if(MouseKey.NONE!=dstKeyEnum){

					if(isPress) {

						//当dstKey是mouseMove而且srcKey是轴时，要计算轴幅度
						float percent=1f;
						if(isAxis){

							if(isCombine){
//								percent=GameKey.getAxisPercent(gameKey.getGamepad(),padKey);
								percent=GameKey.getAxisPercent2(gameKey.getGamepad(),keyMask);
							}else{
								percent=GameKey.getAxisPercent(gameKey.getGamepad(),padKey);
							}
						}

						switch (dstKeyEnum){
							case UP:
								mouseY-=speed*percent;
								//robot.mouseMove(0, mouseY);
								break;
							case DOWN:
								//y-=speed;
								mouseY+=speed*percent;
//										robot.mouseMove(0, mouseY);
								break;
							case LEFT:
								//x-=speed;
								mouseX-=speed*percent;
//										robot.mouseMove( mouseX,0);
								break;
							case RIGHT:
//										x+=speed;
								mouseX+=speed*percent;
//										if(0!=x||0!=y) {
//											robot.mouseMove(x, y);
//										}
//										robot.mouseMove(mouseX,mouseY);
								break;
							case scrollUp:
								scrollY-=scrollSpeed*percent;
								break;
							case scrollDown:
								scrollY+=scrollSpeed*percent;
								break;
							case buttonLeft:
								robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
								break;
							case buttonRight:
								robot.mousePress(java.awt.event.InputEvent.BUTTON3_DOWN_MASK);
								break;
							case buttonScroll:
								robot.mousePress(java.awt.event.InputEvent.BUTTON2_DOWN_MASK);
								break;
							default:
								break;
						}
					}else {
////								robot.keyRelease(sysKey);
//								int x=0;
//								int y=0;
//								//速度大概是5秒移动一个屏幕高度
//								int speed= (int) (ScreenSetting.WORLD_HEIGHT/(5*ScreenSetting.FPS));
						switch (dstKeyEnum){
							case buttonLeft:
								robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
								break;
							case buttonRight:
								robot.mouseRelease(java.awt.event.InputEvent.BUTTON3_DOWN_MASK);
								break;
							case buttonScroll:
								robot.mouseRelease(java.awt.event.InputEvent.BUTTON2_DOWN_MASK);
								break;
							default:
								break;
						}
					}
					keyDowns[historyId]=isPress;
				}
			}
			historyId++;
		}
//		if(0!=mouseX||0!=mouseY){
//			Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
//			robot.mouseMove(((Float)(mouseX+mouseLocation.x)).intValue(),
//					((Float)(mouseY+ mouseLocation.y)).intValue());
//		}
//		if(0!=scrollY){
////					Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
//			robot.mouseWheel(((Float)scrollY).intValue());
//		}
		return historyId;
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


	public class MyTextInputListener implements TextInputListener {
		@Override
		public void input(String text) {
			System.out.println(text);
		}

		@Override
		public void canceled() {
		}
	}
	public static Map<Integer,Integer> initGdxToSysKeyMap(Map<Integer,Integer> gdxToSysKeyMap){

		gdxToSysKeyMap.put(Input.Keys.A,KeyEvent.VK_A);
		gdxToSysKeyMap.put(Input.Keys.B,KeyEvent.VK_B);
		gdxToSysKeyMap.put(Input.Keys.C,KeyEvent.VK_C);
		gdxToSysKeyMap.put(Input.Keys.D,KeyEvent.VK_D);
		gdxToSysKeyMap.put(Input.Keys.E,KeyEvent.VK_E);
		gdxToSysKeyMap.put(Input.Keys.F,KeyEvent.VK_F);
		gdxToSysKeyMap.put(Input.Keys.G,KeyEvent.VK_G);
		gdxToSysKeyMap.put(Input.Keys.H,KeyEvent.VK_H);
		gdxToSysKeyMap.put(Input.Keys.I,KeyEvent.VK_I);
		gdxToSysKeyMap.put(Input.Keys.J,KeyEvent.VK_J);
		gdxToSysKeyMap.put(Input.Keys.K,KeyEvent.VK_K);
		gdxToSysKeyMap.put(Input.Keys.L,KeyEvent.VK_L);
		gdxToSysKeyMap.put(Input.Keys.M,KeyEvent.VK_M);
		gdxToSysKeyMap.put(Input.Keys.N,KeyEvent.VK_N);
		gdxToSysKeyMap.put(Input.Keys.O,KeyEvent.VK_O);
		gdxToSysKeyMap.put(Input.Keys.P,KeyEvent.VK_P);
		gdxToSysKeyMap.put(Input.Keys.Q,KeyEvent.VK_Q);
		gdxToSysKeyMap.put(Input.Keys.R,KeyEvent.VK_R);
		gdxToSysKeyMap.put(Input.Keys.S,KeyEvent.VK_S);
		gdxToSysKeyMap.put(Input.Keys.T,KeyEvent.VK_T);
		gdxToSysKeyMap.put(Input.Keys.U,KeyEvent.VK_U);
		gdxToSysKeyMap.put(Input.Keys.V,KeyEvent.VK_V);
		gdxToSysKeyMap.put(Input.Keys.W,KeyEvent.VK_W);
		gdxToSysKeyMap.put(Input.Keys.X,KeyEvent.VK_X);
		gdxToSysKeyMap.put(Input.Keys.Y,KeyEvent.VK_Y);
		gdxToSysKeyMap.put(Input.Keys.Z,KeyEvent.VK_Z);
		gdxToSysKeyMap.put(Input.Keys.SPACE,KeyEvent.VK_SPACE);
		gdxToSysKeyMap.put(Input.Keys.SHIFT_LEFT,KeyEvent.VK_SHIFT);
		gdxToSysKeyMap.put(Input.Keys.UP,KeyEvent.VK_UP);
		gdxToSysKeyMap.put(Input.Keys.DOWN,KeyEvent.VK_DOWN);
		gdxToSysKeyMap.put(Input.Keys.LEFT,KeyEvent.VK_LEFT);
		gdxToSysKeyMap.put(Input.Keys.RIGHT,KeyEvent.VK_RIGHT);
		gdxToSysKeyMap.put(Input.Keys.ENTER,KeyEvent.VK_ENTER);
		gdxToSysKeyMap.put(Input.Keys.ESCAPE,KeyEvent.VK_ESCAPE);
		gdxToSysKeyMap.put(Input.Keys.BACKSPACE,KeyEvent.VK_BACK_SPACE);
		gdxToSysKeyMap.put(Input.Keys.ALT_LEFT,KeyEvent.VK_ALT);
		gdxToSysKeyMap.put(Input.Keys.CONTROL_LEFT,KeyEvent.VK_CONTROL);
		gdxToSysKeyMap.put(Input.Keys.TAB,KeyEvent.VK_TAB);
		gdxToSysKeyMap.put(Input.Keys.PAGE_UP,KeyEvent.VK_PAGE_UP);
		gdxToSysKeyMap.put(Input.Keys.PAGE_DOWN,KeyEvent.VK_PAGE_DOWN);
		gdxToSysKeyMap.put(Input.Keys.NUM_1,KeyEvent.VK_1);
		gdxToSysKeyMap.put(Input.Keys.NUM_2,KeyEvent.VK_2);
		gdxToSysKeyMap.put(Input.Keys.NUM_3,KeyEvent.VK_3);
		gdxToSysKeyMap.put(Input.Keys.NUM_4,KeyEvent.VK_4);
		gdxToSysKeyMap.put(Input.Keys.NUM_5,KeyEvent.VK_5);
		gdxToSysKeyMap.put(Input.Keys.NUM_6,KeyEvent.VK_6);
		gdxToSysKeyMap.put(Input.Keys.NUM_7,KeyEvent.VK_7);
		gdxToSysKeyMap.put(Input.Keys.NUM_8,KeyEvent.VK_8);
		gdxToSysKeyMap.put(Input.Keys.NUM_9,KeyEvent.VK_9);
		gdxToSysKeyMap.put(Input.Keys.NUM_0,KeyEvent.VK_0);
		gdxToSysKeyMap.put(Input.Keys.F1,KeyEvent.VK_F1);
		gdxToSysKeyMap.put(Input.Keys.F2,KeyEvent.VK_F2);
		gdxToSysKeyMap.put(Input.Keys.F3,KeyEvent.VK_F3);
		gdxToSysKeyMap.put(Input.Keys.F4,KeyEvent.VK_F4);
		gdxToSysKeyMap.put(Input.Keys.F5,KeyEvent.VK_F5);
		gdxToSysKeyMap.put(Input.Keys.F6,KeyEvent.VK_F6);
		gdxToSysKeyMap.put(Input.Keys.F7,KeyEvent.VK_F7);
		gdxToSysKeyMap.put(Input.Keys.F8,KeyEvent.VK_F8);
		gdxToSysKeyMap.put(Input.Keys.F9,KeyEvent.VK_F9);
		gdxToSysKeyMap.put(Input.Keys.F10,KeyEvent.VK_F10);
		gdxToSysKeyMap.put(Input.Keys.F11,KeyEvent.VK_F11);
		gdxToSysKeyMap.put(Input.Keys.F12,KeyEvent.VK_F12);
		return gdxToSysKeyMap;
	}

}
