
public class Main{
  public static void main(String[] args){
    Vector[] vectors = FileReader.readIn();

    int[] pair = MinHash.minHash(vectors);

    if(pair == null) System.out.println("Could not locate pair");
    else System.out.println(pair[0] + " " + pair[1]);
  }
}
