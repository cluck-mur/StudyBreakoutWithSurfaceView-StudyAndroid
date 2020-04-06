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
    protected BallCenter center = new BallCenter(); // 中心座標

    // 移動に関するデータ
    private double speed = 0.5d; // px/ms
    private int left = 1;
    private int top = 1;
    private double angle = 60;

    // 経過時間の管理
    private long updatedTime = 0;     //一つ前の描画時刻

    // ループ回数（デバッグ）
    private int loopCount = 0;

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
        // デバッグ
        Log.d("Ball", String.format("loopCount [%d]", loopCount));
        loopCount++;

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
//                    // 衝突後の経過時間に設定しなおし
//                    elapsed_time = update_display_if.getElapsedTime() - update_display_if.getElapsedTime2();
//                    // 衝突までの時間を再度初期化
//                    elapsed_time2 = 0;
//                    // インターフェースに設定しなおし
//                    update_display_if.setElapsedTime(elapsed_time);    // 経過時間
//                    update_display_if.setElapsedTime2(elapsed_time2);
                    // 衝突フラグを設定しなおし
                    hit_flg = false;
                    HitProcessInterface hpi = null;
                    // ブロックとの衝突判定 ブロックの数だけループ
                    for (Block block : blocks) {
//                        HitProcessInterface tmp_necessary_time_to_hit = block.calcNecessaryTimeToHit(this, update_display_if);
                        HitProcessInterface tmp_hpi = block.checkHit(this, update_display_if);
                        if (tmp_hpi != null && tmp_hpi.getHitableMsec() < elapsed_time) {
                            // 衝突までの時間が前に判定したブロックより小さいなら
                            if (hpi == null || (hpi.getHitableMsec() > 0 && tmp_hpi.getHitableMsec() < hpi.getHitableMsec())) {
                                // 候補として保持
                                hpi = tmp_hpi;
                            }
                        }
                    }

                    // 壁との衝突判定
                    for (Wall wall : walls) {
                        HitProcessInterface tmp_hpi = wall.checkHit(this, update_display_if);
                        if (tmp_hpi != null && tmp_hpi.getHitableMsec() < elapsed_time) {
                            // 衝突までの時間が前に判定したブロックより小さいなら
                            if (hpi == null || (hpi.getHitableMsec() > 0 && tmp_hpi.getHitableMsec() < hpi.getHitableMsec())) {
                                // 候補として保持
                                hpi = tmp_hpi;
                            }
                        }
                    }

                    // ブロックか壁に衝突したか？
                    if (hpi != null) {
                        // ボールがブロックや壁に衝突した時の処理
                        Block block = hpi.getBlock();
                        Wall wall = hpi.getWall();
                        if (block != null) {
                            block.setIsDisplay(false);
                        }
                        // 経過時間データを更新
                        update_display_if.setElapsedTime2(update_display_if.getElapsedTime2() + hpi.getHitableMsec());
                        // ボールにデータをセット
                        setLeft(hpi.getNewBallCenterX() - (int)radius);
                        setTop(hpi.getNewBallCenterY() - (int)radius);
                        setAngle(hpi.getNewAngle());
                        hit_flg = true;
                    }
                } while (hit_flg);

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
