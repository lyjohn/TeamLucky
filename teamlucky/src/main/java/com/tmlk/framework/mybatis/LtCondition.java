package com.tmlk.framework.mybatis;

import java.util.Map;
import java.util.UUID;

/**
 * Created by laiguoqiang on 15/5/17.
 */
public class LtCondition implements ICondition {

    private String propertyName;
    private Object value;

    public LtCondition(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    public String generateExpression(Map<String, Object> params) {
        String uuid = UUID.randomUUID().toString();
        params.put(uuid, value);
        return propertyName + " <#{p." + uuid + "}";
    }

}
