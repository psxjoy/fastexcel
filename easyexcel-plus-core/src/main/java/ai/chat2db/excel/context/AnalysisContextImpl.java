package ai.chat2db.excel.context;

import java.io.InputStream;
import java.util.List;

import ai.chat2db.excel.exception.ExcelAnalysisException;
import ai.chat2db.excel.read.metadata.ReadSheet;
import ai.chat2db.excel.read.metadata.ReadWorkbook;
import ai.chat2db.excel.read.metadata.holder.ReadHolder;
import ai.chat2db.excel.read.metadata.holder.ReadRowHolder;
import ai.chat2db.excel.read.metadata.holder.ReadSheetHolder;
import ai.chat2db.excel.read.metadata.holder.ReadWorkbookHolder;
import ai.chat2db.excel.read.metadata.holder.csv.CsvReadSheetHolder;
import ai.chat2db.excel.read.metadata.holder.csv.CsvReadWorkbookHolder;
import ai.chat2db.excel.read.metadata.holder.xls.XlsReadSheetHolder;
import ai.chat2db.excel.read.metadata.holder.xls.XlsReadWorkbookHolder;
import ai.chat2db.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import ai.chat2db.excel.read.metadata.holder.xlsx.XlsxReadWorkbookHolder;
import ai.chat2db.excel.read.processor.AnalysisEventProcessor;
import ai.chat2db.excel.read.processor.DefaultAnalysisEventProcessor;
import ai.chat2db.excel.support.ExcelTypeEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jipengfei
 */
@Slf4j
public class AnalysisContextImpl implements AnalysisContext {
    /**
     * The Workbook currently written
     */
    private ReadWorkbookHolder readWorkbookHolder;
    /**
     * Current sheet holder
     */
    private ReadSheetHolder readSheetHolder;
    /**
     * Current row holder
     */
    private ReadRowHolder readRowHolder;
    /**
     * Configuration of currently operated cell
     */
    private ReadHolder currentReadHolder;
    /**
     * Event processor
     */
    private final AnalysisEventProcessor analysisEventProcessor;

    public AnalysisContextImpl(ReadWorkbook readWorkbook, ExcelTypeEnum actualExcelType) {
        if (readWorkbook == null) {
            throw new IllegalArgumentException("Workbook argument cannot be null");
        }
        switch (actualExcelType) {
            case XLS:
                readWorkbookHolder = new XlsReadWorkbookHolder(readWorkbook);
                break;
            case XLSX:
                readWorkbookHolder = new XlsxReadWorkbookHolder(readWorkbook);
                break;
            case CSV:
                readWorkbookHolder = new CsvReadWorkbookHolder(readWorkbook);
                break;
            default:
                break;
        }
        currentReadHolder = readWorkbookHolder;
        analysisEventProcessor = new DefaultAnalysisEventProcessor();
        if (log.isDebugEnabled()) {
            log.debug("Initialization 'AnalysisContextImpl' complete");
        }
    }

    @Override
    public void currentSheet(ReadSheet readSheet) {
        switch (readWorkbookHolder.getExcelType()) {
            case XLS:
                readSheetHolder = new XlsReadSheetHolder(readSheet, readWorkbookHolder);
                break;
            case XLSX:
                readSheetHolder = new XlsxReadSheetHolder(readSheet, readWorkbookHolder);
                break;
            case CSV:
                readSheetHolder = new CsvReadSheetHolder(readSheet, readWorkbookHolder);
                break;
            default:
                break;
        }
        currentReadHolder = readSheetHolder;
        if (readWorkbookHolder.getHasReadSheet().contains(readSheetHolder.getSheetNo())) {
            throw new ExcelAnalysisException("Cannot read sheet repeatedly.");
        }
        readWorkbookHolder.getHasReadSheet().add(readSheetHolder.getSheetNo());
        if (log.isDebugEnabled()) {
            log.debug("Began to read：{}", readSheetHolder);
        }
    }

    @Override
    public ReadWorkbookHolder readWorkbookHolder() {
        return readWorkbookHolder;
    }

    @Override
    public ReadSheetHolder readSheetHolder() {
        return readSheetHolder;
    }

    @Override
    public ReadRowHolder readRowHolder() {
        return readRowHolder;
    }

    @Override
    public void readRowHolder(ReadRowHolder readRowHolder) {
        this.readRowHolder = readRowHolder;
    }

    @Override
    public ReadHolder currentReadHolder() {
        return currentReadHolder;
    }

    @Override
    public Object getCustom() {
        return readWorkbookHolder.getCustomObject();
    }

    @Override
    public AnalysisEventProcessor analysisEventProcessor() {
        return analysisEventProcessor;
    }

    @Override
    public List<ReadSheet> readSheetList() {
        return null;
    }

    @Override
    public void readSheetList(List<ReadSheet> readSheetList) {

    }

    @Override
    public ExcelTypeEnum getExcelType() {
        return readWorkbookHolder.getExcelType();
    }

    @Override
    public InputStream getInputStream() {
        return readWorkbookHolder.getInputStream();
    }

    @Override
    public Integer getCurrentRowNum() {
        return readRowHolder.getRowIndex();
    }

    @Override
    public Integer getTotalCount() {
        return readSheetHolder.getTotal();
    }

    @Override
    public Object getCurrentRowAnalysisResult() {
        return readRowHolder.getCurrentRowAnalysisResult();
    }

    @Override
    public void interrupt() {
        throw new ExcelAnalysisException("interrupt error");
    }
}
