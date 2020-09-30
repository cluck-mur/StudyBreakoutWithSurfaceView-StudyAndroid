package jp.comeluck.mura.studybreakoutwithsurfaceview;

public class CrossPoint {
//    PVector[] static getCrossPoints(float x1, float y1, float x2, float y2, float circleX, float circleY, float r) {
//
//        //ax + by + c = 0 の定数項
//        float a = y2 - y1;
//        float b = x1 - x2;
//        float c = -a * x1 - b * y1;
//
//        //円の中心から直線までの距離
//        //mag(a, b) = √a^2+b^2
//        float v = (a * circleX) + (b * circleY) + c;
//        float d = Math.abs(v / mag(a, b));
//
//        //直線の垂線とX軸と平行な線がなす角度θ
//        float theta = atan2(b, a);
//
//        if (d > r) {
//            return null;
//        } else if (d == r) {
//            PVector[] point = new PVector[1];
//
//            //場合わけ
//            if (a * circleX + b * circleY + c > 0) theta += PI;
//
//            float crossX = r * cos(theta) + circleX;
//            float crossY = r * sin(theta) + circleY;
//
//            point[0] = new PVector(crossX, crossY);
//            return point;
//        } else {
//
//            PVector[] crossPoint = new PVector[2];
//            float[] crossX = new float[2];
//            float[] crossY = new float[2];
//
//            //alphaとbetaの角度を求める
//            float alpha, beta, phi;
//            phi = acos(d / r);
//            alpha = theta - phi;
//            beta = theta + phi;
//
//            //場合わけ
//            if (a * circleX + b * circleY + c > 0) {
//                alpha += PI;
//                beta += PI;
//            }
//
//            //交点の座標を求める
//            crossX[0] = r * cos(alpha) + circleX;
//            crossY[0] = r * sin(alpha) + circleY;
//
//            crossX[1] = r * cos(beta) + circleX;
//            crossY[1] = r * sin(beta) + circleY;
//
//
//            for (int i = 0; i < crossPoint.length; i++)
//                crossPoint[i] = new PVector(crossX[i], crossY[i]);
//
//            return crossPoint;
//        }
//    }

    /**
     *
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param circleX
     * @param circleY
     * @param r
     * @return
     */
    public static PVector[] getCrossPoints(double x1, double y1, double x2, double y2, double circleX, double circleY, double r) {

        //ax + by + c = 0 の定数項
        double a = y2 - y1;
        double b = x1 - x2;
        double c = -a * x1 - b * y1;

        //円の中心から直線までの距離
        //mag(a, b) = √a^2+b^2
        double v = (a * circleX) + (b * circleY) + c;
        double d = Math.abs(v / mag(a, b));

        //直線の垂線とX軸と平行な線がなす角度θ
        double theta = Math.atan2(b, a);

        if (d > r) {
            return null;
        } else if (d == r) {
            PVector[] point = new PVector[1];

            //場合わけ
            if (v > 0) theta += Math.PI;

            double crossX = r * Math.cos(theta) + circleX;
            double crossY = r * Math.sin(theta) + circleY;

            point[0] = new PVector(crossX, crossY);
            return point;
        } else {

            PVector[] crossPoint = new PVector[2];
            double[] crossX = new double[2];
            double[] crossY = new double[2];

            //alphaとbetaの角度を求める
            double alpha, beta, phi;
            phi = Math.acos(d / r);
            alpha = theta - phi;
            beta = theta + phi;

            //場合わけ
            if (v > 0) {
                alpha += Math.PI;
                beta += Math.PI;
            }

            //交点の座標を求める
            crossX[0] = r * Math.cos(alpha) + circleX;
            crossY[0] = r * Math.sin(alpha) + circleY;

            crossX[1] = r * Math.cos(beta) + circleX;
            crossY[1] = r * Math.sin(beta) + circleY;


            for (int i = 0; i < crossPoint.length; i++)
                crossPoint[i] = new PVector(crossX[i], crossY[i]);

            return crossPoint;
        }
    }

    /**
     *
     * @param x1
     * @param y1
     * @param angle
     * @param circleX
     * @param circleY
     * @param r
     * @return
     */
    public static PVector[] getCrossPoints(double x1, double y1, double angle, double circleX, double circleY, double r) {
        double radians = Math.toRadians(angle);
        double x2 = 0;
//        double y2 = x2 * Math.tan(radians) + y1 - (x1 * Math.tan(angle));
        double tan = Math.round(Math.tan(radians) * 10000) / 10000;
        double xtan = x1 * tan;
        double y2 = y1 - xtan;
        return getCrossPoints(x2, y2, x1, y1, circleX, circleY, r);
    }

        /**
         * 2点間の距離を返す
         * @param x1
         * @param y1
         * @param x2
         * @param y2
         * @return
         */
    public static double dist( double x1, double y1, double x2, double y2 )
    {
        double l;
        double dx, dy;

        // x座標の差を計算してdxに代入
        dx = x2 - x1;
        // y座標の差を計算してdyに代入
        dy = y2 - y1;

        // ２点間の距離を計算してlに代入
        l = Math.sqrt( dx * dx + dy * dy );

        // 計算した距離を返す
        return l;
    }

    /**
     *
     * @param x2
     * @param y2
     * @return
     */
    private static double mag(double x2, double y2) {
        return dist(0, 0, x2, y2);
    }
}
