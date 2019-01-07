Button control;
Button[] btns;
Button[][] buttonMenu;

int screenSizeX = 840, screenSizeY = 640,
    menuSize = 200, topBarSize = 20;

Menu menu;

PGraphics background;
PGraphics layer;

public void settings()
{
  size(3*displayWidth>>2, 3*displayHeight>>2);//screenSizeX + menuSize, screenSizeY + topBarSize);
}

void setup()
{
  frameRate(100);
  noSmooth();
  //surface.setResizable(true);
  background(255);

  background = createGraphics(width - 245, height - 60);//800, 600);
  layer = createGraphics(width - 245, height - 60);//800, 600);
  menu = new Menu();
  menu.InitialiseMenu();
}

void mousePressed()
{
  menu.TopMenuPressed();
  menu.SideMenuPressed();
}

void mouseDragged()
{

}


void draw()
{
  background.beginDraw();
  background.background(255);
  background.endDraw();

  layer.beginDraw();
  layer.fill(0);
  if (mousePressed)
  {
    if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
    {
      layer.line(mouseX - 20, mouseY - 40, pmouseX - 20, pmouseY - 40);
    }
    if (menu.topBarButtons[0][1].localState)
    {
      layer.clear();
      menu.topBarButtons[0][1].localState = false;
    }
    if (menu.topBarButtons[0][2].localState)
    {
      layer.save("test.png");
    }
  }
  layer.endDraw();


  tint(255);
  background(200);
  image(background, 20, 40);
  image(layer, 20, 40);

  menu.DrawMenu();
  menu.DisplayMenu();
}
