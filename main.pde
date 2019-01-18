import java.util.LinkedList;

Button control;
Button[] btns;
Button[][] buttonMenu;

boolean clicked = true;

int screenSizeX = 840, screenSizeY = 640,
    menuSize = 200, topBarSize = 20;

Menu menu;
ColourPicker colourPicker;

PGraphics background;
PGraphics layer;

MessageQueue messageQueue;
GraphicsFunctions graphicsFunctions;

String path;

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
  messageQueue = new MessageQueue();
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

  layer.beginDraw();
  layer.endDraw();

  for (int i = 0; i < menu.illustratorMenu.length; i++)
  {
    if (menu.illustratorMenu[i].buttonName == "Pencil" && menu.illustratorMenu[i].localState == true)
    {
      graphicsFunctions.Pencil(layer, colourPicker);
    }
    if (menu.illustratorMenu[i].buttonName == "Eraser" && menu.illustratorMenu[i].localState == true)
    {
      graphicsFunctions.Eraser(layer);
    }
    if (menu.illustratorMenu[i].buttonName == "Line" && menu.illustratorMenu[i].localState == true)
    {
      graphicsFunctions.Line(layer, clicked);
    }

  }

  for (int i = 0; i < menu.topBarButtons.length; i++)
  {
    for (int y = 0; y < menu.topBarButtons[i].length; y++)
    {
      if (menu.topBarButtons[i][y].buttonName == "New" && menu.topBarButtons[i][y].localState == true)
      {
        graphicsFunctions.New(layer, menu.topBarButtons[i][y]);
      }
      if (menu.topBarButtons[i][y].buttonName == "Save" && menu.topBarButtons[i][y].localState == true)
      {
        graphicsFunctions.Save(layer, menu.topBarButtons[i][y], path);
      }
    }
  }

  //tint(255);
  background(200);
  image(background, 20, 40);
  image(layer, 20, 40);

  menu.DrawMenu();
  menu.DisplayMenu();
  colourPicker.DrawPicker(width - menu.sideMenuXInset + 5, menu.sideMenuColYInset + 5);
}

void fileSelected(File selection)
{
  messageQueue.put(selection);
  path = selection.getAbsolutePath();
}
