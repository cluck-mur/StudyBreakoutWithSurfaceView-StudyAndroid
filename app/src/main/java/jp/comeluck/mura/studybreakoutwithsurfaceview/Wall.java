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
     * ボールが衝突するまでに必要な時間を計算する
     * @param ball
     * @return  衝突までの時間、 衝突しない場合は -1 を返す
     */
    abstract public long calcNecessaryTimeToHit(Ball ball);

    /**
     * ボールがぶつかったか判定
     * @param ball
     * @return
     */
    abstract public boolean checkHit(Ball ball, UpdateDisplayIf update_display_if);
}
