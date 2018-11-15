class TopBarManager
{
  String[][] topBar;
  Button[][] menuButtons;
  PFont font;
  //int topBheight;
  //int topBwidth

  TopBarManager()
  {
    topBar = new String[][] { {"File", "Save", "Load"}, {"Edit", "Undo", "Redo"}, {"Filter", "Blur", "Sharpen"} };
    font = createFont("arial.ttf", 16);

    int maxY = 0;
    for (int i = 0; i < topBar.length; i++)
    {
      maxY = Math.max(maxY, topBar[i].length);
    }
    menuButtons = new Button[topBar.length][maxY];
  }

  Button[][] InitialiseMenu()
  {
    noStroke();
    fill(130);
    rect(0, 0, width, 20);
    textFont(font, 14);

    int topXstart = 0;
    int topYstart = 0;
    int topBSize = 20;
    int subXstart = 0;
    int subYstart = 20;
    int subBSize = 50;

    for (int topMenu = 0; topMenu < menuButtons.length; topMenu++)
    {
      menuButtons[topMenu][0] = new Button(topXstart, topYstart, topBSize, topBar[topMenu][0]);
      //menuButtons[topMenu][0].displayButton();
      topXstart += 20;

      for (int subMenu = 1; subMenu < menuButtons[topMenu].length; subMenu++)
      {
        menuButtons[topMenu][subMenu] = new Button(subXstart, subYstart, subBSize, topBar[topMenu][subMenu]);
        subYstart += 50;
      }
      subXstart += 50;
      subYstart = 20;
    }

    return menuButtons;
  }

  void DisplayMenu()
  {
    for (int i = 0; i < menuButtons.length; i++)
    {
      for (int y = 0; y < menuButtons[i].length; y++)
      {
        menuButtons[i][0].DisplayButton();
        if (menuButtons[i][0].localState)
        {
          menuButtons[i][y].DisplayButton();
        }
      }
    }
  }

  void TopMenuPressed()
  {
    menuButtons[0][0].TopMenuButtonPressed(menuButtons[1][0]);
    menuButtons[0][0].TopMenuButtonPressed(menuButtons[2][0]);
    menuButtons[1][0].TopMenuButtonPressed(menuButtons[0][0]);
    menuButtons[1][0].TopMenuButtonPressed(menuButtons[2][0]);
    menuButtons[2][0].TopMenuButtonPressed(menuButtons[0][0]);
    menuButtons[2][0].TopMenuButtonPressed(menuButtons[1][0]);
  }


}
