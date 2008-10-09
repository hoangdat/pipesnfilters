/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.Filter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 *
 * @author AS
 */
public class Pipeline {
	
	
	
	public void connect(Filter source, Filter sink, Pipe pipe) {
		pipe.setSource(source);
		pipe.setSink(sink);
		
		source.setSink(pipe);
		sink.setSource(pipe);
	}
	
	
	
	
	
}
