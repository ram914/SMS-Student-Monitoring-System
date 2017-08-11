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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AllStudentDetails {
    
    ArrayList stdRecordList;
    StringTokenizer token;
    PrintWriter p;
    String idSource;
    Student sTemp;

    AllStudentDetails(String fileName, boolean value) throws Exception {
        stdRecordList = new ArrayList();
        sTemp = new Student();
        if(value)
            printAll(fileName);
        else
            deleteAll(fileName);
    }

    AllStudentDetails() {
        stdRecordList = new ArrayList();
        sTemp = new Student();
    }

    private void printAll(String fileName) {
        JFrame f;
        f = new JFrame((new StringBuilder()).append("Students in ").append(fileName).toString());
        int j = 4;
        String[] columnNames = DataModel.getFieldsAsList();
        
        try {
            stdRecordList = DataModel.readTheFile(fileName);
        }
        catch(Exception e) {
            Message.error( null, (new StringBuilder()).append("Exception:").append(e).toString());
        }
        
        Object[][] rowData = new Object[stdRecordList.size()][11];
        for(int i = 0; i < stdRecordList.size(); i++) {
            token = new StringTokenizer((String)stdRecordList.get(i), ",");
            Object[] row = new Object[11];
            int row_i = 0;
            row[row_i++] = ((new StringBuilder()).append(i + 1).append(")").toString());
            while( token.hasMoreTokens() )
                row[row_i++] = (token.nextToken());
            rowData[i] = (row);
        }
        JTable table;
        
        table = new JTable((rowData), (columnNames)){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        JScrollPane scrollPane = new JScrollPane(table);
        table.setGridColor(new Color(150, 50, 150));
        table.getTableHeader().setBackground(new Color(150, 50, 150));
        f.add(scrollPane);
        f.setVisible(true);
        f.setIconImage(((ImageIcon)DataModel.getIcon("/student.png")).getImage());
        f.setSize(850, 300);
    }

    private void deleteAll(String fileName) {
        int val = Message.warning(null, "Delete all the records??\nChanges can not be reverted");
        if(val != 0) {
            try {
                p = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
                p.close();
                Message.acknowledge(null, "All the records have been deleted");
            } catch(Exception ex) {
                p.close();
                Message.error(null, "Either file doesn't exist\n       or\nIt is already deleted");
            }
        } else
            Message.acknowledge(null, "Action terminated\nNo Changes Made");
    }

    Student modifyFile(int con, Student s, String fileName) throws Exception {
        s.result = 0;
        idSource = JOptionPane.showInputDialog("Enter the Id of the person:");
        if(idSource.length() != 10)
            return s;
        if("".equals(fileName))
            fileName = getFileName(idSource);
        if("".equals(fileName))
            return s;
        int secValue = 2;
        if(idSource.substring(8, 10).compareTo("60") <= 0)
            secValue = 1;
        
        // open the file in read by line mode
        DataModel.initReadLine((new StringBuilder()).append(fileName).append("\\Section ").append(secValue).append(".csv").toString());
                
        do {
            String line = DataModel.readLine();
            if(line == null)
                break;
            token = new StringTokenizer(line, ",");
            if(token.nextToken().compareTo(idSource) == 0) {
                s.result = 1;
                if(con != 1) {
                    if(con == 3) {
                        assignValues(idSource, token.nextToken(), token.nextToken(), token.nextToken(), token.nextToken(), token.nextToken(), token.nextToken(), token.nextToken(), token.nextToken(), token.nextToken());
                        s = sTemp;
                        return s;
                    }
                    s = sTemp;
                    if(!Validater.validateData(s, true)) {
                        s.result = 2;
                        Message.error(null, "Fields left Empty!!!");
                        stdRecordList.clear();
                        return s;
                    }
                    String m = (new StringBuilder()).append(s.id).append(",").append(s.name).append(",").append(s.dept).append(",").append(s.gender).append(",").append(s.stdyear).append(",").append(s.cgpa).append(",").append(s.stdatndnce).append(",").append(s.stdAdm).append(",").append(s.phone).append(",").append(s.address).toString();
                    System.out.println(m);
                    stdRecordList.add(m);
                }
            } else
            {
                stdRecordList.add(line);
            }
        } while(true);
        fileName = (new StringBuilder()).append(fileName).append("\\Section ").append(secValue).append(".csv").toString();
        write(fileName);
        return s;
    }

    private void write(String fileName) {
        try {
            p = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            for(int i = 0; i < stdRecordList.size(); i++)
                p.println((String)stdRecordList.get(i));

            p.close();
        } catch(Exception ex) {
            Message.error(null, (new StringBuilder()).append("Exception ").append(ex.toString()).append("\n while writing to file").toString());
            p.close();
        }
    }

    void addTofile(Student s) throws Exception {
        try {
            String fileName = getFileName(s.id);
            int secValue = 2;
            if(s.id.substring(8, 10).compareTo("60") <= 0)
                secValue = 1;
            if(s.id.substring(2, 6).equals("505A") && s.id.substring(8, 10).compareTo("13") >= 0)
                secValue = 2;
            System.out.println((new StringBuilder()).append("Parent path : ").append(fileName).toString());
            File f = new File((new File((new StringBuilder()).append(fileName).append("/Section ").append(secValue).append(".csv").toString())).getAbsolutePath());
            System.out.println((new StringBuilder()).append("Path : ").append(f.getAbsolutePath()).toString());
            if(!f.exists())
            {
                (new File(f.getParent())).mkdirs();
                f.createNewFile();
            }
            String current = (new StringBuilder()).append(s.id).append(",").append(s.name).append(",").append(s.dept).append(",").append(s.gender).append(",").append(s.stdyear).append(",").append(s.cgpa).append(",").append(s.stdatndnce).append(",").append(s.stdAdm).append(",").append(s.phone).append(",").append(s.address).toString();
            ArrayList readTheFile = DataModel.readTheFile(f.toString());
            stdRecordList = Sort.sort(stdRecordList, current, s.id);
            write(f.toString());
        } catch(Exception ex) {
            Message.error(null, (new StringBuilder()).append("Exception ").append(ex.toString()).append("\n while opening the file").toString());
        }
    }

    private String getFileName(String idSource) {
        try {
        int IstYear;
        int curYear;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        int year = Integer.parseInt(dateFormat.format(date).substring(0, 4));
        int month = Integer.parseInt(dateFormat.format(date).substring(5, 7));
        if(month <= 6)
            IstYear = year - 2001;
        else
            IstYear = year - 2000;
        curYear = Integer.parseInt(idSource.substring(0, 2));
        
        String s;
        switch(idSource.substring(2,6)) {
        case (("501A")):
            if(curYear == IstYear) {
                s = "I_YEAR";
                break; 
            } else if(curYear == IstYear - 1) {
                s = "II_YEAR";
                break; 
            } else if(curYear == IstYear - 2) {
                s = "III_YEAR";
                break; 
            } else if(curYear == IstYear - 3)
                s = "IV_YEAR";
            else
                return "";
            break;
        case (("505A")) :
            if(curYear == IstYear) {
                s = "II_YEAR";
                break; 
            } else if(curYear == IstYear - 1) {
                s = "III_YEAR";
                break; 
            } else  if(curYear == IstYear - 2)
                s = "IV_YEAR";
            else
                return "";
            break;

        case "501D": 
            if(curYear == IstYear - 1) {
                s = "I_YEAR";
                break; 
            } else if(curYear == IstYear - 2) {
                s = "II_YEAR";
                break; 
            } else if(curYear == IstYear - 3) {
                s = "III_YEAR";
                break; 
            } else if(curYear == IstYear - 4)
                s = "IV_YEAR";
            else
                return "";
            break;
        default : return "";
        }
        String D;
        String Dt;
        
        
        D = idSource.substring(6, 8);
        switch (D) {
                case "01":Dt = "CE";break;
                case "21":Dt = "AE";break;
                case "02":Dt = "EEE";break;
                case "03":Dt = "MEC";break;
                case "04":Dt = "ECE";break;
                case "05":Dt = "CSE";break;
                case "12":Dt = "IT";break;
                default:return "";
        }
        return (new StringBuilder()).append("std_data/").append(Dt).append("/").append(s).toString();
        } catch(Exception ex) {
            Message.error(null, (new StringBuilder()).append("Exception ").append(ex.toString()).append("\n While trying to open the file").toString());
            return "";
        }
    }

    void assignValues(String id, String namf, String dept, String gen, String yr, String grd, String attan, 
            String adm, String mobile, String ad) {
        sTemp.id = id;
        sTemp.name = namf;
        sTemp.dept = dept;
        sTemp.gender = gen;
        sTemp.stdyear = yr;
        sTemp.cgpa = Double.parseDouble(grd);
        sTemp.stdatndnce = attan;
        sTemp.stdAdm = adm;
        sTemp.phone = mobile;
        sTemp.address = ad;
    }
}

