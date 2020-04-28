class SineWave extends AudioSource{
  float frequency;
  float amplitude = 1;
  
  SineWave(float frequency){
    this.frequency = frequency;
  }
  SineWave(float frequency, float amplitude){
    this.frequency = frequency;
    this.amplitude = amplitude;
  }
  
  float amplitude(float time){
    return (time>=0)? amplitude*sin(time*frequency*2*PI) : 0;
  }
}
