import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{

  /**
   * test client (described below)
   */
  public static void main(final String[] args)
  {
    final int n = Integer.parseInt(args[0]);
    final int trials = Integer.parseInt(args[1]);
    final PercolationStats stats = new PercolationStats(n, trials);
    System.out.println(stats);
  }

  private final int gridSize;
  private final double[] x;

  /**
   * perform trials independent experiments on an n-by-n grid
   */
  public PercolationStats(final int n, final int trials)
  {
    if (n < 1)
    {
      throw new IllegalArgumentException("Invalid grid size, " + n);
    }
    if (trials < 1)
    {
      throw new IllegalArgumentException("Invalid trials, " + trials);
    }

    gridSize = n;
    x = new double[trials];
    performTrials();
  }

  /**
   * high endpoint of 95% confidence interval
   */
  public double confidenceHi()
  {
    return mean() + d();
  }

  /**
   * low endpoint of 95% confidence interval
   */
  public double confidenceLo()
  {
    return mean() - d();
  }

  /**
   * sample mean of percolation threshold
   */
  public double mean()
  {
    return StdStats.max(x);
  }

  /**
   * sample standard deviation of percolation threshold
   */
  public double stddev()
  {
    return StdStats.stddev(x);
  }

  @Override
  public String toString()
  {
    return String.format("mean                    = %.16f%n"
                         + "stddev                  = %.16f%n"
                         + "95%% confidence interval = [%.16f, %.16f]",
                         mean(),
                         stddev(),
                         confidenceLo(),
                         confidenceHi());
  }

  private double d()
  {
    final int trials = x.length;
    return 1.96 * stddev() / Math.sqrt(trials);
  }

  private void performTrials()
  {
    for (int trial = 0; trial < x.length; trial++)
    {
      final int size = gridSize * gridSize;
      final Percolation percolation = new Percolation(gridSize);
      for (int i = 0; i < size; i++)
      {
        // Open a random location
        do
        {
          final int row = randomInSize();
          final int col = randomInSize();
          if (percolation.isOpen(row, col))
          {
            continue;
          }
          else
          {
            percolation.open(row, col);
            break;
          }
        } while (true);
        // Check percolation
        if (percolation.percolates())
        {
          x[trial] = (i + 1) / (double) size;
          break;
        }
      }
    }
  }

  private int randomInSize()
  {
    final int min = 1;
    final int max = gridSize;
    return min + (int) (StdRandom.uniform() * (max - min + 1));
  }

}
