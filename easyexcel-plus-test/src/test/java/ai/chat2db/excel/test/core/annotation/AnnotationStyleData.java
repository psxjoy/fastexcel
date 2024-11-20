package ai.chat2db.excel.test.core.annotation;

import ai.chat2db.excel.enums.poi.FillPatternTypeEnum;
import ai.chat2db.excel.annotation.ExcelProperty;
import ai.chat2db.excel.annotation.write.style.ContentFontStyle;
import ai.chat2db.excel.annotation.write.style.ContentStyle;
import ai.chat2db.excel.annotation.write.style.HeadFontStyle;
import ai.chat2db.excel.annotation.write.style.HeadStyle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
@HeadFontStyle(fontHeightInPoints = 20, color = 15)
@ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 17)
@ContentFontStyle(fontHeightInPoints = 30, color = 22)
public class AnnotationStyleData {
    @ExcelProperty("字符串")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 14)
    @HeadFontStyle(fontHeightInPoints = 40, color = 51)
    @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 40)
    @ContentFontStyle(fontHeightInPoints = 50, color = 12)
    private String string;
    @ExcelProperty("字符串1")
    private String string1;
}
