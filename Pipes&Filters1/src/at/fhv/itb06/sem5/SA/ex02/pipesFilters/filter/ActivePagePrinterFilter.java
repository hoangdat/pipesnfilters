/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullLayoutBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushLayoutBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 *
 * @author AS
 */
public class ActivePagePrinterFilter extends FilterImpl implements PushLayoutBlockFilter, PullLayoutBlockFilter, Runnable {
	
	
	private PullLayoutBlockFilter m_source;
	private PushLayoutBlockFilter m_sink;
	
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.FilterImpl#setSink(at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe)
	 */
	@Override
	public void setSink(Pipe sink) {
		super.setSink(sink);
		m_sink = (PushLayoutBlockFilter) sink;
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.FilterImpl#setSource(at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe)
	 */
	@Override
	public void setSource(Pipe source) {
		super.setSource(source);
		m_source = (PullLayoutBlockFilter) source;
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushLayoutBlockFilter#write(at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock)
	 */
	@Override
	public void write(LayoutBlock page) {
		printPage(page);
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.Component#flush()
	 */
	@Override
	public void flush() {
		System.out.flush();
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullLayoutBlockFilter#read()
	 */
	@Override
	public LayoutBlock read() {
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LayoutBlock page;
		while( (page = m_source.read()) != null ) {
			printPage(page);
		}
	}
	
	
	private void printPage(LayoutBlock page) {
		System.out.print(page.toString());
		System.out.println("[***************************]");
	}


}
