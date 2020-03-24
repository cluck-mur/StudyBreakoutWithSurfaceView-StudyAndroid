package jp.comeluck.mura.studybreakoutwithsurfaceview;

/**
 * ブロックの設定 クラス
 */
public class BlockConfig {
    private final int horizontalUnitsNumber;    // 水平方向のブロック数
    private final int width;  // ブロック1個の幅
    private final int height; // ブロック1個の高さ

    /**
     * コンストラクター
     * @param horizontal_units_number
     * @param view_width
     */
    public BlockConfig(int horizontal_units_number, int view_width, int view_height) {
        horizontalUnitsNumber = horizontal_units_number;
        width = view_width / horizontal_units_number;
        height = (int)(view_height * 0.03);
    }

    /**
     * 水平方向のブロック数のゲッター
     * @return
     */
    public int getHorizontalUnitsNumber() {
        return horizontalUnitsNumber;
    }

    /**
     * 幅 のゲッター
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * 高さ のゲッター
     * @return
     */
    public int getHeight() {
        return height;
    }
}
