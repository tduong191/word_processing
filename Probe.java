import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * An incomplete Hashtable using probing. The get and put functions are stubs only.
 * They must be implemented. A completed implementation need not handle
 * deletions.  Therefore, the implementation need not worry about tombstones,
 * rehashing to reduce the size of the table, or rehashing to reduce the number 
 * of tombstones
 * 
 * @param <K> the type of key
 * @param <V> the type of value
 * @author gtowell 
 * Created:  Sep 27, 2020 
 * Modified Oct 2, 2020
 * Modified: Sep 27, 2021
 * @author trang
 * Modified: Oct 6, 2021 to add get, put functions, and main method for storing unique words and
 * their occurences of a file using Linear Probing
 */
public class Probe<K, V> implements Map151Interface<K, V> {

    /**
     * Small inner class to group together key,value pairs
     */
    protected class Pair<L, W> {
        /** The key, cannot be changed */
        final L key;
        /**
         * The value. It can be changed as a second put with the key will change the
         * value
         */
        W value;

        /**
         * Initialize the node
         */
        public Pair(L ll, W ww) {
            key = ll;
            value = ww;
        }

        /** Print the node, and all subsequent nodes in the linked list */
        public String toString() {
            return "<" + key + ", " + value + ">";
        }
    }

    /** A Constant .. One of the cases in which static are acceptable
     * This one specifies the maximum number of tombstones allowed before 
     * rehashing for tombstone accumulation
     */
    /** When the hashtable needs to grow, by what factor should it grow */
    private static final double GROWTH_RATE = 2.0;
    /** How full the table should be before initiating rehash/growth */
    private static final double MAX_OCCUPANCY = 0.60;
    /** The default size of the backing array */
    private static int DEFAULT_CAPACITY = 1009;
   /** The array in which the hashtable stores data */
    private Pair<K, V>[] backingArray;
    /** The number of active items in the hashtable */
    private int itemCount;
    
 
    /** Default initialization */
    public Probe() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Initialize a hashtable of the given size
     *
     * @param size the size of the hashtable to create
     */
    @SuppressWarnings("unchecked")
    public Probe(int size) {
        // Cannot make an array in which you mention a parameterized type.
        // So just make the generic array. This is a narrowing cast so it does not
        // even need to be explicitly case.
        backingArray = new Pair[size];
        itemCount = 0;
     }

    /**
     * The hash function. Just uses the java object hashvalue. 
     * @param key the Key to be hashed
     * @return the hash value
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % backingArray.length;
    }

    /**
     * The number of active items in the hashtable
     * @return The number of active items in the hashtable
     */
    public int size() {
        return itemCount;
    }

    /**
     * Add a key-value pair to the hashtable. If the key is already in the
     * hashtable, then the old value is replaced. Otherwise this adds a new
     * key-value pair
     * Be sure to update itemCount as needed.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(K key, V value) {
        int loc = hash(key);
        if (itemCount < DEFAULT_CAPACITY*MAX_OCCUPANCY) { // only add if there is space
            while (true) {
                if (backingArray[loc] == null) { // if there is empty space at the hashed location, add the pair
                    backingArray[loc] = new Pair<K, V>(key, value);
                    itemCount++; // increment the number of items after added
                    break;
                } else if (backingArray[loc].key.equals(key)) { // if the pair at the location has the same key, update its value
                    backingArray[loc].value = value;
                    break;
                }  // if the location is occupied by a different key, check the next location
                loc = (loc + 1) % backingArray.length;
            }
        } else { // if runs out of space, make more space
            int grow = (int) GROWTH_RATE;
            DEFAULT_CAPACITY = grow*DEFAULT_CAPACITY;
            rehash(DEFAULT_CAPACITY);
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * Rehash the current table. This should be done rarely as it is expensive
     * @param newSize the size of the table after rehashing
     */
    private void rehash(int newSize) {
        Pair<K, V>[] oldArray = backingArray;
        itemCount = 0;
        backingArray = new Pair[newSize];
        for (int i = 0; i < oldArray.length; i++) {
            if (oldArray[i] != null) {
                put(oldArray[i].key, oldArray[i].value);
            }
        }
    }

    /**
     * Get the value associated with the key
     * @param key the key whose value is sought
     * @return the associated value, or null
     */
    public V get(K key) {
        int loc = hash(key);
        int counter = 0;

        while (backingArray[loc] != null) { // check if there is a pair at the location
            if (counter++ > DEFAULT_CAPACITY) // to avoid infinite loop
                return null;

            // if key found return its value
            if (backingArray[loc].key.equals(key)) { 
                return backingArray[loc].value; 
            }
            // if key not found, check the next location
            loc = (loc + 1) % backingArray.length;
        }

        // If key not found return null
        return null;
    }


    @Override
    /**
     * Does the hashtable contain the key
     * @param key the key
     * @return true iff the key is in the hashtable
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }


    @Override
    /**
     * The complete set of keys active in the hashtable.
     * @return a set containing all of the keys in the hashtable
     */
    public Set<K> keySet() {
        TreeSet<K> set = new TreeSet<>();
        for (Pair<K,V> pr : backingArray) {
            if (pr!=null) {
                set.add(pr.key);
            }
        }
        return set;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Pair<K, V> pair : backingArray) {
            if (pair != null) {
                sb.append("[" + pair.key + ":" + pair.value + "]\n");
            }
        }
        return sb.toString();
    }

    /**
     *
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis(); // Record the time that the program starts
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String filename = sc.nextLine();  // Read filename that is entered
        MyReader reader = new MyReader(filename);
        Probe store = new Probe();

        /**
        * loop to count the number of occurences of each unique word
        **/
        while (true) {
            String line = reader.nextWord();
            if (line == null) { // break out of the loop if all words have been read
                break; 
            }
            if (store.containsKey(line)) { // if the word has already been counted, increment its count by 1
                store.put(line,(Integer) store.get(line) + 1); 
            } else {
                store.put(line, 1); // if the word hasn't been counted, store the word and the count equals 1
            }
        }

        long end = System.currentTimeMillis(); // Record the time that the program ends
        System.out.println("Probe takes " + (end - start) + "ms"); // Print the time that the program takes to run
        }
    }





