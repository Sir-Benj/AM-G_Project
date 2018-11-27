class Menu
{
  // Arrays for holding button string names and buttons
  String[][] topBarNames;
  Button[][] topBarButtons;
  Button[] topBarSubFirst;
  Button[] topBarSubSecond;
  Button[] topBarSubThird;

  String[] illustratorNames;
  Button[] illustratorMenu;

  String[] photoEditNames;
  Button[] photoEditMenu;

  int btnFontSize = 16, sideMenuInset = 200,
      topBarXStart = 0, topBarYStart = 0, topBarWidth = 60, topBarHeight = 20,
      subXStart = 0, subYStart = 20, subBWidth = 100, subBHeight = 20,
      topBarXIncrease = 60, topBarYIncrease = 20;

  PFont btnFont;
  //
  Menu()
  {
    // String arrays - first string in each list is the head of the array, this becomes the name
    // shown on the top bar menu, the rest become sub buttons of this name.
    topBarNames = new String[][] { {"File", "New", "Save", "Load"}, {"Edit", "Undo", "Redo"}, {"Filter", "Blur", "Sharpen"} };
    illustratorNames = new String[] {"Pencil", "Line", "Rectangle", "Circle", "Polygon"};
    photoEditNames = new String[] {"Resize", "Edge Find"};
    btnFont = createFont("arial.ttf", 16);
    // Button arrays for top menu
    topBarButtons = new Button[topBarNames.length][];
    topBarSubFirst = new Button[topBarNames[0].length];
    topBarSubSecond = new Button[topBarNames[1].length];
    topBarSubThird = new Button[topBarNames[2].length];
    topBarButtons[0] = topBarSubFirst;
    topBarButtons[1] = topBarSubSecond;
    topBarButtons[2] = topBarSubThird;
    // Button arrays for side menu
    illustratorMenu = new Button[illustratorNames.length];
    photoEditMenu = new Button[photoEditNames.length];
  }

  void InitialiseMenu()
  {
    for (int topMenu = 0; topMenu < topBarButtons.length; topMenu++)
    {
      topBarButtons[topMenu][0] = new Button(topBarXStart, topBarYStart, topBarWidth, topBarHeight, false, false, topBarNames[topMenu][0]);
      topBarXStart += topBarXIncrease;

      for (int subMenu = 1; subMenu < topBarButtons[topMenu].length; subMenu++)
      {
        topBarButtons[topMenu][subMenu] = new Button(subXStart, subYStart, subBWidth, subBHeight, false, false, topBarNames[topMenu][subMenu]);
        subYStart += topBarYIncrease;
      }
      subXStart += topBarXIncrease;
      subYStart = topBarYIncrease;
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

    for (int topMenu = 0; topMenu < topBarButtons.length; topMenu++)
    {
      for (int subMenu = 0; subMenu < topBarButtons[topMenu].length; subMenu++)
      {
        topBarButtons[topMenu][0].DisplayButton();
        if (topBarButtons[topMenu][0].localState)
        {
          topBarButtons[topMenu][subMenu].DisplayButton();
        }
      }
    }
  }

  void TopMenuPressed()
  {
    topBarButtons[0][0].TopMenuButtonPressed(topBarButtons[1][0]);
    topBarButtons[0][0].TopMenuButtonPressed(topBarButtons[2][0]);
    topBarButtons[1][0].TopMenuButtonPressed(topBarButtons[0][0]);
    topBarButtons[1][0].TopMenuButtonPressed(topBarButtons[2][0]);
    topBarButtons[2][0].TopMenuButtonPressed(topBarButtons[0][0]);
    topBarButtons[2][0].TopMenuButtonPressed(topBarButtons[1][0]);

    topBarButtons[0][1].TopMenuButtonPressed(topBarButtons[0][0]);
    topBarButtons[0][2].TopMenuButtonPressed(topBarButtons[0][0]);
    topBarButtons[0][3].TopMenuButtonPressed(topBarButtons[0][0]);

    topBarButtons[1][1].TopMenuButtonPressed(topBarButtons[0][1]);
    topBarButtons[1][2].TopMenuButtonPressed(topBarButtons[0][1]);

    topBarButtons[2][1].TopMenuButtonPressed(topBarButtons[0][2]);
    topBarButtons[2][2].TopMenuButtonPressed(topBarButtons[0][2]);
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
}
