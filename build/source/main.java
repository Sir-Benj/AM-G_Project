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
  firstPoint = new PVector();

  sliderOne = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 35,
                         140, 10, 1, 400, "Size", "px");

  sliderTwo = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 85,
                         140, 10, 0.0f, 255, "Opacity", "%");

  sliderX = new Slider(20, height - 20,  width - 265, 20, 0.0f, photoLayer.width - width, "xbar", "px");

  sliderY = new Slider(width - 225, 40, 25, height - 85, 0.0f, photoLayer.height - height, "ybar", "px");

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

public void mouseDragged()
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

public void mouseReleased()
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
  // for (int i = 0; i < menu.topBarButtons.length; i++)
  // {
  //   for (int y = 0; y < menu.topBarButtons[i].length; y++)
  //   {
  //     if (menu.topBarButtons[i][y].buttonName == "New" && menu.topBarButtons[i][y].localState == true)
  //     {
  //       graphicsFunctions.New(photoLayer, menu.topBarButtons[i][y]);
  //     }
  //     if (menu.topBarButtons[i][y].buttonName == "Save" && menu.topBarButtons[i][y].localState == true)
  //     {
  //       graphicsFunctions.Save(menu.topBarButtons[i][y], selectOne);
  //     }
  //     if (menu.topBarButtons[i][y].buttonName == "Load" && menu.topBarButtons[i][y].localState == true)
  //     {
  //       graphicsFunctions.Load(menu.topBarButtons[i][y], selectOne);
  //     }
  //   }



  //tint(255);
  background(200);
  image(background, 20, 40);
  image(photoLayer, 20 - sliderXValue, 40 - sliderYValue);
  doc.DrawMe();
  image(paintLayer, 20, 40);

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
    photoLayer = createGraphics(imageToLoad.width, imageToLoad.height);
    photoLayer.beginDraw();
    photoLayer.image(imageToLoad, 0, 0);
    photoLayer.endDraw();

    sliderX = new Slider(20, height - 20,  width - 266, 20, 0.0f, (photoLayer.width - width) + 240, "xbar", "px");
    sliderY = new Slider(width - 221, 40, 20, height - 80, 0.0f, (photoLayer.height - height) + 60, "ybar", "px");
  }
}

