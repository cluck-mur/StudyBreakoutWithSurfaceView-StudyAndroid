package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Racket {
    // ラケットのデータ
    protected double height = 0;
    protected double width = 0;
    protected int fillColor = Color.WHITE;

    // 移動に関するデータ
    protected double left = 0;
    protected double top = 0;

    /**
     * 初期化
     * @param height
     * @param width
     * @param color
     */
    public void init(double height, double width, int color) {
        this.height = height;
        this.width = width;
        this.fillColor = color;
    }

    /**
     * 画面に描画する
     * @param center_x
     * @param view_bottom
     * @param canvas
     */
    public void updateDisplay(double center_x, double view_bottom, Canvas canvas) {
        double left = center_x - (width / 2);
        double right = center_x + (width / 2);
        double top = view_bottom - height;
        double bottom = view_bottom;

        Paint paint = new Paint();
        paint.setColor(fillColor);
        //paint.setFilterBitmap(true);
        canvas.drawRect((int)left, (int)top, (int)right, (int)bottom, paint);
    }
}
