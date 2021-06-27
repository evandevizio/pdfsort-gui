package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.swing.JTextArea;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;


public class Operations {
	
	String fileName;
    String fileNameCleaned;
    String fileExtens;
    String restartInput;
	String currentSize = "";	// The current page size (Letter or Legal)
	String previousSize = ""; // The previous page size.
	String firstPageSize = ""; // The page size of the first page in a sequence.
    // BigDecimal representations of page dimensions. Easier for comparisons.
    BigDecimal width = new BigDecimal(8.5); // Both legal and letter are 8.5" wide.
    BigDecimal letterHeight = new BigDecimal (11.0);
    BigDecimal legalHeight = new BigDecimal (14.0);
    
    int totalLetterCount = 0; // Total page counts for the PDF.
    int totalLegalCount = 0;
    int totalPageCount = 0;
	int seqLetterCount = 0; // Page counts for each sequence.
	int seqLegalCount = 0;
    int numOfSeqs = 0; // Number of stacks of different sized pages (Letter or Legal). Initialized at 1.
	
	boolean sizeChanged = Boolean.FALSE; // This flag switches when page size changes. Initialized as FALSE.
	boolean firstStack; // This flag switches after the first stack is added to the table.
	
	void processPdf(File file, JTextArea ta) {
		try { // Get PDF metadata.
			PDDocument doc = PDDocument.load(file);
			PDDocumentInformation info = doc.getDocumentInformation();
	        
	        getPageInfo(doc, info, ta); // Get page sizes, ranges, and page counts.
	        
			ta.append("\r\n" + "**********************************************************************" + "\r\n");
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		restart();
	}

	void getPageInfo(PDDocument doc, PDDocumentInformation info, JTextArea ta) throws IOException {
		
		TableEntry.printHeader(ta);
		
		PDPageTree allPages = doc.getDocumentCatalog().getPages();

		for (PDPage page : allPages)
		{		
			getPageSize(page, ta); // set currentSize
			
			if (totalPageCount <= 1) // this takes care of the first stack
			{
				previousSize = currentSize;
			}
			
			if (currentSize != previousSize) // new stack if this size is different than previous
			{
				changeStack(ta);
			}
			
			switch (currentSize) // increment page size counts
			{
			case "Letter":
				seqLetterCount++;
				totalLetterCount++;
				break;
			case "Legal":
				seqLegalCount++;
				totalLegalCount++;
				break;
			}
			
			if (totalPageCount >= allPages.getCount()) // this takes care of the final stack
			{
				changeStack(ta);
			}
			
			previousSize = currentSize; // prepare for next page
		}
		
		ta.append("\r\n" + "**********************************************************************" + "\n");
	
		String creator = ((info.getCreator() == null || info.getCreator() == "") ? "N/A" : info.getCreator());
		ta.append("Creator : " + creator + "\n");
		ta.append("Created : " + new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa" + "\n")
				.format(info.getCreationDate().getTimeInMillis()));
		ta.append("Processed: " + new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa" + "\n")
				.format(new Date()));
		ta.append("Page total : " + doc.getNumberOfPages() + "\n");
		ta.append("Letter total: " + totalLetterCount + "\n");
		ta.append("Legal total: " + totalLegalCount + "\n");
	}
	
	void getPageSize(PDPage page, JTextArea ta) {
		// Get the dimensions of a page (1pt = 1/72"):
		float pageHeight = ((page.getMediaBox().getHeight() / 72));
		float pageWidth = ((page.getMediaBox().getWidth() / 72));

		BigDecimal pageH = new BigDecimal(pageHeight);
		BigDecimal pageW = new BigDecimal(pageWidth);
		pageH = pageH.round(new MathContext(2, RoundingMode.HALF_DOWN));
		pageW = pageW.round(new MathContext(2, RoundingMode.HALF_DOWN));
		
		System.out.println(pageH);
		System.out.println(pageW);
		
		// Set the current page size:
		if ((width.compareTo(pageW) == 0) && (letterHeight.compareTo(pageH) == 0))
		{
			currentSize = "Letter";
		}
		else
			if ((width.compareTo(pageW) == 0) && (legalHeight.compareTo(pageH) == 0))
			{
				currentSize = "Legal";
			}
			else
			{
				ta.append("WARNING: This page is neither Letter nor Legal size!");
				ta.append("\r\n");
			}
		
		totalPageCount++;
	}
	
	void changeStack(JTextArea ta) { // increment sequence #, print sequence to console, reset size #s
		numOfSeqs++;
		TableEntry entry = new TableEntry();
		entry.setSeq(numOfSeqs);
		entry.setLetter(seqLetterCount);
		entry.setLegal(seqLegalCount);
		entry.printSequence(ta);
		seqLetterCount = 0;
		seqLegalCount = 0;
	}
	
	void restart() {
		fileName = "";
		fileNameCleaned = "";
		fileExtens = "";
		totalLetterCount = 0;
		totalLegalCount = 0;
		totalPageCount = 0;
		seqLetterCount = 0;
		seqLegalCount = 0;
	}
	
	void createTestFile(JTextArea ta) { // Creates a PDF with 25 blank pages of mixed legal and letter.
		PDDocument doc = new PDDocument();
		String fileName = "test.pdf";
        String path = System.getProperty("user.dir");
		PDPage legalPage = new PDPage(PDRectangle.LEGAL);
		PDPage letterPage = new PDPage(PDRectangle.LETTER);
		PDPage my_array[] = {legalPage, letterPage};
		Random rnd = new Random();
		
		PDDocumentInformation info = doc.getDocumentInformation();
		info.setCreator("Creator name goes here");
		info.setCreationDate(Calendar.getInstance());
		
		int i = 0;
		while (i < 25) {
			doc.addPage(my_array[rnd.nextInt(my_array.length)]);
			i++;
		}
		try {
			doc.save(fileName);
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ta.append("Test PDF created in " + path);
		System.out.println("Test PDF created in " + path);
	}
	
	static void waitASec(int ms) {
		try {
			Thread.sleep(ms); // Sleep for a specified amount of milliseconds.
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Set interrupt flag to true.
			throw new RuntimeException(e);
		}
	}
}