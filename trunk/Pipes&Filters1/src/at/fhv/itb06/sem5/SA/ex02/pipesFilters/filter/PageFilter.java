/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.PageLayout;


/**
 *
 * @author AS
 */
public class PageFilter extends ActiveFilterImpl<LayoutBlock, PageLayout> {
	
	private int m_maxRows;
	private List<LayoutBlock> m_curBuffer;
	
	/**
	 * 
	 */
	public PageFilter() {
		
		m_maxRows = 30;
		m_curBuffer = new LinkedList<LayoutBlock>();
		
	}
	
	
	
	
	
	/**
	 * @return the maxRows
	 */
	public int getMaxRows() {
		return m_maxRows;
	}

	/**
	 * @param maxRows the maxRows to set
	 */
	public void setMaxRows(int maxRows) {
		m_maxRows = maxRows;
	}
	
	
	
	@Override
	protected void addInputValue(LayoutBlock newValue) {
		m_curBuffer.add(newValue);
		if( m_curBuffer.size() == m_maxRows ) {
			createPage();
		}
	}
	
	@Override
	protected void flushInternalToOutBuffer() {
		// if we reach this line, buffer + send should fit on one line
		while( !m_curBuffer.isEmpty() ) {
			createPage();
		}
	}
	
	
	/**
	 * 
	 */
	private void createPage() {
		PageLayout page = new PageLayout(m_maxRows);
		
		Iterator<LayoutBlock> iter = m_curBuffer.iterator();
		
		while( iter.hasNext() ) {
			page.addText(iter.next());
		}
		
		m_outBuffer.add(page);
		m_curBuffer.clear();
	}

}
