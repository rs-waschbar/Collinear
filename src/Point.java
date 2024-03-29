import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

    private final int x; // x-coordinate of this point
    private final int y; // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (x == that.x && y == that.y) return Double.NEGATIVE_INFINITY;
        if (x == that.x) return Double.POSITIVE_INFINITY;
        if (y == that.y) return +0.0;

        return (double) (that.y - y) / (that.x - x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        int result = Integer.compare(y, that.y);
        if (result == 0) {
            result += Integer.compare(x, that.x);
        }

        return result;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {

        return Comparator.comparingDouble(this::slopeTo);

//      return (p1, p2) -> Double.compare(slopeTo(p1), slopeTo(p2));
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p1 = new Point(3, 3);
        Point p2 = new Point(4, 4);
        Point p3 = new Point(5, 5);
        Point p4 = new Point(6, 0);
        Point p5 = new Point(0, 6);
        Point p6 = new Point(1, 7);
        Point p7 = new Point(5, 5);

        System.out.println("points:" + p1 + p2 + p3 + " has equals slope: "
                + (p1.slopeTo(p2) == p2.slopeTo(p3)));
        System.out.println("points:" + p1 + p2 + " and " + p5 + p6
                + " has equals slope: " + (p1.slopeTo(p2) == p5.slopeTo(p6)));
        System.out.println("points:" + p1 + p2  + " and " +  p4 + p6
                + " has equals slope: " + (p1.slopeTo(p2) == p4.slopeTo(p6)));

        System.out.println("points:" + p3 + p7 + " has compareTo int: " + p3.compareTo(p7));
        System.out.println("points:" + p4 + p5 + " has compareTo int: " + p4.compareTo(p5));
    }
}