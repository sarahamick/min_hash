
import java.util.*;

public class Bucket{

  private static final int THRESHOLD = 70;

  private List<Vector> vectors;
  private int hashNum;

  public Bucket(int hashNum){
    vectors = new ArrayList<>();
    this.hashNum = hashNum;
  }

  public void addToBucket(Vector vector){
    vectors.add(vector);
  }

  public int[] bruteForce(){
    for(Vector vector1 : vectors){
      for(Vector vector2 : vectors){
        if(vector1.getPosition()!=vector2.getPosition()) {//make sure we're not comparing the same vector
          if(vector1.similarity(vector2)>=THRESHOLD){ //if they have over 70 1s in common
            int[] result = new int[2];
            result[0] = vector1.getPosition();
            result[1] = vector2.getPosition();
            return result;
          }
        }
      }
    }
    return null;
  }

  public List<Vector> getVectors(){
    return vectors;
  }

  public int getHashNum(){
    return hashNum;
  }
}
