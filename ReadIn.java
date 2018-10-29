import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;

public class ReadIn{
  public static Vector[] readIn(){

    Vector[] vectors = null;

    try{
      BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
      int n = Integer.parseInt(bf.readLine());
      vectors = new Vector[n];

      int curChar = bf.read();
      int index = 0;
      while(curChar != -1){
        long first, second, third, fourth;

        first = calc(curChar, bf);
        curChar = bf.read();
        second = calc(curChar, bf);
        curChar = bf.read();
        third = calc(curChar, bf);
        curChar = bf.read();
        fourth = calc(curChar, bf);
        curChar = bf.read();

        vectors[index] = new Vector(index, first, second, third, fourth);
        index++;
      }
      bf.close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
    finally{
      return vectors;
    }
  }

  private static long calc(Integer curChar, BufferedReader bf) throws IOException {
      long val = 0;
      boolean negative = false;
      if(curChar == 45) {
        negative = true;
        curChar = bf.read();
      }
      while(48 <= curChar && curChar <= 57) {
        val = val * 10 + (curChar - 48);
        curChar = bf.read();
      }
      if(negative) {val = -val;}
      return val;
  }
}
