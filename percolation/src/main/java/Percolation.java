import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{

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

    this.gridSide = n;
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
    final int northRow = row - 1;
    if (northRow > 0)
    {
      final int northLocation = location(northRow, col);
      unionFind.union(northLocation, location);
    }

    // Connect south
    final int southRow = row + 1;
    if (southRow <= gridSide)
    {
      final int southLocation = location(southRow, col);
      unionFind.union(southLocation, location);
    }

    // Connect east
    final int eastCol = col - 1;
    if (eastCol > 0)
    {
      final int eastLocation = location(row, eastCol);
      unionFind.union(eastLocation, location);
    }

    // Connect west
    final int westCol = col - 1;
    if (westCol > 0)
    {
      final int westLocation = location(row, westCol);
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

}
