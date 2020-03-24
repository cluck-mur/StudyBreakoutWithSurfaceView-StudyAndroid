package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * ブロック クラス
 */
public class Block {
    protected int left;
    protected int top;
    protected int height;
    protected int width;
    protected int fillColor = Color.CYAN;

    /**
     * コンストラクター
     * @param left
     * @param top
     * @param height
     * @param width
     */
    public Block(int left, int top, int height, int width, int fill_color) {
        this.left = left;
        this.top = top;
        this.height = height;
        this.width = width;
        this.fillColor = fill_color;
    }

    /**
     * 表示更新
     * @param canvas
     */
    public void updateDisplay(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(fillColor);
        //paint.setFilterBitmap(true);
        float paint_left = left;
        float paint_top = top;
        float paint_right = paint_left + width;
        float paint_bottom = paint_top + height;
//        Log.d("Block", String.format("Left [%f] Top %f] Right [%f] Bottom [%f]",
//                paint_left, paint_top, paint_right, paint_bottom));
        canvas.drawRect(paint_left, paint_top, paint_right, paint_bottom, paint);
    }
}
