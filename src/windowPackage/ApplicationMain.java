package windowPackage;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This program is designed to troll whoever runs it (presumably Ryan) by quickly opening and closing many windows,
 *  which do not terminate when a single window is closed. To end the program once it has been run, a person would
 *  need to use a system tool to manually end the task.
 *
 * Ideally this program would be exported into a .jar file and given a seemingly innocuous name, such as
 *  Minecraft.jar, to entice an unknowing victim to run the program. Or just added to list of things to
 *  execute on system startup.
 *
 *
 * @author Jon Wiggins
 * @version 3/4/2017
 */
public class ApplicationMain implements Runnable {

    public static void main(String[] args) {
        //Create a new ApplicationMain runnable
        ApplicationMain m = new ApplicationMain();
        //Run the runnable
        SwingUtilities.invokeLater(m);

        //Create a new timer that will go off every second
        // This creates a new window every second, this creates a steady flow
        // of windows, as each window closes itself after 7 seconds.
        CloseAttemptListener closeListener = new CloseAttemptListener();
        new Timer(1000, closeListener).start();
    }

    /**
     * This runnable will randomly create and display a window from one of three types:
     *  - A Message Window which taunts the user to attempt to close the window, even though it is futile.
     *  - A Moving Window which floats across the screen, and bounces off of the corners.
     *  - A Growing Window which displays a message and slowly grows in size until it reaches the edge of the display
     * All of these windows will close automatically, 7 seconds after being opened.
     */
    @Override
    public void run() {
        //Create Windows

        //Generate Random number between 1 and number of windows
        int numberOfWindowOptions = 3;
        int randomNum = ThreadLocalRandom.current().nextInt(1, numberOfWindowOptions + 1);

        //Use the random number pick a window type
        switch (randomNum) {
            case 1:
                createMovingFrame();
                break;
            case 2:
                createGrowingFrame();
                break;
            case 3:
                createMessageFrame();
                break;
        }
    }


