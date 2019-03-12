class Menu
{
  // Arrays for holding button string names and buttons
  String[] topBarFile;
  String[] topBarFilter;
  String[] topBarPhotoEdit;

  Button[] topBarFileBtns;
  Button[] topBarFilterBtns;
  Button[] topBarPhotoEditBtns;

  Button drawShape;
  Button selectShape;

  String[] illustratorNames;
  Button[] illustratorMenu;

  int btnFontSize = 16, sideMenuInset = 200,
      topBarXStart = 0, topBarYStart = 0, topBarWidth = 100, topBarHeight = 20,
      subXStart = 0, subYStart = 20, subBWidth = 100, subBHeight = 20,
      topBarXIncrease = 60, topBarYIncrease = 20,
      sideMenuXInset = 180, sideMenuColYInset = 20, sideMenuColWidth = 160, sideMenuColHeight = 350,
      sideMenuSelYInset = 390, sideMenuSelWidth = 160, sideMenuSelHeight = 85;

  PFont btnFont;
  //
  Menu()
  {
    topBarFile = new String[] {"File", "New", "Save", "Load"};
    topBarFilter = new String[] {"Filter", "Blur", "Sharpen", "Greyscale", "Monochrome", "Edge-Detect"};
    topBarPhotoEdit = new String[] {"Edit", "Hue", "Saturation", "Brightness", "Contrast"};

    illustratorNames = new String[] {"Pencil", "Eraser", "Line", "Rectangle", "Circle", "Polygon", "Duplicate", "ScaleShape", "RotateShape", "ClearLayer"};
    btnFont = createFont("arial.ttf", 16);

    topBarFileBtns = new Button[topBarFile.length];
    topBarFilterBtns = new Button[topBarFilter.length];
    topBarPhotoEditBtns = new Button[topBarPhotoEdit.length];

    illustratorMenu = new Button[illustratorNames.length];

    drawShape = new Button(width - sideMenuInset + 45, 490, 50, 50, false, true, "drawShape", false, false);
    selectShape = new Button(width - sideMenuInset + 105, 490, 50, 50, false, true, "drawShape", false, false);
  }

  void InitialiseMenu()
  {
    MenuButtonsInitialise(topBarFile, topBarFileBtns, topBarXStart, topBarYStart, topBarWidth, topBarHeight);
    MenuButtonsInitialise(topBarFilter, topBarFilterBtns, topBarXStart + topBarWidth, topBarYStart, topBarWidth, topBarHeight);
    MenuButtonsInitialise(topBarPhotoEdit, topBarPhotoEditBtns, topBarXStart + (topBarWidth * 2), topBarYStart, topBarWidth, topBarHeight);

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

    TopBarDisplay(topBarFileBtns, topBarFilterBtns, topBarPhotoEditBtns);

    for (int sideBarIll = 0; sideBarIll < illustratorMenu.length; sideBarIll++)
    {
      illustratorMenu[sideBarIll].DisplayButton();
    }

    drawShape.DisplayButton();
    selectShape.DisplayButton();
  }

  void TopMenuPressed()
  {
    topBarFileBtns[0].SingleButtonPress();
    {
      if (topBarFileBtns[0].localState)
      {
        topBarFilterBtns[0].localState = false;
        topBarPhotoEditBtns[0].localState = false;
        topBarFileBtns[0].TopMenuButtonPressed(topBarFileBtns);
        for (int menu = 1; menu < topBarFileBtns.length; menu++)
        {
          if (topBarFileBtns[menu].localState)
          {
            for (int illMenu = 0; illMenu < illustratorMenu.length; illMenu++)
            {
              illustratorMenu[illMenu].localState = false;
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
        topBarFilterBtns[0].TopMenuButtonPressed(topBarFilterBtns);
        for (int menu = 1; menu < topBarFilterBtns.length; menu++)
        {
          if (topBarFilterBtns[menu].localState)
          {
            for (int illMenu = 0; illMenu < illustratorMenu.length; illMenu++)
            {
              illustratorMenu[illMenu].localState = false;
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
        topBarPhotoEditBtns[0].TopMenuButtonPressed(topBarPhotoEditBtns);
        for (int menu = 1; menu < topBarPhotoEditBtns.length; menu++)
        {
          if (topBarPhotoEditBtns[menu].localState)
          {
            for (int illMenu = 0; illMenu < illustratorMenu.length; illMenu++)
            {
              illustratorMenu[illMenu].localState = false;
            }
          }
        }
      }
    }

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
