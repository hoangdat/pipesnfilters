/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.ActiveFilter;

/**
 *
 * @author AS
 */
public abstract class ActiveSourceFilterImpl<T extends DataElement> extends PassiveSourceFilterImpl<T> implements ActiveFilter {

	private boolean m_oldReadable;
	
	public ActiveSourceFilterImpl() {
		m_oldReadable = false;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		T curValue = null;
		m_oldReadable = m_readable;
		m_readable = false;
		
		// while we can read, send data to sink
		do {
			curValue = readNext();
			
			if( curValue != null ) {
				m_sink.write(curValue);
			}
		} while( curValue != null );
		
		// no data are available. flush the pipeline
		m_sink.flush();
		
		m_readable = m_oldReadable;
	}

}
