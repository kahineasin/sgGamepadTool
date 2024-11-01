package com.sellgirl.gamepadtool.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sellgirl.gamepadtool.language.TXT;
import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
import com.sellgirl.sgGameHelper.gamepad.SGKeyboardGamepad;
import com.sellgirl.sgJavaHelper.SGAction;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

/**
 * Created by Benjamin Schulte on 26.11.2018.
 */

public class GamepadKeySettingDialog extends Dialog {
//	private  final String tag="KeySettingDialog";
private static  final String tag=KeySettingDialog.class.getName();
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

//	private String keyName=null;
	private int keyMask=0;
	private boolean done=false;
	private boolean start=false;
	ISGPS5Gamepad gamepad;
	SGAction<String,Integer,Object> action;
	SGAction<String,Integer,Object> onPopupClose=null;
//    public KeySettingDialog(final KeySettingScreen app,//final PurchaseManager purchaseManager,
//                            //final SashaData sasha,
//                            //KofPlayer player, final int playerId,
//							ISGPS5Gamepad gamepad,
//							String keyName,
//							final SGAction1<Object> action) {
//        super("", app.skin);
//        this.app = app;
//        this.skin = app.skin;
////        this.player = player;
//		this.gamepad=gamepad;
//		this.keyName=keyName;
//		this.action=action;
//
//        closeButton = new TextButton(TXT.g("cancel"), app.skin);
//        button(closeButton);
//
//        restoreButton = new TextButton(TXT.g("confirm"), app.skin);
//        //restoreButton.setDisabled(true);
//        restoreButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//            	//if(null!=action) {
//				try {
//					action.go(keyMask);
//				} catch (Exception e) {
//					SGDataHelper.getLog().printException(e,tag);
//				}
////				}else {
////                    app.onSelectCharacterDialogConfirm(playerId);
////                }
//                KeySettingDialog.this.hide();
//            }
//        });
//
//        getButtonTable().add(restoreButton);
//
//
////		AssetManager manager = new AssetManager();
////		// manager.load("data/mytexture.png", Texture.class);
//////		manager.load("sasha/man1.png", Texture.class);
//////		manager.load("sasha/man2.png", Texture.class);
//////		manager.load("sasha/man3.png", Texture.class);
////		manager.load("paycode.jpg", Texture.class);
////		//boolean b =
////				manager.update();
////		manager.finishLoading();
////		 frame1 = manager.get("paycode.jpg", Texture.class);
////
//////		 paycode = new TextureRegion(frame1,200,300);
////		 paycode = new TextureRegion(frame1);
//
////		 paycode.setRegionWidth(200);
////		 paycode.setRegionHeight(300);
//
//        // GUI erstmal so aufbauen
//        fillContent();
//
//        // den Init lostreten so früh es geht, aber nicht bevor die GUI-Referenzen existieren :-)
//        //initPurchaseManager();
//    }

	public GamepadKeySettingDialog(final KeySettingScreen app//,//final PurchaseManager purchaseManager,
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
							//String keyName,
							final SGAction<String,Integer,Object> action,
					 final SGAction<String,Integer,Object> onPopupClose) {
//		super("", app.skin);
//		this.app = app;
		//this.skin = app.skin;
//        this.player = player;
		this.gamepad=gamepad;
		this.useKeyMask= SGKeyboardGamepad.class!=gamepad.getClass();
//		this.keyName=keyName;
		this.action=action;
		this.onPopupClose=onPopupClose;

		start=false;
		if(inited){
//			keyTipLbl.setText(keyName+", 按下想要设置的按键(支持按键组合)");
			keyTipLbl.setText("按下手柄按键(支持按键组合)");
			keyMaskLbl.setText("未按任何按键");
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
				if(null!= GamepadKeySettingDialog.this.onPopupClose){
					GamepadKeySettingDialog.this.onPopupClose.go(null,null,null);
				}
			}
		});
		button(closeButton);

		restoreButton = new TextButton(TXT.g("confirm"), app.skin);
		//restoreButton.setDisabled(true);
		restoreButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//if(null!=action) {
				try {
					GamepadKeySettingDialog.this.action.go(
							null,
							//GamepadKeySettingDialog.this.keyName,
							GamepadKeySettingDialog.this.keyMask,null);
					String aa="aa";
				} catch (Exception e) {
					SGDataHelper.getLog().printException(e,tag);
				}
