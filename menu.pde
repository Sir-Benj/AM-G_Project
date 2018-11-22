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
      topBarXStart = 0, topBarYStart = 0, topBarWidth = 50, topBarHeight = 20,
      subXStart = 0, subYStart = 20, subBWidth = 100, subBHeight = 20,
      topBarXIncrease = 50, topBarYIncrease = 20;

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

  }

  void DrawMenu()
  {
    DrawTopBar();
    DrawSideMenu();
  }

  void DrawTopBar()
  {
    noStroke();
    fill(200);
    rect(0, 0, width, topBarHeight);
    textFont(btnFont, btnFontSize);
  }

  void DrawSideMenu()
  {
    noStroke();
    fill(200);
    rect(width - sideMenuInset, 0, sideMenuInset, height);
    textFont(btnFont, btnFontSize);
  }
}
