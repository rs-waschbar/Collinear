import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * TRUE Bruteforce algorithm for searching collinear points!
 */
public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lines;

    /**
     * Constructor that implements bruteforce search
     * algorithm for finding collinear points and store results.
     *
     * Throw an IllegalArgumentException if the
     * argument to the constructor is null,
     * if any point in the array is null,
     * or if the argument to the constructor contains a repeated point.
     * @param inputPoints array of points to search collinear lines
     */
    public BruteCollinearPoints(Point[] inputPoints) {
        Point[] points = createCloneSortArray(inputPoints);
        lines = new ArrayList<>();
        bruteSearchInLinePoints(points);
    }

    /**
     * Helper method to split logic of Constructor.
     * It's check input data for Exceptions
     * and create sort copy of original array
     *
     * @param inputPoints Array of points that gets passed into constructor
     * @return sorting copy of Array for shield original array from modifications
     */
    private Point[] createCloneSortArray(Point[] inputPoints) {
        if (inputPoints == null)
            throw new IllegalArgumentException("Input array is null");

        for (Point point : inputPoints) {
            if (point == null)
                throw new IllegalArgumentException("One or more point values is null");
        }
        Point[] points = inputPoints.clone();
        Arrays.sort(points);

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("Array must not have duplicates");
        }
        return points;
    }

    /**
     * Bruteforce iteration for searching every four points
     * that create one line
     *
     * @param points input Array of points for search
     */
    private void bruteSearchInLinePoints(Point[] points) {
        //
        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = q + 1; r < points.length - 1; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])
                                && points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {

                            lines.add(new LineSegment(points[p], points[s]));
                        }
                    }
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
        In in = new In("D:\\input200.txt"); // D:\rs1423.txt test file
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
