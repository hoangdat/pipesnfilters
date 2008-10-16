/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.Filter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 *
 * @author AS
 */
public class Pipeline {
	
	
	
	public void connect(Filter source, Filter sink, Object pipe) {
		((Pipe<DataElement>) pipe).setSource((PullFilter<DataElement>) source);
		((Pipe<DataElement>) pipe).setSink((PushFilter<DataElement>) sink);
		
		((Pipe<DataElement>) source).setSink((PushFilter<DataElement>) pipe);
		((Pipe<DataElement>) sink).setSource((PullFilter<DataElement>) pipe);
	}
	
	
	
	
	
}
