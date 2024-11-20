package ai.chat2db.excel.test.core.celldata;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ai.chat2db.excel.enums.CellDataTypeEnum;
import ai.chat2db.excel.util.DateUtils;
import ai.chat2db.excel.test.util.TestFileUtil;
import ai.chat2db.excel.EasyExcel;
import ai.chat2db.excel.metadata.data.FormulaData;
import ai.chat2db.excel.metadata.data.WriteCellData;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author Jiaju Zhuang
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CellDataDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.createNewFile("cellData07.xlsx");
        file03 = TestFileUtil.createNewFile("cellData03.xls");
        fileCsv = TestFileUtil.createNewFile("cellDataCsv.csv");
    }

    @Test
    public void t01ReadAndWrite07() throws Exception {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() throws Exception {
        readAndWrite(file03);
    }

    @Test
    public void t03ReadAndWriteCsv() throws Exception {
        readAndWrite(fileCsv);
    }

    private void readAndWrite(File file) throws Exception {
        EasyExcel.write(file, CellDataWriteData.class).sheet().doWrite(data());
        EasyExcel.read(file, CellDataReadData.class, new CellDataDataListener()).sheet().doRead();
    }

    private List<CellDataWriteData> data() throws Exception {
        List<CellDataWriteData> list = new ArrayList<>();
        CellDataWriteData cellDataData = new CellDataWriteData();
        cellDataData.setDate(new WriteCellData<>(DateUtils.parseDate("2020-01-01 01:01:01")));
        WriteCellData<Integer> integer1 = new WriteCellData<>();
        integer1.setType(CellDataTypeEnum.NUMBER);
        integer1.setNumberValue(BigDecimal.valueOf(2L));
        cellDataData.setInteger1(integer1);
        cellDataData.setInteger2(2);
        WriteCellData<?> formulaValue = new WriteCellData<>();
        FormulaData formulaData = new FormulaData();
        formulaValue.setFormulaData(formulaData);
        formulaData.setFormulaValue("B2+C2");
        cellDataData.setFormulaValue(formulaValue);
        list.add(cellDataData);
        return list;
    }
}
