class Line extends DrawShape
{
  Line(String shapeType, PVector mouseStartLoc, PGraphics layer,
       float hue, float sat, float bri, float sWeight, float opacity, boolean filled)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity, filled);
  }

  void drawThisShape()
  {
    this.layer.beginDraw();
    this.layer.colorMode(HSB);
    DrawSettings();
    if (isDrawing)
    {
      // this.layer.stroke(this.hue,
      //        this.sat,
      //        this.bri,
      //        this.opacity);
      float x1 = this.mouseStart.x;
      float y1 = this.mouseStart.y;
      float x2 = this.mouseDrag.x;
      float y2 = this.mouseDrag.y;
      this.layer.line(x1 - 20, y1 - 100, x2 - 20, y2 - 100);
    }
    else
    {
      float x1 = this.bounds.x1;
      float y1 = this.bounds.y1;
      float wid = this.bounds.x2;
      float hgt = this.bounds.y2;

      if (this.isSelected)
      {
        // this.layer.strokeWeight(this.sWeight + 5);
        // this.layer.stroke(255 - this.hue, 255 - this.sat, 255 - this.bri);
        this.layer.line(x1 - 20, y1 - 100, wid - 20, hgt - 100);
      }

      this.layer.strokeWeight(this.sWeight);
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      this.layer.line(x1 - 20, y1 - 100, wid - 20, hgt - 100);
    }
    this.layer.endDraw();
  }
}
