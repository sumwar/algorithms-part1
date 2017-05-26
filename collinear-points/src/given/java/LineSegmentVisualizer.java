import java.awt.Color;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class LineSegmentVisualizer
{
  public static void main(final String[] args)
  {
    final String filename = "C:/Users/Sualeh Fatehi/Documents/_Projects/"
                            + "algorithms-part1/collinear-points/"
                            + "src/test/resources/collinear-testing/"
                            + "input56.txt"; // args[0];

    // read the n points from a file
    final In in = new In(filename);
    final int n = in.readInt();
    final Point[] points = new Point[n];
    for (int i = 0; i < n; i++)
    {
      final int x = in.readInt();
      final int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.setPenColor(Color.RED);
    for (final Point p: points)
    {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    StdDraw.setPenColor(Color.BLUE);
    final FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (final LineSegment segment: collinear.segments())
    {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }

}
