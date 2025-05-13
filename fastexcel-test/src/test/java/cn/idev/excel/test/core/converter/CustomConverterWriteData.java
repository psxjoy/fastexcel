package cn.idev.excel.test.core.converter;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
public class CustomConverterWriteData {
    @ExcelProperty(value = "时间戳-字符串", converter = TimestampStringConverter.class)
    private Timestamp timestampStringData;
    @ExcelProperty(value = "时间戳-数字", converter = TimestampNumberConverter.class)
    private Timestamp timestampNumberData;
}
