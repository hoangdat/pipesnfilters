/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.TextBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullTextBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushTextBlockFilter;

/**
 *
 * @author AS
 */
public class TextBlockPipe extends Pipe implements PushTextBlockFilter, PullTextBlockFilter {
	
	/**
	 * 
	 */
	public TextBlockPipe() {
		
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe#setSink(at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.PushFilter)
	 */
	@Override
	public void setSink(PushFilter sink) {
		if( sink instanceof PushTextBlockFilter ) {
			super.setSink(sink);
		} else {
			throw new IllegalArgumentException("Excepted: PushTextBlockFilter");
		}
	}
	
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe#setSource(at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.PullFilter)
	 */
	@Override
	public void setSource(PullFilter source) {
		if( source instanceof PullTextBlockFilter ) {
			super.setSource(source);
		} else {
			throw new IllegalArgumentException("Excepted: PullTextBlockFilter");
		}
	}




	public void write(TextBlock text) {
		((PushTextBlockFilter) m_sink).write(text);
	}

	public TextBlock read() {
		return ((PullTextBlockFilter) m_source).read();
	}

}
