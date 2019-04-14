import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.LinkedList; 
import java.util.Iterator; 

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

int xInset = 245, yInset = 120;

float sliderOneValue = 5;
float sliderTwoValue = 255;
float sliderXValue = 0;
float sliderYValue = 0;
float sliderChangeX = 0;
float sliderChangeY = 0;
float sliderRotateValue = 0;
float sliderScaleValue = 1;
float sliderPointInputvalue = 0.1f;
float sliderNewXValue = 100;
float sliderNewYValue = 100;

Button control;
Button[] btns;
Button[][] buttonMenu;

Slider sliderStrokeW;
Slider sliderOpacity;
Slider sliderX;
Slider sliderY;
Slider sliderChangeXPos;
Slider sliderChangeYPos;
Slider sliderRotation;
Slider sliderScale;
Slider sliderPointInput;
Slider sliderNewX;
Slider sliderNewY;

Button confirm;
Button cancel;

boolean clicked, left, right, newPoly = false, isFinished = true;
boolean isFilterBlur, isFilterSharpen, isFilterGreyscale, isFilterMonochrome, isFilterEdgeDetect;
boolean isEditResize, isEditHue, isEditSaturation, isEditBrightness, isEditContrast;

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
PImage destination;

MessageQueue messageQueue;
GraphicsFunctions graphicsFunctions;

String path;
File selectOne;

PVector mouseStart, mouseDrag, mouseFinal, firstPoint;

boolean pressed = false;
boolean released = true;

Document doc;

DrawShape moveShape;
Boolean selectingShape = false;

public void settings()
{
  size(3*displayWidth>>2, 3*displayHeight>>2);
}

