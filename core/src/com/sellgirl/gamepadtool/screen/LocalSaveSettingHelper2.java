package com.sellgirl.gamepadtool.screen;

//import com.mygdx.game.sasha.bulletD3.IKnightSashaGameKey;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.shade.com.alibaba.fastjson.TypeReference;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
//import com.mygdx.game.sasha.util.Constants;
//import com.mygdx.game.sasha.util.LocalSaveSettingHelperBase;
//import com.mygdx.game.share.SGDataHelper7;
import com.sellgirl.gamepadtool.model.KeySimulateItem;
import com.sellgirl.gamepadtool.util.Constants;
import com.sellgirl.gamepadtool.util.LocalSaveSettingHelperBase;
import com.sellgirl.sgGameHelper.SGDataHelper7;
import com.sellgirl.sgJavaHelper.AES;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 本地保存的设置
 */
public class LocalSaveSettingHelper2 extends LocalSaveSettingHelperBase<IKnightSashaGameKey> {
    private static  final String tag= LocalSaveSettingHelper2.class.getName();
    @Override
    protected String getFileKey() {
        return Constants.GAMEPAD_TO_KEYBOARD_SETTING;
    }
    // ---------------- KnightGameKey存取相关 ------------------

    protected String getEncodeStrByGameKey(IKnightSashaGameKey sasha) {

//        String str = sasha.getJump() + "|" + sasha.getDodge() + "|" + sasha.getAttack() + "|" + sasha.getSkill() + "|"
//                + sasha.getDefend() + "|" + sasha.getL2() + "|" + sasha.getKick() + "|" + sasha.getR2()
//                + "|" + sasha.getUp()+ "|" + sasha.getDown()+ "|" + sasha.getLeft()+ "|" + sasha.getRight()
//                + "|" + sasha.getL3()+ "|" + sasha.getR3()+ "|" + sasha.getBack()+ "|" + sasha.getStart()
//                + "|" + sasha.getStick1Up()+ "|" + sasha.getStick1Down()+ "|" + sasha.getStick1Left()+ "|" + sasha.getStick1Right()
//                + "|" + sasha.getStick2Up()+ "|" + sasha.getStick2Down()+ "|" + sasha.getStick2Left()+ "|" + sasha.getStick2Right();

        String str = JSONObject.toJSONString(sasha.getKeyMap());
        try {
            return AES.AESEncryptDemo(str, SGDataHelper.decodeBase64(key));
        } catch (Exception e) {
            //hasReadError=true;
            SGDataHelper.getLog().printException(e,tag+".getEncodeStrBySasha ");
        }
        return null;
    }
    protected  IKnightSashaGameKey initGameKeyByEncodeStr(String s,IKnightSashaGameKey sasha) throws Exception {

        //String[] s2 = AES.AESDecryptDemo(s, SGDataHelper.decodeBase64(key)).split("[|]");
        ////IKnightSashaGameKey sasha = SGKeyboardGamepad.class==gamepadType?new GameKeyKeyboard(): new GameKey();
//        if(23<s2.length) {
//            sasha.setJump(Integer.valueOf(s2[0]));
//            sasha.setDodge(Integer.valueOf(s2[1]));
//            sasha.setAttack(Integer.valueOf(s2[2]));
//            sasha.setSkill(Integer.valueOf(s2[3]));
//            sasha.setDefend(Integer.valueOf(s2[4]));
//            sasha.setL2(Integer.valueOf(s2[5]));
//            sasha.setKick(Integer.valueOf(s2[6]));
//            sasha.setR2(Integer.valueOf(s2[7]));
//            sasha.setUp(Integer.valueOf(s2[8]));
//            sasha.setDown(Integer.valueOf(s2[9]));
//            sasha.setLeft(Integer.valueOf(s2[10]));
//            sasha.setRight(Integer.valueOf(s2[11]));
//            sasha.setL3(Integer.valueOf(s2[12]));
//            sasha.setR3(Integer.valueOf(s2[13]));
//            sasha.setBack(Integer.valueOf(s2[14]));
//            sasha.setStart(Integer.valueOf(s2[15]));
//
//            sasha.setStick1Up(Integer.valueOf(s2[16]));
//            sasha.setStick1Down(Integer.valueOf(s2[17]));
//            sasha.setStick1Left(Integer.valueOf(s2[18]));
//            sasha.setStick1Right(Integer.valueOf(s2[19]));
//            sasha.setStick2Up(Integer.valueOf(s2[20]));
//            sasha.setStick2Down(Integer.valueOf(s2[21]));
//            sasha.setStick2Left(Integer.valueOf(s2[22]));
//            sasha.setStick2Right(Integer.valueOf(s2[23]));
//        }
        try {
            List<KeySimulateItem> map = JSONObject.parseObject(AES.AESDecryptDemo(s, SGDataHelper.decodeBase64(key)),
                    new TypeReference<List<KeySimulateItem>>() {
                    });
            sasha.setKeyMap(map);
        }catch (Throwable e){
            SGDataHelper.getLog().writeException(e,tag);
        }
        return sasha;
    }

