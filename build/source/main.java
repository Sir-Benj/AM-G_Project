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
  size(600, 600);
}

public void setup()
{
  
  surface.setResizable(true);
  background(255, 255, 255, 255);
  btns = new Button[3];
  btns[0] = new Button(10, 30, 50, "paint");
  btns[1] = new Button(10, 90, 50, "erase");
  btns[2] = new Button(10, 150, 50, "smooth");
  control = new Button(0, 0, 0, "control");
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
  topBar.DisplayMenu();
  for (int i = 0; i < btns.length; i++)
  {
    btns[i].DisplayButton();
  }
  // btn1.DisplayButton();
  // btn2.DisplayButton();
  // btn3.DisplayButton();
}
class TopBarManager
{
  String[][] topBar;
  Button[][] menuButtons;
  PFont font;
  //int topBheight;
  //int topBwidth

  TopBarManager()
  {
    topBar = new String[][] { {"File", "Save", "Load"}, {"Edit", "Undo", "Redo"}, {"Filter", "Blur", "Sharpen"} };
    font = createFont("arial.ttf", 16);

    int maxY = 0;
    for (int i = 0; i < topBar.length; i++)
    {
      maxY = Math.max(maxY, topBar[i].length);
    }
    menuButtons = new Button[topBar.length][maxY];
  }

  public Button[][] InitialiseMenu()
  {
    noStroke();
    fill(130);
    rect(0, 0, width, 20);
    textFont(font, 14);

    int topXstart = 0;
    int topYstart = 0;
    int topBSize = 20;
    int subXstart = 0;
    int subYstart = 20;
    int subBSize = 50;

    for (int topMenu = 0; topMenu < menuButtons.length; topMenu++)
    {
      menuButtons[topMenu][0] = new Button(topXstart, topYstart, topBSize, topBar[topMenu][0]);
      //menuButtons[topMenu][0].displayButton();
      topXstart += 20;

      for (int subMenu = 1; subMenu < menuButtons[topMenu].length; subMenu++)
      {
        menuButtons[topMenu][subMenu] = new Button(subXstart, subYstart, subBSize, topBar[topMenu][subMenu]);
        subYstart += 50;
      }
      subXstart += 50;
      subYstart = 20;
    }

    return menuButtons;
  }

  public void DisplayMenu()
  {
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
  int buttonX, buttonY, buttonSize, lastButtonPressed;
  String buttonName;
  boolean localState;
  int buttonColour = color(100), buttonHighlight = color(200);

  Button(int newX, int newY, int newSize, String newName)
  {
    buttonX = newX;
    buttonY = newY;
    buttonSize = newSize;
    buttonName = newName;
    localState = false;
    lastButtonPressed = -1;
  }

  public void DisplayButton()
  {
    //If mouse is over button highlight it
    if (OverButton() || localState)
    {
      stroke(0);
      fill(buttonHighlight);
    }
    //If mouse isnt over and it isnt on then display normal colour
    else if (!OverButton() || !localState)
    {
      stroke(255);
      fill(buttonColour);
    }
    rect(buttonX, buttonY, buttonSize, buttonSize);
  }

  public boolean OverButton()
  {
    //Is mouse within the button area
    if (mouseX > buttonX && mouseX < buttonX + buttonSize
        && mouseY > buttonY && mouseY < buttonY + buttonSize)
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
      if (btns[i].OverButton() && !btns[i].localState)
      {
        btns[i].localState = true;
        for (int j = 0; j < btns.length; j++ )
        {
          if (j != i)
          {
            btns[j].localState = false;
          }
        }
        println("option 1");
      }
      else if (btns[i].OverButton() && btns[i].localState)
      {
        btns[i].localState = false;
        println("option 2");
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

  public boolean LocalState()
  {
    return localState;
  }
}


class SmoothButton extends Button
{
  private int buttonSmooth;

  SmoothButton(int newX, int newY, int newSize, int newSmooth, String newName)
  {
    super(newX, newY, newSize, newName);
    buttonSmooth = newSmooth;
    localState = false;
  }

  public void displayButton()
  {
    {
      //If mouse is over button highlight it
      if (OverButton() || localState)
      {
        stroke(0);
        fill(buttonHighlight);
      }
      //If mouse isnt over and it isnt on then display normal colour
      else if (!OverButton() && !localState)
      {
        stroke(255);
        fill(buttonColour);
      }
      rect(buttonX, buttonY, buttonSize, buttonSize, buttonSmooth);
    }
  }
}

// class RectButton extends Button
// {
//   RectButton()
//   {
//     //do something!
//   }
// }
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
