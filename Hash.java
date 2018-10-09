import java.util.*;

public class Hash{

  private int[] hashSequence;

  public Hash(int size){
      hashSequence = new int[size];
      fillHash();
      shuffleHash();
  }

  public int[] getHashSequence(){
    return hashSequence;
  }

  private void fillHash(){
    for(int i = 0; i < hashSequence.length; i++) hashSequence[i] = i;
  }

  //Implementation of the Fisher–Yates shuffle array function taken from:
  //https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
  private  void shuffleHash(){
    int index;
    Random random = new Random();
    for (int i = hashSequence.length - 1; i > 0; i--){
      index = random.nextInt(i + 1);
      if (index != i){
          hashSequence[index] ^= hashSequence[i];
          hashSequence[i] ^= hashSequence[index];
          hashSequence[index] ^= hashSequence[i];
      }
    }
  }
}
