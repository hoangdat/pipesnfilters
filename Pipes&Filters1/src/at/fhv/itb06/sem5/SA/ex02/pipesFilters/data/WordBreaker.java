/**
 * 
 */
package at.fhv.itb06.sem5.SA.ex02.pipesFilters.data;

/**
 *
 * @author AS
 */

/**
 * Note about breaks:
 * Soft-break: Splits a word by defined rules (syllabification)
 * Middle-break: Splits more words by splitting not-break-after words. (optional)
 * Hard-break: Splits a word after x-letters without taking care about syllabification)
 */
public enum WordBreaker { 
	
	NONE, // does not break words
	SOFT, // break words with syllabificaiton
	MIDDLE, // break words with ignoring the canBreakAfter flag
	HARD // breaks without any rule (syllabification)
}
