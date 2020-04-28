int x,y;
int num = 3;

void setup(){
  size(800,600);
  x = width/2;
  y = height/2;
  
  stroke(0);
  fill(0, 0);
  strokeWeight(1);
}

void draw(){
  background(255);
  
  for(int i = 0; i<width*3; i++){
    stroke(0, 125*(sin(10*i*2*PI/(3*width))+1)/num);
    circle(0, height/2, i);
    stroke(0, 125*(sin(10*i*2*PI/(3*width))+1)/num);
    circle(width, height/2, i);
    //stroke(0, 125*(sin(20*i*2*PI/(3*width))+1)/num);
    //circle(width, 0, i);
  }
  if(mousePressed){
    color c = get(mouseX,mouseY);
    println(brightness(c));
  }
}
