package com.hujin.dota.group.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author hujin
 * @Des 继承spring提供的数据源选择类，复写数据源方法
 */
public class DynamicRoutingDataSources extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceSwitch.get();
    }

}
