/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.TextBlock;

/**
 *
 * @author AS
 */
public interface PushTextBlockFilter extends PushFilter {
	
	public void write(TextBlock text);
	
}
