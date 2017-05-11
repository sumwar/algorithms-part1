import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{

  private static final int BAD_LOCATION = -1;
  private final int gridSide;
  private final WeightedQuickUnionUF unionFind;

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
    unionFind = new WeightedQuickUnionUF(gridSize());
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
    location(row, col);

    return false;
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
    location(row, col);

    return false;
  }

  /**
   * number of open sites
   *
   * @return
   */
  public int numberOfOpenSites()
  {
    return 0;
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

    // Connect grid top row
    if (row == 1)
    {
      unionFind.union(0, location);
    }

    // Connect grid bottom row
    if (row == gridSide)
    {
      unionFind.union(gridSize() - 1, location);
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
    final int eastLocation = location(row, col);
    if (eastLocation > 0)
    {
      unionFind.union(eastLocation, location);
    }

    // Connect west
    final int westLocation = location(row, col);
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
    return unionFind.connected(0, gridSize());
  }

  private int gridSize()
  {
    return gridSide * 2 + 2;
  }

  private int location(final int row, final int col)
  {
    if (row < 1 || row > gridSide)
    {
      throw new IllegalArgumentException("Row out of bounds, " + row);
    }
    if (col < 1 || col > gridSide)
    {
      throw new IllegalArgumentException("Column out of bounds, " + col);
    }

    final int location = (row - 1) * gridSide + col - 1 + 1;
    return location;
  }

  private int northLocation(final int row, final int col)
  {
    final int northRow = row - 1;
    if (northRow > 0)
    {
      return location(northRow, col);
    }
    else
    {
      return BAD_LOCATION;
    }
  }

  private int southLocation(final int row, final int col)
  {
    final int southRow = row - 1;
    if (southRow <= gridSide)
    {
      return location(southRow, col);
    }
    else
    {
      return BAD_LOCATION;
    }
  }

}
