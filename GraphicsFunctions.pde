class GraphicsFunctions
{

  GraphicsFunctions() {}

  void New(PGraphics layer, Button button)
  {
    layer.clear();
    button.localState = false;
  }

  void Save()
  {

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
        layer.line(mouseX - 20, mouseY - 40, pmouseX - 20, pmouseY - 40);
      }
    }
    layer.endDraw();
  }

  void Eraser()
  {

  }

  void Line()
  {

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
