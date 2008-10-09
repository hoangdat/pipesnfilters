/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.gui;

import java.io.FileNotFoundException;

/**
 *
 * @author AS
 */
public class ConsolePush extends Console {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Console c = new ConsolePush();
		c.initialize();
		c.doPush().run();
	}

}
