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

  void Save(PGraphics layer, Button button, String newPath, File newFile)
  {
    selectOutput("Select Output", "fileSelected", selectOne);
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

  void Line(PGraphics layer, boolean clicked, int xFirst, int xSecond,
            int yFirst, int ySecond, ColourPicker colour)
  {
    if (xFirst < 10 || yFirst < 30 || xSecond > width - 200 || ySecond > height - 10)
    {
      return;
    }
    else if (clicked)
    {
      stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal);
      line(xFirst - 20, yFirst - 40, mouseX - 20, mouseY - 40);
      return;
    }

    layer.beginDraw();
    layer.colorMode(HSB);
    layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal);
    layer.strokeWeight(10);
    layer.line(xFirst - 20, yFirst - 40, xSecond - 20, ySecond - 40);
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

  void ClearLayer(PGraphics layer, Button button)
  {
    layer.clear();
    button.localState = false;
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
