/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.PageLayout;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullLayoutBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushLayoutBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;


/**
 *
 * @author AS
 */
public class PageFilter extends FilterImpl implements PushLayoutBlockFilter, PullLayoutBlockFilter {
	
	
	private PullLayoutBlockFilter m_source;
	private PushLayoutBlockFilter m_sink;
	
	private int m_maxRows;
	private List<LayoutBlock> m_outBuffer;
	private List<LayoutBlock> m_curBuffer;
	
	/**
	 * 
	 */
	public PageFilter() {
		m_outBuffer = new LinkedList<LayoutBlock>();
		m_curBuffer = new LinkedList<LayoutBlock>();
		setMaxRows(30);
		
	}

	
	@Override
	public void setSink(Pipe sink) {
		super.setSink(sink);
		m_sink = (PushLayoutBlockFilter) sink;
	}
	
	@Override
	public void setSource(Pipe source) {
		super.setSource(source);
		m_source = (PullLayoutBlockFilter) source;
	}
	
	

	
	@Override
	public LayoutBlock read() {
		if( m_outBuffer.isEmpty() ) {
			LayoutBlock line = null;
			do {
				line = m_source.read();
				if( line != null ) {
					addLine(line);
				}
			} while( m_outBuffer.isEmpty() && line != null );
			
			if( line == null ) {
				flushAll();
				if( m_outBuffer.isEmpty() ) {
					return null;
				}
			}
		}
		
		LayoutBlock lb = m_outBuffer.iterator().next();
		m_outBuffer.remove(lb);
		return lb;
	}
	
	@Override
	public void write(LayoutBlock layout) {
		addLine(layout);
		writePages();
	}


	@Override
	public void flush() {
		flushAll();
		writePages();
		
		m_sink.flush();
	}



	private void flushAll() {
		// if we reach this line, buffer + send should fit on one line
		while( !m_curBuffer.isEmpty() ) {
			createPage();
		}
	}
	
	
	private void writePages() {
		while( !m_outBuffer.isEmpty() ) {
			LayoutBlock lb = m_outBuffer.iterator().next();
			((PushLayoutBlockFilter) m_sink).write(lb);
			m_outBuffer.remove(lb);
		}
	}




	
	/**
	 * @param layout
	 */
	private void addLine(LayoutBlock layout) {
		m_curBuffer.add(layout);
		if( m_curBuffer.size() == m_maxRows ) {
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

}
