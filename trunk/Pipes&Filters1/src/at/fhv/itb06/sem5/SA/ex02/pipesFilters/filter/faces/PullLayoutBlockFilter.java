/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock;

/**
 *
 * @author AS
 */
public interface PullLayoutBlockFilter extends PullFilter {
	
	public LayoutBlock read();
	
}
