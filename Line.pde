class Line extends DrawShape
{
  Line(String shapeType, PVector mouseStartLoc, PGraphics layer,
       float hue, float sat, float bri, float sWeight, float opacity)
  {
    super(shapeType, mouseStartLoc, layer, hue, sat, bri, sWeight, opacity);
  }

  void drawThisShape()
  {
    this.layer.beginDraw();
    this.layer.colorMode(HSB);
    DrawSettings();
    if (isDrawing)
    {
      stroke(this.hue,
             this.sat,
             this.bri,
             this.opacity);
      float x1 = this.mouseStart.x;
      float y1 = this.mouseStart.y;
      float x2 = this.mouseDrag.x;
      float y2 = this.mouseDrag.y;
      line(x1, y1, x2, y2);
    }
    else
    {
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      float x1 = this.bounds.x1;
      float y1 = this.bounds.y1;
      float wid = this.bounds.x2;
      float hgt = this.bounds.y2;
      this.layer.line(x1 - 20, y1 - 100, wid - 20, hgt - 100);

      if (this.isSelected)
      {
        this.layer.line(x1 - 21, y1 - 101, wid - 18, hgt + 98);
      }
    }
    this.layer.endDraw();
    DefaultDrawSettings();
  }
}
