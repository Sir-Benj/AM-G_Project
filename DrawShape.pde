class DrawShape
{
  String shapeToDraw;

  PVector mouseStart, mouseDrag, mouseEnd;

  float hue, sat, bri, opacity, sWeight;

  boolean isSelected = false;
  boolean isDrawing = false;

  Rect bounds;

  PGraphics layer;

  DrawShape(String shapeToDraw, PVector startPoint, PGraphics layer,
    float hue, float sat, float bri, float sWeight, float opacity)
  {
    this.isDrawing = true;
    this.shapeToDraw = shapeToDraw;
    this.mouseStart = startPoint;
    this.mouseDrag = startPoint;
    this.layer = layer;
    this.hue = hue;
    this.sat = sat;
    this.bri = bri;
    this.opacity = opacity;
    this.sWeight = sWeight;
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

  void AddToPoints(PVector mouseStart) {}

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
    point(this.mouseStart.x, this.mouseStart.y);
  }

  void DrawSettings()
  {
    if (isDrawing)
    {
      strokeWeight(sWeight);
      noStroke();
      fill(this.hue,
           this.sat,
           this.bri,
           this.opacity);
    }
    else
    {
      this.layer.strokeWeight(sWeight);
      this.layer.noStroke();
      this.layer.fill(this.hue,
                      this.sat,
                      this.bri,
                      this.opacity);
    }

    if (this.isSelected)
    {
      this.layer.noFill();
      this.layer.strokeWeight(2);
      this.layer.stroke(255 - this.hue,
                        255 - this.sat,
                        255 - this.bri);
    }
  }

  void DefaultDrawSettings()
  {
    stroke(0);
    strokeWeight(1);
    noFill();
  }

}
