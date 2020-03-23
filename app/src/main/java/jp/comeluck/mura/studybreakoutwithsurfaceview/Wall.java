package jp.comeluck.mura.studybreakoutwithsurfaceview;

public abstract class Wall {
    int Left;
    int Top;
    int Height;
    int Width;

    /**
     * コンストラクター
     * @param left
     * @param top
     * @param height
     * @param width
     */
    public Wall(int left, int top, int height, int width) {
        Left = left;
        Top = top;
        Height = height;
        Width = width;
    }

    /**
     * ボールがぶつかったか判定
     * @param ball
     * @return
     */
    abstract public boolean CheckHit(Ball ball);
}
