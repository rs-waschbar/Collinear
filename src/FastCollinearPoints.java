import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lines;

    /**
     *
     * @param inputPoints array of points to search collinear lines
     */
    public FastCollinearPoints(Point[] inputPoints) {
        Point[] points = createCloneSortArray(inputPoints);

        lines = new ArrayList<>();
        ArrayList<Point> visited = new ArrayList<>();
        Point[] pointsClone = points.clone();

        for (Point startingPoint : points) {
            Arrays.sort(pointsClone, startingPoint.slopeOrder());
            findLines(pointsClone, startingPoint, lines, visited);
            visited.add(startingPoint);
        }
        System.out.println(lines.size() + "*****");
    }

    private void findLines(Point[] points, Point startingPoint,
                           ArrayList<LineSegment> lines, ArrayList<Point> visited) {
        ArrayList<Point> inlinePoints = new ArrayList<>();
        inlinePoints.add(startingPoint);
        inlinePoints.add(points[1]);

        double slope = startingPoint.slopeTo(points[1]);

        for (int i = 2; i < points.length; i++) {
            double currentSlope = startingPoint.slopeTo(points[i]);

            if (isCollinearLines(slope, currentSlope)) {
                inlinePoints.add(points[i]);
            } else {
                if (inlinePoints.size() >= 4) {
                    inlinePoints.sort(Comparator.naturalOrder());
                    if (startingPoint.compareTo(inlinePoints.get(0)) == 0) {
                        lines.add(createLineFromPoints(inlinePoints));
                    }
                }

                inlinePoints = new ArrayList<>();
                inlinePoints.add(startingPoint);
                inlinePoints.add(points[i]);

                slope = currentSlope;
            }
        }
        if (inlinePoints.size() >= 4) {
            inlinePoints.sort(Comparator.naturalOrder());
            if (startingPoint.compareTo(inlinePoints.get(0)) == 0) {
                lines.add(createLineFromPoints(inlinePoints));
            }
        }
    }

    //private boolean isVisited

    private boolean isCollinearLines(double slope, double anotherSlope) {
        if (Double.compare(slope, anotherSlope) == 0)
            return true;
        return false;
    }


    private LineSegment createLineFromPoints(ArrayList<Point> inlinePoints) {
//        inlinePoints.sort(Comparator.naturalOrder());
        return new LineSegment(inlinePoints.get(0),
                                inlinePoints.get(inlinePoints.size() -  1));
    }


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

    private void getLineFromPoints(ArrayList<Point> input) {
        input.sort(null);
    }

    public int numberOfSegments() {
        return lines.size();
    }

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