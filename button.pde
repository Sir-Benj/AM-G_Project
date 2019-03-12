class Button
{
  protected int buttonX, buttonY, buttonWidth, buttonHeight, smoothing;
  protected String buttonName;
  protected boolean isSmooth, hasBorder, showName, hasIcon, localState, invert, inverted, menuDisplayed;
  protected color buttonColour = color(180), buttonHighlight = color(210);
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

      if (isSmooth)
      {
        rect(buttonX, buttonY, buttonWidth, buttonHeight, smoothing);
        fill(0);
        if (showName)
        {
          text(buttonName, buttonX, buttonY + buttonHeight/1.5);
        }
      }
      else
      {
        rect(buttonX, buttonY, buttonWidth, buttonHeight);
        fill(0);
        if (showName)
        {
          text(buttonName, buttonX, buttonY + buttonHeight/1.5);
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
          text(buttonName, buttonX, buttonY + buttonHeight/1.5);
        }
      }
      else
      {
        rect(buttonX, buttonY, buttonWidth, buttonHeight);
        fill(0);
        if (showName)
        {
          text(buttonName, buttonX, buttonY + buttonHeight/1.5);
        }
      }

      if (iconImage != null && hasIcon)
      {
        image(iconImage, buttonX, buttonY);
      }
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

  void TopMenuButtonPressed(Button[] btns)
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

  void SingleButtonPress()
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

  void NotOverButton()
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
