/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable.AsciiType;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LineLayout;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.TextBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.Word;



/**
 *
 * @author AS
 */
public class AlignRightFilter extends AlignSpaceFilter {
	
	
	@Override
	protected LineLayout alignLine(LineLayout line) {
		
		int maxLength = line.getMaxLength();
		int curLength = line.getCurrentLength();
		
		int diffLength = Math.max(maxLength - curLength, 0);
		
		TextBlock layoutFormatter = createSpaceLayout(diffLength);
		
		line.insertBefore(line.getFirst(), layoutFormatter);
		
		return line;
	}
	
	
	/**
	 * Creates space characters to align the line on the right side.
	 * @param spaceLength The count of the spaces.
	 * @return
	 */
	private TextBlock createSpaceLayout(int spaceLength) {
		StringBuilder s = new StringBuilder();
		for( int i = 0; i < spaceLength; i++ ) {
			s.append(' ');
		}
		
		Word word = new Word();
		word.setBreakAfter(true);
		word.setPrintAble(true);
		word.setText(s.toString());
		word.setType(AsciiType.SPACE);
		word.setLayout(AsciiType.SPACE);
		
		return word;
	}

}
