class Menu
{
  // Arrays for holding button string names and buttons
  String[] topBarFile;
  String[] topBarEdit;
  String[] topBarFilter;

  Button[] topBarFileBtns;
  Button[] topBarEditBtns;
  Button[] topBarFilterBtns;

  String[] illustratorNames;
  Button[] illustratorMenu;

  String[] photoEditNames;
  Button[] photoEditMenu;

  int btnFontSize = 16, sideMenuInset = 200,
      topBarXStart = 0, topBarYStart = 0, topBarWidth = 100, topBarHeight = 20,
      subXStart = 0, subYStart = 20, subBWidth = 100, subBHeight = 20,
      topBarXIncrease = 60, topBarYIncrease = 20,
      sideMenuXInset = 180, sideMenuColYInset = 20, sideMenuColWidth = 160, sideMenuColHeight = 350,
      sideMenuSelYInset = 390, sideMenuSelWidth = 160, sideMenuSelHeight = 150;

  PFont btnFont;
  //
  Menu()
  {
    // String arrays - first string in each list is the head of the array, this becomes the name
    // shown on the top bar menu, the rest become sub buttons of this name.
    topBarFile = new String[] {"File", "New", "Save", "Load"};
    topBarEdit = new String[] {"Edit", "Undo", "Redo"};
    topBarFilter = new String[] {"Filter", "Blur", "Sharpen", "Greyscale", "Monochrome"};

    illustratorNames = new String[] {"Pencil", "Eraser", "Line", "Rectangle", "Circle", "Polygon", "Duplicate", "ScaleShape", "RotateShape", "ClearLayer"};
    photoEditNames = new String[] {"Resize", "Edge-Detect", "Rotate", "Hue", "Saturation", "Brightness", "Contrast"};
    btnFont = createFont("arial.ttf", 16);

    // Button arrays for top menu
    topBarFileBtns = new Button[topBarFile.length];
    topBarEditBtns = new Button[topBarEdit.length];
    topBarFilterBtns = new Button[topBarFilter.length];

    illustratorMenu = new Button[illustratorNames.length];
    photoEditMenu = new Button[photoEditNames.length];
  }

  void InitialiseMenu()
  {
    MenuButtonsInitialise(topBarFile, topBarFileBtns, topBarXStart, topBarYStart, topBarWidth, topBarHeight);
    MenuButtonsInitialise(topBarEdit, topBarEditBtns, topBarXStart + topBarWidth, topBarYStart, topBarWidth, topBarHeight);
    MenuButtonsInitialise(topBarFilter, topBarFilterBtns, topBarXStart + (topBarWidth * 2), topBarYStart, topBarWidth, topBarHeight);

    int step = 1, startX = width - sideMenuXInset - 5, startY = 550, increaseX = 60, increaseY = 60;
    for (int sideMenuIll = 0; sideMenuIll < illustratorMenu.length; sideMenuIll++)
    {
      illustratorMenu[sideMenuIll] = new Button(startX, startY, 50, 50, false, true, illustratorNames[sideMenuIll], false, true);

      startX += 60;
      step++;
      if (step == 4)
      {
        startX = width - sideMenuXInset - 5;
        startY += 60;
        step = 1;
      }
    }


  }

  void DrawMenu()
  {
    DrawTopBar();
    DrawSideMenu();
  }

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

    TopBarDisplay(topBarFileBtns, topBarEditBtns, topBarFilterBtns);

    for (int sideBarIll = 0; sideBarIll < illustratorMenu.length; sideBarIll++)
    {
      illustratorMenu[sideBarIll].DisplayButton();
    }
  }

  void TopMenuPressed()
  {
    topBarFileBtns[0].SingleButtonPress();
    {
      if (topBarFileBtns[0].localState)
      {
        topBarFileBtns[0].TopMenuButtonPressed(topBarFileBtns);
      }
    }
    topBarFileBtns[0].NotOverButton();

    topBarEditBtns[0].SingleButtonPress();
    {
      if (topBarEditBtns[0].localState)
      {
        topBarEditBtns[0].TopMenuButtonPressed(topBarEditBtns);
      }
    }
    topBarEditBtns[0].NotOverButton();

    topBarFilterBtns[0].SingleButtonPress();
    {
      if (topBarFilterBtns[0].localState)
      {
        topBarFilterBtns[0].TopMenuButtonPressed(topBarFilterBtns);
      }
    }
    topBarFilterBtns[0].NotOverButton();
  }

  void SideMenuPressed()
  {
    illustratorMenu[0].ButtonPressed(illustratorMenu);
  }

  void DrawTopBar()
  {
    noStroke();
    fill(180);
    rect(0, 0, width, topBarHeight);
    textFont(btnFont, btnFontSize);
  }

  void DrawSideMenu()
  {
    noStroke();
    fill(180);
    rect(width - sideMenuInset, 0, sideMenuInset, height);
    textFont(btnFont, btnFontSize);
  }

  void MenuButtonsInitialise(String[] names, Button[] buttons, int tXstart, int tYstart, int tWidth, int tHeight)
  {
    for (int topMenu = 0; topMenu < names.length; topMenu++)
    {
      buttons[topMenu] = new Button(tXstart, tYstart, tWidth, tHeight, false, false, names[topMenu], true, false);
      tYstart += tHeight;
    }
  }

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
