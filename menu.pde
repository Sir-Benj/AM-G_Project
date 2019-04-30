class Menu
{
  // Arrays for holding buttons for the top bars
  String[] topBarFile;
  String[] topBarFilter;
  String[] topBarPhotoEdit;

  Button[] topBarFileBtns;
  Button[] topBarFilterBtns;
  Button[] topBarPhotoEditBtns;

  // Individual buttons
  Button drawShape;
  Button selectShape;
  Button filledShape;

  // String arrays for shape names
  String[] drawShapeNames;
  String[] selectShapeNames;

  // Button arrays for the side menus
  Button[] drawShapeMenu;
  Button[] selectShapeMenu;

  Button[] shapeMenuBtns;

  int btnFontSize = 16, sideMenuInset = 200,
      topBarXStart = 0, topBarYStart = 0, topBarWidth = 100, topBarHeight = 20,
      subXStart = 0, subYStart = 20, subBWidth = 100, subBHeight = 20,
      topBarXIncrease = 60, topBarYIncrease = 20,
      sideMenuXInset = 180, sideMenuColYInset = 20, sideMenuColWidth = 160, sideMenuColHeight = 350,
      sideMenuSelYInset = 390, sideMenuSelWidth = 160, sideMenuSelHeight = 140;

  PFont btnFont;

  Menu()
  {
    // Setting up button names, adding names creates more buttons
    topBarFile = new String[] {"File", "New", "Save", "Load"};
    topBarFilter = new String[] {"Filter", "Blur", "Sharpen", "Greyscale", "Monochrome", "Edge-Detect"};
    topBarPhotoEdit = new String[] {"Edit", "Resize", "Hue", "Saturation", "Brightness", "Contrast"};

    drawShapeNames = new String[] {"Line", "Curve", "Rectangle", "Circle", "Polygon", "ClearLayer"};
    selectShapeNames = new String[] {"ChangeColour", "ChangePosition", "ScaleShape", "RotateShape", "DeleteShape"};

    btnFont = createFont("arial.ttf", 16);

    topBarFileBtns = new Button[topBarFile.length];
    topBarFilterBtns = new Button[topBarFilter.length];
    topBarPhotoEditBtns = new Button[topBarPhotoEdit.length];

    drawShapeMenu = new Button[drawShapeNames.length];
    selectShapeMenu = new Button[selectShapeNames.length];

    shapeMenuBtns = new Button[2];

    drawShape = new Button(width - sideMenuInset + 45, 540, 50, 50, false, true, "DrawShape", false, true);
    selectShape = new Button(width - sideMenuInset + 105, 540, 50, 50, false, true, "SelectShape", false, true);
    filledShape = new Button(width - sideMenuInset + 80, 480, 40, 40, false, true, "FilledShape", false, true);


  }

  // Creates the menus
  void InitialiseMenu()
  {
    MenuButtonsInitialise(topBarFile, topBarFileBtns, topBarXStart, topBarYStart, topBarWidth, topBarHeight);
    MenuButtonsInitialise(topBarFilter, topBarFilterBtns, topBarXStart + topBarWidth, topBarYStart, topBarWidth, topBarHeight);
    MenuButtonsInitialise(topBarPhotoEdit, topBarPhotoEditBtns, topBarXStart + (topBarWidth * 2), topBarYStart, topBarWidth, topBarHeight);

    int step = 1, startX = width - sideMenuXInset - 5, startY = 600, increaseX = 60, increaseY = 60;
    for (int sideMenuIll = 0; sideMenuIll < drawShapeMenu.length; sideMenuIll++)
    {
      drawShapeMenu[sideMenuIll] = new Button(startX, startY, 50, 50, false, true, drawShapeNames[sideMenuIll], false, true);

      startX += 60;
      step++;
      if (step == 4)
      {
        startX = width - sideMenuXInset - 5;
        startY += 60;
        step = 1;
      }
    }

    step = 1; startX = width - sideMenuXInset - 5; startY = 600; increaseX = 60; increaseY = 60;
    for (int sideMenuIll = 0; sideMenuIll < selectShapeMenu.length; sideMenuIll++)
    {
      selectShapeMenu[sideMenuIll] = new Button(startX, startY, 50, 50, false, true, selectShapeNames[sideMenuIll], false, true);

      startX += 60;
      step++;
      if (step == 4)
      {
        startX = width - sideMenuXInset - 5;
        startY += 60;
        step = 1;
      }
    }

    shapeMenuBtns[0] = drawShape;
    shapeMenuBtns[1] = selectShape;
  }

  void DrawMenu()
  {
    DrawTopBar();
    DrawSideMenu();
  }

  // Displays menus to the screen
  void DisplayMenu()
  {
    noStroke();
    fill(180);
    rect(0, 0, width, topBarHeight);
    textFont(btnFont, btnFontSize);

    stroke(150);
    fill(160);
    rect(width - sideMenuXInset, sideMenuColYInset, sideMenuColWidth, sideMenuColHeight);

    stroke(150);
    fill(160);
    rect(width - sideMenuXInset, sideMenuSelYInset, sideMenuSelWidth, sideMenuSelHeight);

    TopBarDisplay(topBarFileBtns, topBarFilterBtns, topBarPhotoEditBtns);

    if (drawShape.localState)
    {
      for (int sideBarIll = 0; sideBarIll < drawShapeMenu.length; sideBarIll++)
      {
        drawShapeMenu[sideBarIll].DisplayButton();
      }
      filledShape.DisplayButton();
    }

    if (selectShape.localState)
    {
      for (int sideBarIll = 0; sideBarIll < selectShapeMenu.length; sideBarIll++)
      {
        selectShapeMenu[sideBarIll].DisplayButton();
      }
      filledShape.DisplayButton();
    }

    drawShape.DisplayButton();
    selectShape.DisplayButton();
  }

  // Check if the top menu is pressed and react accordingly
  void TopMenuPressed()
  {
    topBarFileBtns[0].SingleButtonPress();
    {
      if (topBarFileBtns[0].localState)
      {
        topBarFilterBtns[0].localState = false;
        topBarPhotoEditBtns[0].localState = false;

        for (int menu = 1; menu < topBarFilterBtns.length; menu++)
        {
          topBarFilterBtns[menu].localState = false;
        }
        for (int menu = 1; menu < topBarPhotoEditBtns.length; menu++)
        {
          topBarPhotoEditBtns[menu].localState = false;
        }

        topBarFileBtns[0].TopMenuButtonPressed(topBarFileBtns);
        for (int menu = 1; menu < topBarFileBtns.length; menu++)
        {
          if (topBarFileBtns[menu].localState)
          {
            for (int illMenu = 0; illMenu < drawShapeMenu.length; illMenu++)
            {
              drawShapeMenu[illMenu].localState = false;
            }
            for (int illMenu = 0; illMenu < selectShapeMenu.length; illMenu++)
            {
              selectShapeMenu[illMenu].localState = false;
            }
          }
        }
      }
    }

    topBarFilterBtns[0].SingleButtonPress();
    {
      if (topBarFilterBtns[0].localState)
      {
        topBarFileBtns[0].localState = false;
        topBarPhotoEditBtns[0].localState = false;

        for (int menu = 1; menu < topBarFileBtns.length; menu++)
        {
          topBarFileBtns[menu].localState = false;
        }
        for (int menu = 1; menu < topBarPhotoEditBtns.length; menu++)
        {
          topBarPhotoEditBtns[menu].localState = false;
        }

        topBarFilterBtns[0].TopMenuButtonPressed(topBarFilterBtns);
        for (int menu = 1; menu < topBarFilterBtns.length; menu++)
        {
          if (topBarFilterBtns[menu].localState)
          {
            for (int illMenu = 0; illMenu < drawShapeMenu.length; illMenu++)
            {
              drawShapeMenu[illMenu].localState = false;
            }
            for (int illMenu = 0; illMenu < selectShapeMenu.length; illMenu++)
            {
              selectShapeMenu[illMenu].localState = false;
            }
          }
        }
      }
    }

    topBarPhotoEditBtns[0].SingleButtonPress();
    {
      if (topBarPhotoEditBtns[0].localState)
      {
        topBarFileBtns[0].localState = false;
        topBarFilterBtns[0].localState = false;

        for (int menu = 1; menu < topBarFileBtns.length; menu++)
        {
          topBarFileBtns[menu].localState = false;
        }
        for (int menu = 1; menu < topBarFilterBtns.length; menu++)
        {
          topBarFilterBtns[menu].localState = false;
        }

        topBarPhotoEditBtns[0].TopMenuButtonPressed(topBarPhotoEditBtns);
        for (int menu = 1; menu < topBarPhotoEditBtns.length; menu++)
        {
          if (topBarPhotoEditBtns[menu].localState)
          {
            for (int illMenu = 0; illMenu < drawShapeMenu.length; illMenu++)
            {
              drawShapeMenu[illMenu].localState = false;
            }
            for (int illMenu = 0; illMenu < selectShapeMenu.length; illMenu++)
            {
              selectShapeMenu[illMenu].localState = false;
            }
          }
        }
      }
    }

    shapeMenuBtns[0].ButtonPressed(shapeMenuBtns);
  }

  // Check if side menu is pressed and act accordingly
  void SideMenuPressed()
  {
    if (drawShape.localState)
    {
      drawShapeMenu[0].ButtonPressed(drawShapeMenu);
      filledShape.SingleButtonPress();
      for (Button b : selectShapeMenu)
      {
        b.localState = false;
      }
    }

    if (selectShape.localState)
    {
      selectShapeMenu[0].ButtonPressed(selectShapeMenu);
      filledShape.SingleButtonPress();
      for (Button b : drawShapeMenu)
      {
        b.localState = false;
      }
    }
  }

  // Draws the top bar background rectangle
  void DrawTopBar()
  {
    noStroke();
    fill(180);
    rect(0, 0, width, topBarHeight);
    textFont(btnFont, btnFontSize);
  }

  // Draw the side menu background rectangle
  void DrawSideMenu()
  {
    noStroke();
    fill(180);
    rect(width - sideMenuInset, 0, sideMenuInset, height);
    textFont(btnFont, btnFontSize);
  }

  // Used by the main InitialiseMenu method to initialise the top bar menu
  void MenuButtonsInitialise(String[] names, Button[] buttons, int tXstart, int tYstart, int tWidth, int tHeight)
  {
    for (int topMenu = 0; topMenu < names.length; topMenu++)
    {
      if (topMenu == 0)
      {
        buttons[topMenu] = new Button(tXstart, tYstart, tWidth, tHeight, false, false, names[topMenu], true, false);
        tYstart += tHeight;
        tXstart = 0;
        tHeight += 60;
      }
      else
      {
        buttons[topMenu] = new Button(tXstart, tYstart, tWidth, tHeight, false, false, names[topMenu], false, true);
        tXstart += tWidth;
      }
    }
  }

  // Displays the top bar
  void TopBarDisplay(Button[] topBarBtns1, Button[] topBarBtns2, Button[] topBarBtns3)
  {
    for (int topMenu = 0; topMenu < topBarBtns1.length; topMenu++)
    {
      topBarBtns1[0].DisplayButton();
      if (topBarBtns1[0].localState && !topBarBtns2[0].localState && !topBarBtns3[0].localState)
      {
        topBarBtns1[topMenu].DisplayButton();
      }
    }
    for (int topMenu = 0; topMenu < topBarBtns2.length; topMenu++)
    {
      topBarBtns2[0].DisplayButton();
      if (!topBarBtns1[0].localState && topBarBtns2[0].localState && !topBarBtns3[0].localState)
      {
        topBarBtns2[topMenu].DisplayButton();
      }
    }
    for (int topMenu = 0; topMenu < topBarBtns3.length; topMenu++)
    {
      topBarBtns3[0].DisplayButton();
      if (!topBarBtns1[0].localState && !topBarBtns2[0].localState && topBarBtns3[0].localState)
      {
        topBarBtns3[topMenu].DisplayButton();
      }
    }
  }
}
