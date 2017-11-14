package com.yibitong.common;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 包      名：  com.newzqxq.finances.tender.utils
 * 创 建 人：   寻欢·李
 * 创建时间：  2017/7/27 11:31
 * 修 改 人：
 * 修改日期：
 *
 * 分页bean
 */
@Data
public class PageModel implements Serializable {
    private int total; // 总数
    private int size; // 每页数量
    private int pages; // 页码
    private int current; // 当前页
    private List<?> records = Collections.emptyList(); // 查询结果

    public PageModel(Page page){
        this.total = page.getTotal();
        this.size = page.getSize();
        this.pages = page.getPages();
        this.current = page.getCurrent();
        this.records = page.getRecords();
    }

    public PageModel(Map page){
            this.total = Integer.parseInt(page.get("total").toString());
            this.size = Integer.parseInt(page.get("size").toString());
            this.pages = Integer.parseInt(page.get("pages").toString());
            this.current = Integer.parseInt(page.get("current").toString());
            this.records = (List<?>) page.get("records");
    }
}
