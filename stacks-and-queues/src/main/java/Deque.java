import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
   private int n; // size of the deque
   private Node first; // top of the deque
   private Node last; // bottom of the deque
   
   private class Node {
      Node next;
      Node previous;
      Item item;
   }
   
   // an iterator for deque, does not implement remove()
   private class DequeIterator implements Iterator<Item> {
      private Node current = first;
      
      public boolean hasNext() {
         return current != null;
      }
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
      
      public Item next() {
         if(!hasNext()) {
            throw new NoSuchElementException();
         }
         Item item = current.item;
         current = current.next;
         return item;
      }  
   }
   
   /**
   * construct an empty deque
   */
  public Deque() {
     first = null;
     last = null;
     n = 0;
     
     // review check() method in LinkedStack.java and evaluate if it needs to be implemented
  }

  /**
   * is the deque empty?
   */
  public boolean isEmpty() {
    // return first == null;
     return n == 0;
  }

  /**
   * return the number of items on the deque
   */
  public int size() {
    return n;
  }

  /**
   * add the item to the front
   */
  public void addFirst(Item item) {
     
     if (item == null) {
        throw new NullPointerException();
     }
     
     // add new first and update its current and previous nodes
     Node currentFirst = first; // get the current first node in the deque
     first = new Node();
     first.item = item;
     first.next = currentFirst;
     first.previous = null;
      
     n++;
         
     // check if first was added to an empty deque
     if (n == 1) {
        last = first;
     }
     else {      
        // currentFirst was not null link to new first just added
        currentFirst.previous = first;
     }
  }

  /**
   * add the item to the end
   */
  public void addLast(Item item) {
     
     if (item == null) {
        throw new NullPointerException();
     }
     
     // Add newLast to the deque and update its next and previous nodes
     Node newLast = new Node();
     newLast.item = item;
     newLast.next = null;
     newLast.previous = last;
     
     n++;
     
     // check if newLast was added to an empty deque
     if (n == 1) {
        first = newLast;
     }
     else {
        // there were items in the deque
        last.next = newLast;
     }
     
     last = newLast; // update the last
  }

  /**
   * remove and return the item from the front
   */
  public Item removeFirst() {
    if (isEmpty()) {
       throw new UnsupportedOperationException();
    }
    
    Item item = first.item; // item to return
    first = first.next; // if a only 1 item in deque this will point to null
    
    n--;
          
    if (isEmpty()) {
       last = null;
    } 
    else {
       first.previous = null;
    }
    return item;
  }

  /**
   * remove and return the item from the end
   */
  public Item removeLast() {
     if (isEmpty()) {
        throw new UnsupportedOperationException();
     }
     
     Item item = last.item;
     last = last.previous;
     
     n--;
     
     if (isEmpty()) {
        first = null;
     }
     else {
        last.next = null;
     }
     
     return item;
  }

  /**
   * return an iterator over items in order from front to end
   */
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  /**
   * unit testing (optional)
   */
  public static void main(String[] args)
  {
     Deque<Integer> d = new Deque<Integer>();

     for (int i = 0; i < 5; i++) {
        d.addFirst(i+1);
        // d.addLast(i + 1);
     }
     for (int i = 0; i < 5; i++) {
        System.out.println(d.removeFirst());
        // System.out.println(d.removeLast());
     }
     
  }
}
