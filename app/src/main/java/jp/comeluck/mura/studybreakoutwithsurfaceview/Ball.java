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
    public class Center {
        protected int x;    // 座標 X
        protected int y;    // 座標 Y

        /**
         * 座標 セット
         * @param x
         * @param y
         */
        public void setCoordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * 葉表 X ゲット
         * @return
         */
        public int getX() {
            return x;
        }

        /**
         * 座標 Y ゲット
         * @return
         */
        public int getY() {
            return y;
        }
    }
    protected Center center = new Center(); // 中心座標

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
    public Center getCenter() { return this.center; }

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
     * @param ball_left
     */
    public void setLeft(int left) {
        this.left = left;
    }

    /**
     * ボールの左位置をゲット
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
     *  表示更新
     */
    public void updateDisplay(Canvas offscreen, List<Wall> walls) {
        SurfaceHolder holder = parentView.getSurfaceHolder();
        if (holder != null) {
            long past_time = updatedTime;
            updatedTime = System.currentTimeMillis();
//            String msg = String.format("経過時間 [%d] ms", (mTime - past_time));
//            Log.d("debug", msg);
            if (past_time > 0) {
                long elapsed_time = updatedTime - past_time;
                double distance = speed * elapsed_time;
                double radians = Math.toRadians(angle);
                double x_distance = distance * Math.cos(radians);
                double y_distance = distance * Math.sin(radians);
                left = left + (int)x_distance;
                top = top + (int)y_distance;

                // バウンドの判定
                boolean check_result = false;
                for (Wall wall : walls ) {
                    if (wall.checkHit(this)) {
                        break;
                    }
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
