package com.hujin.dota.group.core.datasource;

import org.apache.commons.lang3.StringUtils;

import com.hujin.dota.group.common.constant.Constants;

/**
 * @author hujin
 * 
 */
public class DataSourceSwitch {

	/**
	*定义threadlocal对象存储数据源
	*/
    private static final ThreadLocal<String> DATASOURCE_TL = new ThreadLocal<String>();

	private static final String DATA_SOURCE_PREFIX = dota_grp_;
	private static final String DATA_SOURCE_MASTER_SUFFIX = _master;
	private static final String DATA_SOURCE_SLAVE_SUFFIX = _slave;

    private DataSourceSwitch() {
    }

    public static void set(String dsKey) {
        DATASOURCE_TL.set(dsKey);
    }

    public static String get() {
        String dsKey = DATASOURCE_TL.get();
        if (StringUtils.isEmpty(dsKey)) {
            throw new IllegalArgumentException("Cannot get datasource tenant key.");
        }
        return dsKey;
    }

	/**
	 * @author hujin
	 * @Des 设置主库
	*/
    public static void setMaster() {
        String tenantId = get();

        if (!tenantId.startsWith(DATA_SOURCE_PREFIX)) {
            set(DATA_SOURCE_PREFIX + tenantId + DATA_SOURCE_MASTER_SUFFIX);

        } else if (tenantId.endsWith(DATA_SOURCE_SLAVE_SUFFIX)) {
            set(tenantId.substring(0, tenantId.lastIndexOf(DATA_SOURCE_SLAVE_SUFFIX))
                    + DATA_SOURCE_MASTER_SUFFIX);
        }
    }

	/**
	 * @author hujin
	 * @Des 设置从库
	*/
    public static void setSlave() {
        String tenantId = get();

        if (!tenantId.startsWith(DATA_SOURCE_PREFIX)) {
            set(DATA_SOURCE_PREFIX + tenantId + DATA_SOURCE_SLAVE_SUFFIX);

        } else if (tenantId.endsWith(DATA_SOURCE_MASTER_SUFFIX)) {
            set(tenantId.substring(0, tenantId.lastIndexOf(DATA_SOURCE_MASTER_SUFFIX))
                    + DATA_SOURCE_SLAVE_SUFFIX);
        }
    }
}
