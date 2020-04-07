package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * ブロック クラス
 */
public class Block {
    protected double left;
    protected double top;
    protected double height;
    protected double width;
    protected int fillColor = Color.CYAN;

    protected boolean isDisplay = true;

    /**
     * コンストラクター
     * @param left
     * @param top
     * @param height
     * @param width
     */
    public Block(double left, double top, double height, double width, int fill_color) {
        this.left = left;
        this.top = top;
        this.height = height;
        this.width = width;
        this.fillColor = fill_color;
    }

    /**
     * isDisplay のゲッター
     * @return
     */
    public boolean getIsDisplay() {
        return isDisplay;
    }

    /**
     * isDisplay のセッター
     * @param isDisplay
     */
    public void setIsDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    /**
     * 表示更新
     * @param canvas
     */
    public void updateDisplay(Canvas canvas) {
        if (!isDisplay) {
            return;
        }

        Paint paint = new Paint();
        paint.setColor(fillColor);
        //paint.setFilterBitmap(true);
        float paint_left = Double.valueOf(left).floatValue();
        float paint_top = Double.valueOf(top).floatValue();
        float paint_right = Double.valueOf(paint_left + width).floatValue();
        float paint_bottom = Double.valueOf(paint_top + height).floatValue();
//        Log.d("Block", String.format("Left [%f] Top %f] Right [%f] Bottom [%f]",
//                paint_left, paint_top, paint_right, paint_bottom));
        canvas.drawRect(paint_left, paint_top, paint_right, paint_bottom, paint);
    }

