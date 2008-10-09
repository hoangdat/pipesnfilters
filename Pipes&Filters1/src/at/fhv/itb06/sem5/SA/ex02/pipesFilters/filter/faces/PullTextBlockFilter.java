/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.TextBlock;

/**
 *
 * @author AS
 */
public interface PullTextBlockFilter extends PullFilter {
	
	public TextBlock read();
	
}
