import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SolverTestClient
{

  public static void main(final String[] args)
  {

    // create initial board from file
    final In in = new In(args[0]);
    final int n = in.readInt();
    final int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
    {
      for (int j = 0; j < n; j++)
      {
        blocks[i][j] = in.readInt();
      }
    }
    final Board initial = new Board(blocks);

    // solve the puzzle
    final Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
    {
      StdOut.println("No solution possible");
    }
    else
    {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (final Board board: solver.solution())
      {
        StdOut.println(board);
      }
    }
  }

}
