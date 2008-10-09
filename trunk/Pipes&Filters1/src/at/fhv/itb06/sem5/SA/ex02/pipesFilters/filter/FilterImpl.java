/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.Component;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.Filter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 *
 * @author AS
 */
public abstract class FilterImpl implements Filter, Component {
	
	protected Pipe m_source;
	protected Pipe m_sink;
	
	
	protected FilterImpl() {
		m_source = null;
		m_sink = null;
	}
	
	public Pipe getSink() {
		return m_sink;
	}
	public void setSink(Pipe sink) {
		m_sink = sink;
	}
	
	public Pipe getSource() {
		return m_source;
	}
	public void setSource(Pipe source) {
		m_source = source;
	}
	
	
	
}
