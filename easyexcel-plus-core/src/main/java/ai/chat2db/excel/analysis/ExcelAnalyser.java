package ai.chat2db.excel.analysis;

import java.util.List;

import ai.chat2db.excel.read.metadata.ReadSheet;
import ai.chat2db.excel.context.AnalysisContext;

/**
 * Excel file analyser
 *
 * @author jipengfei
 */
public interface ExcelAnalyser {
    /**
     * parse the sheet
     *
     * @param readSheetList
     *            Which sheets you need to read.
     * @param readAll
     *            The <code>readSheetList</code> parameter is ignored, and all sheets are read.
     */
    void analysis(List<ReadSheet> readSheetList, Boolean readAll);

    /**
     * Complete the entire read file.Release the cache and close stream
     */
    void finish();

    /**
     * Acquisition excel executor
     *
     * @return Excel file Executor
     */
    ExcelReadExecutor excelExecutor();

    /**
     * get the analysis context.
     *
     * @return analysis context
     */
    AnalysisContext analysisContext();

}
