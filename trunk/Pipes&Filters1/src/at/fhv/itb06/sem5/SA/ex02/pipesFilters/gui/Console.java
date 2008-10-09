/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.gui;

import java.io.FileNotFoundException;

import at.fhv.itb06.sem5.SA.ex02.pipesFilters.Pipeline;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.LayoutBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.data.TextBlock;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.ActiveFileReaderFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.ActivePagePrinterFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.AlignCenterFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.AlignLeftFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.AlignRightFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.AlignSpaceFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.LineFilter;
import at.fhv.itb06.sem5.SA.ex02.pipesFilters.filter.PageFilter;
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
	
	
	private Runnable pullFilter = null;
	private Runnable pushFilter = null;
	
	public void initialize() throws FileNotFoundException {
		int rowCount = 30;
		int lineLength = 80;
		Align align = Align.CENTER;
		
		
		Pipe<Integer> intPipe_1 = new Pipe<Integer>();
		Pipe<TextBlock> textPipe_1 = new Pipe<TextBlock>();
		Pipe<LayoutBlock> layoutPipe_2 = new Pipe<LayoutBlock>();
		Pipe<LayoutBlock> layoutPipe_3 = new Pipe<LayoutBlock>();
		Pipe<LayoutBlock> layoutPipe_4 = new Pipe<LayoutBlock>();
		
		
		ActiveFileReaderFilter charReaderFilter = new ActiveFileReaderFilter();
		Filter wordFilter = new WordFilter();
		LineFilter lineFilter = new LineFilter();
		PageFilter pageFilter = new PageFilter();
		ActivePagePrinterFilter pagePrinterFilter = new ActivePagePrinterFilter();
		
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
		
		
		lineFilter.setMaxLength(lineLength);
		alignFilter.setMaxLength(lineLength);
		pageFilter.setMaxRows(rowCount);
		
		charReaderFilter.setFilename("data/input.txt");
		
		pullFilter = pagePrinterFilter;
		pushFilter = charReaderFilter;
	}
	
	public Runnable doPush() {
		return pushFilter;
	}
	
	public Runnable doPull() {
		return pullFilter;
	}

}
