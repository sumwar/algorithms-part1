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
    final int location = validateLocation(row, col);
    return openSites[location] && unionFind.connected(0, location);
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
    final int location = validateLocation(row, col);
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
    final int location = validateLocation(row, col);
    if (openSites[location])
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
    final int northLocation = location(row - 1, col);
    if (northLocation != BAD_LOCATION && openSites[northLocation])
    {
      unionFind.union(northLocation, location);
    }

    // Connect south
    final int southLocation = location(row + 1, col);
    if (southLocation != BAD_LOCATION && openSites[southLocation])
    {
      unionFind.union(southLocation, location);
    }

    // Connect east
    final int eastLocation = location(row, col - 1);
    if (eastLocation != BAD_LOCATION && openSites[eastLocation])
    {
      unionFind.union(eastLocation, location);
    }

    // Connect west
    final int westLocation = location(row, col + 1);
    if (westLocation != BAD_LOCATION && openSites[westLocation])
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

  private int size()
  {
    return gridSide * gridSide + 2;
  }

  private int validateLocation(final int row, final int col)
  {
    final int location = location(row, col);
    if (location == BAD_LOCATION)
    {
      throw new IllegalArgumentException(String.format("Bad location, (%s, %s)",
                                                       row,
                                                       col));
    }
    return location;
  }

}
