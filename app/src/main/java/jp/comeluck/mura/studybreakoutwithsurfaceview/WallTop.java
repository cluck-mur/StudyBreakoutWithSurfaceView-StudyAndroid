package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.util.Log;

public class WallTop extends Wall {
    /**
     * コンストラクター
     *
     * @param left
     * @param top
     * @param height
     * @param width
     */
    public WallTop(int left, int top, int height, int width) {
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
        int ball_top = ball.getTop();
        int ball_bottom = ball.getBottom();
        double angle = ball.getAngle();
        if (ball_top < top + height) {
            ret_bool = true;

            Log.d("WallTop", String.format("上の壁"));
            if (angle >= 270 && angle < 360) {
                double incidence_angle = 360 - angle;
                angle = (angle + (incidence_angle * 2)) % 360;
            } else if (angle >= 180 && angle < 270) {
                double incidence_angle = angle - 180;
                angle = (angle - (incidence_angle * 2)) % 360;
            }
            ball_top = top + height + (int)(ball.getRadius() * 2);
            Log.d("WallTop", String.format("Angle [%e]", angle));

            ball.setTop(ball_top);
            ball.setAngle(angle);
        }

        return ret_bool;
    }
}
