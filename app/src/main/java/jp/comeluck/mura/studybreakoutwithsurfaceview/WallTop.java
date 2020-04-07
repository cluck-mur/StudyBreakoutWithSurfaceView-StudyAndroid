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
    public WallTop(double left, double top, double height, double width) {
        super(left, top, height, width);
    }

    /**
     * ボールが衝突するまでに必要な時間を計算する
     * @param ball
     * @return  衝突までの時間、 衝突しない場合は -1 を返す
     */
    @Override
    public HitProcessInterface calcNecessaryTimeToHit(Ball ball, UpdateDisplayIf update_display_if) {
        BallCenter ball_center = ball.getCenter();
        double ball_left = ball.getLeft();
        double ball_right = ball.getRight();
        double ball_top = ball.getTop();
        double ball_bottom = ball.getBottom();
        double angle = ball.getAngle();

        double bottom = top + height;
        double right = left + width;

        // ボールと壁との距離 縦方向
        double distance_height = calcDistanceHeight(ball);

        if ((angle >= 270 && angle < 360) || (angle >= 180 && angle < 270)) {
            // ボール下点と上壁が同じ縦方向位置になるまでの時間
            double radians = Math.toRadians(angle);
            long hitable_msec = Double.valueOf(Math.ceil(distance_height / (ball.getSpeed() * Math.abs(Math.sin(radians))))).longValue();
            if (hitable_msec < 0) {
                Log.d("WallTop", "hitable_msec がマイナス");
            }
            // ここまでの経過時間内に衝突の可能性あるか
            if (hitable_msec != -1 && hitable_msec <= update_display_if.getElapsedTime()) {
                // ボール中心がhitable_msecの間に移動した後の横方向の位置
                double move_x = (ball.getSpeed() * hitable_msec) * Math.abs(Math.cos(radians));
                double tmp_ball_center_x = ball_center.x + move_x;
                if (tmp_ball_center_x >= left + ball.getRadius() && tmp_ball_center_x < (left + width - ball.getRadius())) {
                    // Y方向の移動距離
                    double move_y = (ball.getSpeed() * hitable_msec) * Math.abs(Math.sin(radians));
                    // Y位置を計算
                    double tmp_ball_center_y = ball_center.y - move_y;

                    HitProcessInterface hpi = new HitProcessInterface();
                    hpi.setData(this, hitable_msec, tmp_ball_center_x, tmp_ball_center_y, HitProcessInterface.HitSide.BOTTOM);
                    return hpi;
                }
            }
        }
        return null;
    }

    /**
     * ボールがぶつかったか判定
     *
     * @param ball
     * @return
     */
    @Override
    public HitProcessInterface checkHit(Ball ball, UpdateDisplayIf update_display_if) {
        Log.d("WallTop", "in checkHit()");
        boolean ret_bool = false;

        BallCenter ball_center = ball.getCenter();
        double ball_top = ball.getTop();
        double ball_left = ball.getLeft();
        double ball_bottom = ball.getBottom();
        double angle = ball.getAngle();

        double bottom = top + height;
        double right = left + width;

        // ボールと壁との距離 縦方向
        double distance_height = calcDistanceHeight(ball);
        // 縦方向の距離が正の値だったら
        if (distance_height > 0) {
            // ボールと衝突するまでの時間
            HitProcessInterface hpi = calcNecessaryTimeToHit(ball, update_display_if);
            // ボールと衝突する可能性がある場合
            if (hpi != null) {
                // バウンド角（反射角）とX位置を計算
                Log.d("WallTop", String.format("上の壁"));
                if (angle >= 270 && angle < 360) {
                    // バウンド角を計算
                    double incidence_angle = 360 - angle;
                    angle = (angle + (incidence_angle * 2)) % 360;
                } else if (angle >= 180 && angle < 270) {
                    // バウンド角を計算
                    double incidence_angle = angle - 180;
                    angle = (angle - (incidence_angle * 2)) % 360;
                }
                hpi.setNewAngle(angle);
                Log.d("WallBottom", String.format("Angle [%e]", angle));

                return hpi;
            }
        }
        return null;
    }

    /**
     * ボールと上壁の距離 を取得
     * @param ball
     * @return
     */
    protected double calcDistanceHeight(Ball ball) {
        BallCenter ball_center = ball.getCenter();
        double ball_left = ball.getLeft();
        double ball_right = ball.getRight();
        double ball_top = ball.getTop();
        double ball_bottom = ball.getBottom();
        double angle = ball.getAngle();

        double bottom = top + height;
        double right = left + width;

        return ball_top - bottom;
    }
}
