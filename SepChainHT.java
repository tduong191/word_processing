import java.math.BigInteger;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * A fairly basic implementation of a separate chanining hashtable
 * @param <K> the tpe of key
 * @param <V> the type of value
 * Implements full separate chaining, but not rehashing.
 * Similarly, the size of the underlying table, once it is created, cannot
 * be changed.
 * @author gtowell
 * Created: April 25, 2020
 * Modified: Sep 23, 2020 to use ArrayList
 * Modeifed: Mar 6, 2021 to use Map206
 * Modified: Sep 27, 2021 to use Map151Interface
 * @author trang
 * Modified: Oct 6, 2021 to add main method for storing unique words and their occurences of a file 
 * using SepChain 
 */
public class SepChainHT<K,V> implements Map151Interface<K,V> {

    
    /** The array holding the hashtable data.  Yes, this is an array
     * of Map151 objects!!
     */
    private Map151<K,V>[] backingArray;

    /** The default size of the backing array */
    private static int DEFAULT_CAPACITY = 1009;

    /** The number of items in the hashtable */
    private int count;

    /** Default initialization */
    public SepChainHT() {
        this(DEFAULT_CAPACITY);
    }
    /**
     * Initialize a hashtable of the given size
     * @param size the size of the hashtable to create
     */
    @SuppressWarnings("unchecked")
    public SepChainHT(int size) {
        // Cannot make an array object in which you mention a parameterized type.
        // So just make the generic array.  This is a narrowing cast so it does not 
        // even need to be explicit.
        count = 0;
        backingArray = new Map151[size];
    }

    public BigInteger objectHasher(Object ob) {
        return stringHasher(ob.toString());
    }
    /**
     * Implemets Horner's on strings.
     * Since every object can be translated into a string This can be run
     * on an arbitrary object with no loss of generality.
     * @param ss the string to generate a hash value for
     * @return the hash value
     */
    public BigInteger stringHasher(String ss) {
        BigInteger mul = BigInteger.valueOf(23);
        BigInteger ll = BigInteger.valueOf(0);
        for (int i=0; i<ss.length(); i++) {
            ll = ll.multiply(mul);
            ll = ll.add(BigInteger.valueOf(ss.charAt(i)));
        }
        return ll;
    }

    private int h(K k) {
        return objectHasher(k).mod(BigInteger.valueOf(backingArray.length)).intValue();
    }

    /**
     * Add a key-value pair to the hashtable.  If the key is already in the
     * hashtable, then the old value is replaced.  Otherwise this adds a
     * new key-value pair
     * @param key the key
     * @param value the value
     */
    @Override
    public void put(K key, V value) {
        int loc = h(key);
        if (backingArray[loc] == null) {
            backingArray[loc] = new Map151<>();
        }
        if (!backingArray[loc].containsKey(key)) {
            count++;
        }
        backingArray[loc].put(key, value);
    }

    /**
     * Get the value stored in the hashtable given the key.
     * @param key the key 
     * @return the value associated with the key
     */
    @Override
    public V get(K key) {
        int loc = h(key);
        if (backingArray[loc]==null) {
            return null;
        }
        return backingArray[loc].get(key);
    }

    /**
     * The number of distinct keys in the hshtable.
     * @return The number of distinct keys in the hashtable
     */
    @Override
    public int size() {
        return count;
    }


    @Override
    public boolean containsKey(K key) {
        int loc = h(key);
        if (backingArray[loc] == null) {
            return false;
        }
        return backingArray[loc].containsKey(key);
    }

    @Override
    public Set<K> keySet() {
        TreeSet<K> set = new TreeSet<>();
        for (int i = 0; i < backingArray.length; i++) {
            if (backingArray[i] != null)
                set.addAll(backingArray[i].keySet());
        }
        return set;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < backingArray.length; i++) {
            if (backingArray[i] != null) {
                sb.append(i);
                sb.append(" ");
                sb.append(backingArray[i].toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis(); // Record the time that the program starts
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String filename = sc.nextLine();  // Read filename that is entered
        MyReader reader = new MyReader(filename);
        SepChainHT store = new SepChainHT<>();

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
        System.out.println("SepChain takes " + (end - start) + "ms"); // Print the time that the program takes to run
    }
}

