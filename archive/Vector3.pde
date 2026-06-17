class Vector3{
  public float x, y, z;
  
  Vector3(float x, float y){
    this.x = x;
    this.y = y;
    this.z = 0;
  }
  Vector3(float x, float y, float z){
    this.x = x;
    this.y = y;
    this.z = z;
  }  
  
  Vector3 mult(float value){
    return new Vector3(x*value, y*value, z*value);
  }
  
  Vector3 div(float value){
    return new Vector3(x/value, y/value, z/value);
  }
  
  float dist(Vector3 other){
    return sqrt(pow(x-other.x,2)+pow(y-other.y,2)+pow(z-other.z,2));
  }
  
  String toString(){
    return "("+str(x)+", "+str(y)+", "+str(z)+")";
  }
}
