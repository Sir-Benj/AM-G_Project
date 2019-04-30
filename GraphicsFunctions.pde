// The GraphicsFunctions class holds all of the methods needed
// to manipulate shapes and images.
class GraphicsFunctions
{
  // Fields
  float prevX, newChangeX, prevY, newChangeY;
  float prevRotation, newRotation;
  float prevScale, newScale;
  float prevValue, newValue;

  // Constructor
  GraphicsFunctions()
  {
  }

  // For clearing the photo layer
  void New(PGraphics layer, Button button)
  {
    layer.clear();
    button.localState = false;
  }

  // For saving a combined image of the layers
  void Save(Button button, File newFile)
  {
    selectOutput("Select Output", "fileSelected", newFile);
    button.localState = false;
  }

  // For loading in a photo
  void Load(Button button, File newFile)
  {
    selectInput("Select An Image To Edit", "fileChosen", newFile);
    button.localState = false;
  }

  // Creating a blurred image
  void Blur(PGraphics photo)
  {
    float[][] blur_matrix = { {0.1,  0.1,  0.1 },
                              {0.1,  0.1,  0.1 },
                              {0.1,  0.1,  0.1 } };

    colorMode(RGB);
    for(int y = 0; y < photo.height; y++)
    {
      for(int x = 0; x < photo.width; x++)
      {
        color c = Convolution(x, y, blur_matrix, 3, photo);
        photo.set(x, y, c);
      }
    }
    colorMode(HSB);
  }

  // Sharpening the image
  void Sharpen(PGraphics photo)
  {
    float[][] sharpen_matrix = { { 0, -1, 0 },
                                 {-1, 5, -1 },
                                 { 0, -1, 0 } };

    colorMode(RGB);
    for(int y = 0; y < photo.height; y++)
    {
      for(int x = 0; x < photo.width; x++)
      {
        color c = Convolution(x, y, sharpen_matrix, 3, photo);
        photo.set(x, y, c);
      }
    }
    colorMode(HSB);
  }

  // Changing an image to greyscale
  void Greyscale(PGraphics photo)
  {
    colorMode(RGB);
    for (int y = 0; y < photo.height; y++)
    {
      for (int x = 0; x < photo.width; x++)
      {

        color thisPix = photo.get(x,y);

        int r = (int) red(thisPix);
        int g = (int) green(thisPix);
        int b = (int) blue(thisPix);
        int grey = (int)((r + g + b) / 3);
        color newColour = color(grey, grey, grey);
        photo.set(x,y, newColour);
      }
    }
    colorMode(HSB);
  }

  // Changing an image to monochrome
  void Monochrome(PGraphics photo)
  {
    colorMode(RGB);
    for (int y = 0; y < photo.height; y++)
    {
      for (int x = 0; x < photo.width; x++)
      {
        color thisPix = photo.get(x,y);

        int r = (int) red(thisPix);
        int g = (int) green(thisPix);
        int b = (int) blue(thisPix);

        if (r > 128|| g > 128 || b > 128)
        {
          r = 255;
          g = 255;
          b = 255;
        }
        else if (r <= 128 || g <= 128 || b <= 128)
        {
          r = 0;
          g = 0;
          b = 0;
        }

        color newColour = color(r, g, b);
        photo.set(x,y, newColour);
      }
    }
    colorMode(HSB);
  }

  // Unused pencil tool
  void Pencil(PGraphics layer, ColourPicker colourPicker, float sVOne, float sVTwo)
  {

    layer.beginDraw();
    layer.colorMode(HSB);
    if (mousePressed)
    {
      if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
      {
        layer.stroke(colourPicker._hueVal, colourPicker._satVal, colourPicker._briVal, sVTwo);
        layer.strokeWeight(sVOne);
        layer.line(mouseX - 20, mouseY - 100, pmouseX - 20, pmouseY - 100);
      }
    }
    layer.endDraw();
  }

  // Unused eraser tool
  void Eraser(PGraphics layer, float sVOne)
  {
    layer.beginDraw();
    layer.colorMode(HSB);
    if (mousePressed)
    {
      if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
      {
        layer.stroke(255);
        layer.strokeWeight(sVOne);
        layer.line(mouseX - 20, mouseY - 100, pmouseX - 20, pmouseY - 100);
      }
    }
    layer.endDraw();
  }

