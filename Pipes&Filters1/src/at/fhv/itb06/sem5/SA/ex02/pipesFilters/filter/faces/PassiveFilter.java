/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.DataElement;

/**
 * 
 * @author AS
 */
public interface PassiveFilter<T1 extends DataElement, T2 extends DataElement> extends PullFilter<T1>, PushFilter<T2> {
	
}
