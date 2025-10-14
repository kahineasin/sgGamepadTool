package com.sellgirl.gamepadtool.phone;

public interface ISGTouchSimulate {
    //短触摸
    void simulateTouchFromGdx(float x, float y//, final int action
    );

    //长触摸
    boolean simulateTouchDown(float x, float y, int pointerId);
    boolean simulateTouchMove(float x, float y, int pointerId);
    boolean simulateTouchUp(float x, float y, int pointerId);

    boolean simulate();
}
