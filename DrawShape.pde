class DrawShape
{
  String shapeToDraw;

  PVector mouseStart, mouseDrag, mouseEnd;
  float radius;

  boolean isSelected = false;
  boolean isDrawing = false;

  int sWeight = 10;

  Rect bounds;

  PGraphics layer;

  DrawShape()
  {
  }

  void BeginDrawingShape(String shapeToDraw, PVector startPoint, PGraphics layer)
  {
    this.isDrawing = true;
    this.shapeToDraw = shapeToDraw;
    this.mouseStart = startPoint;
    this.mouseDrag = startPoint;
    this.layer = layer;
  }

  void WhileDrawingShape(PVector dragPoint)
  {
    this.mouseDrag = dragPoint;
  }

  void FinishDrawingShape(PVector endPoint)
  {
    this.mouseEnd = endPoint;
    setShapeBounds(this.mouseStart, this.mouseEnd);
    this.isDrawing = false;
  }

  void setShapeBounds(PVector vecOne, PVector vecTwo)
  {
    bounds = new Rect(vecOne, vecTwo);
  }

  boolean SelectThis(PVector vec)
  {
    if (bounds.isInsideThis(vec))
    {
      this.isSelected = !this.isSelected;
      return true;
    }
    else
    {
      return false;
    }
  }

  void drawThisShape()
  {
    this.layer.beginDraw();
    this.layer.strokeWeight(1);
    this.layer.noFill();
    if (isDrawing)
    {
      strokeWeight(1);
      float x1 = this.mouseStart.x;
      float y1 = this.mouseStart.y;
      float wid = this.mouseDrag.x - x1;
      float hgt = this.mouseDrag.y - y1;
      rect(x1, y1,wid,hgt);
    }
    else
    {
      float x1 = this.bounds.left;
      float y1 = this.bounds.top;
      float wid = this.bounds.getWidth();
      float hgt = this.bounds.getHeight();
      this.layer.rect(x1 - 20, y1 - 40, wid, hgt);

      if (this.isSelected)
      {
        this.layer.noFill();
        this.layer.strokeWeight(1);
        this.layer.stroke(255,50,50);
        this.layer.rect(x1-1,y1-1,wid+2,hgt+2);
      }
    }
    this.layer.endDraw();

  }
}
