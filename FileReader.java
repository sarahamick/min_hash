import java.util.*;

public class FileReader{
  public static Vector[] readIn(){
    Vector[] vectors;
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    vectors = new Vector[n];

    int index = 0;
    while(sc.hasNext()){
      int count = 0;
      long[] v = new long[4];
      while(count<4){
        v[count] = sc.nextLong();
        count++;
      }
      vectors[index] = new Vector(index, v[0], v[1], v[2], v[3]);
      index++;
    }
    return vectors;
  }
}
