package com.asrmscii2unicode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        String argSrcFile = args[0];
        String argDstFile = args[1];
        File inputFile = new File(argSrcFile);
        File outputFile = new File(argDstFile);
        
        InputStream in = new FileInputStream(inputFile);
        OutputStream out = new FileOutputStream(outputFile);


        ANSIUnicodeConverter converter = new ANSIUnicodeConverter();
        converter.setDirection(ANSIUnicodeConverter.ANSI2UNICODE);
        StringBuffer output = new StringBuffer();
        
        final int BUFFER_SIZE = 1024 * 1024; // 1M
        byte[] buffer = new byte[BUFFER_SIZE];
        int currentPosition = 0;
        while (in.available() != 0) {
            int currentBufferSize = BUFFER_SIZE;
            if(in.available() < BUFFER_SIZE) {
                currentBufferSize = in.available();
            }
            in.read(buffer, currentPosition, currentBufferSize);
            currentBufferSize = currentBufferSize + currentBufferSize;
            converter.setInput(new String(buffer));

            out.write(converter.convert().getBytes());
        }
        
        in.close();
        out.close();
    }
}
