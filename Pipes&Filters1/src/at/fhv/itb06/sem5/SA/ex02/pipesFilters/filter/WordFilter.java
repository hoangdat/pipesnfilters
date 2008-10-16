/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter;

import java.util.LinkedList;
import java.util.List;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable.AsciiType;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.AsciiCharacter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.Word;


/**
 *
 * @author AS
 */
public class WordFilter extends ActiveFilterImpl<AsciiCharacter, Word> {
	
	private static enum Parser {
		BEGIN, WORD, SPACE, WORD_DOT, MARKER_END_NEXT, MARKER_NEXT, NEWLINE
	};
	
	
	private Word m_word;
	
	private char m_curCharacter;
	private AsciiType m_curType;
	private Parser m_nextParser;
	private List<Word> m_list;
	
	
	public WordFilter() {
		super();
		
		m_nextParser = Parser.BEGIN;
		
		m_list = new LinkedList<Word>();
		
		resetWord();
	}
	
	
	
	@Override
	protected void addInputValue(AsciiCharacter newValue) {
		m_curType = newValue.getType();
		m_curCharacter = newValue.getAsciiValue();
		
		selectParser();
	}
	
	@Override
	protected void flushInternalToOutBuffer() {
		wordComplete();
	}
	
	
	
	private void resetWord() {
		m_word = null;
		m_nextParser = Parser.BEGIN;
	}
	private void createWord() {
		m_word = new Word();
	}
	
	private void wordComplete() {
		if( m_word != null ) {
			m_list.add(m_word);
		}
		resetWord();
	}
	
	
	
	/*********************
	 *   Parser
	 *********************/
	
	
	private void selectParser() {
		switch(m_nextParser) {
		case BEGIN: beginParser(); break;
		case WORD: wordParser(); break;
		case WORD_DOT: wordDotParser(); break;
		case SPACE: spaceParser(); break;
		case MARKER_NEXT: markerNextParser(); break;
		case MARKER_END_NEXT: markerEndNextParser(); break;
		case NEWLINE: newLineParser(); break;
		default:
			System.out.println("no parser selected.");
			//throw new Exception("No parser selected.");
		}
	}
	
	
	private void beginParser() {
		createWord();
		switch (m_curType) {
		case TEXT:
		case NUMBER:
			wordParser();
			break;
		case SPACE:
			spaceParser();
			break;
		case BEGINMARKER:
			markerBeginParser();
			break;
		case ENDMARKER:
			markerEndParser();
			break;
		case MARKER:
			markerParser();
			break;
		case NEWLINE:
			newLineParser();
			break;
		default:
			System.out.println("not implemented");
			break;
		}
	}


	private void wordParser() {
		switch( m_curType ) {
		case TEXT:
		case NUMBER:
			m_word.append(m_curCharacter);
			m_word.setType(m_curType);
			m_nextParser = Parser.WORD;
			break;
		case MARKER:
		case ENDMARKER:
			if( m_curCharacter == AsciiTable.DOT ) {
				m_word.append(m_curCharacter);
				m_nextParser = Parser.WORD_DOT;
			} else {
				m_word.setBreakAfter(false);
				wordComplete();
				
				beginParser();
			}
			break;
		default:
			wordComplete();
			
			beginParser();
		}
	}
	
	private void spaceParser() {
		switch( m_curType ) {
		case SPACE:
			m_word.append(m_curCharacter);
			m_word.setType(m_curType);
			m_word.setPrintAble(true);
			m_nextParser = Parser.SPACE;
			break;
		default:
			wordComplete();
			m_nextParser = Parser.BEGIN;
			
			beginParser();
		}
	}
	
	private void wordDotParser() {
		switch( m_curType ) {
		case TEXT:
		case NUMBER:
			m_word.append(m_curCharacter);
			m_nextParser = Parser.WORD;
			break;
		default:
			String curWord = m_word.getText();
			int dotIndex = curWord.length() - 1;
			m_word.setText(curWord.substring(0, dotIndex));
			m_word.setBreakAfter(false);
			wordComplete();
			
			m_word = new Word();
			m_word.setText(curWord.substring(dotIndex, dotIndex + 1));
			m_word.setBreakAfter(m_curType != AsciiType.ENDMARKER);
			m_word.setType(AsciiType.ENDMARKER);
			wordComplete();
			
			beginParser();
		}
	}
	
	private void markerBeginParser() {
		m_word.append(m_curCharacter);
		m_word.setType(m_curType);
		m_word.setBreakAfter(false);
		wordComplete();
	}
	
	private void markerEndParser() {
		m_word.append(m_curCharacter);
		m_word.setType(m_curType);
		m_nextParser = Parser.MARKER_END_NEXT;
	}
	
	private void markerEndNextParser() {

		switch( m_curType ) {
		case MARKER:
		case ENDMARKER:
			m_word.setBreakAfter(false);
			wordComplete();
			
			beginParser();
			break;
		default:
			wordComplete();
			beginParser();
		}
	}
	
	private void markerParser() {
		m_word.append(m_curCharacter);
		m_word.setType(m_curType);
		m_nextParser = Parser.MARKER_NEXT;
	}
	
	private void markerNextParser() {

		switch( m_curType ) {
		case MARKER:
		case ENDMARKER:
		case TEXT:
		case NUMBER:
			m_word.setBreakAfter(false);
			wordComplete();
			
			beginParser();
			break;
		default:
			wordComplete();
			
			beginParser();
		}
	}
	
	private void newLineParser() {
		switch( m_curType ) {
		case NEWLINE:
			m_word.setBreakAfter(true);
			m_word.setType(m_curType);
			
			byte[] bOld = m_word.getText().getBytes();
			if( bOld != null && bOld.length == 1 ) {
				if( bOld[0] == AsciiTable.NEWLINE_MAC && m_curCharacter == AsciiTable.NEWLINE_UNIX ) {
					m_word.append(m_curCharacter);
					wordComplete();
				} else {
					wordComplete();
					beginParser();
				}
			} else {
				m_word.append(m_curCharacter);
				m_nextParser = Parser.NEWLINE;
			}
			
			
			break;
		default:
			wordComplete();
			
			beginParser();
		}
	}
	
}
