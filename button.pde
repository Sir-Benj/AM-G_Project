class Button
{
  protected int buttonX, buttonY, buttonWidth, buttonHeight, smoothing, layer;
  protected String buttonName;
  protected boolean isSmooth, hasBorder, localState;
  protected color buttonColour = color(100), buttonHighlight = color(200);

  Button(int newX, int newY, int newWidth, int newHeight, int newLayer, boolean smooth, boolean border, String newName)
  {
    buttonX = newX;
    buttonY = newY;
    buttonWidth = newWidth;
    buttonHeight = newHeight;
    layer = newLayer;
    isSmooth = smooth;
    hasBorder = border;
    buttonName = newName;
    smoothing = 8;
    localState = false;
  }

  void DisplayButton()
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
      text(buttonName, buttonX, buttonY + buttonHeight/1.5);
    }
    else
    {
      rect(buttonX, buttonY, buttonWidth, buttonHeight);
      fill(0);
      text(buttonName, buttonX, buttonY + buttonHeight/1.5);
    }
  }

  boolean OverButton()
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

  void ButtonPressed(Button[] btns)
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