    protected String getCombineMapEncodeStrByGameKey(IKnightSashaGameKey sasha) {

//        String str = JSONObject.toJSONString(sasha.getCombinedMap());
        String str = JSONObject.toJSONString(sasha.getCombinedMap2());
        try {
            return AES.AESEncryptDemo(str, SGDataHelper.decodeBase64(key));
        } catch (Exception e) {
            //hasReadError=true;
            SGDataHelper.getLog().printException(e,tag+".getEncodeStrBySasha ");
        }
        return null;
    }

    protected  IKnightSashaGameKey initCombineMapGameKeyByEncodeStr(String s,IKnightSashaGameKey sasha) throws Exception {

        //String[] s2 = AES.AESDecryptDemo(s, SGDataHelper.decodeBase64(key)).split("[|]");
//        Map<Integer,Integer> map=JSONObject.parseObject(AES.AESDecryptDemo(s, SGDataHelper.decodeBase64(key)),
//                new TypeReference<Map<Integer,Integer>>(){});
        List<KeySimulateItem> map=JSONObject.parseObject(AES.AESDecryptDemo(s, SGDataHelper.decodeBase64(key)),
                new TypeReference<List<KeySimulateItem>>(){});
        //IKnightSas
//        sasha.applyCombinedMap(map);
        sasha.setCombinedMap2(map);
        return sasha;
    }
    public List<String> getSettings(String gamepadName){

        Preferences preferences = Gdx.app.getPreferences(getFileKey());
//        SashaData sasha = null;
//        SGCharacter cOld = null;
        List<String> r=new ArrayList<>();
        try {
            String s = preferences.getString(gamepadName+"_settings");
            if (null == s || "".equals(s)) {
//                return null;
//				sasha = SashaData.initCharacter(c);
//				// return sasha;
            } else {
//				sasha = initSashaByEncodeStr(s);
//                return initGameKeyByEncodeStr(s, gamepadType);
                for(String i:s.split(",")){
                    r.add(i);
                }
            }
        } catch (Exception e) {
            SGDataHelper.getLog().printException(e,tag);
        }
        if(r.isEmpty()){
            r.add("setting1");
        }
        return r;
    }
//    public  void saveSetting(String gamepadName,TGameKey gameKey) {
////        if(notSaveDataIfError&&hasReadError){
////            return;
////        }
//        Preferences preferences = Gdx.app.getPreferences(getFileKey());
//
//        try {
//            if(null==gameKey){
//                preferences.remove(gamepadName);
//            }else{
//                preferences.putString(gamepadName, getEncodeStrByGameKey(gameKey));
//            }
//        } catch (Exception e) {
//            SGDataHelper.getLog().printException(e,tag+".saveKnightGameKeyData ");
//        }
//        preferences.flush();
//    }

    public  void saveGameKey(String gamepadName,String setting,IKnightSashaGameKey gameKey){
        Preferences preferences = Gdx.app.getPreferences(getFileKey());

        try {
            String s = preferences.getString(gamepadName+"_settings");
            List<String> r=new ArrayList<>();
            if (null == s || "".equals(s)) {
            } else {
//				sasha = initSashaByEncodeStr(s);
//                return initGameKeyByEncodeStr(s, gamepadType);
                for(String i:s.split(",")){
                    r.add(i);
                }
            }
            if(null==gameKey){
                preferences.remove(gamepadName+setting);
                preferences.remove(gamepadName+setting+"_combine");
                for(int i=r.size()-1;0<=i;i--){
                    if(r.get(i).equals(setting)){
                        r.remove(setting);
                    }
                }
            }else{
                preferences.putString(gamepadName+setting, getEncodeStrByGameKey(gameKey));
                preferences.putString(gamepadName+setting+"_combine", getCombineMapEncodeStrByGameKey(gameKey));
                boolean exist=false;
                for(int i=r.size()-1;0<=i;i--){
                    if(r.get(i).equals(setting)){
                        exist=true;
                        break;
                    }
                }
                if(!exist){
                    r.add(setting);
                }
            }
            preferences.putString(gamepadName+"_settings", SGDataHelper7.join(",",r));
//            preferences.putString(gamepadName+"_settings", String.join(",",r));
        } catch (Exception e) {
            SGDataHelper.getLog().printException(e,tag+".saveKnightGameKeyData ");
        }
        preferences.flush();
    }

    @Override
    public  IKnightSashaGameKey readGameKey(String gamepadName
//            ,Class<?> gamepadType
            ,IKnightSashaGameKey gameKey
    ) {
        Preferences preferences = Gdx.app.getPreferences(getFileKey());
//        SashaData sasha = null;
//        SGCharacter cOld = null;
        try {
            String s = preferences.getString(gamepadName);
            if (null == s || "".equals(s)) {
                //return null;
            } else {
                gameKey= initGameKeyByEncodeStr(s, gameKey);
                //return initGameKeyByEncodeStr(s, gameKey);
            }
        } catch (Exception e) {
            SGDataHelper.getLog().printException(e,tag);
        }
        try {
            String s2 = preferences.getString(gamepadName+"_combine");

            if (null == s2 || "".equals(s2)) {
                //return null;
            } else {
                gameKey=initCombineMapGameKeyByEncodeStr(s2, gameKey);
                //return initGameKeyByEncodeStr(s, gameKey);
            }
        }catch (Throwable e2){

        }
        return gameKey;
    }
}
