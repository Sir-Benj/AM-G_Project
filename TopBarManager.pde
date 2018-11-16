class TopBarManager
{
  String[][] topBar;
  Button[][] menuButtons;
  Button[] sub1;
  Button[] sub2;
  Button[] sub3;

  PFont font;
  //int topBheight;
  //int topBwidth

  TopBarManager()
  {
    topBar = new String[][] { {"File", "New", "Save", "Load"}, {"Edit", "Undo", "Redo"}, {"Filter", "Blur", "Sharpen"} };
    font = createFont("arial.ttf", 16);

    menuButtons = new Button[topBar.length][];
    sub1 = new Button[topBar[0].length];
    sub2 = new Button[topBar[1].length];
    sub3 = new Button[topBar[2].length];

    menuButtons[0] = sub1;
    menuButtons[1] = sub2;
    menuButtons[2] = sub3;
  }

  Button[][] InitialiseMenu()
  {
    noStroke();
    fill(180);
    rect(0, 0, width, 20);
    textFont(font, 14);

    int topXstart = 0;
    int topYstart = 0;
    int topBwidth = 50;
    int topBheight = 20;
    int subXstart = 0;
    int subYstart = 20;
    int subBwidth = 80;
    int subBheight = 20;

    for (int topMenu = 0; topMenu < menuButtons.length; topMenu++)
    {
      menuButtons[topMenu][0] = new Button(topXstart, topYstart, topBwidth, topBheight, false, false, topBar[topMenu][0]);
      //menuButtons[topMenu][0].displayButton();
      topXstart += 50;

      for (int subMenu = 1; subMenu < menuButtons[topMenu].length; subMenu++)
      {
        menuButtons[topMenu][subMenu] = new Button(subXstart, subYstart, subBwidth, subBheight, false, false, topBar[topMenu][subMenu]);
        subYstart += 20;
      }
      subXstart += 50;
      subYstart = 20;
    }

    return menuButtons;
  }

  void DisplayMenu()
  {
    noStroke();
    fill(180);
    rect(0, 0, width, 20);
    textFont(font, 14);

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
