package ai.chat2db.excel.test.temp.read;

import java.io.File;
import java.util.List;
import java.util.Map;

import ai.chat2db.excel.EasyExcel;
import ai.chat2db.excel.metadata.data.CellData;
import com.alibaba.fastjson2.JSON;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/

public class CommentTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentTest.class);

    @Test
    public void comment() throws Exception {
        File file = new File("D:\\test\\d1.xlsx");
        List<Map<Integer, CellData>> datas = EasyExcel.read(file).doReadAllSync();
        for (Map<Integer, CellData> data : datas) {
            LOGGER.info("数据:{}", JSON.toJSONString(data));
        }
    }

}
