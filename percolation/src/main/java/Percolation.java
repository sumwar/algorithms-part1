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
   */
  public Percolation(final int n)
  {
    if (n < 1)
    {
      throw new IndexOutOfBoundsException("Invalid grid size, " + n);
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
   */
  public boolean isFull(final int row, final int col)
  {
    final int site = validateSite(row, col);
    return openSites[site] && unionFind.connected(0, site);
  }

  /**
   * is site (row, col) open?
   */
  public boolean isOpen(final int row, final int col)
  {
    final int site = validateSite(row, col);
    return openSites[site];
  }

  /**
   * number of open sites
   */
  public int numberOfOpenSites()
  {
    return numberOfOpenSites;
  }

  /**
   * open site (row, col) if it is not open already
   */
  public void open(final int row, final int col)
  {
    final int site = validateSite(row, col);
    if (openSites[site])
    {
      return;
    }

    openSites[site] = true;
    numberOfOpenSites++;

    // Connect grid top row
    if (row == 1)
    {
      unionFind.union(0, site);
    }

    // Connect north
    final int northLocation = xyTo1D(row - 1, col);
    if (northLocation != BAD_LOCATION && openSites[northLocation])
    {
      unionFind.union(northLocation, site);
    }

    // Connect south
    final int southLocation = xyTo1D(row + 1, col);
    if (southLocation != BAD_LOCATION && openSites[southLocation])
    {
      unionFind.union(southLocation, site);
    }

    // Connect east
    final int eastLocation = xyTo1D(row, col - 1);
    if (eastLocation != BAD_LOCATION && openSites[eastLocation])
    {
      unionFind.union(eastLocation, site);
    }

    // Connect west
    final int westLocation = xyTo1D(row, col + 1);
    if (westLocation != BAD_LOCATION && openSites[westLocation])
    {
      unionFind.union(westLocation, site);
    }

  }

  /**
   * does the system percolate?
   */
  public boolean percolates()
  {
    // Connect open sites on the bottom row of the grid to the end
    for (int col = 1; col <= gridSide; col++)
    {
      final int site = xyTo1D(gridSide, col);
      if (openSites[site] && isFull(gridSide, col))
      {
        unionFind.union(size() - 1, site);
      }
    }
    // Check percolation
    return unionFind.connected(0, size() - 1);
  }

  private int size()
  {
    return gridSide * gridSide + 2;
  }

  private int validateSite(final int row, final int col)
  {
    final int site = xyTo1D(row, col);
    if (site == BAD_LOCATION)
    {
      throw new IndexOutOfBoundsException(String
        .format("Bad site (%d, %d) for grid size %d", gridSide, row, col));
    }
    return site;
  }

  private int xyTo1D(final int row, final int col)
  {
    if (row < 1 || row > gridSide)
    {
      return BAD_LOCATION;
    }
    if (col < 1 || col > gridSide)
    {
      return BAD_LOCATION;
    }

    final int site = (row - 1) * gridSide + col - 1 + 1;
    return site;
  }

}
