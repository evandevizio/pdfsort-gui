package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class Gui {
	
	String introString1 =
			"**********************************************************************" + "\r\n" +
			"**********************************************************************" + "\r\n" +
			"                                                                      " + "\r\n" +
			"########  ########  ########     ######   #######  ########  ######## " + "\r\n" + 
			"##     ## ##     ## ##          ##    ## ##     ## ##     ##    ##    " + "\r\n" +     
			"##     ## ##     ## ##          ##       ##     ## ##     ##    ##    " + "\r\n" + 
			"########  ##     ## ######       ######  ##     ## ########     ##    " + "\r\n" + 
			"##        ##     ## ##                ## ##     ## ##   ##      ##    " + "\r\n" + 
			"##        ##     ## ##          ##    ## ##     ## ##    ##     ##    " + "\r\n" + 
			"##        ########  ##           ######   #######  ##     ##    ##    " + "\r\n" +
			"                                                                      " + "\r\n" +
			"**********************************************************************" + "\r\n" +
			"**********************************************************************" + "\r\n" +
			"                                                                      " + "\r\n";
	
	String introString2 =
			"-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+" + "\r\n" +
			"-+-+-+-+-+-+-|                   |+-+-+-|                 |+-+-+-+-+-+" + "\r\n" + 
			"-+-+-+-+-+-+-|  by Evan DeVizio  |+-+-+-| evandevizio.com |+-+-+-+-+-+" + "\r\n" + 
			"-+-+-+-+-+-+-|                   |+-+-+-|                 |+-+-+-+-+-+" + "\r\n" +
			"-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+" + "\r\n" +
			"                                                                      " + "\r\n";
    JFrame frame = new JFrame("PDFSort v1.0");
    JPanel panel = new JPanel();	// the panel is not visible in output
    JButton resetBtn = new JButton("Reset");
    JButton themeBtn = new JButton("Theme");
    JTextArea ta = new JTextArea(); 	// the main text area
    JScrollPane scroll = new JScrollPane(ta);
    JMenuBar mb = new JMenuBar();     // creating the MenuBar and options
    JMenu m1 = new JMenu("File");
    JMenuItem mOpen = new JMenuItem("Open");
    JMenuItem mCreateTest = new JMenuItem("Create Test File");
    JMenuItem mExit = new JMenuItem("Exit");
    
    Font font = new Font("Monospaced", Font.BOLD, 12);
    
    File currentFile = null;
    String currentFilePath = "";
    
    int colorIndex = 2;
    Color amber = new Color(185, 128, 0);  
    Color black = new Color (0, 0, 0);
    Color white = new Color (255, 255, 255);
    Color blue = new Color (114, 159, 207);
    Color yellow = new Color (196, 160, 0);
    Color grey = new Color (211, 215, 207);
    Color magenta = new Color (173, 127, 168);
    Color green = new Color (78, 154, 6);
    Color brightYellow = new Color (252, 233, 79);
    Color brightGreen = new Color (138, 226, 52);
    Color brightRed = new Color (239, 41, 41);
    final Color[] bgColors = {black, white, black, yellow, green, magenta, black, black};
    final Color[] fgColors = {white, black, amber, grey, white, brightYellow, brightGreen, brightRed};
    
	public void CreateGui(Operations ops){
    	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(525, 625);
        frame.setResizable(false);
        
        panel.add(resetBtn);
        panel.add(themeBtn);
        
        ta.setLineWrap(true);
        ta.setEditable(false);
        ta.setFont(font);
        ta.setForeground(fgColors[colorIndex]);
        ta.setBackground(bgColors[colorIndex]);
        
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        mb.add(m1); 	// adding menu options
        m1.add(mOpen);
        m1.add(mCreateTest);
        m1.add(mExit);
        
        mOpen.addActionListener(new ActionListener() {
		@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(null);	// select file
				if (returnVal == JFileChooser.APPROVE_OPTION) {
			        File file = fc.getSelectedFile();
			        currentFilePath = file.getAbsolutePath();
			        currentFile = file;
			        ta.setText(introString1 + introString2 + "File path = " + currentFilePath + "\n");
			        ops.processPdf(currentFile, ta);
				}
			}
        });
        
        mCreateTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ops.createTestFile(ta);				
			}
        });
        
        mExit.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		System.exit(0);	// Terminate the program.
        	}
        });
        
        themeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				colorIndex++;
				if (colorIndex == bgColors.length) {
					colorIndex = 0;
				}
				ta.setBackground(bgColors[colorIndex]);
				ta.setForeground(fgColors[colorIndex]);
			}
        });
        
        resetBtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	    colorIndex = 2;
        	    currentFile = null;
        	    currentFilePath = "";
                ta.setForeground(fgColors[colorIndex]);
                ta.setBackground(bgColors[colorIndex]);
                ta.setText(introString1 + introString2);
                ta.append("Please open a PDF. " + "Use \"File -> Open\" to navigate." + "\n");
        	}
        });
        
        // adding components to the frame
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, scroll);
        frame.setVisible(true);
        
        ta.setText(introString1 + introString2);
        ta.append("Please open a PDF. " + "Use \"File -> Open\" to navigate." + "\n");
        ta.setCaretPosition(ta.getDocument().getLength());
	}
}
