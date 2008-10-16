/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;


import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PassiveSource;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 *
 * @author AS
 */
public abstract class PassiveSourceFilterImpl<T extends DataElement> implements PassiveSource<T> {

	protected Pipe<T> m_sink;
	protected boolean m_readable;
	
	public PassiveSourceFilterImpl() {
		m_sink = null;
		m_readable = true;
	}

	@Override
	public Pipe<T> getSink() {
		return m_sink;
	}
	
	@Override
	public void setSink(Pipe<T> sink) {
		m_sink = sink;
	}
	
	@Override
	public T read() {
		if( m_readable ) {
			return readNext();
		} else {
			throw new UnsupportedOperationException("This method is temporarly deactivated.");
		}
	}
	
	/**
	 * Reads the next Input value and returns it.
	 * @return
	 */
	protected abstract T readNext();
	
}
