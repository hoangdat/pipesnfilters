/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;


import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PassiveSink;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 *
 * @author AS
 */
public abstract class PassiveSinkFilterImpl<T extends DataElement> implements PassiveSink<T> {

	protected Pipe<T> m_source;
	
	public PassiveSinkFilterImpl() {
		m_source = null;
	}
	
	
	@Override
	public Pipe<T> getSource() {
		return m_source;
	}
	
	@Override
	public void setSource(Pipe<T> source) {
		m_source = source;
	}
	
	
	@Override
	public void write(T data) {
		addInputValue(data);
	}
	
	
	@Override
	public void flush() {
		flushInternal();
	}

	
	
	/**
	 * The addInputValue method adds a new value to the filter, which should be handled.
	 * @param newValue The value, which should be added and handled by the filter.
	 */
	protected abstract void addInputValue(T newValue);

	/**
	 * The flushInternalToOutBuffer method should flush all internal cached values to the outBuffer.
	 * This method is called, when this filter receives a flush message.
	 */
	protected abstract void flushInternal();
}
