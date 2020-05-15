package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    // サーフェスホルダー
    protected SurfaceHolder surfaceHolder;
    // 描画バッファ用ビットマップ
    protected Bitmap secondScreenBuffer;

    // 画面を構成する部品
//    private Ball Ball = new Ball(this);   // ボール
    private List<Ball> balls = new ArrayList<Ball>();   // ボール
    private List<Racket> rackets = new ArrayList<Racket>(); // ラケット
    private List<Wall> walls = new ArrayList<Wall>();   // 壁
    private List<Block> blocks = new ArrayList<Block>();    // ブロック
    private final int horizontalBlockNumber = 6;
    private final int verticalBlockNumber = 5;
    private int blockTop;
    private List<Integer> blockColors;
    boolean wroteBlocksFlag = false;

    // スレッド用フィールド
    private Thread looper;
    private int waitTime = 10;  // 待機時間
    private long updatedTime = 0;     //一つ前の描画時刻

    /**
     * コンストラクター
     * @param context
     */
    public MySurfaceView(Context context) {
        super(context);
        // 初期化
        init();
    }

    /**
     * コンストラクター
     * @param context
     * @param attrs
     */
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初期化
        init();
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
        init();
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
        init();
    }

    /**
     * 初期化処理
     */
    private void init() {
        getHolder().addCallback(this);
        blockColors = new ArrayList<Integer>();
//        blockColors.add(Color.RED);
//        blockColors.add(Color.GREEN);
//        blockColors.add(Color.YELLOW);
//        blockColors.add(Color.CYAN);
//        blockColors.add(Color.WHITE);
        blockColors.add(Color.WHITE);
        blockColors.add(Color.WHITE);
        blockColors.add(Color.WHITE);
        blockColors.add(Color.WHITE);
        blockColors.add(Color.WHITE);
    }

    /**
     * mHolder（サーフェスホルダー）のゲッター
     * @return
     */
    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    /**
     * コールバック関数 クリエイト時
     * @param holder
     */
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("MySurfaceView", "surfaceCreatedに来た");
        synchronized (this) {
            // サーフェスホルダーを保存
            surfaceHolder = holder;
            // 画面ベースビットアップを生成する
            secondScreenBuffer = Bitmap.createBitmap(getWidth(), getHeight(),
                    Bitmap.Config.ARGB_8888);

            // 壁オブジェクトを生成
            WallLeft wall_left = new WallLeft(0, 0, getHeight(), 0);
            walls.add(wall_left);
            WallRight wall_right = new WallRight(getWidth(), 0, getHeight(), 0);
            walls.add(wall_right);
            WallTop wall_top = new WallTop(0, 0, 0, getWidth());
            walls.add(wall_top);
            WallBottom wall_bottom = new WallBottom(0, getHeight(), 0, getWidth());
            walls.add(wall_bottom);

            // ボールの半径を決める
            float radius = getHeight() * 0.01f;
            // Ball1 オブジェクトを生成
            Ball ball1 = new Ball(this);
            // Ball1を初期化
            ball1.init((int)(secondScreenBuffer.getWidth() * 0.25), (int) (secondScreenBuffer.getHeight() * 0.95), 60, 1.5, radius, Color.GREEN);
            // リストに保存
            balls.add(ball1);
            // ラケット1 オブジェクトを生成
            Racket racket1 = new Racket();
            racket1.init( (int)(getHeight() * 0.005), (int)(ball1.getRadius() * 12), Color.GREEN);
            rackets.add(racket1);

//            // Ball2 オブジェクトを生成
//            Ball ball2 = new Ball(this);
//            // Ball2を初期化
//            ball2.init((int)(secondScreenBuffer.getWidth() * 0.75), (int)(secondScreenBuffer.getHeight() * 0.75), 125, 0.3, radius, Color.MAGENTA);
//            // リストに保存
//            balls.add(ball2);
//            // ラケット2 オブジェクトを生成
//            Racket racket2 = new Racket();
//            racket2.init((int)(getHeight() * 0.005), (int)(ball2.getRadius() * 12), Color.MAGENTA);
//            rackets.add(racket2);

            // ブロックのコンフィグ値を設定
            blockTop = (int)(secondScreenBuffer.getHeight() * 0.15);
            int horizontal_units_number = horizontalBlockNumber;
            BlockConfig block_config = new BlockConfig(horizontal_units_number, secondScreenBuffer.getWidth(), secondScreenBuffer.getHeight());
            // ブロック オブジェクトを生成
            for (int i = 0; i < verticalBlockNumber; i++) {
                for (int j = 0; j < horizontal_units_number; j++) {
                    int width = block_config.getWidth();
                    int height = block_config.getHeight();
                    int space = (int)(secondScreenBuffer.getWidth() * 0.01);
                    Block block = new Block((width * j) + space, blockTop + (height * i) + space, height - space, width - space, blockColors.get(i));
                    blocks.add(block);
                }
            }
//            Log.d("MySurfaceView", String.format("ブロックの数 [%d]", blocks.size()));

            //スレッドの生成
            surfaceHolder = holder;
            looper = new Thread(this);
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
            if (looper != null) {
                //mTime = System.currentTimeMillis();
                looper.start();
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
            surfaceHolder = null;

            //スレッドを削除
            looper = null;

            // 画面ベースビットマップを解放する
            if (secondScreenBuffer != null)
                secondScreenBuffer.recycle();
        }
    }

    /**
     * スレッド本体
     */
    @Override
    public void run() {
        synchronized (this) {
            while (looper != null) {

                //Canvasの取得(マルチスレッド環境対応のためLock)
                Canvas canvas = surfaceHolder.lockCanvas();
                // 描画処理
                if (canvas != null) {
                    // 画面バッファを生成する
                    Canvas offscreen = new Canvas(secondScreenBuffer);
                    // 前の表示を消すため塗りつぶす
                    offscreen.drawColor(Color.BLACK);
                    // 部品を画面バッファに描画
                    for (Ball ball : balls) {
                        ball.updateDisplay(offscreen, walls, blocks, System.currentTimeMillis());
                    }
                    for (Block block : blocks) {
                        block.updateDisplay(offscreen);
                    }
                    int i = 0;
                    for (Racket racket : rackets) {
                        Ball ball = balls.get(i);
                        racket.updateDisplay(ball.getCenter().getX(), getHeight(), offscreen);
                        i++;
                    }
                    // 画面バッファを画面に描画する
                    canvas.drawBitmap(secondScreenBuffer, 0, 0, null);

                }
                //LockしたCanvasを解放、ほかの描画処理スレッドがあればそちらで処理できるようになる。
                surfaceHolder.unlockCanvasAndPost(canvas);

                // CPUを占有しないための待ち
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
