import org.junit.Assert;
import org.junit.Test;

public class TestPoint
{

  @Test
  public void antisymmetric1()
  {
    final Point p = new Point(77, 496);
    final Point q = new Point(242, 352);
    final Point r = new Point(145, 245);

    // final double pqSlope = p.slopeTo(q);
    // final double prSlope = p.slopeTo(r);

    final int qrSlopeOrder = p.slopeOrder().compare(q, r);
    final int rqSlopeOrder = p.slopeOrder().compare(r, q);

    Assert.assertEquals(1, qrSlopeOrder);
    Assert.assertEquals(-1, rqSlopeOrder);
  }

  @Test
  public void reflexive1()
  {
    final Point p = new Point(154, 58);
    final Point q = new Point(263, 28);
    Assert.assertEquals(0, p.slopeOrder().compare(q, q));
  }

  @Test
  public void reflexive2()
  {
    final Point p = new Point(15887, 739);
    final Point q = new Point(15896, 21373);
    Assert.assertEquals(0, p.slopeOrder().compare(q, q));
  }

  @Test
  public void transitive1()
  {
    final Point p = new Point(100, 361);
    final Point q = new Point(389, 310);
    final Point r = new Point(229, 322);
    final Point s = new Point(355, 207);

    Assert.assertEquals(1, p.slopeOrder().compare(q, r));
    Assert.assertEquals(1, p.slopeOrder().compare(r, s));
    Assert.assertEquals(1, p.slopeOrder().compare(q, s));
  }

  @Test
  public void sign_of_compare1()
  {
    final Point p = new Point(254, 251);
    final Point q = new Point(45, 355);
    final Point r = new Point(362, 137);
    Assert.assertEquals(1, p.slopeOrder().compare(q, r));
  }

  @Test
  public void sign_of_compare2()
  {
    final Point p = new Point(13207, 1243);
    final Point q = new Point(3596, 15623);
    final Point r = new Point(20102, 23283);
    Assert.assertEquals(-1, p.slopeOrder().compare(q, r));
  }

  @Test
  public void sign_of_compare3()
  {
    final Point p = new Point(3, 2);
    final Point q = new Point(4, 3);
    final Point r = new Point(5, 4);
    Assert.assertEquals(0, p.slopeOrder().compare(q, r));
  }

}
