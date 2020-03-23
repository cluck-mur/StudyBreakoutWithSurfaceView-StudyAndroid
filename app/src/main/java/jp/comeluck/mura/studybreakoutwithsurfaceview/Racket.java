package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Racket {
    // ラケットのデータ
    int Height = 0;
    int Width = 0;
    int FillColor = Color.WHITE;

    // 移動に関するデータ
    int Left = 0;
    int Top = 0;

    /**
     * 初期化
     * @param height
     * @param width
     * @param color
     */
    public void Init(int height, int width, int color) {
        Height = height;
        Width = width;
        FillColor = color;
    }

    /**
     * 画面に描画する
     * @param left
     * @param top
     * @param canvas
     */
    public void UpdateDisplay(int center_x, int view_bottom, Canvas canvas) {
        float left = center_x - (Width / 2);
        float right = center_x + (Width / 2);
        float top = view_bottom - Height;
        float bottom = view_bottom;

        Paint paint = new Paint();
        paint.setColor(FillColor);
        //paint.setFilterBitmap(true);
        canvas.drawRect(left, top, right, bottom, paint);
    }
}
