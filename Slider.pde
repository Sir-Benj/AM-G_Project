class Slider
{
  int sliderWidth, sliderHeight;
  float xBarPos, yBarPos,
        xSliderPos, newSliderPos,
        sliderMinPos, sliderMaxPos,
        ratio;
  boolean over, locked;

  Slider(float xPos, float yPos, int sWidth, int sHeight)
  {
    sliderWidth = sWidth;
    sliderHeight = sHeight;
    int widthToHeight = sWidth - sHeight;
    ratio = (float)sWidth / (float)widthToHeight;
    xBarPos = xPos;
    yBarPos = yPos - sliderHeight / 2;
    xSliderPos = xBarPos + sWidth / 2 - sHeight / 2;
    newSliderPos = xSliderPos;
    sliderMinPos = xBarPos;
    sliderMaxPos = xBarPos + sWidth - sHeight;
  }

  boolean OverSlider()
  {
    if (mouseX > xBarPos && mouseX < xBarPos + sliderWidth &&
        mouseY > yBarPos && mouseY < yBarPos + sliderHeight)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  void DisplaySlider()
  {
    noStroke();
    fill(200);
    rect(xBarPos, yBarPos, sliderWidth, sliderHeight);
    if (over || locked)
    {
      fill(0, 0, 0);
    }
    else
    {
      fill(102, 102, 102);
    }
    rect(xSliderPos, yBarPos, sliderHeight, sliderHeight);
  }

  float getPos()
  {
    return xBarPos * ratio;
  }
  
}
