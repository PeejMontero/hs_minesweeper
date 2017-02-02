//Philippe J Sitthideth-Montero
//Jan 17, 2012
//This is Minesweeper

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class Minesweeper extends Applet implements MouseListener, MouseMotionListener
{

    /*Data Dictionary
    Variables: 20
    INT
    mx: The x-coordinate of the mouse
    my: The y-coordinate of the mouse

    modX: Modulo function affecting x-coordinate of mouse
    modY: Modulo function affecting y-coordinate of mouse
    These modulo functions allow a click within each box to correspond with the box as a whole
    By dividing the coordinates of the box by 25 (the size of each box), the program finds the remainder
    Subtracting the remainder gives the program the coordinates of the top right corner of the box

    xCoord: The x-coordinate of the top left of the clicked box, determined using variable modX
    YCoord: The Y-coordinate of the top left of the clicked box, determined using variable modY

    xClick + yClick: Used in the algorithm which determines if the clicked box contains a bomb

    clicks: the number of clicks

    statusPrint: The y-coordinate of the status

    numberBombs: The number of bombs found in the game
    spaces: the number of spaces left

    surround: used in the function "bombCounter" which determines the amount of bombs surrounding the selected box

    darkness: used to determine the darkness of the String printed to the screen

    INT (Arrays)
    xBomb, yBomb: The x-coordinate and y-coordinate of each bomb, determined randomly for each game
    clicked: the coordinates of each click

    FONT
    bombFont: The font of the String printed to the screen upon clicking a box

    BOOLEAN
    isButtonPressed: Becomes true only when clicking down the mouse
    alive: Determines if the user is still "alive" in the game or not
    win: is only true if user has won the game
    clear: is only true upon user click if the clicked space has not been selected before
    */

    //Variables
    int mx, my;
    int modX, modY;
    int xCoord, yCoord;
    int xClick, yClick;

    int clicks = 0;

    int numberBombs = 30;
    int spaces = 224;

    int surround = 0;

    int darkness = 0;

    int xBomb[] = new int [numberBombs];
    int yBomb[] = new int [numberBombs];
    int clicked[] [] = new int [2] [999];

    Font bombFont = new Font ("TimesRoman", Font.BOLD, 20);

    String surText;

    boolean isButtonPressed = false;
    boolean alive = true;
    boolean win = false;
    boolean clear = false;

    //The initializer
    public void init ()
    {
	//Sets the size of the applet
	setSize (500, 500);

	//Allows use of mouse
	addMouseListener (this);
	addMouseMotionListener (this);

	//Sets the coordinates of each bomb
	for (int i = 0 ; i < xBomb.length ; i++)
	{
	    xBomb [i] = (int) (Math.random () * 16) + 1;
	    yBomb [i] = (int) (Math.random () * 14) + 1;
	    for (int j = 0 ; j < i ; j++)
	    {
		if ((xBomb [i] == xBomb [j]) && (yBomb [i] == yBomb [j]))
		{
		    i--;
		}
	    } //End for

	} //End for

    } //End initializer


    //This function counts the number of surrounding bombs for the coordinates of the click
    //int xClick: The x Coordinate, in accordance to the algorithm, for the mouse click
    //int yClick: The y Coordinate, in accordance to the algorithm, for the mouse click
    //int xBomb[], yBomb[]: The coordinates of the bombs, in the array
    public static int bombCounter (int xClick, int yClick, int xBomb[], int yBomb[])
    {
	//Sets the number of surrounding bombs at 0
	int surBombs = 0;

	//Repeats this for however many bombs there are. For every bomb, the variable surBombs increases.
	for (int i = 0 ; i < xBomb.length ; i++)
	{
	    //Top Left
	    if ((xClick - 1 == xBomb [i]) && (yClick - 1 == yBomb [i]))
	    {
		surBombs++;
	    }
	    //Top
	    if ((xClick == xBomb [i]) && (yClick - 1 == yBomb [i]))
	    {
		surBombs++;
	    }
	    //Top Right
	    if ((xClick + 1 == xBomb [i]) && (yClick - 1 == yBomb [i]))
	    {
		surBombs++;
	    }
	    //Middle Left
	    if ((xClick - 1 == xBomb [i]) && (yClick == yBomb [i]))
	    {
		surBombs++;
	    }
	    //Middle Right
	    if ((xClick + 1 == xBomb [i]) && (yClick == yBomb [i]))
	    {
		surBombs++;
	    }
	    //Bottom Left
	    if ((xClick - 1 == xBomb [i]) && (yClick + 1 == yBomb [i]))
	    {
		surBombs++;
	    }
	    //Bottom
	    if ((xClick == xBomb [i]) && (yClick + 1 == yBomb [i]))
	    {
		surBombs++;
	    }
	    //Bottom Right
	    if ((xClick + 1 == xBomb [i]) && (yClick + 1 == yBomb [i]))
	    {
		surBombs++;
	    }


	} //End for

	//Return the number of surrounding bombs to the paint method
	return surBombs;
    } //End Function bombCounter


    public void mouseEntered (MouseEvent e)
    {
	//When mouse is within the applet
    }


    public void mouseExited (MouseEvent e)
    {
	//When mouse is away from applet
    }


    public void mouseClicked (MouseEvent e)
    {
	//When button is pressed and released
    }


    public void mousePressed (MouseEvent e)
    {
	//When button is pressed

	//The modulo is used to find the coordinates of the top right corner of the selected box
	modX = mx % 25;
	modY = my % 25;

	//The remainder is subtracted from the original coordinate
	xCoord = mx - modX;
	yCoord = my - modY;

	//Repaints only the clicked area
	repaint (xCoord, yCoord, 25, 25);

	//Sets boolean isButtonPressed to true
	isButtonPressed = true;

    }


    public void mouseReleased (MouseEvent e)
    {
	//When mouse button is released
	//Sets boolean isButtonPressed to false
	isButtonPressed = false;

	//Increases number of clicks
	clicks++;

    } //End method mouseReleased


    public void mouseMoved (MouseEvent e)
    {
	//When mouse is being moved

	//Recieves coordinates for mouse
	mx = e.getX ();
	my = e.getY ();

	//Displays text at the bottom of applet according to user status
	//Notifys user if they have won
	if (win)
	{
	    showStatus ("You have won! Please press reset.");
	}
	//If game has not been won
	else
	{
	    //If user is still alive. Shows mouse status
	    if (alive)
	    {
		showStatus ("Mouse at (" + mx + "," + my + ")");
	    }
	    //If user has died, therefore losing
	    else
	    {
		showStatus ("You are dead. Please press reset.");
	    }
	}
    } //End method mouseMoved


    public void mouseDragged (MouseEvent e)
    {
	//When mouse is being moved while button is down

	//Recieves coordinates for mouse
	mx = e.getX ();
	my = e.getY ();

	//Displays text at the bottom of applet according to user status
	//Notifys user if they have won
	if (win)
	{
	    showStatus ("You have won! Please press reset.");
	}
	//If game has not been won
	else
	{
	    //If user is still alive. Shows mouse status
	    if (alive)
	    {
		showStatus ("Mouse at (" + mx + "," + my + ")");
	    }
	    //If user has died, therefore losing
	    else
	    {
		showStatus ("You are dead. Please press reset.");
	    }
	}
    } //End method mouseDragged


    public void paint (Graphics g)
    {
	//Initial text
	g.drawString ("Minesweeper", 200, 35);

	//Graphic for reset button
	g.drawString ("Reset", 58, 67);
	g.drawRect (50, 50, 50, 25);

	//Displays the number of bombs in the field
	g.drawString ("Number of Bombs:" + numberBombs, 330, 67);

	//Sets the font to bold for user clicks
	g.setFont (bombFont);

	//This draws the happyface at the top

	//Surrounding rectangle
	g.drawRect (225, 45, 25, 25);

	//Happy face
	g.setColor (Color.yellow);
	g.fillOval (227, 47, 21, 21);
	g.setColor (Color.black);
	g.drawOval (227, 47, 21, 21);
	g.fillOval (233, 53, 3, 3);
	g.fillOval (240, 53, 3, 3);
	g.drawArc (233, 56, 10, 8, 180, 180);

	//These three algorithms draw the grid. The third fills the grid with boxes.
	for (int i = 50 ; i <= 450 ; i = i + 25)
	{
	    //Vertical lines
	    g.drawLine (i, 100, i, 450);

	} //End for

	for (int j = 100 ; j <= 450 ; j = j + 25)
	{
	    //Horizontal lines
	    g.drawLine (50, j, 450, j);
	} //End for

	g.setColor (Color.gray);

	for (int i = 50 ; i < 450 ; i = i + 25)
	{
	    for (int j = 100 ; j < 450 ; j = j + 25)
	    {
		g.fillRect (i + 2, j + 2, 22, 22);
	    } //End for j

	} //End fori

	//If user input is within the coordinates of the grid, and the user is still alive
	if ((mx >= 50) && (mx <= 450) && (my >= 100) && (my <= 450) && (isButtonPressed) && (alive))
	{
	    //The modulo is used to find the coordinates of the top right corner of the selected box
	    modX = mx % 25;
	    modY = my % 25;

	    //The remainder is subtracted from the original coordinate
	    xCoord = mx - modX;
	    yCoord = my - modY;

	    //This is the algorithm for the location of the click in accordance with the bomb
	    xClick = ((xCoord - 50) / 25) + 1;
	    yClick = ((yCoord - 100)) / 25 + 1;

	    //Places the clicked coordinates into the array.
	    clicked [0] [clicks] = xClick;
	    clicked [1] [clicks] = yClick;

	    /*
	    Gives the coordinates of the bombs and user input to the function named bombCounter
	    This function counts the number of bombs surrounding the selected box
	    */
	    surround = bombCounter (xClick, yClick, xBomb, yBomb);
	    surText = Integer.toString (surround);

	    /*
	    Determines the darkness of the font
	    Colours are according to how many bombs surround
	    */
	    darkness = ((9 - surround) * 25) + 25;
	    Color dark = new Color (darkness, 0, 0);

	    //If the coordinates of the click matches the coordinates of a bomb, the user is killed
	    for (int i = 0 ; i < xBomb.length ; i++)
	    {

		if ((xClick == xBomb [i]) && (yClick == yBomb [i]))
		{
		    alive = false;
		}

	    } //End for

	    //User did not select a bomb
	    if (alive)
	    {
		//Opens box, displays number of surrounding bombs

		g.setColor (Color.white);
		g.fillRect (xCoord + 2, yCoord + 2, 22, 22);

		g.setColor (dark);
		g.drawString (surText, xCoord + 7, yCoord + 19);

		for (int i = 0 ; i < clicks ; i++)
		{
		    //If coordinates of click have not already been selected
		    if ((clicked [0] [i] != xClick) && (clicked [1] [i] != yClick))
		    {
			clear = true;
		    }
		} //End for

		//If this is true, subtracts 1 from the total amount of remaining spaces, then resets boolean
		if (clear)
		{
		    spaces--;
		    clear = false;

		    //If all spaces are clear, the game is won
		    if (spaces == numberBombs - 1)
		    {
			win = true;
		    }
		}
	    } //End if alive

	    //If user selected a bomb and died
	    else
	    {
		//User is dead, draws a bomb in the location of death

		g.setColor (Color.red);
		g.fillRect (xCoord, yCoord, 25, 25);

		g.setColor (Color.black);
		g.fillOval (xCoord + 2, yCoord + 2, 22, 22);
	    }


	} //End if grid within coordinates

	//If user input is within the location of the reset button
	else if ((mx >= 50) && (mx <= 100) && (my >= 50) && (my <= 75) && (isButtonPressed))
	{
	    /*Reset Game
	    Repaints screen
	    Resets number of unclicked spaces
	    Sets boolean win to false
	    Sets boolean alive to true
	    Reassigns bomb coordinates
	    Resets status of clear boolean
	    */

	    repaint ();
	    spaces = 224;
	    win = false;
	    alive = true;
	    clear = false;

	    for (int i = 0 ; i < xBomb.length ; i++)
	    {
		xBomb [i] = (int) (Math.random () * 16) + 1;
		yBomb [i] = (int) (Math.random () * 14) + 1;
		for (int j = 0 ; j < i ; j++)
		{
		    if ((xBomb [i] == xBomb [j]) && (yBomb [i] == yBomb [j]))
		    {
			i--;
		    }
		} //End for

	    } //End for
	}
    } //End method paint


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);

        final Applet applet = new Minesweeper();

        frame.getContentPane().add(applet);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                applet.stop();
                applet.destroy();
                System.exit(0);
            }
    });

        frame.setVisible(true);
        applet.init();
        applet.start();
    } 

} // End Minesweeper

//End Program





