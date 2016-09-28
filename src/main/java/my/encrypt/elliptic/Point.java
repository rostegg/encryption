package my.encrypt.elliptic;

import static java.lang.Math.*;
import java.util.Locale;

class Point {
    final static int bCoeff = 7;
 
    double x, y;
 
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
 
    static Point zero() {
        return new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
 
    boolean isZero() {
        return this.x > 1e20 || this.x < -1e20;
    }
 
    static Point fromY(double y) {
        return new Point(cbrt(pow(y, 2) - bCoeff), y);
    }
 
    Point dbl() {
        if (isZero())
            return this;
        double L = (3 * this.x * this.x) / (2 * this.y);
        double x2 = pow(L, 2) - 2 * this.x;
        return new Point(x2, L * (this.x - x2) - this.y);
    }
 
    Point negative() {
        return new Point(this.x, -this.y);
    }
 
    Point plus(Point q) {
        if (this.x == q.x && this.y == q.y)
            return dbl();
 
        if (isZero())
            return q;
 
        if (q.isZero())
            return this;
 
        double L = (q.y - this.y) / (q.x - this.x);
        double xx = pow(L, 2) - this.x - q.x;
        return new Point(xx, L * (this.x - xx) - this.y);
    }
 
    Point multiply(int n) {
    	Point r = Point.zero();
    	Point p = this;
        for (int i = 1; i <= n; i <<= 1) {
            if ((i & n) != 0)
                r = r.plus(p);
            p = p.dbl();
        }
        return r;
    }
 
    @Override
    public String toString() {
        if (isZero())
            return "Zero";
        return String.format(Locale.US, "(%.3f,%.3f)", this.x, this.y);
    }
}