//				}else {
//                    app.onSelectCharacterDialogConfirm(playerId);
//                }
				GamepadKeySettingDialog.this.hide();
			}
		});

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

    private void fillContent() {
        Table contentTable = getContentTable();
        contentTable.pad(10);

//        contentTable.add(new Label("My fancy in app shop", app.skin));
//        contentTable.row();


        Table iapTable = new Table();
        iapTable.defaults()//.fillX().uniform().expandX()
        ;


//        new Image(null);
//        iapTable.add(new ImageButton(paycode.get));

////		int paycodeW = 220;
////		int paycodeH = 300;
//		//Image img=new Image(paycode);
//		Image img=new Image(frame1);
//		//img.setBounds(0, 0, paycodeW, paycodeH);//.setWidth(paycodeW);
////		img.scaleBy(220/frame1.getWidth());
//		//img.scaleBy(0.01f);
//		//img.setHeight(paycodeH);
//        iapTable.add(img);
//        //iapTable.add(new Image(paycode));
//		iapTable.row();
//////        buyEntitlement = new IapButton("已支付",MY_ENTITLEMENT, 179);
////        //iapTable.add(buyEntitlement);
////        TextButton tb = new TextButton("已支付",app.skin);
////        iapTable.add(tb);
//        //buyConsumable = new IapButton(MY_CONSUMABLE, 349);
//        iapTable.row();
//        //iapTable.add(buyConsumable);


		// keyTipLbl = new Label(keyName+", 按下想要设置的按键(支持按键组合)",app.skin);
		keyTipLbl = new Label("按下手柄按键(支持按键组合)",app.skin);
		 keyMaskLbl = new Label("未按任何按键",app.skin);
		iapTable.add(keyTipLbl);
		iapTable.row();
		iapTable.add(keyMaskLbl);
		iapTable.row();
//		//skillLbl = new Label("", skin);
//		 //sashaBtn = new TextButton(SGCharacter.SASHA.name()+" 当前选择", skin);
//		TextButton team1Btn = new TextButton("team1", skin);
//		TextButton team2Btn = new TextButton("team2", skin);
//		TextButton team3Btn = new TextButton("no", skin);
//		teamBtns[0]=team1Btn;
//		teamBtns[1]=team2Btn;
//		teamBtns[2]=team3Btn;
//		team1Btn.addListener(new ClickListener() {
//
//		     @Override
//		     public void clicked(InputEvent event, float x, float y) {
//		         player.setTeam(1);
//
//		         teamBtns[0].setChecked(true);
//		         teamBtns[1].setChecked(false);
//		         teamBtns[2].setChecked(false);
//
//		     }
//	  	});
//		team2Btn.addListener(new ClickListener() {
//
//		     @Override
//		     public void clicked(InputEvent event, float x, float y) {
//		         player.setTeam(2);
//		         teamBtns[0].setChecked(false);
//		         teamBtns[1].setChecked(true);
//		         teamBtns[2].setChecked(false);
//		     }
//	  	});
//		team3Btn.addListener(new ClickListener() {
//
//		     @Override
//		     public void clicked(InputEvent event, float x, float y) {
//		         player.setTeam(-1);
//		         teamBtns[0].setChecked(false);
//		         teamBtns[1].setChecked(false);
//		         teamBtns[2].setChecked(true);
//		     }
//	  	});
//		iapTable.add(teamlbl);
//		iapTable.row();
//		iapTable.add(team1Btn);
//		iapTable.row();
//		iapTable.add(team2Btn);
//		iapTable.row();
//		iapTable.add(team3Btn).spaceBottom(10);
//		iapTable.row();
//
////		Label characterlbl = new Label("选择角色. 切换角色 □",app.skin);
//		Label characterlbl = new Label("选择角色.",app.skin);
////		//skillLbl = new Label("", skin);
////		 //sashaBtn = new TextButton(SGCharacter.SASHA.name()+" 当前选择", skin);
////		TextButton sashaBtn = new TextButton("1."+SGCharacter.GODDESSPRINCESSSASHA.name(), skin);
////		TextButton aliceBtn = new TextButton("2."+SGCharacter.ALICE.name(), skin);
////		TextButton chomperBtn = new TextButton("3."+SGCharacter.CHOMPER.name(), skin);
//
//		SGCharacter[] characters=new SGCharacter[] {SGCharacter.GODDESSPRINCESSSASHA,SGCharacter.ALICE,SGCharacter.CHOMPER,SGCharacter.GIANT};
//

        contentTable.add(iapTable);
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
			if(useKeyMask){
//				if(done){//多按键组合的方式,没法自动重启
//					if(0==gamepad.getQuickBtnKey()){
//						done=false;
//					}
//				}
				if(!done){
					int tmpKeyMask=gamepad.getQuickBtnKey();
					if(tmpKeyMask==keyMask){

					}else if(SGDataHelper.EnumHasFlag(tmpKeyMask,keyMask)){
						keyMask=tmpKeyMask;
						keyMaskLbl.setText(com.sellgirl.sgGameHelper.gamepad.XBoxKey.getTexts(keyMask));
					}else{
						done=true;

		//				try {
		//					action.go(keyMask);
		//				} catch (Exception e) {
		//					SGDataHelper.getLog().printException(e,tag);
		//				}
					}
				}
			}else{
				for(int i=0;i<=Input.Keys.MAX_KEYCODE;i++){
					if(Gdx.input.isKeyPressed(i)){
						keyMask=i;
						keyMaskLbl.setText(Input.Keys.toString(keyMask));
						break;
					}
				}
			}
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
