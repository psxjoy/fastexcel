package ai.chat2db.excel.metadata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ai.chat2db.excel.exception.ExcelGenerateException;
import ai.chat2db.excel.metadata.property.ColumnWidthProperty;
import ai.chat2db.excel.metadata.property.FontProperty;
import ai.chat2db.excel.metadata.property.LoopMergeProperty;
import ai.chat2db.excel.metadata.property.StyleProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * excel head
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class Head {
    /**
     * Column index of head
     */
    private Integer columnIndex;
    /**
     * It only has values when passed in {@link Sheet#setClazz(Class)} and {@link Table#setClazz(Class)}
     */
    private Field field;
    /**
     * It only has values when passed in {@link Sheet#setClazz(Class)} and {@link Table#setClazz(Class)}
     */
    private String fieldName;
    /**
     * Head name
     */
    private List<String> headNameList;
    /**
     * Whether index is specified
     */
    private Boolean forceIndex;
    /**
     * Whether to specify a name
     */
    private Boolean forceName;

    /**
     * column with
     */
    private ColumnWidthProperty columnWidthProperty;

    /**
     * Loop merge
     */
    private LoopMergeProperty loopMergeProperty;
    /**
     * Head style
     */
    private StyleProperty headStyleProperty;
    /**
     * Head font
     */
    private FontProperty headFontProperty;

    public Head(Integer columnIndex, Field field, String fieldName, List<String> headNameList, Boolean forceIndex,
        Boolean forceName) {
        this.columnIndex = columnIndex;
        this.field = field;
        this.fieldName = fieldName;
        if (headNameList == null) {
            this.headNameList = new ArrayList<>();
        } else {
            this.headNameList = headNameList;
            for (String headName : headNameList) {
                if (headName == null) {
                    throw new ExcelGenerateException("head name can not be null.");
                }
            }
        }
        this.forceIndex = forceIndex;
        this.forceName = forceName;
    }
}
