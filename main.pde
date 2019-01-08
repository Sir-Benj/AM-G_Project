Button control;
Button[] btns;
Button[][] buttonMenu;

int screenSizeX = 840, screenSizeY = 640,
    menuSize = 200, topBarSize = 20;

Menu menu;
ColourPicker colourPicker;

PGraphics background;
PGraphics layer;

GraphicsFunctions graphicsFunctions;

public void settings()
{
  size(3*displayWidth>>2, 3*displayHeight>>2);//screenSizeX + menuSize, screenSizeY + topBarSize);
}

void setup()
{
  frameRate(100);
  noSmooth();
  colorMode(HSB);
  background(255);

  background = createGraphics(width - 245, height - 60);//800, 600);
  layer = createGraphics(width - 245, height - 60);//800, 600);
  menu = new Menu();
  menu.InitialiseMenu();
  colourPicker = new ColourPicker();
  graphicsFunctions = new GraphicsFunctions();
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

  for (int i = 0; i < menu.illustratorMenu.length; i++)
  {
    if (menu.illustratorMenu[i].buttonName == "Pencil" && menu.illustratorMenu[i].localState == true)
    {
      graphicsFunctions.Pencil(layer, colourPicker);
    }
  }
  //
  // layer.beginDraw();
  // layer.colorMode(HSB);
  // if (mousePressed)
  // {
  //   if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
  //   {
  //     layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal);
  //     layer.line(mouseX - 20, mouseY - 40, pmouseX - 20, pmouseY - 40);
  //   }
  //   if (menu.topBarButtons[0][1].localState)
  //   {
  //     layer.clear();
  //     menu.topBarButtons[0][1].localState = false;
  //   }
  //   if (menu.topBarButtons[0][2].localState)
  //   {
  //     selectOutput("Select a file to write to:", "fileSelected");
  //     menu.topBarButtons[0][2].localState = false;
  //   }
  // }
  // layer.endDraw();


  tint(255);
  background(200);
  image(background, 20, 40);
  image(layer, 20, 40);

  menu.DrawMenu();
  menu.DisplayMenu();
  colourPicker.DrawPicker(width - menu.sideMenuXInset + 5, menu.sideMenuColYInset + 5);
}
