package com.sellgirl.gamepadtool.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

/**
 * 本地保存的设置
 */
public abstract class LocalSaveSettingHelperBase<TGameKey> {
    private static  final String tag= LocalSaveSettingHelperBase.class.getName();
    public static final String key = "a2FoaW5lYXNpbjEyMzQ1Ng==";
    // ---------------- KnightGameKey存取相关 ------------------
    /**
     * Constants.KNIGHT_KEY_SETTING
     * @return
     */
    protected abstract String getFileKey();
    protected abstract String getEncodeStrByGameKey(TGameKey sasha);
//    protected abstract TGameKey initGameKeyByEncodeStr(String s,Class<?> gamepadType) throws Exception;

    protected abstract TGameKey initGameKeyByEncodeStr(String s,TGameKey gameKey) throws Exception;
    public  void saveGameKey(String gamepadName,TGameKey gameKey) {
//        if(notSaveDataIfError&&hasReadError){
//            return;
//        }
        Preferences preferences = Gdx.app.getPreferences(getFileKey());

        try {
            if(null==gameKey){
                preferences.remove(gamepadName);
            }else{
                preferences.putString(gamepadName, getEncodeStrByGameKey(gameKey));
            }
        } catch (Exception e) {
            SGDataHelper.getLog().printException(e,tag+".saveKnightGameKeyData ");
        }
        preferences.flush();
    }

//    public  TGameKey readGameKey(String gamepadName,Class<?> gamepadType) {
//        Preferences preferences = Gdx.app.getPreferences(getFileKey());
//        SashaData sasha = null;
//        SGCharacter cOld = null;
//        try {
//            String s = preferences.getString(gamepadName);
//            if (null == s || "".equals(s)) {
//                return null;
////				sasha = SashaData.initCharacter(c);
////				// return sasha;
//            } else {
////				sasha = initSashaByEncodeStr(s);
//                return initGameKeyByEncodeStr(s, gamepadType);
//            }
//        } catch (Exception e) {
//            SGDataHelper.getLog().printException(e,tag);
//        }
//        return null;
//    }

    public  TGameKey readGameKey(String gamepadName
//            ,Class<?> gamepadType
                                 ,TGameKey gameKey
    ) {
        Preferences preferences = Gdx.app.getPreferences(getFileKey());
//        SashaData sasha = null;
//        SGCharacter cOld = null;
        try {
            String s = preferences.getString(gamepadName);
            if (null == s || "".equals(s)) {
                return null;
//				sasha = SashaData.initCharacter(c);
//				// return sasha;
            } else {
//				sasha = initSashaByEncodeStr(s);
//                return initGameKeyByEncodeStr(s, gamepadType);
                return initGameKeyByEncodeStr(s, gameKey);
            }
        } catch (Exception e) {
            SGDataHelper.getLog().printException(e,tag);
        }
        return null;
    }
    // ---------------- KnightGameKey存取相关 end ------------------

}
