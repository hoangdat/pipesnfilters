/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 * @author AS
 * 
 * 
 *  Filter  ----T---- Pipe
 * 
 */
public interface Pullable<T extends DataElement> extends Filter {
	
	public Pipe<T> getSink();
	public void setSink(Pipe<T> sink);
	
	public T read();
	
}
