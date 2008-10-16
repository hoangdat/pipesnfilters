/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.data;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.AsciiTable.AsciiType;

/**
 *
 * @author AS
 */
public class AsciiCharacter extends TextBlock {
	
	private static AsciiCharacter[] m_sCharacters = new AsciiCharacter[128];
	
	public static AsciiCharacter getCharacterInstance(int character) {
		if( character < 0 || character >= m_sCharacters.length ) {
			return null;
		}
		
		if( m_sCharacters[character] == null ) {
			m_sCharacters[character] = new AsciiCharacter(character);
		}
		return m_sCharacters[character];
	}
	
	
	private String m_character;
	private int m_characterValue;
	
	private AsciiCharacter(int charachter) {
		char[] c = new char[1];
		c[0] = (char) charachter;
		
		m_character = new String(c);
		m_characterValue = charachter;
	}
	
	@Override
	public boolean containsType(AsciiType newline) {
		return AsciiTable.getInstance().getType(m_characterValue) == newline;
	}
	
	@Override
	public boolean getBreakAfter() {
		return true;
	}
	
	@Override
	public int getTextLength() {
		if( AsciiTable.getInstance().isPrintable(AsciiTable.getInstance().getType(m_characterValue)) ) {
			return m_character.length();
		} else {
			return 0;
		}
	}
	
	@Override
	public TextBlock[] splitByAsciiType(AsciiType newline) {
		TextBlock[] tb = new TextBlock[1];
		tb[0] = this;
		return tb;
	}
	
	@Override
	public DataElement getFirst() {
		return this;
	}
	
	@Override
	public void insertBefore(DataElement element, DataElement newElement) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return m_character;
	}
	
	public AsciiType getType() {
		return AsciiTable.getInstance().getType(m_characterValue);
	}
	
	public char getAsciiValue() {
		return (char) m_characterValue;
	}

}
