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
public abstract class ActiveFilterImpl<T1 extends DataElement, T2 extends DataElement>
		extends PassiveFilterImpl<T1, T2>
		implements ActiveFilter {
	
	private boolean m_oldReadable = false;
	private boolean m_oldWriteable = false;
	
	@Override
	public void run() {
		T2 curValue = null;
		m_oldReadable = m_readable;
		m_oldWriteable = m_writeable;
		m_readable = false;
		m_writeable = false;
		
		
		// while we can read ourself, send data to sink
		do {
			curValue = this.readNext();
			
			if( curValue != null ) {
				m_sink.write(curValue);
			}
		} while( curValue != null );
		
		// no more data are available
		// flush the sink
		m_sink.flush();
		
		m_readable = m_oldReadable;
		m_writeable = m_oldWriteable;
	}

}
