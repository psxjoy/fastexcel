package ai.chat2db.excel.test.core.charset;

import ai.chat2db.excel.annotation.ExcelProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CharsetData {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年纪")
    private Integer age;
}
