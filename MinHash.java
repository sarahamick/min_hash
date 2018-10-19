import java.util.*;

public class MinHash{
  /*
  @param: vectors: vectors to hash
  @param: numTries: number of tries to re-hash if it doesn't find the pair
  @param: numHash: number of hash functions to use
  @param: stopPoint: how far into the hash function to go (decimal percentage)
  */
  public static int[] minHash(Vector[] vectors){
    int size = vectors.length;
    int numTries;
    int numHash;
    double stopPoint;

    if(size <= 100)        {numTries = 3; numHash = 1; stopPoint = 1;}
    else if (size <= 1000) {numTries = 6; numHash = 2; stopPoint = 0.75;}
    else if(size <= 10000) {numTries = 12; numHash = 4; stopPoint = 0.65;}
    else if(size <= 500000){numTries = 24; numHash = 6; stopPoint = 0.5;}
    else                   {numTries = 30; numHash = 6; stopPoint = 0.3;}

    int[] result = null;
    while(result == null && numTries > 0){
      result = hashVectors(vectors, numHash, stopPoint);
      numTries--;
    }
    if(result != null){
      if(result[1]<result[0]) { //print in numerical order
        int aux = result[0];
        result[0] = result[1];
        result[1] = aux;
      }
    }
    return result;
  }

  private static int[] hashVectors(Vector[] vectors, int numHash, double stopPoint){

    Map<Double, Bucket> buckets = new HashMap<>();

    Hash[] hashes = new Hash[numHash]; //create all our hash functions
    for(int i = 0; i < numHash; i++){
      Hash hash = new Hash(256);
      hashes[i] = hash;
    }

    for(int i = 0; i < vectors.length; i++){ //go through each of your vectors
      Vector vector = vectors[i]; //get the vector
      int[] hashesOfVector = new int[numHash]; //record which bit was set in the vector for each hash function
      int[] numZerosFound_perHash = new int[numHash]; //record the number zeros found before first 1 for each hash function

      for(int k = 0; k < numHash; k++){ //for each vector, go through each of your hashes
        Hash hash = hashes[k]; //get the hash function

        for(int j = 0; j < (hash.getHashSequence().length*stopPoint); j++){ //for each hash, go through each hash index up to the stop point
          int bitPosition = hash.getHashSequence()[j]; //get the bit position in the hash that we are checking
          if(vector.isBitSet(bitPosition)){ //check if the bit at hash position is set
            hashesOfVector[k] = bitPosition;
            numZerosFound_perHash[k] = j;
            break;
          }
        }
      }
      int numZeros = 0; //add the num zeros for each hash. Larger numbers mean more zeros encountered before the 1 was found
      double bucketNum = 1; //compute the bucket number based on which bit was set in the vector for all hash functions
      for(int x = 0; x < hashesOfVector.length; x++) {
        bucketNum = bucketNum + (hashesOfVector[x] * Math.pow(10, x));
        numZeros += numZerosFound_perHash[x];
      }
      Bucket bucket; //add the vector to the bucket
      if(buckets.keySet().contains(bucketNum)) bucket = buckets.get(bucketNum);
      else bucket = new Bucket(bucketNum, numZeros);
      bucket.addToBucket(vector);
      buckets.put(bucketNum, bucket);
    }
    ArrayList<Bucket> bucketList = new ArrayList<>();
    bucketList.addAll(buckets.values());
    Collections.sort(bucketList); //sort the buckets from most 0s found before first 1
    return findPairs(bucketList); //after hashing into buckets, find the closest pair
  }

  private static int[] findPairs(Collection<Bucket> buckets){
    for(Bucket bucket : buckets) { //start at the last bucket (because the last buckets will have more 0 bits in common)
      if(bucket.getVectors().size()>1){ //if the bucket has at least two vectors inside
        int[] result = bucket.bruteForce(); //run brute force comparisons
        if(result!=null) return result; //if it returns a pair, otherwise go on to the next bucket
      }
    }
    return null;
  }
}
