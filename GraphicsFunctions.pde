class GraphicsFunctions
{
  GraphicsFunctions()
  {
  }

  void New(PGraphics layer, Button button)
  {
    layer.clear();
    button.localState = false;
  }

  void Save(PGraphics layer, Button button, String newPath)
  {
    selectOutput("Select Output", "fileSelected");
    layer.save(newPath);
    button.localState = false;
  }

  void Load()
  {

  }

  void Undo()
  {

  }

  void Redo()
  {

  }

  void Blur()
  {

  }

  void Sharpen()
  {

  }

  void Greyscale()
  {

  }

  void Monochrome()
  {

  }

  void Pencil(PGraphics layer, ColourPicker colourPicker)
  {

    layer.beginDraw();
    layer.colorMode(HSB);
    if (mousePressed)
    {
      if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
      {
        layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal);
        layer.strokeWeight(5);
        layer.line(mouseX - 20, mouseY - 40, pmouseX - 20, pmouseY - 40);
      }
    }
    layer.endDraw();
  }

  void Eraser(PGraphics layer)
  {
    layer.beginDraw();
    layer.colorMode(HSB);
    if (mousePressed)
    {
      if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
      {
        layer.stroke(255);
        layer.strokeWeight(10);
        layer.line(mouseX - 20, mouseY - 40, pmouseX - 20, pmouseY - 40);
      }
    }
    layer.endDraw();
  }

  void Line(PGraphics layer, boolean firstClick)
  {
    int pmX = 0;
    int pmY = 0;

    layer.beginDraw();
    layer.colorMode(HSB);
    if (mousePressed)
    {
      if (firstClick)
      {
        pmX = mouseX;
        pmY = mouseY;

        firstClick = false;
      }
      else if (!firstClick)
      {
        layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal);
        strokeWeight(10);
        line(mouseX, mouseY, pmX, pmY);

        firstClick = true;
      }
    }
  }

  void Rectangle()
  {

  }

  void Circle()
  {

  }

  void Polygon()
  {

  }

  void Duplicate()
  {

  }

  void ScaleShape()
  {

  }

  void RotateShape()
  {

  }

  void ClearLayer()
  {

  }

  void Resize()
  {

  }

  void EdgeDetect()
  {

  }

  void Rotate()
  {

  }

  void Hue()
  {

  }

  void Saturation()
  {

  }

  void Brightness()
  {

  }

  void Contrast()
  {

  }

}
