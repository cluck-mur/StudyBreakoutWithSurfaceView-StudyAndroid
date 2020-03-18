package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * ボール クラス
 */
public class Ball {
    // 親であるView
    protected MySurfaceView ParentView;
    // ビットマップデータ
    protected Bitmap MyBitmap;

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
    public void Init() {
        // 画像をresource drawableからBitmapとして呼び出し
        int image_id = ParentView.getResources().getIdentifier("img01",
                "drawable", this.getClass().getPackage().getName());
        Bitmap bmp = BitmapFactory.decodeResource(ParentView.getResources(), image_id);
        // Bitmapをスケーリング
        int bmp_height = bmp.getHeight();
        int bmp_width = bmp.getWidth();
        float disp_ratio = (float)1/25;
        int view_height = (int)(ParentView.getHeight() * disp_ratio);
        int view_width = (int)(ParentView.getWidth() * disp_ratio);
        Log.d("Ball", String.format("bmp_height [%d] bmp_width [%d] view_height [%d] view_width [%d]", bmp_height, bmp_width, view_height, view_width));
        float scale = 1;
        if (view_height < view_width) {
            scale = (float) view_height / (float) bmp_height;
        } else {
            scale = (float) view_width / (float) bmp_width;
        }
        Log.d("UpdateImage", String.format("scale [%f]", scale));
        Matrix matrix = new Matrix();
        matrix.preScale(scale, scale);
        Bitmap scaled_bmp = Bitmap.createBitmap(bmp, 0, 0, bmp_height, bmp_width, matrix, true);
        Log.d("UpdateImage", String.format("scaled_bmp is [%s]", scaled_bmp.toString()));
        if (scaled_bmp == null) {
            Log.d("UpdateImage", String.format("scaled_bmp is NULL. []"));
        }

        // メンバ変数に保存
        MyBitmap = scaled_bmp;
    }

    /**
     * 初期化
     */
    public void Init(int position_left, int position_top, double angle, double speed) {
        Init();
        BallLeft = position_left;
        BallTop = position_top;
        Angle = angle;
        BallSpeed = speed;
    }

    /**
     * Bitmapをセット
     * @param bitmap
     */
    public void SetBitmap(Bitmap bitmap) {
        MyBitmap = bitmap;
    }

    /**
     * ビットマップをゲット
     * @return
     */
    public Bitmap GetBitmap() {
        return MyBitmap;
    }

    /**
     *  表示更新
     */
    public void UpdateDisplay(Canvas offscreen) {
        // 次に表示する画像のビットマップを取得
        Bitmap scaled_bmp = GetBitmap();
        SurfaceHolder holder = ParentView.GetSurfaceHolder();
        if (holder != null) {
            long past_time = mTime;
            mTime = System.currentTimeMillis();
            String msg = String.format("経過時間 [%d] ms", (mTime - past_time));
            Log.d("debug", msg);
            if (past_time > 0) {
                long elapsed_time = mTime - past_time;
                double distance = BallSpeed * elapsed_time;
                double radians = Math.toRadians(Angle);
                double x_distance = distance * Math.cos(radians);
                double y_distance = distance * Math.sin(radians);
                BallLeft = BallLeft + (int)x_distance;
                BallTop = BallTop + (int)y_distance;
                // バウンドの判定
                // 右の壁
                if (BallLeft + MyBitmap.getWidth() > ParentView.getWidth()) {
                    Log.d("Ball", String.format("右の壁"));
                    if (Angle >= 0 && Angle < 90) {
                        double incidence_angle = 90 - Angle;
                        Angle = (Angle + (incidence_angle * 2)) % 360;
                    } else if (Angle >= 270 && Angle < 360) {
                        double incidence_angle = Angle - 270;
                        Angle = (Angle - (incidence_angle * 2)) % 360;
                    }
                    BallLeft = ParentView.getWidth() - MyBitmap.getWidth();
                    Log.d("Ball", String.format("Angle [%e]", Angle));
                }
                // 左の壁
                else if (BallLeft < 0) {
                    Log.d("Ball", String.format("左の壁"));
                    if (Angle >= 180 && Angle < 270) {
                        double incidence_angle = 270 - Angle;
                        Angle = (Angle + (incidence_angle * 2)) % 360;
                    } else if (Angle >= 90 && Angle < 180) {
                        double incidence_angle = Angle - 90;
                        Angle = (Angle - (incidence_angle * 2)) % 360;
                    }
                    BallLeft = 0;
                    Log.d("Ball", String.format("Angle [%e]", Angle));
                }
                // 下の壁
                else if (BallTop + MyBitmap.getHeight() > ParentView.getHeight()) {
                    Log.d("Ball", String.format("下の壁"));
                    if (Angle >= 90 && Angle < 180) {
                        double incidence_angle = 180 - Angle;
                        Angle = (Angle + (incidence_angle * 2)) % 360;
                    } else if (Angle >= 0 && Angle < 90) {
                        double incidence_angle = Angle;
                        Angle = 360 - Math.abs(((Angle - (incidence_angle * 2)) % 360));
                    }
                    BallTop = ParentView.getHeight() - MyBitmap.getHeight();
                    Log.d("Ball", String.format("Angle [%e]", Angle));
                }
                // 上の壁
                else if (BallTop < 0) {
                    Log.d("Ball", String.format("上の壁"));
                    if (Angle >= 270 && Angle < 360) {
                        double incidence_angle = 360 - Angle;
                        Angle = (Angle + (incidence_angle * 2)) % 360;
                    } else if (Angle >= 180 && Angle < 270) {
                        double incidence_angle = Angle - 180;
                        Angle = (Angle - (incidence_angle * 2)) % 360;
                    }
                    BallTop = 0;
                    Log.d("Ball", String.format("Angle [%e]", Angle));
                }
                Log.d("Ball", String.format("BallLeft [%d] BallTop [%d]", BallLeft, BallTop));
            }

            //描画処理(Lock中なのでなるべく早く)
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
//            offscreen.drawBitmap(scaled_bmp, BallLeft, BallTop, paint);
            offscreen.drawCircle();
        }
    }
}
