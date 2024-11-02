package com.sellgirl.gamepadtool.model;

import com.sellgirl.sgGameHelper.gamepad.XBoxKey;

public class KeySimulateItem {
    public enum KeyType{
        NONE,
        KEYBOARD,MOUSE
    }
    private KeyType dstKeyType;
    private int srcKey;
    private int dstKey;

    public KeySimulateItem(){}
    public KeySimulateItem(int srcKey){
        this.srcKey=srcKey;
    }


    public KeyType getDstKeyType() {
        return dstKeyType;
    }

    public void setDstKeyType(KeyType dstKeyType) {
        this.dstKeyType = dstKeyType;
    }

    public int getSrcKey() {
        return srcKey;
    }

    public void setSrcKey(int srcKey) {
        this.srcKey = srcKey;
    }

    public int getDstKey() {
        return dstKey;
    }

    public void setDstKey(int dstKey) {
        this.dstKey = dstKey;
    }
}
