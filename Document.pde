class Document
{

  ArrayList<DrawShape> shapeList = new ArrayList<DrawShape>();

  // this references the currently drawn shape. It is set to null
  // if no shape is currently being drawn
  public DrawShape currentlyDrawnShape = null;

  public Document()
  {
  }

  public void StartNewShape(String shapeType, PVector mouseStartLoc, PGraphics layer)
  {
    DrawShape newShape = new DrawShape();

    newShape.BeginDrawingShape(shapeType, mouseStartLoc, layer);
    shapeList.add(newShape);
    println(shapeList.size());
    currentlyDrawnShape = newShape;
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
