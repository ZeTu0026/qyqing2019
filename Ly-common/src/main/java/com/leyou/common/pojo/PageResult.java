package com.leyou.common.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private Long total; //总条数
    private Long totalpage; //总页数
    private List<T> items; //当前页数据


    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Long totalpage, List<T> items) {
        this.total = total;
        this.totalpage = totalpage;
        this.items = items;
    }
}
