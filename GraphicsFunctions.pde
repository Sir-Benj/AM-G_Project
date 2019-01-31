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

  void ShapeStart(String name, PVector mouseStart, PGraphics layer, Document doc,
                      ColourPicker colourPicker, float sWeight, float opacity)
  {
     doc.StartNewShape(name, mouseStart, layer,
                       colourPicker._hueVal,
                       colourPicker._satVal,
                       colourPicker._briVal,
                       sWeight, opacity);
  }

  void ShapeDrag(Document doc, PVector mouseDrag)
  {
    if (doc.currentlyDrawnShape == null)
    {
      return;
    }
    doc.currentlyDrawnShape.WhileDrawingShape(mouseDrag);
  }

  void ShapeFinal(Document doc, PVector mouseFinal)
  {
    if (doc.currentlyDrawnShape == null)
    {
      return;
    }
    doc.currentlyDrawnShape.FinishDrawingShape(mouseFinal);
    doc.currentlyDrawnShape = null;
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
