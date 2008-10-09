/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullIntFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushIntFilter;

/**
 *
 * @author AS
 */
public class IntPipe extends Pipe implements PushIntFilter, PullIntFilter {
	
	/**
	 * 
	 */
	public IntPipe() {
		
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe#setSink(at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.PushFilter)
	 */
	@Override
	public void setSink(PushFilter sink) {
		if( sink instanceof PushIntFilter ) {
			super.setSink(sink);
		} else {
			throw new IllegalArgumentException("Excepted: PushIntFilter");
		}
	}
	
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe#setSource(at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.PullFilter)
	 */
	@Override
	public void setSource(PullFilter source) {
		if( source instanceof PullIntFilter ) {
			super.setSource(source);
		} else {
			throw new IllegalArgumentException("Excepted: PullIntFilter");
		}
	}




	public void write(int character) {
		((PushIntFilter) m_sink).write(character);
	}

	public int read() {
		return ((PullIntFilter) m_source).read();
	}

}
