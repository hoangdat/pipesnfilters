/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;


import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LineLayout;


/**
 *
 * @author AS
 */
public abstract class AlignSpaceFilter extends ActiveFilterImpl<LineLayout, LineLayout> {

	@Override
	protected void addInputValue(LineLayout newValue) {
		newValue = alignLine(newValue);
		addLineToBuffer(newValue);
	}
	
	@Override
	protected void flushInternalToOutBuffer() {
		// no internal buffers
	}
	
	
	private void addLineToBuffer(LineLayout line) {
		if( line != null ) {
			m_outBuffer.add(line);
		}
	}
	
	
	protected abstract LineLayout alignLine(LineLayout line);
	
	

	
}
