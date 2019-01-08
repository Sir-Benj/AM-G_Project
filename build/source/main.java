import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class main extends PApplet {

Button control;
Button[] btns;
Button[][] buttonMenu;

int screenSizeX = 840, screenSizeY = 640,
    menuSize = 200, topBarSize = 20;

Menu menu;
ColourPicker colourPicker;

PGraphics background;
PGraphics layer;

public void settings()
{
  size(3*displayWidth>>2, 3*displayHeight>>2);//screenSizeX + menuSize, screenSizeY + topBarSize);
}

public void setup()
{
  frameRate(100);
  
  colorMode(HSB);
  background(255);

  background = createGraphics(width - 245, height - 60);//800, 600);
  layer = createGraphics(width - 245, height - 60);//800, 600);
  menu = new Menu();
  menu.InitialiseMenu();
  colourPicker = new ColourPicker();
}

public void mousePressed()
{
  menu.TopMenuPressed();
  menu.SideMenuPressed();
}

public void mouseDragged()
{

}


public void draw()
{
  background.beginDraw();
  background.background(255);
  background.endDraw();

  layer.beginDraw();
  layer.colorMode(HSB);
  if (mousePressed)
  {
    if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
    {
      layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal);
      layer.line(mouseX - 20, mouseY - 40, pmouseX - 20, pmouseY - 40);
    }
    if (menu.topBarButtons[0][1].localState)
    {
      layer.clear();
      menu.topBarButtons[0][1].localState = false;
    }
    if (menu.topBarButtons[0][2].localState)
    {
      layer.save("test.png");
    }
  }
  layer.endDraw();


  tint(255);
  background(200);
  image(background, 20, 40);
  image(layer, 20, 40);

  menu.DrawMenu();
  menu.DisplayMenu();
  colourPicker.DrawPicker(width - menu.sideMenuXInset + 5, menu.sideMenuColYInset + 5);
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
    _hueVal= DrawSlider(colourMenuXInset, colourMenuYInset + 215, barWidth, 40.0f, _hueVal, _hueVal, "hue");
    _satVal= DrawSlider(colourMenuXInset, colourMenuYInset + 275, barWidth,20.0f, _satVal, _hueVal, "sat");
    _briVal= DrawSlider(colourMenuXInset, colourMenuYInset + 315, barWidth, 20.0f, _briVal, _hueVal, "bri");
    fill(_hueVal, _satVal, _briVal);
    rect(colourMenuXInset, colourMenuYInset, 150, 200);
  }

  public float DrawSlider(float xPos, float yPos, float sWidth, float sHeight, float hueVal, float hueActVal, String display)
  {
    float sliderPos = map(hueVal, 0.0f, 255.0f, 0.0f, sWidth);

    for(int i = 0; i < sWidth; i++)
    {
      float hueValue = map(i, 0.0f, sWidth, 0.0f, 255.0f);
      switch(display)
      {
        case "hue": stroke(hueValue, 255, 255);
                    break;
        case "sat": float satValue=map(i, 0.0f, sWidth, 0.0f, 255);
                    stroke(hueActVal, satValue, 255);
                    break;
        case "bri": float briValue=map(i, 0.0f, sWidth, 0.0f, 255);
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
      case "hue": fill(_hueVal, 255, 255);
              break;
      case "sat": fill(_hueVal, _satVal, 255);
              break;
      case "bri": fill(_hueVal, 255, _briVal);
    }
    rect(sliderPos + xPos - 3, yPos - 5, 6, sHeight + 10);

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
