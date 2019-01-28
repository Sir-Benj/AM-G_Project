class DrawShape
{
  String shapeToDraw;

  PVector mouseStart, mouseDrag, mouseEnd;

  float hue, sat, bri, opacity, sWeight;

  boolean isSelected = false;
  boolean isDrawing = false;
  boolean isDrawn = false;

  Rect bounds;

  PGraphics layer;

  DrawShape()
  {
  }

  void BeginDrawingShape(String shapeToDraw, PVector startPoint, PGraphics layer,
    float hue, float sat, float bri, float sWeight, float opacity)
  {
    this.isDrawing = true;
    this.isDrawn = false;
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
    this.layer.colorMode(HSB);
    DrawSettings();
    if (isDrawing)
    {
      float x1 = this.mouseStart.x;
      float y1 = this.mouseStart.y;
      float wid = this.mouseDrag.x - x1;
      float hgt = this.mouseDrag.y - y1;
      rect(x1, y1, wid, hgt);
    }
    else
    {
      float x1 = this.bounds.left;
      float y1 = this.bounds.top;
      float wid = this.bounds.getWidth();
      float hgt = this.bounds.getHeight();
      this.layer.rect(x1 - 20, y1 - 40, wid, hgt);
      this.isDrawn = true;

      if (this.isSelected)
      {
        this.layer.rect(x1-1,y1-1,wid+2,hgt+2);
      }
    }
    this.layer.endDraw();
    DefaultDrawSettings();
  }

  void DrawSettings()
  {
    if (isDrawing)
    {
      strokeWeight(sWeight);
      stroke(this.hue,
             this.sat,
             this.bri,
             this.opacity);
      fill(this.hue,
           this.sat,
           this.bri,
           this.opacity);
    }
    else
    {
      this.layer.strokeWeight(sWeight);
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      this.layer.fill(this.hue,
                      this.sat,
                      this.bri,
                      this.opacity);
    }

    if (this.isSelected)
    {
      this.layer.noFill();
      this.layer.strokeWeight(1);
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
