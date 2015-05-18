package com.tmlk.framework.mybatis;

import java.util.Map;

/**
 * Created by laiguoqiang on 15/5/17.
 */
public class IsNullCondition implements ICondition {

    private String propertyName;

    public IsNullCondition(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String generateExpression(Map<String, Object> params) {
        return propertyName + " is null";
    }

}
