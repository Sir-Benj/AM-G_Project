class Rect
{
  float left, top, right, bottom;
  float x1, y1, x2, y2;

  Rect(float xOne, float yOne, float xTwo, float yTwo)
  {
    setRect(xOne, yOne, xTwo, yTwo);
    OriginalMousePos(xOne, yOne, xTwo, yTwo);
  }

  Rect(PVector vecOne, PVector vecTwo)
  {
    setRect(vecOne.x, vecOne.y, vecTwo.x, vecTwo.y);
    OriginalMousePos(vecOne.x, vecOne.y, vecTwo.x, vecTwo.y);
  }

  void OriginalMousePos(float xOne, float yOne, float xTwo, float yTwo)
  {
    this.x1 = xOne;
    this.y1 = yOne;
    this.x2 = xTwo;
    this.y2 = yTwo;
  }

  void setRect(float xOne, float yOne, float xTwo, float yTwo)
  {
    this.left = min(xOne, xTwo);
    this.top = min(yOne, yTwo);
    this.right = max(xOne, xTwo);
    this.bottom = max(yOne, yTwo);
  }

  PVector getCentre()
  {
    PVector centre = new PVector();
    centre.x = (this.right - this.left) / 2.0;
    centre.y = (this.bottom - this.top) / 2.0;
    return centre;
  }

  boolean isInsideThis(PVector vec)
  {
    return (isBetween(vec.x, this.left, this.right) && isBetween(vec.y, this.top, this.bottom));
  }

  float getWidth()
  {
    return (this.right - this.left);
  }

  float getHeight()
  {
    return (this.bottom - this.top);
  }
}

boolean isBetween(float value, float low, float high)
{
  return (value >= low && value <= high);
}
