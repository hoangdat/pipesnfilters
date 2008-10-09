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
public class PageLayout extends LayoutBlock {
	
	
	private List<LayoutBlock> m_pageEntries;
	private int m_maxRows;
	
	
	public PageLayout(int maxRows) {
		super();
		
		m_maxRows = maxRows;
		m_pageEntries = new LinkedList<LayoutBlock>();
	}
	
	
	public void addText(LayoutBlock text) {
		m_pageEntries.add(text);
	}


	/**
	 * @return the maxLength
	 */
	public int getMaxRows() {
		return m_maxRows;
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxRows(int maxRows) {
		m_maxRows = maxRows;
	}
	
	public int getCurrentRows() {
		return m_pageEntries.size();
	}


	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement#toString()
	 */
	@Override
	public String toString() {
		StringBuilder newString = new StringBuilder();
		Iterator<LayoutBlock> iter = m_pageEntries.iterator();
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
		return m_pageEntries.iterator().next();
	}


	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement#insertBefore(at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement, at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement)
	 */
	@Override
	public void insertBefore(DataElement element, DataElement newElement) {
		m_pageEntries.add(m_pageEntries.indexOf(element), (LayoutBlock) newElement);
	}
	
}
