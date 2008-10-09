/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LineLayout;


/**
 *
 * @author AS
 */
public class AlignLeftFilter extends AlignSpaceFilter {

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.AlignSpaceFilter#alignLine(at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LineLayout)
	 */
	@Override
	protected LineLayout alignLine(LineLayout line) {
		return line;
	}
	
}
