class Speaker{
  ArrayList<AudioSource> sources; //sounds to play
  Vector3 loc;
  float volume = 1;
  
  Speaker(Vector3 loc){
    this.loc = loc;
    sources = new ArrayList<AudioSource>();
  }
  
  //adds audio to speaker, returns audio index;
  int addSource(AudioSource source){
    sources.add(source);
    return sources.size()-1;
  }
  
  float getAmplitude(Vector3 point, float time){
    float sum = 0;
    float distDelay = loc.dist(point)/speedOfSound;
    for(int i = 0; i < sources.size(); i++){
      sum += sources.get(i).amplitude(time - distDelay);
    }
    return sum/sources.size();
  }
}