public boolean OverCanvas()
{
  return (mouseX >= 20 && mouseX <= width - menu.sideMenuInset
          && mouseY >= 40 && mouseY <= height - 20);
}
class Circle extends DrawShape
{
  Circle(String shapeType, PVector mouseStartLoc, PGraphics layer,
            float hue, float sat, float bri, float sWeight, float opacity)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity);
  }

  public void drawThisShape()
  {
    this.layer.beginDraw();
    this.layer.colorMode(HSB);
    DrawSettings();
    if (isDrawing)
    {
      ellipseMode(CORNER);
      float x1 = this.mouseStart.x;
      float y1 = this.mouseStart.y;
      float wid = this.mouseDrag.x - x1;
      float hgt = this.mouseDrag.y - y1;
      ellipse(x1,y1,wid,hgt);
    }
    else
    {
      this.layer.ellipseMode(CORNER);
      float x1 = this.bounds.left;
      float y1 = this.bounds.top;
      float wid = this.bounds.getWidth();
      float hgt = this.bounds.getHeight();
      this.layer.ellipse(x1 - 20, y1 - 40, wid, hgt);

      if (this.isSelected)
      {
        this.layer.ellipseMode(CORNER);
        this.layer.ellipse(x1 - 21, y1 - 41, wid + 2, hgt + 2);
      }
    }
    this.layer.endDraw();
    DefaultDrawSettings();
  }
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

  public void StartNewShape(String shapeType, PVector mouseStartLoc, PGraphics layer,
                            float hue, float sat, float bri, float sWeight, float opacity)
  {
    switch (shapeType)
    {
      case "Rectangle": DrawShape newRectangle = new Rectangle(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity);
                        shapeList.add(newRectangle);
                        currentlyDrawnShape = newRectangle;
                        break;

      case "Circle":    DrawShape newCircle = new Circle(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity);
                        shapeList.add(newCircle);
                        currentlyDrawnShape = newCircle;
                        break;

      case "Line":      DrawShape newLine = new Line(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity);
                        shapeList.add(newLine);
                        currentlyDrawnShape = newLine;
                        break;

      case "Polygon":   DrawShape newPoly = new Polygon(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity);
                        shapeList.add(newPoly);
                        currentlyDrawnShape = newPoly;
                        break;
    }
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

  float hue, sat, bri, opacity, sWeight;

  boolean isSelected = false;
  boolean isDrawing = false;

  Rect bounds;

  PGraphics layer;

  DrawShape(String shapeToDraw, PVector startPoint, PGraphics layer,
    float hue, float sat, float bri, float sWeight, float opacity)
  {
    this.isDrawing = true;
    this.shapeToDraw = shapeToDraw;
    this.mouseStart = startPoint;
    this.mouseDrag = startPoint;
    this.layer = layer;
    this.hue = hue;
    this.sat = sat;
    this.bri = bri;
    this.opacity = opacity;
    this.sWeight = sWeight;
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

  public void AddToPoints(PVector mouseStart) {}

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
    point(this.mouseStart.x, this.mouseStart.y);
  }

  public void DrawSettings()
  {
    if (isDrawing)
    {
      strokeWeight(sWeight);
      noStroke();
      fill(this.hue,
           this.sat,
           this.bri,
           this.opacity);
    }
    else
    {
      this.layer.strokeWeight(sWeight);
      this.layer.noStroke();
      this.layer.fill(this.hue,
                      this.sat,
                      this.bri,
                      this.opacity);
    }

    if (this.isSelected)
    {
      this.layer.noFill();
      this.layer.strokeWeight(2);
      this.layer.stroke(255 - this.hue,
                        255 - this.sat,
                        255 - this.bri);
    }
  }

  public void DefaultDrawSettings()
  {
    stroke(0);
    strokeWeight(1);
    noFill();
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

  public void ShapeStart(String name, PVector mouseStart, PGraphics layer, Document doc,
                      ColourPicker colourPicker, float sWeight, float opacity)
  {
     doc.StartNewShape(name, mouseStart, layer,
                       colourPicker._hueVal,
                       colourPicker._satVal,
                       colourPicker._briVal,
                       sWeight, opacity);
  }

  public void ShapeDrag(Document doc, PVector mouseDrag)
  {
    if (doc.currentlyDrawnShape == null)
    {
      return;
    }
    doc.currentlyDrawnShape.WhileDrawingShape(mouseDrag);
  }

  public void ShapeFinal(Document doc, PVector mouseFinal)
  {
    if (doc.currentlyDrawnShape == null)
    {
      return;
    }
    doc.currentlyDrawnShape.FinishDrawingShape(mouseFinal);
    doc.currentlyDrawnShape = null;
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
class Line extends DrawShape
{
  Line(String shapeType, PVector mouseStartLoc, PGraphics layer,
       float hue, float sat, float bri, float sWeight, float opacity)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity);
  }

  public void drawThisShape()
  {
    this.layer.beginDraw();
    this.layer.colorMode(HSB);
    DrawSettings();
    if (isDrawing)
    {
      stroke(this.hue,
             this.sat,
             this.bri,
             this.opacity);
      float x1 = this.mouseStart.x;
      float y1 = this.mouseStart.y;
      float x2 = this.mouseDrag.x;
      float y2 = this.mouseDrag.y;
      line(x1, y1, x2, y2);
    }
    else
    {
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      float x1 = this.bounds.x1;
      float y1 = this.bounds.y1;
      float wid = this.bounds.x2;
      float hgt = this.bounds.y2;
      this.layer.line(x1 - 20, y1 - 40, wid - 20, hgt - 40);

      if (this.isSelected)
      {
        this.layer.line(x1 - 21, y1 - 21, wid - 18, hgt + 38);
      }
    }
    this.layer.endDraw();
    DefaultDrawSettings();
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
class Polygon extends DrawShape
{
  ArrayList<PVector> polyPoints;
  PVector newMousePos;
  PShape poly;
  Boolean pickFinished;

  Polygon(String shapeType, PVector mouseStartLoc, PGraphics layer,
          float hue, float sat, float bri, float sWeight, float opacity)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity);
    this.polyPoints = new ArrayList<PVector>();
  }

  public void AddToPoints(PVector mousePos)
  {
    this.polyPoints.add(mousePos);
  }

  public void FinishDrawingShape()
  {
    PVector xyMin, xyMax;
    xyMin = new PVector();
    xyMax = new PVector();

    float xMax = 0, xMin = width, yMax = 0, yMin = height;

    for (PVector v : polyPoints)
    {
      if (v.x > xMax)
      {
        xMax = v.x;
      }
      else if (v.x < xMin)
      {
        xMin = v.x;
      }

      if (v.y > yMax)
      {
        yMax = v.y;
      }
      else if (v.y < yMin)
      {
        yMin = v.y;
      }
    }

    xyMin.x = xMin;
    xyMin.y = yMin;

    xyMax.x = xMax;
    xyMax.y = yMax;

    setShapeBounds(xyMin, xyMax);

    this.isDrawing = false;

  }

  public void drawThisShape()
  {
    this.layer.beginDraw();
    smooth();
    this.layer.colorMode(HSB);
    if (isDrawing)
    {
      this.poly = createShape();
      this.poly.beginShape();
      this.poly.strokeWeight(this.sWeight);
      this.poly.stroke(this.hue,
                       this.sat,
                       this.bri,
                       this.opacity);
      this.poly.noFill();
      for (PVector v : this.polyPoints)
      {
        this.poly.vertex(v.x, v.y);
      }
      this.poly.endShape();
      shape(poly);
    }
    else
    {
      this.poly = createShape();
      this.poly.beginShape();
      this.poly.strokeWeight(this.sWeight);
      this.poly.noStroke();
      this.poly.fill(this.hue,
                     this.sat,
                     this.bri,
                     this.opacity);
      for (PVector v : this.polyPoints)
      {
        this.poly.vertex(v.x - 20, v.y - 40);
      }
      this.poly.endShape(CLOSE);
      this.layer.smooth();
      this.layer.shape(poly);

      if (this.isSelected)
      {
        this.poly = createShape();
        this.poly.beginShape();
        this.poly.strokeWeight(this.sWeight);
        this.poly.stroke(255 - this.hue,
                         255 - this.sat,
                         255 - this.hue);
        this.poly.noFill();
        for (PVector v : this.polyPoints)
        {
          this.poly.vertex(v.x - 21, v.y - 38);
        }
        this.poly.endShape(CLOSE);
        this.layer.shape(poly);

      }
    }
    this.layer.endDraw();
    DefaultDrawSettings();
  }
}
class Rect
{
  float left, top, right, bottom;
  float x1, y1, x2, y2;

  Rect(float xOne, float yOne, float xTwo, float yTwo)
  {
    setRect(xOne, yOne, xTwo, yTwo);
    OriginalMousePos(xOne, yOne, xTwo, yTwo);
  }

  Rect(PVector vecOne, PVector vecTwo)
  {
    setRect(vecOne.x, vecOne.y, vecTwo.x, vecTwo.y);
    OriginalMousePos(vecOne.x, vecOne.y, vecTwo.x, vecTwo.y);
  }

  public void OriginalMousePos(float xOne, float yOne, float xTwo, float yTwo)
  {
    this.x1 = xOne;
    this.y1 = yOne;
    this.x2 = xTwo;
    this.y2 = yTwo;
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
class Rectangle extends DrawShape
{
  Rectangle(String shapeType, PVector mouseStartLoc, PGraphics layer,
            float hue, float sat, float bri, float sWeight, float opacity)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity);
  }

  public void drawThisShape()
  {
    this.layer.beginDraw();
    this.layer.colorMode(HSB);
    DrawSettings();
    if (isDrawing)
    {
      float x1 = this.mouseStart.x;
      float y1 = this.mouseStart.y;
      float wid = this.mouseDrag.x - x1;
      float hgt = this.mouseDrag.y - y1;
      rect(x1,y1,wid,hgt);
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
        this.layer.rect(x1 - 21, y1 - 41, wid + 2, hgt + 2);
      }
    }
    this.layer.endDraw();
    DefaultDrawSettings();
  }
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

  public float DrawSliderMenu(float retValue)
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

    return retValue;
  }

  public float DrawSliderHorizontal(float retValue)
  {
    float sliderPos = map(retValue, mapValueLow, mapValueHigh, 0.0f, barWidth);

    stroke(80);
    fill(100);
    rect(xBarPos, yBarPos, barWidth + barHeight, barHeight);

    if(mousePressed && mouseX >=  xBarPos && mouseX <= (xBarPos + barWidth)
       && mouseY >= yBarPos && mouseY <= yBarPos + barHeight)
    {
      sliderPos = mouseX - xBarPos;
      retValue = map(sliderPos, 0.0f, barWidth, mapValueLow, mapValueHigh);
    }

    stroke(1);
    fill(50);
    rect(sliderPos + xBarPos, yBarPos, barHeight, barHeight);

    return retValue;
  }

  public float DrawSliderVertical(float retValue)
  {
    float sliderPos = map(retValue, mapValueLow, mapValueHigh, 0.0f, barHeight);

    stroke(80);
    fill(100);
    rect(xBarPos, yBarPos, barWidth, barHeight + barWidth);

    if(mousePressed && mouseX >=  xBarPos && mouseX <= (xBarPos + barWidth)
       && mouseY >= yBarPos && mouseY <= yBarPos + barHeight)
    {
      sliderPos = mouseY - yBarPos;
      retValue = map(sliderPos, 0.0f, barHeight, mapValueLow, mapValueHigh);
    }

    stroke(1);
    fill(50);
    rect(xBarPos, yBarPos + sliderPos, barWidth, barWidth);

    return retValue;
  }
}
class Button
{
  protected int buttonX, buttonY, buttonWidth, buttonHeight, smoothing;
  protected String buttonName;
  protected boolean isSmooth, hasBorder, showName, hasIcon, localState, invert, inverted, menuDisplayed;
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
    menuDisplayed = false;

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

