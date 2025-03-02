
import processing.core.PApplet;

public class Main extends PApplet {
  public void settings() {
    size(400, 400);
  }
  
  public void setup() {
    background(200);
  }
  
  public void draw() {
    ellipse(width/2, height/2, 50, 50);
  }
  
  public static void main(String[] args) {
    PApplet.main("Main");
  }
}
