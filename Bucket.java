
import java.util.*;

public class Bucket implements Comparable<Bucket>{

  private static final int THRESHOLD = 70;
  private List<Vector> vectors;
  private double hashNum;
  private int numZerosBeforeFirstOne;

  public Bucket(double hashNum, int numZerosBeforeFirstOne){
    vectors = new ArrayList<>();
    this.hashNum = hashNum;
    this.numZerosBeforeFirstOne = numZerosBeforeFirstOne;
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

  public double getHashNum(){
    return hashNum;
  }
  public int getNumZerosBeforeFirstOne(){
    return numZerosBeforeFirstOne;
  }

  @Override
  public int compareTo(Bucket that){
    if(this.numZerosBeforeFirstOne == that.getNumZerosBeforeFirstOne()) return 0;
    else if(this.numZerosBeforeFirstOne > that.getNumZerosBeforeFirstOne()) return -1;
    else return 1;

  }
}
