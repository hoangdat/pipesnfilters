/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.Pullable;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.Pushable;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 * @param T1: The push type (the incoming type)
 * @param T2: The pull type (the outgoing type)
 * @author AS
 * 
 * 
 * 
 * Pipe ----T1---- Filter ----T2---- Pipe
 */
public abstract class PassiveFilterImpl<T1 extends DataElement, T2 extends DataElement>
		implements Pullable<T2>, Pushable<T1> {
	
	protected Pipe<T1> m_source;
	protected Pipe<T2> m_sink;
	protected List<T2> m_outBuffer;
	protected boolean m_flushing;
	
	
	protected PassiveFilterImpl() {
		m_source = null;
		m_sink = null;
		m_outBuffer = new LinkedList<T2>();
		m_flushing = false;
	}
	
	
	//
	// set & gets the sink and source pipe
	//
	
	
	public Pipe<T2> getSink() {
		return m_sink;
	}
	public void setSink(Pipe<T2> sink) {
		m_sink = sink;
	}
	
	public Pipe<T1> getSource() {
		return m_source;
	}
	public void setSource(Pipe<T1> source) {
		m_source = source;
	}
	
	
	
	//
	// implementation of the push and pull methods
	//
	
	
	/**
	 * The read method reads as long data form the source,
	 * as long there is no value in the outBuffer or the source is null.
	 * If there is something in the outBuffer, the read method will return 
	 * the first value from this buffer.
	 */
	@Override
	public T2 read() {
		if( m_outBuffer.isEmpty() ) {
			T1 newValue = null;
			
			// if flushing is true, we read flush before
			// therefore we should not read the source again.
			if( !m_flushing ) {
				// read data form the source
				do {
					newValue = m_source.read();
					if( newValue != null ) {
						addInputValue(newValue);
					}
				} while( m_outBuffer.isEmpty() && newValue != null );
			}
			if( newValue == null ) {
				m_flushing = true;
				// the source does not have more data --> flush
				flushInternalToOutBuffer();
				if( m_outBuffer.isEmpty() ) {
					m_flushing = false;
					return null;
				}
			}
		}
		
		return m_outBuffer.remove(0);
	}


	@Override
	public void write(T1 data) {
		// add the new data to the filter-haendler
		addInputValue(data);
		
		// flush all created out-buffer elements
		flushOutBuffer();
	}
	
	@Override
	public void flush() throws IOException {
		// flush all internal caches to the outBuffer
		flushInternalToOutBuffer();
		
		// flush the outBuffer to the sink
		flushOutBuffer();
		
		// send the flush event to the sink
		m_sink.flush();
	}
	
	
	protected void flushOutBuffer() {
		while( !m_outBuffer.isEmpty() ) {
			m_sink.write(m_outBuffer.remove(0));
		}
	}
	
	/**
	 * The addInputValue method adds a new value to the filter, which should be handled.
	 * @param newValue The value, which should be added and handled by the filter.
	 */
	protected abstract void addInputValue(T1 newValue);

	/**
	 * The flushInternalToOutBuffer method should flush all internal cached values to the outBuffer.
	 * This method is called, when this filter receives a flush message.
	 */
	protected abstract void flushInternalToOutBuffer();
}
