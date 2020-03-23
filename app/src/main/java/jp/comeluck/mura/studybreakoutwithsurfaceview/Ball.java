package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.List;

/**
 * ボール クラス
 */
public class Ball {
    // 親であるView
    protected MySurfaceView ParentView;

    // ボールデータ
    protected float Radius = 0;
    protected int FillColor = Color.WHITE;
    public class Center {
        protected int X;    // 座標 X
        protected int Y;    // 座標 Y

        /**
         * 座標 セット
         * @param x
         * @param y
         */
        public void SetCoordinate(int x, int y) {
            X = x;
            Y = y;
        }

        /**
         * 葉表 X ゲット
         * @return
         */
        public int GetX() {
            return X;
        }

        /**
         * 座標 Y ゲット
         * @return
         */
        public int GetY() {
            return Y;
        }
    }
    protected Center Center = new Center(); // 中心座標

    // 移動に関するデータ
    private double BallSpeed = 0.5d; // px/ms
    private int BallLeft = 1;
    private int BallTop = 1;
    private double Angle = 60;

    // 経過時間の管理
    private long mTime = 0;     //一つ前の描画時刻

    /**
     * コンストラクター
     */
    public Ball(MySurfaceView parent_view) {
        ParentView = parent_view;
    }

    /**
     * 初期化
     */
    protected void Init() {
    }

    /**
     * 初期化
     */
    public void Init(int position_left, int position_top, double angle, double speed, float radius, int color) {
        Init();
        BallLeft = position_left;
        BallTop = position_top;
        Angle = angle;
        BallSpeed = speed;

        Radius = radius;
        FillColor = color;
    }

    /**
     * 直径を取得
     * @return
     */
    public float GetRadius() {
        return Radius;
    }

    /**
     * 直径をセット
     * @param radius
     */
    public void SetRadius(float radius) {
        Radius = radius;
    }

    /**
     * 中心を取得
     * @return
     */
    public Center GetCenter() { return Center; }

    /**
     * 中心をセット
     * @param x
     * @param y
     */
    public void SetCenter(int x, int y) {
        Center.SetCoordinate(x, y);
    }

    /**
     * 中心をセット
     * @param left
     * @param top
     * @param radius
     */
    public void SetCenter(int left, int top, float radius) {
        int x = left + (int)radius;
        int y = top + (int)radius;
        Center.SetCoordinate(x, y);
    }

    /**
     * ボールの左位置をゲット
     * @return
     */
    public int GetBallLeft() {
        return BallLeft;
    }

    /**
     * ボールの左位置をセット
     * @param ball_left
     */
    public void SetBallLeft(int ball_left) {
        BallLeft = ball_left;
    }

    /**
     * ボールの左位置をゲット
     * @return
     */
    public int GetBallRight() {
        return (int)(BallLeft + (Radius * 2));
    }

    /**
     * ボールの上位置をゲット
     */
    public int GetBallTop() {
        return BallTop;
    }

    /**
     * ボールの上位置をセット
     * @param ball_top
     */
    public void SetBallTop(int ball_top) {
        BallTop = ball_top;
    }

    /**
     * ボールの下位置をゲット
     */
    public int GetBallBottom() {
        return (int)(BallTop + (Radius * 2));
    }

    /**
     * ボールの進行角度をゲット
     * @return
     */
    public double GetAngle() {
        return Angle;
    }

    /**
     * ボールの進行角度をセット
     * @param angle
     */
    public void SetAngle(double angle) {
        Angle = angle;
    }

    /**
     *  表示更新
     */
    public void UpdateDisplay(Canvas offscreen, List<Wall> walls) {
        SurfaceHolder holder = ParentView.GetSurfaceHolder();
        if (holder != null) {
            long past_time = mTime;
            mTime = System.currentTimeMillis();
//            String msg = String.format("経過時間 [%d] ms", (mTime - past_time));
//            Log.d("debug", msg);
            if (past_time > 0) {
                long elapsed_time = mTime - past_time;
                double distance = BallSpeed * elapsed_time;
                double radians = Math.toRadians(Angle);
                double x_distance = distance * Math.cos(radians);
                double y_distance = distance * Math.sin(radians);
                BallLeft = BallLeft + (int)x_distance;
                BallTop = BallTop + (int)y_distance;

                // バウンドの判定
                boolean check_result = false;
                for (Wall wall : walls ) {
                    if (wall.CheckHit(this)) {
                        break;
                    }
                }
                Log.d("Ball", String.format("BallLeft [%d] BallTop [%d]", BallLeft, BallTop));
            }

            // 中心をセット
            SetCenter(BallLeft, BallTop, Radius);

            //描画処理(Lock中なのでなるべく早く)
            Paint paint = new Paint();
            paint.setColor(FillColor);
            offscreen.drawCircle(Center.GetX(), Center.GetY(), Radius, paint);
        }
    }
}
