package com.sellgirl.gamepadtool.android.model;

public class ButtonInfo {
    public String label;
    public float x, y;
    public float radius;

    public ButtonInfo(String label, float x, float y, float radius) {
        this.label = label;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    public boolean isPosIn(float x,float y){
        return this.x-radius<=x&&x<=this.x+radius
                &&this.y-radius<=y&&y<=this.y+radius;
    }
}