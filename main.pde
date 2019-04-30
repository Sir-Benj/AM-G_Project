// Applied Maths and Graphics
// Simple illustrator and editor tool
// Created by Ben Harding
import java.util.LinkedList;
import java.util.Iterator;

// Fields
int xFirstClick, yFirstClick, xSecondCLick, ySecondClick,
    xOnPress, yOnPress, xOffset, yOffset;

int xInset = 245, yInset = 120;

// Initial slider values
float sliderOneValue = 5;
float sliderTwoValue = 255;
float sliderXValue = 0;
float sliderYValue = 0;
float sliderChangeX = 0;
float sliderChangeY = 0;
float sliderRotateValue = 0;
float sliderScaleValue = 1;
float sliderPointInputvalue = 100;
float sliderNewXValue = 100;
float sliderNewYValue = 100;
float sliderValueHSB = 0;

// Buttons used for control
Button control;
Button[] btns;
Button[][] buttonMenu;
Button confirm;
Button cancel;

// Sliders
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
Slider hueSatBri;

// Booleans for checks
boolean clicked, left, right, newPoly = false, isFinished = true;
boolean isFilterBlur, isFilterSharpen, isFilterGreyscale, isFilterMonochrome, isFilterEdgeDetect;
boolean isEditResize, isEditHue, isEditSaturation, isEditBrightness, isEditContrast;

Menu menu;
ColourPicker colourPicker;

// Layers
PGraphics background;
PGraphics photoLayer;
PGraphics paintLayer;
PGraphics combineLayers;

// Images for output and manipulation
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

void setup()
{
  frameRate(60);
  colorMode(HSB);
  background(255);

  // Layers created
  background = createGraphics(width - xInset, height - yInset);
  photoLayer = createGraphics(width - xInset, height - yInset);
  paintLayer = createGraphics(width - xInset, height - yInset);
  combineLayers = createGraphics(width - xInset, height - yInset);
  imageToSaveOne = createImage(width - xInset, height - yInset, HSB);
  imageToSaveTwo = createImage(width - xInset, height - yInset, HSB);
  imageToSaveCombined = createImage(width - xInset, height - yInset, HSB);

  // Components initialised
  menu = new Menu();
  menu.InitialiseMenu();
  colourPicker = new ColourPicker();
  messageQueue = new MessageQueue();
  graphicsFunctions = new GraphicsFunctions();
  path = "";
  selectOne = new File(sketchPath("") + "/*.png");
  doc = new Document();

  // Mouse Vectors initialised
  mouseStart = new PVector();
  mouseDrag = new PVector();
  mouseFinal = new PVector();
  firstPoint = new PVector();

  moveShape = new DrawShape();

  // Instantiate Sliders
  sliderStrokeW = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 25,
                         140, 10, 1, 400, "Stroke Weight", "px");

  sliderOpacity = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 65,
                         140, 10, 0.0, 255, "Opacity", "%");

  sliderX = new Slider(20, height - 20,  width - 265, 20, 0.0, photoLayer.width - width, "xbar", "px");

  sliderY = new Slider(width - 225, 40, 25, height - 85, 0.0, photoLayer.height - height, "ybar", "px");

  sliderChangeXPos = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 25,
                         140, 10, -paintLayer.width , paintLayer.width, "Offset X", "px");

  sliderChangeYPos = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 65,
                         140, 10, -paintLayer.height, paintLayer.height, "Offset Y", "px");

  sliderRotation = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 25,
                         140, 10, 0, 360, "Rotation Angle", "Degrees");

  sliderScale = new Slider(width - menu.sideMenuXInset + 10, menu.sideMenuSelYInset + 65,
                         140, 10, 0.1, 5, "Scale", "Multiplier");

  sliderPointInput = new Slider(600, 75, 140, 10, 0, 128, "Contrast", "");

  sliderNewX = new Slider(600, 55, 140, 10, 1, width, "Width", "px");

  sliderNewY = new Slider(600, 75, 140, 10, 1, height, "Height", "px");

  hueSatBri = new Slider(600, 75, 140, 10, -255, 255, "HSB Value", "");

  confirm = new Button(750, 50, 140, 40, false, true, "  Confirm Changes", true, false);


  // Make the layers do an initial draw
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

