package com.sellgirl.gamepadtool.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sellgirl.gamepadtool.language.TXT;
import com.sellgirl.gamepadtool.model.MouseKey;
import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
import com.sellgirl.sgGameHelper.gamepad.SGKeyboardGamepad;
import com.sellgirl.sgGameHelper.gamepad.XBoxKey;
import com.sellgirl.sgGameHelper.tabUi.ISGTabMap;
import com.sellgirl.sgGameHelper.tabUi.ISGTabNode;
import com.sellgirl.sgGameHelper.tabUi.SGTabUDLRHierarchicalMap2;
import com.sellgirl.sgGameHelper.tabUi.SGTabUDLRMap;
import com.sellgirl.sgGameHelper.tabUi.SGTabUpDownMap;
import com.sellgirl.sgGameHelper.tabUi.TabUi;
import com.sellgirl.sgJavaHelper.SGAction;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

/**
 * 映射鼠标的设置弹窗
 */

public class MouseKeySettingDialog extends Dialog {
//	private  final String tag="KeySettingDialog";
private static  final String tag= MouseKeySettingDialog.class.getName();
//    public static final String MY_ENTITLEMENT = "entitlement_sku";
//    public static final String MY_CONSUMABLE = "consumable_sku";
    public static final String MY_ENTITLEMENT = "become vip";
    public static final String MY_CONSUMABLE = "consumable_sku";

    private final KeySettingScreen app;
    private final Skin skin;
	Label keyMaskLbl;

	private  TextButton restoreButton;
    private  TextButton closeButton;

    //private boolean restorePressed;
//    private IapButton buyEntitlement;
//    private IapButton buyConsumable;
    //PurchaseManager purchaseManager;
    TextureRegion paycode;
    Texture frame1;
     //private SashaData sasha;
     //KofPlayer player;
     TextButton[] teamBtns=new TextButton[3];
//     TextButton[] characterBtns=new TextButton[3];
     TextButton[] characterBtns=null;

	private Integer keyValue=null;
	private String keyName=null;
	private int keyMask=0;
	private boolean done=false;
	private boolean start=false;
	ISGPS5Gamepad gamepad;
	SGAction<String,Integer,Integer> action;
	SGAction<String,Integer,Object> onPopupClose=null;

	TabUi tabUi;
//	SGTabUDLRHierarchicalMap2 tabMap;
	SGTabUDLRMap tabMap2;
	public MouseKeySettingDialog(final KeySettingScreen app//,//final PurchaseManager purchaseManager,
                                 //final SashaData sasha,
                                 //KofPlayer player, final int playerId,
	){
		super("", app.skin);
		this.app = app;
		this.skin = app.skin;
	}
	boolean inited=false;
	Label keyTipLbl=null;
	boolean useKeyMask=true;
	public void init(//final KeySettingScreen app,//final PurchaseManager purchaseManager,
							//final SashaData sasha,
							//KofPlayer player, final int playerId,
							ISGPS5Gamepad gamepad,
					 Integer keyValue,
							String keyName,
							final SGAction<String,Integer,Integer> action,
					 final SGAction<String,Integer,Object> onPopupClose) {


		this.gamepad=gamepad;
		this.useKeyMask= SGKeyboardGamepad.class!=gamepad.getClass();
		this.keyValue=keyValue;
		this.keyName=keyName;
		this.action=action;
		this.onPopupClose=onPopupClose;

		start=false;
		if(inited){
//			keyTipLbl.setText(keyName+", 按下想要设置的按键(支持按键组合)");
			keyTipLbl.setText(keyName+", 选择鼠标按键");
			//keyMaskLbl.setText("未按任何按键");
			keyMask=0;
			done=false;
//			 keyTipLbl = new Label(keyName+", 按下想要设置的按键(支持按键组合)",app.skin);
			return;
		}
		inited=true;

		closeButton = new TextButton(TXT.g("cancel"), app.skin);
		closeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(null!= MouseKeySettingDialog.this.onPopupClose){
					MouseKeySettingDialog.this.onPopupClose.go(null,null,null);
				}
			}
		});
		button(closeButton);

		restoreButton = new TextButton(TXT.g("don't simulate"), app.skin);
		//restoreButton.setDisabled(true);
		restoreButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
