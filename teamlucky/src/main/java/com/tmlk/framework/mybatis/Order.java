package com.tmlk.framework.mybatis;

/**
 * Created by laiguoqiang on 15/5/17.
 */
public class Order {

    private boolean ascending;
    private String propertyName;

    protected Order(String propertyName, boolean ascending) {
        this.propertyName = propertyName;
        this.ascending = ascending;
    }

    public static Order asc(String propertyName) {
        return new Order(propertyName, true);
    }

    public static Order desc(String propertyName) {
        return new Order(propertyName, false);
    }

    public String toSqlString() {
        return propertyName + " " + (ascending ? "asc" : "desc");
    }

}
