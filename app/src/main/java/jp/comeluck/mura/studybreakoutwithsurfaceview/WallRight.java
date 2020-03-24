package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.util.Log;

public class WallRight extends Wall {
    /**
     * コンストラクター
     *
     * @param left
     * @param top
     * @param height
     * @param width
     */
    public WallRight(int left, int top, int height, int width) {
        super(left, top, height, width);
    }

    /**
     * ボールがぶつかったか判定
     *
     * @param ball
     * @return
     */
    @Override
    public boolean checkHit(Ball ball) {
        boolean ret_bool = false;
        int ball_left = ball.getLeft();
        int ball_right = ball.getRight();
        double angle = ball.getAngle();
        if (ball_right > left) {
            ret_bool = true;

            Log.d("WallRight", String.format("右の壁"));
            if (angle >= 0 && angle < 90) {
                double incidence_angle = 90 - angle;
                angle = (angle + (incidence_angle * 2)) % 360;
            } else if (angle >= 270 && angle < 360) {
                double incidence_angle = angle - 270;
                angle = (angle - (incidence_angle * 2)) % 360;
            }
            ball_left = left - (int)(ball.getRadius() * 2);
            Log.d("WallRight", String.format("Angle [%e]", angle));

            ball.setLeft(ball_left);
            ball.setAngle(angle);
        }

        return ret_bool;
    }
}
