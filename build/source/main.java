import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.LinkedList; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class main extends PApplet {



int xFirstClick, yFirstClick, xSecondCLick, ySecondClick,
    xOnPress, yOnPress, xOffset, yOffset;

int sliderOneValue = 5;
int sliderTwoValue = 255;

Button control;
Button[] btns;
Button[][] buttonMenu;

Slider sliderOne;
Slider sliderTwo;

boolean clicked, left;

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

public void setup()
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
                         140, 10, 0.0f, 255, "Opacity", "%");

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

public void mousePressed()
{
  for (int i = 0; i < menu.illustratorMenu.length; i++)
  {
    if (menu.illustratorMenu[i].buttonName == "Rectangle" && menu.illustratorMenu[i].localState == true && released)
    {
<<<<<<< HEAD
      xOnPress = mouseX;
      yOnPress = mouseY;
=======
      mouseStart.x = mouseX;
      mouseStart.y = mouseY;
>>>>>>> a5e698916424d18aa66790516cf9d9dc614cc931
      pressed = true;
      released = false;
      doc.StartNewShape("rectangle", mouseStart, paintLayer);
    }
  }

  if (mousePressed && (mouseButton == LEFT))
  {
    left = true;
  }
  else
  {
    left = false;
  }

  menu.TopMenuPressed();
  menu.SideMenuPressed();
}

public void mouseDragged()
{
  for (int i = 0; i < menu.illustratorMenu.length; i++)
  {
    if (menu.illustratorMenu[i].buttonName == "Rectangle" && menu.illustratorMenu[i].localState == true && pressed && !released)
    {
      mouseDrag.x = mouseX;
      mouseDrag.y = mouseY;

      if (doc.currentlyDrawnShape == null)
      {
        return;
      }
      doc.currentlyDrawnShape.WhileDrawingShape(mouseDrag);
      }
  }
}

public void mouseReleased()
{
<<<<<<< HEAD
  pressed = false;
=======
  for (int i = 0; i < menu.illustratorMenu.length; i++)
  {
    if (menu.illustratorMenu[i].buttonName == "Rectangle" && menu.illustratorMenu[i].localState == true && pressed)
    {
      mouseFinal.x = mouseX;
      mouseFinal.y = mouseY;
      pressed = false;
      released = true;
      if (doc.currentlyDrawnShape == null)
      {
        return;
      }
      doc.currentlyDrawnShape.FinishDrawingShape(mouseFinal);
      doc.currentlyDrawnShape = null;
    }
  }
>>>>>>> a5e698916424d18aa66790516cf9d9dc614cc931
}

