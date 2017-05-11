import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{

  private static final int BAD_LOCATION = -1;

  private final int gridSide;
  private final WeightedQuickUnionUF unionFind;
  private int numberOfOpenSites;
  private final boolean[] openSites;

  /**
   * create n-by-n grid, with all sites blocked
   *
   * @param n
   *        Side of grid
   */
  public Percolation(final int n)
  {
    if (n < 1)
    {
      throw new IllegalArgumentException("Invalid grid size, " + n);
    }

    gridSide = n;
    final int size = size();
    unionFind = new WeightedQuickUnionUF(size);

    openSites = new boolean[size];
    openSites[0] = true;
    openSites[size - 1] = true;
  }

  /**
   * is site (row, col) full?
   *
   * @param row
   * @param col
   * @return
   */
  public boolean isFull(final int row, final int col)
  {
    final int location = location(row, col);
    if (location == BAD_LOCATION)
    {
      throw new IllegalArgumentException(String.format("Bad location, (%s, %s)",
                                                       row,
                                                       col));
    }
    return openSites[location] && unionFind.connected(0, location)
           && unionFind.connected(size() - 1, location);
  }

  /**
   * is site (row, col) open?
   *
   * @param row
   * @param col
   * @return
   */
  public boolean isOpen(final int row, final int col)
  {
    final int location = location(row, col);
    if (location == BAD_LOCATION)
    {
      throw new IllegalArgumentException(String.format("Bad location, (%s, %s)",
                                                       row,
                                                       col));
    }
    return openSites[location];
  }

  /**
   * number of open sites
   *
   * @return
   */
  public int numberOfOpenSites()
  {
    return numberOfOpenSites;
  }

  /**
   * open site (row, col) if it is not open already
   *
   * @param row
   * @param col
   */
  public void open(final int row, final int col)
  {
    final int location = location(row, col);
    if (location == BAD_LOCATION)
    {
      throw new IllegalArgumentException(String.format("Bad location, (%s, %s)",
                                                       row,
                                                       col));
    }
    if (isOpen(row, col))
    {
      return;
    }

    openSites[location] = true;
    numberOfOpenSites++;

    // Connect grid top row
    if (row == 1)
    {
      unionFind.union(0, location);
    }

    // Connect grid bottom row
    if (row == gridSide)
    {
      unionFind.union(size() - 1, location);
    }

    // Connect north
    final int northLocation = northLocation(row, col);
    if (northLocation > 0)
    {
      unionFind.union(northLocation, location);
    }

    // Connect south
    final int southLocation = southLocation(row, col);
    if (southLocation > 0)
    {
      unionFind.union(southLocation, location);
    }

    // Connect east
    final int eastLocation = eastLocation(row, col);
    if (eastLocation > 0)
    {
      unionFind.union(eastLocation, location);
    }

    // Connect west
    final int westLocation = westLocation(row, col);
    if (westLocation > 0)
    {
      unionFind.union(westLocation, location);
    }

  }

  /**
   * does the system percolate?
   */
  public boolean percolates()
  {
    return unionFind.connected(0, size() - 1);
  }

  private int eastLocation(final int row, final int col)
  {
    return location(row, col - 1);
  }

  private int location(final int row, final int col)
  {
    if (row < 1 || row > gridSide)
    {
      return BAD_LOCATION;
    }
    if (col < 1 || col > gridSide)
    {
      return BAD_LOCATION;
    }

    final int location = (row - 1) * gridSide + col - 1 + 1;
    return location;
  }

  private int northLocation(final int row, final int col)
  {
    return location(row - 1, col);
  }

  private int size()
  {
    return gridSide * gridSide + 2;
  }

  private int southLocation(final int row, final int col)
  {
    return location(row - 1, col);
  }

  private int westLocation(final int row, final int col)
  {
    return location(row, col + 1);
  }

}
