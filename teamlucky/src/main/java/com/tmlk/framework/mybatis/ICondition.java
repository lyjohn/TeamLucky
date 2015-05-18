package com.tmlk.framework.mybatis;

import java.util.Map;

/**
 * Created by laiguoqiang on 15/5/17.
 */
public interface ICondition {

    String generateExpression(Map<String, Object> params);
}
