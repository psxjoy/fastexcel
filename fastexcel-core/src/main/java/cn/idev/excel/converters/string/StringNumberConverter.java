package cn.idev.excel.converters.string;

import java.math.BigDecimal;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.util.DateUtils;
import cn.idev.excel.util.NumberDataFormatterUtils;
import cn.idev.excel.util.NumberUtils;
import cn.idev.excel.util.StringUtils;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * String and number converter
 *
 * @author Jiaju Zhuang
 */
public class StringNumberConverter implements Converter<String> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        // If there are "DateTimeFormat", read as date
        if (contentProperty != null && contentProperty.getDateTimeFormatProperty() != null) {
            return DateUtils.format(cellData.getNumberValue(),
                contentProperty.getDateTimeFormatProperty().getUse1904windowing(),
                contentProperty.getDateTimeFormatProperty().getFormat());
        }
        // If there are "NumberFormat", read as number
        if (contentProperty != null && contentProperty.getNumberFormatProperty() != null) {
            return NumberUtils.format(cellData.getNumberValue(), contentProperty);
        }
        // Excel defines formatting
        boolean hasDataFormatData = cellData.getDataFormatData() != null
            && cellData.getDataFormatData().getIndex() != null && !StringUtils.isEmpty(
            cellData.getDataFormatData().getFormat());
        if (hasDataFormatData) {
            return NumberDataFormatterUtils.format(cellData.getNumberValue(),
                cellData.getDataFormatData().getIndex(), cellData.getDataFormatData().getFormat(), globalConfiguration);
        }
        // Default conversion number
        return NumberUtils.format(cellData.getNumberValue(), contentProperty);
    }

    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(new BigDecimal(value));
    }
}
