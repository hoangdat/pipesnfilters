/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe;

import java.io.Flushable;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.Component;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushFilter;


/**
 * 
 * @author AS
 */
public class Pipe<T extends DataElement> implements Component, Flushable {
	
	protected PullFilter<T> m_source;
	protected PushFilter<T> m_sink;
	private boolean m_buffered;
	
	private LinkedBlockingQueue<T> m_buffer;
	
	public Pipe() {
		this(false);
	}

	public Pipe(boolean isBuffered) {
		m_buffered = isBuffered;
		m_source = null;
		m_sink = null;
		if (m_buffered) {
			m_buffer = new LinkedBlockingQueue<T>();
		}
	}
	
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipea#getSink()
	 */
	public PushFilter<T> getSink() {
		return m_sink;
	}
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipea#setSink(at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushFilter)
	 */
	public void setSink(PushFilter<T> sink) {
		m_sink = sink;
	}
	
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipea#getSource()
	 */
	public PullFilter<T> getSource() {
		return m_source;
	}
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipea#setSource(at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullFilter)
	 */
	public void setSource(PullFilter<T> source) {
		m_source = source;
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.Component#flush()
	 */
	@Override
	public void flush() {
		try {
			m_sink.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			m_buffer.add(data);
		} else {
			m_sink.write(data);
		}
	}
	
	public T read() {
		T result = null;
		if (m_buffered) {
			result = m_buffer.remove();
		} else {
			result = m_source.read();
		}
		return result;
	}
}