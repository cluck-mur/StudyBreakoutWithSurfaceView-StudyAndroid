package jp.comeluck.mura.studybreakoutwithsurfaceview;

public class BallCenter {
    protected int x;    // 座標 X
    protected int y;    // 座標 Y

    /**
     * 座標をセット
     *
     * @param x
     * @param y
     */
    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * X のゲッター
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * y のゲッター
     * @return
     */
    public int getY() {
        return y;
    }
}
