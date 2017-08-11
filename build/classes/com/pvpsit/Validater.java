/*
 * This is an open source project
 * Anybody can view, download this project
 *  Authors : Ram Prasad Gudiwada, 
 *                 Poorna Bindu Karuparthi
 */
package com.pvpsit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author ram bablu
 */

public class Validater {
    
    static boolean result = true;
    static String errorString;

    Validater(){}

    static boolean validateData(Student s, boolean control) {
        try {
            result = true;
            if("".equals(s.name) || "".equals(s.gender) || "".equals(s.dept) || "".equals(s.stdyear) ||
                    "".equals(s.stdAdm) || "".equals(s.stdatndnce) || "".equals(s.cgpa) ||
                    s.id.length() != 10 || 10 != s.phone.length()) {
                errorString = (new StringBuilder()).append(errorString).append("\nEmptyFields or Invalid Info given").toString();
                result = false;
            }
            if(s.id.contains("`~!@#$%^&*()_+=-[{]}\\|;:'\"<,>.?/")) {
                errorString = (new StringBuilder()).append(errorString).append("\nInvalid Id given!!!").toString();
                result = false;
            }
            if(s.name.contains("0123456789`~!@#$%^&*()_+=-[{]}\\|;:'\"<,>.?/")) {
                errorString = (new StringBuilder()).append(errorString).append("\nInvalid Name given!!!").toString();
                result = false;
            }
            if(!validateStudentId(s))
                result = false;
            try {
                if(s.cgpa < 0.0D || s.cgpa > 10D) {
                    errorString = (new StringBuilder()).append(errorString).append("\nInvalid CGPA given!!!").toString();
                    result = false;
                }
            }
            catch(Exception ex) {
                errorString = (new StringBuilder()).append(errorString).append("\nInvalid CGPA given!!!").toString();
                result = false;
            }
        }
        catch(Exception ex) {
            result = false;
        }
        if(control && !result)
            Message.error(null, errorString);
        return result;
    }

    private static boolean validateStudentId(Student s) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            int year_one = Integer.parseInt(dateFormat.format(date).substring(0, 4));
            int month = Integer.parseInt(dateFormat.format(date).substring(5, 7));
            if(month <= 6)
                year_one -= 2001;
            else
                year_one -= 2000;
            int yearGiven = Integer.parseInt(s.id.substring(0, 2));
            int max = 3;
            int count = 4;
            StringTokenizer d = new StringTokenizer(DataModel.getYearsList(), ",");
            d.nextToken();
            StringTokenizer d1 = new StringTokenizer((new StringBuilder()).append(year_one).append(",").append(year_one - 1).append(",").append(year_one - 2).append(",").append(year_one - 3).toString(), ",");
            String Admission = "Regular";
            if(s.id.substring(2, 6).equals("505A")) {
                max = 2;
                count = 3;
                System.out.println((new StringBuilder()).append("Ignored").append(d.nextToken()).toString());
                d1 = new StringTokenizer((new StringBuilder()).append(year_one).append(",").append(year_one - 1).append(",").append(year_one - 2).toString(), ",");
                Admission = "Lateral";
                if(Integer.parseInt(s.id.substring(8, 10)) > 24) {
                    errorString = "\nInvalid Id given";
                    result = false;
                }
                if(!s.stdAdm.equals("Lateral")) {
                    errorString = "\nInvalid Id given";
                    result = false;
                }
            } else if(s.id.substring(2, 6).equals("501D")) {
                max = 4;
                d1 = new StringTokenizer((new StringBuilder()).append(year_one - 1).append(",").append(year_one - 2).append(",").append(year_one - 3).append(",").append(year_one - 4).toString(), ",");
                if(!s.stdAdm.equals("Detained")) {
                    Admission = "Detained";
                    errorString = "-Invalid Id given";
                    result = false;
                }
            } else if(!s.id.substring(2, 6).equals("501A")) {
                errorString = "Invalid Id given";
                result = false;
            }
            if(yearGiven > year_one || yearGiven < year_one - max) {
                errorString = "Invalid Id given(1st two characters)";
                result = false;
            }
            int i = 0;
            do
            {
                if(i >= count)
                    break;
                String t1 = (new StringBuilder()).append(d1.nextToken()).append("").toString();
                String t = d.nextToken();
                System.out.println((new StringBuilder()).append("check : ").append(s.id.substring(0, 2)).append(" ").append(t1).append(" ").append(s.stdyear).append(" ").append(t).toString());
                if(s.id.substring(0, 2).equals(t1) && s.stdyear.equals(t) && "Regular Detained Lateral".contains(Admission))
                    break;
                i++;
            } while(true);
            d = new StringTokenizer(DataModel.getDeptsList(), ",");
            d.nextToken();
            d1 = new StringTokenizer("21,01,05,04,02,12,03", ",");
            int j = 0;
            do {
                if(j >= 7)
                    break;
                String t1 = d1.nextToken();
                String t = d.nextToken();
                System.out.println((new StringBuilder()).append(s.id.substring(6, 8)).append(" ").append(t1).append(" ").append(s.dept).append(" ").append(t).toString());
                if(s.id.substring(6, 8).equals(t1) && s.dept.equals(t)) {
                    System.out.println("Breaked");
                    break;
                }
                j++;
            } while(true);
            if(j > 6 || i > count - 1) {
                System.out.println("Here is the error");
                errorString = "Id given doesn't match with department or academic year";
                result = false;
            }
        }
        catch(Exception exception) { }
        System.out.println((new StringBuilder()).append("Error:").append(result).append(" ").toString());
        return result;
    }
}
