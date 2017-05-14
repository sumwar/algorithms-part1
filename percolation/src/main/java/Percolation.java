import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{

  private static final int BAD_SITE_1D_INDEX = -1;

  private final int gridSide;
  private final WeightedQuickUnionUF unionFind;
  private int numberOfOpenSites;
  private final boolean[] openSites;

  /**
   * create n-by-n grid, with all site1DIndexs blocked
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
   * is site1DIndex (row, col) full?
   */
  public boolean isFull(final int row, final int col)
  {
    final int site1DIndex = validateSite(row, col);
    return openSites[site1DIndex] && unionFind.connected(0, site1DIndex);
  }

  /**
   * is site1DIndex (row, col) open?
   */
  public boolean isOpen(final int row, final int col)
  {
    final int site1DIndex = validateSite(row, col);
    return openSites[site1DIndex];
  }

  /**
   * number of open site1DIndexs
   */
  public int numberOfOpenSites()
  {
    return numberOfOpenSites;
  }

  /**
   * open site1DIndex (row, col) if it is not open already
   */
  public void open(final int row, final int col)
  {
    final int site1DIndex = validateSite(row, col);
    if (openSites[site1DIndex])
    {
      return;
    }

    openSites[site1DIndex] = true;
    numberOfOpenSites++;

    // Connect grid top row
    if (row == 1)
    {
      unionFind.union(0, site1DIndex);
    }

    // Connect grid bottom row - may cause backwash!
    if (row == gridSide)
    {
      unionFind.union(size() - 1, site1DIndex);
    }

    // Connect north
    final int north1DIndex = xyTo1D(row - 1, col);
    if (north1DIndex != BAD_SITE_1D_INDEX && openSites[north1DIndex])
    {
      unionFind.union(north1DIndex, site1DIndex);
    }

    // Connect south
    final int south1DIndex = xyTo1D(row + 1, col);
    if (south1DIndex != BAD_SITE_1D_INDEX && openSites[south1DIndex])
    {
      unionFind.union(south1DIndex, site1DIndex);
    }

    // Connect east
    final int east1DIndex = xyTo1D(row, col - 1);
    if (east1DIndex != BAD_SITE_1D_INDEX && openSites[east1DIndex])
    {
      unionFind.union(east1DIndex, site1DIndex);
    }

    // Connect west
    final int west1DIndex = xyTo1D(row, col + 1);
    if (west1DIndex != BAD_SITE_1D_INDEX && openSites[west1DIndex])
    {
      unionFind.union(west1DIndex, site1DIndex);
    }

  }

  /**
   * does the system percolate?
   */
  public boolean percolates()
  {
    return unionFind.connected(0, size() - 1);
  }

  private int size()
  {
    return gridSide * gridSide + 2;
  }

  private int validateSite(final int row, final int col)
  {
    final int site1DIndex = xyTo1D(row, col);
    if (site1DIndex == BAD_SITE_1D_INDEX)
    {
      throw new IndexOutOfBoundsException(String
        .format("Bad site (%d, %d) for grid size %d", gridSide, row, col));
    }
    return site1DIndex;
  }

  private int xyTo1D(final int row, final int col)
  {
    if (row < 1 || row > gridSide)
    {
      return BAD_SITE_1D_INDEX;
    }
    if (col < 1 || col > gridSide)
    {
      return BAD_SITE_1D_INDEX;
    }

    final int site1DIndex = (row - 1) * gridSide + col - 1 + 1;
    return site1DIndex;
  }

}
