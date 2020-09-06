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
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("Array must not have duplicates");
        }

        lines = new ArrayList<>();
        int count;
        Point[] cloneForIter = points.clone();
        Point[] visited = new Point[points.length];
        ArrayList<Point> inline;

        for (int p = 0; p < points.length - 1; p++) {

            Arrays.sort(cloneForIter, points[p].slopeOrder());

            inline = new ArrayList<>();
            inline.add(cloneForIter[0]);
            count = 1;

            for (int q = 1; q < cloneForIter.length; q++) {
                if (cloneForIter[0].slopeTo(cloneForIter[q-1]) == cloneForIter[0].slopeTo(cloneForIter[q])) {
                    count++;
                    inline.add(cloneForIter[q]);
                }
                else {
                    if (count >= 4  && !visitedContains(inline, visited)) {
                        lines.add(getLineFromPoints(inline));
                        visited[p] = points[p];
                    }
                }
            }
        }
    }

    private boolean visitedContains(ArrayList<Point> inline, Point[] visited) {
        for (Point linePoint : inline) {
            for (Point visit : visited) {
                if (linePoint.equals(visit)) return true;
            }

        }
        return false;
    }

    private LineSegment getLineFromPoints(ArrayList<Point> input) {
        Point[] inline = input.toArray(new Point[input.size()]);
        Arrays.sort(inline);
        return new LineSegment(inline[0], inline[inline.length - 1]);
    }


    /**
     * Return number of collinear line segments that
     * contains four and more points
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
        In in = new In("D:\\input20.txt"); // "D:\\rs1423.txt" test file
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