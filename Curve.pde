// The Curve class is a sub class of the Drawshape class,
// and allows the user to specify points which are then connected
// to form a curve.
class Curve extends DrawShape
{
  PVector newMousePos;
  PShape poly;
  Boolean pickFinished;

  // Constructor
  Curve(String shapeType, PVector mouseStartLoc, PGraphics layer,
        float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
    polyPoints = new ArrayList<PVector>();
  }

  // Add the created vertex points to the ArrayList
  void AddToPoints(PVector mousePos)
  {
    this.polyPoints.add(mousePos);
  }

  // Complete the shape by finding the area size,
  // and setting the isDrawing to false;
  void FinishDrawingShape(PVector endPoint)
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

    println(xyMin);
    println(xyMax);

    setShapeBounds(xyMin, xyMax);

    this.isDrawing = false;

  }

  // Draws the shape depending on when it is in the cycle,
  // starting the draw, clicking the points, and
  // finally on right click or closing the shape,
  // the completed shape.
  void drawThisShape()
  {
    this.layer.beginDraw();
    this.layer.colorMode(HSB);
    if (isDrawing)
    {
      this.layer.beginShape();
      this.layer.strokeWeight(this.sWeight);
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

      for (PVector v : this.polyPoints)
      {
        this.layer.curveVertex(v.x - 20, v.y - 100);
        println(v);
      }

      if (isFilled)
      {
        this.layer.endShape(CLOSE);
      }
      else
      {
        this.layer.endShape();
      }
    }
    else
    {
      if (this.isSelected)
      {
        this.layer.beginShape();
        this.layer.strokeWeight(this.sWeight + 5);
        this.layer.stroke(255 - this.hue,
                         255 - this.sat,
                         255 - this.hue);
        this.layer.noFill();
        for (PVector v : this.polyPoints)
        {
          this.layer.curveVertex(v.x - 20, v.y - 100);
        }
        this.layer.pushMatrix();
        this.layer.scale(this.scaleValue);
        this.layer.rotate(this.rotateValue);
        this.layer.endShape();
        this.layer.popMatrix();
      }
    }

    this.layer.beginShape();
    this.layer.strokeWeight(this.sWeight);
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

    for (PVector v : this.polyPoints)
    {
      this.layer.curveVertex(v.x - 20, v.y - 100);
    }

    this.layer.pushMatrix();
    this.layer.scale(this.scaleValue);
    this.layer.rotate(this.rotateValue);
    if (this.isFilled)
    {
      this.layer.endShape(CLOSE);
    }
    else
    {
      this.layer.endShape();
    }
    this.layer.popMatrix();
    this.layer.endDraw();
  }
}
