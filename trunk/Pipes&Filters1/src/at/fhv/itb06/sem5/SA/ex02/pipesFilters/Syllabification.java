/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable.AsciiType;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.TextBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.Word;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.WordBreaker;

/**
 * Singleton
 * @author AS
 */
public class Syllabification {
	
	private static Syllabification m_sInstance = null;
	
	public static Syllabification getInstance() {
		if( m_sInstance == null ) {
			m_sInstance = new Syllabification();
		}
		return m_sInstance;
	}
	
	
	
	private String m_syllLetter = "-";
	private byte[] m_vowel = { 'a', 'e', 'i', 'o', 'u' };
	private int m_vowelLength = m_vowel.length;
	
	protected Syllabification() {
		
	}
	
	
	
	public List<TextBlock> breakWords(List<TextBlock> words, int maxLength, WordBreaker breakMode) {
		int curLength = 0;
		
		List<TextBlock> retValue = new LinkedList<TextBlock>();
		Word[] newWords = null;
		Iterator<TextBlock> iter = words.iterator();
		TextBlock curWord = null;
		
		while( iter.hasNext() ) {
			curWord = iter.next();
			if( curWord.getTextLength() > 0 ) {
				if( curLength < maxLength ) {
					newWords = breakWord((Word) curWord, maxLength - curLength, breakMode);
				} else {
					newWords = new Word[1];
					newWords[0] = (Word) curWord;
				}
				
				for( int k = 0; k < newWords.length; k++ ) {
					retValue.add(newWords[k]);
					curLength += newWords[k].getTextLength();
				}
				
			} else {
				retValue.add(curWord);
			}
		}
		
		return retValue;
	}
	
	public Word[] breakWord(Word word, int maxLength, WordBreaker breakMode) {
		Word[] words = null;
		
		switch (breakMode) {
		case HARD:
			words = breakModeHard(word, maxLength);
			break;
		case SOFT:
			words = breakModeSoft(word, maxLength);
			break;
		default:
			words = new Word[1];
			words[0] = word;
			break;
		}
		
		return words;
	}
	
	
	private Word[] breakModeSoft(Word word, int maxLength) {
		String text = word.getText();
		
		Word[] retValue = null;
		
		if( text.length() >= maxLength ) {
			
			byte[] w = text.getBytes();
			
			int vowelIndex = -1;
			int curIndex = maxLength -1;
			
			//curIndex -= m_syllLetter.length();
			
			
			
			while( curIndex >= 0 && vowelIndex == -1) {
				if( isVowel(w[curIndex]) ) {
					if( curIndex > 0 ) {
						int newIndex = curIndex - 1;
						if( !isVowel(w[newIndex]) ) {
							if( newIndex >= 2 ) {
								vowelIndex = newIndex;
							}
						}
					}
				}
				curIndex--;
			}
			
			if( vowelIndex > -1 ) {
				retValue = new Word[3];
				retValue[0] = new Word(word);
				retValue[0].setText(text.substring(0, vowelIndex));
				retValue[1] = new Word();
				retValue[1].setText(m_syllLetter);
				retValue[1].setBreakAfter(true);
				retValue[1].setType(AsciiType.ENDMARKER);
				retValue[1].setPrintAble(true);
				retValue[2] = new Word(word);
				retValue[2].setText(text.substring(vowelIndex));
			} else {
				retValue = new Word[1];
				retValue[0] = word;
			}
			
		} else {
			retValue = new Word[1];
			retValue[0] = word;
		}
		
		return retValue;
	}
	
	private boolean isVowel(byte b) {
		int i = 0;
		boolean vowelFound = false;
		while( vowelFound == false && i < m_vowelLength ) {
			vowelFound = ( b == m_vowel[i] ) ? true : false;
			i++;
		}
		return vowelFound;
	}
	
	private Word[] breakModeHard(Word word, int maxLength) {
		String text = word.getText();
		Word[] retValue = null;
		
		if( text.length() > maxLength ) {
			String textBegin = text.substring(0, maxLength - 1); 
			String textEnd = text.substring(maxLength - 1);
			
			retValue = new Word[3];
			retValue[0] = new Word(word);
			retValue[0].setText(textBegin);
			retValue[1] = new Word();
			retValue[1].setText(m_syllLetter);
			retValue[1].setBreakAfter(true);
			retValue[1].setType(AsciiType.ENDMARKER);
			retValue[1].setPrintAble(true);
			retValue[2] = new Word(word);
			retValue[2].setText(textEnd);
			
		} else if( text.length() == maxLength ) {
			if( !word.getBreakAfter() ) {
				String textBegin = text.substring(0, maxLength - 1); 
				String textEnd = text.substring(maxLength - 1);
				
				retValue = new Word[3];
				retValue[0] = new Word(word);
				retValue[0].setText(textBegin);
				retValue[1] = new Word();
				retValue[1].setText(m_syllLetter);
				retValue[1].setBreakAfter(true);
				retValue[1].setType(AsciiType.ENDMARKER);
				retValue[1].setPrintAble(true);
				retValue[2] = new Word(word);
				retValue[2].setText(textEnd);
			} else {
				// TODO: err newline
				System.out.println(text.length());
				retValue = new Word[1];
				retValue[0] = word;
			}
		} else {
			retValue = new Word[1];
			retValue[0] = word;
		}
		
		return retValue;
	}
	
}
