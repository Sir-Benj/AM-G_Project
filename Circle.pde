// The Circle class, sub class of the Drawshape class.
// Allows the user to draw circles by dragging the mouse.
class Circle extends DrawShape
{
  // Constructor
  Circle(String shapeType, PVector mouseStartLoc, PGraphics layer,
            float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
  }

  // Draws the shape depending on when it is in the cycle,
  // starting the draw, dragging the mouse to draw, and
  // finally on release the completed shape.
  void drawThisShape()
  {
    this.layer.beginDraw();
    this.layer.colorMode(HSB);
    DrawSettings();
    if (isDrawing)
    {
      this.layer.ellipseMode(CORNER);
      float x1 = this.mouseStart.x - 20;
      float y1 = this.mouseStart.y - 100;
      float wid = this.mouseDrag.x - x1;
      float hgt = this.mouseDrag.y - y1;
      this.layer.ellipse(x1, y1, wid - 20, hgt - 100);
    }
    else
    {
      float x1 = this.bounds.left;
      float y1 = this.bounds.top;
      float wid = this.bounds.getWidth();
      float hgt = this.bounds.getHeight();

      if (this.isSelected)
      {
        this.layer.ellipseMode(CORNER);
        this.layer.strokeWeight(this.sWeight + 5);
        this.layer.stroke(255 - this.hue, 255 - this.sat, 255 - this.bri);
        this.layer.pushMatrix();
        this.layer.scale(this.scaleValue);
        this.layer.rotate(this.rotateValue);
        this.layer.ellipse(x1 - 20, y1 - 100, wid, hgt);
        this.layer.popMatrix();
      }

      this.layer.ellipseMode(CORNER);
      this.layer.strokeWeight(this.sWeight);
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      this.layer.pushMatrix();
      this.layer.scale(this.scaleValue);
      this.layer.rotate(this.rotateValue);
      this.layer.ellipse(x1 - 20, y1 - 100, wid, hgt);
      this.layer.popMatrix();
    }
    this.layer.endDraw();
  }
}
