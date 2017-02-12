//David (Min Xiao) Li, 260564820
import java.util.*;
import java.io.*;

public class KeywordExtractor {
  //main to extract keywords and print to console
  public static void main(String[] args) {
    String dir = args[0]; // name of directory with input files
    HashMap<String, Integer> dfs;
    dfs = readDocumentFrequencies("freqs.txt");
    HashMap<String, Integer> tfs;
    //loops through all 40 documents
    for (int i = 0; i < 40; i++){
      tfs = computeTermFrequencies (dir + "/" + (i+1) + ".txt");
      HashMap<String, Double> tfidf = computeTFIDF (tfs, dfs, 40);
      System.out.println ((i + 1) + ".txt");
      printTopKeywords (tfidf, 5);
      System.out.println ("");
    }

  }
  
  //method to find term frequencies per file
  public static HashMap<String, Integer> computeTermFrequencies(String filename) {
    HashMap<String,Integer> termFrequencies = new HashMap<String,Integer>();
    try {
      //opens file
        BufferedReader in = new BufferedReader (new FileReader (filename));
        Scanner text = new Scanner (in);
        //reads each line, splits it to words, and normalizes in a for loop
        while (text.hasNextLine()) {
          String s = text.nextLine();
          String[] words = s.split (" ");
          String[] wordsNormalized = new String [words.length];
          //adds each word based on if it is there yet
          for (int j = 0; j < words.length; j++){
            wordsNormalized [j] = normalize (words[j]);
            if (termFrequencies.containsKey(wordsNormalized [j]) == false){
              termFrequencies.put(wordsNormalized [j], 1);
            }
            else if (termFrequencies.containsKey(wordsNormalized [j]) == true){
              termFrequencies.put (wordsNormalized [j], termFrequencies.get(wordsNormalized [j]) + 1);
            }
          }
        }
        in.close();
      }
    //catches IO exceptions
    catch (IOException x) {
        System.err.println("There was a problem with " + filename + ".");
      }
    //removes "" characters
    termFrequencies.remove ("");
    return termFrequencies;
  }
  
  //method to read from the document
  public static HashMap<String, Integer> readDocumentFrequencies(String filename) {
    HashMap<String,Integer> documentFrequencies = new HashMap<String,Integer>();
      try {
        //opens file
        BufferedReader in = new BufferedReader (new FileReader (filename));
        Scanner text = new Scanner (in);
        while (text.hasNextLine()) {
          //reads each line of document, splits it to word and frequency
          String s = text.nextLine();
          String[] parts = s.split (" ");
          String[] partsTrimmed = new String [parts.length];
          for (int j = 0; j <parts.length; j++){
            partsTrimmed [j] = parts[j].trim();
          }
          //adds to hashmap
          documentFrequencies.put(partsTrimmed[0], Integer.parseInt(partsTrimmed[1]));
        }
        in.close();
      }
      //catches IO exceptions
      catch (IOException x) {
        System.err.println("There was a problem with " + filename + ".");
      }
      return documentFrequencies;
  }
  
  //method to calculate TFIDF
  public static HashMap<String, Double> computeTFIDF(HashMap<String, Integer> tfs, HashMap<String, Integer> dfs, double nDocs) {
    HashMap<String,Double> tfidf = new HashMap<String,Double>();
    List<String> list = new ArrayList<String>(tfs.keySet());
    for (int i = 0; i < list.size(); i ++){
      //declares variables to make math easier to read, not really needed
      String query = list.get(i);
      int tfsValue = tfs.get(query);
      int dfsValue = dfs.get(query);
      tfidf.put(list.get(i),(tfsValue * Math.log(nDocs/dfsValue)));
    }
    return tfidf;
  }
  
  /**
   * This method prints the top K keywords by TF-IDF in descending order.
   */
  public static void printTopKeywords(HashMap<String, Double> tfidfs, int k) {
    ValueComparator vc =  new ValueComparator(tfidfs);
    TreeMap<String, Double> sortedMap = new TreeMap<String, Double>(vc);
    sortedMap.putAll(tfidfs);
    
    int i = 0;
    for(Map.Entry<String, Double> entry: sortedMap.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();
      
      System.out.println(key + " " + value);
      i++;
      if (i >= k) {
        break;
      }
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

/*
 * This class makes printTopKeywords work. Do not modify.
 */
class ValueComparator implements Comparator<String> {
    
    Map<String, Double> map;
    
    public ValueComparator(Map<String, Double> base) {
      this.map = base;
    }
    
    public int compare(String a, String b) {
      if (map.get(a) >= map.get(b)) {
        return -1;
      } else {
        return 1;
      } // returning 0 would merge keys 
    }
  }