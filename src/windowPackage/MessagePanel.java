package windowPackage;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This panel will draw either a reoccurring message for a growing panel, or it will draw a message to the user
 *  with arrows and a small design.
 *
 * @author Jon Wiggins
 * @version 3/5/2017
 */
public class MessagePanel extends JPanel {


    private boolean isGP;


    /**
     * Create a new MessagePanel for a window. Takes a boolean isGrowingPanel: if it is a growing panel,
     *  the message to the user generated will be reoccurring to fit the growing panel, or if it is not
     *  the message drawn will have a more detailed message and have things drawn using primitive shapes.
     *
     * @param isGrowingPanel a boolean to denote if this object should be a growing panel or a message panel
     */
    public MessagePanel(boolean isGrowingPanel) {
        this.isGP = isGrowingPanel;
    }

    /**
     * Paints message for window, uses class variable isGP to decide which message to paint.
     *
     * @param g graphics
     */
    public void paint(Graphics g) {

        //if it is a growing panel, draw the growing panel contents
        if (isGP) {
            //Store the name of the logged in user in a string
            String victimName = System.getProperty("user.name");
            int vNLength = victimName.length(); // store the length of the name to adjust the spacing of the lines
            g.setColor(Color.white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight()); // fill the background with white
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 24)); //Note the Comic Sans use

            //loop for each line that can fit on the screen
            for (int i = 0; i < this.getWidth() + 500; i += 130) {
                for (int j = 0; j < this.getHeight() + 500; j += 30) {
                    //generate random color
                    g.setColor(randomColor());
                    //display message with username
                    g.drawString("CLOSE ME " + victimName + ".", i, j);

                }
                i+=(vNLength * 17); //adjusting the spacing of the messages based on the length of the username
            }
        } else {
            //otherwise draw the message content

            //Fill Space with Gray Background and White card
            g.setColor(Color.gray);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.WHITE);
            g.fillRoundRect(5, 5, 275, 250, 30, 30);


            //Display Message to user

            g.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
            g.setColor(Color.black);
            g.drawString("Go Ahead, " + System.getProperty("user.name"), 35,125); //Note the use of the User's name
            g.drawString("Hit that big ", 35, 155);
            g.setColor(Color.red);
            g.drawString("RED", 180, 155);

            //Draw Rectangle with X in the middle to resemble the close button
            g.fillRoundRect(231,131, 28,28,10,10);
            g.setColor(Color.white);
            g.drawString(" X", 227, 154);

            //Draw Cursor
            g.setColor(Color.BLACK);
            g.drawLine(240,160,240,178);
            g.drawLine(240,160,224,173);
            g.drawLine(224,173,240,178);

            g.drawLine(230,177,225,185);
            g.drawLine(233,177,228,187);
            g.drawLine(225,185,228,187);


            //Drawing First Arrow using lines
            g.drawLine(100, 100, 230, 20);
            g.drawLine(213, 20, 230, 20);
            g.drawLine(230, 35, 230, 20);


            //Drawing second Arrow using lines
            g.drawLine(260, 20, 260, 100);
            g.drawLine(260, 20, 250, 30);
            g.drawLine(260, 20, 270, 30);
        }
    }


    /**
     * Returns a random color.
     *
     * @return a random color
     */
    public Color randomColor() {

        //RBG Values for color objects are between 0 and 255,
        // so to generate a random color, make a color with 3 random values between
        // 0 and 255.
        int min = 0;
        int max = 255;
        return new Color(ThreadLocalRandom.current().nextInt(min, max + 1),
                ThreadLocalRandom.current().nextInt(min, max + 1),
                ThreadLocalRandom.current().nextInt(min, max + 1));
    }
}
