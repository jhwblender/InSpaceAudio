class Room{
  Vector3 roomSize;
  ArrayList<Speaker> speakers; //speakers in room
  
  Room(Vector3 roomSize){
    this.roomSize = roomSize;
    speakers = new ArrayList<Speaker>();
  }
  
  int addSpeaker(Vector3 loc){
    speakers.add(new Speaker(loc));
    return speakers.size()-1;
  }
  Speaker getSpeaker(int speakerNum){
    return speakers.get(speakerNum);
  }
  
  void draw(Vector2 loc, Vector2 size, float time){
    float scaleX = size.x/roomSize.x;
    float scaleY = size.y/roomSize.y;
    float scale = (scaleX < scaleY)? scaleX : scaleY;
    
    for(int py = (int)loc.y; py < (int)loc.y+size.y; py++){
      for(int px = (int)loc.x; px < (int)loc.x+size.x; px++){
        Vector3 pointLoc = new Vector3((px-loc.x)/scale, (py-loc.y)/scale);
        stroke(128*getPointAmplitude(pointLoc, time)+128);
        point(px,py);
      }
    }
  }
  
  float getPointAmplitude(Vector3 loc, float time){
    float sum = 0;
    for(int i = 0; i < speakers.size(); i++)
      sum += speakers.get(i).getAmplitude(loc, time);
    return sum/speakers.size();
  }
}
