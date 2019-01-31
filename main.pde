import java.util.LinkedList;

int xFirstClick, yFirstClick, xSecondCLick, ySecondClick,
    xOnPress, yOnPress, xOffset, yOffset;

float sliderOneValue = 5;
float sliderTwoValue = 255;

Button control;
Button[] btns;
Button[][] buttonMenu;

Slider sliderOne;
Slider sliderTwo;

boolean clicked, left, right, newPoly = false, isFinished = true;

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

PVector mouseStart, mouseDrag, mouseFinal;

boolean pressed = false;
boolean released = true;

Document doc;

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

  doc = new Document();
  mouseStart = new PVector();
  mouseDrag = new PVector();
  mouseFinal = new PVector();

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

  mouseStart = new PVector();

  if (OverCanvas() && released)
  {
    if (mousePressed && (mouseButton == LEFT))
    {
      left = true;
    }
    else
    {
      left = false;
    }

    if (mousePressed && (mouseButton == RIGHT))
    {
      right = true;
      isFinished = true;
    }
    else
    {
      right = false;
    }

    mouseStart.x = mouseX;
    mouseStart.y = mouseY;
    pressed = true;
    released = false;

    for (int i = 0; i < menu.illustratorMenu.length; i++)
    {
      if (menu.illustratorMenu[i].buttonName == "Rectangle" && menu.illustratorMenu[i].localState == true)
      {
        graphicsFunctions.ShapeStart("Rectangle", mouseStart, paintLayer,
                                         doc ,colourPicker, sliderOneValue, sliderTwoValue);
      }
      if (menu.illustratorMenu[i].buttonName == "Circle" && menu.illustratorMenu[i].localState == true)
      {
        graphicsFunctions.ShapeStart("Circle", mouseStart, paintLayer,
                                         doc ,colourPicker, sliderOneValue, sliderTwoValue);
      }
      if (menu.illustratorMenu[i].buttonName == "Line" && menu.illustratorMenu[i].localState == true)
      {
        graphicsFunctions.ShapeStart("Line", mouseStart, paintLayer,
                                         doc ,colourPicker, sliderOneValue, sliderTwoValue);
      }
      if (menu.illustratorMenu[i].buttonName == "Polygon" && menu.illustratorMenu[i].localState == true)
      {
        if (!newPoly)
        {
          graphicsFunctions.ShapeStart("Polygon", mouseStart, paintLayer,
                                       doc ,colourPicker, sliderOneValue, sliderTwoValue);
          isFinished = false;
          newPoly = true;
        }
        if (doc.currentlyDrawnShape != null && newPoly && !isFinished)
        {
          doc.currentlyDrawnShape.AddToPoints(mouseStart);
        }
      }
    }
  }
}

void mouseDragged()
{
  if (pressed && !released)
  {
    mouseDrag.x = mouseX;
    mouseDrag.y = mouseY;
    for (int i = 0; i < menu.illustratorMenu.length; i++)
    {
      if (menu.illustratorMenu[i].buttonName == "Rectangle" && menu.illustratorMenu[i].localState == true)
      {
        graphicsFunctions.ShapeDrag(doc, mouseDrag);
      }
      if (menu.illustratorMenu[i].buttonName == "Circle" && menu.illustratorMenu[i].localState == true)
      {
        graphicsFunctions.ShapeDrag(doc, mouseDrag);
      }
      if (menu.illustratorMenu[i].buttonName == "Line" && menu.illustratorMenu[i].localState == true)
      {
        graphicsFunctions.ShapeDrag(doc, mouseDrag);
      }
    }
  }
}

void mouseReleased()
{
  if (pressed)
  {
    mouseFinal.x = mouseX;
    mouseFinal.y = mouseY;
    pressed = false;
    released = true;

    for (int i = 0; i < menu.illustratorMenu.length; i++)
    {
      if (menu.illustratorMenu[i].buttonName == "Rectangle" && menu.illustratorMenu[i].localState == true)
      {
        graphicsFunctions.ShapeFinal(doc, mouseFinal);
      }
      if (menu.illustratorMenu[i].buttonName == "Circle" && menu.illustratorMenu[i].localState == true)
      {
        graphicsFunctions.ShapeFinal(doc, mouseFinal);
      }
      if (menu.illustratorMenu[i].buttonName == "Line" && menu.illustratorMenu[i].localState == true)
      {
        graphicsFunctions.ShapeFinal(doc, mouseFinal);
      }
      if (menu.illustratorMenu[i].buttonName == "Polygon" && menu.illustratorMenu[i].localState == true)
      {
        if (isFinished)
        {
          graphicsFunctions.ShapeFinal(doc, mouseFinal);
          newPoly = false;
          isFinished = false;
        }
      }
    }
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
    if (menu.illustratorMenu[i].buttonName == "Pencil" && menu.illustratorMenu[i].localState == true && OverCanvas())
    {
      graphicsFunctions.Pencil(paintLayer, colourPicker, sliderOneValue, sliderTwoValue);
    }
    if (menu.illustratorMenu[i].buttonName == "Eraser" && menu.illustratorMenu[i].localState == true)
    {
      graphicsFunctions.Eraser(paintLayer, sliderOneValue);
    }
    if (menu.illustratorMenu[i].buttonName == "ClearLayer" && menu.illustratorMenu[i].localState == true)
    {
      graphicsFunctions.ClearLayer(paintLayer, menu.illustratorMenu[i], doc);
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
  doc.DrawMe();
  image(paintLayer, 20, 40);

  imageToSaveOne = photoLayer.get(0, 0, width - 245, height - 60);
  imageToSaveTwo = paintLayer.get(0, 0, width - 245, height - 60);

  combineLayers.beginDraw();
  combineLayers.image(imageToSaveOne, 0, 0);
  combineLayers.image(imageToSaveTwo, 0, 0);
  combineLayers.endDraw();

  imageToSaveCombined = combineLayers.get(0, 0, width - 245, height - 60);

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

boolean OverCanvas()
{
  return (mouseX >= 20 && mouseX <= width - menu.sideMenuInset
          && mouseY >= 40 && mouseY <= height - 20);
}
