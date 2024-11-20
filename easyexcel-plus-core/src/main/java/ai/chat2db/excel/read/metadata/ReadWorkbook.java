package ai.chat2db.excel.read.metadata;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Set;

import javax.xml.parsers.SAXParserFactory;

import ai.chat2db.excel.cache.ReadCache;
import ai.chat2db.excel.cache.selector.ReadCacheSelector;
import ai.chat2db.excel.cache.selector.SimpleReadCacheSelector;
import ai.chat2db.excel.enums.CellExtraTypeEnum;
import ai.chat2db.excel.enums.ReadDefaultReturnEnum;
import ai.chat2db.excel.event.AnalysisEventListener;
import ai.chat2db.excel.context.AnalysisContext;
import ai.chat2db.excel.read.listener.ModelBuildEventListener;
import ai.chat2db.excel.support.ExcelTypeEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Workbook
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class ReadWorkbook extends ReadBasicParameter {
    /**
     * Excel type
     */
    private ExcelTypeEnum excelType;
    /**
     * Read InputStream
     * <p>
     * If 'inputStream' and 'file' all not empty, file first
     */
    private InputStream inputStream;
    /**
     * Read file
     * <p>
     * If 'inputStream' and 'file' all not empty, file first
     */
    private File file;
    /**
     * charset.
     * Only work on the CSV file
     */
    private Charset charset;
    /**
     * Mandatory use 'inputStream' .Default is false.
     * <p>
     * if false, Will transfer 'inputStream' to temporary files to improve efficiency
     */
    private Boolean mandatoryUseInputStream;
    /**
     * Default true
     */
    private Boolean autoCloseStream;
    /**
     * This object can be read in the Listener {@link AnalysisEventListener#invoke(Object, AnalysisContext)}
     * {@link AnalysisContext#getCustom()}
     */
    private Object customObject;
    /**
     * A cache that stores temp data to save memory.
     */
    private ReadCache readCache;
    /**
     * Ignore empty rows.Default is true.
     */
    private Boolean ignoreEmptyRow;
    /**
     * Select the cache.Default use {@link SimpleReadCacheSelector}
     */
    private ReadCacheSelector readCacheSelector;
    /**
     * Whether the encryption
     */
    private String password;
    /**
     * SAXParserFactory used when reading xlsx.
     * <p>
     * The default will automatically find.
     * <p>
     * Please pass in the name of a class ,like : "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl"
     *
     * @see SAXParserFactory#newInstance()
     * @see SAXParserFactory#newInstance(String, ClassLoader)
     */
    private String xlsxSAXParserFactoryName;
    /**
     * Whether to use the default listener, which is used by default.
     * <p>
     * The {@link ModelBuildEventListener} is loaded by default to convert the object.
     * defualt is true.
     */
    private Boolean useDefaultListener;

    /**
     * Read not to {@code ai.chat2db.excel.metadata.BasicParameter#clazz} value, the default will return type.
     * Is only effective when set `useDefaultListener=true` or `useDefaultListener=null`.
     *
     * @see ReadDefaultReturnEnum
     */
    private ReadDefaultReturnEnum readDefaultReturn;

    /**
     * Read some additional fields. None are read by default.
     *
     * @see CellExtraTypeEnum
     */
    private Set<CellExtraTypeEnum> extraReadSet;
}
