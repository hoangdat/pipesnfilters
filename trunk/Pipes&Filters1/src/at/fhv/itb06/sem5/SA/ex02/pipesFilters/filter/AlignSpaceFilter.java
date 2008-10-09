/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import java.util.LinkedList;
import java.util.List;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LineLayout;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullLayoutBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushLayoutBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;


/**
 *
 * @author AS
 */
public abstract class AlignSpaceFilter extends FilterImpl implements PushLayoutBlockFilter, PullLayoutBlockFilter {
	
	private PushLayoutBlockFilter m_sink;
	private PullLayoutBlockFilter m_source;
	protected List<LayoutBlock> m_outBuffer;
	protected int m_maxLength;
	
	/**
	 * 
	 */
	public AlignSpaceFilter() {
		m_outBuffer = new LinkedList<LayoutBlock>();
		m_maxLength = 0;
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
			LineLayout line = null;
			do {
				line = (LineLayout) m_source.read();
				if( line != null ) {
					addLine(line);
				}
			} while( m_outBuffer.isEmpty() && line != null );
			
			if( line == null ) {
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
	public void write(LayoutBlock line) {
		addLine((LineLayout) line);
		writeLines();
	}
	
	@Override
	public void flush() {
		writeLines();
		m_sink.flush();
	}
	
	/**
	 * 
	 */
	private void writeLines() {
		LayoutBlock lb;
		while( !m_outBuffer.isEmpty() ) {
			lb = m_outBuffer.iterator().next();
			m_sink.write(lb);
			m_outBuffer.remove(lb);
		}
	}


	private void addLine(LineLayout line) {
		line = alignLine(line);
		addLineToBuffer(line);
	}
	
	
	/**
	 * @param b
	 */
	private void addLineToBuffer(LineLayout line) {
		if( line != null ) {
			m_outBuffer.add(line);
		}
	}
	
	
	protected abstract LineLayout alignLine(LineLayout line);


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
	
}
