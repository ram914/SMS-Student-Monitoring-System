/*
 * This is an open source project
 * Anybody can view, download this project
 *  Authors : Ram Prasad Gudiwada, 
 *                 Poorna Bindu Karuparthi
 */
package com.pvpsit;
import java.util.ArrayList;

/**
 *
 * @author ram bablu
 */


public class Sort {

    public Sort(){}

    // Insertion Sort
    static ArrayList sort(ArrayList ad, String current, String Id) {
        if(!ad.isEmpty()) {
            for(int k = 0; k < ad.size(); k++) {
                int ram = ((String)ad.get(k)).substring(0, 10).compareTo(Id);
                if(ram > 0) {
                    ad.add(k, current);
                    return ad;
                }
                if(ram == 0)
                    return ad;
            }
        }
        ad.add(current);
        return ad;
    }
}