public void mouseClicked()
{
  if (mouseX >= 10 && mouseX <= width - 200 && mouseY >= 30 && mouseY <= height - 10)
  {
    if (left)
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
    else
    {
      xFirstClick = -1; xSecondCLick = -1; yFirstClick = -1; ySecondClick = -1;
    }
}


public void draw()
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
    if (menu.illustratorMenu[i].buttonName == "Rectangle" && menu.illustratorMenu[i].localState == true)
    {
      graphicsFunctions.Rectangle(paintLayer, pressed, xOnPress, xOffset,
                             yOnPress, yOffset, colourPicker, sliderOneValue, sliderTwoValue);
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

  for (int i = 0; i < menu.illustratorMenu.length; i++)
  {
    if  (menu.illustratorMenu[i].buttonName == "Line" && menu.illustratorMenu[i].localState == true)
    {
      if (clicked)
      {
        strokeWeight(sliderOneValue);
        stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal, sliderTwoValue);
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

  for (int i = 0; i < menu.illustratorMenu.length; i++)
  {
    if  (menu.illustratorMenu[i].buttonName == "Rectangle" && menu.illustratorMenu[i].localState == true)
    {
      if (pressed)
      {
        if (xOnPress < 10 || yOnPress < 30 || xOffset > width - 200 || yOffset > height - 10)
        {
          return;
        }
        strokeWeight(sliderOneValue);
        stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal, sliderTwoValue);
        noFill();
        rect(xOnPress, yOnPress, xOffset, yOffset);
      }
      else if (!pressed)
      {
        xOnPress = -1;
        yOnPress = -1;
        xOffset = -1;
        yOffset = -1;
      }
    }
  }

  strokeWeight(1);
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

public void fileSelected(File selection)
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

public void fileChosen(File selection)
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

public boolean OverMenu()
{
  return (mousePressed && mouseX >= 0 && mouseX <= width - menu.sideMenuInset);
}
class ColourPicker
{
  float barWidth = 150;
  public float _hueVal = barWidth;
  public float _satVal = barWidth;
  public float _briVal = barWidth;


  ColourPicker() {}

  public void DrawPicker(float colourMenuXInset, float colourMenuYInset)
  {
    _hueVal= DrawSlider(colourMenuXInset, colourMenuYInset + 190, barWidth, 40.0f, _hueVal, _hueVal, "Hue");
    _satVal= DrawSlider(colourMenuXInset, colourMenuYInset + 265, barWidth, 20.0f, _satVal, _hueVal, "Saturation");
    _briVal= DrawSlider(colourMenuXInset, colourMenuYInset + 315, barWidth, 20.0f, _briVal, _hueVal, "Brightness");
    fill(_hueVal, _satVal, _briVal);
    rect(colourMenuXInset, colourMenuYInset, 150, 160);
  }

  public float DrawSlider(float xPos, float yPos, float sWidth, float sHeight, float hueVal, float hueActVal, String display)
  {
    float sliderPos = map(hueVal, 0.0f, 255.0f, 0.0f, sWidth);

    for(int i = 0; i < sWidth; i++)
    {
      float hueValue = map(i, 0.0f, sWidth, 0.0f, 255.0f);
      switch(display)
      {
        case "Hue": stroke(hueValue, 255, 255);
                    break;
        case "Saturation": float satValue=map(i, 0.0f, sWidth, 0.0f, 255);
                    stroke(hueActVal, satValue, 255);
                    break;
        case "Brightness": float briValue=map(i, 0.0f, sWidth, 0.0f, 255);
                    stroke(hueActVal, 255, briValue);
                    break;
      }
        line(xPos + i, yPos, xPos + i, yPos + sHeight);
    }
    if(mousePressed && mouseX > xPos && mouseX < (xPos + sWidth)
       && mouseY > yPos && mouseY < yPos + sHeight)
    {
       sliderPos = mouseX - xPos;
       hueVal = map(sliderPos, 0.0f, sWidth, 0.0f, 255.0f);
    }

    stroke(100);
    switch(display)
    {
      case "Hue": fill(_hueVal, 255, 255);
              break;
      case "Saturation": fill(_hueVal, _satVal, 255);
              break;
      case "Brightness": fill(_hueVal, 255, _briVal);
    }
    rect(sliderPos + xPos - 3, yPos - 5, 6, sHeight + 10);

    textSize(16);
    fill(0);
    switch(display)
    {
      case "Hue": text(display + ": " + (int)_hueVal , xPos + 10, yPos - 10);
              break;
      case "Saturation": text(display + ": " + (int)_satVal , xPos + 10, yPos - 10);
              break;
      case "Brightness": text(display + ": " + (int)_briVal , xPos + 10, yPos - 10);
    }

    if (hueVal >= 249.0f)
    {
      hueVal = 255;
    }
    else if (hueVal <= 2.0f)
    {
      hueVal = 0.0f;
    }

    return hueVal;
  }
}
class Document
{

  ArrayList<DrawShape> shapeList = new ArrayList<DrawShape>();

  // this references the currently drawn shape. It is set to null
  // if no shape is currently being drawn
  public DrawShape currentlyDrawnShape = null;

  public Document()
  {
  }

  public void StartNewShape(String shapeType, PVector mouseStartLoc, PGraphics layer)
  {
    DrawShape newShape = new DrawShape();

    newShape.BeginDrawingShape(shapeType, mouseStartLoc, layer);
    shapeList.add(newShape);
    println(shapeList.size());
    currentlyDrawnShape = newShape;
  }

  public void DrawMe()
  {
    for(DrawShape s : shapeList)
    {
        s.drawThisShape();
    }
  }

  public void TrySelect(PVector p)
  {
    boolean selectionFound = false;
    for(DrawShape s : shapeList)
    {
      selectionFound = s.SelectThis(p);
      if(selectionFound) break;
    }
  }
}
class DrawShape
{
  String shapeToDraw;

  PVector mouseStart, mouseDrag, mouseEnd;
  float radius;

  boolean isSelected = false;
  boolean isDrawing = false;

  int sWeight = 10;

  Rect bounds;

  PGraphics layer;

  DrawShape()
  {
  }

  public void BeginDrawingShape(String shapeToDraw, PVector startPoint, PGraphics layer)
  {
    this.isDrawing = true;
    this.shapeToDraw = shapeToDraw;
    this.mouseStart = startPoint;
    this.mouseDrag = startPoint;
    this.layer = layer;
  }

  public void WhileDrawingShape(PVector dragPoint)
  {
    this.mouseDrag = dragPoint;
  }

  public void FinishDrawingShape(PVector endPoint)
  {
    this.mouseEnd = endPoint;
    setShapeBounds(this.mouseStart, this.mouseEnd);
    this.isDrawing = false;
  }

  public void setShapeBounds(PVector vecOne, PVector vecTwo)
  {
    bounds = new Rect(vecOne, vecTwo);
  }

  public boolean SelectThis(PVector vec)
  {
    if (bounds.isInsideThis(vec))
    {
      this.isSelected = !this.isSelected;
      return true;
    }
    else
    {
      return false;
    }
  }

  public void drawThisShape()
  {
    this.layer.beginDraw();
    this.layer.strokeWeight(1);
    this.layer.noFill();
    if (isDrawing)
    {
      strokeWeight(1);
      float x1 = this.mouseStart.x;
      float y1 = this.mouseStart.y;
      float wid = this.mouseDrag.x - x1;
      float hgt = this.mouseDrag.y - y1;
      rect(x1, y1,wid,hgt);
    }
    else
    {
      float x1 = this.bounds.left;
      float y1 = this.bounds.top;
      float wid = this.bounds.getWidth();
      float hgt = this.bounds.getHeight();
      this.layer.rect(x1 - 20, y1 - 40, wid, hgt);

      if (this.isSelected)
      {
        this.layer.noFill();
        this.layer.strokeWeight(1);
        this.layer.stroke(255,50,50);
        this.layer.rect(x1-1,y1-1,wid+2,hgt+2);
      }
    }
    this.layer.endDraw();

  }
}
class GraphicsFunctions
{

  GraphicsFunctions()
  {
  }

  public void New(PGraphics layer, Button button)
  {
    layer.clear();
    button.localState = false;
  }

  public void Save(Button button, File newFile)
  {
    selectOutput("Select Output", "fileSelected", newFile);
    button.localState = false;
  }

  public void Load(Button button, File newFile)
  {
    selectInput("Select An Image To Edit", "fileChosen", newFile);
    button.localState = false;
  }

  public void Undo()
  {

  }

  public void Redo()
  {

  }

  public void Blur()
  {

  }

  public void Sharpen()
  {

  }

  public void Greyscale()
  {

  }

  public void Monochrome()
  {

  }

  public void Pencil(PGraphics layer, ColourPicker colourPicker, float sVOne, float sVTwo)
  {

    layer.beginDraw();
    layer.colorMode(HSB);
    if (mousePressed)
    {
      if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
      {
        layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal, sVTwo);
        layer.strokeJoin(ROUND);
        layer.strokeWeight(sVOne);
        layer.line(mouseX - 20, mouseY - 40, pmouseX - 20, pmouseY - 40);
      }
    }
    layer.endDraw();
  }

  public void Eraser(PGraphics layer, float sVOne)
  {
    layer.beginDraw();
    layer.colorMode(HSB);
    if (mousePressed)
    {
      if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
      {
        layer.stroke(255);
        layer.strokeWeight(sVOne);
        layer.line(mouseX - 20, mouseY - 40, pmouseX - 20, pmouseY - 40);
      }
    }
    layer.endDraw();
  }

  public void Line(PGraphics layer, boolean clicked, int xFirst, int xSecond,
            int yFirst, int ySecond, ColourPicker colour, float sVOne, float sVTwo)
  {
    if (xFirst < 10 || yFirst < 30 || xSecond > width - 200 || ySecond > height - 10)
    {
      return;
    }
    else if (clicked)
    {
      return;
    }

    layer.beginDraw();
    layer.colorMode(HSB);
    layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal, sVTwo);
    layer.strokeWeight(sVOne);
    layer.line(xFirst - 20, yFirst - 40, xSecond - 20, ySecond - 40);

  }

  public void Rectangle(PGraphics layer, boolean pressed, float xOnPress, float xOffset, float yOnPress,
                 float yOffset, ColourPicker colourPicker, float sVOne, float sVTwo)
  {
    if (xOnPress < 10 || yOnPress < 30 || xOffset > width - 200 || yOffset > height - 10)
    {
      return;
    }
    if (!pressed)
    {
      layer.beginDraw();
      layer.noFill();
      layer.strokeWeight(sVOne);
      layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal, sVTwo);
      layer.rect(xOnPress - 20, yOnPress - 40, xOffset, yOffset);
      layer.endDraw();
    }
  }

  public void Circle()
  {

  }

  public void Polygon()
  {

  }

  public void Duplicate()
  {

  }

  public void ScaleShape()
  {

  }

  public void RotateShape()
  {

  }

  public void ClearLayer(PGraphics layer, Button button, Document doc)
  {
    layer.clear();
    doc.shapeList = new ArrayList<DrawShape>();
    button.localState = false;
  }

  public void Resize()
  {

  }

  public void EdgeDetect()
  {

  }

  public void Rotate()
  {

  }

  public void Hue()
  {

  }

  public void Saturation()
  {

  }

  public void Brightness()
  {

  }

  public void Contrast()
  {

  }

}
class MessageQueue
{
  public LinkedList queue;

