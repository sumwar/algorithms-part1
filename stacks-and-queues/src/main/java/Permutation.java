import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

  public static void main(String[] args) {
         
     int k = StdIn.readInt();
     
     // unit testing with files  
     String filename = "C:/Users/musta/Documents/GitHub/"
           + "algorithms-part1/stacks-and-queues/"
           + "src/test/resources/queues/"
           + "Tale.txt"; //args[0]
         
     In in = new In(filename);
    
     RandomizedQueue<String> rq = new RandomizedQueue<>();
     
     while(!in.isEmpty()) {
        String string = in.readString();
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
