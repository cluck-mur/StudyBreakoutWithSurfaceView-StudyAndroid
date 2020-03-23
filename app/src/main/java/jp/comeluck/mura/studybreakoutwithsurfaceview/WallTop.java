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
    public boolean CheckHit(Ball ball) {
        boolean ret_bool = false;
        int ball_top = ball.GetBallTop();
        int ball_bottom = ball.GetBallBottom();
        double angle = ball.GetAngle();
        if (ball_top < Top + Height) {
            ret_bool = true;

            Log.d("WallTop", String.format("上の壁"));
            if (angle >= 270 && angle < 360) {
                double incidence_angle = 360 - angle;
                angle = (angle + (incidence_angle * 2)) % 360;
            } else if (angle >= 180 && angle < 270) {
                double incidence_angle = angle - 180;
                angle = (angle - (incidence_angle * 2)) % 360;
            }
            ball_top = Top + Height + (int)(ball.GetRadius() * 2);
            Log.d("WallTop", String.format("Angle [%e]", angle));

            ball.SetBallTop(ball_top);
            ball.SetAngle(angle);
        }

        return ret_bool;
    }
}
