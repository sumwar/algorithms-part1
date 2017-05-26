import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>
{
   private Item[] rq; // array of items in the queue
   private int n; // number of elements in the queue 
 
   /**
   * construct an empty randomized queue
   */
  public RandomizedQueue() {
     rq = (Item[]) new Object[2]; // array of items in queue
     n = 0; // size of queue  
  }
  
  /**
   * is the queue empty?
   */
  public boolean isEmpty() {
    return n == 0;
  }

  /**
   * return the number of items on the queue
   */
  public int size() {
    return n;
  }

  /**
   * add the item
   */
  public void enqueue(Item item) {
     if (item == null) {
        throw new NullPointerException();
     }
     
     // resize size (repeated doubling)
     if (n == rq.length) {
        resize(2 * rq.length);
     }

     rq[n++] = item;
  }

  /**
   * remove and return a random item
   */
  public Item dequeue()
  {
    if (n == 0) {
       throw new NoSuchElementException();
    }
    
    //shuffle the queue, pick last item and reduce queue size if needed
    StdRandom.shuffle(rq, 0, n);
    Item item = rq[n - 1];
    rq[n - 1] = null;
    n--;
    if (n > 0 && n == rq.length/4) {
       resize(rq.length/2);
    }
    
    return item;
  }

  /**
   * return (but do not remove) a random item
   */
  public Item sample()
  {
     if (n == 0) {
        throw new NoSuchElementException();
     }
         
     return rq[StdRandom.uniform(n)];
  }

  /**
   * return an independent iterator over items in random order
   */
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }
  
  // randomized queue iterator
  private class RandomizedQueueIterator implements Iterator<Item> {
     
     private int size;
     private int qi;
     private Item[] ri;
     
     
     // constructor for the iterator
     public RandomizedQueueIterator() {
        size = n;
        ri = (Item[]) new Object[size];
        
        // populate iterator array 
        for (int i = 0; i < size; i ++) {
           ri[i] = rq[i];
        }
        
        StdRandom.shuffle(ri); // shuffle objects in the queue
     }
     
     public boolean hasNext() {
        return qi < size; // queue index is less than array size
     }
     
     public Item next() {
        if (!hasNext()) {
           throw new NoSuchElementException();
        }
        
        return ri[qi++]; // get value at qi and then increment      
     }
     
     // not supported
     public void remove() {
        throw new UnsupportedOperationException();
     }   
  }
  
  /**
   * resize array
   */
  private void resize(int capacity) {
     Item[] copy = (Item[]) new Object[capacity];
     for (int i = 0; i < n; i++) {
        copy[i] = rq[i];
     }
     
     rq = copy;  
  }
  
}
