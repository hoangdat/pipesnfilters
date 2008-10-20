/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe;

import java.io.Flushable;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.Component;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.Pullable;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.Pushable;


/**
 * 
 * @author AS
 */
public class Pipe<T extends DataElement> implements Component, Flushable {
	
	protected Pullable<T> m_source;
	protected Pushable<T> m_sink;
	private boolean m_buffered;
	private DataElement m_EOS;
	
	private LinkedBlockingQueue<DataElement> m_buffer;
	
	public Pipe() {
		this(false);
	}

	public Pipe(boolean isBuffered) {
		m_buffered = isBuffered;
		m_source = null;
		m_sink = null;
		m_buffer = new LinkedBlockingQueue<DataElement>();
		
		m_EOS = new DataElement() {
			@Override
			public DataElement getFirst() {return null;}
			@Override
			public void insertBefore(DataElement element, DataElement newElement) {}
			@Override
			public String toString() {return null;}
		};
	}
	
	public Pushable<T> getSink() {
		return m_sink;
	}
	public void setSink(Pushable<T> sink) {
		m_sink = sink;
	}
	
	public Pullable<T> getSource() {
		return m_source;
	}
	public void setSource(Pullable<T> source) {
		m_source = source;
	}

	/**
	 * @return the buffered
	 */
	public boolean isBuffered() {
		return m_buffered;
	}

	/**
	 * @param buffered the buffered to set
	 */
	public void setBuffered(boolean buffered) {
		m_buffered = buffered;
	}
	
	public void write(T data) {
		if (m_buffered) {
			try {
				m_buffer.put(data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			m_sink.write(data);
		}
	}
	
	@Override
	public void flush() throws IOException {
		if( m_buffered ) {
			try {
				m_buffer.put(m_EOS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			m_sink.flush();
		}
	}
	
	public T read() {
		T result = null;
		if (m_buffered) {
			DataElement el = null;
			try {
				el = m_buffer.take();
				if( el != m_EOS ) {
					result = (T) el;
				} else {
					result = null;
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			result = m_source.read();
		}
		return result;
	}
}
