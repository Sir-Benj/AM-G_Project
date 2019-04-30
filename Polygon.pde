// Polygon class, subclass of DrawShape used to draw open and
// closed polygons to the screen.
class Polygon extends DrawShape
{
  PVector newMousePos;
  PShape poly;
  Boolean pickFinished;

  float xMax = 0, xMin = width, yMax = 0, yMin = height;

  PVector xyMin, xyMax;

  Polygon(String shapeType, PVector mouseStartLoc, PGraphics layer,
          float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
    polyPoints = new ArrayList<PVector>();
    xyMin = new PVector();
    xyMax = new PVector();
  }

  // Adds the vertex points to the poly list when drawing
  void AddToPoints(PVector mousePos)
  {
    this.polyPoints.add(mousePos);
  }

  // Sets the shape bounds when drawing
  void FinishDrawingShape(PVector endPoint)
  {
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

  // Draws the shape at the various stages of the draw
  void drawThisShape()
  {
    this.layer.beginDraw();
    //smooth();
    this.layer.colorMode(HSB);
    //DrawSettings();
    if (isDrawing)
    {
      this.poly = createShape();
      this.poly.beginShape();
      this.poly.strokeWeight(this.sWeight);
      this.poly.stroke(this.hue,
                       this.sat,
                       this.bri,
                       this.opacity);
      if (isFilled)
      {
        this.poly.fill(this.hue,
                       this.sat,
                       this.bri,
                       this.opacity);
      }
      else
      {
        this.poly.noFill();
      }

      for (PVector v : this.polyPoints)
      {
        this.poly.vertex(v.x - 20, v.y - 100);
      }

      if (isFilled)
      {
        this.poly.endShape(CLOSE);
      }
      else
      {
        this.poly.endShape();
      }

      this.layer.shape(poly);
    }
    else
    {
      if (this.isSelected)
      {
        this.poly = createShape();
        this.poly.beginShape();
        this.poly.strokeWeight(this.sWeight + 5);
        this.poly.stroke(255 - this.hue,
                         255 - this.sat,
                         255 - this.hue);
        this.poly.noFill();
        for (PVector v : this.polyPoints)
        {
          this.poly.vertex(v.x - 20, v.y - 100);
        }
        this.poly.endShape();
        this.layer.pushMatrix();
        this.layer.translate((xyMin.x + xyMax.x) / 2 , (xyMin.y + xyMax.y) / 2);
        this.layer.scale(this.scaleValue);
        this.layer.rotate(this.rotateValue);
        this.layer.translate(-((xyMin.x + xyMax.x) / 2) , -((xyMin.y + xyMax.y) / 2));
        this.layer.shape(poly);
        this.layer.popMatrix();
      }
    }

    this.poly = createShape();
    this.poly.beginShape();
    this.poly.strokeWeight(this.sWeight);
    this.poly.stroke(this.hue,
                     this.sat,
                     this.bri,
                     this.opacity);

    if (isFilled)
    {
      this.poly.fill(this.hue,
                     this.sat,
                     this.bri,
                     this.opacity);
    }
    else
    {
      this.poly.noFill();
    }

    for (PVector v : this.polyPoints)
    {
      this.poly.vertex(v.x - 20, v.y - 100);
    }

    if (this.isFilled)
    {
      this.poly.endShape(CLOSE);
    }
    else
    {
      this.poly.endShape();
    }

    this.layer.pushMatrix();
    this.layer.translate((xyMin.x + xyMax.x) / 2 , (xyMin.y + xyMax.y) / 2);
    this.layer.scale(this.scaleValue);
    this.layer.rotate(this.rotateValue);
    this.layer.translate(-((xyMin.x + xyMax.x) / 2) , -((xyMin.y + xyMax.y) / 2));
    this.layer.shape(poly);
    this.layer.popMatrix();
    this.layer.endDraw();
  }
}
