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

public class Student {
    
    int result;
    String id;
    double cgpa;
    int sem;
    String stdyear;
    String name;
    String gender;
    String dept;
    String stdAdm;
    String phone;
    String address;
    String stdatndnce;

    public Student(String id, String namf, String dept, String gen, String yr, double grd, 
            String attan, String adm, String mobile, String ad, boolean control) {
        result = -1;
        this.id = id;
        cgpa = grd;
        name = namf;
        stdyear = yr;
        gender = gen;
        this.dept = dept;
        stdAdm = adm;
        phone = mobile;
        address = ad;
        validate(control);
    }

    public Student() {
        result = -1;
    }

    private void validate(boolean control) {
        try {
            if(Validater.validateData(this, control)) {
                System.out.println("Record Validated");
                (new AllStudentDetails()).addTofile(this);
                result = 0;
            }
        } catch(Exception e) {
            Message.error(null, (new StringBuilder()).append("Exception:").append(e.toString()).toString());
        }
    }
}

