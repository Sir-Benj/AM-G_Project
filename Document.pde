class Document
{

  ArrayList<DrawShape> shapeList = new ArrayList<DrawShape>();

  // this references the currently drawn shape. It is set to null
  // if no shape is currently being drawn
  public DrawShape currentlyDrawnShape = null;

  public Document()
  {
  }

  public void StartNewShape(String shapeType, PVector mouseStartLoc, PGraphics layer,
                            float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
  {
    switch (shapeType)
    {
      case "Rectangle": DrawShape newRectangle = new Rectangle(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
                        shapeList.add(newRectangle);
                        currentlyDrawnShape = newRectangle;
                        break;

      case "Circle":    DrawShape newCircle = new Circle(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
                        shapeList.add(newCircle);
                        currentlyDrawnShape = newCircle;
                        break;

      case "Line":      DrawShape newLine = new Line(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
                        shapeList.add(newLine);
                        currentlyDrawnShape = newLine;
                        break;

      case "Polygon":   DrawShape newPoly = new Polygon(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
                        shapeList.add(newPoly);
                        currentlyDrawnShape = newPoly;
                        break;
      case "Curve":   DrawShape newCurve = new Curve(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
                        shapeList.add(newCurve);
                        currentlyDrawnShape = newCurve;
                        break;
    }
  }

  public void DrawMe()
  {
    for(DrawShape s : shapeList)
    {
      s.drawThisShape();
    }
  }

  public void TrySelect(PVector p)
  {
    boolean selectionFound = false;
    for(DrawShape s : shapeList)
    {
      selectionFound = s.SelectThis(p);
      if(selectionFound) break;
    }
  }
}
