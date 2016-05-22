package com.hujin.dota.group.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author hujin
 * @Des �̳�spring�ṩ������Դѡ���࣬��д����Դ����
 */
public class DynamicRoutingDataSources extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceSwitch.get();
    }

}