    /**
     * Create and display a new frame, then start the background process to move the frame.
     */
    private void createMovingFrame() {


        //create new JFrame
        JFrame window = new JFrame("Close Me");

        //Set the window size and do not let the user change it
        window.setSize(300, 300);
        window.setResizable(false);

        //Set the content of the panel to be a button
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        JButton exitButton = new JButton("Click here " + System.getProperty("user.name")); //Not the use of the username
        window.setContentPane(content);
        content.add(exitButton);

        //create new close listener
        CloseAttemptListener closeListener = new CloseAttemptListener();
        exitButton.addActionListener(closeListener);

        //add a WindowListener to the window -> call Close Attempt Listener each time the user tries to exit
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //Call action listener, using dummy event ID and Command
                closeListener.actionPerformed(new ActionEvent(closeListener, 1234, "lmao"));
            }
        });

        //Get the screen width and height
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();

        //Set the initial position of the window to a random location on the screen

        Random rand = new Random();
        //random starting point for x
        int randomX = rand.nextInt((screenWidth - 300) + 1);
        //random starting point for y
        int randomY = rand.nextInt((screenHeight - 300) + 1);
        window.setLocation(randomX, randomY);
        window.revalidate();


        //Create a new Swing Worker to move the Frame
        // This allows the window moving process to run in the background
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                //Call the moveWindow method, giving it the window, the current x and y location, and the screen size
                moveWindow(window, randomX, randomY, screenWidth, screenHeight);
                return null;
            }
        };


        //Rune the worker to begin moving the window
        worker.execute();
        //show the window for the user
        window.setVisible(true);
        //move the window the the front
        window.toFront();

        //create a new timer to remove the window after 7 seconds
        new Timer(7000, e -> window.dispose()).start();

    }


    /**
     * Designed to be run as a background process, this method takes in a create Jframe, and some information about it,
     *  and moves the frame across the screen, bouncing it off of the edges, for 7.25 seconds, and then stops.
     *
     * @param window a JFrame Window to be moved across the screen
     * @param randomX the initial x location of the window
     * @param randomY the initial y location of the window
     * @param screenWidth the width of the display
     * @param screenHeight the height of the display
     */
    public void moveWindow(JFrame window, int randomX, int randomY, int screenWidth, int screenHeight) {
        //speed of the windows movement
        int dy = 2;
        int dx = 2;

        //keep track of the current position of the window
        int currentX = randomX;
        int currentY = randomY;

        //store the current system time
        long startTime = System.currentTimeMillis();

        //continually move the frame across the screen for 7.25 seconds
        while ((System.currentTimeMillis() - startTime) < 7250) {

            //update the position every 10ms so that it does not move too quickly
            if (System.currentTimeMillis() % 10 == 0) {
                //if x position puts the window off of the screen, change the sign of the change in dx
                if (currentX + 300 >= screenWidth || currentX < 0) {
                    dx *= -1;
                }
                //if y position puts the window off of the screen, change the sign of the change in dy
                if (currentY + 300 >= screenHeight || currentY < 0) {
                    dy *= -1;
                }

                //update the position of the window
                currentX += dx;
                currentY += dy;
                window.setLocation(currentX, currentY);

                //redraw the window
                window.revalidate();
            }
        }
    }

    /**
     * Create and display a new frame with a message on it to the user.
     */
    private void createMessageFrame() {

        //create a new JFrame
        JFrame window = new JFrame("Close Me");

        //Create a new Message Panel
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        MessagePanel centerPanel = new MessagePanel(false); //create a new message panel, note that it is not a growing panel
        content.add(centerPanel, BorderLayout.CENTER); // add it to the window

        //Get the size of the display
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();

        //Set the initial position of the window to be a random
        Random rand = new Random();
        //random starting point for x
        int randomX = rand.nextInt((screenWidth - 300) + 1);
        //random starting point for y
        int randomY = rand.nextInt((screenHeight - 300) + 1);
        window.setLocation(randomX, randomY);

        //create new action listener
        CloseAttemptListener closeListener = new CloseAttemptListener();
        //add a WindowListener to the window -> call Close Attempt Listener each time the user tries to exit
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //Call action listener, using dummy event ID and Command
                closeListener.actionPerformed(new ActionEvent(closeListener, 1234, "lmao"));
            }
        });


        //display the window
        window.setContentPane(content);
        window.setSize(300, 300);
        window.setVisible(true);

        //create a new timer to remove the window after 7 seconds
        new Timer(7000, e -> window.dispose()).start();

    }

    /**
     * Create and display a new frame, then start the background process to grow the frame.
     */
    private void createGrowingFrame() {


        //Create new JFrame
        JFrame window = new JFrame("Close Me");

        //Set the initial size to 300x300 and do not let the user change it
        window.setSize(300, 300);
        window.setResizable(false);

        //create a new JPanel and add the message to it
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        MessagePanel centerPanel = new MessagePanel(true); //Create new Message Panel and note that it is a growing panel
        content.add(centerPanel, BorderLayout.CENTER); //add the message to the window


        //Get the size of the screen
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();


        //Set the Location of the window to be a random location on the screen
        Random rand = new Random();
        //random starting point for x
        int randomX = rand.nextInt((screenWidth - 300) + 1);
        //random starting point for y
        int randomY = rand.nextInt((screenHeight - 300) + 1);
        window.setLocation(randomX, randomY); //set the window to a random location


        //create new action listener
        CloseAttemptListener closeListener = new CloseAttemptListener();
        //add a WindowListener to the window -> call Close Attempt Listener each time the user tries to exit
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //Call action listener, using dummy event ID and Command
                closeListener.actionPerformed(new ActionEvent(closeListener, 1234, "lmao"));
            }
        });


        //Create a new Swing Worker to grow the Frame
        // This allows the window growing process to run in the background
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                //call the grow frame method, using the just created window as a parameter
                // along with the starting location of the window, and the window's size
                growFrame(window, randomX, randomY, screenWidth, screenHeight);
                return null;
            }
        };

        //display and create the window
        window.setContentPane(content);
        window.setResizable(false);
        worker.execute();
        window.setVisible(true);
        window.toFront();

        //create a new timer to remove the window after 7 seconds
        new Timer(7000, e -> window.dispose()).start();

    }

    /**
     * Designed to be run as a background process, this method takes a create JFrame, and some information about it,
     *  and grows the frame slowly until it reaches the edge of the screen, and then stops.
     *
     * @param window a JFrame to be grown
     * @param windowPosX the initial x location of the window
     * @param windowPosY the initial y location of the window
     * @param screenWidth the width of the display
     * @param screenHeight the height of the display
     */
    private void growFrame(JFrame window, int windowPosX, int windowPosY, int screenWidth, int screenHeight) {

        //the initial size of the growing display
        int width = 300;
        int height = 300;

        //rate of growth
        int dx = 3;
        int dy = 3;

        //grow until the window reaches the edge of the screen
        while (windowPosX > 0 && windowPosY > 0 && windowPosX + width < screenWidth && windowPosY + height < screenHeight) {
            //only step the frame once every 100ms so that it does not grow too quickly
            if (System.currentTimeMillis() % 100 == 0) {
                //increment the height and width of the window
                width += dx;
                height += dy;

                //decrement the position of the window, so that it does not just expand to the right and down
                windowPosX -= (dx / 2);
                windowPosY -= (dy / 2);

                //update the screen location and size
                window.setSize(width, height);
                window.setLocation(windowPosX, windowPosY);
                //repaint the window
                window.revalidate();
            }
        }

    }

}
