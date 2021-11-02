import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An object that reads a txt file, store each word in an ArrayList, and return 
 * the next word of the file when the nextWord() function is called.
 * 
 * @param filename the name of the file
 * @author gtowell 
 * Created:  Oct 6, 2021
 */

public class MyReader {
    protected String filename;
    private int numline = 0; // the counter that keeps track of words returned
    private ArrayList<String> wordCollection; //The underlying ArrayList to store each word

    /**
     * Initialize a MyReader with a filename 
     *
     * @param filename the name of the file
     */
    public MyReader(String filename) {
        this.wordCollection = this.storeFile(filename);
    }

    /**
     * Give the next word in the file. If reaches the end of the file, return null
     * 
     * @return the next word or null
     */
    public String nextWord() {
        String word = new String();
        if (numline == this.wordCollection.size()) { // if at the end of the file
            return null;
        } else {
            word = this.wordCollection.get(numline); // search the underlying ArrayList for the next word
            counter(); // increase the counter every time the function gives a new word
            return word;
        }
    }

    /* *
    Keep track of how many words have been returned
        */
    private void counter() {
        this.numline++;
    }

    /**
     * Read the given txt file and return its contents as an arraylist of strings
     * 
     * @param filename the file to read
     * @return the arraylist
     */
    private ArrayList<String> storeFile (String filename) {
        ArrayList<String> lines = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename));) {
            while (br.ready()) {
                String l = br.readLine();
                if (l.length() > 0) {
                    lines.add(l);
                }
            }
        } catch (FileNotFoundException fnf) {
            System.err.println("Could not open the file" + fnf);
        } catch (IOException ioe) {
            System.err.println("Reading problem" + ioe);
        }
        return lines;
    }
}




