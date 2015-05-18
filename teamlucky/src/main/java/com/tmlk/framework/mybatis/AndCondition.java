package com.tmlk.framework.mybatis;

import java.util.Map;

/**
 * Created by laiguoqiang on 15/5/17.
 */
public class AndCondition implements ICondition {
    private ICondition con1;
    private ICondition con2;

    public AndCondition(ICondition con1, ICondition con2) {
        this.con1 = con1;
        this.con2 = con2;
    }

    @Override
    public String generateExpression(Map<String, Object> params) {
        return "(" + con1.generateExpression(params) + " and " + con2.generateExpression(params) + " )";
    }

}
