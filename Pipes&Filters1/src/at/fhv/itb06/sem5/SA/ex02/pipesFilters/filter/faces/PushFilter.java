/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces;

import java.io.Flushable;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 * @author AS
 * 
 * 
 * Pipe ----T---- Filter
 * 
 */
public interface PushFilter<T extends DataElement> extends Filter, Flushable {
	
	public Pipe<T> getSource();
	public void setSource(Pipe<T> source);
	
	public void write(T data);
}
