/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.AsciiCharacter;


/**
 *
 * @author AS
 */
public class FileReaderFilter extends ActiveSourceFilterImpl<AsciiCharacter> {

	public static final int EOF = -1;
	
	private InputStream m_source;
	
	public FileReaderFilter() {
		super();
	}
	
	
	public void setFilename(String filename) throws FileNotFoundException {
		m_source = new FileInputStream(filename);
	}
	
	
	@Override
	protected AsciiCharacter readNext() {
		int nextChar;
		try {
			nextChar = m_source.read();
		} catch (IOException e) {
			e.printStackTrace();
			nextChar = EOF;
		}
		AsciiCharacter ac = AsciiCharacter.getCharacterInstance(nextChar);
		
		if( ac == null ) {
			try {
				m_source.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return ac;
	}	
	
}
