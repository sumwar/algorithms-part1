import java.util.Arrays;

import edu.princeton.cs.algs4.LinkedStack;

public class FastCollinearPoints
{

  private class LineSegmentComparable
  {
    private final Point p0;
    private final Point p1;

    public LineSegmentComparable(final Point p0, final Point p1)
    {
      this.p0 = p0;
      this.p1 = p1;
    }

    @Override
    public boolean equals(final Object obj)
    {
      final LineSegmentComparable otherSegment = (LineSegmentComparable) obj;
      return otherSegment.p0.compareTo(p0) == 0
             && otherSegment.p1.compareTo(p1) == 0;
    }

  }

  private final LinkedStack<LineSegment> segments;

  private final LinkedStack<LineSegmentComparable> segmentsComparable;

  /**
   * finds all line segments containing 4 points
   */
  public FastCollinearPoints(final Point[] points)
  {
    if (points == null)
    {
      throw new NullPointerException();
    }

    segments = new LinkedStack<>();
    segmentsComparable = new LinkedStack<>();

    if (points.length < 4)
    {
      return;
    }

    for (int i = 0; i < points.length; i++)
    {
      final Point point = points[i];
      if (point == null)
      {
        throw new NullPointerException();
      }

      final Point[] pointsOther = new Point[points.length - 1];
      int index = 0;
      for (int j = 0; j < points.length; j++)
      {
        final Point otherPoint = points[j];
        if (otherPoint == null)
        {
          throw new NullPointerException();
        }
        if (i != j)
        {
          pointsOther[index++] = otherPoint;
        }
      }
      Arrays.sort(pointsOther, point.slopeOrder());

      int j = 0;
      while (j < pointsOther.length - 2)
      {
        final double slope = point.slopeTo(pointsOther[j]);
        int k = j + 1;
        while (k < pointsOther.length)
        {
          final double slope2 = point.slopeTo(pointsOther[k]);
          if (!doubleEquals(slope, slope2))
          {
            k--;
            break;
          }
          k++;
        }
        if (k >= pointsOther.length)
        {
          k--;
        }

        if (k >= j + 2)
        {
          final double slopeLast = point.slopeTo(pointsOther[k]);
          if (doubleEquals(slope, slopeLast))
          {
            final Point[] segmentPoints = new Point[k - j + 2];
            segmentPoints[0] = point;
            for (int m = 0; m <= k - j; m++)
            {
              segmentPoints[m + 1] = pointsOther[m + j];
            }
            Arrays.sort(segmentPoints);
            addLineSegments(segmentPoints);
          }
        }
        j++;
      }

    }

  }

  /**
   * the number of line segments
   */
  public int numberOfSegments()
  {
    return segments.size();
  }

  /**
   * the line segments
   */
  public LineSegment[] segments()
  {
    final LineSegment[] segmentsArray = new LineSegment[segments.size()];
    int index = 0;
    for (final LineSegment lineSegment: segments)
    {
      segmentsArray[index++] = lineSegment;
    }
    return segmentsArray;
  }

  private void addLineSegments(final Point[] points)
  {
    Arrays.sort(points);

    final Point pointStart = points[0];
    final Point pointEnd = points[points.length - 1];

    final LineSegmentComparable lineSegmentComparable = new LineSegmentComparable(pointStart,
                                                                                  pointEnd);
    for (final LineSegmentComparable lineSegmentCurrent: segmentsComparable)
    {
      if (lineSegmentComparable.equals(lineSegmentCurrent))
      {
        return;
      }
    }

    final LineSegment lineSegment = new LineSegment(pointStart, pointEnd);
    // DEBUG
    // System.out.println(String.format("%s slope %.3f points: %s",
    // lineSegment,
    // pointStart.slopeTo(pointEnd),
    // Arrays.toString(points)));
    // end DEBUG
    segments.push(lineSegment);
    segmentsComparable.push(lineSegmentComparable);
  }

  private boolean doubleEquals(final double slope1, final double slope2)
  {
    if (slope1 == Double.POSITIVE_INFINITY
        && slope2 == Double.POSITIVE_INFINITY)
    {
      return true;
    }
    if (slope1 == Double.NEGATIVE_INFINITY
        && slope2 == Double.NEGATIVE_INFINITY)
    {
      return true;
    }
    return Math.abs(slope1 - slope2) < 1e-10;
  }

}
