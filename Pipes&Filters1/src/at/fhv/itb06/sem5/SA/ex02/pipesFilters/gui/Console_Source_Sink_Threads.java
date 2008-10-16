/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.gui;

import java.io.FileNotFoundException;

/**
 *
 * @author AS
 */
public class Console_Source_Sink_Threads extends Console {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Console c = new Console_Source_Sink_Threads();
		c.initialize();
		
		c.getPipe(Pipes.LINE_ALIGN).setBuffered(true);
		(new Thread(c.getFilter(Filters.SINK))).start();
		(new Thread(c.getFilter(Filters.SOURCE))).start();
	}

}
