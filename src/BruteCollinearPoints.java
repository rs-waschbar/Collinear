import java.util.ArrayList;
import java.util.Arrays;

/**
 * TRUE Bruteforce algorithm for searching collinear points!
 */
public class BruteCollinearPoints {
    private ArrayList<LineSegment> lines = new ArrayList<>();

    /**
     * Constructor that implements bruteforce search
     * algorithm for finding collinear points and store results
     *
     * Throw an IllegalArgumentException if the
     * argument to the constructor is null,
     * if any point in the array is null,
     * or if the argument to the constructor contains a repeated point.
     * @param inputPoints
     */
    public BruteCollinearPoints(Point[] inputPoints) {
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

        // bruteforce iteration for every four points
        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = q + 1; r < points.length - 1; r++) {
                    for (int s = r + 1; s < points.length; s++) {

                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])
                            && points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {

                            lines.add(new LineSegment(points[p], points[q]));
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
}
