import java.util.LinkedList;

int xFirstClick, yFirstClick, xSecondCLick, ySecondClick,
    xOnPress, yOnPress, xOffset, yOffset;

int xInset = 245, yInset = 120;

float sliderOneValue = 5;
float sliderTwoValue = 255;
float sliderXValue = 0;
float sliderYValue = 0;

Button control;
Button[] btns;
Button[][] buttonMenu;

Slider sliderOne;
Slider sliderTwo;
Slider sliderX;
Slider sliderY;

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

PVector mouseStart, mouseDrag, mouseFinal, firstPoint;

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

  background = createGraphics(width - xInset, height - yInset);
  photoLayer = createGraphics(width - xInset, height - yInset);
  paintLayer = createGraphics(width - xInset, height - yInset);
  combineLayers = createGraphics(width - xInset, height - yInset);
  imageToSaveOne = createImage(width - xInset, height - yInset, HSB);
  imageToSaveTwo = createImage(width - xInset, height - yInset, HSB);
  imageToSaveCombined = createImage(width - xInset, height - yInset, HSB);

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
  firstPoint = new PVector();

  sliderOne = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 35,
                         140, 10, 1, 400, "Size", "px");

  sliderTwo = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 85,
                         140, 10, 0.0, 255, "Opacity", "%");

  sliderX = new Slider(20, height - 20,  width - 265, 20, 0.0, photoLayer.width - width, "xbar", "px");

  sliderY = new Slider(width - 225, 40, 25, height - 85, 0.0, photoLayer.height - height, "ybar", "px");

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
          firstPoint = mouseStart;
          if (doc.currentlyDrawnShape != null)
          {
            doc.currentlyDrawnShape.AddToPoints(mouseStart);
          }
        }
        else if (doc.currentlyDrawnShape != null && newPoly && !isFinished)
        {
          if (mouseStart.x > firstPoint.x - 10 && mouseStart.x < firstPoint.x + 10
            && mouseStart.y > firstPoint.y - 10 && mouseStart.y < firstPoint.y + 10)
            {
              doc.currentlyDrawnShape.AddToPoints(firstPoint);
              isFinished = true;
            }
            else
            {
              doc.currentlyDrawnShape.AddToPoints(mouseStart);
            }
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

  for (int i = 1; i < menu.topBarFileBtns.length; i++)
  {
    if (menu.topBarFileBtns[i].buttonName == "New" && menu.topBarFileBtns[i].localState == true)
        {
          graphicsFunctions.New(photoLayer, menu.topBarFileBtns[i]);
        }
        if (menu.topBarFileBtns[i].buttonName == "Save" && menu.topBarFileBtns[i].localState == true)
        {
          graphicsFunctions.Save(menu.topBarFileBtns[i], selectOne);
        }
        if (menu.topBarFileBtns[i].buttonName == "Load" && menu.topBarFileBtns[i].localState == true)
        {
          graphicsFunctions.Load(menu.topBarFileBtns[i], selectOne);
        }
  }


  background(200);
  image(background, 20, 100);
  image(photoLayer, 20 - sliderXValue, 100 - sliderYValue);
  doc.DrawMe();
  image(paintLayer, 20, 100);

  imageToSaveOne = photoLayer.get(0, 0, photoLayer.width, photoLayer.height);
  imageToSaveTwo = paintLayer.get(0, 0, paintLayer.width, photoLayer.height);

  combineLayers.beginDraw();
  combineLayers.image(imageToSaveOne, 0, 0);
  combineLayers.image(imageToSaveTwo, 0, 0);
  combineLayers.endDraw();

  imageToSaveCombined = combineLayers.get(0, 0, width - 245, height - 60);

  noStroke();
  fill(200);
  rect(0, 20, width - 200, 20);
  rect(0, 20, 20, height - 20);
  rect(width - 225, height - 20, 30, 20);

  menu.DrawMenu();
  menu.DisplayMenu();
  colourPicker.DrawPicker(width - menu.sideMenuXInset + 5, menu.sideMenuColYInset + 5);

  if (photoLayer.width > width - 245)
  {
    sliderXValue = sliderX.DrawSliderHorizontal(sliderXValue);
  }

  if (photoLayer.height > height - 60)
  {
    sliderYValue = sliderY.DrawSliderVertical(sliderYValue);
  }

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
    photoLayer = createGraphics(imageToLoad.width, imageToLoad.height);
    photoLayer.beginDraw();
    photoLayer.image(imageToLoad, 0, 0);
    photoLayer.endDraw();

    sliderX = new Slider(20, height - 20,  width - 266, 20, 0.0, (photoLayer.width - width) + 240, "xbar", "px");
    sliderY = new Slider(width - 221, 40, 20, height - 80, 0.0, (photoLayer.height - height) + 60, "ybar", "px");
  }
}

boolean OverCanvas()
{
  return (mouseX >= 20 && mouseX <= width - menu.sideMenuInset
          && mouseY >= 95 && mouseY <= height - 20);
}
