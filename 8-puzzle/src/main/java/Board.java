public class Board
{

  /**
   * construct a board from an n-by-n array of blocks (where
   * blocks[i][j] = block in row i, column j)
   */
  public Board(final int[][] blocks)
  {
  }

  /**
   * board dimension n
   */
  public int dimension()
  {
    return 0;
  }

  /**
   * does this board equal y?
   */
  @Override
  public boolean equals(final Object y)
  {
    return false;
  }

  /**
   * number of blocks out of place
   */
  public int hamming()
  {
    return 0;
  }

  /**
   * is this board the goal board?
   */
  public boolean isGoal()
  {
    return false;
  }

  /**
   * sum of Manhattan distances between blocks and goal
   */
  public int manhattan()
  {
    return 0;
  }

  /**
   * all neighboring boards
   */
  public Iterable<Board> neighbors()
  {
    return null;
  }

  /**
   * string representation of this board (in the output format specified
   * below)
   */
  @Override
  public String toString()
  {
    return null;
  }

  /**
   * a board that is obtained by exchanging any pair of blocks
   */
  public Board twin()
  {
    return null;
  }

}