  public MessageQueue()
  {
    queue = new LinkedList();
  }

  synchronized public void put(Object value)
  {
    queue.addLast(value);
    notifyAll();
  }

  synchronized public Object get()
  {
    while (queue.isEmpty ()) {
      try {
        wait();
      }
      catch (InterruptedException e) {
      }
    }
    return queue.removeFirst();
  }
}
class Rect
{
  float left, top, right, bottom;

  Rect(float xOne, float yOne, float xTwo, float yTwo)
  {
    setRect(xOne, yOne, xTwo, yTwo);
  }

  Rect(PVector vecOne, PVector vecTwo)
  {
    setRect(vecOne.x, vecOne.y, vecTwo.x, vecTwo.y);
  }

  public void setRect(float xOne, float yOne, float xTwo, float yTwo)
  {
    this.left = min(xOne, xTwo);
    this.top = min(yOne, yTwo);
    this.right = max(xOne, xTwo);
    this.bottom = max(yOne, yTwo);
  }

  public PVector getCentre()
  {
    PVector centre = new PVector();
    centre.x = (this.right - this.left) / 2.0f;
    centre.y = (this.bottom - this.top) / 2.0f;
    return centre;
  }

  public boolean isInsideThis(PVector vec)
  {
    return (isBetween(vec.x, this.left, this.right) && isBetween(vec.y, this.top, this.bottom));
  }

