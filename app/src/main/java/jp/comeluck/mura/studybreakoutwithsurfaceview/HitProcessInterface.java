package jp.comeluck.mura.studybreakoutwithsurfaceview;

/**
 * ボールとブロックや壁が衝突した時のデータを受け渡すクラス
 */
public class HitProcessInterface {
    public enum HitSide {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM
    }
    protected long hitableMsec;     // 衝突までの時間
    protected int newBallCenterX;   // 衝突時のボールセンターのX座標
    protected int newBallCenterY;   // 衝突時のボールセンターのY座標
    protected HitSide hitSide;      // 壁やブロックのどの側面に衝突したか

    /**
     * データセッター
     * @param hitable_msec
     * @param new_ball_center_X
     * @param new_ball_center_y
     */
    public void setData(long hitable_msec, int new_ball_center_X, int new_ball_center_y, HitSide hit_side) {
        hitableMsec = hitable_msec;
        newBallCenterX = new_ball_center_X;
        newBallCenterY = new_ball_center_y;
        hitSide = hit_side;
    }

    /**
     * 衝突までの時間のゲッター
     * @return
     */
    public long getHitableMsec() {
        return hitableMsec;
    }

    /**
     * 衝突時のボールセンターのX座標のゲッター
     * @return
     */
    public int getNewBallCenterX() {
        return newBallCenterX;
    }

    /**
     * 衝突時のボールセンターのY座標のゲッター
     * @return
     */
    public int getNewBallCenterY() {
        return newBallCenterY;
    }

    /**
     * 壁やブロックのどの側面に衝突したか のゲッター
     * @return
     */
    public HitSide getHitSide() {
        return hitSide;
    }
}
