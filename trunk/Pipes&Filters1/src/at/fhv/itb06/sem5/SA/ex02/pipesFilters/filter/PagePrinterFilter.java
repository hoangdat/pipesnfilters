/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.PageLayout;

/**
 *
 * @author AS
 */
public class PagePrinterFilter extends ActiveSinkFilterImpl<PageLayout> {

	
	@Override
	protected void addInputValue(PageLayout newValue) {
		printPage(newValue);
	}
	
	@Override
	protected void flushInternal() {
		System.out.flush();
	}
	
	
	private void printPage(LayoutBlock page) {
		System.out.print(page.toString());
		System.out.println("[***************************]");
	}


}
