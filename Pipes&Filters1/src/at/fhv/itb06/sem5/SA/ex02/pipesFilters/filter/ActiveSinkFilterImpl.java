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
	
	private boolean m_isActive;
	
	public ActiveSinkFilterImpl() {
		m_isActive = false;
	}
	
	@Override
	public void run() {
		T curValue = null;
		m_isActive = true;
		
		// while we can read ourself, send data to sink
		do {
			curValue = m_source.read();
			
			if( curValue != null ) {
				addInputValue(curValue);
			}
		} while( curValue != null );
		
		// no more data are available
		flushInternal();
		
		m_isActive = false;
	}
	
	@Override
	public void flush() {
		checkRight();
		super.flush();
	}
	
	@Override
	public void write(T data) {
		checkRight();
		super.write(data);
	}

	private void checkRight() {
		if( m_isActive ) {
			throw new UnsupportedOperationException("This method is temporarly deactivated.");
		}
	}
	
}
