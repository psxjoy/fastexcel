package ai.chat2db.excel.test.temp.issue1662;

import java.util.Date;

import ai.chat2db.excel.annotation.ExcelProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Data1662 {
    @ExcelProperty(index = 0)
    private String str;
    @ExcelProperty(index = 1)
    private Date date;
    @ExcelProperty(index = 2)
    private double r;
}