public void setup()
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

  moveShape = new DrawShape();

  sliderStrokeW = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 25,
                         140, 10, 1, 400, "Stroke Weight", "px");

  sliderOpacity = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 65,
                         140, 10, 0.0f, 255, "Opacity", "%");

  sliderX = new Slider(20, height - 20,  width - 265, 20, 0.0f, photoLayer.width - width, "xbar", "px");

  sliderY = new Slider(width - 225, 40, 25, height - 85, 0.0f, photoLayer.height - height, "ybar", "px");

  sliderChangeXPos = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 25,
                         140, 10, -paintLayer.width , paintLayer.width, "Offset X", "px");

  sliderChangeYPos = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 65,
                         140, 10, -paintLayer.height, paintLayer.height, "Offset Y", "px");

  sliderRotation = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 25,
                         140, 10, 0, 360, "Rotation Angle", "Degrees");

  sliderScale = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 65,
                         140, 10, 0.1f, 5, "Scale", "Multiplier");

  sliderPointInput = new Slider(600, 50, 140, 10, -1, 3, "Brightness/Contrast", "Value");

  sliderNewX = new Slider(600, 40, 140, 10, 1, width, "Width", "px");

  sliderNewY = new Slider(600, 60, 140, 10, 1, height, "Height", "px");

  confirm = new Button(750, 50, 140, 40, false, true, "  Confirm Changes", true, false);

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
  confirm.SingleButtonPress();

  mouseStart = new PVector();

  if (menu.selectShape.localState)
  {
    doc.TrySelect(new PVector(mouseX, mouseY));
    selectingShape = true;
  }
  else if (!menu.selectShape.localState && selectingShape)
  {
    for (DrawShape s : doc.shapeList)
    {
      s.isSelected = false;
    }
    selectingShape = false;
  }

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

  for (int i = 0; i < menu.drawShapeMenu.length; i++)
  {
    if (menu.drawShapeMenu[i].buttonName == "Rectangle" && menu.drawShapeMenu[i].localState == true && OverCanvas())
    {
      graphicsFunctions.ShapeStart("Rectangle", mouseStart, paintLayer,
                                       doc ,colourPicker, sliderOneValue, sliderTwoValue, menu.filledShape.localState);
    }
    if (menu.drawShapeMenu[i].buttonName == "Circle" && menu.drawShapeMenu[i].localState == true && OverCanvas())
    {
      graphicsFunctions.ShapeStart("Circle", mouseStart, paintLayer,
                                       doc ,colourPicker, sliderOneValue, sliderTwoValue, menu.filledShape.localState);
    }
    if (menu.drawShapeMenu[i].buttonName == "Line" && menu.drawShapeMenu[i].localState == true && OverCanvas())
    {
      graphicsFunctions.ShapeStart("Line", mouseStart, paintLayer,
                                       doc ,colourPicker, sliderOneValue, sliderTwoValue, menu.filledShape.localState);
    }
    if (menu.drawShapeMenu[i].buttonName == "Polygon" && menu.drawShapeMenu[i].localState == true && OverCanvas())
    {
      if (!newPoly)
      {
        graphicsFunctions.ShapeStart("Polygon", mouseStart, paintLayer,
                                     doc ,colourPicker, sliderOneValue, sliderTwoValue, menu.filledShape.localState);
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
    if (menu.drawShapeMenu[i].buttonName == "Curve" && menu.drawShapeMenu[i].localState == true && OverCanvas())
    {
      if (!newPoly)
      {
        graphicsFunctions.ShapeStart("Curve", mouseStart, paintLayer,
                                     doc ,colourPicker, sliderOneValue, sliderTwoValue, menu.filledShape.localState);
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

public void mouseDragged()
{
  if (pressed && !released)
  {
    mouseDrag.x = mouseX;
    mouseDrag.y = mouseY;
    for (int i = 0; i < menu.drawShapeMenu.length; i++)
    {
      if (menu.drawShapeMenu[i].buttonName == "Rectangle" && menu.drawShapeMenu[i].localState == true)
      {
        graphicsFunctions.ShapeDrag(doc, mouseDrag);
      }
      if (menu.drawShapeMenu[i].buttonName == "Circle" && menu.drawShapeMenu[i].localState == true)
      {
        graphicsFunctions.ShapeDrag(doc, mouseDrag);
      }
      if (menu.drawShapeMenu[i].buttonName == "Line" && menu.drawShapeMenu[i].localState == true)
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

    for (int i = 0; i < menu.drawShapeMenu.length; i++)
    {
      if (menu.drawShapeMenu[i].buttonName == "Rectangle" && menu.drawShapeMenu[i].localState == true)
      {
        graphicsFunctions.ShapeFinal(doc, mouseFinal);
      }
      if (menu.drawShapeMenu[i].buttonName == "Circle" && menu.drawShapeMenu[i].localState == true)
      {
        graphicsFunctions.ShapeFinal(doc, mouseFinal);
      }
      if (menu.drawShapeMenu[i].buttonName == "Line" && menu.drawShapeMenu[i].localState == true)
      {
        graphicsFunctions.ShapeFinal(doc, mouseFinal);
      }
      if (menu.drawShapeMenu[i].buttonName == "Polygon" && menu.drawShapeMenu[i].localState == true)
      {
        if (isFinished)
        {
          graphicsFunctions.ShapeFinal(doc, mouseFinal);
          newPoly = false;
          isFinished = false;
        }
      }
      if (menu.drawShapeMenu[i].buttonName == "Curve" && menu.drawShapeMenu[i].localState == true)
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
  paintLayer.clear();
  paintLayer.endDraw();
  photoLayer.beginDraw();
  photoLayer.endDraw();
  combineLayers.beginDraw();
  combineLayers.endDraw();

  for (int i = 0; i < menu.drawShapeMenu.length; i++)
  {
    if (menu.drawShapeMenu[i].buttonName == "Pencil" && menu.drawShapeMenu[i].localState == true && OverCanvas())
    {
      graphicsFunctions.Pencil(paintLayer, colourPicker, sliderOneValue, sliderTwoValue);
    }
    if (menu.drawShapeMenu[i].buttonName == "Eraser" && menu.drawShapeMenu[i].localState == true)
    {
      graphicsFunctions.Eraser(paintLayer, sliderOneValue);
    }
    if (menu.drawShapeMenu[i].buttonName == "ClearLayer" && menu.drawShapeMenu[i].localState == true)
    {
      graphicsFunctions.ClearLayer(paintLayer, menu.drawShapeMenu[i], doc);
    }
  }

  for (int i = 0; i < menu.selectShapeMenu.length; i++)
  {
    if (menu.selectShapeMenu[i].buttonName == "ChangeColour" && menu.selectShapeMenu[i].localState == true)
    {
      graphicsFunctions.ChangeShapeHSB(doc, colourPicker, sliderOneValue, sliderTwoValue, menu.filledShape.localState);
    }
    else if (menu.selectShapeMenu[i].buttonName == "ChangePosition" && menu.selectShapeMenu[i].localState == true)
    {
      graphicsFunctions.ChangeShapePosition(doc, sliderChangeX, sliderChangeY);
    }
    else if (menu.selectShapeMenu[i].buttonName == "RotateShape" && menu.selectShapeMenu[i].localState == true)
    {
      graphicsFunctions.RotateShape(doc, sliderRotateValue);
    }
    else if (menu.selectShapeMenu[i].buttonName == "ScaleShape" && menu.selectShapeMenu[i].localState == true)
    {
      graphicsFunctions.ScaleShape(doc, sliderScaleValue);
    }
    else if (menu.selectShapeMenu[i].buttonName == "DeleteShape" && menu.selectShapeMenu[i].localState == true)
    {
      if (doc.shapeList.size() > 0)
      {
        graphicsFunctions.DeleteShape(doc);
      }
      menu.selectShapeMenu[i].localState = false;
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

  for (int i = 1; i < menu.topBarFilterBtns.length; i++)
  {
    if (menu.topBarFilterBtns[i].buttonName == "Blur" && menu.topBarFilterBtns[i].localState == true)
    {
      graphicsFunctions.Blur(photoLayer);
      menu.topBarFilterBtns[i].localState = false;
    }
    else if (menu.topBarFilterBtns[i].buttonName == "Sharpen" && menu.topBarFilterBtns[i].localState == true)
    {
      graphicsFunctions.Sharpen(photoLayer);
      menu.topBarFilterBtns[i].localState = false;
    }
    else if (menu.topBarFilterBtns[i].buttonName == "Greyscale" && menu.topBarFilterBtns[i].localState == true)
    {
      graphicsFunctions.Greyscale(photoLayer);
      menu.topBarFilterBtns[i].localState = false;
    }
    else if (menu.topBarFilterBtns[i].buttonName == "Monochrome" && menu.topBarFilterBtns[i].localState == true)
    {
      graphicsFunctions.Monochrome(photoLayer);
      menu.topBarFilterBtns[i].localState = false;
    }
    else if (menu.topBarFilterBtns[i].buttonName == "Edge-Detect" && menu.topBarFilterBtns[i].localState == true)
    {
      graphicsFunctions.EdgeDetect(photoLayer);
      menu.topBarFilterBtns[i].localState = false;
    }
  }

  for (int i = 1; i < menu.topBarPhotoEditBtns.length; i++)
  {
    if (menu.topBarPhotoEditBtns[i].buttonName == "Resize" && menu.topBarPhotoEditBtns[i].localState == true)
    {
      if (confirm.localState)
      {
        colorMode(RGB);
        destination = createImage((int)sliderNewXValue, (int)sliderNewYValue, RGB);
        graphicsFunctions.Resize(photoLayer, destination);
        photoLayer.clear();
        photoLayer = createGraphics(destination.width, destination.height);
        photoLayer.beginDraw();
        photoLayer.image(destination, 0, 0);
        photoLayer.endDraw();
        menu.topBarPhotoEditBtns[i].localState = false;
        confirm.localState = false;
        colorMode(HSB);
      }
    }
    else if (menu.topBarPhotoEditBtns[i].buttonName == "Hue" && menu.topBarPhotoEditBtns[i].localState == true)
    {
      if (confirm.localState)
      {
        menu.topBarPhotoEditBtns[i].localState = false;
        graphicsFunctions.Hue(photoLayer, colourPicker, confirm.localState);
        confirm.localState = false;
      }
      else
      {
        graphicsFunctions.Hue(photoLayer, colourPicker, confirm.localState);
      }
    }
    else if (menu.topBarPhotoEditBtns[i].buttonName == "Saturation" && menu.topBarPhotoEditBtns[i].localState == true)
    {
      if (confirm.localState)
      {
        menu.topBarPhotoEditBtns[i].localState = false;
        graphicsFunctions.Saturation(photoLayer, colourPicker, confirm.localState);
        confirm.localState = false;
      }
      else
      {
        graphicsFunctions.Saturation(photoLayer, colourPicker, confirm.localState);
      }
    }
    else if (menu.topBarPhotoEditBtns[i].buttonName == "Brightness" && menu.topBarPhotoEditBtns[i].localState == true)
    {
      if (confirm.localState)
      {
        menu.topBarPhotoEditBtns[i].localState = false;
        graphicsFunctions.Brightness(photoLayer, sliderPointInputvalue, confirm.localState);
        confirm.localState = false;
      }
      else
      {
        graphicsFunctions.Brightness(photoLayer, sliderPointInputvalue, confirm.localState);
      }
    }
    else if (menu.topBarPhotoEditBtns[i].buttonName == "Contrast" && menu.topBarPhotoEditBtns[i].localState == true)
    {
      if (confirm.localState)
      {
        menu.topBarPhotoEditBtns[i].localState = false;
        graphicsFunctions.Contrast(photoLayer, sliderPointInputvalue, confirm.localState);
        confirm.localState = false;
      }
      else
      {
        graphicsFunctions.Contrast(photoLayer, sliderPointInputvalue, confirm.localState);
      }
    }
  }

  background(200);
  image(background, 20, 100);
  image(photoLayer, 20 - sliderXValue, 100 - sliderYValue);
  doc.DrawMe();
  image(paintLayer, 20, 100);

  imageToSaveOne = photoLayer.get(0, 0, photoLayer.width, photoLayer.height);
  imageToSaveTwo = paintLayer.get(0, 0, paintLayer.width, paintLayer.height);

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

  for (int i = 1; i < menu.topBarPhotoEditBtns.length; i++)
  {
    if (menu.topBarPhotoEditBtns[i].localState == true && menu.topBarPhotoEditBtns[i].buttonName == "Contrast")
    {
      sliderPointInputvalue = sliderPointInput.DrawSliderMenu(sliderPointInputvalue);
      confirm.DisplayButton();
    }
    else if (menu.topBarPhotoEditBtns[i].localState == true && menu.topBarPhotoEditBtns[i].buttonName == "Brightness")
    {
      sliderPointInputvalue = sliderPointInput.DrawSliderMenu(sliderPointInputvalue);
      confirm.DisplayButton();
    }
    else if (menu.topBarPhotoEditBtns[i].localState == true && menu.topBarPhotoEditBtns[i].buttonName == "Saturation")
    {
      confirm.DisplayButton();
    }
    else if (menu.topBarPhotoEditBtns[i].localState == true && menu.topBarPhotoEditBtns[i].buttonName == "Hue")
    {
      confirm.DisplayButton();
    }
    else if (menu.topBarPhotoEditBtns[i].localState == true && menu.topBarPhotoEditBtns[i].buttonName == "Resize")
    {
      sliderNewXValue = sliderNewX.DrawSliderMenu(sliderNewXValue);
      sliderNewYValue = sliderNewY.DrawSliderMenu(sliderNewYValue);
      confirm.DisplayButton();
    }
  }

  for (int i = 0; i < menu.drawShapeMenu.length; i++)
  {
    if (menu.drawShapeMenu[i].localState == true && menu.drawShapeMenu[i].buttonName != "Eraser")
    {
      sliderOneValue = sliderStrokeW.DrawSliderMenu(sliderOneValue);
      sliderTwoValue = sliderOpacity.DrawSliderMenu(sliderTwoValue);
    }
    else if (menu.drawShapeMenu[i].localState == true && menu.drawShapeMenu[i].buttonName == "Eraser")
    {
      sliderOneValue = sliderStrokeW.DrawSliderMenu(sliderOneValue);
    }
  }

  for (int i = 0; i < menu.selectShapeMenu.length; i++)
  {
    if (menu.selectShapeMenu[i].localState == true && menu.selectShapeMenu[i].buttonName == "ChangeColour")
    {
      sliderOneValue = sliderStrokeW.DrawSliderMenu(sliderOneValue);
      sliderTwoValue = sliderOpacity.DrawSliderMenu(sliderTwoValue);
    }

    if (menu.selectShapeMenu[i].localState == true && menu.selectShapeMenu[i].buttonName == "ChangePosition")
    {
      sliderChangeX = sliderChangeXPos.DrawSliderMenu(sliderChangeX);
      sliderChangeY = sliderChangeYPos.DrawSliderMenu(sliderChangeY);
    }

    if (menu.selectShapeMenu[i].localState == true && menu.selectShapeMenu[i].buttonName == "RotateShape")
    {
      sliderRotateValue = sliderRotation.DrawSliderMenu(sliderRotateValue);
    }

    if (menu.selectShapeMenu[i].localState == true && menu.selectShapeMenu[i].buttonName == "ScaleShape")
    {
      sliderScaleValue = sliderScale.DrawSliderMenu(sliderScaleValue);
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
          && mouseY >= 95 && mouseY <= height - 20);
}
class Circle extends DrawShape
{
  Circle(String shapeType, PVector mouseStartLoc, PGraphics layer,
            float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
  }

  public void drawThisShape()
  {
    this.layer.beginDraw();
    this.layer.colorMode(HSB);
    DrawSettings();
    if (isDrawing)
    {
      this.layer.ellipseMode(CORNER);
      float x1 = this.mouseStart.x - 20;
      float y1 = this.mouseStart.y - 100;
      float wid = this.mouseDrag.x - x1;
      float hgt = this.mouseDrag.y - y1;
      this.layer.ellipse(x1, y1, wid - 20, hgt - 100);
    }
    else
    {
      float x1 = this.bounds.left;
      float y1 = this.bounds.top;
      float wid = this.bounds.getWidth();
      float hgt = this.bounds.getHeight();

      if (this.isSelected)
      {
        this.layer.ellipseMode(CORNER);
        this.layer.strokeWeight(this.sWeight + 5);
        this.layer.stroke(255 - this.hue, 255 - this.sat, 255 - this.bri);
        this.layer.pushMatrix();
        this.layer.scale(this.scaleValue);
        this.layer.rotate(this.rotateValue);
        this.layer.ellipse(x1 - 20, y1 - 100, wid, hgt);
        this.layer.popMatrix();
      }

      this.layer.ellipseMode(CORNER);
      this.layer.strokeWeight(this.sWeight);
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      this.layer.pushMatrix();
      this.layer.scale(this.scaleValue);
      this.layer.rotate(this.rotateValue);
      this.layer.ellipse(x1 - 20, y1 - 100, wid, hgt);
      this.layer.popMatrix();
    }
    this.layer.endDraw();
  }
}
class ColourPicker
{
  float barWidth = 128;
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
class Curve extends DrawShape
{
  //ArrayList<PVector> polyPoints;
  PVector newMousePos;
  PShape poly;
  Boolean pickFinished;

  Curve(String shapeType, PVector mouseStartLoc, PGraphics layer,
        float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
    polyPoints = new ArrayList<PVector>();
  }

  public void AddToPoints(PVector mousePos)
  {
    this.polyPoints.add(mousePos);
  }

  public void FinishDrawingShape(PVector endPoint)
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

    println(xyMin);
    println(xyMax);

    setShapeBounds(xyMin, xyMax);

    this.isDrawing = false;

  }

  public void drawThisShape()
  {
    this.layer.beginDraw();
    //smooth();
    this.layer.colorMode(HSB);
    //DrawSettings();
    if (isDrawing)
    {
      this.layer.beginShape();
      this.layer.strokeWeight(this.sWeight);
      this.layer.stroke(this.hue,
                       this.sat,
                       this.bri,
                       this.opacity);
      if (isFilled)
      {
        this.layer.fill(this.hue,
                       this.sat,
                       this.bri,
                       this.opacity);
      }
      else
      {
        this.layer.noFill();
      }

      for (PVector v : this.polyPoints)
      {
        this.layer.curveVertex(v.x - 20, v.y - 100);
        println(v);
      }

      if (isFilled)
      {
        this.layer.endShape(CLOSE);
      }
      else
      {
        this.layer.endShape();
      }
    }
    else
    {
      if (this.isSelected)
      {
        this.layer.beginShape();
        this.layer.strokeWeight(this.sWeight + 5);
        this.layer.stroke(255 - this.hue,
                         255 - this.sat,
                         255 - this.hue);
        this.layer.noFill();
        for (PVector v : this.polyPoints)
        {
          this.layer.curveVertex(v.x - 20, v.y - 100);
        }
        this.layer.pushMatrix();
        this.layer.scale(this.scaleValue);
        this.layer.rotate(this.rotateValue);
        this.layer.endShape();
        this.layer.popMatrix();
      }
    }

    this.layer.beginShape();
    this.layer.strokeWeight(this.sWeight);
    this.layer.stroke(this.hue,
                     this.sat,
                     this.bri,
                     this.opacity);

    if (isFilled)
    {
      this.layer.fill(this.hue,
                     this.sat,
                     this.bri,
                     this.opacity);
    }
    else
    {
      this.layer.noFill();
    }

    for (PVector v : this.polyPoints)
    {
      this.layer.curveVertex(v.x - 20, v.y - 100);
    }

    this.layer.pushMatrix();
    this.layer.scale(this.scaleValue);
    this.layer.rotate(this.rotateValue);
    if (this.isFilled)
    {
      this.layer.endShape(CLOSE);
    }
    else
    {
      this.layer.endShape();
    }
    this.layer.popMatrix();
    this.layer.endDraw();
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
                            float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
  {
    switch (shapeType)
    {
      case "Rectangle": DrawShape newRectangle = new Rectangle(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
                        shapeList.add(newRectangle);
                        currentlyDrawnShape = newRectangle;
                        break;

      case "Circle":    DrawShape newCircle = new Circle(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
                        shapeList.add(newCircle);
                        currentlyDrawnShape = newCircle;
                        break;

      case "Line":      DrawShape newLine = new Line(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
                        shapeList.add(newLine);
                        currentlyDrawnShape = newLine;
                        break;

      case "Polygon":   DrawShape newPoly = new Polygon(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
                        shapeList.add(newPoly);
                        currentlyDrawnShape = newPoly;
                        break;
      case "Curve":   DrawShape newCurve = new Curve(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
                        shapeList.add(newCurve);
                        currentlyDrawnShape = newCurve;
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

  float hue, sat, bri, opacity, sWeight, rotateValue, scaleValue;

  boolean isSelected = false;
  boolean isDrawing = false;
  boolean isFilled = false;

  ArrayList<PVector> polyPoints;
  Rect bounds;

  PGraphics layer;

  DrawShape() {}

  DrawShape(String shapeToDraw, PVector startPoint, PGraphics layer,
    float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
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
    this.isFilled = filled;
    this.rotateValue = 0;
    this.scaleValue = 1;
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
    this.bounds = new Rect(vecOne, vecTwo);
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
      this.layer.strokeWeight(sWeight);
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      if (isFilled)
      {
        this.layer.fill(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      }
      else
      {
        this.layer.noFill();
      }
    }
    else
    {
      this.layer.strokeWeight(sWeight);
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      if (isFilled)
      {
        this.layer.fill(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      }
      else
      {
        this.layer.noFill();
      }
    }

    if (this.isSelected)
    {
      if (this.isFilled)
      {
        this.layer.fill(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      }
      else
      {
        this.layer.noFill();
      }

      this.layer.strokeWeight(5);
      this.layer.stroke(255 - this.hue,
                        255 - this.sat,
                        255 - this.bri);
    }
  }
}
class GraphicsFunctions
{
  float prevX, newChangeX, prevY, newChangeY;
  float prevValue, newValue;

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

  public void Blur(PGraphics photo)
  {
    float[][] blur_matrix = { {0.1f,  0.1f,  0.1f },
                              {0.1f,  0.1f,  0.1f },
                              {0.1f,  0.1f,  0.1f } };

    colorMode(RGB);
    for(int y = 0; y < photo.height; y++)
    {
      for(int x = 0; x < photo.width; x++)
      {
        int c = Convolution(x, y, blur_matrix, 3, photo);
        photo.set(x, y, c);
      }
    }
    colorMode(HSB);
  }

  public void Sharpen(PGraphics photo)
  {
    float[][] sharpen_matrix = { { 0, -1, 0 },
                                 {-1, 5, -1 },
                                 { 0, -1, 0 } };

    colorMode(RGB);
    for(int y = 0; y < photo.height; y++)
    {
      for(int x = 0; x < photo.width; x++)
      {
        int c = Convolution(x, y, sharpen_matrix, 3, photo);
        photo.set(x, y, c);
      }
    }
    colorMode(HSB);
  }

  public void Greyscale(PGraphics photo)
  {
    colorMode(RGB);
    for (int y = 0; y < photo.height; y++)
    {
      for (int x = 0; x < photo.width; x++)
      {

        int thisPix = photo.get(x,y);

        int r = (int) red(thisPix);
        int g = (int) green(thisPix);
        int b = (int) blue(thisPix);
        int grey = (int)((r + g + b) / 3);
        int newColour = color(grey, grey, grey);
        photo.set(x,y, newColour);
      }
    }
    colorMode(HSB);
  }

  public void Monochrome(PGraphics photo)
  {
    colorMode(RGB);
    for (int y = 0; y < photo.height; y++)
    {
      for (int x = 0; x < photo.width; x++)
      {
        int thisPix = photo.get(x,y);

        int r = (int) red(thisPix);
        int g = (int) green(thisPix);
        int b = (int) blue(thisPix);

        if (r > 128|| g > 128 || b > 128)
        {
          r = 255;
          g = 255;
          b = 255;
        }
        else if (r <= 128 || g <= 128 || b <= 128)
        {
          r = 0;
          g = 0;
          b = 0;
        }

        int newColour = color(r, g, b);
        photo.set(x,y, newColour);
      }
    }
    colorMode(HSB);
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
        layer.line(mouseX - 20, mouseY - 100, pmouseX - 20, pmouseY - 100);
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
        layer.line(mouseX - 20, mouseY - 100, pmouseX - 20, pmouseY - 100);
      }
    }
    layer.endDraw();
  }

  public void ShapeStart(String name, PVector mouseStart, PGraphics layer, Document doc,
                      ColourPicker colourPicker, float sWeight, float opacity, boolean filled)
  {
     doc.StartNewShape(name, mouseStart, layer,
                       colourPicker._hueVal,
                       colourPicker._satVal,
                       colourPicker._briVal,
                       sWeight, opacity, filled);
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

  public void ChangeShapeHSB(Document doc, ColourPicker colourPicker, float sWeight, float opacity, boolean filled)
  {
    for (DrawShape s : doc.shapeList)
    {
      if (s.isSelected)
      {
        s.hue = colourPicker._hueVal;
        s.sat = colourPicker._satVal;
        s.bri = colourPicker._briVal;
        s.sWeight = sWeight;
        s.opacity = opacity;
        s.isFilled = filled;
      }
    }
  }

  public void ChangeShapePosition(Document doc, float xPosChange, float yPosChange)
  {
    prevX = newChangeX;
    newChangeX = xPosChange;

    prevY = newChangeY;
    newChangeY = yPosChange;

    if (newChangeX != prevX)
    {
      xPosChange -= prevX;
    }

    if (newChangeY != prevY)
    {
      yPosChange -= prevY;
    }

    for (DrawShape s : doc.shapeList)
    {
      if (s.isSelected)
      {
        if (newChangeX != prevX && s.polyPoints == null)
        {
          s.bounds.x1 += xPosChange;
          s.bounds.x2 += xPosChange;
          s.bounds.left += xPosChange;
          s.bounds.right += xPosChange;
        }

        if (newChangeY != prevY && s.polyPoints == null)
        {
          s.bounds.y1 += yPosChange;
          s.bounds.y2 += yPosChange;
          s.bounds.top += yPosChange;
          s.bounds.bottom += yPosChange;
        }

        if (s.polyPoints != null)
        {
          for (PVector v : s.polyPoints)
          {
            if (newChangeX != prevX)
            {
              v.x += xPosChange;
            }

            if (newChangeY != prevY)
            {
              v.y += yPosChange;
            }
          }
        }
      }
    }
  }

  public void ScaleShape(Document doc, float scale)
  {
    for (DrawShape s : doc.shapeList)
    {
      if (s.isSelected)
      {
        s.scaleValue = scale;
      }
    }
  }

  public void RotateShape(Document doc, float rotate)
  {
    for (DrawShape s : doc.shapeList)
    {
      if (s.isSelected)
      {
        s.rotateValue = radians(rotate);
      }
    }
  }

  public void DeleteShape(Document doc)
  {
    Iterator itr = doc.shapeList.iterator();
    while (itr.hasNext())
    {
      DrawShape s = (DrawShape)itr.next();
      if (s.isSelected)
      {
        itr.remove();
      }
    }
  }

  public void ClearLayer(PGraphics layer, Button button, Document doc)
  {
    layer.clear();
    doc.shapeList = new ArrayList<DrawShape>();
    button.localState = false;
  }

  public void Resize(PGraphics photo, PImage destination)
  {
    photo.loadPixels();
    destination.loadPixels();

    destination.pixels = ResizeBilinear(photo.pixels, photo.width, photo.height, destination.width, destination.height);
    destination.updatePixels();
  }

  public void EdgeDetect(PGraphics photo)
  {
    float[][] edge_matrix = { { 0,  -2,  0 },
                              { -2,  8, -2 },
                              { 0,  -2,  0 } };

    colorMode(RGB);
    for(int y = 0; y < photo.height; y++)
    {
      for(int x = 0; x < photo.width; x++)
      {
        int c = Convolution(x, y, edge_matrix, 3, photo);
        photo.set(x, y, c);
      }
    }
    colorMode(HSB);
  }

  public void Hue(PGraphics photo, ColourPicker colourpicker, boolean confirm)
  {
    photo.loadPixels();
    int numPixels = photo.width * photo.height;
    for(int n = 0; n < numPixels; n++)
    {
      int c = photo.pixels[n];

      float hue = hue(c);
      float sat = saturation(c);
      float bri = brightness(c);

      float old = hue;
      float change = colourPicker._hueVal;

      float difference = change - old;
      float result = change - (difference * 0.9f);

      int newColor = color(result, sat, bri);

      photo.pixels[n] = newColor;
    }

    if (confirm)
    {
      println("Confirmed");
      photo.updatePixels();
    }
  }

  public void Saturation(PGraphics photo, ColourPicker colourpicker, boolean confirm)
  {
    photo.loadPixels();
    int numPixels = photo.width * photo.height;
    for(int n = 0; n < numPixels; n++)
    {
      int c = photo.pixels[n];

      float hue = hue(c);
      float sat = saturation(c);
      float bri = brightness(c);

      sat = colourPicker._satVal;

      int newColor = color(hue, sat, bri);

      photo.pixels[n] = newColor;
    }

    if (confirm)
    {
      println("Confirmed");
      photo.updatePixels();
    }
  }

  public void Brightness(PGraphics photo, float brightnessValue, boolean confirm)
  {
    colorMode(RGB);
    int[] lut = makeFunctionLUT("ChangeBrightness", brightnessValue, 0);
    applyPointProcessing(lut, lut, lut, photo, confirm);
    colorMode(HSB);
  }

  public void Contrast(PGraphics photo, float contrastValue, boolean confirm)
  {
    colorMode(RGB);
    int[] lut = makeFunctionLUT("ChangeContrast", contrastValue, 0);
    applyPointProcessing(lut, lut, lut, photo, confirm);
    colorMode(HSB);
  }

  public void applyPointProcessing(int[] redLUT, int[] greenLUT, int[] blueLUT, PGraphics inputImage, boolean confirm)
  {
    inputImage.loadPixels();
    int numPixels = inputImage.width*inputImage.height;
    for(int n = 0; n < numPixels; n++)
    {
      int c = inputImage.pixels[n];

      int r = (int)red(c);
      int g = (int)green(c);
      int b = (int)blue(c);

      r = redLUT[r];
      g = greenLUT[g];
      b = blueLUT[b];
      int newColor = color(r, g, b);

      inputImage.pixels[n] = newColor;
    }

    if (confirm)
    {
      println("Confirmed");
      inputImage.updatePixels();
    }
  }

  public int[] makeFunctionLUT(String functionName, float parameter1, float parameter2)
  {
    int[] lut = new int[256];
    for(int n = 0; n < 256; n++) {

      float p = n/256.0f;
      float val = 0;

      switch(functionName)
      {
        case "ChangeBrightness": val = ChangeBrightness(p, parameter1);
        break;
        case "ChangeContrast": val = ChangeContrast(p, parameter1);
        break;
      }
      lut[n] = (int)(val*255);
    }

    return lut;
  }

  public float ChangeBrightness(float value, float shift)
  {
    float shiftedValue = value + shift;
    return shiftedValue;
  }

  public float ChangeContrast(float value, float conVal)
  {
    float contrastValue = (value - 0.5f) * conVal + 0.5f;
    return contrastValue;
  }

  public int Convolution(int x, int y, float[][] matrix, int matrixsize, PGraphics photo)
  {
    float rtotal = 0.0f;
    float gtotal = 0.0f;
    float btotal = 0.0f;
    int offset = matrixsize / 2;
    for (int i = 0; i < matrixsize; i++)
    {
      for (int j= 0; j < matrixsize; j++)
      {
        // What pixel are we testing
        int xloc = x+i-offset;
        int yloc = y+j-offset;
        int loc = xloc + photo.width * yloc;
        // Make sure we haven't walked off our image, we could do better here
        loc = constrain(loc, 0, photo.pixels.length-1);
        // Calculate the convolution
        rtotal += (red(photo.pixels[loc]) * matrix[i][j]);
        gtotal += (green(photo.pixels[loc]) * matrix[i][j]);
        btotal += (blue(photo.pixels[loc]) * matrix[i][j]);
      }
    }
    // Make sure RGB is within range
    rtotal = constrain(rtotal, 0, 255);
    gtotal = constrain(gtotal, 0, 255);
    btotal = constrain(btotal, 0, 255);
    // Return the resulting color
    return color(rtotal, gtotal, btotal);
  }

  public int[] ResizeBilinear(int[] pxls, int startWidth, int startHeight, int targetWidth, int targetHeight)
  {
    int[] temp = new int[targetWidth * targetHeight];
    int a, b, c, d, x, y, index;
    float xRatio = ((float)(startWidth - 1)) / targetWidth;
    float yRatio = ((float)(startHeight - 1)) / targetHeight;
    float xDiff, yDiff, red, green, blue;
    int offset = 0;

    for (int i = 0; i < targetHeight; i++)
    {
      for (int j = 0; j < targetWidth; j++)
      {
        x = (int)(xRatio * j);
        y = (int)(yRatio * i);
        xDiff = (xRatio * j) - x;
        yDiff = (yRatio * i) - y;
        index = (y * startWidth + x);
        a = pxls[index];
        b = pxls[index + 1];
        c = pxls[index + startWidth];
        d = pxls[index + startWidth + 1];

        //blue
        // Yb = Ab(1-w)(1-h) + Bb(w)(1-h) + Cb(h)(1-w) + Db(wh)
        blue = (a&0xff) * (1-xDiff) * (1-yDiff) + (b&0xff) * (xDiff) * (1-yDiff) +
               (c&0xff) * (yDiff) * (1-xDiff) + (d&0xff) * (xDiff * yDiff);

        green = ((a>>8)&0xff) * (1-xDiff) * (1-yDiff) + ((b>>8)&0xff) * (xDiff) * (1-yDiff) +
                ((c>>8)&0xff) * (yDiff) * (1-xDiff) + ((d>>8)&0xff) * (xDiff * yDiff);

        // red element
        // Yr = Ar(1-w)(1-h) + Br(w)(1-h) + Cr(h)(1-w) + Dr(wh)
        red = ((a>>16)&0xff) * (1-xDiff)*(1-yDiff) + ((b>>16)&0xff) * (xDiff) * (1-yDiff) +
              ((c>>16)&0xff) * (yDiff) * (1-xDiff) + ((d>>16)&0xff) * (xDiff * yDiff);

        temp[offset++] = 0xff000000 | // hardcode alpha
                         ((((int)red)<<16)&0xff0000) |
                         ((((int)green)<<8)&0xff00) |
                         ((int)blue);
      }
    }
    return temp;
  }
}
class Line extends DrawShape
{
  Line(String shapeType, PVector mouseStartLoc, PGraphics layer,
       float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
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
      float x2 = this.mouseDrag.x;
      float y2 = this.mouseDrag.y;
      this.layer.line(x1 - 20, y1 - 100, x2 - 20, y2 - 100);
    }
    else
    {
      float x1 = this.bounds.x1;
      float y1 = this.bounds.y1;
      float wid = this.bounds.x2;
      float hgt = this.bounds.y2;

      if (this.isSelected)
      {
        this.layer.strokeWeight(this.sWeight + 5);
        this.layer.stroke(255 - this.hue, 255 - this.sat, 255 - this.bri);
        this.layer.pushMatrix();
        this.layer.scale(this.scaleValue);
        this.layer.rotate(this.rotateValue);
        this.layer.line(x1 - 20, y1 - 100, wid - 20, hgt - 100);
        this.layer.popMatrix();
      }

      this.layer.strokeWeight(this.sWeight);
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      this.layer.pushMatrix();
      this.layer.scale(this.scaleValue);
      this.layer.rotate(this.rotateValue);
      this.layer.line(x1 - 20, y1 - 100, wid - 20, hgt - 100);
      this.layer.popMatrix();
    }
    this.layer.endDraw();
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
  //ArrayList<PVector> polyPoints;
  PVector newMousePos;
  PShape poly;
  Boolean pickFinished;

  Polygon(String shapeType, PVector mouseStartLoc, PGraphics layer,
          float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
    polyPoints = new ArrayList<PVector>();
  }

  public void AddToPoints(PVector mousePos)
  {
    this.polyPoints.add(mousePos);
  }

  public void FinishDrawingShape(PVector endPoint)
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

    println(xyMin);
    println(xyMax);

    setShapeBounds(xyMin, xyMax);

    this.isDrawing = false;

  }

  public void drawThisShape()
  {
    this.layer.beginDraw();
    //smooth();
    this.layer.colorMode(HSB);
    //DrawSettings();
    if (isDrawing)
    {
      this.poly = createShape();
      this.poly.beginShape();
      this.poly.strokeWeight(this.sWeight);
      this.poly.stroke(this.hue,
                       this.sat,
                       this.bri,
                       this.opacity);
      if (isFilled)
      {
        this.poly.fill(this.hue,
                       this.sat,
                       this.bri,
                       this.opacity);
      }
      else
      {
        this.poly.noFill();
      }

      for (PVector v : this.polyPoints)
      {
        this.poly.vertex(v.x - 20, v.y - 100);
      }

      if (isFilled)
      {
        this.poly.endShape(CLOSE);
      }
      else
      {
        this.poly.endShape();
      }

      this.layer.shape(poly);
    }
    else
    {
      if (this.isSelected)
      {
        this.poly = createShape();
        this.poly.beginShape();
        this.poly.strokeWeight(this.sWeight + 5);
        this.poly.stroke(255 - this.hue,
                         255 - this.sat,
                         255 - this.hue);
        this.poly.noFill();
        for (PVector v : this.polyPoints)
        {
          this.poly.vertex(v.x - 20, v.y - 100);
        }
        this.poly.endShape();
        this.layer.pushMatrix();
        this.layer.scale(this.scaleValue);
        this.layer.rotate(this.rotateValue);
        this.layer.shape(poly);
        this.layer.popMatrix();
      }
    }

    this.poly = createShape();
    this.poly.beginShape();
    this.poly.strokeWeight(this.sWeight);
    this.poly.stroke(this.hue,
                     this.sat,
                     this.bri,
                     this.opacity);

    if (isFilled)
    {
      this.poly.fill(this.hue,
                     this.sat,
                     this.bri,
                     this.opacity);
    }
    else
    {
      this.poly.noFill();
    }

    for (PVector v : this.polyPoints)
    {
      this.poly.vertex(v.x - 20, v.y - 100);
    }

    if (this.isFilled)
    {
      this.poly.endShape(CLOSE);
    }
    else
    {
      this.poly.endShape();
    }

    this.layer.pushMatrix();
    this.layer.scale(this.scaleValue);
    this.layer.rotate(this.rotateValue);
    this.layer.shape(poly);
    this.layer.popMatrix();
    this.layer.endDraw();
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
            float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
  }

  public void drawThisShape()
  {
    this.layer.beginDraw();
    this.layer.colorMode(HSB);

    DrawSettings();
    if (isDrawing)
    {
      float x1 = this.mouseStart.x - 20;
      float y1 = this.mouseStart.y - 100;
      float wid = this.mouseDrag.x - x1;
      float hgt = this.mouseDrag.y - y1;
      this.layer.rect(x1, y1, wid - 20, hgt - 100);
    }
    else
    {
      float x1 = this.bounds.left;
      float y1 = this.bounds.top;
      float wid = this.bounds.getWidth();
      float hgt = this.bounds.getHeight();

      if (this.isSelected)
      {
        this.layer.strokeWeight(this.sWeight + 5);
        this.layer.stroke(255 - this.hue, 255 - this.sat, 255 - this.bri);
        this.layer.pushMatrix();
        this.layer.scale(this.scaleValue);
        this.layer.rotate(this.rotateValue);
        this.layer.rect(x1 - 20, y1 - 100, wid, hgt);
        this.layer.popMatrix();
      }

      this.layer.strokeWeight(this.sWeight);
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      this.layer.pushMatrix();
      this.layer.scale(this.scaleValue);
      this.layer.rotate(this.rotateValue);
      this.layer.rect(x1 - 20, y1 - 100, wid, hgt);
      this.layer.popMatrix();
    }
    this.layer.endDraw();
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

    if (sliderName == "Stroke Weight")
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
    else if (sliderName == "Offset X")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Offset Y")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Rotation Angle")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Scale")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Brightness/Contrast")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (float)retValue + "    " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Width")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + "    " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Height")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + "    " + sNameValue, xBarPos + 10, yBarPos - 10);
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
        //btns[0].localState = false;
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
class Menu
{
  // Arrays for holding button string names and buttons
  String[] topBarFile;
  String[] topBarFilter;
  String[] topBarPhotoEdit;

  Button[] topBarFileBtns;
  Button[] topBarFilterBtns;
  Button[] topBarPhotoEditBtns;

  Button drawShape;
  Button selectShape;
  Button filledShape;

  String[] drawShapeNames;
  String[] selectShapeNames;

  Button[] drawShapeMenu;
  Button[] selectShapeMenu;

  Button[] shapeMenuBtns;

  int btnFontSize = 16, sideMenuInset = 200,
      topBarXStart = 0, topBarYStart = 0, topBarWidth = 100, topBarHeight = 20,
      subXStart = 0, subYStart = 20, subBWidth = 100, subBHeight = 20,
      topBarXIncrease = 60, topBarYIncrease = 20,
      sideMenuXInset = 180, sideMenuColYInset = 20, sideMenuColWidth = 160, sideMenuColHeight = 350,
      sideMenuSelYInset = 390, sideMenuSelWidth = 160, sideMenuSelHeight = 140;

  PFont btnFont;

  Menu()
  {
    topBarFile = new String[] {"File", "New", "Save", "Load"};
    topBarFilter = new String[] {"Filter", "Blur", "Sharpen", "Greyscale", "Monochrome", "Edge-Detect"};
    topBarPhotoEdit = new String[] {"Edit", "Resize", "Hue", "Saturation", "Brightness", "Contrast"};

    drawShapeNames = new String[] {"Line", "Curve", "Rectangle", "Circle", "Polygon", "Arc", "ClearLayer"};
    selectShapeNames = new String[] {"ChangeColour", "ChangePosition", "ScaleShape", "RotateShape", "DeleteShape"};

    btnFont = createFont("arial.ttf", 16);

    topBarFileBtns = new Button[topBarFile.length];
    topBarFilterBtns = new Button[topBarFilter.length];
    topBarPhotoEditBtns = new Button[topBarPhotoEdit.length];

    drawShapeMenu = new Button[drawShapeNames.length];
    selectShapeMenu = new Button[selectShapeNames.length];

    shapeMenuBtns = new Button[2];

    drawShape = new Button(width - sideMenuInset + 45, 540, 50, 50, false, true, "DrawShape", false, true);
    selectShape = new Button(width - sideMenuInset + 105, 540, 50, 50, false, true, "SelectShape", false, true);
    filledShape = new Button(width - sideMenuInset + 80, 480, 40, 40, false, true, "FilledShape", false, true);


  }

  public void InitialiseMenu()
  {
    MenuButtonsInitialise(topBarFile, topBarFileBtns, topBarXStart, topBarYStart, topBarWidth, topBarHeight);
    MenuButtonsInitialise(topBarFilter, topBarFilterBtns, topBarXStart + topBarWidth, topBarYStart, topBarWidth, topBarHeight);
    MenuButtonsInitialise(topBarPhotoEdit, topBarPhotoEditBtns, topBarXStart + (topBarWidth * 2), topBarYStart, topBarWidth, topBarHeight);

    int step = 1, startX = width - sideMenuXInset - 5, startY = 600, increaseX = 60, increaseY = 60;
    for (int sideMenuIll = 0; sideMenuIll < drawShapeMenu.length; sideMenuIll++)
    {
      drawShapeMenu[sideMenuIll] = new Button(startX, startY, 50, 50, false, true, drawShapeNames[sideMenuIll], false, true);

      startX += 60;
      step++;
      if (step == 4)
      {
        startX = width - sideMenuXInset - 5;
        startY += 60;
        step = 1;
      }
    }

    step = 1; startX = width - sideMenuXInset - 5; startY = 600; increaseX = 60; increaseY = 60;
    for (int sideMenuIll = 0; sideMenuIll < selectShapeMenu.length; sideMenuIll++)
    {
      selectShapeMenu[sideMenuIll] = new Button(startX, startY, 50, 50, false, true, selectShapeNames[sideMenuIll], false, true);

      startX += 60;
      step++;
      if (step == 4)
      {
        startX = width - sideMenuXInset - 5;
        startY += 60;
        step = 1;
      }
    }

    shapeMenuBtns[0] = drawShape;
    shapeMenuBtns[1] = selectShape;
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

    TopBarDisplay(topBarFileBtns, topBarFilterBtns, topBarPhotoEditBtns);

    if (drawShape.localState)
    {
      for (int sideBarIll = 0; sideBarIll < drawShapeMenu.length; sideBarIll++)
      {
        drawShapeMenu[sideBarIll].DisplayButton();
      }
      filledShape.DisplayButton();
    }

    if (selectShape.localState)
    {
      for (int sideBarIll = 0; sideBarIll < selectShapeMenu.length; sideBarIll++)
      {
        selectShapeMenu[sideBarIll].DisplayButton();
      }
      filledShape.DisplayButton();
    }

    drawShape.DisplayButton();
    selectShape.DisplayButton();
  }

  public void TopMenuPressed()
  {
    topBarFileBtns[0].SingleButtonPress();
    {
      if (topBarFileBtns[0].localState)
      {
        topBarFilterBtns[0].localState = false;
        topBarPhotoEditBtns[0].localState = false;

        for (int menu = 1; menu < topBarFilterBtns.length; menu++)
        {
          topBarFilterBtns[menu].localState = false;
        }
        for (int menu = 1; menu < topBarPhotoEditBtns.length; menu++)
        {
          topBarPhotoEditBtns[menu].localState = false;
        }

        topBarFileBtns[0].TopMenuButtonPressed(topBarFileBtns);
        for (int menu = 1; menu < topBarFileBtns.length; menu++)
        {
          if (topBarFileBtns[menu].localState)
          {
            for (int illMenu = 0; illMenu < drawShapeMenu.length; illMenu++)
            {
              drawShapeMenu[illMenu].localState = false;
            }
            for (int illMenu = 0; illMenu < selectShapeMenu.length; illMenu++)
            {
              selectShapeMenu[illMenu].localState = false;
            }
          }
        }
      }
    }

    topBarFilterBtns[0].SingleButtonPress();
    {
      if (topBarFilterBtns[0].localState)
      {
        topBarFileBtns[0].localState = false;
        topBarPhotoEditBtns[0].localState = false;

        for (int menu = 1; menu < topBarFileBtns.length; menu++)
        {
          topBarFileBtns[menu].localState = false;
        }
        for (int menu = 1; menu < topBarPhotoEditBtns.length; menu++)
        {
          topBarPhotoEditBtns[menu].localState = false;
        }

        topBarFilterBtns[0].TopMenuButtonPressed(topBarFilterBtns);
        for (int menu = 1; menu < topBarFilterBtns.length; menu++)
        {
          if (topBarFilterBtns[menu].localState)
          {
            for (int illMenu = 0; illMenu < drawShapeMenu.length; illMenu++)
            {
              drawShapeMenu[illMenu].localState = false;
            }
            for (int illMenu = 0; illMenu < selectShapeMenu.length; illMenu++)
            {
              selectShapeMenu[illMenu].localState = false;
            }
          }
        }
      }
    }

    topBarPhotoEditBtns[0].SingleButtonPress();
    {
      if (topBarPhotoEditBtns[0].localState)
      {
        topBarFileBtns[0].localState = false;
        topBarFilterBtns[0].localState = false;

        for (int menu = 1; menu < topBarFileBtns.length; menu++)
        {
          topBarFileBtns[menu].localState = false;
        }
        for (int menu = 1; menu < topBarFilterBtns.length; menu++)
        {
          topBarFilterBtns[menu].localState = false;
        }

        topBarPhotoEditBtns[0].TopMenuButtonPressed(topBarPhotoEditBtns);
        for (int menu = 1; menu < topBarPhotoEditBtns.length; menu++)
        {
          if (topBarPhotoEditBtns[menu].localState)
          {
            for (int illMenu = 0; illMenu < drawShapeMenu.length; illMenu++)
            {
              drawShapeMenu[illMenu].localState = false;
            }
            for (int illMenu = 0; illMenu < selectShapeMenu.length; illMenu++)
            {
              selectShapeMenu[illMenu].localState = false;
            }
          }
        }
      }
    }

    shapeMenuBtns[0].ButtonPressed(shapeMenuBtns);
  }

  public void SideMenuPressed()
  {
    if (drawShape.localState)
    {
      drawShapeMenu[0].ButtonPressed(drawShapeMenu);
      filledShape.SingleButtonPress();
      for (Button b : selectShapeMenu)
      {
        b.localState = false;
      }
    }

    if (selectShape.localState)
    {
      selectShapeMenu[0].ButtonPressed(selectShapeMenu);
      filledShape.SingleButtonPress();
      for (Button b : drawShapeMenu)
      {
        b.localState = false;
      }
    }
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
      if (topMenu == 0)
      {
        buttons[topMenu] = new Button(tXstart, tYstart, tWidth, tHeight, false, false, names[topMenu], true, false);
        tYstart += tHeight;
        tXstart = 0;
        tHeight += 60;
      }
      else
      {
        buttons[topMenu] = new Button(tXstart, tYstart, tWidth, tHeight, false, false, names[topMenu], false, true);
        tXstart += tWidth;
      }
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
