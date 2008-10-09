/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.ActiveFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullIntFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.IntPipe;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;


/**
 *
 * @author AS
 */
public class ActiveFileReaderFilter extends FilterImpl implements ActiveFilter, PullIntFilter {

	public static final int EOF = -1;
	
	private InputStream m_source;
	private IntPipe m_sink;
	
	public ActiveFileReaderFilter() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see asc2606.filter.FilterImpl#setSink(asc2606.pipe.Pipe)
	 */
	@Override
	public void setSink(Pipe sink) {
		super.setSink(sink);
		m_sink = (IntPipe) sink;
	}
	
	
	public void setFilename(String filename) throws FileNotFoundException {
		m_source = new FileInputStream(filename);
	}
	
	
	
	
	public void run() {
		
		int nextChar;
		
		try {
			
			// do while to send eof
			while( (nextChar = readCharacter()) != EOF ) {
				m_sink.write(nextChar);
			}
			m_sink.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			m_sink.flush();
		}
		
	}
	
	
	private int readCharacter() throws IOException {
		return m_source.read();
	}
	
	
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullIntFilter#read()
	 */
	@Override
	public int read() {
		int nextChar;
		try {
			nextChar = readCharacter();
		} catch (IOException e) {
			e.printStackTrace();
			nextChar = EOF;
		}
		return nextChar;
	}

	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.Component#flush()
	 */
	@Override
	public void flush() {
		m_sink.flush();
	}
	
	
}
