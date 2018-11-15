//ImageUI win;
//PaintUI win2;
TopBarManager topBar;
Button control;
Button[] btns;
Button[][] buttonMenu;

public void settings()
{
  size(600, 600);
}

void setup()
{
  noSmooth();
  surface.setResizable(true);
  background(255, 255, 255, 255);
  btns = new Button[3];
  btns[0] = new Button(10, 30, 50, "paint");
  btns[1] = new Button(10, 90, 50, "erase");
  btns[2] = new Button(10, 150, 50, "smooth");
  control = new Button(0, 0, 0, "control");
  topBar = new TopBarManager();
  buttonMenu = topBar.InitialiseMenu();
}

void mousePressed()
{
  topBar.TopMenuPressed();
  control.ButtonPressed(btns);

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
  topBar.DisplayMenu();
  for (int i = 0; i < btns.length; i++)
  {
    btns[i].DisplayButton();
  }
  // btn1.DisplayButton();
  // btn2.DisplayButton();
  // btn3.DisplayButton();
}
