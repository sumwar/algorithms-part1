
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   // private static final int INVALID_SITE = -1;
   private final boolean[] openSites;
   private int numberOfOpenSites;
   private int gridSize;
   private WeightedQuickUnionUF wQUF;
   private int arraySize;

   public Percolation(int n)
   {
      if (n < 1)
      {
         throw new IllegalArgumentException("Invalid grid size " + n); 
      }
      
      gridSize = n;
      arraySize  = gridSize * gridSize + 2; // virtual 0 top and bottom site
      openSites = new boolean[arraySize];
      openSites[0] = true;
      openSites[arraySize - 1] = true;
      wQUF = new WeightedQuickUnionUF(arraySize);
      }

   public void open(int row, int col)
   {
      int site = siteValid(row, col);
      if (!openSites[site])
      {
         openSites[site] = true;
         numberOfOpenSites++;
      }
         
      // if in row 1 connect to top virtual site
      if (row == 1)
      {
         wQUF.union(0, site);
      }
      
      // if in bottom row connect to bottom virtual site
      if (row == gridSize)
      {
         wQUF.union(arraySize - 1, site);
      }

      // connect to neighbors
      unionNeighbors(row, col);
   }
   
   /**
    * is site open
    */
   public boolean isOpen(int row, int col)
   {
      int site = siteValid(row, col);
      return openSites[site];
   }
   
   /**
    * is site full
    */
   public boolean isFull(int row, int col)
   {
      int site = siteValid(row, col);
      return openSites[site] && wQUF.connected(0, site);
   }
   
   public int numberOfOpenSites()
   {
       return numberOfOpenSites;
   }

   private void unionNeighbors(int row, int col)
   {
      int site = siteValid(row, col); // current site
      
      // connect up
      int upSite = siteValid(row - 1, col);
      if (isOpen(row - 1, col) && openSites[upSite])
      {
         wQUF.union(site, upSite);
      }
      
      // connect down
      int downSite = siteValid(row + 1, col);
      if (isOpen(row + 1, col) && openSites[downSite])
      {
          wQUF.union(site, downSite);
      }
      
      // connect left
      int leftSite = siteValid(row, col - 1);
      if (isOpen(row, col - 1) && openSites[leftSite])
      {
          wQUF.union(site, leftSite);
      }
      
      // connect right
      int rightSite = siteValid(row, col + 1);
      if (isOpen(row, col + 1) && openSites[rightSite])
      {
          wQUF.union(site, rightSite);
      }
   }
   
   /**
    * convert a 2D location into a 1D representing an array index
    */
   private int xyTo1D(int row, int col)
   {
       int xyToID = (row - 1) * gridSize + col;
       return xyToID;
   }
   
   private int siteValid(int row, int col)
   {
      if (row < 1 || row > gridSize || col < 1 || col > gridSize)
      {
         throw new IndexOutOfBoundsException(String.format("Invalid site, (%s,%s)", row, col));
      }
      
      int xyTo1D = xyTo1D(row, col);
      return xyTo1D;
   }
      
   public boolean percolates()
   {
       // site located at index = 0 and site located at index arraySize-1 are connected, i.e. they are in the same component
       return wQUF.connected(0, arraySize - 1);
   }
      
   }