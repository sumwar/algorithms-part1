import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats
{
   private double[] results;
   private int gridSize;
  
  /**
   * perform trials independent experiments on an n-by-n grid
   */
  public PercolationStats(int n, int trials)
  {
     if (n < 1)
     {
        throw new IllegalArgumentException("Invalid Grid Size " + n);
     }
     if (trials < 1)
     {
        throw new IllegalArgumentException("Invalid number of trials " + trials);
     }
     
     results = new double[trials];
     gridSize = n;
     runTrials();
  }
  
  private void runTrials()
  {
     for (int i = 0; i < results.length; i++)
     {
        Percolation p = new Percolation(gridSize);
        // run until system percolates
        do
        {
           int row = randomSite();
           int col = randomSite();
           p.open(row, col);
        } while (!p.percolates());
        results[i] = p.numberOfOpenSites() / (double) (gridSize * gridSize); 
     }
  }
  
  /**
   * sample mean of percolation threshold
   */
  public double mean()
  {
     return StdStats.mean(results);
  }

  /**
   * sample standard deviation of percolation threshold
   */
  public double stddev()
  {
     return StdStats.stddev(results);
     // handle when std dev is undefined, i.e. trials = 1
     // return Double.NaN;
  }

  /**
   * low endpoint of 95% confidence interval
   */
  public double confidenceLo()
  {
    return mean() - 1.96 * stddev() / Math.sqrt(results.length);
  }

  /**
   * high endpoint of 95% confidence interval
   */
  public double confidenceHi()
  {
     return mean() +  1.96 * stddev() / Math.sqrt(results.length);
  }
  
  /**
   * randomly generate a site row or column index
   */
  private int randomSite()
  {
     return StdRandom.uniform(gridSize) + 1;
  }

  /**
   * test client (described below)
   */
  public static void main(String[] args)
  {
     int n = Integer.parseInt(args[0]);
     int trials = Integer.parseInt(args[1]);
     PercolationStats exp = new PercolationStats(n, trials);
     System.out.println("mean                    = " + exp.mean());
     System.out.println("stddev                  = " + exp.stddev());
     System.out.println("95% confidence interval = [" + exp.confidenceLo() + ", " + exp.confidenceHi() + "]");
  }
  
}
