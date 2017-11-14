package com.yibitong.common;

import com.yibitong.utils.stringUtils.StrUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName：PageBean
 * Description：分页对象
 * Author：yy
 * Created：2017/11/14
 */
public class PageBean implements Serializable {

    //分页查询条件
    private Map<String, Object> queryParamMap;//查询条件
    private int pageNo = 1;//页码，默认是第一页
    private int pageSize = 15;//每页显示的记录数，默认是15
    private int startRow = 0;
    private String orderColunm;//排序字段
    private String orderMode;//排序方式

    //分页查询结果
    private int totalPage;//总页数
    private int total;//总记录数
    private List<?> rows;//查询的结果


    public PageBean(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageBean() {
    }

    public PageBean(int pageNo) {
        this.pageNo = pageNo <= 0?1:pageNo;
        this.pageSize = 10;
        this.startRow = (this.pageNo - 1) * this.pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        // 在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        this.setTotalPage(totalPage);
    }

    public void setClounmOrder(String orderClounm, String orderMode){
        if(StrUtils.isEmpty(this.getOrderColunm()) && StrUtils.notEmpty(orderClounm)){
            this.setOrderColunm(orderClounm);
            if(StrUtils.notEmpty(orderMode)){
                this.setOrderMode(orderMode);
            }else{
                this.setOrderMode("ASC");
            }
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public int getOffset() {
        int offset = (pageNo - 1) * pageSize;
        return offset > 0 ? offset : 0;
    }

    public Map<String, Object> getQueryParamMap() {
        return queryParamMap;
    }

    public void setQueryParamMap(Map<String, Object> queryParamMap) {
        this.queryParamMap = queryParamMap;
    }

    public String getOrderColunm() {
        return orderColunm;
    }

    public void setOrderColunm(String orderColunm) {
        this.orderColunm = orderColunm;
    }

    public String getOrderMode() {
        return orderMode;
    }

    public void setOrderMode(String orderMode) {
        this.orderMode = orderMode;
    }

    public static int getPageRow(int page_num, int page_size) {
        page_num = page_num <= 0?1:page_num;
        page_size = page_size <=0?15:page_size;
        return (page_num - 1) * page_size;
    }
}
