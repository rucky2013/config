package com.lynn.config.api.dto;

import lombok.Data;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : com.lynn.tkj.cs.core.dto
 * Description :
 * Author : cailinfeng
 * Date : 2016/1/7
 */
@Data
public class JqPageQueryCondition {
    private int sEcho;
    private int iColumns;
    /** 每页开始数标 */
    private int iDisplayStart;
    /** 每页显示条数 */
    private int iDisplayLength;
    /** 当前请求页 */
    private int currentPage = 1;

    public int getCurrentPage() {
        if (this.getIDisplayLength() >= 1 && this.getIDisplayStart() >= 0) {
            return (this.getIDisplayStart() / this.getIDisplayLength()) + 1;
        } else {
            currentPage = 1;
            return currentPage;
        }
    }
    public JqPage getPage(){
        JqPage page = new JqPage();
        page.setCurrentPage(this.getCurrentPage());
        page.setsEcho(this.getSEcho());
        page.setiTotalRecords(this.getIDisplayLength());
        return page;
    }
}