  public float getWidth()
  {
    return (this.right - this.left);
  }

  public float getHeight()
  {
    return (this.bottom - this.top);
  }
}

public boolean isBetween(float value, float low, float high)
{
  return (value >= low && value <= high);
}
class Slider
{
  float xBarPos, yBarPos, barWidth, barHeight, mapValueLow, mapValueHigh,
        retValue;
  String sliderName, sNameValue;

  Slider(float xPos, float yPos, float barW, float barH, float mVL, float mVH,
         String sName, String sNValue)
  {
    xBarPos = xPos;
    yBarPos = yPos;
    barWidth = barW;
    barHeight = barH;
    mapValueLow = mVL;
    mapValueHigh = mVH;
    sliderName = sName;
    sNameValue = sNValue;
  }

  public int DrawSliderMenu(float retValue)
  {
    float sliderPos = map(retValue, mapValueLow, mapValueHigh, 0.0f, barWidth);

    stroke(80);
    fill(100);
    rect(xBarPos, yBarPos, barWidth, barHeight);

    if(mousePressed && mouseX >=  xBarPos && mouseX <= (xBarPos + barWidth)
       && mouseY >= yBarPos && mouseY <= yBarPos + barHeight)
    {
      sliderPos = mouseX - xBarPos;
      retValue = map(sliderPos, 0.0f, barWidth, mapValueLow, mapValueHigh);
    }

    if (sliderName == "Size")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Opacity")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)((retValue / mapValueHigh) * 100) + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }

    stroke(1);
    fill(50);
    rect(sliderPos + xBarPos - 3, yBarPos - 5, 6, barHeight + 10);

    return (int)retValue;
  }
}
// class TopBarManager
// {
//   String[][] topBar;
//   Button[][] menuButtons;
//   Button[] sub1;
//   Button[] sub2;
//   Button[] sub3;
//
//   PFont font;
//   //int topBheight;
//   //int topBwidth
//
//   TopBarManager()
//   {
//     topBar = new String[][] { {"File", "New", "Save", "Load"}, {"Edit", "Undo", "Redo"}, {"Filter", "Blur", "Sharpen"} };
//     font = createFont("arial.ttf", 16);
//
//     menuButtons = new Button[topBar.length][];
//     sub1 = new Button[topBar[0].length];
//     sub2 = new Button[topBar[1].length];
//     sub3 = new Button[topBar[2].length];
//
//     menuButtons[0] = sub1;
//     menuButtons[1] = sub2;
//     menuButtons[2] = sub3;
//   }
//
//   Button[][] InitialiseMenu()
//   {
//     noStroke();
//     fill(180);
//     rect(0, 0, width, 20);
//     textFont(font, 14);
//
//     int topXstart = 0;
//     int topYstart = 0;
//     int topBwidth = 50;
//     int topBheight = 20;
//     int subXstart = 0;
//     int subYstart = 20;
//     int subBwidth = 80;
//     int subBheight = 20;
//
//     for (int topMenu = 0; topMenu < menuButtons.length; topMenu++)
//     {
//       menuButtons[topMenu][0] = new Button(topXstart, topYstart, topBwidth, topBheight, false, false, topBar[topMenu][0]);
//       //menuButtons[topMenu][0].displayButton();
//       topXstart += 50;
//
//       for (int subMenu = 1; subMenu < menuButtons[topMenu].length; subMenu++)
//       {
//         menuButtons[topMenu][subMenu] = new Button(subXstart, subYstart, subBwidth, subBheight, false, false, topBar[topMenu][subMenu]);
//         subYstart += 20;
//       }
//       subXstart += 50;
//       subYstart = 20;
//     }
//
//     return menuButtons;
//   }
//
//   void DisplayMenu()
//   {
//     noStroke();
//     fill(100);
//     rect(0, 0, width, 20);
//     textFont(font, 14);
//
//     for (int i = 0; i < menuButtons.length; i++)
//     {
//       for (int y = 0; y < menuButtons[i].length; y++)
//       {
//         menuButtons[i][0].DisplayButton();
//         if (menuButtons[i][0].localState)
//         {
//           menuButtons[i][y].DisplayButton();
//         }
//       }
//     }
//   }
//
//   void TopMenuPressed()
//   {
//     menuButtons[0][0].TopMenuButtonPressed(menuButtons[1][0]);
//     menuButtons[0][0].TopMenuButtonPressed(menuButtons[2][0]);
//     menuButtons[1][0].TopMenuButtonPressed(menuButtons[0][0]);
//     menuButtons[1][0].TopMenuButtonPressed(menuButtons[2][0]);
//     menuButtons[2][0].TopMenuButtonPressed(menuButtons[0][0]);
//     menuButtons[2][0].TopMenuButtonPressed(menuButtons[1][0]);
//   }
//
//
// }
class Button
{
  protected int buttonX, buttonY, buttonWidth, buttonHeight, smoothing;
  protected String buttonName;
  protected boolean isSmooth, hasBorder, showName, hasIcon, localState, invert, inverted;
  protected int buttonColour = color(180), buttonHighlight = color(210);
  protected PImage iconImage, iconImageInverted;

