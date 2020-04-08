package jp.comeluck.mura.studybreakoutwithsurfaceview;

public abstract class Wall {
    protected double left;
    protected double top;
    protected double height;
    protected double width;

    /**
     * コンストラクター
     * @param left
     * @param top
     * @param height
     * @param width
     */
    public Wall(double left, double top, double height, double width) {
        this.left = left;
        this.top = top;
        this.height = height;
        this.width = width;
    }

    /**
     * ボールが衝突するまでに必要な時間を計算する
     * @param ball
     * @param update_display_if
     * @param arg_distance
     * @return  衝突までの時間などのデータを詰めた HitProcessInterface オブジェクト
     *          衝突しない場合は null を返す
     */
    abstract protected HitProcessInterface calcNecessaryTimeToHit(Ball ball, UpdateDisplayIf update_display_if, Double arg_distance);

    /**
     * ボールが衝突するまでに必要な時間を計算する (デフォルト引数用)
     * @param ball
     * @param update_display_if
     * @return
     */
    protected HitProcessInterface calcNecessaryTimeToHit(Ball ball, UpdateDisplayIf update_display_if) {
        return calcNecessaryTimeToHit(ball, update_display_if, null);
    }

    /**
     * ボールがぶつかったか判定
     * @param ball
     * @return  衝突までの時間などのデータを詰めた HitProcessInterface オブジェクト
     *          衝突しない場合は null を返す
     */
    abstract public HitProcessInterface checkHit(Ball ball, UpdateDisplayIf update_display_if);
}
