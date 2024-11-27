package cn.idev.excel.read.metadata.holder;

import java.util.List;

import cn.idev.excel.read.metadata.property.ExcelReadHeadProperty;
import cn.idev.excel.metadata.ConfigurationHolder;
import cn.idev.excel.read.listener.ReadListener;

/**
 *
 * Get the corresponding Holder
 *
 * @author Jiaju Zhuang
 **/
public interface ReadHolder extends ConfigurationHolder {
    /**
     * What handler does the currently operated cell need to execute
     *
     * @return Current {@link ReadListener}
     */
    List<ReadListener<?>> readListenerList();

    /**
     * What {@link ExcelReadHeadProperty} does the currently operated cell need to execute
     *
     * @return Current {@link ExcelReadHeadProperty}
     */
    ExcelReadHeadProperty excelReadHeadProperty();

}
