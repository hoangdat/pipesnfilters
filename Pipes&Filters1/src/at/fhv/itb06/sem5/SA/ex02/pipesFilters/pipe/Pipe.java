/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.Component;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushFilter;


/**
 * 
 * @author AS
 */
public abstract class Pipe implements Component {
	
	protected PullFilter m_source;
	protected PushFilter m_sink;
	
	protected Pipe() {
		m_source = null;
		m_sink = null;
	}
	
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipea#getSink()
	 */
	public PushFilter getSink() {
		return m_sink;
	}
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipea#setSink(at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushFilter)
	 */
	public void setSink(PushFilter sink) {
		m_sink = sink;
	}
	
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipea#getSource()
	 */
	public PullFilter getSource() {
		return m_source;
	}
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipea#setSource(at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullFilter)
	 */
	public void setSource(PullFilter source) {
		m_source = source;
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.Component#flush()
	 */
	@Override
	public void flush() {
		m_sink.flush();
	}
	
	
	
}
