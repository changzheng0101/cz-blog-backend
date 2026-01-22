package com.weixiao.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果封装类
 *
 * @author weixiao
 * @version 1.0
 * @since 2026-01-21
 */
@Data
@NoArgsConstructor
public class PageBean<T> {
    /**
     * 总记录数
     */
    private long total;
    
    /**
     * 数据列表
     */
    private List<T> data;

    /**
     * 构造函数
     *
     * @param total 总记录数
     * @param data  数据列表
     */
    public PageBean(long total, List<T> data) {
        this.total = total;
        this.data = data;
    }
}