    /**
     * ボールが衝突するまでに必要な時間を計算する
     * @param ball
     * @return  衝突までの時間、 衝突しない場合は -1 を返す
     */
//    @Override
    public HitProcessInterface calcNecessaryTimeToHit(Ball ball, UpdateDisplayIf update_display_if) {
        BallCenter ball_center = ball.getCenter();
        double ball_left = ball.getLeft();
        double ball_right = ball.getRight();
        double ball_top = ball.getTop();
        double ball_bottom = ball.getBottom();
        double angle = ball.getAngle();

        double bottom = top + height;
        double right = left + width;

        // *** 進行角度で分岐
        // 進行方向 右下
        if (angle >= 0 && angle < 90) {
            // *** ボール下点とブロック上面との衝突
            {
                // ボールとブロックとの距離 縦方向
                double distance_height = ball_bottom - top;
                // 縦方向の距離が正の値だったら
                if (distance_height > 0) {
                    // ボール下点とブロックの上面が同じ縦方向位置になるまでの時間
                    double radians = Math.toRadians(angle);
                    long hitable_msec = Double.valueOf(Math.ceil(distance_height / (ball.getSpeed() * Math.abs(Math.sin(radians))))).longValue();
                    // ここまでの経過時間内に衝突の可能性あるか
                    if (hitable_msec <= update_display_if.getElapsedTime()) {
                        // ボール中心がhitable_msecの間に移動した後の横方向の位置
                        double tmp_ball_center_x = ball_center.x + ((ball.getSpeed() * hitable_msec) * Math.abs(Math.cos(radians)));
                        // 衝突しているか
                        if (tmp_ball_center_x >= left && tmp_ball_center_x < right) {
                            double tmp_ball_center_y = ball_center.getY() + distance_height;
                            HitProcessInterface hpi = new HitProcessInterface();
                            hpi.setData(this, hitable_msec, tmp_ball_center_x, tmp_ball_center_y, HitProcessInterface.HitSide.TOP);
                            return hpi;
                        }
                    }
                }
            }
            // *** ブロック左面との衝突
            {
                // ボールとブロックとの距離 横方向
                double distance_width = ball_right - left;
                // 横方向の距離が正の値だったら
                if (distance_width > 0) {
                    // ボール右点とブロックの左面が同じ横方向位置になるまでの時間
                    double radians = Math.toRadians(angle);
                    long hitable_msec = Double.valueOf(Math.ceil(distance_width / (ball.getSpeed() * Math.abs(Math.cos(radians))))).longValue();
                    // ここまでの経過時間内に衝突の可能性あるか
                    if (hitable_msec <= update_display_if.getElapsedTime()) {
                        // ボール中心がhitable_msecの間に移動した後の縦方向の位置
                        double tmp_ball_center_y = ball_center.y + (Math.abs(Math.sin(ball.getSpeed() * hitable_msec)));
                        // 衝突しているか
                        if (tmp_ball_center_y >= top && tmp_ball_center_y < bottom) {
                            double tmp_ball_center_x = ball_center.getX() + distance_width;
                            HitProcessInterface hpi = new HitProcessInterface();
                            hpi.setData(this, hitable_msec, tmp_ball_center_x, tmp_ball_center_y, HitProcessInterface.HitSide.LEFT);
                            return hpi;
                        }
                    }
                }
            }
        }
        // 進行方向 左下
        else if (angle >= 90 && angle < 180) {
            // *** ボール下点とブロック上面との衝突
            {
                // ボールとブロックとの距離 縦方向
                double distance_height = ball_bottom - top;
                // 縦方向の距離が正の値だったら
                if (distance_height > 0) {
                    // ボール下点とブロックの上面が同じ縦方向位置になるまでの時間
                    double radians = Math.toRadians(angle);
                    long hitable_msec = Double.valueOf(Math.ceil(distance_height / (ball.getSpeed() * Math.abs(Math.sin(radians))))).longValue();
                    // ここまでの経過時間内に衝突の可能性あるか
                    if (hitable_msec <= update_display_if.getElapsedTime()) {
                        // ボール中心がhitable_msecの間に移動した後の横方向の位置
                        double tmp_ball_center_x = ball_center.x - ((ball.getSpeed() * hitable_msec) * Math.abs(Math.cos(radians)));
                        // 衝突しているか
                        if (tmp_ball_center_x >= left && tmp_ball_center_x < right) {
                            double tmp_ball_center_y = ball_center.getY() + distance_height;
                            HitProcessInterface hpi = new HitProcessInterface();
                            hpi.setData(this, hitable_msec, tmp_ball_center_x, tmp_ball_center_y, HitProcessInterface.HitSide.TOP);
                            return hpi;
                        }
                    }
                }
            }
            // *** ブロック右面との衝突
            {
                // ボールとブロックとの距離 横方向
                double distance_width = ball_left - right;
                // 横方向の距離が正の値だったら
                if (distance_width > 0) {
                    // ボール左点とブロックの右面が同じ横方向位置になるまでの時間
                    double radians = Math.toRadians(angle);
                    long hitable_msec = Double.valueOf(Math.ceil(distance_width / (ball.getSpeed() * Math.abs(Math.cos(radians))))).longValue();
                    // ここまでの経過時間内に衝突の可能性あるか
                    if (hitable_msec <= update_display_if.getElapsedTime()) {
                        // ボール中心がhitable_msecの間に移動した後の縦方向の位置
                        double tmp_ball_center_y = ball_center.y + ((ball.getSpeed() * hitable_msec) * (Math.abs(Math.sin(radians))));
                        // 衝突しているか
                        if (tmp_ball_center_y >= top && tmp_ball_center_y < bottom) {
                            double tmp_ball_center_x = ball_center.getX() - distance_width;
                            HitProcessInterface hpi = new HitProcessInterface();
                            hpi.setData(this, hitable_msec, tmp_ball_center_x, tmp_ball_center_y, HitProcessInterface.HitSide.RIGHT);
                            return hpi;
                        }
                    }
                }
            }
        }
        // 進行方向 左上
        else if (angle >= 180 && angle < 270) {
            // *** ボール上点とブロック下面との衝突
            {
                // ボールとブロックとの距離 縦方向
                double distance_height = ball_top - bottom;
                // 縦方向の距離が正の値だったら
                if (distance_height > 0) {
                    // ボール下点とブロックの上面が同じ縦方向位置になるまでの時間
                    double radians = Math.toRadians(angle);
                    long hitable_msec = Double.valueOf(Math.ceil(distance_height / (ball.getSpeed() * Math.abs(Math.sin(radians))))).longValue();
                    // ここまでの経過時間内に衝突の可能性あるか
                    if (hitable_msec <= update_display_if.getElapsedTime()) {
                        // ボール中心がhitable_msecの間に移動した後の横方向の位置
                        double tmp_ball_center_x = ball_center.x - ((ball.getSpeed() * hitable_msec) * (Math.abs(Math.cos(radians))));
                        // 衝突しているか
                        if (tmp_ball_center_x >= left && tmp_ball_center_x < right) {
                            double tmp_ball_center_y = ball_center.getY() - distance_height;
                            HitProcessInterface hpi = new HitProcessInterface();
                            hpi.setData(this, hitable_msec, tmp_ball_center_x, tmp_ball_center_y, HitProcessInterface.HitSide.BOTTOM);
                            return hpi;
                        }
                    }
                }
            }
            // *** ブロック右面との衝突
            {
                // ボールとブロックとの距離 横方向
                double distance_width = ball_left - right;
                // 横方向の距離が正の値だったら
                if (distance_width > 0) {
                    // ボール左点とブロックの右面が同じ横方向位置になるまでの時間
                    double radians = Math.toRadians(angle);
                    long hitable_msec = Double.valueOf(Math.ceil(distance_width / (ball.getSpeed() * Math.abs(Math.cos(radians))))).longValue();
                    // ここまでの経過時間内に衝突の可能性あるか
                    if (hitable_msec <= update_display_if.getElapsedTime()) {
                        // ボール中心がhitable_msecの間に移動した後の縦方向の位置
                        double tmp_ball_center_y = ball_center.y - ((ball.getSpeed() * hitable_msec) * Math.abs(Math.sin(radians)));
                        // 衝突しているか
                        if (tmp_ball_center_y >= top && tmp_ball_center_y < bottom) {
                            double tmp_ball_center_x = ball_center.getX() - distance_width;
                            HitProcessInterface hpi = new HitProcessInterface();
                            hpi.setData(this, hitable_msec, tmp_ball_center_x, tmp_ball_center_y, HitProcessInterface.HitSide.RIGHT);
                            return hpi;
                        }
                    }
                }
            }
        }
        // 進行方向 右上
        else if (angle >= 270 && angle < 360) {
            // *** ボール上点とブロック下面との衝突
            {
                // ボールとブロックとの距離 縦方向
                double distance_height =  ball_top - bottom;
                // 縦方向の距離が正の値だったら
                if (distance_height > 0) {
                    // ボール下点とブロックの上面が同じ縦方向位置になるまでの時間
                    double radians = Math.toRadians(angle);
                    long hitable_msec = Double.valueOf(Math.ceil(distance_height / (ball.getSpeed() * Math.abs(Math.sin(radians))))).longValue();
                    // ここまでの経過時間内に衝突の可能性あるか
                    if (hitable_msec <= update_display_if.getElapsedTime()) {
                        // ボール中心がhitable_msecの間に移動した後の横方向の位置
                        double tmp_ball_center_x = ball_center.x + ((ball.getSpeed() * hitable_msec) * Math.abs(Math.cos(radians)));
                        // 衝突しているか
                        if (tmp_ball_center_x >= left && tmp_ball_center_x < right) {
                            double tmp_ball_center_y = ball_center.getY() - distance_height;
                            HitProcessInterface hpi = new HitProcessInterface();
                            hpi.setData(this, hitable_msec, tmp_ball_center_x, tmp_ball_center_y, HitProcessInterface.HitSide.BOTTOM);
                            return hpi;
                        }
                    }
                }
            }
            // *** ブロック左面との衝突
            {
                // ボールとブロックとの距離 横方向
                double distance_width = left - ball_right;
                // 横方向の距離が正の値だったら
                if (distance_width > 0) {
                    // ボール左点とブロックの右面が同じ横方向位置になるまでの時間
                    double radians = Math.toRadians(angle);
                    long hitable_msec = Double.valueOf(Math.ceil(distance_width / (ball.getSpeed() * Math.abs(Math.cos(radians))))).longValue();
                    // ここまでの経過時間内に衝突の可能性あるか
                    if (hitable_msec <= update_display_if.getElapsedTime()) {
                        // ボール中心がhitable_msecの間に移動した後の縦方向の位置
                        double tmp_ball_center_y = ball_center.y - ((ball.getSpeed() * hitable_msec) * Math.abs(Math.sin(radians)));
                        // 衝突しているか
                        if (tmp_ball_center_y >= top && tmp_ball_center_y < bottom) {
                            double tmp_ball_center_x = ball_center.getX() + distance_width;
                            HitProcessInterface hpi = new HitProcessInterface();
                            hpi.setData(this, hitable_msec, tmp_ball_center_x, tmp_ball_center_y, HitProcessInterface.HitSide.LEFT);
                            return hpi;
                        }
                    }
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
    public HitProcessInterface checkHit(Ball ball, UpdateDisplayIf update_display_if) {
        Log.d("Block", "in checkHit()");
        // リターンデータ
        HitProcessInterface hpi = null;
        // このブロックがすでに画面から消えていたら
        if (!isDisplay) {
            // nullでリターン
            return hpi;
        }

        BallCenter ball_center = ball.getCenter();
        double ball_left = ball.getLeft();
        double ball_right = ball.getRight();
        double ball_top = ball.getTop();
        double ball_bottom = ball.getBottom();
        double angle = ball.getAngle();

        double bottom = top + height;
        double right = left + width;

        // 衝突チェック
        hpi = calcNecessaryTimeToHit(ball, update_display_if);
        // 衝突なし
        if (hpi == null) {
            // null でリターン
            return hpi;
        }

        //++ バウンド後のボールデータ(反射角)を計算
        long hitable_msec = hpi.getHitableMsec();
        BallCenter new_ball_center = new BallCenter();
        new_ball_center.setCoordinate(hpi.getNewBallCenterX(), hpi.getNewBallCenterY());
        HitProcessInterface.HitSide hit_side = hpi.getHitSide();

        // *** 進行角度で分岐
        // 進行方向 右下
        if (angle >= 0 && angle < 90) {
            // *** ボール下点とブロック上面との衝突
            if (hit_side == HitProcessInterface.HitSide.TOP) {
                // バウンド角（反射角）の計算
                double incidence_angle = angle;
                angle = 360 - Math.abs(((angle - (incidence_angle * 2)) % 360));

                // update_display_if にボールデータ（反射角）をセット
                hpi.setNewAngle(angle);
            }
            // *** ブロック左面との衝突
            else if (hit_side == HitProcessInterface.HitSide.LEFT) {
                // バウンド角（反射角）の計算
                double incidence_angle = 90 - angle;
                angle = (angle + (incidence_angle * 2)) % 360;

                // update_display_if にボールデータ（反射角）をセット
                hpi.setNewAngle(angle);
            }
        }
        // 進行方向 左下
        else if (angle >= 90 && angle < 180) {
            // *** ボール下点とブロック上面との衝突
            if (hit_side == HitProcessInterface.HitSide.TOP) {
                // バウンド角（反射角）の計算
                double incidence_angle = 180 - angle;
                angle = (angle + (incidence_angle * 2)) % 360;

                // update_display_if にボールデータ（反射角）をセット
                hpi.setNewAngle(angle);
            }
            // *** ブロック右面との衝突
            else if (hit_side == HitProcessInterface.HitSide.RIGHT) {
                // バウンド角（反射角）の計算
                double incidence_angle = angle - 90;
                angle = (angle - (incidence_angle * 2)) % 360;

                // update_display_if にボールデータ（反射角）をセット
                hpi.setNewAngle(angle);
            }
        }
        // 進行方向 左上
        else if (angle >= 180 && angle < 270) {
            // *** ボール上点とブロック下面との衝突
            if (hit_side == HitProcessInterface.HitSide.BOTTOM) {
                // バウンド角（反射角）の計算
                double incidence_angle = angle - 180;
                angle = (angle - (incidence_angle * 2)) % 360;

                // update_display_if にボールデータ（反射角）をセット
                hpi.setNewAngle(angle);
            }
            // *** ブロック右面との衝突
            else if (hit_side == HitProcessInterface.HitSide.RIGHT) {
                // バウンド角（反射角）の計算
                double incidence_angle = 270 - angle;
                angle = (angle + (incidence_angle * 2)) % 360;

                // update_display_if にボールデータ（反射角）をセット
                hpi.setNewAngle(angle);
            }
        }
        // 進行方向 右上
        else if (angle >= 270 && angle < 360) {
            // *** ボール上点とブロック下面との衝突
            if (hit_side == HitProcessInterface.HitSide.BOTTOM) {
                // バウンド角（反射角）の計算
                double incidence_angle = 360 - angle;
                angle = (angle + (incidence_angle * 2)) % 360;

                // update_display_if にボールデータ（反射角）をセット
                hpi.setNewAngle(angle);
            }
            // *** ブロック左面との衝突
            else if (hit_side == HitProcessInterface.HitSide.LEFT) {
                // バウンド角（反射角）の計算
                double incidence_angle = angle - 270;
                angle = (angle - (incidence_angle * 2)) % 360;

                // update_display_if にボールデータ（反射角）をセット
                hpi.setNewAngle(angle);
            }
        }
        return hpi;
    }
}
