package com.sellgirl.gamepadtool.util;

import com.sellgirl.sgGameHelper.gamepad.SGPS5GamepadSetting;
import com.sellgirl.sgJavaHelper.AES;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

/**
 * 本地保存的设置
 */
public class LocalSaveSettingHelper3  extends LocalSaveSettingHelperBase<SGPS5GamepadSetting>{
    private static  final String tag= LocalSaveSettingHelper3.class.getName();
    @Override
    protected String getFileKey() {
        return Constants.GAMEPAD_SETTING;
    }
    // ---------------- GamepadSetting存取相关 ------------------

    protected   String getEncodeStrByGameKey(SGPS5GamepadSetting sasha) {

        String str = sasha.getAxisLeftSpace().x1 + "|" + sasha.getAxisLeftSpace().x2
                + "|" + sasha.getAxisLeftSpace().y1 + "|" + sasha.getAxisLeftSpace().y2
                + "|" +sasha.getAxisRightSpace().x1 + "|" + sasha.getAxisRightSpace().x2
                + "|" + sasha.getAxisRightSpace().y1 + "|" + sasha.getAxisRightSpace().y2;
        try {
            return AES.AESEncryptDemo(str, SGDataHelper.decodeBase64(key));
        } catch (Exception e) {
            //hasReadError=true;
            SGDataHelper.getLog().printException(e,tag+".getEncodeStrByGamepadSetting ");
        }
        return null;
    }
    protected SGPS5GamepadSetting initGameKeyByEncodeStr(String s,SGPS5GamepadSetting sasha
    ) throws Exception {

        String[] s2 = AES.AESDecryptDemo(s, SGDataHelper.decodeBase64(key)).split("[|]");
////		IKnightSashaGameKey sasha = SGKeyboardGamepad.class==gamepadType?new GameKeyKeyboard(): new GameKey();
//        SGPS5GamepadSetting sasha=new SGPS5GamepadSetting();
//        sasha.setAxisRightSpace(Float.valueOf(s2[0]),Float.valueOf(s2[1]),Float.valueOf(s2[2]),Float.valueOf(s2[3]));//left写法成right了
        sasha.setAxisLeftSpace(Float.valueOf(s2[0]),Float.valueOf(s2[1]),Float.valueOf(s2[2]),Float.valueOf(s2[3]));
        sasha.setAxisRightSpace(Float.valueOf(s2[4]),Float.valueOf(s2[5]),Float.valueOf(s2[6]),Float.valueOf(s2[7]));
        return sasha;
    }

//    public static void saveGamepadSettingData(String gamepadName,SGPS5GamepadSetting gameKey) {
////		if(notSaveDataIfError&&hasReadError){
////			return;
////		}
//        Preferences preferences = Gdx.app.getPreferences(Constants.GAMEPAD_SETTING);
//
//        try {
//            if(null==gameKey){
//                preferences.remove(gamepadName);
//            }else{
//                preferences.putString(gamepadName, getEncodeStrByGamepadSetting(gameKey));
//            }
//        } catch (Exception e) {
//            //e.printStackTrace();
//            SGDataHelper.getLog().printException(e,tag+".saveGamepadSettingData ");
//        }
//        preferences.flush();
//    }
//
//    public static SGPS5GamepadSetting readGamepadSetting(String gamepadName) {
//        Preferences preferences = Gdx.app.getPreferences(Constants.GAMEPAD_SETTING);
//        SashaData sasha = null;
//        SGCharacter cOld = null;
//        try {
////			String s3 = preferences.getString("character");
//////			//SGCharacter c=SGCharacter.SASHA;
////			if(null!=s3&&!"".equals(s3)) {
////				cOld=SGCharacter.valueOf(AES.AESDecryptDemo(s3, SGDataHelper.decodeBase64(key)));
////				//if(cOld==c) {return null;}
////			}
//            String s = preferences.getString(gamepadName);
//            if (null == s || "".equals(s)) {
//                return null;
////				sasha = SashaData.initCharacter(c);
////				// return sasha;
//            } else {
////				sasha = initSashaByEncodeStr(s);
//                return initGamepadSettingByEncodeStr(s);
//            }
//        } catch (Exception e) {
//            SGDataHelper.getLog().printException(e,tag);
//        }
//        return null;
//
//    }
    // ---------------- GamepadSetting存取相关 end ------------------
}
