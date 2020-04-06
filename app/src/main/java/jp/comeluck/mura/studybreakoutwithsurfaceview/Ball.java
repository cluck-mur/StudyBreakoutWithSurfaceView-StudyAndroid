package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.List;

/**
 * ボール クラス
 */
public class Ball {
    // 親であるView
    protected MySurfaceView parentView;

    // ボールデータ
    protected float radius = 0;
    protected int fillColor = Color.WHITE;

//    /**
//     * 中心座標を保持するクラス
//     */
//    public class Center {
//        protected int x;
//        protected int y;
//
//        public Center(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//
//        /**
//         * 葉表 X ゲット
//         * @return
//         */
//        public int getX() {
//            return x;
//        }
//
//        /**
//         * 座標 Y ゲット
//         * @return
//         */
//        public int getY() {
//            return y;
//        }
//    }
    protected BallCenter center = new BallCenter(); // 中心座標

    // 移動に関するデータ
    private double speed = 0.5d; // px/ms
    private int left = 1;
    private int top = 1;
    private double angle = 60;

    // 経過時間の管理
    private long updatedTime = 0;     //一つ前の描画時刻

    /**
     * コンストラクター
     */
    public Ball(MySurfaceView parent_view) {
        parentView = parent_view;
    }

    /**
     * 初期化
     */
    protected void init() {
    }

    /**
     * 初期化
     */
    public void init(int position_left, int position_top, double angle, double speed, float radius, int color) {
        init();
        this.left = position_left;
        this.top = position_top;
        this.angle = angle;
        this.speed = speed;

        this.radius = radius;
        this.fillColor = color;
    }

    /**
     * 直径を取得
     * @return
     */
    public float getRadius() {
        return this.radius;
    }

    /**
     * 直径をセット
     * @param radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * 中心を取得
     * @return
     */
    public BallCenter getCenter() { return this.center; }

    /**
     * 中心をセット
     * @param x
     * @param y
     */
    public void setCenter(int x, int y) {
        this.center.setCoordinate(x, y);
    }

    /**
     * 中心をセット
     * @param left
     * @param top
     * @param radius
     */
    public void setCenter(int left, int top, float radius) {
        int x = left + (int)radius;
        int y = top + (int)radius;
        this.center.setCoordinate(x, y);
    }

    /**
     * ボールの左位置をゲット
     * @return
     */
    public int getLeft() {
        return this.left;
    }

    /**
     * ボールの左位置をセット
     * @param left
     */
    public void setLeft(int left) {
        this.left = left;
    }

    /**
     * ボールの右位置をゲット
     * @return
     */
    public int getRight() {
        return (int)(this.left + (this.radius * 2));
    }

    /**
     * ボールの上位置をゲット
     */
    public int getTop() {
        return top;
    }

    /**
     * ボールの上位置をセット
     * @param ball_top
     */
    public void setTop(int ball_top) {
        top = ball_top;
    }

    /**
     * ボールの下位置をゲット
     */
    public int getBottom() {
        return (int)(top + (radius * 2));
    }

    /**
     * ボールの進行角度をゲット
     * @return
     */
    public double getAngle() {
        return angle;
    }

    /**
     * ボールの進行角度をセット
     * @param angle
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * スピード をゲット
     * @return
     */
    public double getSpeed() {
        return speed;
    }