  Button(int newX, int newY, int newWidth, int newHeight, boolean smooth,
         boolean border, String newName, boolean nameOnOff, Boolean iconOnOff)
  {
    buttonX = newX;
    buttonY = newY;
    buttonWidth = newWidth;
    buttonHeight = newHeight;
    isSmooth = smooth;
    hasBorder = border;
    buttonName = newName;
    showName = nameOnOff;
    hasIcon = iconOnOff;
    invert = false;
    inverted = false;
    smoothing = 8;
    localState = false;

    if (hasIcon)
    {
      iconImage = loadImage("Icon" + buttonName + ".png");
      iconImageInverted = loadImage("Icon" + buttonName + ".png");
      if (iconImageInverted != null && hasIcon)
      {
        iconImageInverted.filter(INVERT);
      }
    }
  }

  public void DisplayButton()
  {
    //If mouse is over button highlight it
    if (OverButton() || localState)
    {
      if (hasBorder)
      {
        stroke(0);
        fill(buttonHighlight);
      }
      else
      {
        noStroke();
        fill(buttonHighlight);
      }

      if (isSmooth)
      {
        rect(buttonX, buttonY, buttonWidth, buttonHeight, smoothing);
        fill(0);
        if (showName)
        {
          text(buttonName, buttonX, buttonY + buttonHeight/1.5f);
        }
      }
      else
      {
        rect(buttonX, buttonY, buttonWidth, buttonHeight);
        fill(0);
        if (showName)
        {
          text(buttonName, buttonX, buttonY + buttonHeight/1.5f);
        }
      }

      if (iconImage != null && hasIcon)
      {
        image(iconImageInverted, buttonX, buttonY);
      }
    }
    //If mouse isnt over and it isnt on then display normal colour
    else if (!OverButton() || !localState)
    {
      if (hasBorder)
      {
        stroke(0);
        fill(buttonColour);
      }
      else
      {
        noStroke();
        fill(buttonColour);
      }

      if (isSmooth)
      {
        rect(buttonX, buttonY, buttonWidth, buttonHeight, smoothing);
        fill(0);
        if (showName)
        {
          text(buttonName, buttonX, buttonY + buttonHeight/1.5f);
        }
      }
      else
      {
        rect(buttonX, buttonY, buttonWidth, buttonHeight);
        fill(0);
        if (showName)
        {
          text(buttonName, buttonX, buttonY + buttonHeight/1.5f);
        }
      }

      if (iconImage != null && hasIcon)
      {
        image(iconImage, buttonX, buttonY);
      }
    }
  }

