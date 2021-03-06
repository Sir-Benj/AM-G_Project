// Rectangle class, subclass of DrawShape class used to draw rectangles to the screen
class Rectangle extends DrawShape
{
  Rectangle(String shapeType, PVector mouseStartLoc, PGraphics layer,
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
      float x1 = this.mouseStart.x - 20;
      float y1 = this.mouseStart.y - 100;
      float wid = this.mouseDrag.x - x1;
      float hgt = this.mouseDrag.y - y1;
      this.layer.rect(x1, y1, wid - 20, hgt - 100);
    }
    else
    {
      float x1 = this.bounds.left;
      float y1 = this.bounds.top;
      float wid = this.bounds.getWidth();
      float hgt = this.bounds.getHeight();

      if (this.isSelected)
      {
        this.layer.strokeWeight(this.sWeight + 5);
        this.layer.stroke(255 - this.hue, 255 - this.sat, 255 - this.bri);
        this.layer.pushMatrix();
        this.layer.translate((x1 + wid) / 2 , (y1 + hgt) / 2);
        this.layer.scale(this.scaleValue);
        this.layer.rotate(this.rotateValue);
        this.layer.translate(-((x1 + wid) / 2) , -((y1 + hgt) / 2));
        this.layer.rect(x1 - 20, y1 -100, wid, hgt);
        this.layer.popMatrix();
      }

      this.layer.strokeWeight(this.sWeight);
      this.layer.stroke(this.hue,
                        this.sat,
                        this.bri,
                        this.opacity);
      this.layer.pushMatrix();
      this.layer.translate((x1 + wid) / 2 , (y1 + hgt) / 2);
      this.layer.scale(this.scaleValue);
      this.layer.rotate(this.rotateValue);
      this.layer.translate(-((x1 + wid) / 2) , -((y1 + hgt) / 2));
      this.layer.rect(x1 - 20, y1 - 100, wid, hgt);
      this.layer.popMatrix();
    }
    this.layer.endDraw();
  }
}
