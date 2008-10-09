/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.Syllabification;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable.AsciiType;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LineLayout;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.TextBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.Word;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.WordBreaker;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullLayoutBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullTextBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushLayoutBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PushTextBlockFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;


/**
 *
 * @author AS
 */
public class LineFilter extends FilterImpl implements PushTextBlockFilter, PullLayoutBlockFilter {
	
	
	private PushLayoutBlockFilter m_sink;
	private PullTextBlockFilter m_source;
	private int m_maxLength;
	private List<TextBlock> m_bufferWords;
	private List<TextBlock> m_sendWords; // the words, which can be sent
	private List<LineLayout> m_outBuffer; // these words can be sent
	private int m_curLengthSend; // the send length of the words
	private int m_curLengthBuffer; // the buffer length of the words
	private boolean m_isRecursive;
	private Syllabification m_syllabification;
	
	public LineFilter() {
		setMaxLength(80);
		m_sendWords = new LinkedList<TextBlock>();
		m_bufferWords = new LinkedList<TextBlock>();
		m_outBuffer = new LinkedList<LineLayout>();
		m_syllabification = Syllabification.getInstance();
		m_isRecursive = false;
		m_sink = null;
		m_source = null;
	}
	
	
	
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
		m_source = (PullTextBlockFilter) source;
	}

	/* (non-Javadoc)
	 * @see asc2606.filter.word.WordPushFilter#write(asc2606.filter.word.Word)
	 */
	@Override
	public void write(TextBlock word) {
		
		addTextBlock(word);
		
		writeLine();
	}


	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.PullLayoutBlockFilter#read()
	 */
	@Override
	public LayoutBlock read() {
		if( m_outBuffer.isEmpty() ) {
			TextBlock text;
			do {
				text = m_source.read();
				if( text != null ) {
					addTextBlock(text);
				}
			} while( m_outBuffer.isEmpty() && text != null );
			
			if( text == null ) {
				flushAll();
				if( m_outBuffer.isEmpty() ) {
					return null;
				}
			}
		}
		
		LayoutBlock lb = m_outBuffer.iterator().next();
		m_outBuffer.remove(lb);
		return lb;
	}
	
	
	/* (non-Javadoc)
	 * @see at.fhv.itb06.sem5.SA.ex02.pipesFilters.Component#flush()
	 */
	@Override
	public void flush() {
		flushAll();
		writeLine();
		
		m_sink.flush();
	}
	
	private void flushAll() {
		// if we reach this line, buffer + send should fit on one line
		moveBuffer(m_bufferWords.size());
		createLineAndMoveToOutBuffer(false);
	}

	private void writeLine() {
		while( !m_outBuffer.isEmpty() ) {
			LayoutBlock lb = m_outBuffer.iterator().next();
			((PushLayoutBlockFilter) m_sink).write(lb);
			m_outBuffer.remove(lb);
		}
	}
	
	private void addTextBlock(TextBlock text) {
		
		if( text.containsType(AsciiType.NEWLINE) ) {
			TextBlock[] blocks = text.splitByAsciiType(AsciiType.NEWLINE);
			for( int i = 0; i < blocks.length; i++ ) {
				
				m_curLengthBuffer += blocks[i].getTextLength();
				
				if( blocks[i].containsType(AsciiType.NEWLINE) ) {
					m_bufferWords.add(blocks[i]);
					handleBufferAndSend();
					createLineAndMoveToOutBuffer(false);
				} else {
					m_bufferWords.add(blocks[i]);
					handleBufferAndSend();
				}
				
			}
		} else {
			m_bufferWords.add(text);
			m_curLengthBuffer += text.getTextLength();
			handleBufferAndSend();
		}
	}
	

	/**
	 * @return Returns whether the buffer/sendWords has changed or not.
	 * This might be an important information to let the caller decide,
	 * what to do next.
	 */
	private boolean moveBufferToSend(WordBreaker breakMode) {
		
		boolean bufferChanged = false;
		int buffSize = m_bufferWords.size();
		
		if( buffSize > 0 ) {
			
			TextBlock lastWord = m_bufferWords.get(buffSize - 1);
			
			// we move the words, if the last can be moved
			if( lastWord.getBreakAfter() ) {
				// we can add the buffer to the sendWords
				if( m_curLengthSend + m_curLengthBuffer <= m_maxLength ) {
					
					moveBuffer(buffSize);
					
					bufferChanged = true;
				}
			}
			
			if( !bufferChanged ) {
				if( breakMode != WordBreaker.NONE ){
					// break soft
					
					List<TextBlock> wordList = m_syllabification.breakWords(m_bufferWords, m_maxLength - m_curLengthSend, breakMode);
					if( wordList.size() != m_bufferWords.size() ) {
						Iterator<TextBlock> wordIter = wordList.iterator();
						
						m_bufferWords.clear();
						m_curLengthBuffer = 0;
						TextBlock curWord;
						int newLength = 0;
						boolean oldRecursive = m_isRecursive;
						boolean canExecute = ( m_isRecursive ) ? false : true;
						
						m_isRecursive = true;
						
						while( wordIter.hasNext() ) {
							curWord = wordIter.next();
							m_bufferWords.add(curWord);
							m_curLengthBuffer += curWord.getTextLength();
							newLength = m_curLengthBuffer;
							
							if( canExecute ) {
								handleBufferAndSend(); // call this only once, syll is executed
								if( newLength < m_curLengthBuffer ) {
									canExecute = false;
								}
							}
						}
						m_isRecursive = oldRecursive;
						bufferChanged = true;
					} else {
						bufferChanged = false;
					}
				}
			}
		}
		
		return bufferChanged;
	}

	private void moveBuffer(int moveCount) {
		
		int i = 0;
		int curLength;
		TextBlock curWord;
		
		while( i < moveCount && !m_bufferWords.isEmpty() ) {
			// read data
			curWord = m_bufferWords.remove(0);
			curLength = curWord.getTextLength();
			
			// calculate/move new values
			m_curLengthSend += curLength;
			m_curLengthBuffer -= curLength;
			m_sendWords.add(curWord);
		}
	}


	private void handleBufferAndSend() {
		boolean bufferChanged = false;
		do {
			// just move the word, dont break any word
			bufferChanged = moveBufferToSend(WordBreaker.NONE);
			
			if( !bufferChanged && (m_curLengthSend + m_curLengthBuffer >= m_maxLength) && !m_isRecursive ) {
				// move the buffer to send, if it is possible
				// use soft mode break to split words
				bufferChanged = moveBufferToSend(WordBreaker.SOFT);
				
				if( bufferChanged ) {
					createLineAndMoveToOutBuffer(true);
				} else {
					// soft break was not possible
					// we do a middle break;
					bufferChanged = moveBufferToSend(WordBreaker.MIDDLE);
					if( !m_sendWords.isEmpty() ) {
						createLineAndMoveToOutBuffer(true);
						bufferChanged = true;
					}
					if( !bufferChanged && (m_curLengthBuffer >= m_maxLength) ) {
						// buffer can not be splitted
						// do a hard break to fit all letters on the page
						bufferChanged = moveBufferToSend(WordBreaker.HARD);
						createLineAndMoveToOutBuffer(true);
						// if bufferChanged == false ==> WordBreaker Exception... wrong implementation?
					}
				}
			} else if( m_curLengthSend == m_maxLength ) {
				createLineAndMoveToOutBuffer(true);
				bufferChanged = true;
			}
		} while( (m_curLengthBuffer >= m_maxLength || bufferChanged) && !m_isRecursive );
	}
	
	private void appendNewLine() {
		m_sendWords.add(createNewLine());
	}
	
	private TextBlock createNewLine() {
		Word word = new Word();
		
		word.setBreakAfter(false);
		word.setPrintAble(false);
		
		word.setText("" + (char) AsciiTable.NEWLINE_WIN[0] + (char) AsciiTable.NEWLINE_WIN[1]);
		word.setType(AsciiType.NEWLINE);
		word.setLayout(AsciiType.NEWLINE);
		
		return word;
	}
	
	private void createLineAndMoveToOutBuffer(boolean attachNewLine) {
		if( !m_sendWords.isEmpty() ) {
			if( attachNewLine ) {
				appendNewLine();
			}
			
			LineLayout line = new LineLayout(m_maxLength);
			Iterator<TextBlock> iter = m_sendWords.iterator();
			while( iter.hasNext() ) {
				line.addText(iter.next());
			}
			
			m_sendWords.clear();
			m_curLengthSend = 0;
			m_outBuffer.add(line);
		}
	}
	
	
	/**
	 * @return the maxLength
	 */
	public int getMaxLength() {
		return m_maxLength;
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(int maxLength) {
		m_maxLength = maxLength;
	}

	
	
}
