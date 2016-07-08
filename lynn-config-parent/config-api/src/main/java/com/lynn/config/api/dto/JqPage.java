package com.lynn.config.api.dto;

import java.util.List;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.tkj.cs.back.dto
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/7
 */
public class JqPage {

    private int iTotalRecords;

	private int draw;
    private int recordsTotal;
    private List<?> data;
    private int currentPage;
    private int pageCount;
    private int sEcho;
    private int pageSize;

    /**
     * @return the data
     */
    public List<?> getData() {
        return data;
    }

    /**
     * @param Data the Data to set
     */
    public void setData(List<?> data) {
        this.data = data;
    }

    /**
     * @return the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the pageCount
     */
    public int getPageCount() {
        return this.recordsTotal % this.getPageSize() == 0?this.recordsTotal / this.getPageSize():this.recordsTotal / this.getPageSize() + 1;
    }
//
//    /**
//     * @param pageCount the pageCount to set
//     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @return the sEcho
     */
    public int getsEcho() {
        return sEcho;
    }

    /**
     * @param sEcho the sEcho to set
     */
    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return this.getRecordsTotal();
    }

    public int getiTotalRecords() {
        return this.getRecordsTotal();
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return this.getRecordsTotal();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    @Override
    public String toString() {
    	return "JqPage [iTotalRecords=" + iTotalRecords + ", draw=" + draw + ", recordsTotal=" + recordsTotal + ", data=" + data + ", currentPage="
    			+ currentPage + ", pageCount=" + pageCount + ", sEcho=" + sEcho + ", pageSize=" + pageSize + "]";
    }
}

