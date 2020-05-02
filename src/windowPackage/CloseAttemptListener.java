package windowPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Hold an actionListener to be called each time there is an attempt to close a
 * window. Also called by a timer to open a new window every second.
 *
 * @author Jon Wiggins
 * @version 5/2/2020
 */
public class CloseAttemptListener implements java.awt.event.ActionListener {

    private static final int NEW_WINDOW_COUNT = 5;

    /**
     * If the event that calls this method is a Timer, then one new instance of the
     * ApplicationMain class will be created and run, otherwise 5 will be created
     * and run.
     *
     * @param e the triggering event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // get the source of the object that called this method
        Object actionSource = e.getSource();

        // if the source of the event is a timer, create and run one new instance of
        // ApplicationMain
        if (actionSource instanceof Timer) {
            ApplicationMain first = new ApplicationMain();
            SwingUtilities.invokeLater(first);
        } else {
            // otherwise create and run new instances of ApplicationMain
            for (int count = 0; count < NEW_WINDOW_COUNT; count++) {
                ApplicationMain first = new ApplicationMain();
                SwingUtilities.invokeLater(first);
            }

        }

    }

}
