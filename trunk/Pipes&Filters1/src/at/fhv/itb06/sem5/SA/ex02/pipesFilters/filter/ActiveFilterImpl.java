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
	
	private boolean m_isActive;
	
	public ActiveFilterImpl() {
		super();
		m_isActive = false;
	}
	
	@Override
	public void run() {
		T2 curValue = null;
		m_isActive = true;
		
		
		// while we can read ourself, send data to sink
		do {
			curValue = super.read();
			
			if( curValue != null ) {
				m_sink.write(curValue);
			}
		} while( curValue != null );
		
		// no more data are available
		// flush the sink
		m_sink.flush();
		
		m_isActive = false;
	}

	
	
	
	@Override
	public void flush() {
		checkRight();
		super.flush();
	}
	
	@Override
	public T2 read() {
		checkRight();
		return super.read();
	}
	
	@Override
	public void write(T1 data) {
		checkRight();
		super.write(data);
	}

	protected void checkRight() {
		if( !m_isActive ) {
			throw new UnsupportedOperationException("This method is temporary deactivated.");
		}
	}
	
}
