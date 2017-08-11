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

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class DataModel {

    static final String CONTENT_FILE = "resources/Model/KeysFile.txt";
    static final String DATA_MODEL_FILE = "resources/Model/Data_Model.csv";
    
    private static String deptsList;
    private static String yearsList;
    private static String attendancelist;
    private static String sectionList;
    private static String admissionTypeList;
    private static String fontStylesList;
    private static String fontNameList;
    private static BufferedReader br_line;
    private static String studentFieldNames;

    public static String getStudentFieldNames() {
        return studentFieldNames;
    }

    static String[] getFieldsAsList() {
        StringTokenizer tok = new StringTokenizer(getStudentFieldNames(),",");
        String[] list = new String[11];
        tok.nextToken(); // removes first token
        int i = 0;
        while(tok.hasMoreTokens()) {
            list[i++] = tok.nextToken();
        }
        return list;
    }

    public DataModel()
    {
    }

    public static String getDeptsList()
    {
        return deptsList;
    }

    public static String getYearsList()
    {
        return yearsList;
    }

    public static String getAttendancelist()
    {
        return attendancelist;
    }

    public static String getSectionList()
    {
        return sectionList;
    }

    public static String getAdmissionTypeList()
    {
        return admissionTypeList;
    }

    public static String getFontStylesList()
    {
        return fontStylesList;
    }

    public static String getFontNameList()
    {
        return fontNameList;
    }

    public static void readDataFromTheModel() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_MODEL_FILE));
            deptsList = br.readLine().replaceAll("(,)*$", "");
            yearsList = br.readLine().replaceAll("(,)*$", "");
            sectionList = br.readLine().replaceAll("(,)*$", "");
            admissionTypeList = br.readLine().replaceAll("(,)*$", "");
            attendancelist = br.readLine().replaceAll("(,)*$", "");
            fontStylesList = br.readLine().replaceAll("(,)*$", "");
            fontNameList = br.readLine().replaceAll("(,)*$", "");
            studentFieldNames = br.readLine().replaceAll("(,)*$", "");
        } catch(FileNotFoundException ex) {
            Logger.getLogger(DataModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex) {
            Logger.getLogger(DataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Icon getIcon(String file)
    {
        if(file == null)
            throw new IllegalArgumentException();
        try
        {
            java.net.URL resource = DataModel.class.getResource(file);
            ImageIcon icon = new ImageIcon(resource);
            return icon;
        }
        catch(Exception fne)
        {
            System.out.println("Fuc*ed up dude XDER\n");
        }
        return null;
    }

    public static void main(String args[])
    {
        readDataFromTheModel();
        System.out.println(getFontNameList());
        System.out.println(getFontNameList().replaceAll("(,)*$", ""));
        System.out.println(getFontStylesList());
        System.out.println(getAdmissionTypeList());
    }

    public static ArrayList readTheFile(String fileName) throws Exception {
        
        ArrayList<String> a = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for(String SingleStudent = br.readLine(); SingleStudent != null; SingleStudent = br.readLine())
                a.add(SingleStudent);
        }
        return a;
    }
    
    public static String readLine() throws IOException {
        String line = br_line.readLine();
        if( line == null) {
            br_line.close();
        }
        return line;
    }
    
    public static void initReadLine(String fileName) throws FileNotFoundException {
       br_line = new BufferedReader(new FileReader(fileName));
    }
}

