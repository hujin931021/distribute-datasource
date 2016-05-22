package com.hujin.dota.group.core.datasource;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * 根据请求方法，设置tenantId对应的Master或者Slave数据源
 * 
 * @author hujin
 * 
 */
public class DataSourceAdvice implements MethodBeforeAdvice, AfterReturningAdvice {

    private static final List<String> PREFIX_METHOD_NAMES = Arrays.asList(new String[] { "get", "count", "find", "query","select" });

    
    public void before(Method method, Object[] args, Object target) throws Throwable {
    	DataSourceSwitch.setMaster();
        for (String prefixMethodName : PREFIX_METHOD_NAMES) {
            if (method.getName().startsWith(prefixMethodName)) {
                DataSourceSwitch.setSlave();
                break;
            }
        }
    }

    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        DataSourceSwitch.setMaster();
    }
}
