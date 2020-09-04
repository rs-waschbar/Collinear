import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lines;

    /**
     *
     * @param inputPoints array of points to search collinear lines
     */
    public FastCollinearPoints(Point[] inputPoints) {
        // input checks
        if (inputPoints == null)
            throw new IllegalArgumentException("Input array is null");

        for (Point point : inputPoints) {
            if (point == null)
                throw new IllegalArgumentException("One or more point values is null");
        }
        Point[] points = inputPoints.clone();
        Arrays.sort(points);

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == points[i+1])
                throw new IllegalArgumentException("Array must not have duplicates");
        }

        lines = new ArrayList<>();
        int count;

        for (int p = 0; p < points.length - 2; p++) {
            Arrays.sort(points, points[p].slopeOrder());
            count = 1;

            for (int q = p + 1; q < points.length - 1; q++) {
                if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[q + 1])) {
                    count++;
                }
                else if (count >= 4) {
                    lines.add(new LineSegment(points[p], points[p]));
                }
            }
        }
    }


    /**
     * Return number of collinear line segments that
     * contains four points
     *
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return lines.size();
    }

    /**
     * Method that return array of collinear
     * line segments
     *
     * @return the line segments
     */
    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[lines.size()]);
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }



        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}