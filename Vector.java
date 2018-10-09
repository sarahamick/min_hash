public class Vector{

  private long[] data = new long[4];
  private int position;

  public Vector(int position, long x1, long x2, long x3, long x4){
    this.position = position;
    data[0] = x1;
    data[1] = x2;
    data[2] = x3;
    data[3] = x4;
  }

  public int similarity(Vector that){
    int sim = 0;
    for(int k = 0; k < 4; k++){
      sim += Long.bitCount(this.data[k] & that.data[k]);
    }
    return sim;
  }

  public boolean isBitSet(int pos){
    int part = pos / 64;
    int newpos = pos % 64;
    return (data[part] & (1l << (63 - newpos))) > 0;
  }

  public int getPosition(){
    return position;
  }
}
