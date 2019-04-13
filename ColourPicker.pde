class ColourPicker
{
  float barWidth = 128;
  public float _hueVal = barWidth;
  public float _satVal = barWidth;
  public float _briVal = barWidth;


  ColourPicker() {}

  void DrawPicker(float colourMenuXInset, float colourMenuYInset)
  {
    _hueVal= DrawSlider(colourMenuXInset, colourMenuYInset + 190, barWidth, 40.0, _hueVal, _hueVal, "Hue");
    _satVal= DrawSlider(colourMenuXInset, colourMenuYInset + 265, barWidth, 20.0, _satVal, _hueVal, "Saturation");
    _briVal= DrawSlider(colourMenuXInset, colourMenuYInset + 315, barWidth, 20.0, _briVal, _hueVal, "Brightness");
    fill(_hueVal, _satVal, _briVal);
    rect(colourMenuXInset, colourMenuYInset, 150, 160);
  }

  float DrawSlider(float xPos, float yPos, float sWidth, float sHeight, float hueVal, float hueActVal, String display)
  {
    float sliderPos = map(hueVal, 0.0, 255.0, 0.0, sWidth);

    for(int i = 0; i < sWidth; i++)
    {
      float hueValue = map(i, 0.0, sWidth, 0.0, 255.0);
      switch(display)
      {
        case "Hue": stroke(hueValue, 255, 255);
                    break;
        case "Saturation": float satValue=map(i, 0.0, sWidth, 0.0, 255);
                    stroke(hueActVal, satValue, 255);
                    break;
        case "Brightness": float briValue=map(i, 0.0, sWidth, 0.0, 255);
                    stroke(hueActVal, 255, briValue);
                    break;
      }
        line(xPos + i, yPos, xPos + i, yPos + sHeight);
    }
    if(mousePressed && mouseX > xPos && mouseX < (xPos + sWidth)
       && mouseY > yPos && mouseY < yPos + sHeight)
    {
       sliderPos = mouseX - xPos;
       hueVal = map(sliderPos, 0.0, sWidth, 0.0, 255.0);
    }

    stroke(100);
    switch(display)
    {
      case "Hue": fill(_hueVal, 255, 255);
              break;
      case "Saturation": fill(_hueVal, _satVal, 255);
              break;
      case "Brightness": fill(_hueVal, 255, _briVal);
    }
    rect(sliderPos + xPos - 3, yPos - 5, 6, sHeight + 10);

    textSize(16);
    fill(0);
    switch(display)
    {
      case "Hue": text(display + ": " + (int)_hueVal , xPos + 10, yPos - 10);
              break;
      case "Saturation": text(display + ": " + (int)_satVal , xPos + 10, yPos - 10);
              break;
      case "Brightness": text(display + ": " + (int)_briVal , xPos + 10, yPos - 10);
    }

    if (hueVal >= 249.0)
    {
      hueVal = 255;
    }
    else if (hueVal <= 2.0)
    {
      hueVal = 0.0;
    }

    return hueVal;
  }
}
