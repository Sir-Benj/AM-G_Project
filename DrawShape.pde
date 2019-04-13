class DrawShape
{
  String shapeToDraw;

  PVector mouseStart, mouseDrag, mouseEnd;

  float hue, sat, bri, opacity, sWeight;

  boolean isSelected = false;
  boolean isDrawing = false;
  boolean isFilled = false;

  ArrayList<PVector> polyPoints; 
  Rect bounds;

  PGraphics layer;

  DrawShape() {}

  DrawShape(String shapeToDraw, PVector startPoint, PGraphics layer,
    float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
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
    this.isFilled = filled;
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
    this.bounds = new Rect(vecOne, vecTwo);
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
      this.layer.strokeWeight(sWeight);
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      if (isFilled)
      {
        this.layer.fill(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      }
      else
      {
        this.layer.noFill();
      }
    }
    else
    {
      this.layer.strokeWeight(sWeight);
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      if (isFilled)
      {
        this.layer.fill(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      }
      else
      {
        this.layer.noFill();
      }
    }

    if (this.isSelected)
    {
      if (this.isFilled)
      {
        this.layer.fill(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      }
      else
      {
        this.layer.noFill();
      }

      this.layer.strokeWeight(5);
      this.layer.stroke(255 - this.hue,
                        255 - this.sat,
                        255 - this.bri);
    }
  }
}
