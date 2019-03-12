class Rectangle extends DrawShape
{
  Rectangle(String shapeType, PVector mouseStartLoc, PGraphics layer,
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
      float x1 = this.mouseStart.x;
      float y1 = this.mouseStart.y;
      float wid = this.mouseDrag.x - x1;
      float hgt = this.mouseDrag.y - y1;
      rect(x1,y1,wid,hgt);
    }
    else
    {
      float x1 = this.bounds.left;
      float y1 = this.bounds.top;
      float wid = this.bounds.getWidth();
      float hgt = this.bounds.getHeight();
      this.layer.rect(x1 - 20, y1 - 100, wid, hgt);

      if (this.isSelected)
      {
        this.layer.rect(x1 - 21, y1 - 101, wid + 2, hgt + 2);
      }
    }
    this.layer.endDraw();
    DefaultDrawSettings();
  }
}
