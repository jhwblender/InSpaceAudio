private static final float speedOfSound = 343.3; //m/s
Room room;
float time = 0;

void setup(){
  surface.setResizable(true); 
  //surface.setSize((int)(displayWidth*0.75), (int)(displayHeight*0.75));
  surface.setSize(100,100);
  surface.setLocation((int)(displayWidth*1.125), (int)(displayHeight*0.125));
  
  room = new Room(new Vector3(6, 6, 3));
  room.addSpeaker(new Vector3(6, 3, 0));
  room.addSpeaker(new Vector3(0, 3, 0));
  room.getSpeaker(0).addSource(new SineWave(300));
  room.getSpeaker(1).addSource(new SineWave(600));
}

void draw(){
  println(time);
  room.draw(new Vector2(0,0),new Vector2(width, height), time);
  time += 0.0001;
}