  public void TopMenuButtonPressed(Button[] btns)
  {
    for (int i = 1; i < btns.length; i++)
    {
      if (btns[i].OverButton() && !btns[i].LocalState())
      {
        btns[i].localState = true;
        btns[0].localState = false;
        for (int j = 1; j < btns.length; j++ )
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

  public void SingleButtonPress()
  {
    if (OverButton() && !localState)
    {
      localState = true;
    }
    else if (!OverButton() && localState)
    {
      //localState = false;
    }
    else if (OverButton() && localState)
    {
      localState = false;
    }
  }

  public void NotOverButton()
  {
    if (!OverButton() && localState)
    {
      localState = false;
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
  String[] topBarFile;
  String[] topBarEdit;
  String[] topBarFilter;

  Button[] topBarFileBtns;
  Button[] topBarEditBtns;
  Button[] topBarFilterBtns;

  String[] illustratorNames;
  Button[] illustratorMenu;

  String[] photoEditNames;
  Button[] photoEditMenu;

  int btnFontSize = 16, sideMenuInset = 200,
      topBarXStart = 0, topBarYStart = 0, topBarWidth = 100, topBarHeight = 20,
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
    topBarFile = new String[] {"File", "New", "Save", "Load"};
    topBarEdit = new String[] {"Edit", "Undo", "Redo"};
    topBarFilter = new String[] {"Filter", "Blur", "Sharpen", "Greyscale", "Monochrome"};

    illustratorNames = new String[] {"Pencil", "Eraser", "Line", "Rectangle", "Circle", "Polygon", "Duplicate", "ScaleShape", "RotateShape", "ClearLayer"};
    photoEditNames = new String[] {"Resize", "Edge-Detect", "Rotate", "Hue", "Saturation", "Brightness", "Contrast"};
    btnFont = createFont("arial.ttf", 16);

    // Button arrays for top menu
    topBarFileBtns = new Button[topBarFile.length];
    topBarEditBtns = new Button[topBarEdit.length];
    topBarFilterBtns = new Button[topBarFilter.length];

    illustratorMenu = new Button[illustratorNames.length];
    photoEditMenu = new Button[photoEditNames.length];
  }

  public void InitialiseMenu()
  {
    MenuButtonsInitialise(topBarFile, topBarFileBtns, topBarXStart, topBarYStart, topBarWidth, topBarHeight);
    MenuButtonsInitialise(topBarEdit, topBarEditBtns, topBarXStart + topBarWidth, topBarYStart, topBarWidth, topBarHeight);
    MenuButtonsInitialise(topBarFilter, topBarFilterBtns, topBarXStart + (topBarWidth * 2), topBarYStart, topBarWidth, topBarHeight);

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

    TopBarDisplay(topBarFileBtns, topBarEditBtns, topBarFilterBtns);

    for (int sideBarIll = 0; sideBarIll < illustratorMenu.length; sideBarIll++)
    {
      illustratorMenu[sideBarIll].DisplayButton();
    }
  }

  public void TopMenuPressed()
  {
    topBarFileBtns[0].SingleButtonPress();
    {
      if (topBarFileBtns[0].localState)
      {
        topBarFileBtns[0].TopMenuButtonPressed(topBarFileBtns);
      }
    }
    topBarFileBtns[0].NotOverButton();

    topBarEditBtns[0].SingleButtonPress();
    {
      if (topBarEditBtns[0].localState)
      {
        topBarEditBtns[0].TopMenuButtonPressed(topBarEditBtns);
      }
    }
    topBarEditBtns[0].NotOverButton();

    topBarFilterBtns[0].SingleButtonPress();
    {
      if (topBarFilterBtns[0].localState)
      {
        topBarFilterBtns[0].TopMenuButtonPressed(topBarFilterBtns);
      }
    }
    topBarFilterBtns[0].NotOverButton();
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

  public void MenuButtonsInitialise(String[] names, Button[] buttons, int tXstart, int tYstart, int tWidth, int tHeight)
  {
    for (int topMenu = 0; topMenu < names.length; topMenu++)
    {
      buttons[topMenu] = new Button(tXstart, tYstart, tWidth, tHeight, false, false, names[topMenu], true, false);
      tYstart += tHeight;
    }
  }

  public void TopBarDisplay(Button[] topBarBtns1, Button[] topBarBtns2, Button[] topBarBtns3)
  {
    for (int topMenu = 0; topMenu < topBarBtns1.length; topMenu++)
    {
      topBarBtns1[0].DisplayButton();
      if (topBarBtns1[0].localState && !topBarBtns2[0].localState && !topBarBtns3[0].localState)
      {
        topBarBtns1[topMenu].DisplayButton();
      }
    }
    for (int topMenu = 0; topMenu < topBarBtns2.length; topMenu++)
    {
      topBarBtns2[0].DisplayButton();
      if (!topBarBtns1[0].localState && topBarBtns2[0].localState && !topBarBtns3[0].localState)
      {
        topBarBtns2[topMenu].DisplayButton();
      }
    }
    for (int topMenu = 0; topMenu < topBarBtns3.length; topMenu++)
    {
      topBarBtns3[0].DisplayButton();
      if (!topBarBtns1[0].localState && !topBarBtns2[0].localState && topBarBtns3[0].localState)
      {
        topBarBtns3[topMenu].DisplayButton();
      }
    }
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
