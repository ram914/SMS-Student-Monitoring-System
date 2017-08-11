/*
 * This is an open source project
 * Anybody can view, download this project
 *  Authors : Ram Prasad Gudiwada, 
 *            Poorna Bindu Karuparthi
 */
package com.pvpsit;
/**
 *
 * @author ram bablu
 */

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SMS {

    private SMS(String text) {
        System.out.println("Hello World");
        GraphicsLoader.init(text);
        GraphicsLoader.setEnableDisable(false);
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SMS sms = new SMS("Student Info Management (SIM)");
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            Message.error(null, (new StringBuilder()).append("Application Crashed\nReopen the Application\n").append(e.toString()).toString());
        }
    }
}
