package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    // サーフェスホルダー
    protected SurfaceHolder mHolder;
    // 描画バッファ用ビットマップ
    protected Bitmap BaseBitmap;

    // 画面を構成する部品
//    private Ball Ball = new Ball(this);   // ボール
    private List<Ball> Balls = new ArrayList<Ball>();   // ボール
    private List<Racket> Rackets = new ArrayList<Racket>(); // ラケット
    private List<Wall> Walls = new ArrayList<Wall>();   // 壁

    // スレッド用フィールド
    private Thread mLooper;
    private int MilliSec = 10;  // 待機時間
    private long mTime = 0;     //一つ前の描画時刻

    /**
     * コンストラクター
     * @param context
     */
    public MySurfaceView(Context context) {
        super(context);
        // 初期化
        Init();
    }

    /**
     * コンストラクター
     * @param context
     * @param attrs
     */
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初期化
        Init();
    }

    /**
     * コンストラクター
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初期化
        Init();
    }

    /**
     * コンストラクター
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        // 初期化
        Init();
    }

    /**
     * 初期化処理
     */
    private void Init() {
        getHolder().addCallback(this);
    }

    /**
     * mHolder（サーフェスホルダー）のゲッター
     * @return
     */
    public SurfaceHolder GetSurfaceHolder() {
        return mHolder;
    }

    /**
     * コールバック関数 クリエイト時
     * @param holder
     */
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("MySurfaceView", "surfaceCreatedに来た");
        synchronized (this) {
            // サーフェスホルダーを保存
            mHolder = holder;
            // 画面ベースビットアップを生成する
            BaseBitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                    Bitmap.Config.ARGB_8888);

            // 壁
            WallLeft wall_left = new WallLeft(0, 0, getHeight(), 0);
            Walls.add(wall_left);
            WallRight wall_right = new WallRight(getWidth(), 0, getHeight(), 0);
            Walls.add(wall_right);
            WallTop wall_top = new WallTop(0, 0, 0, getWidth());
            Walls.add(wall_top);
            WallBottom wall_bottom = new WallBottom(0, getHeight(), 0, getWidth());
            Walls.add(wall_bottom);

            // ボールの直径を決める
            float radius = getHeight() * 0.015f;
            // Ball1を作成
            Ball ball1 = new Ball(this);
            // Ball1を初期化
            ball1.Init(1, 1, 60, 1.5, radius, Color.GREEN);
            // リストに保存
            Balls.add(ball1);
            // Ball2を作成
            Ball ball2 = new Ball(this);
            // Ball2を初期化
            ball2.Init(512, 1, 150, 0.3, radius, Color.MAGENTA);
            // リストに保存
            Balls.add(ball2);

            // ラケット
            Racket racket1 = new Racket();
            racket1.Init( (int)(getHeight() * 0.005), (int)ball1.GetRadius() * 8, Color.GREEN);
            Rackets.add(racket1);
            Racket racket2 = new Racket();
            racket2.Init((int)(getHeight() * 0.005), (int)ball2.GetRadius() * 8, Color.MAGENTA);
            Rackets.add(racket2);

            //スレッドの生成
            mHolder = holder;
            mLooper = new Thread(this);
        }
    }

    /**
     * コールバック関数 変化時
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("MySurfaceView", "surfaceChanged来た");
        synchronized (this) {
            //スレッド処理を開始
            if (mLooper != null) {
                //mTime = System.currentTimeMillis();
                mLooper.start();
            }
        }
    }

    /**
     * コールバック関数 消去時
     * @param holder
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("MySurfaceView", "surfaceDestroyed来た");
        synchronized (this) {
            mHolder = null;

            //スレッドを削除
            mLooper = null;

            // 画面ベースビットマップを解放する
            if (BaseBitmap != null)
                BaseBitmap.recycle();
        }
    }

    /**
     * スレッド本体
     */
    @Override
    public void run() {
        synchronized (this) {
            while (mLooper != null) {

                //Canvasの取得(マルチスレッド環境対応のためLock)
                Canvas canvas = mHolder.lockCanvas();
                // 描画処理
                if (canvas != null) {
                    // 画面バッファを生成する
                    Canvas offscreen = new Canvas(BaseBitmap);
                    // 前の表示を消すため塗りつぶす
                    offscreen.drawColor(Color.BLACK);
                    // 部品を画面バッファに描画
                    for (Ball ball : Balls) {
                        ball.UpdateDisplay(offscreen, Walls);
                    }
                    int i = 0;
                    for (Racket racket : Rackets) {
                        Ball ball = Balls.get(i);
                        racket.UpdateDisplay(ball.GetCenter().GetX(), getHeight(), offscreen);
                        i++;
                    }
                    // 画面バッファを画面に描画する
                    canvas.drawBitmap(BaseBitmap, 0, 0, null);

                }
                //LockしたCanvasを解放、ほかの描画処理スレッドがあればそちらで処理できるようになる。
                mHolder.unlockCanvasAndPost(canvas);

                // CPUを占有しないための待ち
                try {
                    this.wait(MilliSec);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