  // Method for starting a shape
  void ShapeStart(String name, PVector mouseStart, PGraphics layer, Document doc,
                      ColourPicker colourPicker, float sWeight, float opacity, boolean filled)
  {
     doc.StartNewShape(name, mouseStart, layer,
                       colourPicker._hueVal,
                       colourPicker._satVal,
                       colourPicker._briVal,
                       sWeight, opacity, filled);
  }

  // Method while drawing the shpe through dragging
  void ShapeDrag(Document doc, PVector mouseDrag)
  {
    if (doc.currentlyDrawnShape == null)
    {
      return;
    }
    doc.currentlyDrawnShape.WhileDrawingShape(mouseDrag);
  }

  // Method for finalising a shape when the drawing is finished
  void ShapeFinal(Document doc, PVector mouseFinal)
  {
    if (doc.currentlyDrawnShape == null)
    {
      return;
    }
    doc.currentlyDrawnShape.FinishDrawingShape(mouseFinal);
    doc.currentlyDrawnShape = null;
  }

  // Changing a shapes colour and strokeweight values
  void ChangeShapeHSB(Document doc, ColourPicker colourPicker, float sWeight, float opacity, boolean filled)
  {
    for (DrawShape s : doc.shapeList)
    {
      if (s.isSelected)
      {
        s.hue = colourPicker._hueVal;
        s.sat = colourPicker._satVal;
        s.bri = colourPicker._briVal;
        s.sWeight = sWeight;
        s.opacity = opacity;
        s.isFilled = filled;
      }
    }
  }

  // Changing shape(s) position through the use of sliders
  void ChangeShapePosition(Document doc, float xPosChange, float yPosChange)
  {
    if (xPosChange == 0)
    {
      newChangeX = 0;
    }

    prevX = newChangeX;
    newChangeX = xPosChange;

    if (yPosChange == 0)
    {
      newChangeY = 0;
    }

    prevY = newChangeY;
    newChangeY = yPosChange;

    if (newChangeX != prevX)
    {
      xPosChange -= prevX;
    }

    if (newChangeY != prevY)
    {
      yPosChange -= prevY;
    }

    for (DrawShape s : doc.shapeList)
    {
      if (s.isSelected)
      {
        if (s.polyPoints == null)
        {
          if (newChangeX != prevX)
          {
            s.bounds.x1 += xPosChange;
            s.bounds.x2 += xPosChange;
            s.bounds.left += xPosChange;
            s.bounds.right += xPosChange;
          }
          if (newChangeY != prevY)
          {
            s.bounds.y1 += yPosChange;
            s.bounds.y2 += yPosChange;
            s.bounds.top += yPosChange;
            s.bounds.bottom += yPosChange;
          }
        }
        else if (s.polyPoints != null)
        {
          for (PVector v : s.polyPoints)
          {
            if (newChangeX != prevX)
            {
              v.x += xPosChange;
            }

            if (newChangeY != prevY)
            {
              v.y += yPosChange;
            }
          }
        }
      }
    }
  }

  // Scaling a shape(s) through the use of a slider
  void ScaleShape(Document doc, float scale)
  {

    for (DrawShape s : doc.shapeList)
    {
      if (s.isSelected)
      {
        s.scaleValue = scale;
      }
    }
  }

  // Rotating a shape(s) through the use of a slider
  void RotateShape(Document doc, float rotate)
  {

    for (DrawShape s : doc.shapeList)
    {
      if (s.isSelected)
      {
        s.rotateValue = radians(rotate);
      }
    }
  }

  // Deleting a selected shape
  void DeleteShape(Document doc)
  {
    Iterator itr = doc.shapeList.iterator();
    while (itr.hasNext())
    {
      DrawShape s = (DrawShape)itr.next();
      if (s.isSelected)
      {
        itr.remove();
      }
    }
  }

  // Clear the shape layer
  void ClearLayer(PGraphics layer, Button button, Document doc)
  {
    layer.clear();
    doc.shapeList = new ArrayList<DrawShape>();
    button.localState = false;
  }

  // Resize an image (bilinear)
  void Resize(PGraphics photo, PImage destination)
  {
    photo.loadPixels();
    destination.loadPixels();

    destination.pixels = ResizeBilinear(photo.pixels, photo.width, photo.height, destination.width, destination.height);
    destination.updatePixels();
  }

