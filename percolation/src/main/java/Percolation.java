
import edu.princeton.cs.algs4.WeightedQuickUnionUF;;

public class Percolation {
	private final boolean[] openSites;
	private int numberOfOpenSites;
	private int gridSize;
	private WeightedQuickUnionUF wQUF;
	private int arraySize;
	
	public Percolation(int n)
	{
		if (n < 1 ) 
		{
			throw new IllegalArgumentException( n + ", grid size invalid");
		}
		
		gridSize = n;
		arraySize  = gridSize * gridSize + 2; //virtual 0 top row site, virtual bottom row site
		openSites = new boolean[arraySize];
		openSites[0] = true;
		openSites[arraySize - 1] = true;
		wQUF = new WeightedQuickUnionUF(arraySize);
	}
	
	public void open(int row, int col)
	{
		int site = xyTo1D(row, col);
		if(!openSites[site])
		{
			openSites[site] = true;
			numberOfOpenSites ++;
		}
		
		//if in row 1 connect to top virtual site
		if(row == 1)
		{
			wQUF.union(0, site);
		}
		
		//if in bottom row connect to bottom virtual site
		if(row == gridSize) 
		{
			wQUF.union(arraySize - 1, site);
		}
		
		//connect up
		int upSite = xyTo1D(row - 1, col);
		if (openSites[upSite])
		{
			wQUF.union(site, upSite);
		}
		
		//connect down
		int downSite = xyTo1D(row + 1, col);
		if (openSites[downSite])
		{
			wQUF.union(site, downSite);
		}
		
		//left
		int leftSite = xyTo1D(row, col - 1);
		if (openSites[leftSite])
		{
			wQUF.union(site, leftSite);
		}
		
		//right
		int rightSite = xyTo1D(row, col + 1);
		if (openSites[rightSite])
		{
			wQUF.union(site, rightSite);
		}
		
	}
	
	public boolean isOpen(int row, int col)
	{
		int site = xyTo1D(row, col);
		return openSites[site];
	}
	
	public boolean isFull(int row, int col)
	{
		//need to understand concept further. The site has to be open and connected to the top row
		//an open site that is surrounded by sites that are closed is "blocked" and therefore is not Full
		int site = xyTo1D(row, col);
		return openSites[site] && wQUF.connected(0, site);
	}
	
	public int numberOfOpenSites()
	{
		return numberOfOpenSites;
	}
	
	/*
	 * convert a 2D location into a 1D representing an array index
	 */
	private int xyTo1D(int row, int col)
	{
		
		if(siteValid(row, col))
		{
		
			int xyTo1D = 0;
			
			xyTo1D = (row - 1) * gridSize + col; //site(1,1) will be represented by index 1 in the array, site(1,2) will be 2
			return xyTo1D;
		} 
		else 
		{
			throw new IllegalArgumentException(String.format("Invalid site, (%s,%s)", row, col));
		}
	}
	
	private boolean siteValid(int row, int col)
	{
		if (row < 1 || row > gridSize) 
		{
			return false;
		}
		if (col < 1 || col > gridSize) 
		{
			return false;
		}
		
		return true;
	}
	
	public boolean percolates()
	{
		//site located at index = 0 and site located at index arraySize-1 are connected, i.e. they are in the same component
		return wQUF.connected(0, arraySize - 1);
	}
	
	private static void main(String[] args)
	{
		//add code for standard input to test code
	}

}
