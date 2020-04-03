package jp.comeluck.mura.studybreakoutwithsurfaceview;

/**
 * updateDisplay() メソッドの引数に使う
 */
public class UpdateDisplayIf {
    protected long elapsedTime;  // 経過時間
    protected long elapsedTime2; // 処理に使った経過時間

    /**
     * elapsedTime をゲット
     * @return
     */
    public long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * elapsedTime をセット
     * @param elapsedTime
     */
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * elapsedTime2 をゲット
     * @return
     */
    public long getElapsedTime2() {
        return elapsedTime2;
    }

    /**
     * elapsedTime2 をセット
     * @param elapsedTime2
     */
    public void setElapsedTime2(long elapsedTime2) {
        this.elapsedTime2 = elapsedTime2;
    }
}
