//David (Min Xiao) Li, 260564820
import java.util.*;
import java.io.*;

public class DocumentFrequency {
  
  //main to write to document
  public static void main(String[] args) {
    String dir = args[0]; // name of directory with input files
    HashMap<String, Integer> dfs;
    dfs = extractDocumentFrequencies(dir, 40);
    writeDocumentFrequencies(dfs, "freqs.txt");
  }
  
  //method to extract individual frequencies
  public static HashMap<String,Integer> extractDocumentFrequencies (String directory, int ndoc){
    HashMap<String,Integer> documentFrequencies = new HashMap<String,Integer>();
    //checks every file
    for (int i = 0; i < ndoc; i++){
      String filename = directory + "/" + (i+1) + ".txt";
      HashSet<String> currentDoc = new HashSet<String>();
      //gets a list of word from every file
      currentDoc = extractWordsFromDocument (filename);
      for (String s : currentDoc){
        //adds said words to the total document frequency hashmap
        if (documentFrequencies.containsKey(s) == false){
          documentFrequencies.put(s, 1);
        }
        else if (documentFrequencies.containsKey(s) == true){
          documentFrequencies.put(s, documentFrequencies.get(s)+1);
        }
      }
    }
    return documentFrequencies;
  }
  
  //method to extract unique words from each file
  public static HashSet<String> extractWordsFromDocument(String filename) {
    HashSet<String> vocabulary = new HashSet<String>();
    //file IO
    try{
      BufferedReader in = new BufferedReader (new FileReader (filename));
      Scanner text = new Scanner (in);
      //splits lines to get each word, then normalizes
      while (text.hasNextLine()) {
        String s = text.nextLine();
        String words[] = s.split(" ");
        String wordsNormalized[] = new String[words.length];
        //adds the word if it isn't present yet
        for (int i =0; i < words.length; i++){
          wordsNormalized [i] = normalize (words[i]);
          if (vocabulary.contains (wordsNormalized[i]) == false){
            vocabulary.add(wordsNormalized[i]);
          }
        }
      }
      in.close();
    }
    //checks for IO errors
    catch (IOException x) {
      System.err.println("There was a problem with " + filename + ".");
    }
    vocabulary.remove("");
    return vocabulary;
  }
  
  //method to print 
  public static void writeDocumentFrequencies(HashMap<String, Integer> dfs, String filename) {
    //converts the hashmap to an ordered arraylist
    List<String> list = new ArrayList<String>(dfs.keySet());
    Collections.sort (list);
    //file IO
    try{
      PrintWriter out = new PrintWriter (new BufferedWriter (new FileWriter (filename)));
      for (int i = 0; i < list.size(); i++){
        //print the list followed by the numerical value associated with the key in the hashmap
        String s = list.get(i);
        out.println (s + " " + dfs.get(s));
      }
      out.close();
    }
    //catches IO errors
    catch (IOException x) {
      System.err.println("There was a problem with " + filename + ".");
    }
  }
  
  /*
   * This method "normalizes" a word, stripping extra whitespace and punctuation.
   * Do not modify.
   */
  public static String normalize(String word) {
    return word.replaceAll("[^a-zA-Z ']", "").toLowerCase();
  }
  
}