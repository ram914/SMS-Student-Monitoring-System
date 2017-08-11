/*
 * This is an open source project
 * Anybody can view, download this project
 *  Authors : Ram Prasad Gudiwada, 
 *                 Poorna Bindu Karuparthi
 */
package com.pvpsit;

/**
 *
 * @author ram bablu
 */

import java.awt.Component;
import javax.swing.JOptionPane;

public class Message {

    private static final int ERROR_CODE = 0;
    private static final int ACKNOWLEDGEMENT = 1;
    private static final int WARNING = 0;
    private static final int QUESTION = 3;
    
    public Message(){}

    public static void error(Component parent, String err_message) {
        JOptionPane.showMessageDialog(parent, err_message, "Error", ERROR_CODE, DataModel.getIcon("/error_icon.png"));
    }

    public static void acknowledge(Component parent, String ack_message) {
        JOptionPane.showMessageDialog(parent, ack_message, "Task Completed", ACKNOWLEDGEMENT, DataModel.getIcon("/acknowledgement_icon.png"));
    }

    public static int warning(Component parent, String warn_message) {
        return JOptionPane.showConfirmDialog(parent, warn_message, "Warning", WARNING, QUESTION);
    }   
}

