# Letter and Legal PDF Sorter
Java application that reads a PDF file and displays the order of different page sizes for the purpose of printing legal documentation in *sequence stack* order.
This is especially helpful for title closers, notary publics, or others handling loan document PDFs containing mixed pages of both letter and legal sizes.

## Summary
The program reads in a PDF file, and begins placing the pages into a stack based on the page dimensions. If the size of the page changes 
from letter to legal or vice-versa, a new sequence is started of the opposite size. The stack sequence is logged in a table which is displayed 
after the entire PDF is sorted.

## How To Use
Select your PDF from the "File -> Open" option in the menu bar and the program will automatically attempt to sort it.
To sort another file just open it in the same way.
For alternate color themes you can scroll through them by clicking the "Themes" button.
The "Reset" button will restart the program, clearing the text area and defaulting the color theme.
You can create a PDF for testing purposes by selecting "Create Test File" from the "File" menu.
This will generate a blank PDF of 25 randomly mixed letter and legal sized pages in the program directory. 

![Image](/images/img02.jpg)

![Image](/images/img01.jpg)

## Credits
Developed by [Evan DeVizio](https://evandevizio.com)

Special thanks to:

[Oracle Corporation](https://www.oracle.com/index.html)

[Apache Software Foundation](https://www.apache.org/)

[Notary Rotary](https://www.notaryrotary.com/)
