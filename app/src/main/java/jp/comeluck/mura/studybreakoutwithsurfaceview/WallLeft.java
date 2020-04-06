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

//        // ボールと壁との距離 縦方向
//        int distance_height = top - ball_bottom;
        // ボールとブロックとの距離 横方向
        int distance_width = calcDistanceWidth(ball);

        if ((angle >= 180 && angle < 270) || (angle >= 90 && angle < 180)) {
            // ボール下点と上壁が同じ縦方向位置になるまでの時間
            double radians = Math.toRadians(angle);
            long hitable_msec = (long) (distance_width / (ball.getSpeed() * Math.abs(Math.cos(radians))));
            if (hitable_msec < 0) {
                Log.d("WallLeft", "hitable_msec がマイナス");
            }
//            return hitable_msec;
            // ここまでの経過時間内に衝突の可能性あるか
            if (hitable_msec != -1 && hitable_msec <= update_display_if.getElapsedTime()) {
                // ボール中心がhitable_msecの間に移動した後の縦方向の位置
//                double radians = Math.toRadians(angle);
                int move_y = (int) ((ball.getSpeed() * hitable_msec) * Math.abs(Math.sin(radians)));
                int tmp_ball_center_y = ball_center.x + move_y;
                // 壁の高さに収まっていたら衝突
                if (tmp_ball_center_y >= top + ball.getRadius() && tmp_ball_center_y < (top + height - ball.getRadius())) {
                    // 衝突までの時間を保存
//                    elapsed_time2 = hitable_msec;
                    update_display_if.setElapsedTime2(update_display_if.getElapsedTime2() + hitable_msec);

                    // X方向の移動距離
                    int move_x = (int) ((ball.getSpeed() * hitable_msec) * Math.abs(Math.cos(radians)));
                    // X位置を計算
//                    ball_left = ball_left - move_x;
                    int tmp_ball_center_x = ball_center.x - move_x;

                    HitProcessInterface hpi = new HitProcessInterface();
                    hpi.setData(this, hitable_msec, tmp_ball_center_x, tmp_ball_center_y, HitProcessInterface.HitSide.RIGHT);
                    return hpi;
                }
            }
        }
//        } else {
//            return -1;
//        }
        return null;
    }


    /**
     * ボールがぶつかったか判定
     * @param ball
     * @return
     */
    @Override
//    public boolean checkHit(Ball ball, UpdateDisplayIf update_display_if) {
    public HitProcessInterface checkHit(Ball ball, UpdateDisplayIf update_display_if) {
        Log.d("WallLeft", "in checkHit()");
        boolean ret_bool = false;

        BallCenter ball_center = ball.getCenter();
        int ball_top = ball.getTop();
        int ball_left = ball.getLeft();
        int ball_bottom = ball.getBottom();
        double angle = ball.getAngle();

        int bottom = top + height;
        int right = left + width;

//        // ボールと壁との距離 縦方向
//        int distance_height = calcDistanceHeight(ball);
        // ボールとブロックとの距離 横方向
        int distance_width = calcDistanceWidth(ball);

        // 横方向の距離が正の値だったら
        if (distance_width > 0) {
            // ボール左点と左壁が同じ横方向位置になるまでの時間
//            long hitable_msec = (long)(distance_height / Math.sin(ball.getSpeed()));
//            long hitable_msec = calcNecessaryTimeToHit(ball, update_display_if);
            HitProcessInterface hpi = calcNecessaryTimeToHit(ball, update_display_if);

//            // ここまでの経過時間内に衝突の可能性あるか
//            if (hitable_msec != -1 && hitable_msec <= update_display_if.getElapsedTime()) {
//                // ボール中心がhitable_msecの間に移動した後の縦方向の位置
//                double radians = Math.toRadians(angle);
//                int move_y = (int)((ball.getSpeed() * hitable_msec) * Math.abs(Math.sin(radians)));
//                int tmp_ball_center_y = ball_center.x + move_y;
//                // 壁の高さに収まっていたら衝突
//                if (tmp_ball_center_y >= top + ball.getRadius() && tmp_ball_center_y < (top + height - ball.getRadius())) {
//                    // 衝突までの時間を保存
////                    elapsed_time2 = hitable_msec;
//                    update_display_if.setElapsedTime2(update_display_if.getElapsedTime2() + hitable_msec);
//
//                    // X方向の移動距離
//                    int move_x = (int)((ball.getSpeed() * hitable_msec) * Math.abs(Math.cos(radians)));
//                    // X位置を計算
//                    ball_left = ball_left - move_x;
//
//                    // バウンド角（反射角）とY位置を計算
//                    Log.d("WallLeft", String.format("左の壁"));
//                    if (angle >= 180 && angle < 270) {
//                        // バウンド角を計算
//                        double incidence_angle = 270 - angle;
//                        angle = (angle + (incidence_angle * 2)) % 360;
//                        // Y位置を計算
//                        ball_top = ball_top - move_y;
//                    } else if (angle >= 90 && angle < 180) {
//                        // バウンド角を計算
//                        double incidence_angle = angle - 90;
//                        angle = (angle - (incidence_angle * 2)) % 360;
//                        // Y位置を計算
//                        ball_top = ball_top + move_y;
//                    }
//                    Log.d("WallBottom", String.format("Angle [%e]", angle));
//
//                    // ボールにデータをセット
//                    ball.setLeft(ball_left);
//                    ball.setTop(ball_top);
//                    ball.setAngle(angle);
//                    // 衝突したフラグをセット
//                    ret_bool = true;
//                }
//            }
            if (hpi != null) {
                // バウンド角（反射角）とY位置を計算
                Log.d("WallLeft", String.format("左の壁"));
                if (angle >= 180 && angle < 270) {
                    // バウンド角を計算
                    double incidence_angle = 270 - angle;
                    angle = (angle + (incidence_angle * 2)) % 360;
                } else if (angle >= 90 && angle < 180) {
                    // バウンド角を計算
                    double incidence_angle = angle - 90;
                    angle = (angle - (incidence_angle * 2)) % 360;
                }
                hpi.setNewAngle(angle);
                Log.d("WallBottom", String.format("Angle [%e]", angle));

                return hpi;
            }
        }

//        int ball_left = ball.getLeft();
//        double angle = ball.getAngle();
//        if (ball_left < left + width) {
//            ret_bool = true;
//
//            Log.d("WallLeft", String.format("左の壁"));
//            if (angle >= 180 && angle < 270) {
//                double incidence_angle = 270 - angle;
//                angle = (angle + (incidence_angle * 2)) % 360;
//            } else if (angle >= 90 && angle < 180) {
//                double incidence_angle = angle - 90;
//                angle = (angle - (incidence_angle * 2)) % 360;
//            }
//            ball_left = left + width + (int)(ball.getRadius() * 2);
//            Log.d("WallLeft", String.format("Angle [%e]", angle));
//
//            ball.setLeft(ball_left);
//            ball.setAngle(angle);
//        }

//        return ret_bool;
        return null;
    }

    /**
     * ボールと左壁の距離 を取得
     * @param ball
     * @return
     */
    protected int calcDistanceWidth(Ball ball) {
        BallCenter ball_center = ball.getCenter();
        int ball_left = ball.getLeft();
        int ball_right = ball.getRight();
        int ball_top = ball.getTop();
        int ball_bottom = ball.getBottom();
        double angle = ball.getAngle();

        int bottom = top + height;
        int right = left + width;

        return ball_left - right;
    }
}
