/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.data;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable.AsciiType;


/**
 *
 * @author AS
 */
public abstract class TextBlock extends DataElement {
	
	public abstract int getTextLength();
	public abstract boolean getBreakAfter();
	public abstract boolean containsType(AsciiType newline);
	public abstract TextBlock[] splitByAsciiType(AsciiType newline);
	
}
