package com.asrmscii2unicode;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class MainFrame extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel lblInput;
    private JButton btnInput;
    private JLabel lblOutput;
    private JButton btnOutput;
    private File inputFile;
    private File outputFile;

    public MainFrame() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        btnInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseFileFor(btnInput);
            }
        });
        btnOutput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseFileFor(btnOutput);
            }
        });
    }

    private void chooseFileFor(JButton chooserBtn) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showDialog(this, "Choose a file");
        File file = fileChooser.getSelectedFile();
        if(file != null) {
            chooserBtn.setText(file.getAbsolutePath());
            if(chooserBtn == btnInput) {
                inputFile = file;
            } else {
                outputFile = file;
            }
        } else {
            chooserBtn.setText("");
        }
    }

    private void onOK() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        MainFrame dialog = new MainFrame();
        dialog.pack();
        dialog.setVisible(true);
    }
}
