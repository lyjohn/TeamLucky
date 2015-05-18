package com.tmlk.framework.util;

import java.math.RoundingMode;

import com.google.common.math.IntMath;

public class Pagination {

    /**
     * 每页个数
     */
    private int pageSize = Constants.DEFAULT_PAGE_SIZE;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总记录数
     */
    private int totalResult;

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 当前页起始索引
     */
    private int currentResult;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        if (totalResult % pageSize == 0)
            totalPage = totalResult / pageSize;
        else
            totalPage = totalResult / pageSize + 1;
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getCurrentPage() {
        if (currentPage <= 0)
            currentPage = 1;
//		if(currentPage>getTotalPage())
//			currentPage = getTotalPage();
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentResult() {
        currentResult = (getCurrentPage() - 1) * getPageSize();
        if (currentResult < 0)
            currentResult = 0;
        return currentResult;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult = currentResult;
    }

    public void checkPagination(int total) {
        this.totalResult = total;
        if (currentPage * pageSize >= total + pageSize) {
            int page = IntMath.divide(total, pageSize, RoundingMode.UP);
            if (page > 0) {
                currentPage = page;
            } else {
                currentPage = 1;
            }
        }
    }

}
