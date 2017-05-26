import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

  public static void main(String[] args) {
     
     /*
      * code for unit testing. Commented for final submission 
     int k = StdIn.readInt();
       
     String filename = "C:/Users/musta/Documents/GitHub/"
           + "algorithms-part1/stacks-and-queues/"
           + "src/test/resources/queues/"
           + "Tale.txt"; //args[0]
         
     In in = new In(filename);
      */
     
     // for submission
     int k = Integer.parseInt(args[0]);
     RandomizedQueue<String> rq = new RandomizedQueue<>();
     
     while(!StdIn.isEmpty()) {
        String string = StdIn.readString();
        rq.enqueue(string);
     }
     
     if (k > 0) {
        for (String s : rq) {
           StdOut.println(s);
           k--;
           if (k == 0) {
              break;
           }
        } 
     }   
  }

}
