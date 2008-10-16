/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.gui;

import java.io.FileNotFoundException;

/**
 *
 * @author AS
 */
public class Console_Source_Push extends Console {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Console c = new Console_Source_Push();
		c.initialize();
		c.getFilter(Filters.SINK).run();
	}

}
