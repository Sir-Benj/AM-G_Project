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
Button btn1;
Button btn2;
SmoothButton btn3;

public void settings()
{
  size(600, 600);
}

public void setup()
{
  surface.setResizable(true);
  background(255, 255, 255, 255);
  btn1 = new Button(10, 10, 50, "paint");
  btn2 = new Button(10, 70, 50, "erase");
  btn3 = new SmoothButton(10, 130, 50, 8, "smooth");
}

public void mousePressed()
{
  btn1.buttonPressed(btn2);
  btn2.buttonPressed(btn1);
  btn3.buttonPressed(btn1);


  if (btn1.changeState && !btn2.changeState)
  {
    stroke(0);
    fill(0);
    ellipse(mouseX, mouseY, 20, 20);
  }
  if (btn2.changeState && !btn1.changeState)
  {
    stroke(255);
    fill(255);
    ellipse(mouseX, mouseY, 20, 20);
  }
}

public void mouseDragged()
{
  if (btn1.changeState() && !btn2.changeState)
  {
    stroke(0);
    fill(0);
    ellipse(mouseX, mouseY, 20, 20);
  }
  if (btn2.changeState() && !btn1.changeState)
  {
    stroke(255);
    fill(255);
    ellipse(mouseX, mouseY, 20, 20);
  }
}

public void draw()
{
  btn1.displayButton();
  btn2.displayButton();
  btn3.displayButton();
}
class Button
{
  int buttonX, buttonY, buttonSize;
  String buttonName;
  boolean changeState;
  int buttonColour = color(100), buttonHighlight = color(200);

  Button(int newX, int newY, int newSize, String newName)
  {
    buttonX = newX;
    buttonY = newY;
    buttonSize = newSize;
    buttonName = newName;
    changeState = false;
  }

  public void displayButton()
  {
    //If mouse is over button highlight it
    if (buttonOver() || changeState)
    {
      stroke(0);
      fill(buttonHighlight);
    }
    //If mouse isnt over and it isnt on then display normal colour
    else if (!buttonOver() && !changeState)
    {
      stroke(255);
      fill(buttonColour);
    }
    rect(buttonX, buttonY, buttonSize, buttonSize);
  }

  public boolean buttonOver()
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

  public void buttonPressed(Button btn)
  {
    if (buttonOver() && !changeState)
    {
      //Button on
      changeState = true;
      btn.changeState = false;
    }
    else if (buttonOver() && changeState || btn.changeState)
    {
      //Button off
      changeState = false;
    }
  }

  public boolean changeState()
  {
    return changeState;
  }
}


class SmoothButton extends Button
{
  private int buttonSmooth;

  SmoothButton(int newX, int newY, int newSize, int newSmooth, String newName)
  {
    super(newX, newY, newSize, newName);
    buttonSmooth = newSmooth;
    changeState = false;
  }

  public void displayButton()
  {
    {
      //If mouse is over button highlight it
      if (buttonOver() || changeState)
      {
        stroke(0);
        fill(buttonHighlight);
      }
      //If mouse isnt over and it isnt on then display normal colour
      else if (!buttonOver() && !changeState)
      {
        stroke(255);
        fill(buttonColour);
      }
      rect(buttonX, buttonY, buttonSize, buttonSize, buttonSmooth);
    }
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
