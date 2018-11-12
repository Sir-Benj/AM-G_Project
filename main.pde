//ImageUI win;
//PaintUI win2;
Button btn1;
Button btn2;
SmoothButton btn3;

public void settings()
{
  size(600, 600);
}

void setup()
{
  surface.setResizable(true);
  background(255, 255, 255, 255);
  btn1 = new Button(10, 10, 50, "paint");
  btn2 = new Button(10, 70, 50, "erase");
  btn3 = new SmoothButton(10, 130, 50, 8, "smooth");
}

void mousePressed()
{
  btn1.buttonPressed(btn2);
  btn2.buttonPressed(btn1);
  btn3.buttonPressed(btn1);


  if (btn1.changeState && !btn2.changeState)
  {
    stroke(0);
    fill(0);
    ellipse(mouseX, mouseY, 20, 20);
  }
  if (btn2.changeState && !btn1.changeState)
  {
    stroke(255);
    fill(255);
    ellipse(mouseX, mouseY, 20, 20);
  }
}

void mouseDragged()
{
  if (btn1.changeState() && !btn2.changeState)
  {
    stroke(0);
    fill(0);
    ellipse(mouseX, mouseY, 20, 20);
  }
  if (btn2.changeState() && !btn1.changeState)
  {
    stroke(255);
    fill(255);
    ellipse(mouseX, mouseY, 20, 20);
  }
}

void draw()
{
  btn1.displayButton();
  btn2.displayButton();
  btn3.displayButton();
}
