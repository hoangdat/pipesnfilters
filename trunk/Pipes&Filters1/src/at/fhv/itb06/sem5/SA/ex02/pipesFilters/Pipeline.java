/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.Filter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.Pullable;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.Pushable;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 *
 * @author AS
 */
public class Pipeline {
	
	
	
	public void connect(Filter source, Filter sink, Pipe pipe) {
		((Pipe<DataElement>) pipe).setSource((Pullable<DataElement>) source);
		((Pipe<DataElement>) pipe).setSink((Pushable<DataElement>) sink);
		
		((Pullable<DataElement>) source).setSink((Pipe<DataElement>) pipe);
		((Pushable<DataElement>) sink).setSource((Pipe<DataElement>) pipe);
	}
	
	
	
	
	
}
