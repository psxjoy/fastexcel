package cn.idev.excel.test.core.converter;

import cn.idev.excel.ExcelWriter;
import cn.idev.excel.FastExcel;
import cn.idev.excel.converters.Converter;
import cn.idev.excel.converters.ConverterKeyBuild;
import cn.idev.excel.test.util.TestFileUtil;
import cn.idev.excel.write.builder.ExcelWriterSheetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class CustomConverterTest {

    private static File converterCsvFile10;
    private static File converterExcelFile11;
    private static File converterExcelFile12;

    @BeforeAll
    static void init() {
        converterCsvFile10 = TestFileUtil.createNewFile("converter10.csv");
        converterExcelFile11 = TestFileUtil.createNewFile("converter11.xls");
        converterExcelFile12 = TestFileUtil.createNewFile("converter12.xlsx");
    }

    @Test
    void t01ConverterMapTest() throws Exception {
        TimestampStringConverter timestampStringConverter = new TimestampStringConverter();
        TimestampNumberConverter timestampNumberConverter = new TimestampNumberConverter();
        ExcelWriter excelWriter = FastExcel.write(converterCsvFile10)
            .registerConverter(timestampStringConverter)
            .registerConverter(timestampNumberConverter)
            .build();
        Map<ConverterKeyBuild.ConverterKey, Converter<?>> converterMap = excelWriter.writeContext()
            .currentWriteHolder().converterMap();
        excelWriter.write(data(), new ExcelWriterSheetBuilder().sheetNo(0).build());
        excelWriter.finish();
        Assertions.assertTrue(converterMap.containsKey(ConverterKeyBuild
            .buildKey(timestampStringConverter.supportJavaTypeKey(), timestampStringConverter.supportExcelTypeKey())));
        Assertions.assertTrue(converterMap.containsKey(ConverterKeyBuild
            .buildKey(timestampNumberConverter.supportJavaTypeKey(), timestampNumberConverter.supportExcelTypeKey())));
    }

    @Test
    void t02Write10() throws Exception {
        writeFile(converterCsvFile10);
    }

    @Test
    void t03Write11() throws Exception {
        writeFile(converterExcelFile11);
    }

    @Test
    void t04Write12() throws Exception {
        writeFile(converterExcelFile12);
    }

    private void writeFile(File file) throws Exception {
        FastExcel.write(file)
            .registerConverter(new TimestampNumberConverter())
            .registerConverter(new TimestampStringConverter())
            .sheet()
            .doWrite(data());
    }

    private List<CustomConverterWriteData> data() throws Exception {
        List<CustomConverterWriteData> list = new ArrayList<>();
        CustomConverterWriteData writeData = new CustomConverterWriteData();
        writeData.setTimestampStringData(Timestamp.valueOf("2020-01-01 01:00:00"));
        writeData.setTimestampNumberData(Timestamp.valueOf("2020-12-01 12:12:12"));
        list.add(writeData);
        return list;
    }

}
