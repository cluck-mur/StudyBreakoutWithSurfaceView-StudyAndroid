package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.util.Log;

public class WallBottom extends Wall {
    /**
     * コンストラクター
     * @param left
     * @param top
     * @param height
     * @param width
     */
    public WallBottom(int left, int top, int height, int width) {
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
        int ball_top = ball.getTop();
        int ball_bottom = ball.getBottom();
        double angle = ball.getAngle();
        if (ball_bottom > top) {
            ret_bool = true;

            Log.d("WallBottom", String.format("下の壁"));
            if (angle >= 90 && angle < 180) {
                double incidence_angle = 180 - angle;
                angle = (angle + (incidence_angle * 2)) % 360;
            } else if (angle >= 0 && angle < 90) {
                double incidence_angle = angle;
                angle = 360 - Math.abs(((angle - (incidence_angle * 2)) % 360));
            }
            ball_top = top - (int)(ball.getRadius() * 2);
            Log.d("WallBottom", String.format("Angle [%e]", angle));

            ball.setTop(ball_top);
            ball.setAngle(angle);
        }

        return ret_bool;
    }
}
