package jp.comeluck.mura.studybreakoutwithsurfaceview;

public class BallCenter {
    protected double x;    // 座標 X
    protected double y;    // 座標 Y

    /**
     * 座標をセット
     *
     * @param x
     * @param y
     */
    public void setCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * X のゲッター
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     * y のゲッター
     * @return
     */
    public double getY() {
        return y;
    }
}
