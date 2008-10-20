/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import java.io.IOException;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.ActiveFilter;

/**
 *
 * @author AS
 */
public abstract class ActiveSourceFilterImpl<T extends DataElement> extends PassiveSourceFilterImpl<T> implements ActiveFilter {

	private boolean m_isActive;
	
	public ActiveSourceFilterImpl() {
		m_isActive = false;
	}
	
	
	@Override
	public void run() {
		T curValue = null;
		m_isActive = true;
		
		// while we can read, send data to sink
		do {
			curValue = super.read();
			
			if( curValue != null ) {
				m_sink.write(curValue);
			}
		} while( curValue != null );
		
		// no data are available. flush the pipeline
		try {
			m_sink.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		m_isActive = false;
	}
	
	@Override
	public T read() {
		checkRight();
		return super.read();
	}
	
	private void checkRight() {
		if( m_isActive ) {
			throw new UnsupportedOperationException("This method is temporarly deactivated.");
		}
	}

}
