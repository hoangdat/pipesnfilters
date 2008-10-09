/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullLayoutBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushLayoutBlockFilter;

/**
 *
 * @author AS
 */
public class LayoutBlockPipe extends Pipe implements PushLayoutBlockFilter, PullLayoutBlockFilter {
	
	/**
	 * 
	 */
	public LayoutBlockPipe() {
		
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe#setSink(at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.PushFilter)
	 */
	@Override
	public void setSink(PushFilter sink) {
		if( sink instanceof PushLayoutBlockFilter ) {
			super.setSink(sink);
		} else {
			throw new IllegalArgumentException("Excepted: PushLayoutBlockFilter");
		}
	}
	
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe#setSource(at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.PullFilter)
	 */
	@Override
	public void setSource(PullFilter source) {
		if( source instanceof PullLayoutBlockFilter ) {
			super.setSource(source);
		} else {
			throw new IllegalArgumentException("Excepted: PullLayoutBlockFilter");
		}
	}




	public void write(LayoutBlock layout) {
		((PushLayoutBlockFilter) m_sink).write(layout);
	}

	public LayoutBlock read() {
		return ((PullLayoutBlockFilter) m_source).read();
	}

}
