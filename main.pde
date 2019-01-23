import java.util.LinkedList;

int xFirstClick, yFirstClick, xSecondCLick, ySecondClick;

int sliderOneValue = 5;
int sliderTwoValue = 255;

Button control;
Button[] btns;
Button[][] buttonMenu;

Slider sliderOne;
Slider sliderTwo;

boolean clicked;

Menu menu;
ColourPicker colourPicker;

PGraphics background;
PGraphics photoLayer;
PGraphics paintLayer;
PGraphics combineLayers;

PImage imageToLoad;
PImage imageToSaveOne;
PImage imageToSaveTwo;
PImage imageToSaveCombined;

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
  frameRate(60);
  colorMode(HSB);
  background(255);

  background = createGraphics(width - 245, height - 60);
  photoLayer = createGraphics(width - 245, height - 60);
  paintLayer = createGraphics(width - 245, height - 60);
  combineLayers = createGraphics(width - 245, height - 60);
  imageToSaveOne = createImage(width - 245, height - 60, HSB);
  imageToSaveTwo = createImage(width - 245, height - 60, HSB);
  imageToSaveCombined = createImage(width - 245, height - 60, HSB);

  menu = new Menu();
  menu.InitialiseMenu();
  colourPicker = new ColourPicker();
  messageQueue = new MessageQueue();
  graphicsFunctions = new GraphicsFunctions();
  path = "";
  selectOne = new File(sketchPath("") + "/*.png");

  sliderOne = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 35,
                         140, 10, 1, 400, "Size", "px");

  sliderTwo = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 85,
                         140, 10, 0.0, 255, "Opacity", "%");

  background.beginDraw();
  background.colorMode(HSB);
  background.background(255);
  background.endDraw();

  paintLayer.beginDraw();
  paintLayer.colorMode(HSB);
  paintLayer.endDraw();

  photoLayer.beginDraw();
  photoLayer.colorMode(HSB);
  photoLayer.endDraw();

  combineLayers.beginDraw();
  combineLayers.colorMode(HSB);
  combineLayers.endDraw();
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
  paintLayer.beginDraw();
  paintLayer.endDraw();
  photoLayer.beginDraw();
  photoLayer.endDraw();
  combineLayers.beginDraw();
  combineLayers.endDraw();

  for (int i = 0; i < menu.illustratorMenu.length; i++)
  {
    if (menu.illustratorMenu[i].buttonName == "Pencil" && menu.illustratorMenu[i].localState == true && OverMenu())
    {
      graphicsFunctions.Pencil(paintLayer, colourPicker, sliderOneValue, sliderTwoValue);
    }
    if (menu.illustratorMenu[i].buttonName == "Eraser" && menu.illustratorMenu[i].localState == true)
    {
      graphicsFunctions.Eraser(paintLayer, sliderOneValue);
    }
    if (menu.illustratorMenu[i].buttonName == "Line" && menu.illustratorMenu[i].localState == true)
    {
      graphicsFunctions.Line(paintLayer, clicked, xFirstClick, xSecondCLick,
                             yFirstClick, ySecondClick, colourPicker, sliderOneValue, sliderTwoValue);
    }
    if (menu.illustratorMenu[i].buttonName == "ClearLayer" && menu.illustratorMenu[i].localState == true)
    {
      graphicsFunctions.ClearLayer(paintLayer, menu.illustratorMenu[i]);
    }

  }

  for (int i = 0; i < menu.topBarButtons.length; i++)
  {
    for (int y = 0; y < menu.topBarButtons[i].length; y++)
    {
      if (menu.topBarButtons[i][y].buttonName == "New" && menu.topBarButtons[i][y].localState == true)
      {
        graphicsFunctions.New(photoLayer, menu.topBarButtons[i][y]);
      }
      if (menu.topBarButtons[i][y].buttonName == "Save" && menu.topBarButtons[i][y].localState == true)
      {
        graphicsFunctions.Save(menu.topBarButtons[i][y], selectOne);
      }
      if (menu.topBarButtons[i][y].buttonName == "Load" && menu.topBarButtons[i][y].localState == true)
      {
        graphicsFunctions.Load(menu.topBarButtons[i][y], selectOne);
      }
    }
  }


  //tint(255);
  background(200);
  image(background, 20, 40);
  image(photoLayer, 20, 40);
  image(paintLayer, 20, 40);

  imageToSaveOne = photoLayer.get(0, 0, width - 245, height - 60);
  imageToSaveTwo = paintLayer.get(0, 0, width - 245, height - 60);

  combineLayers.beginDraw();
  combineLayers.image(imageToSaveOne, 0, 0);
  combineLayers.image(imageToSaveTwo, 0, 0);
  combineLayers.endDraw();

  imageToSaveCombined = combineLayers.get(0, 0, width - 245, height - 60);

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
    else if  (menu.illustratorMenu[i].buttonName == "Line" && menu.illustratorMenu[i].localState == false)
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

  for (int i = 0; i < menu.illustratorMenu.length; i++)
  {
    if (menu.illustratorMenu[i].localState == true && menu.illustratorMenu[i].buttonName != "Eraser")
    {
      sliderOneValue = sliderOne.DrawSliderMenu(sliderOneValue);
      sliderTwoValue = sliderTwo.DrawSliderMenu(sliderTwoValue);
    }
    else if (menu.illustratorMenu[i].localState == true && menu.illustratorMenu[i].buttonName == "Eraser")
    {
      sliderOneValue = sliderOne.DrawSliderMenu(sliderOneValue);
    }
  }

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
    imageToSaveCombined.save(path);
  }
}

void fileChosen(File selection)
{
  if (selection == null)
  {
    println("Window was closed or the user hit cancel.");
  }
  else
  {
    photoLayer.clear();
    messageQueue.put(selection);
    path = selection.getAbsolutePath();
    imageToLoad = loadImage(path);
    photoLayer.beginDraw();
    photoLayer.image(imageToLoad, 0, 0);
    photoLayer.endDraw();
  }
}

boolean OverMenu()
{
  return (mousePressed && mouseX >= 0 && mouseX <= width - menu.sideMenuInset);
}