  // Edge-Detect method on an image to highlight edges
  void EdgeDetect(PGraphics photo)
  {
    float[][] edge_matrix = { { 0,  -2,  0 },
                              { -2,  8, -2 },
                              { 0,  -2,  0 } };

    colorMode(RGB);
    for(int y = 0; y < photo.height; y++)
    {
      for(int x = 0; x < photo.width; x++)
      {
        color c = Convolution(x, y, edge_matrix, 3, photo);
        photo.set(x, y, c);
      }
    }
    colorMode(HSB);
  }

  // Change the Hue of a selected image
  void Hue(PGraphics photo, float sliderValue, boolean confirm)
  {
    photo.loadPixels();
    int numPixels = photo.width * photo.height;
    for(int n = 0; n < numPixels; n++)
    {
      color c = photo.pixels[n];

      float hue = hue(c);
      float sat = saturation(c);
      float bri = brightness(c);

      float old = hue;
      float change =  old + sliderValue;

      // float difference = change - old;
      // float result = change - (difference * 0.9);

      color newColor = color(change, sat, bri);

      photo.pixels[n] = newColor;
    }

    if (confirm)
    {
      println("Confirmed");
      photo.updatePixels();
    }
  }

  // Change the saturation of a selected image
  void Saturation(PGraphics photo, float sliderValue, boolean confirm)
  {
    photo.loadPixels();
    int numPixels = photo.width * photo.height;
    for(int n = 0; n < numPixels; n++)
    {
      color c = photo.pixels[n];

      float hue = hue(c);
      float sat = saturation(c);
      float bri = brightness(c);

      float change = sat + sliderValue;

      color newColor = color(hue, change, bri);

      photo.pixels[n] = newColor;
    }

    if (confirm)
    {
      println("Confirmed");
      photo.updatePixels();
    }
  }

  void Brightness(PGraphics photo, float sliderValue, boolean confirm)
  {
    photo.loadPixels();
    int numPixels = photo.width * photo.height;
    for(int n = 0; n < numPixels; n++)
    {
      color c = photo.pixels[n];

      float hue = hue(c);
      float sat = saturation(c);
      float bri = brightness(c);

      float change = bri + sliderValue;

      color newColor = color(hue, sat, change);

      photo.pixels[n] = newColor;
    }

    if (confirm)
    {
      println("Confirmed");
      photo.updatePixels();
    }
  }

  void Contrast(PGraphics photo, float contrastValue, boolean confirm)
  {
    colorMode(RGB);
    int[] lut = makeFunctionLUT("ChangeContrast", contrastValue, 0);
    applyPointProcessing(lut, lut, lut, photo, confirm);
    colorMode(HSB);
  }

  // Method for applying point processing operations
  void applyPointProcessing(int[] redLUT, int[] greenLUT, int[] blueLUT, PGraphics inputImage, boolean confirm)
  {
    inputImage.loadPixels();
    int numPixels = inputImage.width*inputImage.height;
    for(int n = 0; n < numPixels; n++)
    {
      color c = inputImage.pixels[n];

      int r = (int)red(c);
      int g = (int)green(c);
      int b = (int)blue(c);

      r = redLUT[r];
      g = greenLUT[g];
      b = blueLUT[b];
      color newColor = color(r, g, b);

      inputImage.pixels[n] = newColor;
    }

    if (confirm)
    {
      println("Confirmed");
      inputImage.updatePixels();
    }
  }

  void applyPointProcessingHSB(int[] hueLUT, int[] satLUT, int[] briLUT, PGraphics inputImage, boolean confirm, String type)
  {
    inputImage.loadPixels();
    int numPixels = inputImage.width*inputImage.height;
    for(int n = 0; n < numPixels; n++)
    {
      color c = inputImage.pixels[n];

      int h = (int)hue(c);
      int s = (int)saturation(c);
      int b = (int)brightness(c);

      switch (type)
      {
        case "Hue" : h = hueLUT[h];
        break;
        case "Sat" : s = satLUT[s];
        break;
        case "Bri" : b = briLUT[b];
        break;
      }

      color newColor = color(h, s, b);

      inputImage.pixels[n] = newColor;
    }

    if (confirm)
    {
      println("Confirmed");
      inputImage.updatePixels();
    }
  }

