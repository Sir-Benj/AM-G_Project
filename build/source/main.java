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

//ImageUI win;
//PaintUI win2;
TopBarManager topBar;
Button control;
Button[] btns;
Button[][] buttonMenu;

public void settings()
{
  size(1000, 800);
}

public void setup()
{
  
  surface.setResizable(true);
  background(255, 255, 255, 255);
  btns = new Button[7];
  btns[0] = new Button(10, 30, 50, 50, true, true, "paint");
  btns[1] = new Button(10, 90, 50, 50, true, true, "erase");
  btns[2] = new Button(10, 150, 50, 50, true, true, "thirdtester");
  btns[3] = new Button(70, 30, 20, 50, false, true, "firstRect");
  btns[4] = new Button(70, 90, 20, 50, true, false, "secondRect");
  btns[5] = new Button(70, 150, 20, 50, true, true, "thirdRect");
  btns[6] = new Button(70, 210, 20, 50, false, false, "forthRect");

  control = new Button(0, 0, 0, 0, false, false, "control");
  topBar = new TopBarManager();
  buttonMenu = topBar.InitialiseMenu();
}

public void mousePressed()
{
  topBar.TopMenuPressed();
  control.ButtonPressed(btns);

  // if (btn1.localState && !btn2.localState)
  // {
  //   noStroke();
  //   fill(0);
  //   ellipse(mouseX, mouseY, 20, 20);
  // }
  // if (btn2.localState && !btn1.localState)
  // {
  //   noStroke();
  //   fill(255);
  //   ellipse(mouseX, mouseY, 20, 20);
  // }
}

public void mouseDragged()
{
  // if (btn1.localState && !btn2.localState)
  // {
  //   noStroke();
  //   fill(0);
  //   ellipse(mouseX, mouseY, 20, 20);
  // }
  // if (btn2.localState && !btn1.localState)
  // {
  //   noStroke();
  //   fill(255);
  //   ellipse(mouseX, mouseY, 20, 20);
  // }
}

public void draw()
{
  background(255);

  for (int i = 0; i < btns.length; i++)
  {
    btns[i].DisplayButton();
  }

  topBar.DisplayMenu();
}
class TopBarManager
{
  String[][] topBar;
  Button[][] menuButtons;
  Button[] sub1;
  Button[] sub2;
  Button[] sub3;

  PFont font;
  //int topBheight;
  //int topBwidth

  TopBarManager()
  {
    topBar = new String[][] { {"File", "New", "Save", "Load"}, {"Edit", "Undo", "Redo"}, {"Filter", "Blur", "Sharpen"} };
    font = createFont("arial.ttf", 16);

    menuButtons = new Button[topBar.length][];
    sub1 = new Button[topBar[0].length];
    sub2 = new Button[topBar[1].length];
    sub3 = new Button[topBar[2].length];

    menuButtons[0] = sub1;
    menuButtons[1] = sub2;
    menuButtons[2] = sub3;
  }

  public Button[][] InitialiseMenu()
  {
    noStroke();
    fill(180);
    rect(0, 0, width, 20);
    textFont(font, 14);

    int topXstart = 0;
    int topYstart = 0;
    int topBwidth = 50;
    int topBheight = 20;
    int subXstart = 0;
    int subYstart = 20;
    int subBwidth = 80;
    int subBheight = 20;

    for (int topMenu = 0; topMenu < menuButtons.length; topMenu++)
    {
      menuButtons[topMenu][0] = new Button(topXstart, topYstart, topBwidth, topBheight, false, false, topBar[topMenu][0]);
      //menuButtons[topMenu][0].displayButton();
      topXstart += 50;

      for (int subMenu = 1; subMenu < menuButtons[topMenu].length; subMenu++)
      {
        menuButtons[topMenu][subMenu] = new Button(subXstart, subYstart, subBwidth, subBheight, false, false, topBar[topMenu][subMenu]);
        subYstart += 20;
      }
      subXstart += 50;
      subYstart = 20;
    }

    return menuButtons;
  }

  public void DisplayMenu()
  {
    noStroke();
    fill(180);
    rect(0, 0, width, 20);
    textFont(font, 14);

    for (int i = 0; i < menuButtons.length; i++)
    {
      for (int y = 0; y < menuButtons[i].length; y++)
      {
        menuButtons[i][0].DisplayButton();
        if (menuButtons[i][0].localState)
        {
          menuButtons[i][y].DisplayButton();
        }
      }
    }
  }

  public void TopMenuPressed()
  {
    menuButtons[0][0].TopMenuButtonPressed(menuButtons[1][0]);
    menuButtons[0][0].TopMenuButtonPressed(menuButtons[2][0]);
    menuButtons[1][0].TopMenuButtonPressed(menuButtons[0][0]);
    menuButtons[1][0].TopMenuButtonPressed(menuButtons[2][0]);
    menuButtons[2][0].TopMenuButtonPressed(menuButtons[0][0]);
    menuButtons[2][0].TopMenuButtonPressed(menuButtons[1][0]);
  }


}
class Button
{
  protected int buttonX, buttonY, buttonWidth, buttonHeight, smoothing;
  protected String buttonName;
  protected boolean isSmooth, hasBorder, localState;
  protected int buttonColour = color(100), buttonHighlight = color(200);

  Button(int newX, int newY, int newWidth, int newHeight, boolean smooth, boolean border, String newName)
  {
    buttonX = newX;
    buttonY = newY;
    buttonWidth = newWidth;
    buttonHeight = newHeight;
    isSmooth = smooth;
    hasBorder = border;
    buttonName = newName;
    smoothing = 8;
    localState = false;
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
    }

    if (isSmooth)
    {
      rect(buttonX, buttonY, buttonWidth, buttonHeight, smoothing);
      fill(0);
      text(buttonName, buttonX, buttonY + buttonHeight/1.5f);
    }
    else
    {
      rect(buttonX, buttonY, buttonWidth, buttonHeight);
      fill(0);
      text(buttonName, buttonX, buttonY + buttonHeight/1.5f);
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
class ButtonManager
{
  
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
