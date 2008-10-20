/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.gui;

import java.io.FileNotFoundException;
import java.util.HashMap;


import at.fhv.itb06.sem5.SA.ex02.pipesFilters.Pipeline;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.AsciiCharacter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.TextBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.FileReaderFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.AlignCenterFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.AlignLeftFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.AlignRightFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.AlignSpaceFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.LineFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.PageFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.PagePrinterFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.WordFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.faces.Filter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.pipe.Pipe;

/**
 *
 * @author AS
 */
public abstract class Console {
	public enum Align {
		LEFT, CENTER, RIGHT
	};
	
	public enum Filters {
		SOURCE, SINK, WORD, LINE, ALIGN, PAGE
	};
	
	public enum Pipes {
		SOURCE_WORD, WORD_LINE, LINE_ALIGN, ALIGN_PAGE, PAGE_SINK
	};
	
	
	private HashMap<Filters, Filter> m_filters = null;
	private HashMap<Pipes, Pipe> m_pipes = null;
	
	public void initialize() throws FileNotFoundException {
		int rowCount = 30;
		int lineLength = 80;
		Align align = Align.CENTER;
		
		m_filters = new HashMap<Filters, Filter>();
		m_pipes = new HashMap<Pipes, Pipe>();
		
		
		Pipe<AsciiCharacter> intPipe_1 = new Pipe<AsciiCharacter>();
		Pipe<TextBlock> textPipe_1 = new Pipe<TextBlock>();
		Pipe<LayoutBlock> layoutPipe_2 = new Pipe<LayoutBlock>();
		Pipe<LayoutBlock> layoutPipe_3 = new Pipe<LayoutBlock>();
		Pipe<LayoutBlock> layoutPipe_4 = new Pipe<LayoutBlock>();
		
		
		Filter charReaderFilter = new FileReaderFilter();
		Filter wordFilter = new WordFilter();
		Filter lineFilter = new LineFilter();
		Filter pageFilter = new PageFilter();
		Filter pagePrinterFilter = new PagePrinterFilter();
		
		AlignSpaceFilter alignFilter = null;
		switch (align) {
		case RIGHT:
			alignFilter = new AlignRightFilter();
			break;
		case CENTER:
			alignFilter = new AlignCenterFilter();
			break;
		default:
			alignFilter = new AlignLeftFilter();
			break;
		}
		
		Pipeline p = new Pipeline();
		
		
		p.connect(charReaderFilter, wordFilter, intPipe_1);
		p.connect(wordFilter, lineFilter, textPipe_1);
		p.connect(lineFilter, alignFilter, layoutPipe_2);
		p.connect(alignFilter, pageFilter, layoutPipe_3);
		p.connect(pageFilter, pagePrinterFilter, layoutPipe_4);
		
		
		((LineFilter) lineFilter).setMaxLength(lineLength);
		((PageFilter) pageFilter).setMaxRows(rowCount);
		((FileReaderFilter) charReaderFilter).setFilename("data/input.txt");
		
		
		m_filters.put(Filters.SOURCE, charReaderFilter);
		m_filters.put(Filters.WORD, wordFilter);
		m_filters.put(Filters.LINE, lineFilter);
		m_filters.put(Filters.ALIGN, alignFilter);
		m_filters.put(Filters.PAGE, pageFilter);
		m_filters.put(Filters.SINK, pagePrinterFilter);
		
		m_pipes.put(Pipes.SOURCE_WORD, intPipe_1);
		m_pipes.put(Pipes.WORD_LINE, textPipe_1);
		m_pipes.put(Pipes.LINE_ALIGN, layoutPipe_2);
		m_pipes.put(Pipes.ALIGN_PAGE, layoutPipe_3);
		m_pipes.put(Pipes.PAGE_SINK, layoutPipe_4);
		
	}
	
	public Runnable getFilter(Filters type) {
		return (Runnable) m_filters.get(type);
	}
	
	public Pipe getPipe(Pipes type) {
		return m_pipes.get(type);
	}

}
