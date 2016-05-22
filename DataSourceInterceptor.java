/*******************************************************************************
 * COPYRIGHT (C) 2016 hujin Limited - ALL RIGHTS RESERVED.
 * Creation Date: 2016年4月18日
 *******************************************************************************/

package com.hujin.dota.group.core.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hujin.dota.group.common.constant.Constants;
import com.hujin.dota.group.common.util.RestUtil;
import com.hujin.dota.group.core.datasource.DataSourceSwitch;
import com.hujin.dota.operation.framework.base.json.JsonUtil;

/**
 * 获取用户ajax请求的tenantId并设置到ThreadLocal中，供后续操作对应DB使用
 * 
 * @author hujin
 */
public class DataSourceInterceptor extends HandlerInterceptorAdapter {

	/**
     * 获取的ajax请求参数key
     */
    public static final String AJAX_PARAMETER = "ajax_parameter";
	/**
     * 租户ID，用于确认数据源
     */
    public static final String TENANT_ID = "tenantId";

    @SuppressWarnings({ "unchecked" })
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String parameter = RestUtil.getRestData(request, response);

        if (StringUtils.isNotBlank(parameter)) {
            Map<String, Object> paramMap = JsonUtil.toBean(parameter, Map.class);
            if (null != paramMap) {
                String tenantId = (null == paramMap.get(TENANT_ID) ? null : (String) paramMap.get(TENANT_ID));
                if (StringUtils.isNotBlank(tenantId)) {
                    DataSourceSwitch.set(tenantId);
                    // 后续拦截器使用
                    request.setAttribute(AJAX_PARAMETER, paramMap);
                    return true;
                } else {
                    throwException();
                }
            } else {
                throwException();
            }
        } else {
            throwException();
        }

        return true;
    }

    private void throwException() {
        throw new IllegalArgumentException("Cannot get tenant Id, please check your json parameter.");
    }

}
