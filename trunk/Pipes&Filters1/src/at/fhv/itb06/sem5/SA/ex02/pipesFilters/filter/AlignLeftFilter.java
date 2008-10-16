/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LineLayout;


/**
 * By default, every line is left-aligned. so we do not have to implement here anything
 * @author AS
 */
public class AlignLeftFilter extends AlignSpaceFilter {

	@Override
	protected LineLayout alignLine(LineLayout line) {
		return line;
	}
	
}
