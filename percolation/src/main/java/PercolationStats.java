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
    return String.format("mean                    = %0.16f%n"
                         + "stddev                  = %0.16f%n"
                         + "95% confidence interval = [%0.16f, %0.16f]",
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
    for (int i = 0; i < x.length; i++)
    {
      final int size = i * i;
      final Percolation percolation = new Percolation(gridSize);
      for (int j = 0; j < size; j++)
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
          x[i] = (j + 1) / (double) gridSize;
          break;
        }
      }
    }
  }

  private int randomInSize()
  {
    final int min = 1;
    final int max = gridSize;
    return min + (int) (StdRandom.gaussian() * (max - min + 1));
  }

}
