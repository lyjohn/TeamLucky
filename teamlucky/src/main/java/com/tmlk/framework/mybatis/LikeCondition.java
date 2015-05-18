package com.tmlk.framework.mybatis;

import java.util.Map;
import java.util.UUID;

/**
 * Created by laiguoqiang on 15/5/17.
 */
public class LikeCondition implements ICondition {

    private String propertyName;
    private Object value;

    public LikeCondition(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    public String generateExpression(Map<String, Object> params) {
        String uuid = UUID.randomUUID().toString();
        params.put(uuid, value);
        return propertyName + " like CONCAT(CONCAT('%', #{p." + uuid + "}),'%')";
    }

}
