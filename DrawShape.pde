// The base class for all the shape classes, all the shape subclasses inherit
// from this and thus it allows for them to be easily stored in a single ArrayList
class DrawShape
{
  // Fields

  // Shape type
  String shapeToDraw;
  // Starting, dragging and final mouse coordinate vectors
  PVector mouseStart, mouseDrag, mouseEnd;
  // All stroke and colour values of the shape
  float hue, sat, bri, opacity, sWeight, rotateValue, scaleValue;
  // Selection, drawing and if the shape is filled or not
  boolean isSelected = false;
  boolean isDrawing = false;
  boolean isFilled = false;
  // ArrayList for the vertex points in polygon and curve shapes
  ArrayList<PVector> polyPoints;
  // For keeping the rectangular bounds of the shape
  Rect bounds;
  // The layer onto which the shape is drawn
  PGraphics layer;

  // Empty Constructor
  DrawShape() {}

  // Main Constructor
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
    this.rotateValue = 0;
    this.scaleValue = 1;
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

  // For use in the polygon and curve classes
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

  // Settings shared between shapes
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
