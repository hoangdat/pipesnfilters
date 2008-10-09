/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author AS
 */
public class LineLayout extends LayoutBlock {
	
	
	private List<TextBlock> m_lineEntries;
	private int m_maxLength;
	
	
	public LineLayout(int maxLength) {
		super();
		
		m_maxLength = maxLength;
		m_lineEntries = new LinkedList<TextBlock>();
	}
	
	
	public void addText(TextBlock text) {
		m_lineEntries.add(text);
	}


	/**
	 * @return the maxLength
	 */
	public int getMaxLength() {
		return m_maxLength;
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(int maxLength) {
		m_maxLength = maxLength;
	}
	
	public int getCurrentLength() {
		int curLength = 0;
		Iterator<TextBlock> iter = m_lineEntries.iterator();
		while( iter.hasNext() ) {
			curLength += iter.next().getTextLength();
		}
		return curLength;
	}


	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement#toString()
	 */
	@Override
	public String toString() {
		StringBuilder newString = new StringBuilder();
		Iterator<TextBlock> iter = m_lineEntries.iterator();
		while( iter.hasNext() ) {
			newString.append(iter.next().toString());
		}
		return newString.toString();
	}


	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement#getFirst()
	 */
	@Override
	public DataElement getFirst() {
		return m_lineEntries.iterator().next();
	}


	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement#insertBefore(at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement, at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement)
	 */
	@Override
	public void insertBefore(DataElement element, DataElement newElement) {
		m_lineEntries.add(m_lineEntries.indexOf(element), (TextBlock) newElement);
	}
	
}
