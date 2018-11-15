class Button
{
  int buttonX, buttonY, buttonSize;
  String buttonName;
  boolean localState;
  color buttonColour = color(100), buttonHighlight = color(200);

  Button(int newX, int newY, int newSize, String newName)
  {
    buttonX = newX;
    buttonY = newY;
    buttonSize = newSize;
    buttonName = newName;
    localState = false;
  }

  void DisplayButton()
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

  boolean OverButton()
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

  void ButtonPressed(Button[] btns)
  {
    for (int i = 0; i < btns.length; i++)
    {
      if (btns[i].OverButton() && !btns[i].localState)
      {
        btns[i].localState = true;
      }
      else if (btns[i].OverButton() && btns[i].localState)
      {
        btns[i].localState = false;
      }
    }
  }

  void TopMenuButtonPressed(Button btn)
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

  boolean LocalState()
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

  void displayButton()
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
