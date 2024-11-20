package ai.chat2db.excel.test.temp.read;

import ai.chat2db.excel.event.AnalysisEventListener;
import ai.chat2db.excel.context.AnalysisContext;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author JiaJu Zhuang
 * @date 2020/4/9 16:33
 **/
@Slf4j
public class TestListener extends AnalysisEventListener {

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        log.info("解析一条:{}", JSON.toJSONString(o));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
