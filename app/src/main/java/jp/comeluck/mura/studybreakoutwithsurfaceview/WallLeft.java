package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.util.Log;

public class WallLeft extends Wall {
    /**
     * コンストラクター
     * @param left
     * @param top
     * @param height
     * @param width
     */
    public WallLeft(int left, int top, int height, int width) {
        super(left, top, height, width);
    }

    /**
     * ボールがぶつかったか判定
     * @param ball
     * @return
     */
    @Override
    public boolean checkHit(Ball ball) {
        boolean ret_bool = false;
        int ball_left = ball.getLeft();
        double angle = ball.getAngle();
        if (ball_left < left + width) {
            ret_bool = true;

            Log.d("WallLeft", String.format("左の壁"));
            if (angle >= 180 && angle < 270) {
                double incidence_angle = 270 - angle;
                angle = (angle + (incidence_angle * 2)) % 360;
            } else if (angle >= 90 && angle < 180) {
                double incidence_angle = angle - 90;
                angle = (angle - (incidence_angle * 2)) % 360;
            }
            ball_left = left + width + (int)(ball.getRadius() * 2);
            Log.d("WallLeft", String.format("Angle [%e]", angle));

            ball.setLeft(ball_left);
            ball.setAngle(angle);
        }

        return ret_bool;
    }
}
