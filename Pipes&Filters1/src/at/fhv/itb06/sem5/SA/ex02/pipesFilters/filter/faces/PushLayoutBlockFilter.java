/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock;

/**
 *
 * @author AS
 */
public interface PushLayoutBlockFilter extends PushFilter {
	
	public void write(LayoutBlock text);
	
}
