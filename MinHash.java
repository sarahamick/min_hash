import java.util.*;

public class MinHash{
  /*
  @param: vectors: vectors to hash
  @param: numTries: number of tries to re-hash if it doesn't find the pair
  @param: numHash: number of hash functions to use
  @param: stopPoint: how far into the hash function to go
  */
  public static int[] minHash(Vector[] vectors, int numTries, int numHash, int stopPoint){
    int[] result = null;
    if(result == null && numTries > 0){
      result = hashVectors(vectors, numHash);
      numTries--;
    }
    if(result != null){
      if(result[1]<result[0]) {
        int aux = result[0];
        result[0] = result[1];
        result[1] = aux;
      }
    }
    return result;
  }

  private static int[] hashVectors(Vector[] vectors, int numHash){

    Map<Integer, Bucket> buckets = new HashMap<>();
    Hash[] hashes = new Hash[numHash];
    for(int i = 0; i < numHash; i++){
      Hash hash = new Hash(256);
      hashes[i] = hash;
    }

    for(int i = 0; i < vectors.length; i++){ //go through each of your vectors
      Vector vector = vectors[i]; //get the vector
      int[] hashesOfVector = new int[numHash]; //for each hash function, record which bit was set for the vector

      for(int k = 0; k < numHash; k++){ //for each vector, go through each of your hashes
        Hash hash = hashes[k]; //get the hash function

        for(int j = 0; j < hash.getHashSequence().length; j++){ //for each hash, go through each hash index
          int bitPosition = hash.getHashSequence()[j]; //get the bit position in the hash that we are checking

          if(vector.isBitSet(bitPosition)){ //check if the bit at hash position is set
            hashesOfVector[k] = bitPosition;
            break;
          }
        }
      }
      int bucketNum = 0; //compute the bucket number based on the vectors hashesOfVector for all hash functions
      for(int x = 0; x < hashesOfVector.length; x++) bucketNum += hashesOfVector[x] * 10;
      Bucket bucket;
      if(buckets.keySet().contains(bucketNum)) bucket = buckets.get(bucketNum);
      else bucket = new Bucket(bucketNum);
      bucket.addToBucket(vector);
      buckets.put(bucketNum, bucket);
    }
    return findPairs(buckets);
  }

  private static int[] findPairs(Map<Integer, Bucket> buckets){
    for(int i : buckets.keySet()){ //start at the last bucket (because the last buckets will have more bits in common)
      Bucket bucket = buckets.get(i); //get the bucket
      if(bucket.getVectors().size()>1){ //if the bucket has at least two vectors inside
        int[] result = bucket.bruteForce(); //run brute force comparisons
        if(result!=null) return result; //if it returns a result
      }
    }
    return null;
  }
}
