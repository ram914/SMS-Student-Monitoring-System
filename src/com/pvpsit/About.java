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

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class About {
    private JPanel panel;
    private JLabel label;
    private JButton closeButton = new JButton("close");
    JTabbedPane jtp;
    private final Font DEFAULT_FONT = new Font("consolas", 0, 15);
    private final Color DEFAULT_COLOR = new Color(22, 228, 226);

    About(String title) {
        JFrame f = new JFrame();
            f.setBackground(Color.blue);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            DataModel.initReadLine(DataModel.CONTENT_FILE);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exv) {
            Message.error(null, "Exception :" + exv.toString());
            return;
        } catch (FileNotFoundException ex) {
            Message.error(null, "Exception :" + ex.toString());
            Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        JLabel imageLabel = new JLabel(DataModel.getIcon("/about_clg.png"));
        imageLabel.setBackground(Color.white);
        f.add((Component)imageLabel, "North");
        
        this.jtp = new JTabbedPane();
        this.addLableToTab(this.panel, 4, 1);
        this.addLableToTab(this.panel, 5, 1);
        this.addLableToTab(this.panel, 2, 1);
        this.addLableToTab(this.panel, 2);
        f.add((Component)this.jtp, "Center");
        f.add((Component)this.closeButton, "South");
        this.closeButton.addActionListener(e -> {
            f.dispose();
        });
        this.closeButton.setBackground(new Color(75, 15, 175));
        this.closeButton.setFont(this.DEFAULT_FONT);
        
        f.setVisible(true);
        f.setTitle(title);
        f.setResizable(false);
        f.getContentPane().setBackground(Color.WHITE);
        f.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/informationA.png")));
        f.pack();
        f.setLocationRelativeTo(null);
    }

    private void addLableToTab(JPanel p, int r, int c) {
        try {
            p = new JPanel(new GridLayout(r, c));
            for (int i = 0; i < r; ++i) {
                final String text = DataModel.readLine();
                if (text.endsWith("in/")) {
                    this.label = new JLabel();
                    this.label.setText("<html><pre>Blogs\t:<a href=\"\">" + text + "</a></pre></html>");
                    this.label.setCursor(new Cursor(12));
                    this.label.addMouseListener(new MouseAdapter(){

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                Desktop.getDesktop().browse(new URI(text));
                            }
                            catch (IOException | URISyntaxException ex) {
                                Message.error(null, "Unable to open the link\n" + ex.toString());
                                System.out.println("Exception");
                            }
                        }
                    });
                } else {
                    this.label = new JLabel(text);
                }
                this.label.setFont(this.DEFAULT_FONT);
                p.add(this.label);
            }
            this.jtp.addTab(DataModel.readLine(), p);
            p.setBackground(this.DEFAULT_COLOR);
        }
        catch (Exception exp) {
            Message.error(null, "Exception :" + exp.toString());
        }
    }

    private void addLableToTab(JPanel p, int r) {
        p = new JPanel(new GridLayout(1, 1));
        try {
            String content = getContent();
            JTextArea ta = new JTextArea(content, 3, 1);
            ta.setFont(this.DEFAULT_FONT);
            ta.setBackground(this.DEFAULT_COLOR);
            ta.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(ta);
            p.add(scrollPane);
            this.jtp.addTab("Keys", p);
            p.setBackground(this.DEFAULT_COLOR);
        }
        catch (Exception ex) {
            Message.error(null, "Exception :" + ex.toString());
            Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] as) {
        About aboutMnuItmFrame = new About("About.....");
    }

    private String getContent() throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while( (line = DataModel.readLine() ) != null ) {
            content.append(line).append('\n');
        }
        return content.toString();
    }
}