// All mouse pressed checks
void mousePressed()
{
  menu.TopMenuPressed();
  menu.SideMenuPressed();
  confirm.SingleButtonPress();

  mouseStart = new PVector();

  // Starting positions
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

  // Checks for selected menu items
  for (int i = 0; i < menu.drawShapeMenu.length; i++)
  {
    if (!OverCanvas() && newPoly)
    {
      graphicsFunctions.ShapeFinal(doc, mouseFinal);
      newPoly = false;
    }
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

// All mouse dragged checks
void mouseDragged()
{
  if (pressed && !released)
  {
    mouseDrag.x = mouseX;
    mouseDrag.y = mouseY;
    // Check for all shapes if pressed that have a drag element
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

// All mouse released checks
void mouseReleased()
{
  // Only activate if the mouse was pressed first and not just on release
  if (pressed)
  {
    mouseFinal.x = mouseX;
    mouseFinal.y = mouseY;
    pressed = false;
    released = true;

    // Check which shape is pressed and perform the correct finalisation
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

void draw()
{
  // Prime the layers
  paintLayer.beginDraw();
  paintLayer.clear();
  paintLayer.endDraw();
  photoLayer.beginDraw();
  photoLayer.endDraw();
  combineLayers.beginDraw();
  combineLayers.endDraw();

  // To prevent null pointer errors
  if (!OverCanvas() && newPoly)
  {
    graphicsFunctions.ShapeFinal(doc, mouseFinal);
    newPoly = false;
  }

  if (!selectingShape)
  {
    sliderRotateValue = 0;
    sliderChangeX = 0;
    sliderChangeY = 0;
  }

  for (int i = 0; i < menu.drawShapeMenu.length; i++)
  {
    // Unused draw functions
    // if (menu.drawShapeMenu[i].buttonName == "Pencil" && menu.drawShapeMenu[i].localState == true && OverCanvas())
    // {
    //   graphicsFunctions.Pencil(paintLayer, colourPicker, sliderOneValue, sliderTwoValue);
    // }
    // if (menu.drawShapeMenu[i].buttonName == "Eraser" && menu.drawShapeMenu[i].localState == true)
    // {
    //   graphicsFunctions.Eraser(paintLayer, sliderOneValue);
    // }
    if (menu.drawShapeMenu[i].buttonName == "ClearLayer" && menu.drawShapeMenu[i].localState == true)
    {
      graphicsFunctions.ClearLayer(paintLayer, menu.drawShapeMenu[i], doc);
    }
  }

  // Select shape manu items
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

  // Top menu File button checks
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

  // Top menu Filter button checks
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

  // Top menu Edit button checks
  for (int i = 1; i < menu.topBarPhotoEditBtns.length; i++)
  {
    if (menu.topBarPhotoEditBtns[i].buttonName == "Resize" && menu.topBarPhotoEditBtns[i].localState == true)
    {
      if (confirm.localState)
      {
        // Resize changes the canvas, photolayer has to be resized along with it
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
        graphicsFunctions.Hue(photoLayer, sliderValueHSB, confirm.localState);
        confirm.localState = false;
      }
      else
      {
        graphicsFunctions.Hue(photoLayer, sliderValueHSB, confirm.localState);
      }
    }
    else if (menu.topBarPhotoEditBtns[i].buttonName == "Saturation" && menu.topBarPhotoEditBtns[i].localState == true)
    {
      if (confirm.localState)
      {
        menu.topBarPhotoEditBtns[i].localState = false;
        graphicsFunctions.Saturation(photoLayer, sliderValueHSB, confirm.localState);
        confirm.localState = false;
      }
      else
      {
        graphicsFunctions.Saturation(photoLayer, sliderValueHSB, confirm.localState);
      }
    }
    else if (menu.topBarPhotoEditBtns[i].buttonName == "Brightness" && menu.topBarPhotoEditBtns[i].localState == true)
    {
      if (confirm.localState)
      {
        menu.topBarPhotoEditBtns[i].localState = false;
        graphicsFunctions.Brightness(photoLayer, sliderValueHSB, confirm.localState);
        confirm.localState = false;
      }
      else
      {
        graphicsFunctions.Brightness(photoLayer, sliderValueHSB, confirm.localState);
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

  // Draw all the layers and shapes after they have been manipulated
  background(200);
  image(background, 20, 100);
  image(photoLayer, 20 - sliderXValue, 100 - sliderYValue);
  doc.DrawMe();
  image(paintLayer, 20, 100);

  imageToSaveOne = photoLayer.get(0, 0, photoLayer.width, photoLayer.height);
  imageToSaveTwo = paintLayer.get(0, 0, paintLayer.width, paintLayer.height);

  combineLayers.beginDraw();
  combineLayers.clear();
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

  // Display screen position sliders if layer is above starting canvas size.
  if (photoLayer.width > width - 245)
  {
    sliderXValue = sliderX.DrawSliderHorizontal(sliderXValue);
  }

  if (photoLayer.height > height - 60)
  {
    sliderYValue = sliderY.DrawSliderVertical(sliderYValue);
  }

  // Update slider values
  for (int i = 1; i < menu.topBarPhotoEditBtns.length; i++)
  {
    if (menu.topBarPhotoEditBtns[i].localState == true && menu.topBarPhotoEditBtns[i].buttonName == "Contrast")
    {
      sliderPointInputvalue = sliderPointInput.DrawSliderMenu(sliderPointInputvalue);
      confirm.DisplayButton();
    }
    else if (menu.topBarPhotoEditBtns[i].localState == true && menu.topBarPhotoEditBtns[i].buttonName == "Brightness")
    {
      //sliderPointInputvalue = sliderPointInput.DrawSliderMenu(sliderPointInputvalue);
      sliderValueHSB = hueSatBri.DrawSliderMenu(sliderValueHSB);
      confirm.DisplayButton();
    }
    else if (menu.topBarPhotoEditBtns[i].localState == true && menu.topBarPhotoEditBtns[i].buttonName == "Saturation")
    {
      sliderValueHSB = hueSatBri.DrawSliderMenu(sliderValueHSB);
      confirm.DisplayButton();
    }
    else if (menu.topBarPhotoEditBtns[i].localState == true && menu.topBarPhotoEditBtns[i].buttonName == "Hue")
    {
      sliderValueHSB = hueSatBri.DrawSliderMenu(sliderValueHSB);
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

// Saving the image
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

// Loading an image
void fileChosen(File selection)
{
  if (selection == null)
  {
    println("Window was closed or the user hit cancel.");
  }
  else
  {
    photoLayer.clear();
    paintLayer.clear();
    messageQueue.put(selection);
    path = selection.getAbsolutePath();
    imageToLoad = loadImage(path);
    photoLayer = createGraphics(imageToLoad.width, imageToLoad.height);
    paintLayer = createGraphics(imageToLoad.width, imageToLoad.height);
    photoLayer.beginDraw();
    photoLayer.image(imageToLoad, 0, 0);
    photoLayer.endDraw();

    sliderX = new Slider(20, height - 20,  width - 266, 20, 0.0, (photoLayer.width - width) + 240, "xbar", "px");
    sliderY = new Slider(width - 221, 40, 20, height - 80, 0.0, (photoLayer.height - height) + 60, "ybar", "px");
  }
}

// If mouse is over the canvas check
boolean OverCanvas()
{
  return (mouseX >= 20 && mouseX <= width - menu.sideMenuInset
          && mouseY >= 95 && mouseY <= height - 20);
}
