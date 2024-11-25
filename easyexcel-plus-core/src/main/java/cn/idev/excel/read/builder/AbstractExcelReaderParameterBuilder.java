package cn.idev.excel.read.builder;

import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.read.metadata.ReadBasicParameter;
import cn.idev.excel.util.ListUtils;
import cn.idev.excel.metadata.AbstractParameterBuilder;

/**
 * Build ExcelBuilder
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractExcelReaderParameterBuilder<T extends AbstractExcelReaderParameterBuilder,
    C extends ReadBasicParameter> extends AbstractParameterBuilder<T, C> {
    /**
     * Count the number of added heads when read sheet.
     *
     * <p>
     * 0 - This Sheet has no head ,since the first row are the data
     * <p>
     * 1 - This Sheet has one row head , this is the default
     * <p>
     * 2 - This Sheet has two row head ,since the third row is the data
     *
     * @param headRowNumber
     * @return
     */
    public T headRowNumber(Integer headRowNumber) {
        parameter().setHeadRowNumber(headRowNumber);
        return self();
    }

    /**
     * Whether to use scientific Format.
     *
     * default is false
     *
     * @param useScientificFormat
     * @return
     */
    public T useScientificFormat(Boolean useScientificFormat) {
        parameter().setUseScientificFormat(useScientificFormat);
        return self();
    }

    /**
     * Custom type listener run after default
     *
     * @param readListener
     * @return
     */
    public T registerReadListener(ReadListener<?> readListener) {
        if (parameter().getCustomReadListenerList() == null) {
            parameter().setCustomReadListenerList(ListUtils.newArrayList());
        }
        parameter().getCustomReadListenerList().add(readListener);
        return self();
    }
}
