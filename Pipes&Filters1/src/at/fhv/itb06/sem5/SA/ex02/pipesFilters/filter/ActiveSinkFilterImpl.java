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
public abstract class ActiveSinkFilterImpl<T extends DataElement> extends PassiveSinkFilterImpl<T> implements ActiveFilter {
	
	private boolean m_oldWriteable;
	
	public ActiveSinkFilterImpl() {
		m_oldWriteable = false;
	}
	
	@Override
	public void run() {
		T curValue = null;
		m_oldWriteable = m_writeable;
		m_writeable = false;
		
		// while we can read ourself, send data to sink
		do {
			curValue = m_source.read();
			
			if( curValue != null ) {
				addInputValue(curValue);
			}
		} while( curValue != null );
		
		// no more data are available
		flushInternal();
		
		m_writeable = m_oldWriteable;
	}

}