  public boolean OverButton()
  {
    //Is mouse within the button area
    if (mouseX > buttonX && mouseX < buttonX + buttonWidth
        && mouseY > buttonY && mouseY < buttonY + buttonHeight)
        {
          return true;
        }
        else
        {
          return false;
        }
  }

  public void ButtonPressed(Button[] btns)
  {
    for (int i = 0; i < btns.length; i++)
    {
      if (btns[i].OverButton() && !btns[i].LocalState())
      {
        btns[i].localState = true;
        for (int j = 0; j < btns.length; j++ )
        {
          if (j != i)
          {
            btns[j].localState = false;
          }
        }
      }
      else if (btns[i].OverButton() && btns[i].LocalState())
      {
        btns[i].localState = false;
      }
    }
  }

  public void TopMenuButtonPressed(Button btn)
  {
    if (OverButton() && !localState)
    {
      //Button on
      localState = true;
      btn.localState = false;
    }
    else if (!OverButton() && localState && btn.OverButton())
    {
      //Button off
      localState = false;
    }
    else if (!OverButton() && !btn.OverButton())
    {
      localState = false;
      btn.localState = false;
    }
  }

  public int ButtonX()
  {
    return buttonX;
  }

  public int ButtonY()
  {
    return buttonY;
  }

  public int ButtonWidth()
  {
    return buttonWidth;
  }

  public int ButtonHeight()
  {
    return buttonHeight;
  }

  public boolean LocalState()
  {
    return localState;
  }

