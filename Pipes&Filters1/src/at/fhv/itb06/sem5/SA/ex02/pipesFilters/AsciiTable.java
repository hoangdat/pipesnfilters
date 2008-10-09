/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters;

/**
 *
 * @author AS
 */
public class AsciiTable {
	
	
	public static enum AsciiType {
		TEXT, NUMBER, SPACE, NEWLINE, NEWPAGE, CONTROLL, MARKER, BEGINMARKER, ENDMARKER, UNDEFINED
	};
	
	
	public static final int EOF = -1;
	
	public static final byte NEWLINE_UNIX = 0xA;
	public static final byte NEWLINE_MAC = 0xD;
	public static final byte[] NEWLINE_WIN = { 0xD, 0xA };
	public static final byte DOT = 0x2E;
	
	
	private static AsciiTable m_sInstance;
	
	public static AsciiTable getInstance() {
		if( m_sInstance == null ) {
			m_sInstance = new AsciiTable();
		}
		return m_sInstance;
	}
	
	
	private AsciiTable() {
	}
	
	
	public AsciiType getType(int character) {
		
		AsciiType type = AsciiType.UNDEFINED;
		
		switch(character) {
		case 0x0:
		case 0x1:
		case 0x2:
		case 0x3:
		case 0x4:
		case 0x5:
		case 0x6:
		case 0x7:
		case 0x8: type = AsciiType.CONTROLL; break;
		case 0x9: type = AsciiType.SPACE; break;		// TAB
		case 0xA: type = AsciiType.NEWLINE; break;		// NL
		case 0xB: type = AsciiType.SPACE; break;		// VTAB
		case 0xC: type = AsciiType.NEWPAGE; break;		// New Page
		case 0xD: type = AsciiType.NEWLINE; break;		// CR
		case 0xE:
		case 0xF:
		case 0x10:
		case 0x11:
		case 0x12:
		case 0x13:
		case 0x14:
		case 0x15:
		case 0x16:
		case 0x17:
		case 0x18:
		case 0x19:
		case 0x1A:
		case 0x1B:
		case 0x1C:
		case 0x1D:
		case 0x1E:
		case 0x1F: type = AsciiType.CONTROLL; break;
		case 0x20: type = AsciiType.SPACE; break;	// SPACE
		case 0x21: type = AsciiType.ENDMARKER; break;	// !
		case 0x22: type = AsciiType.MARKER; break;	// "
		case 0x23: type = AsciiType.MARKER; break;	// #
		case 0x24: type = AsciiType.MARKER; break;	// $
		case 0x25: type = AsciiType.MARKER; break;	// %
		case 0x26: type = AsciiType.MARKER; break;	// &
		case 0x27: type = AsciiType.MARKER; break;	// '
		case 0x28: type = AsciiType.BEGINMARKER; break;	// (
		case 0x29: type = AsciiType.ENDMARKER; break;	// )
		case 0x2A: type = AsciiType.BEGINMARKER; break;	// *
		case 0x2B: type = AsciiType.BEGINMARKER; break;	// +
		case 0x2C: type = AsciiType.ENDMARKER; break;	// ,
		case 0x2D: type = AsciiType.BEGINMARKER; break;	// -
		case 0x2E: type = AsciiType.ENDMARKER; break;	// .
		case 0x2F: type = AsciiType.MARKER; break;	// /
		case 0x30:										// 0
		case 0x31:
		case 0x32:
		case 0x33:
		case 0x34:
		case 0x35:
		case 0x36:
		case 0x37:
		case 0x38:
		case 0x39: type = AsciiType.NUMBER; break;		// 9
		case 0x3A: type = AsciiType.ENDMARKER; break;	// :
		case 0x3B: type = AsciiType.ENDMARKER; break;	// ;
		case 0x3C: type = AsciiType.BEGINMARKER; break;	// <
		case 0x3D: type = AsciiType.MARKER; break;		// =
		case 0x3E: type = AsciiType.ENDMARKER; break;	// >
		case 0x3F: type = AsciiType.ENDMARKER; break;	// ?
		case 0x40: type = AsciiType.MARKER; break;		// @
		case 0x41:										// A
		case 0x42:
		case 0x43:
		case 0x44:
		case 0x45:
		case 0x46:
		case 0x47:
		case 0x48:
		case 0x49:
		case 0x4A:
		case 0x4B:
		case 0x4C:
		case 0x4D:
		case 0x4E:
		case 0x4F:
		case 0x50:
		case 0x51:
		case 0x52:
		case 0x53:
		case 0x54:
		case 0x55:
		case 0x56:
		case 0x57:
		case 0x58:
		case 0x59:
		case 0x5A: type = AsciiType.TEXT; break;		// Z
		case 0x5B: type = AsciiType.BEGINMARKER; break;	// [
		case 0x5C: type = AsciiType.MARKER; break;		// \
		case 0x5D: type = AsciiType.ENDMARKER; break;	// ]
		case 0x5E: type = AsciiType.MARKER; break;		// ^
		case 0x5F: type = AsciiType.MARKER; break;		// _
		case 0x60: type = AsciiType.MARKER; break;		// `
		case 0x61:										// a
		case 0x62:
		case 0x63:
		case 0x64:
		case 0x65:
		case 0x66:
		case 0x67:
		case 0x68:
		case 0x69:
		case 0x6A:
		case 0x6B:
		case 0x6C:
		case 0x6D:
		case 0x6E:
		case 0x6F:
		case 0x70:
		case 0x71:
		case 0x72:
		case 0x73:
		case 0x74:
		case 0x75:
		case 0x76:
		case 0x77:
		case 0x78:
		case 0x79:
		case 0x7A: type = AsciiType.TEXT; break;		// z
		case 0x7B: type = AsciiType.BEGINMARKER; break;	// {
		case 0x7C: type = AsciiType.MARKER; break;		// |
		case 0x7D: type = AsciiType.ENDMARKER; break;	// }
		case 0x7E: type = AsciiType.MARKER; break;		// ~
		case 0x7F: type = AsciiType.CONTROLL; break;	// DEL
			
		default: break;
		}
		
		return type;
	}
	
	
	public boolean isPrintable(AsciiType type) {
		
		boolean retValue = false;
		
		switch (type) {
		case BEGINMARKER:
		case ENDMARKER:
		case MARKER:
		case TEXT:
		case NUMBER:
		case SPACE:
			retValue = true;
			break;
		case UNDEFINED:
		case CONTROLL:
		default:
			retValue = false;
			break;
		}
		
		return retValue;
	}
	
}
