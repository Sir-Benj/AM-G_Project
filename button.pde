class Button
{
  int buttonX, buttonY, buttonSize;
  String buttonName;
  boolean changeState;
  color buttonColour = color(100), buttonHighlight = color(200);

  Button(int newX, int newY, int newSize, String newName)
  {
    buttonX = newX;
    buttonY = newY;
    buttonSize = newSize;
    buttonName = newName;
    changeState = false;
  }

  void displayButton()
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

  boolean buttonOver()
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

  void buttonPressed(Button btn)
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

  boolean changeState()
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

  void displayButton()
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
