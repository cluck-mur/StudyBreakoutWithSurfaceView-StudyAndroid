package jp.comeluck.mura.studybreakoutwithsurfaceview;

public abstract class Wall {
    protected int left;
    protected int top;
    protected int height;
    protected int width;

    /**
     * コンストラクター
     * @param left
     * @param top
     * @param height
     * @param width
     */
    public Wall(int left, int top, int height, int width) {
        this.left = left;
        this.top = top;
        this.height = height;
        this.width = width;
    }

    /**
     * ボールがぶつかったか判定
     * @param ball
     * @return
     */
    abstract public boolean checkHit(Ball ball);
}
