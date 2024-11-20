package ai.chat2db.excel.test.temp;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class StyleData {
    private byte[] byteValue;
    private Byte[] byteValue2;
    private byte byteValue1;
    private Byte byteValue4;
    private byte byteValue3;
    private String[] ss;
    private List<String> s1s;
}
