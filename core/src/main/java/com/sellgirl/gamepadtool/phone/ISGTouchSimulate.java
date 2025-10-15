package com.sellgirl.gamepadtool.phone;

public interface ISGTouchSimulate {
    //短触摸
    void simulateTouchFromGdx(float x, float y//, final int action
    );
//    void simulateTouchFromGdx(
//            float x0, float y0,
//            float x, float y//, final int action
//    );

    //长触摸，由于是持久状态，所以需要pointerId区分
//    boolean simulateTouchDown(float x, float y, int pointerId);
    boolean simulateTouchDown(float x0, float y0,float x, float y, int pointerId);
    boolean simulateTouchMove(float x, float y, int pointerId);
    boolean simulateTouchUp(float x, float y, int pointerId);

    //右摇杆其实和左摇杆是不一样的，操作视觉时更像是每帧都从x0 y0开始移动
    boolean simulateDrag(float x0, float y0,float x, float y);

    boolean simulate();
}
