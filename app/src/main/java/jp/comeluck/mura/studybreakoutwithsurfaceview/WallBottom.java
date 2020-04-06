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
     * ボールが衝突するまでに必要な時間を計算する
     * @param ball
     * @return  衝突までの時間、 衝突しない場合は -1 を返す
     */
    @Override
//    public long calcNecessaryTimeToHit(Ball ball, UpdateDisplayIf update_display_if) {
    public HitProcessInterface calcNecessaryTimeToHit(Ball ball, UpdateDisplayIf update_display_if) {
        BallCenter ball_center = ball.getCenter();
        int ball_left = ball.getLeft();
        int ball_right = ball.getRight();
        int ball_top = ball.getTop();
        int ball_bottom = ball.getBottom();
        double angle = ball.getAngle();

        int bottom = top + height;
        int right = left + width;

        // ボールと壁との距離 縦方向
        int distance_height = calcDistanceHeight(ball);

        if ((angle >= 90 && angle < 180) || (angle >= 0 && angle < 90)) {
            // ボール下点と上壁が同じ縦方向位置になるまでの時間
            double radians = Math.toRadians(angle);
            long hitable_msec = (long)(distance_height / (ball.getSpeed() * Math.abs(Math.sin(radians))));
            if (hitable_msec < 0) {
                Log.d("WallBottom", "hitable_msec がマイナス");
            }

            // ここまでの経過時間内に衝突の可能性あるか
            if (hitable_msec != -1 && hitable_msec <= update_display_if.getElapsedTime()) {
                // ボール中心がhitable_msecの間に移動した後の横方向の位置
                int move_x = (int) ((ball.getSpeed() * hitable_msec) * Math.abs(Math.cos(radians)));
                int tmp_ball_center_x = ball_center.x + move_x;
                // 壁の幅に収まっていたら衝突
                if (tmp_ball_center_x >= left + ball.getRadius() && tmp_ball_center_x < (left + width - ball.getRadius())) {
                    // Y方向の移動距離
                    int move_y = (int) ((ball.getSpeed() * hitable_msec) * Math.abs(Math.sin(radians)));
                    // Y位置を計算
                    int tmp_ball_center_y = ball_center.y + move_y;

                    HitProcessInterface hpi = new HitProcessInterface();
                    hpi.setData(this, hitable_msec, tmp_ball_center_x, tmp_ball_center_y, HitProcessInterface.HitSide.TOP);
                    return hpi;
                }
            }
        }

        return null;
    }


    /**
     * ボールがぶつかったか判定
     * @param ball
     * @return
     */
    @Override
    public HitProcessInterface checkHit(Ball ball, UpdateDisplayIf update_display_if) {
        Log.d("WallBottom", "in checkHit()");
        boolean ret_bool = false;

        BallCenter ball_center = ball.getCenter();
        int ball_top = ball.getTop();
        int ball_left = ball.getLeft();
        int ball_bottom = ball.getBottom();
        double angle = ball.getAngle();

        int bottom = top + height;
        int right = left + width;

        // ボールと壁との距離 縦方向
        int distance_height = calcDistanceHeight(ball);
        // 縦方向の距離が正の値だったら
        if (distance_height > 0) {
            // ボールが衝突するまでの時間
            HitProcessInterface hpi = calcNecessaryTimeToHit(ball, update_display_if);
            // ボールが衝突する可能性がある場合
            if (hpi != null) {
                // バウンド角（反射角）とX位置を計算
                Log.d("WallBottom", String.format("下の壁"));
                if (angle >= 90 && angle < 180) {
                    // バウンド角を計算
                    double incidence_angle = 180 - angle;
                    angle = (angle + (incidence_angle * 2)) % 360;
                } else if (angle >= 0 && angle < 90) {
                    // バウンド角を計算
                    double incidence_angle = angle;
                    angle = 360 - Math.abs(((angle - (incidence_angle * 2)) % 360));
                }
                hpi.setNewAngle(angle);
                Log.d("WallBottom", String.format("Angle [%e]", angle));
            }
        }
        return null;
    }

    /**
     * ボールと下壁の距離 を取得
     * @param ball
     * @return
     */
    protected int calcDistanceHeight(Ball ball) {
        BallCenter ball_center = ball.getCenter();
        int ball_left = ball.getLeft();
        int ball_right = ball.getRight();
        int ball_top = ball.getTop();
        int ball_bottom = ball.getBottom();
        double angle = ball.getAngle();

        int bottom = top + height;
        int right = left + width;

        return top - ball_bottom;
    }
}
