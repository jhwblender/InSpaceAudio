class Vector2{
  public float x, y;
  
  Vector2(float x, float y){
    this.x = x;
    this.y = y;
  }
  Vector2(float x, float y, float z){
    this.x = y;
    this.y = y;
  }  
  
  Vector2 mult(float value){
    return new Vector2(x*value, y*value);
  }
  
  Vector2 add(Vector2 value){
    return new Vector2(x+value.x, y+value.y);
  }
  
  String toString(){
    return "("+str(x)+", "+str(y)+")";
  }
}
