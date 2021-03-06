// Slider class, creates UI slider elements which can be used
// to manipulate various pieces of data.
class Slider
{
  float xBarPos, yBarPos, barWidth, barHeight, mapValueLow, mapValueHigh,
        retValue;
  String sliderName, sNameValue;

  Slider(float xPos, float yPos, float barW, float barH, float mVL, float mVH,
         String sName, String sNValue)
  {
    xBarPos = xPos;
    yBarPos = yPos;
    barWidth = barW;
    barHeight = barH;
    mapValueLow = mVL;
    mapValueHigh = mVH;
    sliderName = sName;
    sNameValue = sNValue;
  }

  // Draws the slider depending on what is required for display
  float DrawSliderMenu(float retValue)
  {
    float sliderPos = map(retValue, mapValueLow, mapValueHigh, 0.0, barWidth);

    stroke(80);
    fill(100);
    rect(xBarPos, yBarPos, barWidth, barHeight);

    if(mousePressed && mouseX >=  xBarPos && mouseX <= (xBarPos + barWidth)
       && mouseY >= yBarPos && mouseY <= yBarPos + barHeight)
    {
      sliderPos = mouseX - xBarPos;
      retValue = map(sliderPos, 0.0, barWidth, mapValueLow, mapValueHigh);
    }

    if (sliderName == "Stroke Weight")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Opacity")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)((retValue / mapValueHigh) * 100) + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Offset X")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Offset Y")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Rotation Angle")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Scale")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos + 10, yBarPos - 10);
    }
    else if (sliderName == "Contrast")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (float)retValue + " " + sNameValue, xBarPos - 10, yBarPos - 10);
    }
    else if (sliderName == "Width")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos - 90, yBarPos + 10);
    }
    else if (sliderName == "Height")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (int)retValue + " " + sNameValue, xBarPos - 95, yBarPos + 10);
    }
    else if (sliderName == "HSB Value")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (float)retValue + " " + sNameValue, xBarPos - 10, yBarPos - 10);
    }
    else if (sliderName == "Contrast")
    {
      textSize(14);
      fill(1);
      text(sliderName + ": " + (float)retValue + " " + sNameValue, xBarPos - 10, yBarPos - 10);
    }

    stroke(1);
    fill(50);
    rect(sliderPos + xBarPos - 3, yBarPos - 5, 6, barHeight + 10);

    return retValue;
  }

  // Draws a horizontal slider
  float DrawSliderHorizontal(float retValue)
  {
    float sliderPos = map(retValue, mapValueLow, mapValueHigh, 0.0, barWidth);

    stroke(80);
    fill(100);
    rect(xBarPos, yBarPos, barWidth + barHeight, barHeight);

    if(mousePressed && mouseX >=  xBarPos && mouseX <= (xBarPos + barWidth)
       && mouseY >= yBarPos && mouseY <= yBarPos + barHeight)
    {
      sliderPos = mouseX - xBarPos;
      retValue = map(sliderPos, 0.0, barWidth, mapValueLow, mapValueHigh);
    }

    stroke(1);
    fill(50);
    rect(sliderPos + xBarPos, yBarPos, barHeight, barHeight);

    return retValue;
  }

  // Draws a vertical slider
  float DrawSliderVertical(float retValue)
  {
    float sliderPos = map(retValue, mapValueLow, mapValueHigh, 0.0, barHeight);

    stroke(80);
    fill(100);
    rect(xBarPos, yBarPos, barWidth, barHeight + barWidth);

    if(mousePressed && mouseX >=  xBarPos && mouseX <= (xBarPos + barWidth)
       && mouseY >= yBarPos && mouseY <= yBarPos + barHeight)
    {
      sliderPos = mouseY - yBarPos;
      retValue = map(sliderPos, 0.0, barHeight, mapValueLow, mapValueHigh);
    }

    stroke(1);
    fill(50);
    rect(xBarPos, yBarPos + sliderPos, barWidth, barWidth);

    return retValue;
  }
}
