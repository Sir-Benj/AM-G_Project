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

  void Save(Button button, File newFile)
  {
    selectOutput("Select Output", "fileSelected", newFile);
    button.localState = false;
  }

  void Load(Button button, File newFile)
  {
    selectInput("Select An Image To Edit", "fileChosen", newFile);
    button.localState = false;
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

  void Pencil(PGraphics layer, ColourPicker colourPicker, float sVOne, float sVTwo)
  {

    layer.beginDraw();
    layer.colorMode(HSB);
    if (mousePressed)
    {
      if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
      {
        layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal, sVTwo);
        layer.strokeJoin(ROUND);
        layer.strokeWeight(sVOne);
        layer.line(mouseX - 20, mouseY - 40, pmouseX - 20, pmouseY - 40);
      }
    }
    layer.endDraw();
  }

  void Eraser(PGraphics layer, float sVOne)
  {
    layer.beginDraw();
    layer.colorMode(HSB);
    if (mousePressed)
    {
      if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
      {
        layer.stroke(255);
        layer.strokeWeight(sVOne);
        layer.line(mouseX - 20, mouseY - 40, pmouseX - 20, pmouseY - 40);
      }
    }
    layer.endDraw();
  }

  void Line(PGraphics layer, boolean clicked, int xFirst, int xSecond,
            int yFirst, int ySecond, ColourPicker colour, float sVOne, float sVTwo)
  {
    if (xFirst < 10 || yFirst < 30 || xSecond > width - 200 || ySecond > height - 10)
    {
      return;
    }
    else if (clicked)
    {
      return;
    }

    layer.beginDraw();
    layer.colorMode(HSB);
    layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal, sVTwo);
    layer.strokeWeight(sVOne);
    layer.line(xFirst - 20, yFirst - 40, xSecond - 20, ySecond - 40);

  }

  void Rectangle(PGraphics layer, boolean pressed, float xOnPress, float xOffset, float yOnPress,
                 float yOffset, ColourPicker colourPicker, float sVOne, float sVTwo)
  {
    if (xOnPress < 10 || yOnPress < 30 || xOffset > width - 200 || yOffset > height - 10)
    {
      return;
    }
    if (!pressed)
    {
      layer.beginDraw();
      layer.noFill();
      layer.strokeWeight(sVOne);
      layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal, sVTwo);
      layer.rect(xOnPress - 20, yOnPress - 40, xOffset, yOffset);
      layer.endDraw();
    }
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

  void ClearLayer(PGraphics layer, Button button, Document doc)
  {
    layer.clear();
    doc.shapeList = new ArrayList<DrawShape>();
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
