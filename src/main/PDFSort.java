package main;

/**
 * The "Letter and Legal PDF Sorter" program implements an application that reads a PDF file 
 * and displays the order in which to stack pages of the same size in sequence. This is especially helpful
 * for title closers and notary publics who may encounter loan document files containing pages of both letter and legal sizes.
 * 
 * @author Evan DeVizio
 * @version 1.0
 * @since 2020-07-06
 *
 */

public class PDFSort {
	
	public static void main(String[] args) {
		Operations ops = new Operations();
		Gui gui = new Gui();
		gui.CreateGui(ops);
	}
}