  public String ButtonName()
  {
    return buttonName;
  }
}
// boolean buttonOver = false;
// boolean buttonPressed = false;
// color buttonColour, highlight;
// Button bOne;
//
// class ImageUI extends PApplet
// {
//   ImageUI()
//   {
//     super();
//     ImageUI.runSketch(new String[] {this.getClass().getSimpleName()}, this);
//   }
//
//   void settings()
//   {
//     size(500, 200);
//   }
//
//   void setup()
//   {
//     surface.setTitle("Image Manipulation Tools");
//     background(0);
//     buttonColour = color(100);
//     highlight = color(200);
//     drawButton();
//     bOne = new Button(70, 10, 50, 50);
//   }
//
//   void draw()
//   {
//     background(0);
//     bOne.displayButton();
//     overButton();
//   }
//
//   void mousePressed()
//   {
//     buttonPressed();
//   }
//
//   void mouseClicked()
//   {
//     if (bOne.buttonPressed())
//     {
//       println("Button clicked");
//     }
//   }
//
//   void drawButton()
//   {
//     fill(buttonColour);
//     rect(10, 10, 50, 50);
//   }
//
//   void overButton()
//   {
//   if (mouseX > 10 && mouseX < 60
//       && mouseY > 10 && mouseY < 60)
//      {
//        buttonOver = true;
//        fill(highlight);
//      }
//   else
//      {
//        buttonOver = false;
//        fill(buttonColour);
//      }
//      stroke(255);
//      rect(10, 10, 50, 50);
//   }
//
//   void buttonPressed()
//   {
//     if (buttonOver && !buttonPressed)
//     {
//       buttonPressed = true;
//       buttonColour = highlight;
//     }
//     else if (buttonOver && buttonPressed)
//     {
//       buttonPressed = false;
//       buttonColour = color(150);
//     }
//   }
//
//   void exit()
//   {
//     dispose();
//   }
// }
class Menu
{
  // Arrays for holding button string names and buttons
  String[][] topBarNames;
  Button[][] topBarButtons;
  Button[] topBarSubFirst;
  Button[] topBarSubSecond;
  Button[] topBarSubThird;

  String[] illustratorNames;
  Button[] illustratorMenu;

  String[] photoEditNames;
  Button[] photoEditMenu;

  int btnFontSize = 16, sideMenuInset = 200,
      topBarXStart = 0, topBarYStart = 0, topBarWidth = 60, topBarHeight = 20,
      subXStart = 0, subYStart = 20, subBWidth = 100, subBHeight = 20,
      topBarXIncrease = 60, topBarYIncrease = 20,
      sideMenuXInset = 180, sideMenuColYInset = 20, sideMenuColWidth = 160, sideMenuColHeight = 350,
      sideMenuSelYInset = 390, sideMenuSelWidth = 160, sideMenuSelHeight = 150;

  PFont btnFont;
  //
  Menu()
  {
    // String arrays - first string in each list is the head of the array, this becomes the name
    // shown on the top bar menu, the rest become sub buttons of this name.
    topBarNames = new String[][] { {"File", "New", "Save", "Load"}, {"Edit", "Undo", "Redo"}, {"Filter", "Blur", "Sharpen", "Greyscale", "Monochrome"} };
    illustratorNames = new String[] {"Pencil", "Eraser", "Line", "Rectangle", "Circle", "Polygon", "Duplicate", "ScaleShape", "RotateShape", "ClearLayer"};
    photoEditNames = new String[] {"Resize", "Edge-Detect", "Rotate", "Hue", "Saturation", "Brightness", "Contrast"};
    btnFont = createFont("arial.ttf", 16);
    // Button arrays for top menu
    topBarButtons = new Button[topBarNames.length][];
    topBarSubFirst = new Button[topBarNames[0].length];
    topBarSubSecond = new Button[topBarNames[1].length];
    topBarSubThird = new Button[topBarNames[2].length];
    topBarButtons[0] = topBarSubFirst;
    topBarButtons[1] = topBarSubSecond;
    topBarButtons[2] = topBarSubThird;
    // Button arrays for side menu
    illustratorMenu = new Button[illustratorNames.length];
    photoEditMenu = new Button[photoEditNames.length];
  }

  public void InitialiseMenu()
  {
    for (int topMenu = 0; topMenu < topBarButtons.length; topMenu++)
    {
      topBarButtons[topMenu][0] = new Button(topBarXStart, topBarYStart, topBarWidth, topBarHeight, false, false, topBarNames[topMenu][0], true, false);
      topBarXStart += topBarXIncrease;

      for (int subMenu = 1; subMenu < topBarButtons[topMenu].length; subMenu++)
      {
        topBarButtons[topMenu][subMenu] = new Button(subXStart, subYStart, subBWidth, subBHeight, false, false, topBarNames[topMenu][subMenu], true, false);
        subYStart += topBarYIncrease;
      }
      subXStart += topBarXIncrease;
      subYStart = topBarYIncrease;
    }

    int step = 1, startX = width - sideMenuXInset - 5, startY = 550, increaseX = 60, increaseY = 60;
    for (int sideMenuIll = 0; sideMenuIll < illustratorMenu.length; sideMenuIll++)
    {
      illustratorMenu[sideMenuIll] = new Button(startX, startY, 50, 50, false, true, illustratorNames[sideMenuIll], false, true);

      startX += 60;
      step++;
      if (step == 4)
      {
        startX = width - sideMenuXInset - 5;
        startY += 60;
        step = 1;
      }
    }


  }

