//ImageUI win;
//PaintUI win2;
TopBarManager topBar;
Button control;
Button[] btns;
Button[][] buttonMenu;

public void settings()
{
  size(1000, 800);
}

void setup()
{
  noSmooth();
  surface.setResizable(true);
  background(255, 255, 255, 255);
  btns = new Button[7];
  btns[0] = new Button(10, 30, 50, 50, 1, true, true, "paint");
  btns[1] = new Button(10, 90, 50, 50, 1, true, true, "erase");
  btns[2] = new Button(10, 150, 50, 50, 1, true, true, "thirdtester");
  btns[3] = new Button(70, 30, 20, 50, 1, false, true, "firstRect");
  btns[4] = new Button(70, 90, 20, 50, 1, true, false, "secondRect");
  btns[5] = new Button(70, 150, 20, 50, 1, true, true, "thirdRect");
  btns[6] = new Button(70, 210, 20, 50, 1, false, false, "forthRect");

  control = new Button(0, 0, 0, 0, 1, false, false, "control");
  topBar = new TopBarManager();
  buttonMenu = topBar.InitialiseMenu();
}

void mousePressed()
{
  topBar.TopMenuPressed();
  control.ButtonPressed(btns);
}

void mouseDragged()
{
  // if (btn1.localState && !btn2.localState)
  // {
  //   noStroke();
  //   fill(0);
  //   ellipse(mouseX, mouseY, 20, 20);
  // }
  // if (btn2.localState && !btn1.localState)
  // {
  //   noStroke();
  //   fill(255);
  //   ellipse(mouseX, mouseY, 20, 20);
  // }
}

void draw()
{
  background(255);

  for (int i = 0; i < btns.length; i++)
  {
    btns[i].DisplayButton();
  }

  topBar.DisplayMenu();
}
