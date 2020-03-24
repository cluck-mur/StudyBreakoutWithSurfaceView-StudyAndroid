package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Racket {
    // ラケットのデータ
    protected int height = 0;
    protected int width = 0;
    protected int fillColor = Color.WHITE;

    // 移動に関するデータ
    protected int left = 0;
    protected int top = 0;

    /**
     * 初期化
     * @param height
     * @param width
     * @param color
     */
    public void init(int height, int width, int color) {
        this.height = height;
        this.width = width;
        this.fillColor = color;
    }

    /**
     * 画面に描画する
     * @param left
     * @param top
     * @param canvas
     */
    public void updateDisplay(int center_x, int view_bottom, Canvas canvas) {
        float left = center_x - (width / 2);
        float right = center_x + (width / 2);
        float top = view_bottom - height;
        float bottom = view_bottom;

        Paint paint = new Paint();
        paint.setColor(fillColor);
        //paint.setFilterBitmap(true);
        canvas.drawRect(left, top, right, bottom, paint);
    }
}
