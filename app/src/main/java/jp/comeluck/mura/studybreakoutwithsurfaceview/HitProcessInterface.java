package jp.comeluck.mura.studybreakoutwithsurfaceview;

import android.util.Log;

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
    protected Block block = null;
    protected Wall wall = null;
    protected long hitableMsec;     // 衝突までの時間 ( -1 は衝突なし)
    protected double newBallCenterX;   // 衝突時のボールセンターのX座標
    protected double newBallCenterY;   // 衝突時のボールセンターのY座標
    protected double newAngle;      // 衝突後の進行方向
    protected HitSide hitSide;      // 壁やブロックのどの側面に衝突したか

    /**
     * データセッター
     * @param hitable_msec
     * @param new_ball_center_X
     * @param new_ball_center_y
     */
    private void setData(long hitable_msec, double new_ball_center_X, double new_ball_center_y, HitSide hit_side) {
        hitableMsec = hitable_msec;
        newBallCenterX = new_ball_center_X;
        newBallCenterY = new_ball_center_y;
        hitSide = hit_side;
    }

    /**
     * データセッター
     * @param block
     * @param hitable_msec
     * @param new_ball_center_X
     * @param new_ball_center_y
     * @param hit_side
     */
    public void setData(Block block, long hitable_msec, double new_ball_center_X, double new_ball_center_y, HitSide hit_side) {
        this.block = block;
        setData(hitable_msec, new_ball_center_X, new_ball_center_y, hit_side);
    }

    /**
     * データセッター
     * @param wall
     * @param hitable_msec
     * @param new_ball_center_X
     * @param new_ball_center_y
     * @param hit_side
     */
    public void setData(Wall wall, long hitable_msec, double new_ball_center_X, double new_ball_center_y, HitSide hit_side) {
        this.wall = wall;
        setData(hitable_msec, new_ball_center_X, new_ball_center_y, hit_side);
    }

    /**
     * block のゲッター
     * @return
     */
    public Block getBlock() {
        return block;
    }

    /**
     * block のセッター
     * @param block
     */
    public void setBlock(Block block) {
        this.block = block;
    }

    /**
     * wall のゲッター
     * @return
     */
    public Wall getWall() {
        return wall;
    }

    /**
     * wall のセッター
     * @param wall
     */
    public void setWall(Wall wall) {
        this.wall = wall;
    }

    /**
     * 衝突までの時間のゲッター
     * @return
     */
    public long getHitableMsec() {
        return hitableMsec;
    }

    /**
     * 衝突までの時間のセッター
     * @param hitableMsec
     */
    public void setHitableMsec(long hitableMsec) {
        this.hitableMsec = hitableMsec;
    }

    /**
     * 衝突時のボールセンターのX座標のゲッター
     * @return
     */
    public double getNewBallCenterX() {
        return newBallCenterX;
    }

    /**
     * 衝突時のボールセンターのX座標のセッター
     * @param newBallCenterX
     */
    public void setNewBallCenterX(double newBallCenterX) {
        this.newBallCenterX = newBallCenterX;
    }

    /**
     * 衝突時のボールセンターのY座標のゲッター
     * @return
     */
    public double getNewBallCenterY() {
        return newBallCenterY;
    }

    /**
     * 衝突時のボールセンターのY座標のセッター
     * @param newBallCenterY
     */
    public void setNewBallCenterY(double newBallCenterY) {
       this.newBallCenterY = newBallCenterY;
    }

    /**
     * 新しいボールの進行角度のゲッター
     * @return
     */
    public double getNewAngle() {
        return newAngle;
    }

    /**
     * 新しいボールの進行角度のセッター
     * @param newAngle
     */
    public void setNewAngle(double newAngle) {
        this.newAngle = newAngle;
        if (newAngle == 0d || newAngle == 180d) {
            Log.d("Ball", String.format("角度がおかしい"));
        }
    }

    /**
     * 壁やブロックのどの側面に衝突したか のゲッター
     * @return
     */
    public HitSide getHitSide() {
        return hitSide;
    }

    /**
     * 壁やブロックのどの側面に衝突したか のセッター
     * @param hitSide
     */
    public void setHitSide(HitSide hitSide) {
        this.hitSide = hitSide;
    }
}
