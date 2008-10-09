/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.data;

/**
 *
 * @author AS
 */
public abstract class DataElement {
	
	public abstract String toString();

	public abstract DataElement getFirst();
	public abstract void insertBefore(DataElement element, DataElement newElement);
}
