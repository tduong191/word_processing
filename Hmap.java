import java.util.Scanner;
import java.util.HashMap;

/**
 * Only uses main method to store unique words and their occurences of a file using HashMap from java.util
 * 
 * @author trang
 */
public class Hmap {
    public static void main(String[] args) {
        long start = System.currentTimeMillis(); // Record the time that the program starts
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String filename = sc.nextLine();  // Read filename that is entered
        MyReader reader = new MyReader(filename);
        HashMap store = new HashMap();

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
        System.out.println("HashMap takes " + (end - start) + "ms"); // Print the time that the program takes to run
        }
}
