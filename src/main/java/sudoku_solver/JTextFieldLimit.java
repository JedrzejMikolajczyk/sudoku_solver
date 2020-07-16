/*
most of the code from:
http://www.java2s.com/Code/Java/Swing-JFC/LimitJTextFieldinputtoamaximumlength.htm

added allowedChars parameter so that only allowed characters might be used in a document
if allowedChars = null then all characters are allowed

*/
package sudoku_solver;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.util.List;

class JTextFieldLimit extends PlainDocument {
  private int limit;
  List<Character> allowedChars;
  JTextFieldLimit(int limit, List<Character> allowedChars) {
    super();
    this.limit = limit;
    this.allowedChars = allowedChars;
  }

  JTextFieldLimit(int limit, boolean upper) {
    super();
    this.limit = limit;
  }

  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
    if (str == null)
    return;

    if(allowedChars != null){
      for (char ch: str.toCharArray()) {
        if(!allowedChars.contains(ch))
          return;
        }
    }

    if ((getLength() + str.length()) <= limit) {
      super.insertString(offset, str, attr);
    }
    else if(str.length() >= limit){
      str = str.substring(0, limit);
      super.replace(0, limit, str, attr);
    }
    else{
      super.replace(0, limit, str, attr);
    }
  }
}