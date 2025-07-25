package cn.idev.excel.test.temp.dataformat;

import cn.idev.excel.metadata.data.ReadCellData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 **/
@Getter
@Setter
@EqualsAndHashCode
public class DataFormatData {
    private ReadCellData<String> date;
    private ReadCellData<String> num;
}
