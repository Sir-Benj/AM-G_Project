class Polygon extends DrawShape
{
  ArrayList<PVector> polyPoints;
  PVector newMousePos;
  PShape poly;
  Boolean pickFinished;

  Polygon(String shapeType, PVector mouseStartLoc, PGraphics layer,
          float hue, float sat, float bri, float sWeight, float opacity)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity);
    this.polyPoints = new ArrayList<PVector>();
  }

  void AddToPoints(PVector mousePos)
  {
    this.polyPoints.add(mousePos);
  }

  void FinishDrawingShape()
  {
    PVector xyMin, xyMax;
    xyMin = new PVector();
    xyMax = new PVector();

    float xMax = 0, xMin = width, yMax = 0, yMin = height;

    for (PVector v : polyPoints)
    {
      if (v.x > xMax)
      {
        xMax = v.x;
      }
      else if (v.x < xMin)
      {
        xMin = v.x;
      }

      if (v.y > yMax)
      {
        yMax = v.y;
      }
      else if (v.y < yMin)
      {
        yMin = v.y;
      }
    }

    xyMin.x = xMin;
    xyMin.y = yMin;

    xyMax.x = xMax;
    xyMax.y = yMax;

    setShapeBounds(xyMin, xyMax);

    this.isDrawing = false;

  }

  void drawThisShape()
  {
    this.layer.beginDraw();
    smooth();
    this.layer.colorMode(HSB);
    if (isDrawing)
    {
      this.poly = createShape();
      this.poly.beginShape();
      this.poly.strokeWeight(this.sWeight);
      this.poly.stroke(this.hue,
                       this.sat,
                       this.bri,
                       this.opacity);
      this.poly.noFill();
      for (PVector v : this.polyPoints)
      {
        this.poly.vertex(v.x, v.y);
      }
      this.poly.endShape();
      shape(poly);
    }
    else
    {
      this.poly = createShape();
      this.poly.beginShape();
      this.poly.strokeWeight(this.sWeight);
      this.poly.noStroke();
      this.poly.fill(this.hue,
                     this.sat,
                     this.bri,
                     this.opacity);
      for (PVector v : this.polyPoints)
      {
        this.poly.vertex(v.x - 20, v.y - 40);
      }
      this.poly.endShape(CLOSE);
      this.layer.smooth();
      this.layer.shape(poly);

      if (this.isSelected)
      {
        this.poly = createShape();
        this.poly.beginShape();
        this.poly.strokeWeight(this.sWeight);
        this.poly.stroke(255 - this.hue,
                         255 - this.sat,
                         255 - this.hue);
        this.poly.noFill();
        for (PVector v : this.polyPoints)
        {
          this.poly.vertex(v.x - 21, v.y - 38);
        }
        this.poly.endShape(CLOSE);
        this.layer.shape(poly);

      }
    }
    this.layer.endDraw();
    DefaultDrawSettings();
  }
}
