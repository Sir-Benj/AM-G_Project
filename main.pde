Button control;
Button[] btns;
Button[][] buttonMenu;

Menu menu;

PGraphics layer;

public void settings()
{
  size(1000, 800);
}

void setup()
{
  frameRate(60);
  noSmooth();
  surface.setResizable(true);
  background(255, 255, 255, 255);

  menu = new Menu();
  menu.InitialiseMenu();
}

void mousePressed()
{

}

void mouseDragged()
{

}


void draw()
{
  menu.DrawMenu();
}
