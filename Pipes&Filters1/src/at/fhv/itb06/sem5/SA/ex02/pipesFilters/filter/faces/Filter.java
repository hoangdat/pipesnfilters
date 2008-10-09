/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces;

import java.io.Flushable;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.Component;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 * 
 * @author AS
 */
public interface Filter extends Component, PushFilter, PullFilter, Flushable {
	public Pipe getSink();
	public void setSink(Pipe sink);
	
	public Pipe getSource();
	public void setSource(Pipe source);
	
}