  // Look up table for point processing
  int[] makeFunctionLUT(String functionName, float parameter1, float parameter2)
  {
    int[] lut = new int[256];
    for(int n = 0; n < 256; n++) {

      float p = n/256.0f;
      float val = 0;

      switch(functionName)
      {
        case "ChangeContrast": val = ChangeContrast(p, parameter1);
        break;
      }
      lut[n] = (int)(val * 255);
    }

    return lut;
  }

  // Point processing Brightness
  float ChangeBrightness(float value, float shift)
  {
    float shiftedValue = value + shift;
    return shiftedValue;
  }

  // Point processing Contrast
  float ChangeContrast(float value, float conVal)
  {
    float upperBound = 255;
    float lowerBound = 0;

    upperBound -= conVal;
    lowerBound += conVal;

    upperBound /= 255;
    lowerBound /= 255;

    if (value >= upperBound)
    {
      value = 1;
    }
    else if (value <= lowerBound)
    {
      value = 0;
    }

    return value;
  }

  // Method for processing convolution filters
  color Convolution(int x, int y, float[][] matrix, int matrixsize, PGraphics photo)
  {
    float rtotal = 0.0;
    float gtotal = 0.0;
    float btotal = 0.0;
    int offset = matrixsize / 2;
    for (int i = 0; i < matrixsize; i++)
    {
      for (int j= 0; j < matrixsize; j++)
      {
        // What pixel are we testing
        int xloc = x+i-offset;
        int yloc = y+j-offset;
        int loc = xloc + photo.width * yloc;
        // Make sure we haven't walked off our image, we could do better here
        loc = constrain(loc, 0, photo.pixels.length-1);
        // Calculate the convolution
        rtotal += (red(photo.pixels[loc]) * matrix[i][j]);
        gtotal += (green(photo.pixels[loc]) * matrix[i][j]);
        btotal += (blue(photo.pixels[loc]) * matrix[i][j]);
      }
    }
    // Make sure RGB is within range
    rtotal = constrain(rtotal, 0, 255);
    gtotal = constrain(gtotal, 0, 255);
    btotal = constrain(btotal, 0, 255);
    // Return the resulting color
    return color(rtotal, gtotal, btotal);
  }

  // Method for the Bilinear resize
  int[] ResizeBilinear(int[] pxls, int startWidth, int startHeight, int targetWidth, int targetHeight)
  {
    int[] temp = new int[targetWidth * targetHeight];
    int a, b, c, d, x, y, index;
    float xRatio = ((float)(startWidth - 1)) / targetWidth;
    float yRatio = ((float)(startHeight - 1)) / targetHeight;
    float xDiff, yDiff, red, green, blue;
    int offset = 0;

    for (int i = 0; i < targetHeight; i++)
    {
      for (int j = 0; j < targetWidth; j++)
      {
        x = (int)(xRatio * j);
        y = (int)(yRatio * i);
        xDiff = (xRatio * j) - x;
        yDiff = (yRatio * i) - y;
        index = (y * startWidth + x);
        a = pxls[index];
        b = pxls[index + 1];
        c = pxls[index + startWidth];
        d = pxls[index + startWidth + 1];

        //blue
        // Yb = Ab(1-w)(1-h) + Bb(w)(1-h) + Cb(h)(1-w) + Db(wh)
        blue = (a&0xff) * (1-xDiff) * (1-yDiff) + (b&0xff) * (xDiff) * (1-yDiff) +
               (c&0xff) * (yDiff) * (1-xDiff) + (d&0xff) * (xDiff * yDiff);

        green = ((a>>8)&0xff) * (1-xDiff) * (1-yDiff) + ((b>>8)&0xff) * (xDiff) * (1-yDiff) +
                ((c>>8)&0xff) * (yDiff) * (1-xDiff) + ((d>>8)&0xff) * (xDiff * yDiff);

        // red element
        // Yr = Ar(1-w)(1-h) + Br(w)(1-h) + Cr(h)(1-w) + Dr(wh)
        red = ((a>>16)&0xff) * (1-xDiff)*(1-yDiff) + ((b>>16)&0xff) * (xDiff) * (1-yDiff) +
              ((c>>16)&0xff) * (yDiff) * (1-xDiff) + ((d>>16)&0xff) * (xDiff * yDiff);

        temp[offset++] = 0xff000000 | // hardcode alpha
                         ((((int)red)<<16)&0xff0000) |
                         ((((int)green)<<8)&0xff00) |
                         ((int)blue);
      }
    }
    return temp;
  }
}