  public void DrawMenu()
  {
    DrawTopBar();
    DrawSideMenu();
  }

  public void DisplayMenu()
  {
    noStroke();
    fill(180);
    rect(0, 0, width, topBarHeight);
    textFont(btnFont, btnFontSize);

    stroke(150);
    fill(160);
    rect(width - sideMenuXInset, sideMenuColYInset, sideMenuColWidth, sideMenuColHeight);

    stroke(150);
    fill(160);
    rect(width - sideMenuXInset, sideMenuSelYInset, sideMenuSelWidth, sideMenuSelHeight);

    for (int topMenu = 0; topMenu < topBarButtons.length; topMenu++)
    {
      for (int subMenu = 0; subMenu < topBarButtons[topMenu].length; subMenu++)
      {
        topBarButtons[topMenu][0].DisplayButton();
        if (topBarButtons[topMenu][0].localState)
        {
          topBarButtons[topMenu][subMenu].DisplayButton();
        }
      }
    }

    for (int sideBarIll = 0; sideBarIll < illustratorMenu.length; sideBarIll++)
    {
      illustratorMenu[sideBarIll].DisplayButton();
    }
  }

  public void TopMenuPressed()
  {
    topBarButtons[0][0].TopMenuButtonPressed(topBarButtons[1][0]);
    topBarButtons[0][0].TopMenuButtonPressed(topBarButtons[2][0]);
    topBarButtons[1][0].TopMenuButtonPressed(topBarButtons[0][0]);
    topBarButtons[1][0].TopMenuButtonPressed(topBarButtons[2][0]);
    topBarButtons[2][0].TopMenuButtonPressed(topBarButtons[0][0]);
    topBarButtons[2][0].TopMenuButtonPressed(topBarButtons[1][0]);

    topBarButtons[0][1].TopMenuButtonPressed(topBarButtons[0][0]);
    topBarButtons[0][2].TopMenuButtonPressed(topBarButtons[0][0]);
    topBarButtons[0][3].TopMenuButtonPressed(topBarButtons[0][0]);

    topBarButtons[1][1].TopMenuButtonPressed(topBarButtons[0][1]);
    topBarButtons[1][2].TopMenuButtonPressed(topBarButtons[0][1]);

    topBarButtons[2][1].TopMenuButtonPressed(topBarButtons[0][2]);
    topBarButtons[2][2].TopMenuButtonPressed(topBarButtons[0][2]);

    for (int i = 0; i < topBarButtons.length; i++)
    {
      for (int y = 0; y < topBarButtons[i].length; y++)
      {
        if (topBarButtons[i][y].localState)
        {
          for (int sideMenu = 0; sideMenu < illustratorMenu.length; sideMenu++)
          {
            illustratorMenu[sideMenu].localState = false;
          }
        }
      }
    }
  }

  public void SideMenuPressed()
  {
    illustratorMenu[0].ButtonPressed(illustratorMenu);
  }

  public void DrawTopBar()
  {
    noStroke();
    fill(180);
    rect(0, 0, width, topBarHeight);
    textFont(btnFont, btnFontSize);
  }

  public void DrawSideMenu()
  {
    noStroke();
    fill(180);
    rect(width - sideMenuInset, 0, sideMenuInset, height);
    textFont(btnFont, btnFontSize);
  }
}
class PaintUI extends PApplet
{
  PaintUI()
  {
    super();
    PaintUI.runSketch(new String[] {this.getClass().getSimpleName()}, this);
  }

  public void settings()
  {
    size(500, 200);
  }

  public void setup()
  {
    surface.setTitle("Illustration Tools");
    background(150);
  }

  public void mousePressed()
  {
    println("Mouse pressed in Paint UI");
  }

  public void exit()
  {
    dispose();
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "main" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
