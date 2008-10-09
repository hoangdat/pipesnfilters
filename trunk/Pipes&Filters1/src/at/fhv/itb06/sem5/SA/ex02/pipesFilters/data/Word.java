/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.data;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable.AsciiType;


/**
 * @author AS
 *
 */
public class Word extends TextBlock {
	
	private String m_text;
	private boolean m_breakAfter;
	private boolean m_printAble;
	private AsciiType m_type;
	private AsciiType m_layout;
	
	public Word() {
		m_text = "";
		m_breakAfter = true;
		m_type = AsciiType.UNDEFINED;
		m_layout = AsciiType.UNDEFINED;
	}
	
	public Word(Word word) {
		m_text = word.m_text;
		m_breakAfter = word.m_breakAfter;
		m_printAble = word.m_printAble;
		m_type = word.m_type;
		m_layout = word.m_layout;
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return m_text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		m_text = text;
	}
	
	public void append(char character) {
		setText(getText() + character);
	}
	
	/**
	 * @return the breakAfter
	 */
	public boolean getBreakAfter() {
		return m_breakAfter;
	}

	/**
	 * @param breakAfter the breakAfter to set
	 */
	public void setBreakAfter(boolean breakAfter) {
		m_breakAfter = breakAfter;
	}

	/**
	 * @return the printAble
	 */
	public boolean isPrintAble() {
		return m_printAble;
	}

	/**
	 * @param printAble the printAble to set
	 */
	public void setPrintAble(boolean printAble) {
		m_printAble = printAble;
	}

	/**
	 * @return the type
	 */
	public AsciiType getType() {
		return m_type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(AsciiType type) {
		m_type = type;
		m_printAble = AsciiTable.getInstance().isPrintable(m_type);
	}
	
	public int getTextLength() {
		if( m_printAble ) {
			return m_text.length();
		} else {
			return 0;
		}
	}

	/**
	 * @return the layout
	 */
	public AsciiType getLayout() {
		return m_layout;
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(AsciiType layout) {
		m_layout = layout;
	}
	
	public String toString() {
		//if( m_printAble ) {
			return m_text;
		//}
		//return "";
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.TextBlock#containsType(at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable.AsciiType)
	 */
	@Override
	public boolean containsType(AsciiType newline) {
		return ( m_type == newline );
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.TextBlock#splitByAsciiType(at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable.AsciiType)
	 */
	@Override
	public TextBlock[] splitByAsciiType(AsciiType newline) {
		TextBlock[] tb = new TextBlock[1];
		tb[0] = this;
		return tb;
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement#getFirst()
	 */
	@Override
	public DataElement getFirst() {
		return this;
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement#insertBefore(at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement, at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement)
	 */
	@Override
	public void insertBefore(DataElement element, DataElement newElement) {
		m_text = ((Word) element).getText() + m_text;
	}
}