    /**
     *  表示更新
     */
    public void updateDisplay(Canvas offscreen, List<Wall> walls, List<Block> blocks, long now_time) {
        // MyFurfaceView から画面を操作するために SurfaceHolder を取得
        SurfaceHolder holder = parentView.getSurfaceHolder();
        // SurfaceHolder を無事ゲットできたら
        if (holder != null) {
            //++ 以下、前の画面更新から今までの時間で衝突した全部のブロックと壁に対応する処理
            // 前の画面更新時間
            long past_time = updatedTime;
            // 今の時間
            updatedTime = now_time;
            // 経過時間
            long elapsed_time = updatedTime - past_time;
            long elapsed_time2 = 0;
            Log.d("debug", String.format("経過時間 [%d] ms", elapsed_time));
            // 前の画面更新時間が 0より大きかったら 有効
            if (past_time > 0) {
                // 計算で経過時間を管理するためのインタフェースオブジェクトを生成
                UpdateDisplayIf update_display_if = new UpdateDisplayIf();
                // 経過時間をセット
                update_display_if.setElapsedTime(elapsed_time);
                // 衝突までの時間を初期化
                update_display_if.setElapsedTime2(elapsed_time2);
                // 衝突フラグを初期化
                boolean hit_flg = false;

                // 経過時間内に衝突したブロックや壁がなくなるまでループ
                do {
                    //++ ブロックや壁との衝突判定
                    // 衝突後の経過時間に設定しなおし
                    elapsed_time = update_display_if.getElapsedTime() - update_display_if.getElapsedTime2();
                    // 衝突までの時間を再度初期化
                    elapsed_time2 = 0;
                    // インターフェースに設定しなおし
                    update_display_if.setElapsedTime(elapsed_time);    // 経過時間
                    update_display_if.setElapsedTime2(elapsed_time2);
                    // 衝突フラグを設定しなおし
                    hit_flg = false;
                    HitProcessInterface necessary_time_to_hit = null;
                    // ブロックとの衝突判定 ブロックの数だけループ
                    for (Block block : blocks) {
//                        HitProcessInterface tmp_necessary_time_to_hit = block.calcNecessaryTimeToHit(this, update_display_if);
                        HitProcessInterface tmp_necessary_time_to_hit = block.checkHit(this, update_display_if);
                        if (tmp_necessary_time_to_hit != null && tmp_necessary_time_to_hit.getHitableMsec() < elapsed_time) {
                            if () {

                            }

                        hit_flg = block.checkHit(this, update_display_if, tmp_necessary_time_to_hit);
                        if (hit_flg) {
                            break;
                        }
                    }

                    if (!hit_flg) {
                        // 最初にボールが衝突する壁を見つける
                        Wall hit_wall = null;
//                        long necessary_time_to_hit = 0;
                        int wall_count = 0;
                        for (Wall wall : walls) {
//                            long tmp_necessary_time_to_hit = wall.calcNecessaryTimeToHit(this);
                            tmp_necessary_time_to_hit = wall.calcNecessaryTimeToHit(this, update_display_if);
                            if (tmp_necessary_time_to_hit != -1 && tmp_necessary_time_to_hit < elapsed_time) {
                                if (necessary_time_to_hit == 0 || tmp_necessary_time_to_hit < necessary_time_to_hit) {
                                    hit_wall = wall;
                                    necessary_time_to_hit = tmp_necessary_time_to_hit;
                                    if (necessary_time_to_hit < 0) {
                                        Log.d("Ball", "necessary_time_to_hit がマイナス");
                                    }
                                }
                            }
                            wall_count++;
                        }

                        // 壁に衝突したか？
                        if (hit_wall != null) {
                            // ボールが壁に衝突した時の処理
                            hit_flg = hit_wall.checkHit(this, update_display_if);
                        }
                    }
                } while (hit_flg);

//                    do {
//                        // ブロックとの衝突判定
//                        long elapsed_time = update_display_if.getElapsedTime() - update_display_if.getElapsedTime2();
//                        long elapsed_time2 = 0;
//                        update_display_if.setElapsedTime(elapsed_time);    // 経過時間
//                        update_display_if.setElapsedTime2(elapsed_time2);
//                        hit_flg = false;
//                        // ブロックとの衝突判定
//                        for (Block block : blocks) {
//                            hit_flg = block.checkHit(this, update_display_if);
//                            if (hit_flg) {
//                                break;
//                            }
//                        }
//                    } while (hit_flg);
//
//
//                // 壁との衝突の判定
//                int i = 0;
//                do {
//                    long elapsed_time = update_display_if.getElapsedTime() - update_display_if.getElapsedTime2();
//                    long elapsed_time2 = 0;
//                    update_display_if.setElapsedTime(elapsed_time);    // 経過時間
//                    update_display_if.setElapsedTime2(elapsed_time2);
//                    hit_flg = false;
//
//                    // 最初にボールが衝突する壁を見つける
//                    Wall hit_wall = null;
//                    long necessary_time_to_hit = 0;
//                    int wall_count = 0;
//                    for (Wall wall : walls) {
//                        long tmp_necessary_time_to_hit = wall.calcNecessaryTimeToHit(this);
//                        if (tmp_necessary_time_to_hit != -1 && tmp_necessary_time_to_hit < elapsed_time) {
//                            if (necessary_time_to_hit == 0 || tmp_necessary_time_to_hit < necessary_time_to_hit) {
//                                hit_wall = wall;
//                                necessary_time_to_hit = tmp_necessary_time_to_hit;
//                                if (necessary_time_to_hit < 0) {
//                                    Log.d("Ball", "necessary_time_to_hit がマイナス");
//                                }
//                            }
//                        }
//                        wall_count++;
//                    }
//
//                    // 壁に衝突したか？
//                    if (hit_wall != null) {
//                        // ボールが壁に衝突した時の処理
//                        hit_flg = hit_wall.checkHit(this, update_display_if);
//                    }
//                    i++;
//                } while (hit_flg);

                // 最終的なボールの位置
                Log.d("Ball", String.format("最終的なボールの位置を計算する"));
                long final_elapsed_time = update_display_if.getElapsedTime() - update_display_if.getElapsedTime2();
                if (!hit_flg || final_elapsed_time > 0) {
                    double distance = speed * final_elapsed_time;
                    double radians = Math.toRadians(angle);
                    double x_distance = distance * Math.cos(radians);
                    double y_distance = distance * Math.sin(radians);
                    left = left + (int)x_distance;
                    top = top + (int)y_distance;
                }
                Log.d("Ball", String.format("BallLeft [%d] BallTop [%d]", left, top));
            }

            // 中心をセット
            setCenter(left, top, radius);

            //描画処理(Lock中なのでなるべく早く)
            Paint paint = new Paint();
            paint.setColor(fillColor);
            offscreen.drawCircle(center.getX(), center.getY(), radius, paint);
        }
    }
}
