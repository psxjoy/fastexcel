package cn.idev.excel.converters.bigdecimal;

import java.math.BigDecimal;
import java.text.ParseException;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.util.NumberUtils;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * Converter for handling the conversion between BigDecimal and Excel string types.
 *
 * @author Jiaju Zhuang
 */
public class BigDecimalStringConverter implements Converter<BigDecimal> {

    /**
     * Specifies the Java type supported by this converter.
     *
     * @return The class type of BigDecimal.
     */
    @Override
    public Class<BigDecimal> supportJavaTypeKey() {
        return BigDecimal.class;
    }

    /**
     * Specifies the Excel cell data type supported by this converter.
     *
     * @return The cell data type enumeration for strings.
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * Converts Excel cell data to a BigDecimal object.
     * This method parses the string value from the cell data into a BigDecimal using utility methods.
     *
     * @param cellData               The Excel cell data containing the string value.
     * @param contentProperty        The content property associated with the cell.
     * @param globalConfiguration    The global configuration for the conversion process.
     * @return                       The converted BigDecimal value from the string.
     * @throws ParseException        If there is an error parsing the string to a BigDecimal.
     */
    @Override
    public BigDecimal convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                        GlobalConfiguration globalConfiguration) throws ParseException {
        return NumberUtils.parseBigDecimal(cellData.getStringValue(), contentProperty);
    }

    /**
     * Converts a BigDecimal object to Excel cell data in string format.
     * This method formats the BigDecimal value into a string representation suitable for Excel cells.
     *
     * @param value                  The BigDecimal value to be converted.
     * @param contentProperty        The content property associated with the cell.
     * @param globalConfiguration    The global configuration for the conversion process.
     * @return                       The WriteCellData object containing the formatted string.
     */
    @Override
    public WriteCellData<?> convertToExcelData(BigDecimal value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return NumberUtils.formatToCellDataString(value, contentProperty);
    }
}
