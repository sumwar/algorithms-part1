
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private static final int INVALID_SITE = -1;
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
      int site = xyTo1D(row, col);
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
      unionAround(row, col);
   }
   
   /**
    * is site open
    */
   public boolean isOpen(int row, int col)
   {
      int site = xyTo1D(row, col);
      return openSites[site];
   }
   
   /**
    * is site full
    */
   public boolean isFull(int row, int col)
   {
      int site = xyTo1D(row, col);
      return openSites[site] && wQUF.connected(0, site);
   }
   
   public int numberOfOpenSites()
   {
       return numberOfOpenSites;
   }

   private void unionAround(int row, int col)
   {
      int site = xyTo1D(row, col); // current site
      
      // connect up
      int upSite = xyTo1D(row - 1, col);
      if (upSite != INVALID_SITE && openSites[upSite])
      {
         wQUF.union(site, upSite);
      }
      
      // connect down
      int downSite = xyTo1D(row + 1, col);
      if (downSite != INVALID_SITE && openSites[downSite])
      {
          wQUF.union(site, downSite);
      }
      
      // connect left
      int leftSite = xyTo1D(row, col - 1);
      if (leftSite != INVALID_SITE && openSites[leftSite])
      {
          wQUF.union(site, leftSite);
      }
      
      // connect right
      int rightSite = xyTo1D(row, col + 1);
      if (rightSite != INVALID_SITE && openSites[rightSite])
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
      if (row < 1 || row > gridSize || col < 1 || col > gridSize)
      {
         xyToID = INVALID_SITE;
         throw new IndexOutOfBoundsException(String.format("Invalid site, (%s,%s)", row, col));
      }
       
      return xyToID;
   }
         
   public boolean percolates()
   {
       // site located at index = 0 and site located at index arraySize-1 are connected, i.e. they are in the same component
       return wQUF.connected(0, arraySize - 1);
   }
      
   }