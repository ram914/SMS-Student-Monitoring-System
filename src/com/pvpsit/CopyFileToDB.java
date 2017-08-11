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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CopyFileToDB implements ActionListener {
    
    JFrame f;
    String actionString;
    String errorString;
    String parent;
    File selectedFile;
    ArrayList a;
    Student s;
    StringTokenizer token;
    int recordcount;
    PrintWriter p;

    CopyFileToDB(String title) {
        errorString = "";
        s = new Student();
        recordcount = 0;
        JFrame.setDefaultLookAndFeelDecorated(true);
        f = new JFrame();
        JFileChooser fc = new JFileChooser(".");
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Comma Separated Values", new String[] {
            "csv"
        }));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Text Documents", new String[] {
            "txt"
        }));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Excel Documents", new String[] {
            "xlsx"
        }));
        fc.addActionListener(getInstance());
        f.add(fc, "Center");
        f.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/folder.png")));
        f.setIconImage(((ImageIcon)DataModel.getIcon("/folder.png")).getImage());
        f.setVisible(true);
        f.setTitle(title);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(155, 150, 205));
        f.pack();
    }
    
    private CopyFileToDB getInstance() {
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        actionString = e.getActionCommand();
        if(actionString.equals("ApproveSelection"))
            try {
                selectedFile = ((JFileChooser)e.getSource()).getSelectedFile();
                if(selectedFile.toString().toLowerCase().endsWith("csv") || selectedFile.toString().toLowerCase().endsWith("txt")) {
                    parent = selectedFile.getParent();
                    a = DataModel.readTheFile(selectedFile.toString());
                    copyToTheFile();
                    if(recordcount == a.size()) {
                        Message.acknowledge(null, (new StringBuilder()).append("All Records ").append(a.size()).append(" are added.").toString());
                        (new File((new StringBuilder()).append(parent).append("\\tempy.csv").toString())).delete();
                    } else if(recordcount < a.size())
                        Message.acknowledge(null, (new StringBuilder()).append("Total records = ").append(a.size()).append("\nRecords added = ").append(recordcount).append("\nErrors countered:\n").append(errorString).append("\n Check this file ").append(parent).append("\\tempy.csv").toString());
                } else {
                    Message.error(null, "Required .csv or .txt files\nFile can not be copied");
                }
                f.dispose();
            } catch(Exception ex) {
                Message.error(null, (new StringBuilder()).append("Exception :").append(ex.toString()).toString());
            }
        else if(actionString.equals("CancelSelection"))
            f.dispose();
    }

    private void copyToTheFile() throws IOException {
        int i = 0;
        p = new PrintWriter(new BufferedWriter(new FileWriter((new StringBuilder()).append(parent).append("\\tempy.csv").toString(), true)));
        while(i < a.size()) {
            token = new StringTokenizer((String)a.get(i++), ",");
            s.id = token.nextToken();
            s.name = token.nextToken();
            s.dept = token.nextToken();
            s.gender = token.nextToken();
            s.stdyear = token.nextToken();
            s.cgpa = Double.parseDouble(token.nextToken());
            s.stdatndnce = token.nextToken();
            s.stdAdm = token.nextToken();
            s.phone = token.nextToken();
            s.address = token.nextToken();
            Student s1 = new Student(s.id, s.name, s.dept, s.gender, s.stdyear, s.cgpa, s.stdatndnce, s.stdAdm, s.phone, s.address, false);
            if(s1.result == 1)
                recordcount++;
            else
                p.println((String)a.get(i - 1));
        }
        p.close();
    }

    public static void main(String as[]) {
        CopyFileToDB copyFileToDB = new CopyFileToDB("Select file to copy.....");
    }
}