//				MouseKeySettingDialog.this.selectBtn(MouseKey.NONE);
				MouseKeySettingDialog.this.selectBtn(MouseKey.NONE);
			}
		});
//
		getButtonTable().add(restoreButton);


//		AssetManager manager = new AssetManager();
//		// manager.load("data/mytexture.png", Texture.class);
////		manager.load("sasha/man1.png", Texture.class);
////		manager.load("sasha/man2.png", Texture.class);
////		manager.load("sasha/man3.png", Texture.class);
//		manager.load("paycode.jpg", Texture.class);
//		//boolean b =
//				manager.update();
//		manager.finishLoading();
//		 frame1 = manager.get("paycode.jpg", Texture.class);
//
////		 paycode = new TextureRegion(frame1,200,300);
//		 paycode = new TextureRegion(frame1);

//		 paycode.setRegionWidth(200);
//		 paycode.setRegionHeight(300);

		// GUI erstmal so aufbauen
		fillContent();

		// den Init lostreten so früh es geht, aber nicht bevor die GUI-Referenzen existieren :-)
		//initPurchaseManager();
	}

	private void selectBtn(MouseKey key){
		try {
			MouseKeySettingDialog.this.action.go(MouseKeySettingDialog.this.keyName, key.ordinal(),this.keyValue);
		} catch (Exception e) {
			SGDataHelper.getLog().printException(e,tag);
		}
//				}else {
//                    app.onSelectCharacterDialogConfirm(playerId);
//                }
		MouseKeySettingDialog.this.hide();
	}
	private void addBtnListener(Label upLbl, final MouseKey key){

		upLbl.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				MouseKeySettingDialog.this.selectBtn(key);
			}
		});
	}
    private void fillContent() {
        Table contentTable = getContentTable();
        contentTable.pad(10);

//        contentTable.add(new Label("My fancy in app shop", app.skin));
//        contentTable.row();


        Table iapTable = new Table();
        iapTable.defaults()//.fillX().uniform().expandX()
        ;



//		 keyTipLbl = new Label(keyName+", 按下想要设置的按键(支持按键组合)",app.skin);
//		keyTipLbl = new Label(keyName+", 按下想要映射的键盘按键",app.skin);
		keyTipLbl = new Label(keyName+", 选择鼠标按键",app.skin);
//		keyTipLbl.setText(keyName+", 按下想要映射的键盘按键");
		// keyMaskLbl = new Label("未按任何按键",app.skin);
		Label upLbl = new Label("上移",app.skin);
		Label downLbl = new Label("下移",app.skin);
		Label leftLbl = new Label("左移",app.skin);
		Label rightLbl = new Label("右移",app.skin);
		Label buttonLeftLbl = new Label("鼠标左键",app.skin);
		Label buttonRightLbl = new Label("鼠标右键",app.skin);
		Label buttonScrollLbl = new Label("鼠标滚轮",app.skin);
		Label scrollUpLbl = new Label("鼠标滚轮(上)",app.skin);
		Label scrollDownLbl = new Label("鼠标滚轮(下)",app.skin);

		tabMap2=new SGTabUDLRMap();
		tabMap2.endToBegin= SGTabUpDownMap.EndToBeginType.TO;
		ISGTabNode node1=tabMap2.newNode(upLbl);
		ISGTabNode node2=tabMap2.newNode(downLbl);
		ISGTabNode node3=tabMap2.newNode(leftLbl);
		ISGTabNode node4=tabMap2.newNode(rightLbl);
		ISGTabNode node5=tabMap2.newNode(buttonLeftLbl);
		ISGTabNode node6=tabMap2.newNode(buttonRightLbl);
		ISGTabNode node7=tabMap2.newNode(buttonScrollLbl);
		ISGTabNode node8=tabMap2.newNode(scrollUpLbl);
		ISGTabNode node9=tabMap2.newNode(scrollDownLbl);
		ISGTabNode node10=tabMap2.newNode(closeButton);
		ISGTabNode node11=tabMap2.newNode(restoreButton);
		tabMap2.generateUDLRConnect(node1,node2,node3,node4,null
		,node5,node6,null,node7,node8,node9,null,node10,node11);
		tabMap2.setFirstNode(node1);
//		tabMap2.setEndNode(node10);
		tabUi=new TabUi();
		tabUi.setTabMap(tabMap2);

//		upLbl.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				MouseKeySettingDialog.this.selectBtn(MouseKey.UP);
//			}
//		});
		addBtnListener(upLbl,MouseKey.UP);
		addBtnListener(downLbl,MouseKey.DOWN);
		addBtnListener(leftLbl,MouseKey.LEFT);
		addBtnListener(rightLbl,MouseKey.RIGHT);
		addBtnListener(buttonLeftLbl,MouseKey.buttonLeft);
		addBtnListener(buttonRightLbl,MouseKey.buttonRight);
		addBtnListener(buttonScrollLbl,MouseKey.buttonScroll);
		addBtnListener(scrollUpLbl,MouseKey.scrollUp);
		addBtnListener(scrollDownLbl,MouseKey.scrollDown);

		int spaceRight=20;
		int spaceBottom=20;
		iapTable.add(keyTipLbl).colspan(4)//.spaceRight(spaceRight)
				.spaceBottom(spaceBottom);
		iapTable.row();
//		iapTable.add(keyMaskLbl);
		iapTable.add(upLbl).spaceRight(spaceRight).spaceBottom(spaceBottom);
		iapTable.add(downLbl).spaceRight(spaceRight).spaceBottom(spaceBottom);
		iapTable.add(leftLbl).spaceRight(spaceRight).spaceBottom(spaceBottom);
		iapTable.add(rightLbl).spaceBottom(spaceBottom);
		iapTable.row();
		iapTable.add(buttonLeftLbl).spaceRight(spaceRight).spaceBottom(spaceBottom);
		iapTable.add(buttonRightLbl).colspan(3).spaceBottom(spaceBottom);
		iapTable.row();
		iapTable.add(buttonScrollLbl).spaceRight(spaceRight).spaceBottom(spaceBottom);
		iapTable.add(scrollUpLbl).spaceRight(spaceRight).spaceBottom(spaceBottom);
		iapTable.add(scrollDownLbl).colspan(2).spaceBottom(spaceBottom);
		iapTable.row();


        contentTable.add(iapTable);
    }

