class PaintUI extends PApplet
{
  PaintUI()
  {
    super();
    PaintUI.runSketch(new String[] {this.getClass().getSimpleName()}, this);
  }

  void settings()
  {
    size(500, 200);
  }

  void setup()
  {
    surface.setTitle("Illustration Tools");
    background(150);
  }

  void mousePressed()
  {
    println("Mouse pressed in Paint UI");
  }

  void exit()
  {
    dispose();
  }
}
