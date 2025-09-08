//package com.sellgirl.gamepadtool.screen;
//
//import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
//import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
//import static com.badlogic.gdx.scenes.scene2d.actions.Actions.touchable;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input.TextInputListener;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.controllers.Controller;
//import com.badlogic.gdx.controllers.Controllers;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.Touchable;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
//import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.ui.TextField;
//import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.utils.viewport.StretchViewport;
//import com.sellgirl.gamepadtool.GamepadTool;
//import com.sellgirl.gamepadtool.MainMenuScreen;
//import com.sellgirl.gamepadtool.ScreenSetting;
//import com.sellgirl.gamepadtool.language.TXT;
//import com.sellgirl.gamepadtool.model.KeySimulateItem;
//import com.sellgirl.gamepadtool.model.MouseKey;
//import com.sellgirl.sgGameHelper.SGLibGdxHelper;
//import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
//import com.sellgirl.sgGameHelper.gamepad.SGPS5Gamepad;
//import com.sellgirl.sgGameHelper.gamepad.SGTouchGamepad;
//import com.sellgirl.sgGameHelper.gamepad.XBoxKey;
//import com.sellgirl.sgGameHelper.list.Array2;
//import com.sellgirl.sgGameHelper.tabUi.SGTabUpDownMap;
//import com.sellgirl.sgGameHelper.tabUi.TabUi;
//import com.sellgirl.sgJavaHelper.SGAction;
//import com.sellgirl.sgJavaHelper.SGRef;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//
////import com.sellgirl.sgJavaHelper.config.SGDataHelper;
//
///**
// * 旧版本用的是TabUpDownMap, 想改为新的复合Map方式
// */
//@Deprecated
//public class KeySettingScreenOld implements Screen {
//
//	private Stage stage;
//	private Table table;
//	final GamepadTool game;
//	SpriteBatch batch;
//
////	// OrthographicCamera camera;
////	@Deprecated
////	Controller controller;
//	ISGPS5Gamepad sgcontroller;
////	IApiUrl url = null;
//	//SashaData sasha;
////	SashaData previewSasha;
//	// Label dataLbl;
//	public Skin skin;
////	String code = "";
////
////	TextField text1;
//
//	private Label axisLeftLbl;
//	TextField axisLeftX1TF;
//	TextField axisLeftX2TF;
//	TextField axisLeftY1TF;
//	TextField axisLeftY2TF;
//	TextButton axisLeftAutoBtn;
//	boolean axisLeftCalculating=false;
//	float axisLeftCalculatingTime=0;
//	private SGPS5Gamepad.AxisSpace axisLeftTmp=new SGPS5Gamepad.AxisSpace();
//
//	private Label axisRightLbl;
//	TextField axisRightX1TF;
//	TextField axisRightX2TF;
//	TextField axisRightY1TF;
//	TextField axisRightY2TF;
//	TextButton axisRightAutoBtn;
//	boolean axisRightCalculating=false;
//	float axisRightCalculatingTime=0;
//	private SGPS5Gamepad.AxisSpace axisRightTmp=new SGPS5Gamepad.AxisSpace();
//	Label saveResultLbl;
//	Color successMsgColor;
//	String tmpEmail;
//	// TextureRegion paycode;
//
//	// public PurchaseManager wechatPurchaseManager;
//	float xWait = 4;
//	float oWait = 1;
//	float squartWait = 1;
//
//	private final float buttonWait=0.4f;
//	private float buttonWaitCount=0.4f;
////	private KofGameScreen screen = null;
////	private SGCharacter currentCharacter = SGCharacter.GODDESSPRINCESSSASHA;
//
////	 TextButton sashaBtn ;
////	 TextButton aliceBtn ;
//
////	private float nextWait = 0;
////	private int step = 1;
////
////	private float actorBeginX;
////	TextButton goToGameBtn;
////	TextButton goToGameD3Btn;
//	// AssetManager manager;
//	// private Screen lastScreen = null;
//	ArrayList<ISGPS5Gamepad> pads = new ArrayList<ISGPS5Gamepad>();
////	ArrayList<KofPlayer> players = new ArrayList<KofPlayer>();
////	ArrayList<KofPlayer> pcplayers = new ArrayList<KofPlayer>();
//	//ArrayList<Label> playerlbls = new ArrayList<Label>();
////	ArrayList<TextButton> keySettingLbls = null;
//
//	HashMap<String,Label> keySettingLbls = null;
//	HashMap<String,Label> mouseKeySettingLbls = null;
//
//	HashMap<String,Label> combinKeyLbls = null;
////	HashMap<String,Label> combinKeySettingLbls = null;
//	HashMap<Integer,Label> combinKeySettingLbls = null;
//	HashMap<Integer,Label> combineMouseKeySettingLbls = null;
//	HashMap<Integer,TextButton> combinKeyBtns=null;
//	//Label settingTypeLbl=null;//多配置的方式似乎不需要默认
//	IKnightSashaGameKey gameKey=null;
//	@Deprecated
//	HashMap<String,Integer> gameKeyMap=null;
//	Array2<KeySimulateItem> gameKeyMap2=null;
//
////	HashMap<Integer,Integer> gameKeyCombinMap=null;
//	Array2<KeySimulateItem> gameKeyCombinMap2=null;
//	/**
//	 * 当前正在设定的手柄
//	 */
//	ISGPS5Gamepad gamepad;
//	boolean settingDefault=true;
//	KeySettingDialog dialog;
//	MouseKeySettingDialog mouseKeyDialog;
//	GamepadKeySettingDialog gamepadKeyDialog;
//	TabUi tabUi;
//	SGTabUpDownMap tabMap;
//	Array<Actor> tabMapItem;
////
////	public SelectCharacterScreen(final SashaGame game, final SashaData sasha
////			) {
//
//	LocalSaveSettingHelper2 localSaveSettingHelper2=null;
//	//LocalSaveSettingHelper3 localSaveSettingHelper3=null;
//
//	/**
//	 * 使b处于a c之间
//	 * @param a
//	 * @param b
//	 * @param c
//	 * @return
//	 */
//	private float min(float a,float b,float c){
//		return Math.min( Math.max(a,b),c);
//	}
//	public void loadGamepadSetting(ISGPS5Gamepad gamepad){
//
////		gameKey=localSaveSettingHelper2.readGameKey(gamepad.getPadUniqueName(),gamepad.getClass());
////		gameKey=localSaveSettingHelper2.readGameKey(gamepad.getPadUniqueName(),SGKeyboardGamepad.class==gamepad.getClass()?new GameKeyKeyboard():new GameKey());
////		gameKey=localSaveSettingHelper2.readGameKey(gamepad.getPadUniqueName(),new GameKey());
//		String setting=settingCombo.getSelected();
//		gameKey=localSaveSettingHelper2.readGameKey(gamepad.getPadUniqueName()+setting,new GameKey());
//		if(null==gameKey){
////			gameKey=SGKeyboardGamepad.class==gamepad.getClass()?new GameKeyKeyboard(): new GameKey();
//			gameKey= new GameKey();
//			settingDefault=true;
//		}else{
////			if(gameKey.getClass()!=(SGKeyboardGamepad.class==gamepad.getClass()?GameKeyKeyboard.class: GameKey.class)){
////				gameKey=SGKeyboardGamepad.class==gamepad.getClass()?new GameKeyKeyboard(): new GameKey();
////			}
//			settingDefault=false;
//		}
//		if(null==gameKeyMap){
////			gameKeyMap=new HashMap<>();
//			gameKeyMap=new LinkedHashMap<>();
//		}
//		gameKeyMap=gameKey.toMap(gameKeyMap);
//
////		if(null==gameKeyMap2){
//////			gameKeyMap=new HashMap<>();
////			gameKeyMap2=new Array2<KeySimulateItem>();
////		}
////		gameKeyMap2=gameKey.getMap(gameKeyMap2);
//
//		if(null==gameKeyMap2){
//			gameKeyMap2=new Array2<KeySimulateItem>();
//		}else{
//			gameKeyMap2.clear();
//		}
//		if(null==gameKey.getKeyMap()){
//			//gameKeyMap2=new Array2<KeySimulateItem>();
//		}else{
//			for(KeySimulateItem i:gameKey.getKeyMap()){
//				gameKeyMap2.add(i);
//			}
//			//gameKeyMap2=gameKey.getKeyMap();
//		}
//		if(gameKeyMap2.isEmpty()){
//			for(KeySimulateItem i:GameKey.intDefaultKeyMap(new ArrayList<KeySimulateItem>())){
//				gameKeyMap2.add(i);
//			}
//			//gameKeyMap2=GameKey.intDefaultKeyMap(gameKeyMap2);
//		}
//
////		if(null==gameKey.getCombinedMap()){
////			gameKeyCombinMap=new LinkedHashMap<>();
////		}else{
////			gameKeyCombinMap=new LinkedHashMap<>(gameKey.getCombinedMap());
////		}
//
//		if(null==gameKeyCombinMap2){
//			gameKeyCombinMap2=new Array2<KeySimulateItem>();
//		}else{
//			gameKeyCombinMap2.clear();
//		}
//		if(null==gameKey.getCombinedMap2()){
//		}else{
//			for(KeySimulateItem i:gameKey.getCombinedMap2()){
//				gameKeyCombinMap2.add(i);
//			}
//		}
//
////		gameKeyCombinMap=gameKey.getCombinedMap();
//
//		this.gamepad=gamepad;
//		if(SGPS5Gamepad.class==gamepad.getClass()){
//			SGPS5Gamepad tmpGamepad=(SGPS5Gamepad) this.gamepad;
//			SGPS5Gamepad.AxisSpace tmpAL= tmpGamepad.getAxisLeftSpace();
//			SGPS5Gamepad.AxisSpace tmpAR= tmpGamepad.getAxisRightSpace();
//			tmpGamepad.setAxisLeftSpace(min(-0.9f,tmpAL.x1,-0.5f),min(0.5f,tmpAL.x2,0.9f),min(-0.9f,tmpAL.y1,-0.5f),min(0.5f,tmpAL.y2,0.9f));
////			tmpGamepad.setAxisRightSpace(Math.min(-0.5f,tmpAR.x1),Math.max(0.5f,tmpAR.x2),Math.min(-0.5f,tmpAR.y1),Math.max(0.5f,tmpAR.y2));
//			tmpGamepad.setAxisRightSpace(min(-0.9f,tmpAR.x1,-0.5f),min(0.5f,tmpAR.x2,0.9f),min(-0.9f,tmpAR.y1,-0.5f),min(0.5f,tmpAR.y2,0.9f));
//			tmpGamepad.setL2Space(min(0.5f,tmpGamepad.getL2Space(),0.9f));
//			tmpGamepad.setR2Space(min(0.5f,tmpGamepad.getR2Space(),0.9f));
//		}
////		if(SGPS5Gamepad.class==gamepad.getClass()){
////			SGPS5Gamepad tmp=(SGPS5Gamepad) gamepad;
////			axisLeftTmp.x1=tmp.getAxisLeftSpace().x1;
////			axisLeftTmp.x2=tmp.getAxisLeftSpace().x2;
////			axisLeftTmp.y1=tmp.getAxisLeftSpace().y1;
////			axisLeftTmp.y2=tmp.getAxisLeftSpace().y2;
////		}else{
////
////			axisLeftTmp.x1=0;
////			axisLeftTmp.x2=0;
////			axisLeftTmp.y1=0;
////			axisLeftTmp.y2=0;
////		}
////
////		if(SGPS5Gamepad.class==gamepad.getClass()){
////			SGPS5Gamepad tmp=(SGPS5Gamepad) gamepad;
////			axisRightTmp.x1=tmp.getAxisRightSpace().x1;
////			axisRightTmp.x2=tmp.getAxisRightSpace().x2;
////			axisRightTmp.y1=tmp.getAxisRightSpace().y1;
////			axisRightTmp.y2=tmp.getAxisRightSpace().y2;
////		}else{
////
////			axisRightTmp.x1=0;
////			axisRightTmp.x2=0;
////			axisRightTmp.y1=0;
////			axisRightTmp.y2=0;
////		}
//	}
//	public void updateUIAfterChangeGamepadSetting(){
//
//		if(null!=saveResultLbl){
//			saveResultLbl.setText("");}
//		if(null!=keySettingLbls){
//
////			for (Map.Entry<String, Integer> m1 : gameKeyMap.entrySet()) {
//			for (KeySimulateItem m1 : gameKeyMap2) {
//				if(m1.getDstKeyType()== KeySimulateItem.KeyType.KEYBOARD
//				&&0!=m1.getDstKey()
//				){
//					keySettingLbls.get(XBoxKey.values()[ m1.getSrcKey()].toString())
//							.setText( gameKey.getKeyNamesByMask(m1.getDstKey()) );
//				}else{
//					keySettingLbls.get(XBoxKey.values()[ m1.getSrcKey()].toString())
//							.setText("映射键盘");
//				}
//				if(m1.getDstKeyType()== KeySimulateItem.KeyType.MOUSE
//						&&0!=m1.getDstKey()
//				){
//					mouseKeySettingLbls.get(XBoxKey.values()[ m1.getSrcKey()].toString())
//							.setText(MouseKey.values()[m1.getDstKey()].toString() );
//				}else{
//					mouseKeySettingLbls.get(XBoxKey.values()[ m1.getSrcKey()].toString())
//							.setText("映射鼠标");
//				}
//			}
//		}
////		if(gameKeyCombinMap.isEmpty()){
////
////		}else {
////			int more = combinKeySettingLbls.size() - gameKeyCombinMap.size();
////			ArrayList<Integer> keys = new ArrayList<>();
////		if(0<more){
////			for(int i=combinKeySettingLbls.size()-1;combinKeySettingLbls.size()-more<=i;i--){
////				combinKeySettingLbls.get(i).remove();
////				combinKeySettingLbls.remove(i);
////			}
////		}
////			int cnt = 0;
////			for (Map.Entry<Integer, Label> m1 : combinKeySettingLbls.entrySet()) {
////				keys.add(m1.getKey());
////				cnt++;
////				if (cnt == more) {
////					break;
////				}
////			}
////			for (Integer i : keys) {
////				combinKeySettingLbls.get(i).remove();
////				combinKeySettingLbls.remove(i);
////			}
////		}
//		//由于按钮事件的参数不一样，所以combine的label还是全清再加才对的
//
////		for (Map.Entry<Integer, Label> m1 : combinKeySettingLbls.entrySet()) {
////			//keys.add(m1.getKey());
//////			cnt++;
//////			if (cnt == more) {
//////				break;
//////			}
////			combinKeySettingLbls.get(m1.getKey()).remove();
//////			combinKeySettingLbls.get(m1.getKey()).getParent().remove();
////			combinKeyBtns.get(m1.getKey()).remove();
////		}
//////		for (Integer i : keys) {
//////			combinKeySettingLbls.get(i).remove();
////////			combinKeySettingLbls.remove(i);
//////		}
//		int oldCombineNum=combinKeySettingLbls.size();
//		combinKeySettingLbls.clear();
//		combinKeyBtns.clear();
//		playerRow2.clear();
////		for(int i=tabMapItem.size-4;i>=tabMapItem.size-4-oldCombineNum+1;i--){
////			tabMapItem.removeIndex(i);//这样删除，索引报错，原因未明
////		}
//		if(0<oldCombineNum) {
//			tabMapItem.removeRange(tabMapItem.size - 4 - oldCombineNum + 1, tabMapItem.size - 4);
//			tabMap.setItem(tabMapItem);
//		}
//
//		generateCombineLbls();
//
////		axisLeftX1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.x1,3)));
////		axisLeftX2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.x2,3)));
////		axisLeftY1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.y1,3)));
////		axisLeftY2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisLeftTmp.y2,3)));
////		axisRightX1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.x1,3)));
////		axisRightX2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.x2,3)));
////		axisRightY1TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.y1,3)));
////		axisRightY2TF.setText(String.valueOf(SGDataHelper.getFloatPrecision(axisRightTmp.y2,3)));
////		if(null!=settingTypeLbl){
////			if(settingDefault){
////				settingTypeLbl.setText("当前为默认配置");
////			}else{
////				settingTypeLbl.setText("当前为自定义配置");
////			}
////		}
//	}
//	public KeySettingScreenOld(final GamepadTool game
//                               //, SashaData sasha
//			) {
//		localSaveSettingHelper2=new LocalSaveSettingHelper2();
//		//localSaveSettingHelper3=new LocalSaveSettingHelper3();
//		this.game = game;
//
////		if (sasha.getKill() > 0) {
////			xWait = 1;
////		}
//
//		batch = new SpriteBatch();
//
//		toScrollWait=ScreenSetting.SPF;
//		toScrollWaitCount=toScrollWait;
////		skin = MainMenuScreen.getSkin();
////		skin.add("default", MainMenuScreen.getButtonStyle(skin));
////		skin.add("default", MainMenuScreen.getLabelStyle(skin));
////		skin.add("default", MainMenuScreen.getTextFieldStyle(skin));
////		skin.add("default", MainMenuScreen.getWindowStyle(skin));
//////		skin.add("default", MainMenuScreen.getListStyle(skin));
////		skin.add("default", MainMenuScreen.getSelectBoxStyle(skin));
//
//		skin=MainMenuScreen.getSkin2(game.font);
//
////		for (Controller controller : Controllers.getControllers()) {
////			if (SGControllerName.XInputController.equals(controller.getName())) {
////				this.controller = controller;
////				this.controller.addListener(new SGXInputControllerListener());
////			}
////
////		}
//
////		this.controller = SGLibGdxHelper.getGamepad();
////		if (null != controller) {
////			sgcontroller=new SGPS5Gamepad(controller);
////			this.controller.addListener(new SGXInputControllerListener());
////		}
////
////		players = new ArrayList<KofPlayer>(pads.size() + 1);
//		int i = 0;
//		for (Controller controller : Controllers.getControllers()) {
//			pads.add(new SGPS5Gamepad(controller));
////			KofPlayer player = new KofPlayer();
////			player.setPlayName("p" + (1 + i));
////			players.add(player);
//			i++;
//		}
////		if(game.hasKeyboard){
////			pads.add(new SGKeyboardGamepad());
//////			KofPlayer tmpplayer = new KofPlayer();
//////			tmpplayer.setPlayName("p" + (1 + i));
////			//players.add(tmpplayer);
////			i++;
////		}
////		if(game.showTouchpad){
////			//SGTouchGamepad touchPad= new SGTouchGamepad();
////			pads.add(new SGTouchGamepad());
//////			KofPlayer tmpplayer = new KofPlayer();
//////			tmpplayer.setPlayName("p" + (1 + i));
////			//players.add(tmpplayer);
////			i++;
////		}
//		if(0<i){
//			ok=true;
//		}else{
//			return;
//		}
//		gamepad=pads.get(0);
////		this.sgcontroller = pads.get(0);//主控
//		this.sgcontroller = SGLibGdxHelper.getSGGamepad();
//
//
//		//loadGamepadSetting(pads.get(0));
//		// initSG();
//
//		//this.sasha = sasha;
//
//
//
//		// stage = new Stage(new ExtendViewport(800, 450));//如果二维码变型不能用时,就改为这句的方式
//		stage = new Stage(new StretchViewport(ScreenSetting.WORLD_WIDTH, ScreenSetting.WORLD_HEIGHT));
//		Gdx.input.setInputProcessor(stage);
//
//
//		prepareUI();
//
////		loadGamepadSetting(pads.get(0));
//	}
//
//	private final int pcCount = 0;
//	//private ArrayList<Actor> pcList = new ArrayList<Actor>();
//
//	private static class AxisGroup{
//
//	}
////	TextButton simulateBtn;
//	//boolean simulating=false;
//	// private Table table=null;
//	private SelectBox<String> settingCombo=null;
//	private Array<String> settingComboItem=null;
//	private Table playerRow;
//	private Table playerRow2;
//	ScrollPane scrollPane;
//	private void prepareUI() {
//
//
//		// Create a table that fills the screen. Everything else will go inside this
//		// table.
//		table = new Table();
//		tabUi=new TabUi();
//		tabMap=new SGTabUpDownMap();
//		tabMapItem=new Array2<>();
//		// table.setFillParent(true);
//		// table.defaults().pad(5);
//		int valueCol=5;
////		table.setX(ScreenSetting.WORLD_WIDTH*0.5f );//300);
//////		table.setY(ScreenSetting.WORLD_HEIGHT - 200);
////		table.setY(ScreenSetting.WORLD_HEIGHT*0.5f);
//
//		stage.addActor(table);
//
//
//
////		final Table playerRow = new Table();
//		playerRow = new Table();
//		playerRow2 = new Table();
//
//		SelectBox<String> gamepadCombo = getStringSelectBox();
////		SelectBox<String>
//				settingCombo = getSettingSelectBox();
//		final TextField settingTF=new TextField("",skin);
//		TextButton addSettingBtn=new TextButton(TXT.g("addSetting"),skin);
//		TextButton delSettingBtn=new TextButton(TXT.g("delSetting"),skin);
//		addSettingBtn.addListener(new ClickListener() {
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				if(null!=settingTF.getText()&&!"".equals(settingTF.getText())) {
//					boolean exist=false;
//					for (int i = settingComboItem.size - 1; 0 <= i; i--) {
//						if (settingTF.getText().equals(settingComboItem.get(i))) {
//							exist=true;
//							break;
//						}
//					}
//					if(!exist) {
//						settingComboItem.add(settingTF.getText());
//						settingCombo.setItems(settingComboItem);
//						settingCombo.setSelected(settingTF.getText());
//					}
//				}
//			}
//		});
//		delSettingBtn.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				String setting=settingCombo.getSelected();
//				localSaveSettingHelper2.saveGameKey(gamepad.getPadUniqueName(),setting,null);
//				if(1<settingComboItem.size) {
//					for (int i = settingComboItem.size - 1; 0 <= i; i--) {
//						if (setting.equals(settingComboItem.get(i))) {
//							settingComboItem.removeIndex(i);
//							break;
//						}
//					}
//					settingCombo.setItems(settingComboItem);
//				}
//				saveResultLbl.setText("delete setting success");
//			}
//		});
//
//		//settingTypeLbl=new Label( settingDefault?"当前为默认配置":"当前为自定义配置",skin);
//		table.add(gamepadCombo).colspan(valueCol+1).spaceBottom(20);
//		table.row();
//		table.add(settingCombo).spaceRight(20).spaceBottom(20);
//		table.add(settingTF).spaceRight(20).spaceBottom(20);
//		table.add(addSettingBtn).spaceRight(20).spaceBottom(20);
//		table.add(delSettingBtn).spaceRight(20).spaceBottom(20);
//
//		table.row();
////		table.add(settingTypeLbl).colspan(valueCol+1).spaceBottom(20);
////		table.row();
//
//		loadGamepadSetting(pads.get(0));
//
////		tabUi.addItem(gamepadCombo);
////		tabUi.addItem(settingCombo);
////		tabMap.addItem(gamepadCombo);
////		tabMap.addItem(settingCombo);
//		tabMapItem.add(gamepadCombo);
//		tabMapItem.add(settingCombo);
//
//
////		table.add(playerRow).colspan(valueCol+1).spaceBottom(40);
////		table.row();
//
//		int i = 0;
//
//
//		dialog=new KeySettingDialog(KeySettingScreenOld.this//, //sasha,
//				);
//		mouseKeyDialog=new MouseKeySettingDialog(KeySettingScreenOld.this//, //sasha,
//		);
//		gamepadKeyDialog=new GamepadKeySettingDialog(KeySettingScreenOld.this//, //sasha,
//		);
////		keySettingLbls=new ArrayList<>();
//		keySettingLbls=new HashMap<>();
//		mouseKeySettingLbls=new HashMap<>();
////		for (final Map.Entry<String, Integer> player : gameKeyMap.entrySet()) {
//		for (final KeySimulateItem player : gameKeyMap2) {
////			final Label playerlbl = new Label(gameKey.getKeyNamesByMask(player.getValue()), skin);
////			if(XBoxKey.stick1Up.ordinal()== player.getSrcKey()){
////				String aa="aa";
////			}
//			final Label playerlbl = new Label(KeySimulateItem.KeyType.KEYBOARD==player.getDstKeyType()&&0!=player.getDstKey()? gameKey.getKeyNamesByMask(player.getDstKey()):"映射键盘", skin);
//			final Label mouselbl = new Label(KeySimulateItem.KeyType.MOUSE==player.getDstKeyType()&&0!=player.getDstKey()?MouseKey.values()[player.getDstKey()].toString():"映射鼠标"  , skin);
//			//playerlbls.add(playerlbl);
//
//			final String srcKeyName=XBoxKey.values()[player.getSrcKey()].toString();
//			keySettingLbls.put(srcKeyName,playerlbl);
//			mouseKeySettingLbls.put(srcKeyName,mouselbl);
//
//			TextButton padNameBtn = new TextButton(srcKeyName + ":", skin);
//			//// final SGRef<Integer> iRef=new SGRef<Integer>(i);
//			//final int i2 = i;
//			ClickListener listener = new ClickListener() {
//
//				@Override
//				public void clicked(InputEvent event, float x, float y) {
//
//					dialog.init(//KeySettingScreen.this, //sasha,
//							//player, pcCount,
//							gamepad,
//							srcKeyName,
//							new SGAction<String,Integer,Object>() {
//
//								@Override
//								public void go(String s, Integer integer, Object o) {
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
//				}
//			};
//
//			ClickListener mouseListener = new ClickListener() {
//
//				@Override
//				public void clicked(InputEvent event, float x, float y) {
//
//					mouseKeyDialog.init(//KeySettingScreen.this, //sasha,
//							//player, pcCount,
//							gamepad,
//							player.getSrcKey(),
//							srcKeyName,
//							new SGAction<String,Integer,Integer>() {
//
//								@Override
//								public void go(String s, Integer integer, Integer o) {
//									onMouseKeyChange(s,integer,o);
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
//					);
//					mouseKeyDialog.show(stage);
//
//				}
//			};
//			playerlbl.addListener(listener);
//			mouselbl.addListener(mouseListener);
//			padNameBtn.addListener(listener);
//			playerRow.add(padNameBtn).spaceBottom(20).spaceRight(10);
//			playerRow.add(playerlbl).spaceBottom(20).spaceRight(10);
//			playerRow.add(mouselbl).spaceBottom(20);
//			playerRow.row();
//
////			tabUi.addItem(padNameBtn);
////			tabMap.addItem(padNameBtn);
//			tabMapItem.add(padNameBtn);
//
//			i++;
//		}
//
//		combinKeySettingLbls=new HashMap<>();
//		combineMouseKeySettingLbls=new HashMap<>();
////		combinKeySettingLbls=new LinkedHashMap<>();
//		combinKeyBtns=new HashMap<>();
//		generateCombineLbls();
////		for (final Map.Entry<Integer, Integer> player : gameKeyCombinMap.entrySet()) {
////			final Label playerlbl = new Label(gameKey.getKeyNamesByMask(player.getValue()), skin);
////			//playerlbls.add(playerlbl);
////		   this.combinKeySettingLbls.put(player.getKey(),playerlbl);
////
////		   final String keyName=XBoxKey.getTexts( player.getKey());
////			TextButton padNameBtn = new TextButton( keyName+ ":", skin);
////			//// final SGRef<Integer> iRef=new SGRef<Integer>(i);
////			//final int i2 = i;
////			ClickListener listener = new ClickListener() {
////
////				@Override
////				public void clicked(InputEvent event, float x, float y) {
////					 dialog.init(//KeySettingScreen.this, //sasha,
////							//player, pcCount,
////							gamepad,
////							keyName,//player.getKey(),
////							new SGAction<String,Integer,Object>() {
////
////								@Override
////								public void go(String s, Integer integer, Object o) {
////
////								 onGamepadKeySettingChange(player.getKey(),integer);
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
////			playerRow.add(padNameBtn).spaceBottom(20).spaceRight(10);
////			playerRow.add(playerlbl).spaceBottom(20);
////			playerRow.row();
////
////			tabUi.addItem(padNameBtn);
////			//i++;
////		}
//		playerRow.add(playerRow2).colspan(3);
//		scrollPane=new ScrollPane(playerRow);
//		scrollPane.setWidth(ScreenSetting.WORLD_WIDTH);
//
//		table.setFillParent(true);
//		table.add(scrollPane).colspan(valueCol+1).spaceBottom(40);
//		table.row();
//
//
//		combinKeyLbls=new LinkedHashMap<>();
////		combinKeySettingLbls=new LinkedHashMap<>();
//		TextButton combinKeyBtn=new TextButton(TXT.g("add combin key"), skin);
//		combinKeyBtn.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//
//				gamepadKeyDialog.init(//KeySettingScreen.this, //sasha,
//						//player, pcCount,
//						gamepad,//player.getKey(),
//						new SGAction<String,Integer,Object>() {
//
//							@Override
//							public void go(String s, Integer integer, Object o) {
////								onKeyChange(s,integer);
//								onGamepadKeyChange(s,integer);
//								if (SGTouchGamepad.class ==gamepad.getClass()) {
//									((SGTouchGamepad) gamepad).remove();
//								}
//							}
//						},
//						SGTouchGamepad.class ==gamepad.getClass()?new SGAction<String,Integer,Object>() {
//
//							@Override
//							public void go(String s, Integer integer, Object o) {
//								if (SGTouchGamepad.class ==gamepad.getClass()) {
//									((SGTouchGamepad) gamepad).remove();
//								}
//							}
//
//						}:null
//				);
////				dialog.show(stage);
//				gamepadKeyDialog.show(stage);
//
//			}
//		});
//
//		saveResultLbl=new Label("",skin);
//		successMsgColor=new Color(saveResultLbl.getColor());
//		TextButton saveBtn = new TextButton(TXT.g("save setting, press □"), skin);
//		saveBtn.addListener(new ClickListener() {
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//
//				if (0 >= buttonWaitCount) {
//					saveKey();
//					buttonWaitCount=buttonWait;
//				}
//			}
//		});
//
//
//
//
//		TextButton backBtn = new TextButton(TXT.g("return, press ○"), skin);
//		backBtn.addListener(new ClickListener() {
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				goToMainPage();
//			}
//		});
//
//		int spaceBottom=20;
//		int spaceRight=10;
//		table.add(saveResultLbl).colspan(4).spaceBottom(spaceBottom);
//		table.row();
//		table.add(combinKeyBtn).colspan(4).spaceBottom(spaceBottom);
//		table.row();
//		table.add(saveBtn).colspan(4).spaceBottom(spaceBottom)//.spaceRight(spaceRight)
//		;
//		//table.add(saveResultLbl).colspan(3).spaceBottom(spaceBottom);
//		table.row();
//
//		table.add(backBtn).colspan(4);
//
////		tabUi.addItem(combinKeyBtn);
////		tabUi.addItem(saveBtn);
////		tabUi.addItem(backBtn);
////		tabMap.addItem(combinKeyBtn);
////		tabMap.addItem(saveBtn);
////		tabMap.addItem(backBtn);
//		tabMapItem.add(combinKeyBtn);
//		tabMapItem.add(saveBtn);
//		tabMapItem.add(backBtn);
//		tabMap.setItem(tabMapItem);
//		tabUi.setTabMap(tabMap);
//		isSaveBtnAdded=true;
////		if(SGCharacter.GODDESSPRINCESSSASHA==sasha.getCharacter()) {
////			stage.addActor(sashaActor);// .colspan(2);
////		}else {
////			stage.addActor(aliceActor);// .colspan(2);
////		}
//
//
////		try {
////			robot = new Robot();
////		} catch (AWTException e) {
////			throw new RuntimeException(e);
////		}
//	}
//
////	private void generateRowByKeyMap(ISGList<KeySimulateItem> gameKeyMap2,
////	   Map<String,Label> keySettingLbls,
////		Map<String,Label> mouseKeySettingLbls
////	){
////		for (final KeySimulateItem player : gameKeyMap2) {
//////			final Label playerlbl = new Label(gameKey.getKeyNamesByMask(player.getValue()), skin);
//////			if(XBoxKey.stick1Up.ordinal()== player.getSrcKey()){
//////				String aa="aa";
//////			}
////			final Label playerlbl = new Label(KeySimulateItem.KeyType.KEYBOARD==player.getDstKeyType()&&0!=player.getDstKey()? gameKey.getKeyNamesByMask(player.getDstKey()):"映射键盘", skin);
////			final Label mouselbl = new Label(KeySimulateItem.KeyType.MOUSE==player.getDstKeyType()&&0!=player.getDstKey()?MouseKey.values()[player.getDstKey()].toString():"映射鼠标"  , skin);
////			//playerlbls.add(playerlbl);
////
////			final String srcKeyName=XBoxKey.values()[player.getSrcKey()].toString();
////			keySettingLbls.put(srcKeyName,playerlbl);
////			mouseKeySettingLbls.put(srcKeyName,mouselbl);
////
////			TextButton padNameBtn = new TextButton(srcKeyName + ":", skin);
////			//// final SGRef<Integer> iRef=new SGRef<Integer>(i);
////			//final int i2 = i;
////			ClickListener listener = new ClickListener() {
////
////				@Override
////				public void clicked(InputEvent event, float x, float y) {
////
////					dialog.init(//KeySettingScreen.this, //sasha,
////							//player, pcCount,
////							gamepad,
////							srcKeyName,
////							new SGAction<String,Integer,Object>() {
////
////								@Override
////								public void go(String s, Integer integer, Object o) {
////									onKeyChange(s,integer);
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
////
////			ClickListener mouseListener = new ClickListener() {
////
////				@Override
////				public void clicked(InputEvent event, float x, float y) {
////
////					mouseKeyDialog.init(//KeySettingScreen.this, //sasha,
////							//player, pcCount,
////							gamepad,
////							srcKeyName,
////							new SGAction<String,Integer,Object>() {
////
////								@Override
////								public void go(String s, Integer integer, Object o) {
////									onMouseKeyChange(s,integer);
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
////					mouseKeyDialog.show(stage);
////
////				}
////			};
////			playerlbl.addListener(listener);
////			mouselbl.addListener(mouseListener);
////			padNameBtn.addListener(listener);
////			playerRow.add(padNameBtn).spaceBottom(20).spaceRight(10);
////			playerRow.add(playerlbl).spaceBottom(20).spaceRight(10);
////			playerRow.add(mouselbl).spaceBottom(20);
////			playerRow.row();
////
////			tabUi.addItem(padNameBtn);
////			i++;
////		}
////
//	//}
//	private boolean isSaveBtnAdded=false;
//	private void generateCombineLbls(){
//
////		for (final Map.Entry<Integer, Integer> player : gameKeyCombinMap.entrySet()) {
//		for (final KeySimulateItem player : gameKeyCombinMap2) {
////			if(KeySimulateItem.KeyType.NONE!=player.getDstKeyType()
////					&&0<player.getDstKey()
////			){
////
////			}else{
////				continue;
////			}
////			String lblPrev=KeySimulateItem.KeyType.KEYBOARD==player.getDstKeyType()?"键盘:":"鼠标:";
////			String lblText=KeySimulateItem.KeyType.KEYBOARD==player.getDstKeyType()
////					?gameKey.getKeyNamesByMask(player.getDstKey())
////					:MouseKey.values()[player.getDstKey()].toString();
////			final Label playerlbl = new Label(gameKey.getKeyNamesByMask(player.getValue()), skin);
//			//final Label playerlbl = new Label(lblPrev+lblText, skin);
//
//			final Label playerlbl = new Label(KeySimulateItem.KeyType.KEYBOARD==player.getDstKeyType()&&0!=player.getDstKey()? gameKey.getKeyNamesByMask(player.getDstKey()):"映射键盘", skin);
//			final Label mouselbl = new Label(KeySimulateItem.KeyType.MOUSE==player.getDstKeyType()&&0!=player.getDstKey()?MouseKey.values()[player.getDstKey()].toString():"映射鼠标"  , skin);
//			//playerlbls.add(playerlbl);
//
//			//final String keyName=XBoxKey.values()[ player.getSrcKey()].toString();
//			//this.combinKeySettingLbls.put(player.getKey(),playerlbl);
//			combinKeySettingLbls.put(player.getSrcKey(),playerlbl);
//			combineMouseKeySettingLbls.put(player.getSrcKey(),mouselbl);
//
//			final String srcKeyName=XBoxKey.getTexts( player.getSrcKey());
//			TextButton padNameBtn = new TextButton( srcKeyName+ ":", skin);
////			TextButton padNameBtn = new TextButton(srcKeyName + "->", skin);
//			this.combinKeyBtns.put(player.getSrcKey(),padNameBtn);
//			//// final SGRef<Integer> iRef=new SGRef<Integer>(i);
//			//final int i2 = i;
//			ClickListener listener = new ClickListener() {
//
//				@Override
//				public void clicked(InputEvent event, float x, float y) {
//					dialog.init(//KeySettingScreen.this, //sasha,
//							//player, pcCount,
//							gamepad,
//							srcKeyName,//player.getKey(),
//							new SGAction<String,Integer,Object>() {
//
//								@Override
//								public void go(String s, Integer integer, Object o) {
//
//									onGamepadKeySettingChange(player.getSrcKey(),integer);
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
//					);
//					dialog.show(stage);
//
//				}
//			};
//
//			ClickListener mouseListener = new ClickListener() {
//				@Override
//				public void clicked(InputEvent event, float x, float y) {
//
//					mouseKeyDialog.init(//KeySettingScreen.this, //sasha,
//							//player, pcCount,
//							gamepad,
//							player.getSrcKey(),
//							srcKeyName,
//							new SGAction<String,Integer,Integer>() {
//
//								@Override
//								public void go(String s, Integer integer, Integer o) {
////									onMouseKeyChange(s,integer,o);
//									onCombineMouseKeyChange(s,integer,o);
//									if (SGTouchGamepad.class ==gamepad.getClass()) {
//										((SGTouchGamepad) gamepad).remove();
//										//((SGTouchGamepad) gamepad).setVisible(false);
//										//if(!needAccessStageInput){needAccessStageInput=true;}
//									}
//								}
//
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
//							}:null
//					);
//					mouseKeyDialog.show(stage);
//
//				}
//			};
//
//			playerlbl.addListener(listener);
//			mouselbl.addListener(mouseListener);
//			padNameBtn.addListener(listener);
//			int spaceBottom=20;
//			int spaceRight=10;
//			playerRow2.add(padNameBtn).spaceBottom(spaceBottom).spaceRight(spaceRight);
//			playerRow2.add(playerlbl).spaceBottom(spaceBottom).spaceRight(spaceRight);
//			playerRow2.add(mouselbl).spaceBottom(spaceBottom);
//			playerRow2.row();
//
////			tabUi.addItem(padNameBtn);
////			tabMap.addItem(padNameBtn);
//			if(isSaveBtnAdded) {
//				tabMapItem.insert(tabMapItem.size-3,padNameBtn);
//				tabMap.setItem(tabMapItem);
//			}else {
//				tabMapItem.add(padNameBtn);
//			}
//			//i++;
//		}
////		String aa="aa";
//	}
//	private SelectBox<String> getStringSelectBox() {
//		SelectBox<String> gamepadCombo=new SelectBox(skin);
//
//		Array<String> gamepadComboItem=new Array<>();
//		for ( ISGPS5Gamepad pad : pads) {
//			gamepadComboItem.add(pad.getPadUniqueName());
//		}
//		//gamepadComboItem.add("setting1");
//		gamepadCombo.setItems(gamepadComboItem);
//
//		gamepadCombo.addListener(new ChangeListener() {
//			@Override
//			public void changed(ChangeEvent event, Actor actor) {
//				//onSaveClicked();
//				//gameKey=new GameKey();gameKeyMap=gameKey.toMap();
//
//				for(ISGPS5Gamepad i:pads){
//					if(((SelectBox<String>)actor).getSelected().equals(i.getPadUniqueName())){
//					//if(gamepad==i){
//
//						gamepad=i;
//						List<String> settings=localSaveSettingHelper2.getSettings(gamepad.getPadUniqueName());
//						settingComboItem.clear();
//						for ( String pad : settings) {
//							settingComboItem.add(pad);
//						}
//						settingCombo.setItems(settingComboItem);
//
////						loadGamepadSetting(i);
////						updateUIAfterChangeGamepadSetting();
//
//						break;
//					}
//				}
//			}
//		});
//		return gamepadCombo;
//	}
//
//	private SelectBox<String> getSettingSelectBox() {
//		SelectBox<String> gamepadCombo=new SelectBox(skin);
//
//		//Array<String> gamepadComboItem=new Array<>();
////		if(null==gamepadComboItem){
////			gamepadComboItem=new Array<>();
////		}
//		settingComboItem=new Array<>();
//		List<String> settings=localSaveSettingHelper2.getSettings(gamepad.getPadUniqueName());
//		for ( String pad : settings) {
//			settingComboItem.add(pad);
//		}
//		//gamepadComboItem.add("setting1");
//		gamepadCombo.setItems(settingComboItem);
//
//		gamepadCombo.addListener(new ChangeListener() {
//			@Override
//			public void changed(ChangeEvent event, Actor actor) {
//				//onSaveClicked();
//				//gameKey=new GameKey();gameKeyMap=gameKey.toMap();
//
////				for(ISGPS5Gamepad i:pads){
////					if(((SelectBox<String>)actor).getSelected().equals(i.getPadUniqueName())){
////						loadGamepadSetting(i);
////						updateUIAfterChangeGamepadSetting();
////						break;
////					}
////				}
//				loadGamepadSetting(gamepad);
//				updateUIAfterChangeGamepadSetting();
//			}
//		});
//		return gamepadCombo;
//	}
//
////	private Robot robot;
//	private boolean ok=false;
//	private void saveKey(){
////				MainMenuScreen.saveKnightGameKeyData(game.knightGameKey);
//		gameKey.applyMap(gameKeyMap);
//		ArrayList<KeySimulateItem> tmpMap=new ArrayList<KeySimulateItem>() ;
//		for(KeySimulateItem i:gameKeyMap2){
//			tmpMap.add(i);
//		}
//		gameKey.setKeyMap(tmpMap);
//
////		ArrayList<Integer> delList=new ArrayList<>();
////		for (Map.Entry<Integer, Integer> m1 : gameKeyCombinMap.entrySet()) {
////			if(0==m1.getValue()){
////				delList.add(m1.getKey());
////			}
////		}
////		for(Integer i:delList){
////			gameKeyCombinMap.remove(i);
////		}
////		gameKey.applyCombinedMap(gameKeyCombinMap);
//
//		 tmpMap=new ArrayList<KeySimulateItem>() ;
//		for(KeySimulateItem i:gameKeyCombinMap2){
//			if(0!=i.getDstKey()){
//				tmpMap.add(i);
//			}
//		}
//		gameKey.setCombinedMap2(tmpMap);
//
//
////				LocalSaveSettingHelper.saveKnightGameKeyData(gamepad.getPadUniqueName(),gameKey);
////		localSaveSettingHelper2.saveGameKey(gamepad.getPadUniqueName(),gameKey);
//		String setting=settingCombo.getSelected();
////		localSaveSettingHelper2.saveGameKey(gamepad.getPadUniqueName()+setting,gameKey);
//		localSaveSettingHelper2.saveGameKey(gamepad.getPadUniqueName(),setting,gameKey);
////		if(SGPS5Gamepad.class==gamepad.getClass()){
////			SGPS5GamepadSetting tmp=new SGPS5GamepadSetting();
////			tmp.setAxisLeftSpace(axisLeftTmp.x1,axisLeftTmp.x2,axisLeftTmp.y1,axisLeftTmp.y2);
////			tmp.setAxisRightSpace(axisRightTmp.x1,axisRightTmp.x2,axisRightTmp.y1,axisRightTmp.y2);
//////					LocalSaveSettingHelper.saveGamepadSettingData(gamepad.getPadUniqueName(),tmp);
////			localSaveSettingHelper3.saveGameKey(gamepad.getPadUniqueName(),tmp);
////		}
//
////		saveResultLbl.setText(TXT.g("save success"));
////		settingDefault=false;
////		//settingTypeLbl.setText("当前为自定义配置");
////		this.msgWaitCount=this.msgWait;
////		showSaveResultLbl(true,false);
//		settingDefault=false;
//		showSuccessText(TXT.g("save success"));
//	}
//	private void restoreKey(){
//		gameKey.init();
////				LocalSaveSettingHelper.saveKnightGameKeyData(gamepad.getPadUniqueName(),null);
//		localSaveSettingHelper2.saveGameKey(gamepad.getPadUniqueName(),null);
////				if(null!=game.knightGameKey){
////					game.knightGameKey.remove(gamepad.getPadUniqueName());
////				}
//		gameKeyMap=gameKey.toMap(gameKeyMap);
//		settingDefault=true;
//		updateUIAfterChangeGamepadSetting();
//	}
//	private void onKeyChange(String key,int keyMask){
//		gameKeyMap.put(key,keyMask);//这里用iter操作已经很有风险了
//		boolean exist=false;
//		for(KeySimulateItem i:gameKeyMap2){
//			if(i.getSrcKey()==XBoxKey.valueOf(key).ordinal()){
//				i.setDstKeyType(KeySimulateItem.KeyType.KEYBOARD);
//				i.setDstKey(keyMask);
//				exist=true;
//			}
//		}
//		if(!exist){
//			KeySimulateItem i=new KeySimulateItem();
//			i.setDstKeyType(KeySimulateItem.KeyType.KEYBOARD);
//			i.setDstKey(keyMask);
//			gameKeyMap2.add(i);
//		}else{
////			showErrorText(TXT.g("combine key exist"));
//		}
//		keySettingLbls.get(key).setText(0==keyMask?"映射键盘":gameKey.getKeyNamesByMask(keyMask));
////		mouseKeySettingLbls.get(key).setText("设置鼠标键位");
//		mouseKeySettingLbls.get(key).setText("映射鼠标");//按键");
//	}
//	private void showErrorText(String err){
//		saveResultLbl.setText(err);
//		saveResultLbl.setColor(Color.RED);
//		this.msgWaitCount=this.msgWait;
//		showSaveResultLbl(true,false,saveResultLbl);
//	}
//	private void showSuccessText(String msg){
//		saveResultLbl.setText(msg);
//		saveResultLbl.setColor(successMsgColor);
//		this.msgWaitCount=this.msgWait;
//		showSaveResultLbl(true,false,saveResultLbl);
//	}
//	private void onMouseKeyChange(String key,int keyMask,int keyValue){
//		//gameKeyMap.put(key,keyMask);//这里用iter操作已经很有风险了
//		boolean exist=false;
//		for(KeySimulateItem i:gameKeyMap2){
//			if(//i.getSrcKey()==XBoxKey.valueOf(key).ordinal()
//					i.getSrcKey()==keyValue
//			){
//				i.setDstKeyType(KeySimulateItem.KeyType.MOUSE);
//				i.setDstKey(keyMask);
//				exist=true;
//			}
//		}
//		if(!exist){
//			KeySimulateItem i=new KeySimulateItem(keyValue);
//			i.setDstKeyType(KeySimulateItem.KeyType.MOUSE);
//			i.setDstKey(keyMask);
//			gameKeyMap2.add(i);
//		}
////		keySettingLbls.get(key).setText("设置键盘键位");
//		keySettingLbls.get(key).setText("映射键盘");//按键");
//		mouseKeySettingLbls.get(key).setText(0==keyMask?"映射鼠标":MouseKey.values()[keyMask].toString());
//	}
//
//	/**
//	 * 手柄组合键改变事件
//	 * @param key
//	 * @param keyMask
//	 */
//	private void onGamepadKeyChange(String key, final int keyMask){
////		if(gameKeyCombinMap.containsKey(keyMask)){
////			return;
////		}
//		for(KeySimulateItem i:gameKeyCombinMap2){
//			if(keyMask==i.getSrcKey()){
//
//				showErrorText(TXT.g("combine key exist"));
//				return;
//			}
//		}
//
////		gameKeyCombinMap.put(keyMask,0);
//
//		final KeySimulateItem player=new KeySimulateItem(keyMask);
//		gameKeyCombinMap2.add(player);
//
////				final Label playerlbl = new Label("设置键盘按键", skin);
//		//final Label playerlbl = new Label("设置键盘按键", skin);
//		final Label playerlbl = new Label(KeySimulateItem.KeyType.KEYBOARD==player.getDstKeyType()&&0!=player.getDstKey()? gameKey.getKeyNamesByMask(player.getDstKey()):"映射键盘", skin);
//		final Label mouselbl = new Label(KeySimulateItem.KeyType.MOUSE==player.getDstKeyType()&&0!=player.getDstKey()?MouseKey.values()[player.getDstKey()].toString():"映射鼠标"  , skin);
//
//				//playerlbls.add(playerlbl);
////				combinKeySettingLbls.put(String.valueOf(keyMask),playerlbl);
//		combinKeySettingLbls.put(keyMask,playerlbl);
//		combineMouseKeySettingLbls.put(keyMask,mouselbl);
//
////				TextButton padNameBtn = new TextButton(player.getKey() + ":", skin);
////		final String keyName=gameKey.getKeyNamesByMask(keyMask);
//		final String srcKeyName=XBoxKey.getTexts(keyMask);
//				TextButton padNameBtn = new TextButton(srcKeyName+":", skin);
//				ClickListener listener = new ClickListener() {
//
//					@Override
//					public void clicked(InputEvent event, float x, float y) {
//						dialog.init(//KeySettingScreen.this, //sasha,
//								//player, pcCount,
//								gamepad,
//								srcKeyName,//player.getKey(),
//								new SGAction<String,Integer,Object>() {
//
//									@Override
//									public void go(String s, Integer integer, Object o) {
//
////										onGamepadKeySettingChange(s,integer);// onKeyChange(s,integer);
//
//										onGamepadKeySettingChange(keyMask,integer);
//										if (SGTouchGamepad.class ==gamepad.getClass()) {
//											((SGTouchGamepad) gamepad).remove();
//											//((SGTouchGamepad) gamepad).setVisible(false);
//											//if(!needAccessStageInput){needAccessStageInput=true;}
//										}
//									}
//								},
//								SGTouchGamepad.class ==gamepad.getClass()?new SGAction<String,Integer,Object>() {
//
//									@Override
//									public void go(String s, Integer integer, Object o) {
//										if (SGTouchGamepad.class ==gamepad.getClass()) {
//											((SGTouchGamepad) gamepad).remove();
//										}
//									}
//
//
//								}:null
//						);
//						dialog.show(stage);
//
//					}
//				};
//
//		ClickListener mouseListener = new ClickListener() {
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//
//				mouseKeyDialog.init(//KeySettingScreen.this, //sasha,
//						//player, pcCount,
//						gamepad,
//						player.getSrcKey(),
//						srcKeyName,
//						new SGAction<String,Integer,Integer>() {
//
//							@Override
//							public void go(String s, Integer integer, Integer o) {
//								 onCombineMouseKeyChange(s,integer,o);
//								if (SGTouchGamepad.class ==gamepad.getClass()) {
//									((SGTouchGamepad) gamepad).remove();
//									//((SGTouchGamepad) gamepad).setVisible(false);
//									//if(!needAccessStageInput){needAccessStageInput=true;}
//								}
//							}
//
//						},
//						SGTouchGamepad.class ==gamepad.getClass()?new SGAction<String,Integer,Object>() {
//
//							@Override
//							public void go(String s, Integer integer, Object o) {
//								if (SGTouchGamepad.class ==gamepad.getClass()) {
//									((SGTouchGamepad) gamepad).remove();
////										((SGTouchGamepad) gamepad).setVisible(false);
////										//if(!needAccessStageInput){needAccessStageInput=true;}
//								}
//							}
//						}:null
//				);
//				mouseKeyDialog.show(stage);
//
//			}
//		};
//				playerlbl.addListener(listener);
//		mouselbl.addListener(mouseListener);
//				padNameBtn.addListener(listener);
//				playerRow2.add(padNameBtn).spaceBottom(20).spaceRight(10);
//				playerRow2.add(playerlbl).spaceBottom(20).spaceRight(10);
//		playerRow2.add(mouselbl).spaceBottom(20);
//				playerRow2.row();
//
////				tabMap.addItem(padNameBtn);
//		tabMapItem.insert(tabMapItem.size-3,padNameBtn);//
//		tabMap.setItem(tabMapItem);//当前这个api勉强可以用来当作insert
//
////		ensureCurrentInView2(padNameBtn);
////		scrollPane.setScrollY(needHeight-(scrollPane.getHeight()/2f));
//		//刚刚添加的项本帧不会撑开，所以只能add 1计算
//		toScrollEnd=true;
//	}
//	private boolean toScrollEnd=false;
//	/**
//	 * 刚添加进scroll的元素，要等待1帧才能刷新
//	 */
//	private  float  toScrollWait=0.1f;
//	private float toScrollWaitCount=0.1f;
//
//	private void onGamepadKeySettingChange(//String key,
//										   int key,
//										   int keyMask){
////		gameKeyCombinMap.put(key,keyMask);
////		this.combinKeySettingLbls.get(key).setText(gameKey.getKeyNamesByMask(keyMask));
//
//		//gameKeyMap.put(key,keyMask);//这里用iter操作已经很有风险了
//		boolean exist=false;
//		for(KeySimulateItem i:gameKeyCombinMap2){
//			if(//i.getSrcKey()==XBoxKey.valueOf(key).ordinal()
//			i.getSrcKey()==key
//			){
//				i.setDstKeyType(KeySimulateItem.KeyType.KEYBOARD);
//				i.setDstKey(keyMask);
//				exist=true;
//			}
//		}
//		if(!exist){
//			KeySimulateItem i=new KeySimulateItem();
//			i.setDstKeyType(KeySimulateItem.KeyType.KEYBOARD);
//			i.setDstKey(keyMask);
//			gameKeyCombinMap2.add(i);
//		}
//		combinKeySettingLbls.get(key).setText(0==keyMask?"映射键盘":gameKey.getKeyNamesByMask(keyMask));
//		combineMouseKeySettingLbls.get(key).setText("映射鼠标");//按键");
//	}
//
//	private void onCombineMouseKeyChange(String key,int keyMask,
//int keyValue
////			,Array2<KeySimulateItem> gameKeyMap2
////	,Map<Integer,Label> keySettingLbls,Map<Integer,Label> mouseKeySettingLbls
//	){
//		//gameKeyMap.put(key,keyMask);//这里用iter操作已经很有风险了
//		boolean exist=false;
//		for(KeySimulateItem i:gameKeyCombinMap2){
////			if(i.getSrcKey()==XBoxKey.valueOf(key).ordinal()){
//			if(i.getSrcKey()==keyValue){
//				i.setDstKeyType(KeySimulateItem.KeyType.MOUSE);
//				i.setDstKey(keyMask);
//				exist=true;
//			}
//		}
//		if(!exist){
//			KeySimulateItem i=new KeySimulateItem(keyValue);
//			i.setDstKeyType(KeySimulateItem.KeyType.MOUSE);
//			i.setDstKey(keyMask);
//			gameKeyCombinMap2.add(i);
//		}
////		keySettingLbls.get(key).setText("设置键盘键位");
//	   this.combinKeySettingLbls.get(keyValue).setText("映射键盘");//按键");
//		combineMouseKeySettingLbls.get(keyValue).setText(0==keyMask?"映射鼠标":MouseKey.values()[keyMask].toString());
//	}
////	private void goToGamePage() {
////////		if (null == screen) {
////////
////////			AssetManager manager = GameScreen.getAssetsManager();
////////			boolean b = manager.update();
////////			manager.finishLoading();
////////			game.setScreen(new GameScreen(game, manager, sasha));
////////			dispose();
////////		} else {
////////			game.setScreen(screen);
////////			screen.resume();
////////			dispose();
////////		}
//////
//////		if(null!=screen) {//暂停页是不应该进入本页的,所以如果进这判断,就是有问题
//////			Guide2Screen g1=new Guide2Screen(game,screen.getSashaData());
//////			g1.setGameScreen(screen);
//////			game.setScreen(g1);
//////			//screen.resume();
//////			dispose();
//////		}else {
//////			sasha=MainMenuScreen.changeCharacterData(currentCharacter);
//////			Guide2Screen g1=new Guide2Screen(game,sasha);
//////			game.setScreen(g1);
//////			dispose();
//////		}
////
////		sasha=MainMenuScreen.changeCharacterData(currentCharacter);
////		Guide2Screen g1=new Guide2Screen(game,sasha);
////		game.setScreen(g1);
////		dispose();
////	}
//
//	private void goToMainPage() {
//		game.setScreen(new MainMenuScreen(game));
//		dispose();
//	}
////	private void goToKofGamePage() {
////
////		AssetManager manager = GameScreen.getAssetsManager();
////
////		HashMap<SGCharacter, Boolean> map = new HashMap<SGCharacter, Boolean>();
////
////		ArrayList<KofPlayer> mergeList = new ArrayList<KofPlayer>(players);
////		mergeList.addAll(pcplayers);
////		for (KofPlayer player : mergeList) {
////			map.put(player.getCharacter(), true);
////		}
////		for (SGCharacter i : map.keySet()) {
////			if (SGCharacter.GODDESSPRINCESSSASHA == i) {
////				SashaActor.loadAssets(manager);
////			}
////			else if (SGCharacter.ALICE == i) {
////				AliceActor.loadAssets(manager);
////			}
////			else if (SGCharacter.CHOMPER == i) {
////				ChomperActor.loadAssets(manager);
////			}
////			else if (SGCharacter.GIANT == i) {
////				GiantActor.loadAssets(manager);
////			}
////		}
////		// boolean b =
////		manager.update();
////		manager.finishLoading();
//////		ArrayList<KofPlayer> tmp=new ArrayList<KofPlayer>(players);
//////		tmp.addAll(pcplayers);
//////		game.setScreen(new KofGameScreen(game, manager, sasha, mergeList, pads));
////		game.setScreen(new KofGameScreen(game, manager, mergeList, pads));
////		dispose();
////
////	}
////
////	private void goToKofGameD3Page() {
////
////		AssetManager manager = GameScreen.getAssetsManager();
//////		GameScreenD3.loadAssets(manager);
////		KofGameScreenD3.loadAssets(manager);
//////		SashaActor.loadAssets(manager);
//////		SashaActorD3.loadAssets(manager);
//////		AliceActor.loadAssets(manager);
////
////		HashMap<SGCharacter, Boolean> map = new HashMap<SGCharacter, Boolean>();
////
////		ArrayList<KofPlayer> mergeList = new ArrayList<KofPlayer>(players);
////		mergeList.addAll(pcplayers);
////		for (KofPlayer player : mergeList) {
////			map.put(player.getCharacter(), true);
////		}
////		PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
////		Array<ParticleBatch<?>> list=new Array<>();
////		list.add(pointSpriteBatch);
////		ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(list);
////		for (SGCharacter i : map.keySet()) {
////			if (SGCharacter.GODDESSPRINCESSSASHA == i) {
////				SashaActor.loadAssets(manager);
////				SashaActorD3.loadAssets(manager);
////				SashaActorD3.loadParticleAssets(manager,loadParam);
////			}
////			else if (SGCharacter.ALICE == i) {
////				AliceActor.loadAssets(manager);
////			}
////			else if (SGCharacter.CHOMPER == i) {
////				ChomperActor.loadAssets(manager);
////			}
////			else if (SGCharacter.GIANT == i) {
////				GiantActor.loadAssets(manager);
////			}
////		}
////		// boolean b =
////		manager.update();
////		manager.finishLoading();
////
////		//game.setScreen(new KofGameScreenD3(game, manager, mergeList, pads));
////
////		KofGameScreenD3 tmp=new KofGameScreenD3();
////		tmp.setPointSpriteBatch(pointSpriteBatch);
////		tmp.init(game, manager, mergeList, pads);
////		game.setScreen(tmp);
////		dispose();
////
////	}
////	private void goToGuide() {
////		Guide1Screen g1=new Guide1Screen(game,screen.getSashaData());
////		g1.setGameScreen(screen);
////		game.setScreen(g1);
////		//screen.resume();
////		dispose();
////	}
////	/**
////	 * 发布前要初始化数据
////	 */
////	private void initSysDataBeforeRelease() {
////		sasha = SashaData.init();
////		saveSashaData(sasha);
////	}
////
////	private void initTestSashaData() {
////		// readUserData();
////		sasha = SashaData.init();
////		sasha = readSashaData();
////		sasha.setCountry("cn");
////		sasha.setEmail(key);
////		;
////		saveSashaData(sasha);
////	}
//
//	@Override
//	public void show() {
//
//	}
//
//	//private int jumpCount = 0;
//
//	private float backToMainTime=3;
//
//	private final float  msgWait=2f;
//	private float msgWaitCount=2f; //这个要设置为所有wait的max值
//	public static void showSaveResultLbl(boolean visible, boolean animated,Label saveResultLbl) {
//		float alphaTo = visible ? 0.8f : 0.0f;
//		float duration = animated ? 1.0f : 0.0f;
//		Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;
//		if(saveResultLbl.hasActions()){
//			saveResultLbl.clearActions();
//		}
//		saveResultLbl.addAction(sequence(
//				touchable(touchEnabled),
//				alpha(alphaTo, duration)));
//	}
//	private ScrollPane isInScrollPanel(Actor actor, SGRef<Actor> outActor){
////		return Label.class==actor.getClass();
//		if( null!=actor.getParent()
//				&&null!=actor.getParent().getParent()
//				&&ScrollPane.class==actor.getParent().getParent().getClass()){
//			outActor.SetValue(actor);
//			return (ScrollPane) actor.getParent().getParent();
//		}
//		//combine的嵌套多了一层
//		if( null!=actor.getParent()
//				&&null!=actor.getParent().getParent()
//				&&null!=actor.getParent().getParent().getParent()
//				&&ScrollPane.class==actor.getParent().getParent().getParent().getClass()){
//			outActor.SetValue(actor.getParent());
//			return (ScrollPane) actor.getParent().getParent().getParent();
//		}
//		return null;
//	}
//
//	SGRef<Actor> actorRef=null;
////	private void ensureCurrentInView2(Actor actor2){
////
////		if(null==actorRef){actorRef=new SGRef<>();}
////		ScrollPane scrollPane = this.isInScrollPanel(actor2,actorRef);
////		Actor actor=actorRef.GetValue();
////		if (null != scrollPane ) {
////
////			float panMaxHeight=scrollPane.getActor().getHeight();//pan.getMaxHeight()永远是0，gdx旧版本就有值
////
////
////			float needHeight=0f;
////			boolean isInView=false;
////			needHeight=panMaxHeight-actor.getY();
////			isInView=needHeight-scrollPane.getScrollY()>0
////					&&needHeight-scrollPane.getScrollY()<scrollPane.getHeight();
////			if(!isInView){
////				scrollPane.setScrollY(needHeight-(scrollPane.getHeight()/2f));
////			}
////
////		}
////	}
//	private void ensureCurrentInView(){
//		if(null!=tabMap.getCurrent()) {
//			Actor actor2=(Actor) tabMap.getCurrent();
//			//ensureCurrentInView2(actor2);
//
//			if(null==actorRef){actorRef=new SGRef<>();}
//			ScrollPane scrollPane = this.isInScrollPanel(actor2,actorRef);
//			Actor actor=actorRef.GetValue();
//			if (null != scrollPane ) {
//
//				float panMaxHeight=scrollPane.getActor().getHeight();//pan.getMaxHeight()永远是0，gdx旧版本就有值
//
//
//				float needHeight=0f;
//				boolean isInView=false;
//				needHeight=panMaxHeight-actor.getY();
//				isInView=needHeight-scrollPane.getScrollY()>0
//						&&needHeight-scrollPane.getScrollY()<scrollPane.getHeight();
//				if(!isInView){
//					scrollPane.setScrollY(needHeight-(scrollPane.getHeight()/2f));
//				}
//
//			}
//		}
//	}
//	@Override
//	public void render(float delta) {
//
//		if(!ok){
//
//			if(0>=backToMainTime){
//				this.goToMainPage();
//				return;
//			}else{
//				ScreenUtils.clear(0, 0, 0.2f, 1);
//				Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
//				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//
//				batch.begin();
//				game.font.draw(batch,String.format(TXT.g("no gamepad. back to main screen(%.1f seconds)"),backToMainTime),100,ScreenSetting.WORLD_HEIGHT-100);
//				batch.end();
//				backToMainTime-=delta;
//				return;
//			}
//		}
//
//		if(toScrollEnd){
//
//			if(0>=toScrollWaitCount) {
////			scrollPane.setScrollY(scrollPane.getActor().getHeight()*(1f+tabMapItem.size)/(tabMapItem.size));
//				scrollPane.setScrollY(scrollPane.getActor().getHeight() + 100);
//				toScrollEnd = false;
//				toScrollWaitCount = toScrollWait;
//			}else{
//				toScrollWaitCount -= delta;
//			}
//		}
//		if(0>=buttonWaitCount) {
//
////			if(simulating){
////
////				if(sgcontroller.isCROSS()){
////					robot.keyPress(KeyEvent.VK_Z);
////				}
////			}else{
//				boolean isDialogOpen=!(null == dialog || (!dialog.isVisible()) || null == dialog.getStage());
//			boolean isGamepadKeyDialogOpen=!(null == gamepadKeyDialog || (!gamepadKeyDialog.isVisible()) || null == gamepadKeyDialog.getStage());
//				if (
//						!isDialogOpen
//				&&!isGamepadKeyDialogOpen
//				) {
//					if (tabUi.isEditing()) {
//
//						if (0 >= buttonWaitCount) {
//							if (sgcontroller.isROUND()) {
//								tabUi.select();
//								buttonWaitCount = buttonWait;
//							} else {
//								if (tabUi.edit(sgcontroller)) {
//
//									buttonWaitCount = buttonWait;
//								}
//							}
//						}
//					} else {
//						//无编辑时的主要操作
//						if (oWait <= 0) {
//							if (null != sgcontroller && sgcontroller.isROUND()) {
//								goToMainPage();
//								return;
//							}
//						}
//
//						if (0 >= buttonWaitCount) {
//							if (null != sgcontroller && sgcontroller.isUP()) {
//								tabUi.up();
//								ensureCurrentInView();
//								buttonWaitCount = buttonWait;
//							} else if (null != sgcontroller && sgcontroller.isDOWN()) {
//								tabUi.down();
//								ensureCurrentInView();
//								buttonWaitCount = buttonWait;
//							} else if (null != sgcontroller && sgcontroller.isCROSS()) {
//								//				((ClickListener)((TextButton)tabUi.getCurrentActor()).getListeners().get(((TextButton)tabUi.getCurrentActor()).getListeners().size-1)).clicked(null,0,0);
//								//				((TextButton)tabUi.getCurrentActor()).getClickListener().clicked(new InputEvent(),0,0);
//								tabUi.select();
//								buttonWaitCount = buttonWait;
//							}
//							else if(null != sgcontroller && sgcontroller.isSQUARE()){
//								saveKey();
//								buttonWaitCount = buttonWait;
//							}else if(null != sgcontroller && sgcontroller.isTRIANGLE()){
//								restoreKey();
//								buttonWaitCount = buttonWait;
//							}
//						}
//					}
//				} else {
//					//有弹窗时
//					if (isDialogOpen&&0 >= buttonWaitCount) {
//						if (null != sgcontroller && sgcontroller.isCROSS() && dialog.isDone()) {
//							dialog.getRestoreButton().toggle();
//							buttonWaitCount = buttonWait;
//						} else if (null != sgcontroller && sgcontroller.isROUND() && dialog.isDone()) {
//							dialog.getCloseButton().toggle();
//							buttonWaitCount = buttonWait;
//						}
//					}
//
//					if (isGamepadKeyDialogOpen&&0 >= buttonWaitCount) {
//						if (null != sgcontroller && sgcontroller.isCROSS() && gamepadKeyDialog.isDone()) {
//							gamepadKeyDialog.getRestoreButton().toggle();
//							buttonWaitCount = buttonWait;
//						} else if (null != sgcontroller && sgcontroller.isROUND() && gamepadKeyDialog.isDone()) {
//							gamepadKeyDialog.getCloseButton().toggle();
//							buttonWaitCount = buttonWait;
//						}
//					}
//				}
//			//}
//		}
//
//		if(null==stage){//tabUi触发页面跳转
//			return;
//		}
//
//		if(0>=msgWaitCount){
//			if(Touchable.disabled!=saveResultLbl.getTouchable()) {
//				showSaveResultLbl(false, true,saveResultLbl);
//			}
//		}
//
////		ScreenUtils.clear(0, 0, 0.2f, 1);
//		ScreenUtils.clear(Color.PINK);
//		if (buttonWaitCount > 0) {
//
//			buttonWaitCount -= delta;
//		} else if (buttonWaitCount < 0) {
//			buttonWaitCount = 0;
//		}
//
//		if (msgWaitCount > 0) {
//
//			msgWaitCount -= delta;
//		} else if (msgWaitCount < 0) {
//			msgWaitCount = 0;
//		}
//		if (xWait > 0) {
//
//			xWait -= delta;
//		} else if (xWait < 0) {
//			xWait = 0;
//		}
//
//		if (oWait > 0) {
//
//			oWait -= delta;
//		} else if (oWait < 0) {
//			oWait = 0;
//		}
//
//		if (squartWait > 0) {
//
//			squartWait -= delta;
//		} else if (squartWait < 0) {
//			squartWait = 0;
//		}
//
////		if (xWait <= 0) {
////
////			goToGameBtn.setText(TXT.g("enter the 2d game, or press □"));
////			goToGameD3Btn.setText(TXT.g("enter the 3d game, or press X"));
////		} else {
////			goToGameBtn.setText(TXT.g("enter the 2d game, or press □") + "(等" + ((Float) xWait).intValue() + "秒)");
////			goToGameD3Btn.setText(TXT.g("enter the 3d game, or press X") + "(等" + ((Float) xWait).intValue() + "秒)");
////		}
//
////		dataLbl.setText("当前选择 "+currentCharacter+": agi("+com.sellgirl.sgJavaHelper.config.SGDataHelper.ScientificNotation(previewSasha.getAgi())
////		+") str("+com.sellgirl.sgJavaHelper.config.SGDataHelper.ScientificNotation(previewSasha.getStr())+")");
//
////		// if(nextWait<=0) {
////		if (SGCharacter.SASHA == sasha.getCharacter()) {
////			if (1 == step) {
////				if (nextWait <= 0) {
////					actor.dodge();
////					skillLbl.setText("○ 躲闪");
////				}
////				nextWait += delta;
////				if (nextWait > 2) {
////
////					nextWait = 0;
////					step++;
////					actor.setX(actorBeginX);
////				}
////			} else if (2 == step) {
////				actor.defence();
////				;
////				skillLbl.setText("L1 防御");
////				nextWait += delta;
////				if (nextWait > 2) {
////
////					nextWait = 0;
////					step++;
////				}
////			} else if (3 == step) {
////				if (nextWait <= 0) {
////					actor.jump();
////					skillLbl.setText("X 跳");
////				}else if(nextWait>0.5&&jumpCount==0) {
////					actor.jump();
////					jumpCount++;
////				}
////				nextWait += delta;
////				if (nextWait > 2) {
////
////					nextWait = 0;
////					step++;
////					jumpCount=0;
////				}
////			}  else if (4== step) {
////				if (nextWait <= 0) {
////					actor.doSkill1(stage);
////					skillLbl.setText("↓↘→□ 气功");
////				}
////				nextWait += delta;
////				if (nextWait > 2) {
////
////					nextWait = 0;
////					step++;
////				}
////			} else if (5 == step) {
////				if (nextWait <= 0) {
////					actor.doSkill2(stage);
////					skillLbl.setText("→↘↓↘→□ 天翔龙闪");
////				}
////				nextWait += delta;
////				if (nextWait > 2) {
////
////					nextWait = 0;
////					step = 1;
////					actor.setX(actorBeginX);
////				}
////			}
//////				step++;
//////				if(step>4) {step=1;}
////		}
//
//		// }
////		nextWait-=delta;
////		if(nextWait<0) {nextWait=0;}
//
////		int paycodeW = 220;
////		int paycodeH = 300;
////		batch.begin();
////		batch.draw(paycode, Gdx.graphics.getWidth() / 2 - (paycodeW / 2), Gdx.graphics.getHeight() - paycodeH - 20,
////				paycodeW, paycodeH);
////
////		game.font.draw(batch,
////				TXT.g("This is a free game developed by BENJAMIN, and VIP will have a better experience in the game. You may pay 1 yuan or more to permanently activate VIP. The developer promises to permanently update and maintain this game. You can scan the QR code above to make payment, I will active your VIP in one day, thanks."),
////				10, Gdx.graphics.getHeight() - paycodeH - 40, Gdx.graphics.getWidth() - 20,
////				Gdx.graphics.getHeight() - paycodeH - 20, true);
////
////
//////		game.font.draw(batch,
//////				TXT.g("pay description"),
//////				10, Gdx.graphics.getHeight() - paycodeH - 40, Gdx.graphics.getWidth() - 20,
//////				Gdx.graphics.getHeight() - paycodeH - 20, true);
////		batch.end();
//
////		// 更新舞台逻辑
//		stage.act();
//		// 绘制舞台
//		stage.draw();
//
////		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
////		stage.act(Gdx.graphics.getDeltaTime());
////		stage.draw();
//	}
//
////	private void gotoGamePage() {
////		AssetManager manager = new AssetManager();
////		// manager.load("data/mytexture.png", Texture.class);
//////		manager.load("sasha/man1.png", Texture.class);
//////		manager.load("sasha/man2.png", Texture.class);
//////		manager.load("sasha/man3.png", Texture.class);
////		manager.load("man1.png", Texture.class);
////		manager.load("man2.png", Texture.class);
////		manager.load("man3.png", Texture.class);
////		manager.load("drop.wav", Sound.class);
////		manager.load("rain.mp3", Music.class);
////
////		boolean b = manager.update();
////		manager.finishLoading();
////		game.setScreen(new GameScreen(game, manager));
////		dispose();
////
////	}
//
//	@Override
//	public void resize(int width, int height) {
//
//	}
//
//	@Override
//	public void pause() {
//
//	}
//
//	@Override
//	public void resume() {
//
//	}
//
//	@Override
//	public void hide() {
//
//	}
//
//	@Override
//	public void dispose() {
////		if(null!=lastScreen) {
////			lastScreen.dispose();
////			lastScreen=null;
////		}
//		if(null!=stage) {
//			stage.dispose();
//			stage=null;
//		}
//		if(null!=skin) {
//			skin.dispose();
//			skin=null;
//		}
//		if(null!=batch) {
//			batch.dispose();
//			batch=null;
//		}
//		//System.out.println(this.getClass().getSimpleName()+" dispose");
//	}
//
////	public void onSelectCharacterDialogConfirm(int playerId) {
////		this.playerlbls.get(playerId)
////				.setText("team" + players.get(playerId).getTeam() + ", " + players.get(playerId).getCharacter());
////	}
//
//	// ...Rest of class omitted for succinctness.
//
////	private class SGXInputControllerListener extends ControllerAdapter {
////
////		@Override
////		public boolean buttonDown(Controller controller, int buttonIndex) {
//////		         if(XBoxKey.CROSS.ordinal()==buttonIndex) {
//////			         Gdx.app.log(TAG, "jump: " +buttonIndex);
//////		         }
////
////			if (buttonIndex == controller.getMapping().buttonA) {
//////			         Gdx.app.log(TAG, "jump: " +buttonIndex);
////				// gotoGamePage();
////			}
////
////			return false;
////		}
////
////		@Override
////		public boolean buttonUp(Controller controller, int buttonIndex) {
////			return false;
////		}
////
////		@Override
////		public void connected(Controller controller) {
////		}
////
////		@Override
////		public void disconnected(Controller controller) {
////		}
////	}
//
////	public void setGameScreen(GameScreen screen) {
////		this.screen = screen;
////	}
//
////	private void readUserData() {
////		Preferences preferences = Gdx.app.getPreferences("pre1.test");
////		preferences.putString("sasha", "Kitty");
////		preferences.putBoolean("visible", true);
////		preferences.putInteger("age", 25);
////		preferences.flush();
////
////		String strName1 = preferences.getString("name");
////		boolean isVisible = preferences.getBoolean("visible");
////		int age1 = preferences.getInteger("age");
////	}
////
////	private String key = "a2FoaW5lYXNpbjEyMzQ1Ng==";
////
////	private void saveSashaData(SashaData sasha) {
////		Preferences preferences = Gdx.app.getPreferences("pre1.test");
////		String str = sasha.getAgi() + "|" + sasha.getEmail() + "|" + sasha.getKill() + "|" + sasha.getBirth() + "|"
////				+ sasha.getStr() + "|" + sasha.getUserId() + "|" + sasha.getObedient() + "|" + sasha.getLastLogin()
////				+ "|" + sasha.getVip() + "|" + sasha.getVer() + "|" + sasha.getCountry();
////		try {
////			preferences.putString("sasha", AES.AESEncryptDemo(str, SGDataHelper.decodeBase64(key)));
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////		preferences.flush();
////	}
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
//
//
//}