//	public ISGTabMap getTabMap(){
//		return tabMap2;
//	}

	public TabUi getTabUi(){
		return tabUi;
	}



	@Override
	public void act (float delta) {
		super.act(delta);
		if(!start){
			//使用tabUi后，可能是用X键打开窗口的
			if(!gamepad.isCROSS()){
				start=true;
			}
			return;
		}
//			if(
//					false
////					useKeyMask
//			){
//				if(!done){
//					int tmpKeyMask=gamepad.getQuickBtnKey();
//					if(tmpKeyMask==keyMask){
//
//					}else if(SGDataHelper.EnumHasFlag(tmpKeyMask,keyMask)){
//						keyMask=tmpKeyMask;
//						keyMaskLbl.setText(XBoxKey.getTexts(keyMask));
//					}else{
//						done=true;
//
//		//				try {
//		//					action.go(keyMask);
//		//				} catch (Exception e) {
//		//					SGDataHelper.getLog().printException(e,tag);
//		//				}
//					}
//				}
//			}else{
//				for(int i=0;i<=Input.Keys.MAX_KEYCODE;i++){
//					if(Gdx.input.isKeyPressed(i)){
//						keyMask=i;
//						keyMaskLbl.setText(Input.Keys.toString(keyMask));
//						break;
//					}
//				}
//			}
	}


	public TextButton getRestoreButton() {
		return restoreButton;
	}

	public TextButton getCloseButton() {
		return closeButton;
	}
	public boolean isDone(){
		return done;
	}
}
