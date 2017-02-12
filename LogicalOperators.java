//David (Min Xiao) Li, 260564820
public class LogicalOperators{
  
  //tests conjunction iteratively
  public static boolean conjunctionIter (boolean[] input){
    //default values
    boolean conjunction = input[0];
    int counter = 0;
    //basically just using an infinite loop, letting the caught out of bounds error to break the loop
    while(counter!=-1){
      try {
        conjunction = conjunction && input [counter+1];
        counter++;
      }
      //return statement once all the possible combinations of pairs have been tested
      catch (ArrayIndexOutOfBoundsException aioobe){
        return conjunction;
      }
    }
    //avoids compiler error, will should see this
    return conjunction;
  }
  
  //helper method to test conjunction recursively, testing the current and the next entries in the array
  private static boolean conHelper (boolean[] input, boolean conjunction, int n){
    boolean tempConjunction = conjunction && input[n];
    //recursion after testing the current set to test the next set, throws and error once all possible sets in the array are done
    return tempConjunction && conHelper(input, tempConjunction, n+1);
  }
  
  //tests conjunction iteratively 
  public static boolean conjunctionRec (boolean[] input){
    //default values
    boolean conjunction = input [0];
    //recursion starting to compare each of the possible pairs, starting from input[0] and input[1]
    try {
      conjunction = conHelper (input, conjunction, 1);
    }
    //return statement once all possible combinatinos of paris have been tested 
    catch (ArrayIndexOutOfBoundsException aioobe){
      return conjunction;
    }
    //avoids compiler error, should never see this
    return conjunction;
  }
  
  //tests disjunction iteratively
  public static boolean disjunctionIter (boolean[] input){
    //default values
    boolean disjunction = input[0];
    int counter = 0;
    //basically just using an infinite loop, letting the caught out of bounds error to break the loop
    while(counter!=-1){
      try {
        disjunction = disjunction || input [counter+1];
        counter++;
      }
      //return statement once all the possible combinations of pairs have been tested
      catch (ArrayIndexOutOfBoundsException aioobe){
        return disjunction;
      }
    }
    //avoids compiler error, will should see this
    return disjunction;
  }
  
  //helper method to test disjunction recursively, testing the current and the next entries in the array
  private static boolean disHelper (boolean[] input, boolean disjunction, int n){
    boolean tempDisjunction = disjunction || input[n];
    //recursion after testing the current set to test the next set, throws and error once all possible sets in the array are done
    return tempDisjunction || disHelper(input, tempDisjunction, n+1);
  }
  
  //tests disjunction recursively
  public static boolean disjunctionRec (boolean[] input){
    //default values
    boolean disjunction = input [0];
    //recursion starting to compare each of the possible pairs, starting from input[0] and input[1]
    try {
      disjunction = disHelper (input, disjunction, 1);
    }
    //return statement once all possible combinatinos of paris have been tested 
    catch (ArrayIndexOutOfBoundsException aioobe){
      return disjunction;
    }
    //avoids compiler error, should never see this
    return disjunction;
  }
  
  //testing
  public static void main(String[] args){
    boolean[] whatever = {true, true, false};
    System.out.println (conjunctionIter(whatever));
    System.out.println (conjunctionRec(whatever));
    System.out.println (disjunctionIter(whatever));
    System.out.println (disjunctionRec(whatever));
  }
}