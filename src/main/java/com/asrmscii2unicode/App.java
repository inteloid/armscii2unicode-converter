package com.asrmscii2unicode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point of application
 */
public class App {
    public static void main(String[] args) {
        if(System.console() != null) {
            try {
                File inputFile = new File(args[0]);
                File outputFile = new File(args[0]);

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
                    if (in.available() < BUFFER_SIZE) {
                        currentBufferSize = in.available();
                    }
                    in.read(buffer, currentPosition, currentBufferSize);
                    currentBufferSize = currentBufferSize + currentBufferSize;
                    converter.setInput(new String(buffer));

                    out.write(converter.convert().getBytes());
                }

                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            MainFrame dialog = new MainFrame();
            dialog.pack();
            dialog.setVisible(true);
        }
    }
}
