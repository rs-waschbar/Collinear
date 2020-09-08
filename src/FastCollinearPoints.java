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
        Point[] pointsClone = points.clone();

        for (Point startingPoint : points) {
            Arrays.sort(pointsClone, startingPoint.slopeOrder());
            findLines(pointsClone, startingPoint, lines);
        }
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

    private void findLines(Point[] points, Point startingPoint,
                           ArrayList<LineSegment> lineSegments) {
        if (points.length < 4) {
            return;
        }
        ArrayList<Point> inline = createInlineArr(startingPoint, points[1]);
        double slope = startingPoint.slopeTo(points[1]);

        for (int i = 2; i < points.length; i++) {
            double currentSlope = startingPoint.slopeTo(points[i]);

            if (isCollinear(slope, currentSlope)) {
                inline.add(points[i]);
            } else {
                extractLineFromPoints(inline, startingPoint, lineSegments);
                inline = createInlineArr(startingPoint, points[i]);
                slope = currentSlope;
            }
        }
        extractLineFromPoints(inline, startingPoint, lineSegments);
    }



    private ArrayList<Point> createInlineArr(Point startingPoint, Point secondPoint) {
        ArrayList<Point> arrayList = new ArrayList<>();
        arrayList.add(startingPoint);
        arrayList.add(secondPoint);

        return arrayList;
    }

    private boolean isCollinear(double slope, double anotherSlope) {
        return Double.compare(slope, anotherSlope) == 0;
    }

    private void extractLineFromPoints(ArrayList<Point> inlinePoints, Point startingPoint,
                                       ArrayList<LineSegment> lineSegments){
        if (inlinePoints.size() >= 4) {
            inlinePoints.sort(Comparator.naturalOrder());
            if (startingPoint.compareTo(inlinePoints.get(0)) == 0) {
                lineSegments.add(new LineSegment(inlinePoints.get(0),
                                         inlinePoints.get(inlinePoints.size() -  1)));
            }
        }
    }

//
//    private LineSegment createLineFromPoints(ArrayList<Point> inlinePoints) {
//        return new LineSegment(inlinePoints.get(0),
//                                inlinePoints.get(inlinePoints.size() -  1));
//    }

    public int numberOfSegments() {
        return lines.size();
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[lines.size()]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]); // "D:\\rs1423.txt" test file
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