package windowPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Hold an actionListener to be called each time there is an attempt to close a window.
 *  Also called by a timer to open a new window every second.
 *
 * @author Jon Wiggins
 * @version 3/4/2017
 */
public class CloseAttemptListener implements java.awt.event.ActionListener {

    /**
     * If the event that calls this method is a Timer, then one new instance of the ApplicationMain class
     *  will be created and run, otherwise 5 will be created and run.
     *
     * @param e the triggering event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //get the source of the object that called this method
        Object actionSource = e.getSource();

        //if the source of the event is a timer, create and run one new instance of ApplicationMain
        if (actionSource instanceof Timer) {
            ApplicationMain first = new ApplicationMain();
            SwingUtilities.invokeLater(first);
        } else {
            //otherwise create and run 5 new instances of ApplicationMain
            ApplicationMain first = new ApplicationMain();
            SwingUtilities.invokeLater(first);

            ApplicationMain second = new ApplicationMain();
            SwingUtilities.invokeLater(second);

            ApplicationMain third = new ApplicationMain();
            SwingUtilities.invokeLater(third);

            ApplicationMain fourth = new ApplicationMain();
            SwingUtilities.invokeLater(fourth);

            ApplicationMain fifth = new ApplicationMain();
            SwingUtilities.invokeLater(fifth);
        }

    }

}
