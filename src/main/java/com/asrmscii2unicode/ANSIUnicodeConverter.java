package com.asrmscii2unicode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: inteloid
 * Date: 8/25/12
 * Time: 7:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class ANSIUnicodeConverter {
    private List<Integer> ascii;
    private List<Integer> unicode;
    
    private String input;
    private StringBuffer output;

    private int direction;
    
    public static final int ANSI2UNICODE = 1;
    public static final int UNICODE2ANSI = 2;
    
    public ANSIUnicodeConverter() {
        init();
    }
    
    private void init() {
        ascii = new ArrayList<Integer>();
        unicode = new ArrayList<Integer>();

        for(int i = 178; i <= 252; i+=2){
            ascii.add(i); //mecatar ascii
            unicode.add(1328 + (i-176)/2); //mecatar unicode
            ascii.add(i+1); //poqratar ascii
            unicode.add(1376 + (i-176)/2); //poqratar unicode
        }
        ascii.add(168);		unicode.add(0x587);  //ev

        ascii.add(183);		unicode.add(8226);	 //poqratar g-n (bullet)
        ascii.add(8226);	unicode.add(1379);	 //poqratar g-n (bullet)

        ascii.add(39);		unicode.add(0x55A);  //apostrophe
        ascii.add(176);		unicode.add(0x55B);  //shesht
        ascii.add(175);		unicode.add(0x55C);  //bacakanchakan
        ascii.add(170);		unicode.add(0x55D);  //but
        ascii.add(177);		unicode.add(0x55E);  //harcakan
        ascii.add(163);		unicode.add(0x589);  //verjaket
        ascii.add(173);		unicode.add(0x58A);  //hyphen
        ascii.add(167);		unicode.add(0xAB);   //bacvogh chakert
        ascii.add(166);		unicode.add(0xBB);   //pakvogh chakert
        ascii.add(171);		unicode.add(0x2C);   //storaket
        ascii.add(169);		unicode.add(0x2E);   //mijaket
        ascii.add(174);		unicode.add(0x2026); //bazmaket

        ascii.add(0,0);		unicode.add(0,0); //2 hat CUSTOM :)
    }

    public String convert() {
        output = new StringBuffer();
        List<Integer> from = direction ==  ANSI2UNICODE ? ascii : unicode;
        List<Integer> to = direction ==  ANSI2UNICODE ? unicode : ascii;
        int currentDestChar;

        for(int i = 0; i < input.length(); i++) {
            int currentSrcChar = input.charAt(i);
            try {
                int index = from.indexOf(currentSrcChar);
                currentDestChar = to.get(index);
            } catch (ArrayIndexOutOfBoundsException oobe) {
                currentDestChar = currentSrcChar;
            }
            output.append((char)currentDestChar);
        }

        return output.toString();
    }
    
    public String convert(String input) {
        setInput(input);
        return convert();
    }
    
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output.toString();
    }
}
