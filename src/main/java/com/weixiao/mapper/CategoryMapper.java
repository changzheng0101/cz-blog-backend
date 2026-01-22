package com.weixiao.mapper;

import com.weixiao.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author weixiao
 * @description 文章分类Mapper接口
 */
@Mapper
public interface CategoryMapper {
    /**
     * 新增分类
     *
     * @param category 分类对象
     * @return 影响行数
     */
    int insert(Category category);

    /**
     * 根据ID删除分类
     *
     * @param id 分类ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 更新分类
     *
     * @param category 分类对象
     * @return 影响行数
     */
    int update(Category category);

    /**
     * 根据ID查询分类
     *
     * @param id 分类ID
     * @return 分类对象
     */
    Category selectById(Long id);

    /**
     * 查询所有分类
     *
     * @return 分类列表
     */
    List<Category> selectAll();
}