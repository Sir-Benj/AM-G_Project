import java.util.LinkedList;

int xFirstClick, yFirstClick, xSecondCLick, ySecondClick;

Button control;
Button[] btns;
Button[][] buttonMenu;

boolean clicked;

Menu menu;
ColourPicker colourPicker;

PGraphics background;
PGraphics layer;

MessageQueue messageQueue;
GraphicsFunctions graphicsFunctions;

String path;
File selectOne;

public void settings()
{
  size(3*displayWidth>>2, 3*displayHeight>>2);
}

void setup()
{
  frameRate(100);
  noSmooth();
  colorMode(HSB);
  background(255);

  background = createGraphics(width - 245, height - 60);
  layer = createGraphics(width - 245, height - 60);
  menu = new Menu();
  menu.InitialiseMenu();
  colourPicker = new ColourPicker();
  messageQueue = new MessageQueue();
  graphicsFunctions = new GraphicsFunctions();
  path = "";
  selectOne = new File(sketchPath("") + "/*.png");

  background.beginDraw();
  background.colorMode(HSB);
  background.background(255);
  background.endDraw();

  layer.beginDraw();
  layer.colorMode(HSB);
  layer.background(255);
  layer.endDraw();
}

void mousePressed()
{
  menu.TopMenuPressed();
  menu.SideMenuPressed();
}

void mouseDragged()
{

}

void mouseClicked()
{
  if (mouseX >= 10 && mouseX <= width - 200 && mouseY >= 30 && mouseY <= height - 10)
  {
    if (clicked)
    {
      xSecondCLick = mouseX;
      ySecondClick = mouseY;
      clicked = false;
      return;
    }

    xFirstClick = mouseX;
    yFirstClick = mouseY;
    clicked = true;
  }
}


void draw()
{

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
      graphicsFunctions.Line(layer, clicked, xFirstClick, xSecondCLick,
                             yFirstClick, ySecondClick, colourPicker);
    }
    if (menu.illustratorMenu[i].buttonName == "ClearLayer" && menu.illustratorMenu[i].localState == true)
    {
      graphicsFunctions.ClearLayer(layer, menu.illustratorMenu[i]);
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
        graphicsFunctions.Save(layer, menu.topBarButtons[i][y], path, selectOne);
      }
    }
  }


  //tint(255);
  background(200);
  image(background, 20, 40);
  image(layer, 20, 40);

  for (int i = 0; i < menu.illustratorMenu.length; i++)
  {
    if  (menu.illustratorMenu[i].buttonName == "Line" && menu.illustratorMenu[i].localState == true)
    {
      if (clicked)
      {
        //strokeWeight(10);
        stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal);
        line(xFirstClick, yFirstClick, mouseX, mouseY);
      }
      else if (!clicked)
      {
        xFirstClick = -1;
        yFirstClick = -1;
        xSecondCLick = -1;
        ySecondClick = -1;
      }
    }
    if  (menu.illustratorMenu[i].buttonName == "Line" && menu.illustratorMenu[i].localState == false)
    {
      xFirstClick = -1;
      yFirstClick = -1;
      xSecondCLick = -1;
      ySecondClick = -1;
    }
  }

  menu.DrawMenu();
  menu.DisplayMenu();
  colourPicker.DrawPicker(width - menu.sideMenuXInset + 5, menu.sideMenuColYInset + 5);
}

void fileSelected(File selection)
{
  if (selection == null)
  {
    println("Window was closed or the user hit cancel.");
  }
  else
  {
    messageQueue.put(selection);
    path = selection.getAbsolutePath();
    layer.save(path);
  }
}
